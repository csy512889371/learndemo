package com.ctoedu.demo.test.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ctoedu.demo.facade.user.entity.UmsUser;
import com.ctoedu.demo.facade.user.exception.UserUsernameExistsException;
import com.ctoedu.demo.facade.user.service.UmsUserFacade;
@SuppressWarnings("resource")
public class Test {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.ctoedu.demo.config");
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-dubbo-consumer.xml");
		context.start();
//		context.start();
//		UmsMenuResFacade umsMenuResFacade = (UmsMenuResFacade) context.getBean("umsMenuResFacade");
//		System.out.println(umsMenuResFacade);
//		Searchable searchable = Searchable.newSearchable();
//		searchable.addSearchParam("isAvailable_eq", AvailableEnum.TRUE.getValue());
//		searchable.addSearchParam("application.id_eq", "ff80808159aa70380159af77e1aa0002");
//		searchable.addSort(Direction.ASC, "menuOrder");
//		umsMenuResFacade.listUmsMenuResources(searchable);
		
		UmsUserFacade umsUserFacade = (UmsUserFacade) context.getBean("umsUserFacade");
		UmsUser user = new UmsUser();
		user.setNickname("111111");
		user.setUsername("sss2991182");
		user.setPassword("123456");
		try{
			UmsUser umsUser = umsUserFacade.register(user);
		}catch (UserUsernameExistsException e){
			System.out.print(e.getMessage());
		}


//		UmsApp app1 = new UmsApp();
//		app1.setSn("Simba");
//		app1.setName("开发平台");
//		umsAppFacade.register(app1);
	}
	
}
