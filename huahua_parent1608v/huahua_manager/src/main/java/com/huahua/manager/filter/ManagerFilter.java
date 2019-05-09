package com.huahua.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import huahua.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {


    @Autowired
    private JwtUtil jwtUtil;


    /**
     * 在请求前或千秋后制定过滤
     * @return
     */
    @Override
    public String filterType() {
        //return "post"  //后置过滤器
        return "pre";// 前置过滤器
    }

    /**
     * 多个过滤器的执行顺序.数字越小越先执行
     * @return
     */
    @Override
    public int filterOrder() {

        return 0;    // 可以有多个过滤器 ,优先级为0，数字越大，优先级越低
    }

    /**
     * 当前过滤器是否开启
     * @return
     */
    @Override
    public boolean shouldFilter() {

        return true;// 是否执行该过滤器，此处为true，说明需要过滤
    }

    /**
     * 表示过滤器内执行的过滤操作
     * return 任何Object的值都表示放行,可以继续执行其他过滤器或下一步操作
     * setSendZuulResponse(false) 表示不再继续执行
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("Zuul过滤器..............");

        RequestContext requestContext=RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        if(request.getMethod().equals("OPTIONS")){
            return null;
        }

        String url=request.getRequestURL().toString();
        if(url.indexOf("/admin/login")>0){
            System.out.println("登陆页面"+url);
            return null;
        }

        String authHeader =(String)request.getHeader("Authorization");//获取头信息
        if(authHeader!=null && authHeader.startsWith("Bearer ") ){

            String token = authHeader.substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            try {
                if(claims!=null){
                    if("admin".equals(claims.get("roles"))){

                        requestContext.addZuulRequestHeader("Authorization",authHeader);
                        System.out.println("token 验证通过，添加了头信息"+authHeader);
                        return null;
                    }
                }
            }catch (Exception e){
               e.printStackTrace();
               requestContext.setSendZuulResponse(false); //终止运行
            }
        }
        requestContext.setSendZuulResponse(false);//终止运行
        requestContext.setResponseStatusCode(401); //http状态码 权限不足
        requestContext.setResponseBody("无权访问");
        requestContext.getResponse().setContentType("text/html;charset=UTF‐8");
        return null;

    }

}
