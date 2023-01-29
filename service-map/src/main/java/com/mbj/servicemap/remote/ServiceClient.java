package com.mbj.servicemap.remote;

import com.mbj.internalcommmon.constant.AmapConstant;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.response.ServiceResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-29 13:51
 * @Description:
 * @Version:
 */
@Service
public class ServiceClient {

    @Value("${amap.key}")
    private String key;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult add(String name){
        // 组装调用url
        String urlBuilder = AmapConstant.SERVICE_URL +
                "?" +
                "key=" + key +
                "&name=" + name;

        ResponseEntity<String> forEntity = restTemplate.postForEntity(urlBuilder, null,String.class);
        String body = forEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        String sid = data.getString("sid");
        ServiceResponse serviceResponse =new ServiceResponse();
        serviceResponse.setSid(sid);
        return ResponseResult.success(serviceResponse);
    }

}
