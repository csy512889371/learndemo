package com.ctoedu.demo.api.controller.vo.person;

import com.ctoedu.common.utils.DateUtil;
import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.facade.department.entity.UmsUserDepartmentRelation;
import com.ctoedu.demo.facade.org.entity.UmsUserOrgRelation;
import com.ctoedu.demo.facade.position.entity.UmsUserPositionRelation;
import com.ctoedu.demo.facade.user.entity.UmsPerson;
import com.ctoedu.demo.facade.user.entity.UmsUser;

public class PersonVO implements BaseVO {
	
	private String name;
	
	private String sn;
	
	private String phone;
	
	private String sex;
	
	private String birth;
	
	private String userId;
	
	@Override
	public void convertPOToVO(Object poObj) {
		
		if(poObj != null){
			UmsPerson person = null;
			UmsUser user = null;
			if(poObj instanceof UmsUser){
				user = (UmsUser)poObj;
				person = user.getPerson();
			}else if(poObj instanceof UmsPerson){
				person = (UmsPerson)poObj;
				if(person != null) user = person.getUser();
			}else if(poObj instanceof UmsUserOrgRelation){
				UmsUserOrgRelation uuor = (UmsUserOrgRelation)poObj;
				user = uuor.getUser();
				if(user != null){
					person = user.getPerson();
				}
			}else if(poObj instanceof UmsUserDepartmentRelation){
				UmsUserDepartmentRelation uugr = (UmsUserDepartmentRelation)poObj;
				user = uugr.getUser();
				if(user != null){
					person = user.getPerson();
				}
			}else if(poObj instanceof UmsUserPositionRelation){
				UmsUserPositionRelation uupr = (UmsUserPositionRelation)poObj;
				user = uupr.getUser();
				if(user != null){
					person = user.getPerson();
				}
			}
			if(person != null){
				this.name = person.getName();
				this.sn = person.getSn();
				this.phone = person.getPhone();
				this.sex = String.valueOf(person.getSex());
				this.birth = DateUtil.formatDate(DateUtil.DATE_FORMATS[5], person.getBirth());
				if(user != null){
					this.userId = user.getId();
				}
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

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
