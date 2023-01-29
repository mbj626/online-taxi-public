package com.mbj.servicemap.remote;

import com.mbj.internalcommmon.constant.AmapConstant;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.request.PointDTO;
import com.mbj.internalcommmon.request.PointRequest;
import com.mbj.internalcommmon.response.TrackResponse;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-29 13:51
 * @Description:
 * @Version:
 */
@Service
public class PointClient {

    @Value("${amap.key}")
    private String key;

    @Value("${amap.sid}")
    private String sid;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult<TrackResponse> upload(PointRequest pointRequest){
        // 组装调用url
        StringBuilder urlBuilder = new StringBuilder(AmapConstant.POINT_UPLOAD +
                "?" +
                "key=" + key +
                "&sid=" + sid +
                "&tid=" + pointRequest.getTid() +
                "&trid=" + pointRequest.getTrid() +
                "&points=");
        urlBuilder.append("%5B");
        PointDTO[] points = pointRequest.getPoints();
        for (PointDTO point : points) {
            urlBuilder.append("%7B");
            urlBuilder.append("%22location%22%3A");
            urlBuilder.append("%22").append(point.getLocation()).append("%22").append("%2C");
            urlBuilder.append("%22locatetime%22%3A");
            urlBuilder.append("%22").append(point.getLocatetime()).append("%22");
            urlBuilder.append("%7D");
        }
        urlBuilder.append("%5D");

        System.out.println("上传位置请求："+urlBuilder);
        ResponseEntity<String> forEntity = restTemplate.postForEntity(URI.create(urlBuilder.toString()), null,String.class);
        String body = forEntity.getBody();
        System.out.println("上传位置响应："+body);
        return ResponseResult.success();
    }

}
