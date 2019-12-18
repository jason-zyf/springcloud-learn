package com.pci.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(value="service-hi",fallback = SchedualServiceHiHystric.class)         // 指定调用哪个服务,调用失败启用熔断器
@FeignClient(value="service-hi")
public interface SchedualServiceHi {

    @RequestMapping(value="/hi",method = RequestMethod.GET)
    String sayHiFromFeignOne(@RequestParam(value="name") String name);

}
