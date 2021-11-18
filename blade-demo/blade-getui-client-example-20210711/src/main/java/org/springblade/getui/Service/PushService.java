package org.springblade.getui.Service;

import com.getui.push.v2.sdk.common.ApiResult;
import com.getui.push.v2.sdk.dto.req.message.PushChannel;
import com.getui.push.v2.sdk.dto.req.message.PushDTO;
import org.springblade.getui.dto.PushParam;

/**
 * @author zz
 * @date 2021/7/11
 */
public interface PushService {

	ApiResult<?> customPush(PushParam param, Integer pushType, Integer isTrans);

	PushChannel getPushChannel(String channelType, PushParam param);

	PushDTO getPushDTO(PushParam param);
}
