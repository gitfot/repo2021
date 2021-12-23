package com.fun.excel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.fun.excel.temp.ChildParam;
import com.fun.excel.temp.MetaData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wanwan 2021/12/8
 */
@RequestMapping("/export")
@RestController
@Slf4j
public class DemoController {

	/**
	 * 文件下载并且失败的时候返回json（默认失败了会返回一个有部分数据的Excel）
	 *
	 * @since 2.1.1
	 */
	@PostMapping("/demo1")
	public void downloadFailedUsingJson(HttpServletResponse response, @RequestBody MetaData data) throws IOException {

		ExcelWriter excelWriter = null;
		System.out.println("123");
		// 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("utf-8");
			// 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
			String fileName = StringUtils.isBlank(data.getTitle()) ? String.valueOf(System.currentTimeMillis()) : data.getTitle();
			//response.addHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
			String path = "/E:/Desktop/";
			// 这里 指定文件
			excelWriter = EasyExcel.write(path + fileName.replaceAll("/", "-") + ".xlsx", ChildParam.class).build();

			WriteSheet writeSheet = EasyExcel.writerSheet(data.getTitle()).build();

			for (Map.Entry<String, List<ChildParam>> entry : data.getResult().entrySet()) {
				excelWriter.write(entry.getValue(), writeSheet);
			}

		} catch (Exception e) {
			// 重置response
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("status", "failure");
			resultMap.put("message", "下载文件失败" + e.getMessage());
			response.getWriter().println(JSON.toJSONString(resultMap));
		}
		finally {
			// 千万别忘记finish 会帮忙关闭流
			if (excelWriter != null) {
				excelWriter.finish();

			}
		}
	}
}
