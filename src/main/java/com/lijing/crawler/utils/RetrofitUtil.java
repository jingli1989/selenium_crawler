package com.lijing.crawler.utils;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * retrofit 工具类
 * Created by Lijing on 2017/3/2.
 */
public class RetrofitUtil {
    /**
     * 创建retrofit
     * @param headerParams 头部参数
     * @param apiBaseUrl 域名
     * @return retrofit
     */
    public static Retrofit createRetrofit(final Map<String,String> headerParams, String apiBaseUrl) {

        Interceptor interceptor  =new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder b = chain.request().newBuilder();
                if(headerParams!=null) {
                    for (String key : headerParams.keySet()) {
                        b.addHeader(key, headerParams.get(key));
                    }
                }
                return chain.proceed(b.build());
            }
        };

        X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        };

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");

            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);

        OkHttpClient client = builder
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .sslSocketFactory(sslContext.getSocketFactory())
                .hostnameVerifier(DO_NOT_VERIFY)
                .build();

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(apiBaseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}
