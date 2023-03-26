package org.example;

import utils.OkHttpUtil;

import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuZ3V5dmFudHJ1b25nIiwiaWF0IjoxNjc5ODE0MDEwLCJleHAiOjE2Nzk4MTc2MTB9.RegZyML3VLhE5kUzna921oVzp1CfAGXeSSEklOhfluY";
        OkHttpUtil okHttpUtil = OkHttpUtil.getInstance();
        Map<String, String> header = new HashMap<>() {{
            put("Authorization", "Bearer " + token);
        }};
        System.out.println(okHttpUtil.get("http://localhost:8081/api/v1/user/username/giacatluong", null, header));
    }
}
