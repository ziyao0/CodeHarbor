package com.ziyao.harbor.usercenter.security.processer;

import com.ziyao.harbor.core.JwtTokenGenerator;
import com.ziyao.harbor.core.token.JwtInfo;
import com.ziyao.harbor.core.utils.BeanMaps;
import com.ziyao.harbor.core.utils.Dates;
import com.ziyao.harbor.core.utils.SecurityUtils;
import com.ziyao.harbor.usercenter.security.api.AccessToken;
import com.ziyao.harbor.usercenter.security.auth.SuccessAuthDetails;
import com.ziyao.harbor.usercenter.security.core.LoginPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 处理并生成token
 *
 * @author ziyao zhang
 * @since 2023/5/8
 */
@Component
public class AuthTokenProcessor implements LoginPostProcessor {

    @Override
    public AccessToken process(SuccessAuthDetails details) {

        JwtInfo jwtInfo = JwtInfo.builder()
                .payload(BeanMaps.tomap(details))
                .expiresAt(Dates.skip(30))
                .secret(SecurityUtils.loadJwtTokenSecret())
                .jwtHeader(new JwtInfo.JwtHeader()).build();

        String token = new JwtTokenGenerator().generateToken(jwtInfo);
        return new AccessToken(token);
    }
}
