package com.lxf.serviceprice.service.impl;

import com.lxf.internalcommon.constant.CommonStatusEnum;
import com.lxf.internalcommon.dto.PriceRule;
import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.internalcommon.request.ForecastPriceDTO;
import com.lxf.internalcommon.response.DirectionResponse;
import com.lxf.internalcommon.response.ForecastPriceResponse;
import com.lxf.internalcommon.util.BigDecimalUtils;
import com.lxf.serviceprice.mapper.PriceRuleMapper;
import com.lxf.serviceprice.remote.ServiceMapClient;
import com.lxf.serviceprice.service.ForecastPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ForecastPriceServiceImpl implements ForecastPriceService {

    @Resource
    private ServiceMapClient serviceMapClient;
    @Resource
    private PriceRuleMapper priceRuleMapper;

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
        ResponseResult<DirectionResponse> direction = serviceMapClient.direction(forecastPriceDTO);
        Integer distance = direction.getData().getDistance();
        Integer duration = direction.getData().getDuration();
        log.info("距离："+distance+",时长："+duration);

        log.info("读取计价规则");
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("city_code","110000");
        queryMap.put("vehicle_type","1");
        List<PriceRule> priceRules = priceRuleMapper.selectByMap(queryMap);
        if (ObjectUtils.isEmpty(priceRules)){
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(),CommonStatusEnum.FAIL.getValue());
        }
        PriceRule priceRule = priceRules.get(0);

        log.info("根据距离、时长和计价规则，计算价格");
        double price = getPrice(distance, duration, priceRule);
        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(price);
        return ResponseResult.success(forecastPriceResponse);
    }

    /**
     * 根据距离、时长 和计价规则，计算最终价格
     * @param distance  距离
     * @param duration  时长
     * @param priceRule 计价规则
     * @return
     */
    private static double getPrice(Integer distance , Integer duration,PriceRule priceRule){
        double price = 0;

        // 起步价
        double startFare = priceRule.getStartFare();
        price = BigDecimalUtils.add(price,startFare);

        // 里程费
        // 总里程 m
        double distanceMile = BigDecimalUtils.divide(distance,1000);
        // 起步里程
        double startMile = (double)priceRule.getStartMile();
        double distanceSubtract = BigDecimalUtils.substract(distanceMile,startMile);
        // 最终收费的里程数 km
        double mile = distanceSubtract<0?0:distanceSubtract;
        // 计程单价 元/km
        double unitPricePerMile = priceRule.getUnitPricePerMile();
        // 里程价格
        double mileFare = BigDecimalUtils.multiply(mile,unitPricePerMile);
        price = BigDecimalUtils.add(price,mileFare);

        // 时长费
        // 时长的分钟数
        double timeMinute = BigDecimalUtils.divide(duration,60);
        // 计时单价
        double unitPricePerMinute = priceRule.getUnitPricePerMinute();

        // 时长费用
        double timeFare = BigDecimalUtils.multiply(timeMinute,unitPricePerMinute);
        price = BigDecimalUtils.add(price,timeFare);

        BigDecimal priceBigDecimal = new BigDecimal(price);
        priceBigDecimal = priceBigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);

        return priceBigDecimal.doubleValue();
    }

    public static void main(String[] args) {
        PriceRule priceRule = new PriceRule();
        priceRule.setUnitPricePerMile(1.8);
        priceRule.setUnitPricePerMinute(0.5);
        priceRule.setStartFare(10.0);
        priceRule.setStartMile(3);

        System.out.println(getPrice(6500,1800,priceRule));
    }
}
