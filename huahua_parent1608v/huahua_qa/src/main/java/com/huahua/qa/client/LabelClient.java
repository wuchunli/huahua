package com.huahua.qa.client;


import com.huahua.qa.client.impl.LabelClientImpl;
import huahua.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 *  @FeignClient注解用于指定从哪个服务中调用功能 ，
 *  注意 里面的名称与被调用的服务名保持一致，并且不能包含下划线。
 * @RequestMapping注解用于对被调用的微服务进行地址映射。
 *  注意 @PathVariable注解一定要指定参数名称，否则出错
 */

@FeignClient(value = "huahua-base",fallback = LabelClientImpl.class)
public interface LabelClient {

    /**
     *   假设你要调用 Base下 LabelController 的 findById()方法
     *
     *   1 你需要把方法的注解和方法的声明拷贝过来
     *   @RequestMapping( value = "/{id}", method = RequestMethod.GET )
     *   public Result findById(@PathVariable String id) {
     *        return new Result(true, StatusCode.OK, "查询成功", labelService.findById(id));
     *   }
     *   2 LabelContoller 类的虚拟路径也拿过来 才能找到  /label/{id}
     */
    @RequestMapping(value="/label/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id);


}
