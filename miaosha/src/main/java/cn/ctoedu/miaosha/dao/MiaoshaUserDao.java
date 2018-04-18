package cn.ctoedu.miaosha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.ctoedu.miaosha.domain.MiaoshaUser;

@Mapper
public interface MiaoshaUserDao {
	
	@Select("select * from miaosha_user where id = #{id}")
	public MiaoshaUser getById(@Param("id")long id);

	@Update("update miaosha_user set password = #{password} where id = #{id}")
	public void update(MiaoshaUser toBeUpdate);
}
