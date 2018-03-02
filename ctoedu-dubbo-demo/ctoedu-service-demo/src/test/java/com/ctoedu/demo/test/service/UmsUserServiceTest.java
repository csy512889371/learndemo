package com.ctoedu.demo.test.service;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.common.utils.security.Md5Utils;
import com.ctoedu.demo.core.user.service.UmsPersonService;
import com.ctoedu.demo.core.user.service.UmsUserService;
import com.ctoedu.demo.facade.user.entity.UmsPerson;
import com.ctoedu.demo.facade.user.entity.UmsUser;
import com.ctoedu.demo.test.base.BaseTest;

/**
 * User:cxtww
 * Date:2016年11月23日 下午9:07:45
 * Version:1.0
 */
public class UmsUserServiceTest extends BaseTest{

	@Autowired
	private UmsUserService umsUserService;
	@Autowired
	private UmsPersonService umsPersonService;
	
	@Test
	public void findAll(){
		String nickname = null;
		String username = null;
		int number =2;
		int size = 2;
		Pageable page = new PageRequest(number, size);
		
		Sort sort = new Sort(Direction.DESC, "createDate");
		
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("nickname_like", nickname);
		searchable.addSearchParam("username_like", username);
		
		searchable.setPage(page);
		searchable.addSort(sort);
		
		Page<UmsUser> users = umsUserService.findAll(searchable);
		System.out.println(users.getContent());
		for(UmsUser user : users.getContent()){
			System.out.println(user.getNickname()+ ":" + user.getUsername());
		}
	}
	
//	@Test
	public void addUser(){
		UmsUser user = new UmsUser();
		UmsPerson person = new UmsPerson();
		user.setUsername("zhangsan1");
		user.setNickname("张三11");
		user.setPassword("123456");
		user.generateSalt();
        user.setPassword(encryptPassword(user.getUsername(), user.getPassword(), user.getSalt()));
        user.setCreateDate(new Date());
//        user.setPerson(person);
//        umsUserService.save(user);
        person.setUser(user);
		umsPersonService.savePerson(person);
	}
	
//	@Test
	public void login(){
		UmsUser umsUser = umsUserService.login("爱死费崇政123456", "fay123456");
//		UmsUser umsUser = umsUserService.login("fay123456", "fay123456");
		System.out.println(umsUser.getUsername());
	}
	
//	@Test
	public void findUserByUsername(){
//		UmsUser user = new UmsUser();
//		UmsPerson person = new UmsPerson();
//		user.setUsername("zhangsan");
//		user.setNickname("张三");
//		user.setPassword("123456");
//		user.generateSalt();
//        user.setPassword(encryptPassword(user.getUsername(), user.getPassword(), user.getSalt()));
//        user.setCreateDate(new Date());
//        person.setUser(user);
//		umsPersonService.savePerson(person);
		UmsPerson person = umsPersonService.findOne("402882945949a13d015949a151330000");
		System.out.println(person.getUser().getUsername());
	}
	
	/*@Test(expected = UserUsernameExistsException.class)
	public void addUserException(){
		UmsUser user = new UmsUser();
		UmsPerson person = new UmsPerson();
		user.setUsername("lisi");
		user.setNickname("李四");
		user.setPassword("123456");
		user.generateSalt();
        user.setPassword(encryptPassword(user.getUsername(), user.getPassword(), user.getSalt()));
        user.setCreateDate(new Date());
        person.setUser(user);
		umsPersonService.savePerson(person);
	}
	
	@Test
	public void changePassword(){
		String oldPassword = "123456";
		UmsUser user = new UmsUser();
		UmsPerson person = new UmsPerson();
		user.setUsername("zhangsan");
		user.setNickname("张三");
		user.setPassword(oldPassword);
		user.generateSalt();
        user.setPassword(encryptPassword(user.getUsername(), user.getPassword(), user.getSalt()));
        user.setCreateDate(new Date());
        person.setUser(user);
		umsPersonService.savePerson(person);
		UmsUser user = umsUserService.findOne("402881e75912103601591210444e0001");
		umsUserService.delete(user);
		umsUserService.findOne("402881e75912103601591210444e0001");
		umsUserService.findOne("402881e75912103601591210444e0001");
//		user.setPassword("12345678");
//		umsUserService.update(user);
//		assertNotEquals(oldPassword, user.getPassword());
	}
	
	@Test
	public void findUserByUsername(){
//		UmsUser user = new UmsUser();
//		UmsPerson person = new UmsPerson();
//		user.setUsername("zhangsan");
//		user.setNickname("张三");
//		user.setPassword("123456");
//		user.generateSalt();
//        user.setPassword(encryptPassword(user.getUsername(), user.getPassword(), user.getSalt()));
//        user.setCreateDate(new Date());
//        person.setUser(user);
//		umsPersonService.savePerson(person);
		UmsPerson person = umsPersonService.findOne("402882945949734501594973580e0000");
		System.out.println(person.getUser());
	}
	
	@Test
	public void login(){
		umsUserService.login("lisi", "123456");
	}
	@Test(expected = UserNotExistsException.class)
	public void loginUserNotExistsException(){
		umsUserService.login("zhangsan", "123456");
	}
	@Test(expected = UserPasswordNotMatchException.class)
	public void loginUserPasswordNotMatchException(){
		umsUserService.login("lisi", "12345678");
	}
	@Test(expected = UserUnAvailableException.class)
	public void loginUserUnAvailableException(){
		umsUserService.login("wangwu", "123456");
	}*/
    private String encryptPassword(String username, String password, String salt) {
        return Md5Utils.hash(username + password + salt);
    }
}
