package org.fun.test.handset;

import com.fun.common.utils.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HandsetRateplanController {

    @Resource
    private HandsetRateplanService handsetRateplanService;

    @PostMapping("/getHandsetRateplanDetail")
    public R<?> getHandsetRateplanDetail(){
        return R.data(handsetRateplanService.getHandsetRateplanDetail());
    }

	@PostMapping("/getHandsetDetailOfCust")
	public R<?> getHandsetDetailOfCust() throws Exception {
		return R.data(handsetRateplanService.getHandsetDetailOfCust());
	}
}
