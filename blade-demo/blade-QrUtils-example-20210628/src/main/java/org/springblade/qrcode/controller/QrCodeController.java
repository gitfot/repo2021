package org.springblade.qrcode.controller;

import com.alibaba.fastjson.JSONObject;
import com.fun.common.utils.result.Result;
import com.fun.common.utils.result.ResultUtil;
import org.springblade.qrcode.utils.QrUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author zz
 * @date 2021/9/6
 */
@RestController
@RequestMapping("/code")
public class QrCodeController {

	/**
	 * 在流中直接返回图片
	 */
	@GetMapping("/getCode")
	public void getCode(HttpServletResponse response) {
		try {
			response.setContentType("image/jpg");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			OutputStream stream = response.getOutputStream();

			BufferedImage qrCodeImage = QrUtil.createDefault("http://www.baidu.com");
			//直接写入文件到指定路径
			//ImageIO.write(qrcode, "png", new File(StringUtils.isNotBlank(savePath) ? savePath : defaultPath));
			ImageIO.write(qrCodeImage, "png", stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/getBase64Code")
	public Result<?> getBase64Code(HttpServletResponse response) {
		JSONObject object = new JSONObject();

			response.setContentType("image/jpg");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			BufferedImage qrCodeImage = QrUtil.createDefault("http://www.baidu.com");

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
		{
			//直接写入文件到指定路径
			//ImageIO.write(qrcode, "png", new File(StringUtils.isNotBlank(savePath) ? savePath : defaultPath));
			ImageIO.write(qrCodeImage, "png", outputStream);
			BASE64Encoder encoder = new BASE64Encoder();
			String base64 = encoder.encodeBuffer(outputStream.toByteArray()).trim();
			base64 = base64.replaceAll("\n", "").replaceAll("\r", "");
			object.put("code", "data:image/jpg;base64," + base64);
			//response.getWriter().write(object.toString());
			return ResultUtil.success(object);
		} catch (IOException e) {
			e.printStackTrace();
			return ResultUtil.sendErrorMessage("生成图片失败！"  );
		}
	}
}
