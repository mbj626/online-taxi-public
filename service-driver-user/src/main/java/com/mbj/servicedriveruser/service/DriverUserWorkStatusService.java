package com.mbj.servicedriveruser.service;

import com.mbj.internalcommmon.dto.DriverUserWorkStatus;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mbj
 * @since 2023-01-28
 */
@Service
public class DriverUserWorkStatusService {

    @Autowired
    DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    public ResponseResult changeWorkStatus(Long driverId,Integer workStatus){
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("driver_id",driverId);
        List<DriverUserWorkStatus> driverUserWorkStatuses = driverUserWorkStatusMapper.selectByMap(queryMap);
        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatuses.get(0);

        driverUserWorkStatus.setWorkStatus(workStatus);

        driverUserWorkStatusMapper.updateById(driverUserWorkStatus);

        return ResponseResult.success("");
    }

}
