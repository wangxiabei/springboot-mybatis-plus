package com.spring.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "${feign.mall:item-mall}")
public interface FeignClientDemo {
    @GetMapping("/mall/sendMessage/1")
    String getSomething();
}
