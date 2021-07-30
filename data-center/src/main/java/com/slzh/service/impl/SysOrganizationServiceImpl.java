package com.slzh.service.impl;

import com.slzh.dao.SysOrganizationMapper;
import com.slzh.model.SysOrganization;
import com.slzh.model.SysUser;
import com.slzh.service.SysOrganizationService;
import io.jsonwebtoken.lang.Collections;
import org.apache.commons.beanutils.BeanMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SysOrganizationServiceImpl implements SysOrganizationService {
    // 组织架构 层级id  比如 id为2的组织是id为1下的子组织 等等 在用户查询的时候需要用到
    private static Map<Integer, List<Integer>> organizaLevelMap;
    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;



    @Override
    public Map<Integer, List<Integer>> getOrganizaLevelMap() {
        Map<Integer, List<Integer>> result = getOrganizationLevelMap();
        return result;
    }

    @Override
    public Integer saveOrUpdate(SysOrganization sysOrganization) {
        if(sysOrganization.getId() == null) {
            sysOrganizationMapper.insertOne(sysOrganization);
        } else {
            sysOrganizationMapper.update(sysOrganization);
        }
        return 1;
    }

    @Override
    public Integer delete(Integer organizationId) throws RuntimeException {
        List<Integer> subOrganizationIds = sysOrganizationMapper.getSubOrganizationId(organizationId);
        if(!Collections.isEmpty(subOrganizationIds)) {
            throw new RuntimeException("当前组织存在子组织架构无法直接删除！");
        }
        sysOrganizationMapper.clearUserOrganizationId(organizationId);
        return sysOrganizationMapper.delete(organizationId);
    }

    @Override
    public List<SysUser> getOrganizationPeople(Integer organizationId) {
        return sysOrganizationMapper.getOrganizationPeople(organizationId);
    }

    @Override
    public Map getTaskRecevier() {
        List<SysOrganization> sysOrganizations = get();
        SysOrganization sysOrganization = sysOrganizations.get(0);
        idSetNull(sysOrganization);
        Map result = resetChildren(sysOrganization);

        return result;
    }

    private Map resetChildren(SysOrganization sysOrganization){
        if(sysOrganization == null) {
            return null;
        }
        Map organization = new BeanMap(sysOrganization);
        if(organization.get("children") == null) {
            return null;
        }
        List childrenPersons = (List) organization.get("childrenPersons");
        List children = (List) organization.get("children");
        if(children != null && children.size() > 0) {
            for(Object temp : children) {
                SysOrganization tempOrganization = (SysOrganization) temp;
                resetChildren(tempOrganization);
            }
        }
        if(childrenPersons != null && childrenPersons.size() > 0) {
            for(Object tempUser : childrenPersons) {
                SysUser sysUser = (SysUser) tempUser;
                sysUser.setLabel(sysUser.getNickname());
            }
            children.addAll(childrenPersons);
        }
        return organization;
    }
    private void idSetNull(SysOrganization sysOrganization) {

        if(!Collections.isEmpty(sysOrganization.getChildren())) {
            for(SysOrganization organization : sysOrganization.getChildren()) {
                idSetNull(organization);
            }
        }
        sysOrganization.setId(null);
//        sysOrganization.getChildren().addAll(sysOrganization.getChildrenPersons());
    }

    @Override
    public List<SysOrganization> get() {
        List<SysOrganization> organizations = sysOrganizationMapper.get();
//
        List<SysOrganization> sysOrganizations = new ArrayList<>();
        for (SysOrganization organization : organizations) {
            if (organization.getParentId() == null || organization.getParentId() == 0) {
                organization.setLevel(0);
                if(!exists(sysOrganizations, organization)) {
                    sysOrganizations.add(organization);
                }
            }
        }
        findChildren(sysOrganizations, organizations);

        return sysOrganizations;
    }
    public void InnerForeach(Map<Integer, List<Integer>> result, Integer parentId, Integer subId) {
        for(Integer i : result.keySet()) {
            List<Integer> tempList = result.get(i);
            if(tempList != null && tempList.contains(parentId)) {
                tempList.add(subId);
            }
        }
    }


    private Map<Integer, List<Integer>> getData() {
        List<SysOrganization> sysOrganizations = sysOrganizationMapper.get();
        Map<Integer, List<Integer>> result = new ConcurrentHashMap<>();
        for(SysOrganization sysOrganization : sysOrganizations) {
            Integer tempId = sysOrganization.getId();
            if(result.get(tempId) == null) {
                result.put(tempId, new ArrayList<>());
            }
            List<Integer> tempList = result.get(tempId);
            tempList.add(tempId);
            InnerForeach(result, sysOrganization.getParentId(), tempId);
        }
        return result;
    }

    private Map<Integer, List<Integer>> getOrganizationLevelMap() {
        if(organizaLevelMap != null) {
            return organizaLevelMap;
        }
        synchronized (organizaLevelMap) {
            if(organizaLevelMap != null) {
                return organizaLevelMap;
            }
            organizaLevelMap = getData();
        }
        return organizaLevelMap;
    }

    private boolean exists(List<SysOrganization> sysOrganizations, SysOrganization sysOrganization) {
        boolean exist = false;
        for (SysOrganization organization : sysOrganizations) {
            if (organization.getId().equals(sysOrganization.getId())) {
                exist = true;
            }
        }
        return exist;
    }
    private void findChildren(List<SysOrganization> sysOrganizations, List<SysOrganization> organizations) {
        for (SysOrganization sysOrganization : sysOrganizations) {
            List<SysOrganization> children = new ArrayList<>();
            for (SysOrganization organization : organizations) {
                // 嵌套循环，外层循环sysMenu和menus的parentId一个个进行比较，如果比对上了
                if (sysOrganization.getId() != null && sysOrganization.getId().equals(organization.getParentId())) {
                    // 设置子菜单的父菜单名
                    organization.setParentName(sysOrganization.getName());
                    // 设置子菜单测层级为父菜单的层级+1
                    organization.setLevel(sysOrganization.getLevel() + 1);
                    // 判断children中是否有menu  child是一个列表，每个sysmenu都有一个child列表
                    if (!exists(children, organization)) {
                        children.add(organization);
                    }
                }
            }
            sysOrganization.setChildren(children);
            // 递归调用 判断child是否还有child
            findChildren(children, organizations);
        }
    }
}
