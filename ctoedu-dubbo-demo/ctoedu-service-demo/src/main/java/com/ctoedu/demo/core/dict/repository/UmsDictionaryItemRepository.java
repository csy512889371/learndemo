package com.ctoedu.demo.core.dict.repository;

import com.ctoedu.common.repository.BaseRepository;
import com.ctoedu.demo.facade.dict.entity.UmsDictionaryItem;
import com.ctoedu.demo.facade.dict.entity.UmsDictionaryType;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/10/11.
 */
@Repository
public interface UmsDictionaryItemRepository extends BaseRepository<UmsDictionaryItem,String> {

    public UmsDictionaryItem findByCodeAndType(Integer code, UmsDictionaryType type);

}
