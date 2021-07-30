package com.slzh.service;

import com.slzh.model.SysOrganization;
import com.slzh.model.SysUser;

import java.util.List;
import java.util.Map;

public interface SysOrganizationService {
    List<SysOrganization> get();
    Map<Integer, List<Integer>> getOrganizaLevelMap();
    Integer saveOrUpdate(SysOrganization sysOrganization);
    Integer delete(Integer organizationId);

    List<SysUser> getOrganizationPeople(Integer organizationId);

    Map getTaskRecevier();
}
