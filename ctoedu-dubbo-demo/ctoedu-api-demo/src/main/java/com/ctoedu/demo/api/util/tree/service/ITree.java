package com.ctoedu.demo.api.util.tree.service;

import java.util.List;

import com.ctoedu.demo.api.util.tree.domain.TreeNode;

public interface ITree {
	List<TreeNode> getTree();
    List<TreeNode> getRoot();
    TreeNode getTreeNode(String nodeId);
}
