package com.huahua.article.controller;

import com.huahua.article.pojo.CommentMongoDB;
import com.huahua.article.service.CommentMongoDBService;
import huahua.common.Result;
import huahua.common.StatusCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentMongoController {
    @Autowired
    private CommentMongoDBService commentMongoDBService;
    public Result save(@RequestBody CommentMongoDB commentMongoDB){
        if(StringUtils.isEmpty(commentMongoDB.getArticleid())){
            return new Result(true, StatusCode.OK,"参数有误!");
        }
        commentMongoDBService.add(commentMongoDB);
        return new Result(true,StatusCode.OK,"添加成功");
    }
    @RequestMapping(method = RequestMethod.POST,value = "/article/{articleId}")
    public Result queryByArticleId(@PathVariable("articleId") String articleId){
        List<CommentMongoDB> list =  commentMongoDBService.queryByArticleId(articleId);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }
    /**
     * 删除评论
     */
    @RequestMapping(method = RequestMethod.DELETE,value = "/{ids}")
    public Result delete(@PathVariable String ids){
        commentMongoDBService.delete(ids);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
