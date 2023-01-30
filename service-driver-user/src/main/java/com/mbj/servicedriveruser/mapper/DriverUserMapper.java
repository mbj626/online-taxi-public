package com.mbj.servicedriveruser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mbj.internalcommmon.dto.DriverUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-28 16:34
 * @Description:
 * @Version:
 */
@Repository
public interface DriverUserMapper extends BaseMapper<DriverUser> {

    public int selectDriverUserCountByCityCode(@Param("cityCode") String cityCode);

}
