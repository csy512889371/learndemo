package com.ctoedu.demo.facade.auth.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ctoedu.demo.facade.auth.domain.Authorization;
import com.ctoedu.demo.facade.auth.entity.UmsAcl;
import com.ctoedu.demo.facade.user.entity.UmsUser;

/**
 *
 * Date:2016年11月23日 下午1:44:09
 * Version:1.0
 */
public interface UmsAuthFacade {
    /**
     * 获取用户所拥有的权限集合
     * @param user
     * @return
     */
    public Set<String> findStringPermissions(UmsUser user);
    
    /**
     * 对主体授权
     * @param pid
     * @param ptype
     * @param rids
     * @param rtype
     * @param map
     */
    public void addPrincipalAcl(String pid, String ptype, String[] rids, String rtype, Map<String, String[]> map);
    
    public void addPrincipalAcl(String pid, String ptype, String rid, String rtype);
    
    public void deletePrincipalAcl(String pid, String ptype, String rid, String rtype);

    /**
     * 根据主体id、主体类型、资源类型获取权限信息
     * @param pid
     * @param ptype
     * @param rtype
     * @return
     */
    List<UmsAcl> findAcl(String pid, String ptype, String rtype);
    
    List<UmsAcl> findAclByP(String pid, String ptype);
    
    Authorization auth(String path, String appSn, String token);
    
    void forbiddenPrincipalAcl(String pid, String ptype, String rid, String rtype);

    void deleteForbiddenPrincipalAcl(String pid, String ptype, String rid, String rtype);
}
