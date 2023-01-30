package com.mbj.serviceorder.remote;

import com.mbj.internalcommmon.dto.PriceRule;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.request.PriceRuleIsNewRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-29 22:57
 * @Description:
 * @Version:
 */
@FeignClient("service-price")
public interface ServicePriceClient {

    @RequestMapping(method = RequestMethod.POST,value = "/price-rule/is-new")
    public ResponseResult<Boolean> isNew(@RequestBody PriceRuleIsNewRequest priceRuleIsNewRequest);

    @RequestMapping(method = RequestMethod.GET,value = "/price-rule/is-exists")
    public ResponseResult<Boolean> isExists(@RequestBody PriceRule priceRule);
}
