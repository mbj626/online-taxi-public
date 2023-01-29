package com.mbj.servicemap.remote;

import com.mbj.internalcommmon.constant.AmapConstant;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.response.TerminalResponse;
import com.mbj.internalcommmon.response.TrackResponse;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
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
public class TrackClient {

    @Value("${amap.key}")
    private String key;

    @Value("${amap.sid}")
    private String sid;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult<TrackResponse> add(String tid){
        // 组装调用url
        String urlBuilder = AmapConstant.TRACK_ADD +
                "?" +
                "key=" + key +
                "&sid=" + sid +
                "&tid=" + tid;

        ResponseEntity<String> forEntity = restTemplate.postForEntity(urlBuilder, null,String.class);
        String body = forEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        String trid = data.getString("trid");
        String trname = "";
        if (data.has("trname")){
            trname = StringUtils.isBlank(data.getString("trname"))?"":data.getString("trname");
        }
        TrackResponse trackResponse =new TrackResponse();
        trackResponse.setTrid(trid);
        trackResponse.setTrname(trname);
        return ResponseResult.success(trackResponse);
    }

}
