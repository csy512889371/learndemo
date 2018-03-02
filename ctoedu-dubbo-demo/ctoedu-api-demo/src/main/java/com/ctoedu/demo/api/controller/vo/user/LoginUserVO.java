package com.ctoedu.demo.api.controller.vo.user;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.facade.user.entity.UmsUser;

public class LoginUserVO implements BaseVO {
	
	private String id;
	
	private String nickname;
	
	private String username;
	
	private String token;
	
//	private String name;

	@Override
	public void convertPOToVO(Object poObj) {
		
		if(poObj != null && poObj instanceof UmsUser){
			UmsUser user = (UmsUser)poObj;
			this.id = user.getId();
			this.nickname = user.getNickname();
			this.username = user.getUsername();
//			UmsPerson person = user.getPerson();
//			if(person != null){
//				this.name = person.getName();
//			}
		}
	}

//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}

	public String getNickname() {
		return nickname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}