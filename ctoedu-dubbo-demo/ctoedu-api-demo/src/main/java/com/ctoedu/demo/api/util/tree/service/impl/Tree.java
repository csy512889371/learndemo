package com.ctoedu.demo.api.util.tree.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.ctoedu.demo.api.util.tree.domain.TreeNode;
import com.ctoedu.demo.api.util.tree.service.ITree;
import com.ctoedu.demo.api.util.tree.service.ITreeNode;

public class Tree implements ITree {
	private HashMap<String, TreeNode> treeNodesMap = new LinkedHashMap<String, TreeNode>();
    private List<TreeNode> treeNodesList = new ArrayList<TreeNode>();

    public Tree(List<ITreeNode> list){
        initTreeNodeMap(list);
        initTreeNodeList();
    }

    private void initTreeNodeMap(List<ITreeNode> list){
        TreeNode treeNode = null;
        for(ITreeNode item : list){
            treeNode = new TreeNode(item);
            treeNodesMap.put(treeNode.getNodeId(), treeNode);
        }

        Iterator<TreeNode> iter = treeNodesMap.values().iterator();
        TreeNode parentTreeNode = null;
        while(iter.hasNext()){
            treeNode = iter.next();
            if(treeNode.getParentNodeId() == null || treeNode.getParentNodeId() == ""){
                continue;
            }

            parentTreeNode = treeNodesMap.get(treeNode.getParentNodeId());
            if(parentTreeNode != null){
                treeNode.setParent(parentTreeNode);
                parentTreeNode.addChild(treeNode);
            }
        }
    }

    private void initTreeNodeList(){
        if(treeNodesList.size() > 0){
            return;
        }
        if(treeNodesMap.size() == 0){
            return;
        }
        Iterator<TreeNode> iter = treeNodesMap.values().iterator();
        TreeNode treeNode = null;
        while(iter.hasNext()){
            treeNode = iter.next();
            if(treeNode.getParent() == null){
                this.treeNodesList.add(treeNode);
                this.treeNodesList.addAll(treeNode.getAllChildren());
            }
        }
    }

    @Override
    public List<TreeNode> getTree() {
        return this.treeNodesList;
    }

    @Override
    public List<TreeNode> getRoot() {
        List<TreeNode> rootList = new ArrayList<TreeNode>();
        if (this.treeNodesList.size() > 0) {
            for (TreeNode node : treeNodesList) {
                if (node.getParent() == null)
                    rootList.add(node);
            }
        }
        return rootList;
    }

    @Override
    public TreeNode getTreeNode(String nodeId) {
        return this.treeNodesMap.get(nodeId);
    }
}
