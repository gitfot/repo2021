package com.file.export.utils;

import com.fun.common.utils.utils.FileTransferUtils;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

/**
 * 压缩文件夹工具类
 */
public class CompressUtils {

	private static final Logger logger = LoggerFactory.getLogger(FileTransferUtils.class);

	/**
	 * 压缩并下载
	 * @param srcDir 目标文件夹
	 */
	public static Boolean downloadZip(String srcDir, String fileName, HttpServletResponse response) {
		//支持中文
		try {
			fileName = URLEncoder.encode(fileName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.info("文件编码失败！");
			return false;
		}
		// 设置强制下载不打开
		response.setContentType("application/force-download");
		// 设置文件名
		response.addHeader("Content-Disposition", "attachment;fileName=" + fileName+".zip");

		try (OutputStream outputStream = response.getOutputStream()) {
			return zip(srcDir, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 压缩文件夹到指定输出流中，可以是本地文件输出流，也可以是web响应下载流
	 *
	 * @param srcDir 源文件夹
	 * @param outputStream 压缩后文件的输出流
	 * @throws IOException IO异常，抛出给调用者处理
	 */
	public static Boolean zip(String srcDir, OutputStream outputStream) {
		try (
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
			ArchiveOutputStream out = new ZipArchiveOutputStream(bufferedOutputStream)
		) {
			Path start = Paths.get(srcDir);
			Files.walkFileTree(start, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					ArchiveEntry entry = new ZipArchiveEntry(dir.toFile(), start.relativize(dir).toString());
					out.putArchiveEntry(entry);
					out.closeArchiveEntry();
					return super.preVisitDirectory(dir, attrs);
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					try (InputStream input = new FileInputStream(file.toFile())) {
						ArchiveEntry entry = new ZipArchiveEntry(file.toFile(), start.relativize(file).toString());
						out.putArchiveEntry(entry);
						IOUtils.copy(input, out);
						out.closeArchiveEntry();
					}
					return super.visitFile(file, attrs);
				}

			});
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 解压zip文件到指定文件夹
	 * @param zipFileName 源zip文件路径
	 * @param destDir 解压后输出路径
	 * @throws IOException IO异常，抛出给调用者处理
	 */
	public static void unzip(String zipFileName, String destDir) throws IOException {
		try (
			InputStream inputStream = new FileInputStream(zipFileName)
		) {
			unzip(inputStream, destDir);
		}

	}

	/**
	 * 从输入流中获取zip文件，并解压到指定文件夹
	 *
	 * @param inputStream zip文件输入流，可以是本地文件输入流，也可以是web请求上传流
	 * @param destDir 解压后输出路径
	 * @throws IOException IO异常，抛出给调用者处理
	 */
	public static void unzip(InputStream inputStream, String destDir) throws IOException {
		try (
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			ArchiveInputStream in = new ZipArchiveInputStream(bufferedInputStream)
		) {
			ArchiveEntry entry;
			while (Objects.nonNull(entry = in.getNextEntry())) {
				if (in.canReadEntryData(entry)) {
					File file = Paths.get(destDir, entry.getName()).toFile();
					if (entry.isDirectory()) {
						if (!file.exists()) {
							file.mkdirs();
						}
					} else {
						try (
							OutputStream out = new FileOutputStream(file)
						) {
							IOUtils.copy(in, out);
						}
					}
				} else {
					System.out.println(entry.getName());
				}
			}
		}

	}

}
