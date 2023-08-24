package com.ziyao.harbor.core.jwt;

import lombok.Data;

import java.util.Map;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
@Data
public class JwtData {

    private Header header;

    private Map<String, ?> payload;

    @Data
    static class Header {
        private String issuer;

        private String subject;

        private String audience;
    }


}