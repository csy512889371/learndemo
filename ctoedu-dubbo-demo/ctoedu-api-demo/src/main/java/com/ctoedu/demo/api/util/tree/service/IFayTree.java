package com.ctoedu.demo.api.util.tree.service;

import java.util.List;

import com.ctoedu.demo.api.util.tree.domain.FayTreeNode;

public interface IFayTree {
	List<FayTreeNode> getTree();
    List<FayTreeNode> getRoot();
    FayTreeNode getFayTreeNode(String nodeId);
}
