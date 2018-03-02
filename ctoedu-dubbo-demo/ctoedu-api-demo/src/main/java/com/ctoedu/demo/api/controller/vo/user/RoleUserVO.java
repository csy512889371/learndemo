package com.ctoedu.demo.api.controller.vo.user;

import com.ctoedu.common.utils.DateUtil;
import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.facade.role.entity.UmsUserRoleRelation;
import com.ctoedu.demo.facade.user.entity.UmsUser;

public class RoleUserVO implements BaseVO {
	
	private String id;
	
	private String nickname;
	
	private String username;
	
	private String createDate;
	/**
	 * 是否可用
	 */
	private Short isAvailable;
	/**
	 * 是否删除
	 */
    private Short deleted;

	@Override
	public void convertPOToVO(Object poObj) {
		if(poObj != null && poObj instanceof UmsUserRoleRelation){
			UmsUserRoleRelation uurr = (UmsUserRoleRelation)poObj;
			UmsUser user = uurr.getUser();
			if(user != null){
				this.id = user.getId();
				this.nickname = user.getNickname();
				this.username = user.getUsername();
				this.createDate = DateUtil.formatDate(DateUtil.DATE_FORMATS[1], user.getCreateDate());
				this.isAvailable = user.getIsAvailable();
				this.deleted = user.getDeleted();
			}
		}
	}

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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Short getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Short isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Short getDeleted() {
		return deleted;
	}

	public void setDeleted(Short deleted) {
		this.deleted = deleted;
	}
}
