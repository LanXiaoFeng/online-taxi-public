package com.lxf.apipassenger.service;

import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.internalcommon.request.ForecastPriceDTO;

public interface ForecastPriceService {
    ResponseResult forecastPrice(ForecastPriceDTO forecastPriceDTO);
}
