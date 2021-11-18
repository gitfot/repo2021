package org.springblade.generated.feign;


import org.springblade.generated.entity.Banner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 服务降级接口
 */
@Component
public class BannerClientFallBack implements BannerFeignService {

	@Override
	@GetMapping(API_PREFIX + "/getBannerById")
	public Banner getBannerById(@RequestParam("id") String id) {
		return null;
	}
}
