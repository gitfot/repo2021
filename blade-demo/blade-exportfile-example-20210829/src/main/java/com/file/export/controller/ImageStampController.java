package com.file.export.controller;

import com.file.export.utils.StampUtils;
import com.fun.common.utils.result.Result;
import com.fun.common.utils.result.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author zz
 * @date 2021/9/7
 */
@RestController
@RequestMapping("/stamp")
public class ImageStampController {

	@PostMapping("/pdfStamp")
	public Result<?> testPdfStamp() {
		try {
			StampUtils.pdfStamp("E:\\Desktop\\image\\signImage.png", "E:\\Desktop\\image\\test.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultUtil.success();
	}

	public static void main(String[] args) {
		try {
			StampUtils.pdfStamp("E:\\Desktop\\image\\signImage.png", "E:\\Desktop\\image\\test.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
