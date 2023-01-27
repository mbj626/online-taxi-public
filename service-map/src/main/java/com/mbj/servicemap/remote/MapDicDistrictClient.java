package com.mbj.servicemap.remote;

import com.mbj.internalcommmon.constant.AmapConstant;
import com.mbj.internalcommmon.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-27 22:39
 * @Description:
 * @Version:
 */
@Service
@Slf4j
public class MapDicDistrictClient {

    @Value("${amap.key}")
    private String key;

    @Autowired
    private RestTemplate restTemplate;

    public String initDicDistrict(String keywords){
        // 组装调用url
        String urlBuilder = AmapConstant.DISTRICT_URL +
                "?" +
                "keywords=" + keywords +
                "&subdistrict=" + 3 +
                "&key=" + key;

        ResponseEntity<String> forEntity = restTemplate.getForEntity(urlBuilder, String.class);
        return forEntity.getBody();
    }

}
