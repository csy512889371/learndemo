package com.ctoedu.demo.api.util.tree;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.ctoedu.demo.api.util.tree.service.ITreeNode;
import com.ctoedu.demo.api.util.tree.service.impl.TreeSelect;

public class TreeUtil {

	public static Object getTreeSelectInJsonObject(List<? extends ITreeNode> list){
		if(list == null || list.isEmpty()){
			return new ArrayList<>();
		}
		TreeSelect treeSelect = new TreeSelect(new ArrayList<>(list));
		SimplePropertyPreFilter filter1 = new SimplePropertyPreFilter(); // 构造方法里，也可以直接传需要序列化的属性名字
		filter1.getExcludes().add("parent");
		filter1.getExcludes().add("allChildren");
		filter1.getExcludes().add("parentNodeId");
		String treeJsonString = JSONObject.toJSONString(treeSelect.getRoot(), filter1);
		Object data = JSONObject.parse(treeJsonString);
		return data;
	}
}
