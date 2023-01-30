package com.mbj.serviceorder.remote;

import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.response.TerminalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-30 16:37
 * @Description:
 * @Version:
 */
@FeignClient("service-map")
public interface ServiceMapClient {

    // terminal/aroundsearch
    @RequestMapping(method = RequestMethod.POST,value = "/terminal/aroundsearch")
    public ResponseResult<List<TerminalResponse>> terminalAroundSearch(@RequestParam String center, @RequestParam Integer radius);

}
