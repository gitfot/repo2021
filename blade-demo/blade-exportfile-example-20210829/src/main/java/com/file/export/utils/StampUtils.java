package com.file.export.utils;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;

/**
 * @author zz
 * @date 2021/9/7
 */
public class StampUtils {

	/**
	 * 插入水印的坐标位置
	 */
	private static final float DEFAULT_ABSOLUTE_X = 160f;
	private static final float DEFAULT_ABSOLUTE_Y = 40f;
	/**
	 * 水印大小比例
	 */
	private static final float DEFAULT_SCALE_PERCENT = 10;
	/**
	 * 水印透明度
	 */
	private static final float DEFAULT_FILL_OPACITY = 1f;


	public static void pdfStamp(String urlJPG, String urlPdf) throws Exception{
		String newFileName = String.valueOf((int) (( Math.random() * 9 + 1) * 1000));
		pdfStamp(urlJPG, urlPdf, newFileName);
	}

	public static void pdfStamp(String urlJPG, String urlPdf, String newFileName) throws Exception{
		pdfStamp(urlJPG, urlPdf, newFileName, DEFAULT_ABSOLUTE_X, DEFAULT_ABSOLUTE_Y, DEFAULT_SCALE_PERCENT);
	}

	/**
	 * 功能描述: 为pdf文件加上水印
	 * @param urlJPG 水印图片
	 * @param urlPdf PDF路径
	 */
	public static void pdfStamp(String urlJPG, String urlPdf, String newFileName, float absoluteX, float absoluteY, float scalePercent) throws Exception {

		// 获取去除后缀的文件路径
		String fileName = urlPdf.substring(0, urlPdf.lastIndexOf("."));
		String pdfUrl = fileName + newFileName + ".pdf";
		//要加水印的原pdf文件路径
		PdfReader reader = new PdfReader(urlPdf, "PDF".getBytes());
		//加了水印后要输出的路径
		PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(pdfUrl));
		// 插入水印
		Image img = Image.getInstance(urlJPG);
		//原pdf文件的总页数
		int pageSize = reader.getNumberOfPages();
		//印章位置(左下角位置)
		img.setAbsolutePosition(absoluteX, absoluteY);
		//印章大小
		img.scalePercent(scalePercent);

		PdfGState gs = new PdfGState();
		//设置透明度
		gs.setFillOpacity(DEFAULT_FILL_OPACITY);

		for (int i = 1; i <= pageSize; i++) {
			//背景被覆盖
            PdfContentByte under = stamp.getUnderContent(i);
			//文字被覆盖
//			PdfContentByte under = stamp.getOverContent(i);
			under.setGState(gs);
			//添加电子印章
			under.addImage(img);
		}
		// 关闭
		stamp.close();
		//关闭
		reader.close();
	}

}
