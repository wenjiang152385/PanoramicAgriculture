package com.oraro.panoramicagriculture.http.retrofit;

import android.content.Context;
import android.util.Log;

import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
 * okHttp的配置
 * 其实完全可以直接写到RetrofitUtils  但是请求头比较重要 单独拿出来 方便你们扩展
 */
public class OkHttpUtils {

    private static OkHttpClient mOkHttpClient;

    /**
     * 获取OkHttpClient对象
     */
    public static OkHttpClient getOkHttpClient() {

        if (null == mOkHttpClient) {

            //同样okhttp3后也使用build设计模式
            mOkHttpClient = new OkHttpClient.Builder()
                    //添加拦截器
                    .addInterceptor(mTokenInterceptor)
                    //添加网络连接器
                    //设置请求读写的超时时间
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

        }

        return mOkHttpClient;
    }

    /**
     * 云端响应头拦截器
     * 用于添加统一请求头  请按照自己的需求添加
     * 主要用于加密传输 和设备数据传输
     */
    private static final Interceptor mTokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request authorised = originalRequest.newBuilder()
                    .build();
            return chain.proceed(authorised);
        }
    };

    private static SSLSocketFactory getSocketFactory(Context context) {
        SSLSocketFactory sslSocketFactory = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = new BufferedInputStream(context.getAssets().open("client.cer"));
            final Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                Log.i("Longer", "ca=" + ((X509Certificate) ca).getSubjectDN());
                Log.i("Longer", "key=" + ((X509Certificate) ca).getPublicKey());
            } finally {
                caInput.close();
            }
            // Create an SSLContext that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLSv1", "AndroidOpenSSL");
            sslContext.init(null, new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain,
                                                       String authType)
                                throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain,
                                                       String authType)
                                throws CertificateException {
                            for (X509Certificate cert : chain) {
                                Log.i("Longer", "key=" + (cert).getPublicKey());

                                // Make sure that it hasn't expired.
                                cert.checkValidity();

                                // Verify the certificate's public key chain.
                                try {
                                    cert.verify(((X509Certificate) ca).getPublicKey());
                                } catch (NoSuchAlgorithmException e) {
                                    e.printStackTrace();
                                } catch (InvalidKeyException e) {
                                    e.printStackTrace();
                                } catch (NoSuchProviderException e) {
                                    e.printStackTrace();
                                } catch (SignatureException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            }, null);

            sslSocketFactory = sslContext.getSocketFactory();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }
}
