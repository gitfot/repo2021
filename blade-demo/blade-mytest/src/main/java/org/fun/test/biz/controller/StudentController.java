package org.fun.test.biz.controller;

import com.fun.core.api.R;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.fun.test.biz.entity.Student;
import org.fun.test.biz.service.StudentService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 学生表-测试(student)表控制层
 *
 * @author zz
 * @since 2022-02-15 23:10:16
 */
@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@Api(value = "xxx", tags = "xxx接口")
public class StudentController {

	private final StudentService studentService;

	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/test1")
	public void testTransactional() {
		studentService.removeById(1);
		if (false) {
			throw new RuntimeException();
		}
		studentService.removeById(2);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入student")
	public R<?> submit(@Valid @RequestBody Student student) {
		return R.status(studentService.saveOrUpdate(student));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<?> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		Iterable<String> split = Splitter.onPattern(",").omitEmptyStrings().trimResults().split(ids);
		return R.status(studentService.removeByIds(Lists.newArrayList(split)));
	}
}
