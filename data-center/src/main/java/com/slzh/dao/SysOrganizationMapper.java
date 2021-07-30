package com.slzh.dao;

import com.slzh.model.SysOrganization;
import com.slzh.model.SysUser;

import java.util.List;

public interface SysOrganizationMapper {
    List<SysOrganization> get();

    Integer insertOne(SysOrganization sysOrganization);

    Integer update(SysOrganization sysOrganization);

    Integer delete(Integer organizationId);

    Integer clearUserOrganizationId(Integer organizationId);

    List<Integer> getSubOrganizationId(Integer organizationId);

    List<SysUser> getOrganizationPeople(Integer organizationId);
}
