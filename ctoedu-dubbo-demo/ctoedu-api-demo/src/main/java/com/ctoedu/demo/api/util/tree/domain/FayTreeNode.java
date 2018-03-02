package com.ctoedu.demo.api.util.tree.domain;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.ctoedu.demo.api.util.tree.service.IFayTreeNode;

public class FayTreeNode {
	//树节点ID
    @JSONField(ordinal=1)
    private String nodeId;
    //父节点ID
    @JSONField(ordinal=3)
    private String parentNodeId;
//    //节点在树中的排序号
//    @JSONField(ordinal=4)
//    private int orderNum;
//    //节点所在的层级
//    @JSONField(ordinal=5)
//    private int level;
    private FayTreeNode parent;
    
    private IFayTreeNode treeObject;
    //当前节点的二子节点
    @JSONField(ordinal=6)
    private List<FayTreeNode> children = new ArrayList<FayTreeNode>();
    //当前节点的子孙节点
    private List<FayTreeNode> allChildren = new ArrayList<FayTreeNode>();
    
    public FayTreeNode(){
    	
    }

    public FayTreeNode(IFayTreeNode obj){
        this.nodeId = obj.getNodeId();
        this.parentNodeId = obj.getNodeParentId();
        this.treeObject = obj;
    }
    public void addChild(FayTreeNode treeNode){
        this.children.add(treeNode);
    }
    public void removeChild(FayTreeNode treeNode){
        this.children.remove(treeNode);
    }
    public String getNodeId() {
        return nodeId;
    }
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
    public String getParentNodeId() {
        return parentNodeId;
    }
    public void setParentNodeId(String parentNodeId) {
        this.parentNodeId = parentNodeId;
    }
    public FayTreeNode getParent() {
        return parent;
    }
    public void setParent(FayTreeNode parent) {
        this.parent = parent;
    }
    public List<FayTreeNode> getChildren() {
    	if(children == null || children.isEmpty()) return null;
        return children;
    }
    public void setChildren(List<FayTreeNode> children) {
        this.children = children;
    }
    
	public IFayTreeNode getTreeObject() {
		return treeObject;
	}

	public void setTreeObject(IFayTreeNode treeObject) {
		this.treeObject = treeObject;
	}

	public List<FayTreeNode> getAllChildren() {
        if(this.allChildren.isEmpty()){
            for(FayTreeNode treeNode : this.children){
                this.allChildren.add(treeNode);
                this.allChildren.addAll(treeNode.getAllChildren());
            }
        }
        return this.allChildren;
    }
}
