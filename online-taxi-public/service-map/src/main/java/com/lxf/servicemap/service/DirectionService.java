package com.lxf.servicemap.service;

import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.internalcommon.request.ForecastPriceDTO;

public interface DirectionService {
    ResponseResult driving(ForecastPriceDTO forecastPriceDTO);
}
