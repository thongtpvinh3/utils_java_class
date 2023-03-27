package io.github.thongtpvinh3.static_init;//package static_init;
//
//import com.squareup.okhttp.OkHttpClient;
//
//import java.util.concurrent.TimeUnit;
//
//public class CallApiClient {
//    private static CallApiClient instance;
//    private final OkHttpClient client;
//
//    private CallApiClient() {
//        client = new OkHttpClient();
//        client.setConnectTimeout(60, TimeUnit.SECONDS);
//    }
//
//    public static CallApiClient getInstance() {
//        if (instance == null) {
//            instance = new CallApiClient();
//        }
//        return instance;
//    }
//}
