package com.lxf.apipassenger.service.impl;

import com.lxf.apipassenger.remote.ForecastPriceClient;
import com.lxf.apipassenger.service.ForecastPriceService;
import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.internalcommon.request.ForecastPriceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class ForecastPriceServiceImpl implements ForecastPriceService {

    @Resource
    private ForecastPriceClient forecastPriceClient;

    @Override
    public ResponseResult forecastPrice(ForecastPriceDTO forecastPriceDTO) {
        String depLongitude = forecastPriceDTO.getDepLongitude();
        String depLatitude = forecastPriceDTO.getDepLatitude();
        String destLongitude = forecastPriceDTO.getDestLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();
        log.info("出发地经度："+depLongitude);
        log.info("出发地维度："+depLatitude);
        log.info("目的地经度："+destLongitude);
        log.info("目的地维度："+destLatitude);

        log.info("调用计价服务，计算价格");
        return forecastPriceClient.forecast(forecastPriceDTO);
    }
}
