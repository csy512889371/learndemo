package com.ctoedu.demo.facade.dict.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.alibaba.dubbo.config.annotation.Service;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.core.dict.service.UmsDictionaryItemService;
import com.ctoedu.demo.facade.dict.entity.UmsDictionaryItem;
import com.ctoedu.demo.facade.dict.entity.UmsDictionaryType;
import com.ctoedu.demo.facade.dict.service.UmsDictionaryItemFacade;

/**
 * Created by Administrator on 2017/10/9.
 */
@Service(retries = 2)
public class UmsDictionaryItemFacadeImpl implements UmsDictionaryItemFacade {
    @Autowired
    private UmsDictionaryItemService umsDictionaryItemService;

    @Override
    public UmsDictionaryItem create(UmsDictionaryItem umsDictionaryItem) {
        return umsDictionaryItemService.saveUmsDictionaryItem(umsDictionaryItem);
    }

    @Override
    public UmsDictionaryItem update(UmsDictionaryItem umsDictionaryItem) {
        return umsDictionaryItemService.update(umsDictionaryItem);
    }

    @Override
    public void delete(String... ids) {
        umsDictionaryItemService.delete(ids);
    }

    @Override
    public UmsDictionaryItem getUmsDictionaryItemById(String id){
        return umsDictionaryItemService.findOne(id);
    }

    @Override
    public UmsDictionaryItem getUmsDictionaryItemByCodeAndType(Integer code, UmsDictionaryType type) {
        return umsDictionaryItemService.getUmsDictionaryTypeByCode(code, type);
    }

    @Override
    public Page<UmsDictionaryItem> listPage(Searchable searchable) {
        return umsDictionaryItemService.findAll(searchable);
    }

    @Override
    public List<UmsDictionaryItem> list(Searchable searchable) {
        return umsDictionaryItemService.findAllWithNoPageNoSort(searchable);
    }
}
