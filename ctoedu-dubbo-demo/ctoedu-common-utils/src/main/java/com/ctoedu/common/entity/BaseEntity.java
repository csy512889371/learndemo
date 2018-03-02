package com.ctoedu.common.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * <p> 抽象实体基类，提供统一的ID，和相关的基本功能方法
 * 如果是oracle请参考{@link BaseOracleEntity}
 *
 * Date:2016年11月2日 下午1:24:08
 * Version:1.0
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> extends AbstractEntity<ID> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private ID id;

    @Override
    public ID getId() {
        return id;
    }

    @Override
    public void setId(ID id) {
        this.id = id;
    }

}
