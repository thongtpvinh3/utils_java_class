package utils;

import constant.HttpStatus;
import exception.NetworkApiException;
import helper.GsonHelper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.*;

@Slf4j
public class OkHttpUtil {

    private static final int BAD_REQUEST = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int TOO_MANY_REQUESTS = 429;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final Set<Integer> FAILURE_STATUS_CODE = new HashSet<>(Arrays.asList(BAD_REQUEST, UNAUTHORIZED, FORBIDDEN, NOT_FOUND, TOO_MANY_REQUESTS, INTERNAL_SERVER_ERROR, SERVICE_UNAVAILABLE));
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient client;
    private final GsonHelper gsonHelper;
    private static OkHttpUtil instance;

    private OkHttpUtil() {
        client = new OkHttpClient();
        gsonHelper = GsonHelper.getInstance();
    }

    public static OkHttpUtil getInstance() {
        if (instance == null) {
            instance = new OkHttpUtil();
        }
        return instance;
    }

    public String get(String endpoint, Map<String, String> params, Map<String, String> headers) throws NetworkApiException {
        if (headers == null) {
            headers = new HashMap<>();
        }
        String url = buildUrl(endpoint, params);
        Request request = new Request.Builder().url(url).headers(Headers.of(headers)).get().build();
        Call call = client.newCall(request);
        ResponseBody body = null;
        String res = "";
        try (Response response = call.execute()) {
            body = response.body();
            if (body != null) {
                res = body.string();
            }
            if (FAILURE_STATUS_CODE.contains(response.code())) {
                log.error(res);
                handleApiException(response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (body != null) {
                body.close();
            }
        }
        return res;
    }

    public String postWithJsonBody(String endpoint, Map<String, String> params, Map<String, String> headers, Object body) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        String url = buildUrl(endpoint, params);
        ResponseBody responseBody = null;
        try {
            RequestBody requestBody = RequestBody.create(gsonHelper.convertFromObject(body), MEDIA_TYPE_JSON);
            Request request = new Request.Builder().url(url).headers(Headers.of(headers)).post(requestBody).build();
            try (Response response = doCall(request)) {
                if (FAILURE_STATUS_CODE.contains(response.code())) {
                    handleApiException(response.code());
                }
                responseBody = response.body();
                if (responseBody != null) {
                    return responseBody.string();
                }
            }
            return "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (responseBody != null) {
                responseBody.close();
            }
        }
    }

    public String postWithFormData(String endpoint, Map<String, String> params, Map<String, String> headers, Map<String, String> data) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        String url = buildUrl(endpoint, params);
        ResponseBody responseBody = null;
        try {
            MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
            data.forEach(requestBody::addFormDataPart);
            Request request = new Request.Builder().url(url).headers(Headers.of(headers)).post(requestBody.build()).build();
            try (Response response = doCall(request)) {
                handleApiException(response.code());
                responseBody = response.body();
            }
            if (responseBody != null) {
                return responseBody.string();
            }
            return "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (responseBody != null) {
                responseBody.close();
            }
        }
    }

    public String delete(String endpoint, Map<String, String> params, Map<String, String> headers) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        String url = buildUrl(endpoint, params);
        Request request = new Request.Builder().url(url).headers(Headers.of(headers)).delete().build();
        ResponseBody body = null;
        try (Response response = doCall(request)) {
            handleApiException(response.code());
            body = response.body();
            if (body != null) {
                return body.string();
            }
            return "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (body != null) {
                body.close();
            }
        }
    }

    public String patchWithJsonBody(String endpoint, Map<String, String> params, Map<String, String> headers, Object body) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        String url = buildUrl(endpoint, params);
        ResponseBody responseBody = null;
        try {
            RequestBody requestBody = RequestBody.create(gsonHelper.convertFromObject(body), MEDIA_TYPE_JSON);
            Request request = new Request.Builder().url(url).headers(Headers.of(headers)).patch(requestBody).build();
            Call call = client.newCall(request);
            try (Response response = call.execute()) {
                handleApiException(response.code());
                responseBody = response.body();
                if (responseBody != null) {
                    return responseBody.string();
                }
            }
            return "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (responseBody != null) {
                responseBody.close();
            }
        }
    }

    public String putWithJsonBody(String endpoint, Map<String, String> params, Map<String, String> headers, Object body) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        String url = buildUrl(endpoint, params);
        ResponseBody responseBody = null;
        try {
            RequestBody requestBody = RequestBody.create(gsonHelper.convertFromObject(body), MEDIA_TYPE_JSON);
            Request request = new Request.Builder().url(url).headers(Headers.of(headers)).put(requestBody).build();
            try (Response response = doCall(request)) {
                handleApiException(response.code());
                responseBody = response.body();
            }
            if (responseBody != null) {
                return responseBody.string();
            }
            return "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (responseBody != null) {
                responseBody.close();
            }
        }
    }

    private String buildUrl(String endpoint, Map<String, String> params) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(endpoint)).newBuilder();
        if (params != null) {
            params.forEach(urlBuilder::addQueryParameter);
        }
        return urlBuilder.build().toString();
    }

    private Response doCall(Request request) {
        Call call = client.newCall(request);
        try {
            return call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleApiException(int code) {
        if (!FAILURE_STATUS_CODE.contains(code)) {
            return;
        }
        switch (code) {
            case BAD_REQUEST:
                throw new NetworkApiException(HttpStatus.SC_BAD_REQUEST, "Invalid information send to server!");
            case UNAUTHORIZED:
                throw new NetworkApiException(HttpStatus.SC_UNAUTHORIZED, "Authentication failed!");
            case FORBIDDEN:
                throw new NetworkApiException(HttpStatus.SC_FORBIDDEN, "Not authorized!");
            case NOT_FOUND:
                throw new NetworkApiException(HttpStatus.SC_BAD_REQUEST, "Endpoint is not found in server!");
            case TOO_MANY_REQUESTS:
                throw new NetworkApiException(HttpStatus.SC_BAD_REQUEST, "Too many request call to server!");
            case INTERNAL_SERVER_ERROR:
                throw new NetworkApiException(HttpStatus.SC_BAD_REQUEST, "Platform errored when solve this request!");
            case SERVICE_UNAVAILABLE:
                throw new NetworkApiException(HttpStatus.SC_BAD_REQUEST, "Platform service unavailable!");
            default:
        }
    }
}
