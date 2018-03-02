package com.ctoedu.common.repository.hibernate.type;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 *
 * Date:2016年11月4日 下午4:47:29
 * Version:1.0
 */
public class JsonMap implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8938455467003150436L;
	private Map<Object, Object> map;

    public JsonMap() {
    }

    public JsonMap(Map<Object, Object> map) {
        this.map = map;
    }

    public Map<Object, Object> getMap() {
        if (map == null) {
            map = Maps.newHashMap();
        }
        return map;
    }

    public void setMap(Map<Object, Object> map) {
        this.map = map;
    }
}
