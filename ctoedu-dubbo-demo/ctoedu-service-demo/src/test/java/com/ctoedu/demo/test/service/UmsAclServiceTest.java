package com.ctoedu.demo.test.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ctoedu.demo.core.auth.service.UmsAclService;
import com.ctoedu.demo.test.base.BaseTest;

/**
 *  2017/5/22.
 * Version:1.0
 */
public class UmsAclServiceTest extends BaseTest {

    @Autowired
    UmsAclService umsAclService;
//    @org.junit.Test
//    public void getAclTest(){
//        UmsAcl umsAcl = new UmsAcl();
//        umsAcl.setAclState(0);
//        umsAcl.setPid("4028524259aa70650159b0cfb0d20225");
//        umsAcl.setPtype("ORG");
//        umsAcl.setRid("4028524259aa70650159b0cfb0d20225");
//        umsAcl.setRtype("MENU");
//        umsAclService.save(umsAcl);
//        UmsAcl acl = umsAclService.getAcl(umsAcl.getPid(),umsAcl.getPtype(),umsAcl.getRid(),umsAcl.getRtype());
//        assertEquals(acl, umsAcl);
//    }
    
//    @org.junit.Test
//    public void add(){
//    	umsAclService.addPrincipalAcl("297e06725a081427015a08147f3a0001", Principal.PRINCIPAL_ORG, new String[0], Resource.RESOURCE_MENU, null);
//    	List<UmsAcl> acls = umsAclService.getAcl("297e06725a081427015a08147f3a0001", Principal.PRINCIPAL_ORG, Resource.RESOURCE_MENU);
//    	System.out.println(acls.size());
//    }
}
