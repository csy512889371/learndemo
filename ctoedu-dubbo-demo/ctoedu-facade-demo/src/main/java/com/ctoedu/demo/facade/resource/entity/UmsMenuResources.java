package com.ctoedu.demo.facade.resource.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ctoedu.common.entity.UUIDEntity;
import com.ctoedu.common.entity.enums.AvailableEnum;
import com.ctoedu.demo.facade.Resource;
import com.ctoedu.demo.facade.app.entity.UmsApp;

/**
 *
 * Date:2016年11月8日 下午3:01:38
 * Version:1.0
 */
@Entity
@Table(name = "UMS_MENU_RESOURCE")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UmsMenuResources extends UUIDEntity<String> implements Resource,Comparable<UmsMenuResources>{

	private static final long serialVersionUID = 1L;
	@Column(name="MENU_SN")
	private String menuSn;
	@Column(name="MENU_NAME")
	private String menuName;
	@Column(name="MENU_URL")
	private String menuUrl;
	@Column(name="MENU_ICON")
	private String menuIcon;
	@Column(name="MENU_ORDER")
	private Integer menuOrder;
	@Column(name="IS_AVAILABLE")
	private Short isAvailable = AvailableEnum.TRUE.getValue();
	@Column(name="IS_LEAF")
	private Short isLeaf;
	@ManyToOne
	@JoinColumn(name="PARENT_ID")
	private UmsMenuResources parent;
	@ManyToOne
	@JoinColumn(name="APPLICATION_ID")
	private UmsApp application;
	
	public String getMenuSn() {
		return menuSn;
	}
	public void setMenuSn(String menuSn) {
		this.menuSn = menuSn;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getMenuIcon() {
		return menuIcon;
	}
	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}
	public Integer getMenuOrder() {
		return menuOrder;
	}
	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}
	public Short getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Short isAvailable) {
		this.isAvailable = isAvailable;
	}
	public Short getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Short isLeaf) {
		this.isLeaf = isLeaf;
	}
	public UmsMenuResources getParent() {
		return parent;
	}
	public void setParent(UmsMenuResources parent) {
		this.parent = parent;
	}
	public UmsApp getApplication() {
		return application;
	}
	public void setApplication(UmsApp application) {
		this.application = application;
	}
	@Override
	public int compareTo(UmsMenuResources umr) {
		if(this.menuOrder == null && umr.menuOrder == null){
			return this.menuName.compareTo(umr.menuName);
		}else if(this.menuOrder == null){
			return -1;
		}else if(umr.menuOrder == null){
			return 1;
		}else{
			int num = this.menuOrder.compareTo(umr.menuOrder);
			return num==0?this.menuName.compareTo(umr.menuName):num;
		}
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj)  
	        return false;  
	    if(obj == null)  
	        return false;  
	    if(getClass() != obj.getClass() )  
	        return false;  
	    UmsMenuResources other = (UmsMenuResources)obj;  
	    if(this.getId() != other.getId())  
	        return false;  
	    return true;  
	}
}
