package com.file.export.controller;

import com.file.export.utils.CompressUtils;
import com.fun.common.utils.result.Result;
import com.fun.common.utils.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author zz
 * @date 2021/9/14
 */
@RestController
@RequestMapping("/compress")
@Slf4j
public class CompressController {

	@GetMapping("/zip")
	public Result<?> compress() {
		Boolean result = false;
		try (FileOutputStream fileOutputStream = new FileOutputStream("E:\\project\\guns\\test.zip")) {
			result = CompressUtils.zip("E:\\Desktop\\image\\icon", fileOutputStream);
		} catch (IOException e) {
			e.printStackTrace();
			log.info("压缩失败！");
		}
		return ResultUtil.status(result);
	}

	@GetMapping("/downloadWithZip")
	public Result<?> download(HttpServletResponse response) {
		Boolean result = CompressUtils.downloadZip("E:\\Desktop\\image\\icon", "test", response);
		return ResultUtil.status(result);
	}
}
