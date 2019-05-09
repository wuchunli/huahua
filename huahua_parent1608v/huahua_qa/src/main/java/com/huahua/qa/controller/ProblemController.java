package com.huahua.qa.controller;
import java.util.List;
import java.util.Map;


import com.huahua.qa.eureka.LabelEureka;
import com.huahua.qa.pojo.Reply;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.huahua.qa.pojo.Problem;
import com.huahua.qa.service.ProblemService;

import huahua.common.PageResult;
import huahua.common.StatusCode;
import huahua.common.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;
	@Autowired
	HttpServletRequest request;
	@Autowired
	private LabelEureka labelEureka;
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",problemService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param problem
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Problem problem  ){
		Claims claims = (Claims) request.getAttribute("admin_claims");
		//只有一个!!!
		if(null == claims){
			return new Result(false,StatusCode.AUTOROLES,"无权访问");
		}
		problem.setId(claims.getId());
		problemService.add(problem);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		Claims claims = (Claims) request.getAttribute("admin_claims");
		//只有一个!!!
		if(null == claims){
			return new Result(false,StatusCode.AUTOROLES,"无权访问");
		}
		problemService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 最新问答
	 * @param labelid
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/newlist/{labelid}/{page}/{size}")
	public Result newlist(@PathVariable String labelid,
						  @PathVariable Integer page,
						  @PathVariable Integer size){
		Page<Problem> problemPage = problemService.newlist(labelid, page, size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(problemPage.getTotalElements(),problemPage.getContent()));
	}

	/**
	 * 热门问答
	 * @param labelid
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/hotlist/{labelid}/{page}/{size}")
	public Result hotlist(@PathVariable String labelid,
						  @PathVariable Integer page,
						  @PathVariable Integer size){
		Page<Problem> problemPage = problemService.hotlist(labelid, page, size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(problemPage.getTotalElements(),problemPage.getContent()));
	}
	/**
	 * 等待问答
	 * @param labelid
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/waitlist/{labelid}/{page}/{size}")
	public Result waitlist(@PathVariable String labelid,
						  @PathVariable Integer page,
						  @PathVariable Integer size){
		Page<Problem> problemPage = problemService.waitlist(labelid, page, size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(problemPage.getTotalElements(),problemPage.getContent()));
	}

	/**
	 * 调用huahua-base
	 * @param lableId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/lable/{lableId}")
	public Result queryById(@PathVariable("lableId") String lableId){
		return labelEureka.queryById(lableId);
	}


}
