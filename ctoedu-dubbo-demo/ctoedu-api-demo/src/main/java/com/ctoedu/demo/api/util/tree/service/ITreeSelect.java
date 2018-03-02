package com.ctoedu.demo.api.util.tree.service;

import java.util.List;

import com.ctoedu.demo.api.util.tree.domain.TreeSelectNode;

public interface ITreeSelect {
	List<TreeSelectNode> getTree();
    List<TreeSelectNode> getRoot();
    TreeSelectNode getTreeSelectNode(String nodeId);
}
