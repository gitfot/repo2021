package org.springblade.generated.feign;


import org.springblade.core.launch.constant.AppConstant;
import org.springblade.generated.entity.Banner;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
	value = AppConstant.XXX,
	fallback = BannerClientFallBack.class
)
public interface BannerFeignService {

	String API_PREFIX = "/banner";

	@GetMapping(API_PREFIX + "/getBannerById")
	Banner getBannerById(@RequestParam("id") String id);
}
