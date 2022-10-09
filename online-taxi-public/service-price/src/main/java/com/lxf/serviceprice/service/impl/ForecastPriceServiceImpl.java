package com.lxf.serviceprice.service.impl;

import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.internalcommon.request.ForecastPriceDTO;
import com.lxf.internalcommon.response.ForecastPriceResponse;
import com.lxf.serviceprice.service.ForecastPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForecastPriceServiceImpl implements ForecastPriceService {

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

        log.info("调用地图服务，查询距离和时长");

        log.info("读取计价规则");

        log.info("根据距离、时长和计价规则，计算价格");

        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(12.23);
        return ResponseResult.success(forecastPriceResponse);
    }
}
