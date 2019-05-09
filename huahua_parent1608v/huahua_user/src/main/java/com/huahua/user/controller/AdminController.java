package com.huahua.user.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import huahua.common.PageResult;
import huahua.common.Result;
import huahua.common.StatusCode;
import huahua.common.utils.JwtUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.huahua.user.pojo.Admin;
import com.huahua.user.service.AdminService;


/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private JwtUtil jwtUtil;
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true, StatusCode.OK,"查询成功",adminService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",adminService.findById(id));
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
		Page<Admin> pageList = adminService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Admin>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",adminService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param admin
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Admin admin  ){
		adminService.add(admin);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param admin
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Admin admin, @PathVariable String id ){
		admin.setId(id);
		adminService.update(admin);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		adminService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 管理员登录
	 * @param admin
	 * @return
	 */
	@PostMapping("/login")
	public Result login(@RequestBody Admin admin){
		if(null ==admin || StringUtils.isEmpty(admin.getPassword())){
			return new Result(false,StatusCode.ERROR,"参数有误");
		}
		if(null ==admin || StringUtils.isEmpty(admin.getLoginname())){
			return new Result(false,StatusCode.ERROR,"参数有误");
		}
		Admin byLoginName = adminService.findByLoginName(admin);
		if(null != byLoginName){
			//生成token  处理根用户一系列相关的信息 如:权限查询 用户一些相关的业务
			String token = jwtUtil.createJWT(byLoginName.getId(), byLoginName.getLoginname(), "admin");
			//jwt前端与后端访问的唯一标识-->都要效验token否则都会让操作的用户返回登录
			//把token数据返回给前端(身份唯一标识)
			HashMap<Object, Object> map = new HashMap<>();
			map.put("token",token);
			map.put("username",byLoginName.getLoginname());
			return new Result(true,StatusCode.OK,"登录成功",map);
		}else {
			return new Result(false,StatusCode.LOGINGERROR,"登录失败");
		}

	}

}
