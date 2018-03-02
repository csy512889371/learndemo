package com.ctoedu.demo.facade.org.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ctoedu.common.entity.UUIDEntity;
import com.ctoedu.common.entity.enums.AvailableEnum;
import com.ctoedu.demo.facade.Principal;
import com.ctoedu.demo.facade.org.entity.enums.OrgManagerTypeEnum;

/**
 * 组织机构实体类
 *
 * Date:2016年11月7日 下午4:38:26
 * Version:1.0
 */
@Entity
@Table(name="UMS_ORG")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UmsOrg extends UUIDEntity<String> implements Principal{

	private static final long serialVersionUID = 1L;
	/**
	 * 组织机构的名称
	 */
	private String name;
	/**
	 * 组织机构所属类型的id
	 */
	@Column(name="TYPE_ID")
	private String typeId;
	/**
	 * 组织机构所属类型的名称
	 */
	@Column(name="TYPE_NAME")
	private String typeName;
	/**
	 * 组织机构的排序号
	 */
	@Column(name="ORDER_NUM")
	private Integer orderNum;
	/**
	 * 组织机构的父亲组织
	 */
	@ManyToOne
	@JoinColumn(name="PARENT_ID", referencedColumnName="ID")
	private UmsOrg parent;
	/**
	 * 管理类型
	 */
	@Column(name="MANAGER_TYPE")
	private Short managerType = OrgManagerTypeEnum.DEFAULT.getValue();
	/**
	 * 组织机构的地址
	 */
	private String address;
	/**
	 * 组织机构的电话
	 */
	private String phone;
	
	/**
	 * 是否可用
	 */
	@Column(name="IS_AVAILABLE")
	private Short isAvailable = AvailableEnum.TRUE.getValue();
	
	/**
	 * 扩展属性1，用于在针对某些特殊的组织存储相应的信息
	 */
	private String att1;
	/**
	 * 扩展属性2，用于在针对某些特殊的组织存储相应的信息
	 */
	private String att2;
	/**
	 * 扩展属性3，用于在针对某些特殊的组织存储相应的信息
	 */
	private String att3;
	/**
	 * 扩展属性4，用于在针对某些特殊的组织存储相应的信息
	 */
	private String att4;
	/**
	 * 扩展属性5，用于在针对某些特殊的组织存储相应的信息
	 */
	private String att5;
	/**
	 * 扩展属性6，用于在针对某些特殊的组织存储相应的信息
	 */
	private String att6;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public UmsOrg getParent() {
		return parent;
	}
	public void setParent(UmsOrg parent) {
		this.parent = parent;
	}
	public Short getManagerType() {
		return managerType;
	}
	public void setManagerType(Short managerType) {
		this.managerType = managerType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Short getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Short isAvailable) {
		this.isAvailable = isAvailable;
	}
	public String getAtt1() {
		return att1;
	}
	public void setAtt1(String att1) {
		this.att1 = att1;
	}
	public String getAtt2() {
		return att2;
	}
	public void setAtt2(String att2) {
		this.att2 = att2;
	}
	public String getAtt3() {
		return att3;
	}
	public void setAtt3(String att3) {
		this.att3 = att3;
	}
	public String getAtt4() {
		return att4;
	}
	public void setAtt4(String att4) {
		this.att4 = att4;
	}
	public String getAtt5() {
		return att5;
	}
	public void setAtt5(String att5) {
		this.att5 = att5;
	}
	public String getAtt6() {
		return att6;
	}
	public void setAtt6(String att6) {
		this.att6 = att6;
	}
	
}
