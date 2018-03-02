package com.ctoedu.demo.facade.dict.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.facade.dict.entity.UmsDictionaryType;


/**
 *
 * Date:2016年11月23日 下午1:44:09
 * Version:1.0
 */
public interface UmsDictionaryTypeFacade {
    /**
     * 新增数据字典条目记录
     * @param umsDictionaryType
     * @return
     */
    public UmsDictionaryType create(UmsDictionaryType umsDictionaryType);

    /**
     * 更新数据字典条目记录
     * @param umsDictionaryType
     * @return
     */
    public UmsDictionaryType update(UmsDictionaryType umsDictionaryType);

    /**
     * 根据ids删除数据字典条目记录
     * @param ids
     * @return
     */
    public void delete(String... ids);

    /**
     * 根据id查询数据字典条目记录
     * @param id
     * @return
     */
    public UmsDictionaryType getUmsDictionaryTypeById(String id);

    /**
     * 根据code查询数据字典条目记录
     * @param code
     * @return
     */
    public UmsDictionaryType getUmsDictionaryTypeByCode(String code);

    /**
     * 分页查询
     * @param searchable
     * @return
     */
    public Page<UmsDictionaryType> listPage(Searchable searchable);

    /**
     * 条件查询
     * @param searchable
     * @return
     */
    public List<UmsDictionaryType> list(Searchable searchable);
}
