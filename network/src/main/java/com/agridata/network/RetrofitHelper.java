package com.agridata.network;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.agridata.network.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitHelper {

    //baseUrl
    public static String baseUrlWanBei;
    public static String baseUrl;
    public static String baseUrlImg;
    public static String baseUrlSendSms;
    private static volatile RetrofitHelper instance;

    private final Retrofit.Builder retrofitBuilder;


    /**
     * 使用默认BASE_URL
     *
     * @return
     */
    public Retrofit getRetrofit(int code) {
        return getRetrofit(null, code);
    }

    /**
     * 方法重载
     *
     * @param baseUrl baseUrl不为空，替换默认BASE_URL
     * @return
     */
    public Retrofit getRetrofit(String baseUrl, int code) {
        LogUtil.d("lzx----》", code + "");
        Retrofit retrofit = null;
        if (TextUtils.isEmpty(baseUrl)) {
            if (code == 1) {
                retrofit = retrofitBuilder.baseUrl(BuildConfig.baseUrlWanBei).build();
            } else if (code == 2) {
                retrofit = retrofitBuilder.baseUrl(BuildConfig.baseUrl).build();
            } else if (code == 3) {
                retrofit = retrofitBuilder.baseUrl(BuildConfig.baseUrlImg).build();
            } else if (code == 4) {
                retrofit = retrofitBuilder.baseUrl(BuildConfig.baseUrlTest).build();
            } else if (code == 5) {
                retrofit = retrofitBuilder.baseUrl(BuildConfig.baseUrlAhiapi).build();
            } else {
                retrofit = retrofitBuilder.baseUrl(BuildConfig.baseUrlSendSms).build();
            }

        } else {
            retrofit = retrofitBuilder.baseUrl(baseUrl).build();
        }
        return retrofit;
    }

    private RetrofitHelper() {
        //默认BASE_URL赋值
        //构建OkHttpClient对象
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Interceptor headerInterceptor = chain -> {
            Request originalRequest = chain.request();
            Request.Builder requestBuilder = originalRequest.newBuilder();
            if (!isLoginRequest(originalRequest)) {
                // 获取 Bearer Token
                String token = CubeUI.getInstance().getCubeDataProvider().getUserToken();
                if (token == null || token.isEmpty()) {

                } else {

                    requestBuilder.addHeader("Authorization", "Bearer " + token);
                }
            }
//            登录等公用：4b23d35ed33311ef9e139764e5a7f062
//            短信：ae8dfc3fc50211efbf3ceb93ea660718
//            app：d128ef58c4c411ef982f412f5ac7e1f4
//            客户端：45597eecc4c511efacbe1f8afc5f6956
            if (isLoginRequest(originalRequest)) {
                requestBuilder.addHeader("vb-access-key", "4b23d35ed33311ef9e139764e5a7f062");
            } else if (isSmsRequest(originalRequest)) {
                requestBuilder.addHeader("vb-access-key", "ae8dfc3fc50211efbf3ceb93ea660718");//ae8dfc3fc50211efbf3ceb93ea660718
            } else {
                requestBuilder.addHeader("vb-access-key", "d128ef58c4c411ef982f412f5ac7e1f4");//d128ef58c4c411ef982f412f5ac7e1f4
            }

            requestBuilder.method(originalRequest.method(), originalRequest.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
        //日志拦截器
        // 打印请求log日志
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> LogUtil.i("okhttp:" + message));
        setSSL(builder);
        builder.addInterceptor(getRequestInterceptor()).addInterceptor(headerInterceptor).addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)).connectTimeout(10, TimeUnit.SECONDS).readTimeout(45, TimeUnit.SECONDS).writeTimeout(45, TimeUnit.SECONDS).retryOnConnectionFailure(true);
        //builder.followRedirects(false);
        OkHttpClient okHttpClient = builder.build();
        //创建retrofit构建者对象
        retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.client(okHttpClient).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 添加公用的头部内容
     */
    @NonNull
//    private Interceptor getHeaderInterceptor() {
//        return chain -> {
//            Request originalRequest = chain.request();
//            LogUtil.i("lzx----》", "originalRequest: " + originalRequest.body().toString());
//            Request.Builder builder = originalRequest.newBuilder();
//            // 判断请求是否为登录请求
////            if (!isLoginRequest(originalRequest)) {
////                // 获取 Bearer Token
////                String token = CubeUI.getInstance().getCubeDataProvider().getUserToken();
////                LogUtil.d("Interceptor", "Token: " + token);  // 输出 Token 值
////                if (token != null && !token.isEmpty()) {
////                    builder.addHeader("Authorization", "Bearer " + token);
////                } else {
////                    LogUtil.d("Interceptor", "Token is null or empty");
////                }
////            }
//            // 添加 appkey 头部
//            builder.addHeader("appkey", "d74f55dec4e611ef9e955b732d2f8091");
//            builder.method(originalRequest.method(), originalRequest.body());
//            Request request = builder.build();
//            return chain.proceed(request);
//        };
//    }

    /**
     * 获取请求的参数，处理 GET 和 POST 请求
     */ private Map<String, String> getRequestParams(Request request) throws IOException {
        Map<String, String> params = new HashMap<>();

        // 处理 GET 请求参数
        if ("GET".equalsIgnoreCase(request.method())) {
            HttpUrl url = request.url();
            for (int i = 0; i < url.querySize(); i++) {
                params.put(url.queryParameterName(i), url.queryParameterValue(i));
            }
        }
        // 处理 POST 请求参数 (假设请求体是 JSON)
        else if ("POST".equalsIgnoreCase(request.method())) {
            RequestBody body = request.body();
            if (body != null) {
                // 如果请求体是 JSON 格式
                String jsonBody = bodyToString(body);
                LogUtil.i("lzx----》", "jsonBody: " + jsonBody);
                if (!jsonBody.isEmpty()) {
                    // 使用 Gson 解析 JSON 请求体
                    Gson gson = new Gson();
                    Map<String, String> jsonMap = gson.fromJson(jsonBody, new TypeToken<Map<String, String>>() {
                    }.getType());
                    params.putAll(jsonMap);
                }
            }
        }

        return params;
    }

    /**
     * 将 RequestBody 转换为 String
     */
    private String bodyToString(RequestBody body) throws IOException {
        final BufferedSink sink = Okio.buffer(Okio.sink(new ByteArrayOutputStream()));
        body.writeTo(sink);
        sink.flush();
        return sink.buffer().readUtf8();
    }

    /**
     * 使用 HmacSHA1 算法生成签名
     *
     * @param params 请求参数
     * @return 签名字符串
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String generateHmacSHA1Signature(Map<String, String> params) {
        try {
            // 构建请求参数的排序字符串
            StringBuilder paramString = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                paramString.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            if (paramString.length() > 0) {
                paramString.deleteCharAt(paramString.length() - 1);  // 移除最后一个 "&"
            }
            String secret = "veinbase@1024";
            // 生成 HmacSHA1 签名
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA1");
            mac.init(keySpec);
            byte[] rawHmac = mac.doFinal(paramString.toString().getBytes());

            // 使用 Base64 编码生成签名
            return Base64.getEncoder().encodeToString(rawHmac);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断是否是登录请求，登录请求不添加 Bearer Token
     * 你可以根据实际的 URL 或其他标识来判断
     */
    private boolean isLoginRequest(Request request) {
        // 假设登录请求的 URL 里包含 "/login" 或其他特定标识
        return request.url().toString().contains("/user/login");
    }


    private boolean isUserRequest(Request request) {
        // 假设登录请求的 URL 里包含 "/login" 或其他特定标识
        return request.url().toString().contains("/user/getUserInfoByUserId");
    }


    private boolean isSmsRequest(Request request) {
        // 假设登录请求的 URL 里包含 "/login" 或其他特定标识
        return request.url().toString().contains("/sms/code/send");
    }

    /**
     * 添加公用的 query 参数
     */
    @NonNull
    private Interceptor getRequestInterceptor() {
        return chain -> {
            Request originalRequest = chain.request();

            // 添加新的参数
            HttpUrl.Builder authorizedUrlBuilder = originalRequest.url().newBuilder().scheme(originalRequest.url().scheme()).host(originalRequest.url().host());
//                    .addQueryParameter("clientType", "mobile")
            // 新的请求
            Request newRequest = originalRequest.newBuilder().method(originalRequest.method(), originalRequest.body()).url(authorizedUrlBuilder.build()).build();

            return chain.proceed(newRequest);
        };
    }

    /**
     * 设置忽略ssl证书验证
     *
     * @param builder
     */
    private void setSSL(OkHttpClient.Builder builder) {
        try {
            @SuppressLint("CustomX509TrustManager") X509TrustManager xtm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());
            HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            builder.sslSocketFactory(sslContext.getSocketFactory(), xtm);
            builder.hostnameVerifier(DO_NOT_VERIFY);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            LogUtil.e("====设置忽略ssl证书验证=====" + e.getMessage());
        }
    }

}
