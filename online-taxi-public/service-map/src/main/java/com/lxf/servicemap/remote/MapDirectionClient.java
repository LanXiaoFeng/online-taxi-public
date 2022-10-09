package com.lxf.servicemap.remote;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lxf.internalcommon.constant.AmapConfigConstants;
import com.lxf.internalcommon.request.ForecastPriceDTO;
import com.lxf.internalcommon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
@Slf4j
public class MapDirectionClient {

    @Value("${amap.key}")
    private String amapKey;

    @Resource
    private RestTemplate restTemplate;

    public DirectionResponse direction(ForecastPriceDTO forecastPriceDTO){
        // 组装请求调用url
        // extensions=all&output=xml&key=c4b8615a541f446a0a8b8f8166724247
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(AmapConfigConstants.DIRECTION_URL);
        stringBuilder.append("?");
        stringBuilder.append("origin="+forecastPriceDTO.getDepLongitude()+","+forecastPriceDTO.getDepLatitude());
        stringBuilder.append("&");
        stringBuilder.append("destination="+forecastPriceDTO.getDestLongitude()+","+forecastPriceDTO.getDestLatitude());
        stringBuilder.append("&");
        stringBuilder.append("extensions=base");
        stringBuilder.append("&");
        stringBuilder.append("output=json");
        stringBuilder.append("&");
        stringBuilder.append("key="+amapKey);
        log.info(stringBuilder.toString());

        // 调用高德接口
        ResponseEntity<String> forEntity = restTemplate.getForEntity(stringBuilder.toString(), String.class);
        log.info("高德地图：路径规划，返回信息："+forEntity.getBody());
        // 解析接口
        DirectionResponse directionResponse = parseDirectionEntity(forEntity.getBody());

        return directionResponse;
    }

    private DirectionResponse parseDirectionEntity(String directinString){
        DirectionResponse directionResponse = null;
        try {

            JSONObject result = JSONObject.parseObject(directinString, JSONObject.class);
            if (result.containsKey(AmapConfigConstants.STATUS)){
                Integer status = result.getInteger(AmapConfigConstants.STATUS);
                if (status == 1) {
                    if (result.containsKey(AmapConfigConstants.ROUTE)){
                        JSONObject routeObject = result.getJSONObject(AmapConfigConstants.ROUTE);
                        JSONArray pathArray = routeObject.getJSONArray(AmapConfigConstants.PATHS);
                        JSONObject pathObject = pathArray.getJSONObject(0);
                        directionResponse = new DirectionResponse();
                        if (pathObject.containsKey(AmapConfigConstants.DISTANCE)){
                            Integer distance = pathObject.getInteger(AmapConfigConstants.DISTANCE);
                            directionResponse.setDistance(distance);
                        }
                        if (pathObject.containsKey(AmapConfigConstants.DURATION)){
                            Integer duration = pathObject.getInteger(AmapConfigConstants.DURATION);
                            directionResponse.setDuration(duration);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return directionResponse;
    }
}
