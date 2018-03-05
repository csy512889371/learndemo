package com.ctoedu.service.web;

import com.ctoedu.service.Domain.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping(value="/users")
public class UserController {
      
	   static Map<Integer, User> Users = Collections.synchronizedMap(new HashMap<Integer, User>()); 
	   
	  
	    @ApiOperation(value="获取用户列表")
	    @RequestMapping(value="/",method=RequestMethod.GET)
	    public List<User> getUserList(){
	    	List<User> User=new ArrayList<User>(Users.values());
	    	return User;
	    }
	    
	    
		 @ApiOperation(value="创建用户")
		 @ApiImplicitParam(name = "user", value = "用户实体", required = true, dataType = "User")
	    @RequestMapping(value="/",method=RequestMethod.POST)
	    public String addUsers(@ModelAttribute User User){
	    	Users.put(User.getId(), User);
	    	return Users.size()+"";
	    }
	  
		 @ApiOperation(value="根据id获取用户信息")
		 @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer",paramType="path")
	    @RequestMapping(value="/{id}",method=RequestMethod.GET)
	    public User getUser(@PathVariable Integer id){
	    	return Users.get(id);
	    }
	   
		 @ApiOperation(value="根据id更新用户信息")
		  @ApiImplicitParams({
		   @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer",paramType="path"),
		   @ApiImplicitParam(name = "user", value = "用户实体", required = true, dataType = "User")
		  })
	    @RequestMapping(value="/{id}", method=RequestMethod.PUT) 
	    public String updateUser(@PathVariable Integer id, @ModelAttribute User s) { 
	    	User User = Users.get(id); 
	    	User.setName(s.getName()); 
	    	User.setAge(s.getAge()); 
	    	Users.put(id, User); 
	        return "success"; 
	    } 
	  
		 @ApiOperation(value="根据id删除用户信息")
		 @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer",paramType="path")
	    @RequestMapping(value="/{id}", method=RequestMethod.DELETE) 
	    public String deleteUser(@PathVariable Integer id) { 
	        Users.remove(id); 
	        return "success"; 
	    } 
}
