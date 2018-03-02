package com.ctoedu.demo.core.dict.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctoedu.common.service.BaseService;
import com.ctoedu.demo.core.dict.repository.UmsDictionaryTypeRepository;
import com.ctoedu.demo.facade.dict.entity.UmsDictionaryType;
import com.ctoedu.demo.facade.dict.exception.DictionaryTypeCodeExistsException;

/**
 * Created by Administrator on 2017/10/9.
 */
@Service
public class UmsDictionaryTypeService extends BaseService<UmsDictionaryType,String> {
    @Autowired
    private UmsDictionaryTypeRepository umsDictionaryTypeRepository;

    public UmsDictionaryType saveUmsDictionaryType(UmsDictionaryType type){
        if(getUmsDictionaryTypeByCode(type.getCode())!=null){
            throw new DictionaryTypeCodeExistsException();
        }
        return save(type);
    }

    public UmsDictionaryType getUmsDictionaryTypeByCode(String code) {
        return umsDictionaryTypeRepository.findByCode(code);
    }
}
