package com.slzh.service;




import com.slzh.model.SysMenu;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 *
 * @author lanb
 * @date Jan 13, 2019
 */
public interface SysMenuService extends CurdService<SysMenu> {

    List<SysMenu> findMenuTree(Long userId, Integer menuType);
    List<SysMenu> findAllMenus();
    List<SysMenu> listAllMenuAuthority();
    List<SysMenu> getAllMenuAuthority();
    Map<String, Object> getHaveFunctionRoleIds();

    List<Map<String, Object>> getAllApplicationInfo();
}
