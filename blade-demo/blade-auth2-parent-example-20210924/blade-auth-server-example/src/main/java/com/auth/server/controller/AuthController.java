//package com.auth.server.controller;
//
//import com.auth.server.domain.dto.Oauth2TokenDto;
//import com.fun.common.utils.api.CommonResult;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.security.Principal;
//import java.util.Map;
//import java.util.Objects;
//
///**
// * @author wanwan 2021/9/29
// */
//@RestController
//@RequestMapping("/oauth")
//@RequiredArgsConstructor
//public class AuthController {
//
//	private final TokenEndpoint tokenEndpoint;
//	private final PasswordEncoder passwordEncoder;
//
//	/**
//	 * Oauth2登录认证
//	 */
//	@PostMapping("/token")
//	public CommonResult<Oauth2TokenDto> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
//		//parameters.put("password", passwordEncoder.encode(parameters.get("password")));
//		OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
//		Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
//			.token(Objects.requireNonNull(oAuth2AccessToken).getValue())
//			.refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
//			.expiresIn(oAuth2AccessToken.getExpiresIn())
//			.tokenHead("Bearer ").build();
//		return CommonResult.success(oauth2TokenDto);
//	}
//
//}
