package com.ziyao.harbor.core.jwt;

import lombok.Data;

import java.util.Map;

/**
 * @author zhangziyao
 * @since  2023/4/23
 */
@Data
public class JwtInfo {

    private Header header;

    private Map<String, Object> payload;

    @Data
    static class Header {

        private String issuer;

        private String subject;

        private String audience;
    }


}
