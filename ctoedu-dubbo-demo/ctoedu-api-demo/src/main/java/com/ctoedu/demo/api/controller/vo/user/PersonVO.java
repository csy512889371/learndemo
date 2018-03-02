package com.ctoedu.demo.api.controller.vo.user;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.facade.user.entity.UmsPerson;
import com.ctoedu.demo.facade.user.entity.UmsUser;

public class PersonVO implements BaseVO {
	
	private String name;
	
	private String phone;
	
	private String sex;
	
	private String birth;
	
	private UserVO user;

	@Override
	public void convertPOToVO(Object poObj) {
		
		if(poObj != null && poObj instanceof UmsPerson){
			UmsPerson person = (UmsPerson)poObj;
			this.name = person.getName();
			this.phone = person.getPhone();
			this.sex = String.valueOf(person.getSex());
//			this.birth = person.getBirth();
			UmsUser umsUser = person.getUser();
			if(umsUser != null){
				user.setNickname(umsUser.getNickname());
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}
}
