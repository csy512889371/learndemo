package com.ctoedu.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 *
 * Date:2016年11月23日 下午5:19:53
 * Version:1.0
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class UUIDEntity<ID extends Serializable> extends AbstractEntity<ID> {

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解  
    @GeneratedValue(generator="idGenerator") //使用uuid的生成策略 
    @Column(name="id",length=32)
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
