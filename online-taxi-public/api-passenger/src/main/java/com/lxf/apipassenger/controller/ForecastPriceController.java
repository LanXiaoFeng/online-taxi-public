package com.lxf.apipassenger.controller;

import com.lxf.apipassenger.service.ForecastPriceService;
import com.lxf.apipassenger.service.impl.ForecastPriceServiceImpl;
import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.internalcommon.request.ForecastPriceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class ForecastPriceController {

    @Resource
    private ForecastPriceService forecastPriceService;

    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO){
        return forecastPriceService.forecastPrice(forecastPriceDTO);
    }
}
