package com.ziyao.harbor.openplatform.web.http;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.lang.NonNull;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * @author ziyao zhang
 * @since 2024/1/10
 */
public class SslHttpRequestFactory extends SimpleClientHttpRequestFactory {

    public SslHttpRequestFactory() {
        setOutputStreaming(false);
    }

    @Override
    protected void prepareConnection(@NonNull HttpURLConnection connection, @NonNull String httpMethod) throws IOException {
        if (connection instanceof HttpsURLConnection) {
            HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
            super.prepareConnection(connection, httpMethod);
            TrustManager[] trustAllCerts = new TrustManager[]{new NoCheckX509TrustManager()};
            SSLContext sslContext;
            try {
                sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                throw new RuntimeException(e);
            }
            httpsConnection.setSSLSocketFactory(new DelegateSSLSocketFactory(sslContext.getSocketFactory()));
            // 不验证主机名.
            httpsConnection.setHostnameVerifier((s, sslSession) -> true);
        }
        super.prepareConnection(connection, httpMethod);
    }

    /**
     * 不检查证书
     */
    static class NoCheckX509TrustManager implements X509TrustManager {

        /**
         * 该方法检查客户端的证书，若不信任该证书则抛出异常。由于我们不需要对客户端进行认证，
         * <p>
         * 因此我们只需要执行默认的信任管理器的这个方法。JSSE中，默认的信任管理器类为TrustManager。
         *
         * @param chain    chain
         * @param authType authType
         */
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {

        }

        /**
         * 该方法检查服务器的证书，若不信任该证书同样抛出异常。通过自己实现该方法，可以使之信任我们指定的任何证书。
         * <p>
         * 在实现该方法时，也可以简单的不做任何处理，即一个空的函数体，由于不会抛出异常，它就会信任任何证书。
         *
         * @param chain    chain
         * @param authType authType
         */
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    static class DelegateSSLSocketFactory extends SSLSocketFactory {
        private static final String[] protocols = new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"};
        private final SSLSocketFactory delegate;

        DelegateSSLSocketFactory(SSLSocketFactory delegate) {
            this.delegate = delegate;
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return delegate.getDefaultCipherSuites();
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return delegate.getSupportedCipherSuites();
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
            final Socket underlyingSocket = delegate.createSocket(socket, host, port, autoClose);
            return overrideProtocol(underlyingSocket);
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
            final Socket underlyingSocket = delegate.createSocket(host, port);
            return overrideProtocol(underlyingSocket);
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localAddress, int localPort) throws IOException, UnknownHostException {
            final Socket underlyingSocket = delegate.createSocket(host, port, localAddress, localPort);
            return overrideProtocol(underlyingSocket);
        }

        @Override
        public Socket createSocket(InetAddress inetAddress, int port) throws IOException {
            final Socket underlyingSocket = delegate.createSocket(inetAddress, port);
            return overrideProtocol(underlyingSocket);
        }

        @Override
        public Socket createSocket(InetAddress inetAddress, int port, InetAddress localAddress, int localPort) throws IOException {
            final Socket underlyingSocket = delegate.createSocket(inetAddress, port, localAddress, localPort);
            return overrideProtocol(underlyingSocket);
        }

        private Socket overrideProtocol(final Socket socket) {
            if (socket instanceof SSLSocket) {
                ((SSLSocket) socket).setEnabledProtocols(protocols);
            }
            return socket;
        }
    }
}
