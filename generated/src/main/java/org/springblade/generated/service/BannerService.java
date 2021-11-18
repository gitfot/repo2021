package org.springblade.generated.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.generated.entity.Banner;
import org.springblade.generated.vo.BannerVO;

/**
 * (habitant_banner)表服务接口
 *
 * @author makejava
 * @since 2021-05-16 02:25:05
 */
public interface BannerService extends BaseService<Banner> {

	/**
	 * 自定义分页
	 */
	IPage<BannerVO> selectBannerPage(IPage<BannerVO> page, BannerVO banner);
}
