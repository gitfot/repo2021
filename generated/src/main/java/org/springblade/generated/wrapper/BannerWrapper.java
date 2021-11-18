package org.springblade.generated.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.generated.entity.Banner;
import org.springblade.generated.vo.BannerVO;

/**
 * (habitant_banner)wrapper接口
 *
 * @author makejava
 * @since 2021-05-16 02:41:20
 */
public class BannerWrapper extends BaseEntityWrapper<Banner, BannerVO> {

	public static BannerWrapper build() {
		return new BannerWrapper();
	}

	@Override
	public BannerVO entityVO(Banner entity) {
		BannerVO entityVO = BeanUtil.copy(entity, BannerVO.class);
		return entityVO;
	}
}
