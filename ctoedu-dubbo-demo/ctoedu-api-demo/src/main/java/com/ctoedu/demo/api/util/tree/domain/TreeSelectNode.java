package com.ctoedu.demo.api.util.tree.domain;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.ctoedu.demo.api.util.tree.service.ITreeNode;

public class TreeSelectNode {
	//树节点名称
    @JSONField(ordinal=1)
    private String label;
	//树节点ID
    @JSONField(ordinal=2)
    private String value;
    //树节点ID
    @JSONField(ordinal=3)
    private String key;
    //父节点ID
    @JSONField(ordinal=4)
    private String parentNodeId;
    private TreeSelectNode parent;
    //当前节点的二子节点
    @JSONField(ordinal=7)
    private List<TreeSelectNode> children = new ArrayList<TreeSelectNode>();
    //当前节点的子孙节点
    private List<TreeSelectNode> allChildren = new ArrayList<TreeSelectNode>();

    public TreeSelectNode(ITreeNode obj){
        this.key = obj.getNodeId();
        this.value = obj.getNodeId();
        this.label = obj.getNodeName();
        this.parentNodeId = obj.getNodeParentId();
    }
    public void addChild(TreeSelectNode treeNode){
        this.children.add(treeNode);
    }
    public void removeChild(TreeSelectNode treeNode){
        this.children.remove(treeNode);
    }
    
    public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public TreeSelectNode getParent() {
		return parent;
	}
	public void setParent(TreeSelectNode parent) {
		this.parent = parent;
	}
	public String getParentNodeId() {
		return parentNodeId;
	}
	public void setParentNodeId(String parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	public List<TreeSelectNode> getChildren() {
        return children;
    }
    public void setChildren(List<TreeSelectNode> children) {
        this.children = children;
    }

    public List<TreeSelectNode> getAllChildren() {
        if(this.allChildren.isEmpty()){
            for(TreeSelectNode treeNode : this.children){
                this.allChildren.add(treeNode);
                this.allChildren.addAll(treeNode.getAllChildren());
            }
        }
        return this.allChildren;
    }
}
