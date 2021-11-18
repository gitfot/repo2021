package org.springblade.generated.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.generated.entity.Banner;
import org.springblade.generated.service.BannerService;
import org.springblade.generated.vo.BannerVO;
import org.springblade.generated.wrapper.BannerWrapper;

import javax.validation.Valid;

/**
 * (habitant_banner)表控制层
 *
 * @author makejava
 * @since 2021-05-16 02:25:02
 */
@RestController
@RequestMapping("banner")
@AllArgsConstructor
@Api(value = "xxx", tags = "xxx接口")
public class BannerController {

	private final BannerService bannerService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入banner")
	public R<Banner> detail(Banner banner) {
		Banner detail = bannerService.getOne(Condition.getQueryWrapper(banner));
		return R.data(detail);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入banner")
	public R<IPage<BannerVO>> list(Banner banner, Query query) {
		IPage<Banner> pages = bannerService.page(Condition.getPage(query), Condition.getQueryWrapper(banner));
		return R.data(BannerWrapper.build().pageVO(pages));
	}

	/**
	 * 自定义分页 住户表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入banner")
	public R<IPage<BannerVO>> page(BannerVO banner, Query query) {
		IPage<BannerVO> pages = bannerService.selectBannerPage(Condition.getPage(query), banner);
		return R.data(pages);
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入banner")
	public R<?> save(@Valid @RequestBody Banner banner) {
		return R.status(bannerService.save(banner));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入banner")
	public R<?> update(@Valid @RequestBody Banner banner) {
		return R.status(bannerService.updateById(banner));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入banner")
	public R<?> submit(@Valid @RequestBody Banner banner) {
		return R.status(bannerService.saveOrUpdate(banner));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<?> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(bannerService.deleteLogic(Func.toLongList(ids)));
	}
}
