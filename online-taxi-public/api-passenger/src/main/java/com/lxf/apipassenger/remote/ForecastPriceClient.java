package com.lxf.apipassenger.remote;

import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.internalcommon.request.ForecastPriceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-price")
public interface ForecastPriceClient {

    @RequestMapping(method = RequestMethod.POST,value = "/forecast-price")
    ResponseResult forecast(@RequestBody ForecastPriceDTO forecastPriceDTO);
}
