package com.huahua.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebFilter extends ZuulFilter {


    /**
     * 过滤的类型
     * pre   前置过滤
     * post  后置过滤
     * @return
     */
    @Override
    public String filterType() {

        return "pre"; //前置过滤器
    }

    /**
     * 过滤器的优先级
     * // 优先级为0，数字越大，优先级越低
     * @return
     */
    @Override
    public int filterOrder() {

        return 0; //优先级为0,数字越大.优先级越低
    }


    /**
     * // 是否执行该过滤器，此处为true，说明需要过滤
     * @return
     */
    @Override
    public boolean shouldFilter() {

        return true;// 是否执行该过滤器，此处为true，说明需要过滤
    }


    /**
     * 过滤器内执行的操作，return 任何object的值 都表示继续执行
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {

        System.out.println("zuul过滤器................");

        //获取头信息通过 requestContext继续传下去--解决了通过网关头信息丢失的问题
        RequestContext requestContext =  RequestContext.getCurrentContext();
        HttpServletRequest request =  requestContext.getRequest();
        String headerVale =  request.getHeader("Authorization");

        //在requestContext继续传下去
        if(null != headerVale){
            requestContext.addZuulRequestHeader("Authorization",headerVale);
        }
        return null;
    }


}
