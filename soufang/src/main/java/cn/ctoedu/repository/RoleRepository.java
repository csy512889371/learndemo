package cn.ctoedu.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cn.ctoedu.entity.Role;

/**
 * 角色数据DAO
 *
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
    List<Role> findRolesByUserId(Long userId);
}
