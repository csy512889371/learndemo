package com.ctoedu.demo.api.util.tree;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.ctoedu.demo.api.util.tree.domain.Org;
import com.ctoedu.demo.api.util.tree.service.IFayTreeNode;
import com.ctoedu.demo.api.util.tree.service.ITreeNode;
import com.ctoedu.demo.api.util.tree.service.impl.FayTree;
import com.ctoedu.demo.api.util.tree.service.impl.Tree;
import com.ctoedu.demo.api.util.tree.service.impl.TreeSelect;

public class TreeDemo {
	public static void main(String[] args) {
        Tree tree = new Tree(genOrgList());
//        TreeNode treeNode = tree.getTreeNode("2");

        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(); // 构造方法里，也可以直接传需要序列化的属性名字
        filter.getExcludes().add("parent");
        filter.getExcludes().add("allChildren");
        String data = JSONObject.toJSONString(tree, filter);
        System.out.println(data);
        
        TreeSelect treeSelect = new TreeSelect(genOrgList());
//      TreeNode treeNode = tree.getTreeNode("2");

      SimplePropertyPreFilter filter1 = new SimplePropertyPreFilter(); // 构造方法里，也可以直接传需要序列化的属性名字
      filter1.getExcludes().add("parent");
      filter1.getExcludes().add("allChildren");
      filter1.getExcludes().add("parentNodeId");
      String data1 = JSONObject.toJSONString(treeSelect.getRoot(), filter1);
      System.out.println(data1);
      
      FayTree fayTree = new FayTree(genFayOrgList());
//    TreeNode treeNode = tree.getTreeNode("2");

    SimplePropertyPreFilter filter2 = new SimplePropertyPreFilter(); // 构造方法里，也可以直接传需要序列化的属性名字
    filter2.getExcludes().add("parent");
    filter2.getExcludes().add("allChildren");
    filter2.getExcludes().add("parentNodeId");
    filter2.getExcludes().add("nodeId");
    String data2 = JSONObject.toJSONString(fayTree.getRoot(), filter2);
    System.out.println(data2);
    }

    public static List<ITreeNode> genOrgList(){
        List<ITreeNode> list = new ArrayList<ITreeNode>();

        Org org = new Org("2", "1", "北京市", 2, "110000", "2");
        list.add(org);
        org = new Org("3", "2", "市辖区", 3, "110100", "3");
        list.add(org);
        org = new Org("4", "3", "东城区", 4, "110101", "4");
        list.add(org);
        org = new Org("5", "3", "东城区", 5, "110102", "4");
        list.add(org);
        org = new Org("6", "3", "东城区", 6, "110105", "4");
        list.add(org);
        org = new Org("7", "3", "东城区", 7, "110106", "4");
        list.add(org);
        org = new Org("8", "3", "东城区", 8, "110107", "4");
        list.add(org);
        org = new Org("9", "3", "东城区", 9, "110108", "4");
        list.add(org);
        org = new Org("10", "3", "东城区", 10, "110109", "4");
        list.add(org);
        org = new Org("11", "3", "东城区", 11, "110111", "4");
        list.add(org);
        org = new Org("12", "3", "东城区", 12, "110112", "4");
        list.add(org);
        org = new Org("13", "3", "东城区", 13, "110113", "4");
        list.add(org);
        org = new Org("14", "3", "东城区", 14, "110114", "4");
        list.add(org);
        org = new Org("15", "3", "东城区", 15, "110115", "4");
        list.add(org);
        org = new Org("16", "3", "东城区", 16, "110116", "4");
        list.add(org);
        org = new Org("17", "3", "东城区", 17, "110117", "4");
        list.add(org);
        org = new Org("18", "2", "县", 3, "110200", "3");
        list.add(org);
        org = new Org("19", "18", "密云县", 19, "110228", "4");
        list.add(org);
        org = new Org("20", "18", "延庆县", 20, "110229", "4");
        list.add(org);
        return list;
    }
    
    public static List<IFayTreeNode> genFayOrgList(){
        List<IFayTreeNode> list = new ArrayList<IFayTreeNode>();

        Org org = new Org("2", "1", "北京市", 2, "110000", "2");
        list.add(org);
        org = new Org("3", "2", "市辖区", 3, "110100", "3");
        list.add(org);
        org = new Org("4", "3", "东城区", 4, "110101", "4");
        list.add(org);
        org = new Org("5", "3", "东城区", 5, "110102", "4");
        list.add(org);
        org = new Org("6", "3", "东城区", 6, "110105", "4");
        list.add(org);
        org = new Org("7", "3", "东城区", 7, "110106", "4");
        list.add(org);
        org = new Org("8", "3", "东城区", 8, "110107", "4");
        list.add(org);
        org = new Org("9", "3", "东城区", 9, "110108", "4");
        list.add(org);
        org = new Org("10", "3", "东城区", 10, "110109", "4");
        list.add(org);
        org = new Org("11", "3", "东城区", 11, "110111", "4");
        list.add(org);
        org = new Org("12", "3", "东城区", 12, "110112", "4");
        list.add(org);
        org = new Org("13", "3", "东城区", 13, "110113", "4");
        list.add(org);
        org = new Org("14", "3", "东城区", 14, "110114", "4");
        list.add(org);
        org = new Org("15", "3", "东城区", 15, "110115", "4");
        list.add(org);
        org = new Org("16", "3", "东城区", 16, "110116", "4");
        list.add(org);
        org = new Org("17", "3", "东城区", 17, "110117", "4");
        list.add(org);
        org = new Org("18", "2", "县", 3, "110200", "3");
        list.add(org);
        org = new Org("19", "18", "密云县", 19, "110228", "4");
        list.add(org);
        org = new Org("20", "18", "延庆县", 20, "110229", "4");
        list.add(org);
        return list;
    }
}
