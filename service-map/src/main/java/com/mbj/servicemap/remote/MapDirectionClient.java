package com.mbj.servicemap.remote;

import com.mbj.internalcommmon.constant.AmapConstant;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-26 21:09
 * @Description:
 * @Version:
 */
@Service
@Slf4j
public class MapDirectionClient {

    @Value("${amap.key}")
    private String key;

    @Autowired
    private RestTemplate restTemplate;

    public DirectionResponse direction(String depLongitude, String depLatitude, String destLongitude, String destLatitude){
        // 组装调用url
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(AmapConstant.DIRECTION_URL);
        urlBuilder.append("?");
        urlBuilder.append("origin=").append(depLongitude).append(",").append(depLatitude);
        urlBuilder.append("&destination=").append(destLongitude).append(",").append(destLatitude);
        urlBuilder.append("&output=json");
        urlBuilder.append("&key=").append(key);
        // 调用高德接口
        ResponseEntity<String> directionEntity = restTemplate.getForEntity(urlBuilder.toString(), String.class);
        String directionString = directionEntity.getBody();
        log.info("高德地图路径规划，返回信息：" + directionString);
        // 解析接口并返回
        return parseDirectionEntity(directionString);
    }

    private DirectionResponse parseDirectionEntity(String directionString){
        DirectionResponse directionResponse = null;
        try{
            JSONObject result = JSONObject.fromObject(directionString);
            if (result.has(AmapConstant.STATUS)){
                int status = result.getInt(AmapConstant.STATUS);
                if (status == 1){
                    if (result.has("route")){
                        JSONObject routeObject = result.getJSONObject(AmapConstant.ROUTE);
                        JSONArray pathsArray = routeObject.getJSONArray(AmapConstant.PATHS);
                        JSONObject pathObject = pathsArray.getJSONObject(0);
                        directionResponse = new DirectionResponse();
                        if (pathObject.has(AmapConstant.DISTANCE)){
                            int distance = pathObject.getInt(AmapConstant.DISTANCE);
                            directionResponse.setDistance(distance);
                        }
                        if (pathObject.has(AmapConstant.DURATION)){
                            int duration = pathObject.getInt(AmapConstant.DURATION);
                            directionResponse.setDuration(duration);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return directionResponse;
    }

}
