package com.ctoedu.demo.api.util.tree;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.ctoedu.demo.api.util.tree.service.IFayTreeNode;
import com.ctoedu.demo.api.util.tree.service.impl.FayTree;

public class FayTreeUtil {

	public static Object getTreeInJsonObject(List<? extends IFayTreeNode> list){
		if(list == null || list.isEmpty()){
			return new ArrayList<>();
		}
		FayTree tree = new FayTree(new ArrayList<>(list));
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(); // 构造方法里，也可以直接传需要序列化的属性名字
		filter.getExcludes().add("parent");
		filter.getExcludes().add("allChildren");
		filter.getExcludes().add("parentNodeId");
		filter.getExcludes().add("nodeId");
		String treeJsonString = JSONObject.toJSONString(tree.getRoot(), filter);
		Object data = JSONObject.parse(treeJsonString);
		return data;
	}
}
