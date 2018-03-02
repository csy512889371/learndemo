package com.ctoedu.demo.facade.dict.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.core.dict.service.UmsDictionaryTypeService;
import com.ctoedu.demo.facade.dict.entity.UmsDictionaryType;
import com.ctoedu.demo.facade.dict.service.UmsDictionaryTypeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */
@Service(retries = 2)
public class UmsDictionaryTypeFacadeImpl implements UmsDictionaryTypeFacade {
    @Autowired
    private UmsDictionaryTypeService umsDictionaryTypeService;
    @Override
    public UmsDictionaryType create(UmsDictionaryType umsDictionaryType) {
        return umsDictionaryTypeService.saveUmsDictionaryType(umsDictionaryType);
    }

    @Override
    public UmsDictionaryType update(UmsDictionaryType umsDictionaryType) {
        return umsDictionaryTypeService.update(umsDictionaryType);
    }

    @Override
    public void delete(String... ids) {
        umsDictionaryTypeService.delete(ids);
    }

    @Override
    public UmsDictionaryType getUmsDictionaryTypeById(String id){
        return umsDictionaryTypeService.findOne(id);
    }

    @Override
    public UmsDictionaryType getUmsDictionaryTypeByCode(String code) {
        return umsDictionaryTypeService.getUmsDictionaryTypeByCode(code);
    }

    @Override
    public Page<UmsDictionaryType> listPage(Searchable searchable) {
        return umsDictionaryTypeService.findAll(searchable);
    }

    @Override
    public List<UmsDictionaryType> list(Searchable searchable) {
        return umsDictionaryTypeService.findAllWithNoPageNoSort(searchable);
    }
}
