package com.ctoedu.demo.api.controller.vo.unify;//package com.ctoedu.demo.api.controller.vo.unify;
//
//import com.ctoedu.common.vo.BaseVO;
//import com.ctoedu.demo.api.enums.UnifyType;
//import com.ctoedu.demo.api.util.tree.service.IFayTreeNode;
//import com.ctoedu.demo.facade.app.entity.UmsApp;
//import com.ctoedu.demo.facade.role.entity.UmsRole;
//import com.ctoedu.demo.facade.role.entity.UmsUserRoleRelation;
//
//public class AppRoleTreeVO implements BaseVO,IFayTreeNode {
//	
//	private String id;
//	
//	/**
//	 * 组织的名称
//	 */
//	private String name;
//	/**
//	 * 组织机构的排序号
//	 */
//	private Integer orderNum;
//	
//	private String parentId;
//	
//	private String type;
//	
//	private String available = "1";//'1'为可用，'2'为不可用
//	
//	@Override
//	public void convertPOToVO(Object poObj) {
//		
//		if(poObj != null){
//			if(poObj instanceof UmsApp){
//				UmsApp app = (UmsApp)poObj;
//				this.id = app.getId();
//				this.name = app.getName();
//				this.type = UnifyType.APP.toString();
//				this.available = "2";
//			}else if(poObj instanceof UserVO){
//				UserVO user = (UserVO)poObj;
//				this.id = user.getId() + "_" + user.getParentType() + "_" + user.getParentId();
//				this.name = user.getName();
//				this.type = user.getType();
//				this.parentId = user.getParentId();
//			}else{
//				UmsRole role = null;
//				if(poObj instanceof UmsRole){
//					role = (UmsRole)poObj;
//				}else if(poObj instanceof UmsUserRoleRelation){
//					UmsUserRoleRelation uurr = (UmsUserRoleRelation)poObj;
//					role = uurr.getRole();
//				}
//				if(role != null){
//					this.id = role.getId();
//					this.name = role.getName();
//					this.parentId = role.getAppId();
//					this.type = UnifyType.ROLE.toString();
//					this.available = "2";
//				}
//			}
//		}
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public Integer getOrderNum() {
//		return orderNum;
//	}
//
//	public void setOrderNum(Integer orderNum) {
//		this.orderNum = orderNum;
//	}
//
//	public String getParentId() {
//		return parentId;
//	}
//
//	public void setParentId(String parentId) {
//		this.parentId = parentId;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	@Override
//	public String getNodeId() {
//		return this.id;
//	}
//
//	@Override
//	public String getNodeParentId() {
//		return this.parentId;
//	}
//
//	public String getAvailable() {
//		return available;
//	}
//
//	public void setAvailable(String available) {
//		this.available = available;
//	}
//}