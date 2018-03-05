package com.ctoedu.service.Domain.Primary;

import org.apache.ibatis.annotations.*;

import java.util.Map;


/**
 * 用mybatis操作mysql
 *
 *
 */

@Mapper
public interface Teacher3Mapper {
	//使用@Param
    @Select("SELECT * FROM teacher3 WHERE NAME = #{name}")
    Teacher3 findByName(@Param("name") String name);
    
    //接受参数
    @Insert("INSERT INTO teacher3(NAME, AGE) VALUES(#{name}, #{age})")
    int insert(@Param("name") String name, @Param("age") Integer age);
    
    //以map形式接受参数
    @Insert("INSERT INTO teacher3(NAME, AGE) VALUES(#{name,jdbcType=VARCHAR}, #{age,jdbcType=INTEGER})")
    int insertByMap(Map<String, Object> map);
    
    //以实体类形式接受参数
    @Insert("INSERT INTO teacher3(NAME, AGE) VALUES(#{name}, #{age})")
    int insertByTeacher3(Teacher3 teacher3);
    
    //更新
    @Update("update teacher3 set age=30 where id=#{id}")
    void update(@Param("id") Integer id); 
    
    //删除
    @Delete("DELETE FROM teacher3 WHERE id =#{id}")
    void delete(Integer id);
}
