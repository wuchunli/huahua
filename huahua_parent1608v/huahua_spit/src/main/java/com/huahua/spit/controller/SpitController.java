package com.huahua.spit.controller;

import com.huahua.spit.entity.Spit;
import com.huahua.spit.service.SpitService;
import huahua.common.PageResult;
import huahua.common.Result;
import huahua.common.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/spit")
@CrossOrigin
public class SpitController {

    @Autowired
    private SpitService spitService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }
    @RequestMapping(method = RequestMethod.GET,value = "/{id}")
    public Result findById(@PathVariable String id){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findById(id));
    }

    /**
     * 新增
     * @param spit
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        spit.setPublishtime(new Date());
        spitService.add(spit);
        return new Result(true, StatusCode.OK,"新增成功");
    }

    /**
     * 修改
     * @param spit
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/{id}")
    public Result update(@PathVariable Spit spit,@PathVariable String id){
        spit.set_id(id);
        spitService.update(spit);
        return new Result(true, StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id){
        spitService.delete(id);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    /**
     * 根据上级id查询吐槽数据(分页)
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/comment/{parentid}/{page}/{size}")
    public Result findByPidList(@PathVariable("parentid") String parentid,
                                @PathVariable("page") Integer page,
                                @PathVariable("size") Integer size){
    Page<Spit> spitList = spitService.findByPidList(parentid,page,size);
    return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(spitList.getTotalElements(),spitList.getContent()));
    }
    /**
     * 吐槽点赞
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/thumbup/{spitId}")
    public Result updateThumbup(@PathVariable("spitId") String spitId){
        //判断当前的用户是否已经点赞了,由于没有搞登录认证,先把userid写死
        String userid = "111";
        if(redisTemplate.opsForValue().get("thumbup_"+userid+spitId) != null){
            return new Result(false,StatusCode.ERROR,"不能重复点赞");
        }
        //调用service层,进行点赞
        spitService.updateThumbup(spitId);
        //如果用户点赞成功,就把点赞的信息,存入redis中
        redisTemplate.opsForValue().set("thumbup_"+userid+spitId,1);
        return new Result(true,StatusCode.OK,"点赞成功");

    }
}
