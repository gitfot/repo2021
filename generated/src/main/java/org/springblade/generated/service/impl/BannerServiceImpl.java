package org.springblade.generated.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.generated.entity.Banner;
import org.springblade.generated.mapper.BannerMapper;
import org.springblade.generated.service.BannerService;
import org.springblade.generated.vo.BannerVO;
import org.springframework.stereotype.Service;

/**
 * (habitant_banner)表服务实现类
 *
 * @author makejava
 * @since 2021-05-16 02:25:07
 */
@Service
@AllArgsConstructor
public class BannerServiceImpl extends BaseServiceImpl<BannerMapper, Banner> implements BannerService {

	/**
	 * 自定义分页
	 */
	@Override
	public IPage<BannerVO> selectBannerPage(IPage<BannerVO> page, BannerVO banner) {
		QueryWrapper<BannerVO> wrapper = new QueryWrapper<>(banner);
		//wrapper.apply("is_deleted = 0");
		//wrapper.lambda().orderByDesc(BannerVO::getCreateTime);
		return page.setRecords(baseMapper.selectBannerPage(page, wrapper));
	}
}
