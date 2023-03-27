package io.github.thongtpvinh3.utils;//package utils;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpHeaders;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//
//import java.io.IOException;
//
//public class HttpClientUtil {
//
//    public static void main(String[] args) {
//
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            HttpGet request = new HttpGet("https://httpbin.org/get");
//
//            // Add Request headers
//            request.addHeader("custom-key", "thong");
//            request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
//
//            try (CloseableHttpResponse response = httpClient.execute(request)) {
//                System.out.println(response.getProtocolVersion());
//                System.out.println(response.getStatusLine().getStatusCode());
//                System.out.println(response.getStatusLine().getReasonPhrase());
//                System.out.println(response.getStatusLine().toString());
//
//                HttpEntity entity = response.getEntity();
//                if (entity != null) {
//                    // Return it as a String
//                    String result = EntityUtils.toString(entity);
//                    System.out.println(result);
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
