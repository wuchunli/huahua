package com.huahua.qa.client.impl;

import com.huahua.qa.client.LabelClient;


import huahua.common.Result;
import huahua.common.StatusCode;
import org.springframework.stereotype.Component;

@Component
public class LabelClientImpl implements LabelClient {


    @Override
    public Result findById(String id) {
        //为了往这个容器里调用,就随便写一个 Result，以后可以在里边记录日志
        return new Result(false, StatusCode.ERROR,"熔断器启动了");
    }

}
