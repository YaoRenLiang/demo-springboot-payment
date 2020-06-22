package com.free.yrl.demospringbootpayment.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.free.yrl.demospringbootpayment.base.ResponseMessage;
import com.free.yrl.demospringbootpayment.entity.DetailEntity;
import com.free.yrl.demospringbootpayment.service.PaymentService;
import com.free.yrl.demospringbootpayment.util.UrlUtils;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * 付款相关接口
 *
 * @author 姚壬亮
 **/
@Slf4j
@Api(tags = "付款相关接口")
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Resource
    private PaymentService paymentServiceImpl;

    @ApiOperation("支付")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "platform", value = "付款类型：1.PayPal、2.Alipay、3.WeChat", dataType = "int", required = false),
    })
    @PostMapping(value = "pay")
    public Mono<ResponseMessage<String>> pay(HttpServletRequest request,
                                             @RequestBody List<String> myOrderIdList,
                                             @RequestParam(required = false) Integer platform,
                                             @RequestParam(required = false) String currencyCode) {

        String baseUrl = UrlUtils.getBaseUrl(request);
        return Mono.justOrEmpty(paymentServiceImpl.getPayInfo(myOrderIdList,
                platform,
                currencyCode,
                baseUrl));

    }

}
