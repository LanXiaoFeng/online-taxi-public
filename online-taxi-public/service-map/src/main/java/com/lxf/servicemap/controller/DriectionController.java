package com.lxf.servicemap.controller;

import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.internalcommon.request.ForecastPriceDTO;
import com.lxf.servicemap.service.DirectionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/direction")
public class DriectionController {

    @Resource
    private DirectionService directionService;

    @PostMapping("/driving")
    public ResponseResult driving(@RequestBody ForecastPriceDTO forecastPriceDTO){
        return directionService.driving(forecastPriceDTO);
    }
}
