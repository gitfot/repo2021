package com.file.export.controller;

import com.deepoove.poi.XWPFTemplate;
import com.file.export.service.ExportCompreAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author zz
 * @date 2021/9/15
 */
@RestController
@RequestMapping("/export")
@RequiredArgsConstructor
public class ExportWordFormWithPoiController {

	private final ExportCompreAccessService exportCompreAccessService;

	/**
	 * 导出综合评标表格（综合评估法）
	 */
	@GetMapping("/comprehensive")
	public void exportCompreForm(HttpServletRequest request, HttpServletResponse response) {
		try {
			//       Long projectId = 1433070661300604930L; Long expertId = 1377167803790897153L; Long assessId = 1433072433054306305L;
			//导出详细评审表
			Long projectId = Long.parseLong(request.getParameter("projectId"));
			Long assessId = Long.parseLong(request.getParameter("assessId"));
			exportCompreAccessService.exportInitial(assessId, projectId);
			exportCompreAccessService.exportDetail(assessId, projectId);
			exportCompreAccessService.exportPrice(assessId, projectId);

			/*PriceInfo priceInfo = priceInfoService.getById(projectId);
			if (!Objects.isNull(priceInfo)) {
				exportCompreAccessService.getDataMap().put("projectName", priceInfo.getProjectName());
				exportCompreAccessService.getDataMap().put("projectNumber", priceInfo.getSysCode());
			}*/

			write("demo.docx", exportCompreAccessService.getDataMap(), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 写出文件
	 *
	 * @param templateName 模板名称
	 * @param dataMap      导出数据
	 * @param response     响应
	 */
	public void write(String templateName, Map<String, Object> dataMap, HttpServletResponse response) {
		String basePath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "wordtemplate/";
		String resource = basePath + templateName;
		//生成文件名
		Long time = System.currentTimeMillis();
		// 生成的word格式
		String formatSuffix = ".docx";
		// 拼接后的文件名,带后缀
		String fileName = time + formatSuffix;
		// 设置强制下载不打开
		response.setContentType("application/force-download");
		// 设置文件名
		response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);

		try (OutputStream out = response.getOutputStream();
			 XWPFTemplate template = XWPFTemplate.compile(resource).render(dataMap)
		) {
			template.write(out);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
