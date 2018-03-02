package com.ctoedu.common.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 
 *
 *
 * @param <T>
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class ListVO<T extends BaseVO> {

	
	private List<T> voList = new ArrayList<T>();
	
	public ListVO(List poList, Class<T> VOType){
		for(Object obj : poList){
			try {
				BaseVO vo = VOType.newInstance();
				vo.convertPOToVO(obj);
				voList.add((T) vo);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ListVO(Set poList, Class<T> VOType){
		for(Object obj : poList){
			try {
				BaseVO vo = VOType.newInstance();
				vo.convertPOToVO(obj);
				voList.add((T) vo);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public List<T> getVoList() {
		return voList;
	}

	public void setVoList(List voList) {
		this.voList = voList;
	}
}
