package com.huahua.friend.controller;

import com.huahua.friend.service.FriendSerevice;
import huahua.common.Result;
import huahua.common.StatusCode;
import io.jsonwebtoken.Claims;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.ipc.netty.http.server.HttpServerRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
@CrossOrigin
public class FriendController {
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private FriendSerevice friendSerevice;
    @RequestMapping(value = "/like/{friendid}/{type}",method = RequestMethod.PUT)
    public Result addfriend(@PathVariable("friendid")String friendid,@PathVariable("type")String type){
        //判断值是否存在
        Claims claims = (Claims) httpServletRequest.getAttribute("user_claims");
        if(null == claims){
            return new Result(false, StatusCode.AUTOROLES,"无权访问");
        }
        //判断type类型是1 喜欢还是0 不喜欢
        //如果喜欢
        if(StringUtils.equals("1",type)){
            //a喜欢b   已存在状态
            if(friendSerevice.addFriend(claims.getId(),friendid)==0){
                return new Result(false, StatusCode.AUTOROLES,"已关注不能再关注了");
            }
        }else{
            //不喜欢
            friendSerevice.nolikeFriend(claims.getId(),friendid);
        }
        //返回状态
        return new Result(false, StatusCode.OK,"操作成功");
    }
    /**
     * 不喜欢
     *
     */
    @DeleteMapping("/{friendid}")
    public Result delete(@PathVariable("friendid") String friendid){
        //判断值是否存在
        Claims claims = (Claims) httpServletRequest.getAttribute("user_claims");
        if(null == claims){
            return new Result(false, StatusCode.AUTOROLES,"无权访问");
        }
        friendSerevice.delete(claims.getId(),friendid);
        return new Result(false, StatusCode.OK,"操作成功");
    }
}
