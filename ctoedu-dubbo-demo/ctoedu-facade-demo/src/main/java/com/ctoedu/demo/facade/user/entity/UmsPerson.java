package com.ctoedu.demo.facade.user.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.ctoedu.common.entity.AbstractEntity;

/**
 *
 * Date:2016年11月8日 下午2:08:38
 * Version:1.0
 */
@Entity
@Table(name="UMS_PERSON")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UmsPerson extends AbstractEntity<String>{

	private static final long serialVersionUID = 1L;
	@Id   
	@GeneratedValue(generator = "idGenerator")  
	@GenericGenerator(name = "idGenerator", strategy = "foreign",   
	         parameters = { @Parameter(name = "property", value = "user") })   
	private String id;
	private String name;
	private String sfzh;
	private String phone;
	private Short sex;
	private Date birth;
	private String sn;
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn  
	private UmsUser user;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSfzh() {
		return sfzh;
	}
	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Short getSex() {
		return sex;
	}
	public void setSex(Short sex) {
		this.sex = sex;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public UmsUser getUser() {
		return user;
	}
	public void setUser(UmsUser user) {
		this.user = user;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
}
