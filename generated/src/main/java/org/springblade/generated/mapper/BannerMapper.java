package org.springblade.generated.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springblade.generated.entity.Banner;
import org.springblade.generated.vo.BannerVO;

import java.util.List;

/**
 * (habitant_banner)Mapper接口
 *
 * @author makejava
 * @since 2021-05-16 02:39:25
 */
public interface BannerMapper extends BaseMapper<Banner> {
	/**
	 * 自定义分页
	 */
	List<BannerVO> selectBannerPage(IPage<BannerVO> page, @Param("ew") Wrapper<?> wrapper);
}
