package com.ctoedu.demo.api.util.tree.domain;

import com.ctoedu.demo.api.util.tree.service.IFayTreeNode;
import com.ctoedu.demo.api.util.tree.service.ITreeNode;

public class Org implements ITreeNode,IFayTreeNode {
	private String uuid;
	private String parentId;
	private String name;
	private Integer orderNum;
	private String code;
	private String type;

	public Org(String uuid, String parentId, String name, Integer orderNum, String code, String type) {
		this.uuid = uuid;
		this.parentId = parentId;
		this.name = name;
		this.orderNum = orderNum;
		this.code = code;
		this.type = type;
	}

	@Override
	public String getNodeId() {
		return this.uuid;
	}

	@Override
	public String getNodeName() {
		return this.name;
	}

	@Override
	public String getNodeParentId() {
		return this.parentId;
	}

	@Override
	public Integer getOrderNum() {
		return this.orderNum;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
}
