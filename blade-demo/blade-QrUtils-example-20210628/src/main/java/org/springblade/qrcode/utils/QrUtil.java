package org.springblade.qrcode.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 二维码生成工具类
 * @author zz
 * @date 2021/6/28
 */
public class QrUtil {
	//默认保存路径
	public static String defaultPath = "e:\\desktop\\qr.png";
	//logo默认位置
	public static String logoPath = "e:\\desktop\\logo.jpg";
	//二维码参数基本设置
	public static Map<EncodeHintType, Object> hints = new HashMap<>();

	static {
		//设置编码为UTF-8
		hints.put(EncodeHintType.CHARACTER_SET, CharacterSetECI.UTF8);
		//设置二维码纠错等级
		// L:7%纠错率  M:15%纠错率 Q:25%纠错率 H:30纠错率   纠错率越高越容易识别出来,但是纠错率越高识别速度越慢
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		//设置二维码白边的范围(此值可能不生效)
		hints.put(EncodeHintType.MARGIN, 0.5);
	}

	public static BufferedImage createDefault(String content) {
		try {
			return QREncode(content, 400, 400, defaultPath, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage createWithPath(String content,String savePath, String logoPath) {
		try {
			return QREncode(content, 400, 400, savePath, logoPath);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 二维码生成
	 * @param content 内容
	 * @param width 宽度
	 * @param height 高度
	 * @param savePath 保存路径
	 * @param logoPath logo所在路径
	 */
	public static BufferedImage QREncode(String content, Integer width, Integer height, String savePath, String logoPath) throws Exception {

		//设置生成的图片类型为QRCode
		BarcodeFormat format = BarcodeFormat.QR_CODE;
		//初始化一个矩阵
		BitMatrix matrix = Objects.isNull(width) || Objects.isNull(height) ?
			new MultiFormatWriter().encode(content, format, 400, 400)
			: new MultiFormatWriter().encode(content, format, width, height);

		//设置位矩阵对象转图片的参数(前景色,背景色)
		MatrixToImageConfig config = new MatrixToImageConfig(Color.gray.getRGB(), Color.white.getRGB());
		//位矩阵对象转BufferedImage对象
		BufferedImage qrcode = MatrixToImageWriter.toBufferedImage(matrix, config);

		//保存为普通的二维码
		if (StringUtils.isEmpty(logoPath)) {
			return qrcode;
			//直接写入文件到指定路径
			//ImageIO.write(qrcode, "png", new File(StringUtils.isNotBlank(savePath) ? savePath : defaultPath));
		}
		//保存为带logo的二维码
		else {
			//BufferedImage logoMatrix = LogoMatrix(qrcode, logoPath);
			//ImageIO.write(logoMatrix, "png", new File(StringUtils.isNotBlank(savePath) ? savePath : defaultPath));
			return LogoMatrix(qrcode, logoPath);
		}
	}

	public static BufferedImage LogoMatrix(BufferedImage matrixImage, String logoPath) throws IOException{
		File logoFile = new File(logoPath);
		//读取二维码图片，并构建绘图对象
		Graphics2D g2 = matrixImage.createGraphics();

		//读取logo图片
		BufferedImage logo = ImageIO.read(logoFile);
		//设置logo宽和高
		int logoWidth = (int) (matrixImage.getWidth() * 0.12);
		int logoHeight = (int) (matrixImage.getHeight() * 0.12);
		//设置返回logo的二维码图片的起始位置
		int x = (matrixImage.getWidth() - logoWidth) / 2;
		int y = (matrixImage.getHeight() - logoHeight) / 2;

		//开始绘制图片
		g2.drawImage(logo,x,y, logoWidth, logoHeight, null);
		BasicStroke stroke = new BasicStroke(5,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
		// 设置笔画对象
		g2.setStroke(stroke);
		//指定弧度的圆角矩形
		RoundRectangle2D.Float round = new RoundRectangle2D.Float(x, y, logoWidth, logoHeight,20,20);
		g2.setColor(Color.white);
		// 绘制圆弧矩形
		g2.draw(round);

		//设置logo 有一道灰色边框
		BasicStroke stroke2 = new BasicStroke(0.6F,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
		g2.setStroke(stroke2);
		RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(x+2, y+2, logoWidth-4, logoHeight-4,20,20);
		g2.setColor(new Color(128,128,128));
		// 绘制圆弧矩形
		g2.draw(round2);
		g2.dispose();
		matrixImage.flush() ;
		return matrixImage ;
	}

	/**
	 * 识别二维码
	 */
	public static String QRDecode(String targetPath) throws IOException, NotFoundException {
		File file = new File(targetPath);
		MultiFormatReader reader = new MultiFormatReader();
		//将图片文件转为BufferedImage对象
		BufferedImage bufferedImage = ImageIO.read(file);
		//生成BinaryBitmap对象
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
		//二维码参数基本设置
		Map<DecodeHintType, Object> hints = new HashMap<>();
		hints.put(DecodeHintType.CHARACTER_SET, CharacterSetECI.UTF8);
		//识别二维码
		Result result = reader.decode(binaryBitmap, hints);
		bufferedImage.flush();
		return result.getText();
	}
}
