package com.ctoedu.demo.core.dict.repository;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.dict.entity.UmsDictionaryType;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/10/11.
 */
@Repository
public interface UmsDictionaryTypeRepository extends BaseRepository<UmsDictionaryType,String> {

    public UmsDictionaryType findByCode(String code);

}
