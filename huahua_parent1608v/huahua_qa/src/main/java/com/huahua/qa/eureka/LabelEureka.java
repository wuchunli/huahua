package com.huahua.qa.eureka;

import huahua.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//调用的是哪一个微服务
@FeignClient("huahua-base")
public interface LabelEureka {
    //value要写全部路径
    @RequestMapping(method = RequestMethod.GET,value = "/lable/{lableId}")
    public Result queryById(@PathVariable("lableId") String lableId);


}
