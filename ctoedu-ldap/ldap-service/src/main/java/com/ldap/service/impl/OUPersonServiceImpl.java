package com.ldap.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.SearchControls;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

import com.ldap.model.mapper.HierarchyAttributeMapper;
import com.ldap.model.mapper.PersonAttributeMapper;
import com.ldap.model.rest.OUHierarchyRequest;
import com.ldap.model.rest.OUHierarchyResponse;
import com.ldap.model.rest.OUPersonRequest;
import com.ldap.model.rest.OUPersonResponse;
import com.ldap.model.rest.OURegisterRequest;
import com.ldap.model.util.Md5;
import com.ldap.model.util.StrToJson;
import com.ldap.model.util.StrToJson1;
import com.ldap.service.OUPersonService;

@Service("oUPersonService")
@Slf4j
public class OUPersonServiceImpl implements OUPersonService {

    @Resource(name = "ldapTemplate")
    private LdapTemplate ldapTemplate;

    /**
     * 直接增加人员
     */
    public String addOUPerson(OUPersonRequest oUPersonRequest) {
        try {
            BasicAttribute ba = new BasicAttribute("objectClass");
            ba.add("top");
            ba.add("organizationalPerson");
            ba.add("inetOrgPerson");
            Attributes attrs = new BasicAttributes();
            attrs.put(ba);
            if (oUPersonRequest.getRdn() != null && oUPersonRequest.getRdn().length() > 0) {
                if (oUPersonRequest.getUid() != null && oUPersonRequest.getUid().length() > 0) {
                    attrs.put("uid", oUPersonRequest.getUid());
                    attrs.put("cn", oUPersonRequest.getUid());
                } else {
                    return StrToJson.convert(1, "login_name can not null");
                }
                if (oUPersonRequest.getPassword() != null && oUPersonRequest.getPassword().length() > 0) {
                    attrs.put("userPassword", Md5.getMD5(oUPersonRequest.getPassword()));
                } else {
                    return StrToJson.convert(1, "password can not null");
                }
                if (oUPersonRequest.getSn() != null && oUPersonRequest.getSn().length() > 0) {
                    attrs.put("sn", oUPersonRequest.getSn());
                } else {
                    return StrToJson.convert(1, "username can not null");
                }
                if (oUPersonRequest.getMobile() != null && oUPersonRequest.getMobile().length() > 0) {
                    attrs.put("mobile", oUPersonRequest.getMobile());
                }
                if (oUPersonRequest.getCompany() != null && oUPersonRequest.getCompany().length() > 0) {
                    attrs.put("o", oUPersonRequest.getCompany());
                }
                if (oUPersonRequest.getDepartment() != null && oUPersonRequest.getDepartment().length() > 0) {
                    attrs.put("ou", oUPersonRequest.getDepartment());
                }
                if (oUPersonRequest.getEmployeeType() != null && oUPersonRequest.getEmployeeType().length() > 0) {
                    attrs.put("employeeType", oUPersonRequest.getEmployeeType());
                }
                if (oUPersonRequest.getRegionName() != null && oUPersonRequest.getRegionName().length() > 0) {
                    attrs.put("l", oUPersonRequest.getRegionName());
                }
            } else {
                return StrToJson.convert(1, "rdn can not null");
            }
            ldapTemplate.bind("cn=" + oUPersonRequest.getUid() + ",ou=PersonnelInformation," + oUPersonRequest.getRdn(), null, attrs);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
        return StrToJson.convert(0, "ok");
    }

    /**
     * 删除人员
     */
    public String delOUPerson(String rdn) {
        try {
            if (rdn != null && rdn.length() > 0) {
                ldapTemplate.unbind(rdn);
            } else {
                return StrToJson.convert(1, "rdn can not null");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
        return StrToJson.convert(0, "ok");
    }

    /**
     * 通过注册增加人员
     */
    public String addOURegister(OURegisterRequest oURegisterRequest) {
        try {
            BasicAttribute ba = new BasicAttribute("objectClass");
            ba.add("top");
            ba.add("organizationalPerson");
            ba.add("inetOrgPerson");
            Attributes attrs = new BasicAttributes();
            attrs.put(ba);
            if (oURegisterRequest.getUid() != null && oURegisterRequest.getUid().length() > 0) {
                attrs.put("uid", oURegisterRequest.getUid());
                attrs.put("cn", oURegisterRequest.getUid());
            } else {
                return StrToJson.convert(1, "login_name can not null");
            }
            if (oURegisterRequest.getPassword() != null && oURegisterRequest.getPassword().length() > 0) {
                attrs.put("userPassword", Md5.getMD5(oURegisterRequest.getPassword()));
            } else {
                return StrToJson.convert(1, "password can not null");
            }
            if (oURegisterRequest.getSn() != null && oURegisterRequest.getSn().length() > 0) {
                attrs.put("sn", oURegisterRequest.getSn());
            } else {
                return StrToJson.convert(1, "username can not null");
            }
            if (oURegisterRequest.getMobile() != null && oURegisterRequest.getMobile().length() > 0) {
                attrs.put("mobile", oURegisterRequest.getMobile());
            }
            if (oURegisterRequest.getCompany() != null && oURegisterRequest.getCompany().length() > 0) {
                attrs.put("o", oURegisterRequest.getCompany());
            }
            if (oURegisterRequest.getDepartment() != null && oURegisterRequest.getDepartment().length() > 0) {
                attrs.put("ou", oURegisterRequest.getDepartment());
            }
            if (oURegisterRequest.getEmployeeType() != null && oURegisterRequest.getEmployeeType().length() > 0) {
                attrs.put("employeeType", oURegisterRequest.getEmployeeType());
            }
            if (oURegisterRequest.getRegionName() != null && oURegisterRequest.getRegionName().length() > 0) {
                attrs.put("l", oURegisterRequest.getRegionName());
            }
            ldapTemplate.bind("cn=" + oURegisterRequest.getUid() + ",ou=PersonnelInformation,o=RegistrationCenter", null, attrs);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
        return StrToJson.convert(0, "ok");
    }

    /**
     * 通过登录名和密码查询人员
     */
    public OUPersonResponse getOUPerson(String loginName, String userPassword) {
        List<OUPersonResponse> persons = new ArrayList<OUPersonResponse>();
        OUPersonResponse response = new OUPersonResponse();
        try {
            AndFilter andFilter = new AndFilter();
            andFilter.and(new EqualsFilter("objectclass", "inetOrgPerson"));
            if (loginName != null && loginName.length() > 0) {
                andFilter.and(new EqualsFilter("uid", loginName));
            }
            if (userPassword != null && userPassword.length() > 0) {
                andFilter.and(new EqualsFilter("userPassword", userPassword));
            }
            persons = ldapTemplate.search("", andFilter.encode(), new PersonAttributeMapper());
            if (persons.size() > 0) {
                response = persons.get(0);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
        return response;
    }

    /**
     * 移动某个人员到另一路径下
     */

    public String moveOUPerson(String oldrdn, String newrdn) {
        try {
            DirContextAdapter newContext = new DirContextAdapter(newrdn);
            DirContextOperations oldContext = ldapTemplate.lookupContext(oldrdn);
            NamingEnumeration<? extends Attribute> attrs = oldContext
                    .getAttributes().getAll();
            try {
                while (attrs.hasMore()) {
                    Attribute attr = attrs.next();
                    newContext.getAttributes().put(attr);
                }
            } catch (NamingException e) {
                throw new RuntimeException("remove entry error:" + e.getMessage());
            }
            ldapTemplate.unbind(oldrdn);
            ldapTemplate.bind(newContext);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * 增加层级关系
     */
    public String addOUHierarchy(OUHierarchyRequest oUHierarchyRequest) {
        try {
            BasicAttribute ba = new BasicAttribute("objectClass");
            ba.add("top");
            ba.add("organizationalPerson");
            ba.add("inetOrgPerson");
            Attributes attrs = new BasicAttributes();
            attrs.put(ba);
            if (oUHierarchyRequest.getRdn() != null && oUHierarchyRequest.getRdn().length() > 0) {
                if (oUHierarchyRequest.getCn() != null && oUHierarchyRequest.getCn().length() > 0) {
                    attrs.put("cn", oUHierarchyRequest.getCn());
                } else {
                    return StrToJson.convert(1, "login_name can not null");
                }
                if (oUHierarchyRequest.getSn() != null && oUHierarchyRequest.getSn().length() > 0) {
                    attrs.put("sn", oUHierarchyRequest.getSn());
                } else {
                    return StrToJson.convert(1, "username can not null");
                }
            } else {
                return StrToJson.convert(1, "rdn can not null");
            }
            ldapTemplate.bind("cn=" + oUHierarchyRequest.getCn() + "," + oUHierarchyRequest.getRdn(), null, attrs);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
        return StrToJson.convert(0, "ok");
    }

    /**
     * 根据人员uid返回具体路径
     */
    public String getRdn(String uid) {
        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("uid", uid));
        List result = ldapTemplate.search(DistinguishedName.EMPTY_PATH, andFilter.toString(), new AbstractContextMapper() {
            protected Object doMapFromContext(DirContextOperations ctx) {
                return ctx.getNameInNamespace();
            }
        });
        if (result.size() != 1) {
            throw new RuntimeException("User not found or not unique");
        }
        return StrToJson1.convert((String) result.get(0));
    }

    /**
     * 返回下一级所有人员
     */
    public List<OUHierarchyResponse> getNextOUHierarchy(String rdn) {
        List<OUHierarchyResponse> persons = new ArrayList<OUHierarchyResponse>();
        try {
            AndFilter andFilter = new AndFilter();
            andFilter.and(new EqualsFilter("objectclass", "top"));
            andFilter.and(new EqualsFilter("objectclass", "organizationalPerson"));
            andFilter.and(new EqualsFilter("objectclass", "inetOrgPerson"));
            persons = ldapTemplate.search(rdn, andFilter.encode(), SearchControls.ONELEVEL_SCOPE, new HierarchyAttributeMapper());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
        return persons;
    }

    public String delOUHierarchy(String rdn) {
        try {
            if (rdn != null && rdn.length() > 0) {
                ldapTemplate.unbind(rdn);
            } else {
                return StrToJson.convert(1, "rdn can not null");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
        return StrToJson.convert(0, "ok");
    }
}
