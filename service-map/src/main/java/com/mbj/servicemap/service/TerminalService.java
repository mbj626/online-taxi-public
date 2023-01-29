package com.mbj.servicemap.service;

import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.response.TerminalResponse;
import com.mbj.servicemap.remote.TerminalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-29 14:07
 * @Description:
 * @Version:
 */
@Service
public class TerminalService {

    @Autowired
    TerminalClient terminalClient;

    public ResponseResult<TerminalResponse> add(String name,String desc){
        return terminalClient.add(name,desc);
    }

    public ResponseResult<List<TerminalResponse>> aroundsearch(String center, Integer radius) {
        return terminalClient.aroundsearch(center,radius);
    }
}
