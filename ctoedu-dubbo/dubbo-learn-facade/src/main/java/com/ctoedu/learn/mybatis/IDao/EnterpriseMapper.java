package com.ctoedu.learn.mybatis.IDao;


import com.ctoedu.learn.mybatis.domain.Enterprise;

public interface EnterpriseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Enterprise record);

    int insertSelective(Enterprise record);

    Enterprise selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Enterprise record);

    int updateByPrimaryKey(Enterprise record);
}