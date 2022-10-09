package com.lxf.servicemap.service.impl;

import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.internalcommon.request.ForecastPriceDTO;
import com.lxf.internalcommon.response.DirectionResponse;
import com.lxf.servicemap.remote.MapDirectionClient;
import com.lxf.servicemap.service.DirectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("directionService")
public class DirectionServiceImpl implements DirectionService {

    @Resource
    private MapDirectionClient mapDirectionClient;

    /**
     * 根据经纬度计算距离（米）和时长（分钟）
     * @param forecastPriceDTO
     * @return
     */
    @Override
    public ResponseResult driving(ForecastPriceDTO forecastPriceDTO) {
        // 调用地图接口
        DirectionResponse direction = mapDirectionClient.direction(forecastPriceDTO);

        return ResponseResult.success(direction);
    }
}
