package com.ctoedu.demo.core.dict.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctoedu.common.service.BaseService;
import com.ctoedu.demo.core.dict.repository.UmsDictionaryItemRepository;
import com.ctoedu.demo.facade.dict.entity.UmsDictionaryItem;
import com.ctoedu.demo.facade.dict.entity.UmsDictionaryType;
import com.ctoedu.demo.facade.dict.exception.DictionaryItemCodeExistsException;

/**
 * Created by Administrator on 2017/10/9.
 */
@Service
public class UmsDictionaryItemService extends BaseService<UmsDictionaryItem,String> {
    @Autowired
    private UmsDictionaryItemRepository umsDictionaryItemRepository;

    public UmsDictionaryItem saveUmsDictionaryItem(UmsDictionaryItem item){
        if(getUmsDictionaryTypeByCode(item.getCode(), item.getType())!=null){
            throw new DictionaryItemCodeExistsException();
        }
        return save(item);
    }

    public UmsDictionaryItem getUmsDictionaryTypeByCode(Integer code, UmsDictionaryType type) {
        return umsDictionaryItemRepository.findByCodeAndType(code, type);
    }
}
