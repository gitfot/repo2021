package org.springblade.generated.feign;


import lombok.AllArgsConstructor;
import org.springblade.generated.entity.Banner;
import org.springblade.generated.service.BannerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@AllArgsConstructor
public class BannerFeignServiceImpl implements BannerFeignService {

	private final BannerService bannerService;

	@Override
	@GetMapping(API_PREFIX + "/getBannerById")
	public Banner getBannerById(@RequestParam("id") String id) {
		return bannerService.getById(id);
	}

}
