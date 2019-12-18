package com.pci.controller;

import com.pci.service.SchedualServiceHi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @Autowired
    SchedualServiceHi schedualServiceHi;

    @GetMapping("/hi")
    public String sayHi(@RequestParam String name){
        return schedualServiceHi.sayHiFromFeignOne(name);
    }

}
