package com.mbj.servicemap.remote;

import com.mbj.internalcommmon.constant.AmapConstant;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.response.TerminalResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-29 13:51
 * @Description:
 * @Version:
 */
@Service
public class TerminalClient {

    @Value("${amap.key}")
    private String key;

    @Value("${amap.sid}")
    private String sid;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult<TerminalResponse> add(String name,String desc){
        // 组装调用url
        String urlBuilder = AmapConstant.TERMINAL_URL +
                "?" +
                "key=" + key +
                "&sid=" + sid +
                "&name=" + name +
                "&desc=" + desc;

        System.out.println("创建终端请求："+urlBuilder);
        ResponseEntity<String> forEntity = restTemplate.postForEntity(urlBuilder, null,String.class);
        String body = forEntity.getBody();
        System.out.println("创建终端响应："+body);
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        String tid = data.getString("tid");
        TerminalResponse terminalResponse =new TerminalResponse();
        terminalResponse.setTid(tid);
        return ResponseResult.success(terminalResponse);
    }

    public ResponseResult<List<TerminalResponse>> aroundsearch(String center, Integer radius){
        // 组装调用url
        String urlBuilder = AmapConstant.TERMINAL_AROUNDSEARCH +
                "?" +
                "key=" + key +
                "&sid=" + sid +
                "&center=" + center +
                "&radius=" + radius;

        System.out.println("终端搜索请求："+urlBuilder);
        ResponseEntity<String> forEntity = restTemplate.postForEntity(urlBuilder, null,String.class);
        String body = forEntity.getBody();
        System.out.println("终端搜索响应："+body);
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        JSONArray results = data.getJSONArray("results");
        List<TerminalResponse> terminalResponseList = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            TerminalResponse terminalResponse = new TerminalResponse();
            JSONObject jsonObject = results.getJSONObject(i);
            // desc是carId
            String desc = jsonObject.getString("desc");
            if (desc == null || desc.equals("\"null\"") || StringUtils.isBlank(desc)){
                continue;
            }
            Long carId = Long.valueOf(desc);
            String tid = jsonObject.getString("tid");

            JSONObject location = jsonObject.getJSONObject("location");
            String longitude = location.getString("longitude");
            String latitude = location.getString("latitude");

            terminalResponse.setCarId(carId);
            terminalResponse.setTid(tid);
            terminalResponse.setLongitude(longitude);
            terminalResponse.setLatitude(latitude);
            terminalResponseList.add(terminalResponse);
        }
        return ResponseResult.success(terminalResponseList);
    }

}
