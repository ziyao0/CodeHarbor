package com.ziyao.harbor.core;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author ziyao zhang
 * @since 2023/10/17
 */
public abstract class SslSocketConstructor {

    /**
     * 通过这个类我们可以获得SSLSocketFactory，这个东西就是用来管理证书和信任证书的
     * 构造
     * 构造器
     *
     * @return {@link SSLSocketFactory}
     */
    public static SSLSocketFactory constructSslSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, constructTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 获取TrustManagers
     *
     * @return {@link TrustManager[]}
     */
    public static TrustManager[] constructTrustManagers() {
        // 不校检证书链
        return new TrustManager[]{new X509TrustManager() {

            /**
             * 该方法检查客户端的证书，若不信任该证书则抛出异常。由于我们不需要对客户端进行认证，
             * 因此我们只需要执行默认的信任管理器的这个方法。JSSE中，默认的信任管理器类为TrustManager。
             *
             * @param chain    chain
             * @param authType authType
             */
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {

            }

            /**
             *该方法检查服务器的证书，若不信任该证书同样抛出异常。通过自己实现该方法，可以使之信任我们指定的任何证书。
             * <p>
             * 在实现该方法时，也可以简单的不做任何处理，即一个空的函数体，由于不会抛出异常，它就会信任任何证书。
             *
             * @param chain chain
             * @param authType authType
             */
            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {

            }

            /**
             * OK http3.0以前返回null,3.0以后返回new X509Certificate[]{};
             * @return X509Certificate[]
             */
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        }};
    }

    /**
     * 获取TrustManager
     *
     * @return {@link TrustManager[]}
     */
    public static X509TrustManager constructX509TrustManager() {
        // 不校检证书链
        return new X509TrustManager() {
            /**
             * 该方法检查客户端的证书，若不信任该证书则抛出异常。由于我们不需要对客户端进行认证，
             * 因此我们只需要执行默认的信任管理器的这个方法。JSSE中，默认的信任管理器类为TrustManager。
             *
             * @param chain    chain
             * @param authType authType
             */
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            /**
             *该方法检查服务器的证书，若不信任该证书同样抛出异常。通过自己实现该方法，可以使之信任我们指定的任何证书。
             * <p>
             * 在实现该方法时，也可以简单的不做任何处理，即一个空的函数体，由于不会抛出异常，它就会信任任何证书。
             *
             * @param chain chain
             * @param authType authType
             */
            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {

            }

            /**
             * OK http3.0以前返回null,3.0以后返回new X509Certificate[]{};
             * @return X509Certificate[]
             */
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    /**
     * 获取HostnameVerifier
     *
     * @return HostnameVerifier
     */
    public static HostnameVerifier constructHostnameVerifier(HostnameVerifier hv) {
        // @formatter:off
        return (hostname, session) -> {
            if (hv != null) {
                return hv.verify(hostname, session);
            } else {
                return HttpsURLConnection.getDefaultHostnameVerifier()
                        .verify(hostname, session);
            }
        };
        // @formatter:on
    }

    private SslSocketConstructor() {
    }
}
