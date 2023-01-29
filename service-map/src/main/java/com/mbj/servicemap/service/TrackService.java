package com.mbj.servicemap.service;

import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.response.TerminalResponse;
import com.mbj.internalcommmon.response.TrackResponse;
import com.mbj.servicemap.remote.TerminalClient;
import com.mbj.servicemap.remote.TrackClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-29 14:07
 * @Description:
 * @Version:
 */
@Service
public class TrackService {

    @Autowired
    TrackClient trackClient;

    public ResponseResult<TrackResponse> add(String tid){
        return trackClient.add(tid);
    }

}
