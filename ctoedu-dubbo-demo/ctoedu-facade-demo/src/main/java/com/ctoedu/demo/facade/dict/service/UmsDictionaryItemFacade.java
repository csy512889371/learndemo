package com.ctoedu.demo.facade.dict.service;


import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.facade.dict.entity.UmsDictionaryItem;
import com.ctoedu.demo.facade.dict.entity.UmsDictionaryType;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 * Date:2016年11月23日 下午1:44:09
 * Version:1.0
 */
public interface UmsDictionaryItemFacade {
    /**
     * 新增数据字典条目记录
     * @param umsDictionaryItem
     * @return
     */
    public UmsDictionaryItem create(UmsDictionaryItem umsDictionaryItem);

    /**
     * 更新数据字典条目记录
     * @param umsDictionaryItem
     * @return
     */
    public UmsDictionaryItem update(UmsDictionaryItem umsDictionaryItem);

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
    public UmsDictionaryItem getUmsDictionaryItemById(String id);

    /**
     * 根据code查询数据字典条目记录
     * @param code
     * @param type
     * @return
     */
    public UmsDictionaryItem getUmsDictionaryItemByCodeAndType(Integer code, UmsDictionaryType type);

    /**
     * 分页查询
     * @param searchable
     * @return
     */
    public Page<UmsDictionaryItem> listPage(Searchable searchable);

    /**
     * 条件查询
     * @param searchable
     * @return
     */
    public List<UmsDictionaryItem> list(Searchable searchable);
}
