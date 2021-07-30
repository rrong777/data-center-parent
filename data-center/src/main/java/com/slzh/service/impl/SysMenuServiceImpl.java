package com.slzh.service.impl;


import com.slzh.dao.PortalLabelMapper;
import com.slzh.dao.SysMenuMapper;
import com.slzh.model.SysFunction;
import com.slzh.model.SysMenu;
import com.slzh.model.desktop.PortalLabel;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.MybatisPageHelper;
import com.slzh.model.page.PageRequest;
import com.slzh.model.page.PageResult;

import com.slzh.service.SysMenuService;
import com.slzh.utils.login.SecurityUtils;
import io.jsonwebtoken.lang.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysMenuServiceImpl implements SysMenuService {


    private static final Logger log = LoggerFactory.getLogger(SysMenuServiceImpl.class);

    private static List<SysMenu> ALL_MENU_AUTHORITY= new ArrayList();

    @Autowired
    private SysMenuMapper sysMenuMapper;




    @Override
    public List<SysMenu> findAllMenus() {
        // 加工function对象，让function对象 中存 有这个function的角色idList
        List<Map<String, Object>> functionRoleIdsMap = sysMenuMapper.listAllFunctionRoleMapping();
        Map<String, Set<Integer>> funcAndHasFuncRoleIdsMap = new HashMap<>();
        for(Map<String, Object> temp : functionRoleIdsMap) {
            Integer roleId = (Integer)temp.get("role_id");
            String perms = (String) temp.get("perms");
            List<String> permList = Arrays.asList(perms.split(",").clone());
            for(String functionId: permList) {
                Set<Integer> roleIdSet = funcAndHasFuncRoleIdsMap.get(functionId);
                if(roleIdSet == null) {
                    roleIdSet = new HashSet<>();
                    funcAndHasFuncRoleIdsMap.put(functionId, roleIdSet);
                }
                roleIdSet.add(roleId);
            }
        }

        List<SysMenu> sysMenus = sysMenuMapper.listAllMenus();
        for(SysMenu menu : sysMenus) {
            if(!Collections.isEmpty(menu.getSysFunctionList())) {
                List<SysFunction> sysFunctionList = menu.getSysFunctionList();
                for(SysFunction sysFunction: sysFunctionList) {
                    String functionId = sysFunction.getId().toString();
                    sysFunction.setHasFunctionRoleIds(funcAndHasFuncRoleIdsMap.get(functionId));
                }
            }
        }
        return sysMenus;
    }

    @Override
    public List<SysMenu> listAllMenuAuthority() {
        return sysMenuMapper.listAllMenuAuthority();
    }

    @Override
    public Map<String, Object> getHaveFunctionRoleIds() {
//        List<Map<String, Object>> menuInfo = sysMenuMapper.listAllMenuInfo();
        List<Map<String, Object>> functionRoleIdsMap = sysMenuMapper.listAllFunctionRoleMapping();
        Map<String, Object> funcAndHasFuncRoleIdsMap = new HashMap<>();
        for(Map<String, Object> temp : functionRoleIdsMap) {
            Integer roleId = (Integer)temp.get("role_id");
            String perms = (String) temp.get("perms");
            List<String> permList = Arrays.asList(perms.split(",").clone());
            for(String functionId: permList) {
                Set<Integer> roleIdSet = (Set<Integer>)funcAndHasFuncRoleIdsMap.get(functionId);
                if(roleIdSet == null) {
                    roleIdSet = new HashSet<>();
                    funcAndHasFuncRoleIdsMap.put(functionId, roleIdSet);
                }
                roleIdSet.add(roleId);
            }
        }
        List<Map<String, String>> haveMenuRoleIds = sysMenuMapper.getHaveMenuRoleIds();

        for (Map<String, String> temp : haveMenuRoleIds) {
            String roleIds = temp.get("roleIds");
            List<Integer> splitList = Arrays.asList(stringToIntegerArr(roleIds));

            funcAndHasFuncRoleIdsMap.put(temp.get("name"), splitList);
        }

        // 俊杰说不用 返回菜单所拥有的功能id 返回拥有菜单的角色id即可
//        for(Map<String, Object> temp: menuInfo) {
//            String functionIdStr = (String)temp.get("functionIdStr");
//            if(!StringUtils.isBlank(functionIdStr)) {
//                List<String> menuHaveFunctionId =  Arrays.asList(functionIdStr.split(",").clone());
//                funcAndHasFuncRoleIdsMap.put((String) temp.get("menuName"), menuHaveFunctionId);
//            }
//        }
        return funcAndHasFuncRoleIdsMap;
    }

    public Integer[] stringToIntegerArr(String arrStr) {
        String[] arrs = arrStr.split(",");
        Integer[] ints = new Integer[arrs.length];
        for (int i = 0; i < arrs.length; i++) {
            ints[i] = Integer.parseInt(arrs[i]);
        }
        return ints;
    }
    @Override
    public List<SysMenu> getAllMenuAuthority() {
        if(!Collections.isEmpty(ALL_MENU_AUTHORITY)) {
            return ALL_MENU_AUTHORITY;
        }
        synchronized (ALL_MENU_AUTHORITY) {
            if(!Collections.isEmpty(ALL_MENU_AUTHORITY)) {
                return ALL_MENU_AUTHORITY;
            }
            ALL_MENU_AUTHORITY = listAllMenuAuthority();
        }
        return ALL_MENU_AUTHORITY;
    }

    /**
     * 获取本人所有菜单
     * @param userId
     * @param menuType
     * @return
     */
    @Override
    public List<SysMenu> findMenuTree(Long userId, Integer menuType) {
        // SysMenus为当前用户所拥有的所有的最高级菜单（parentId为空或者为0）
        List<SysMenu> sysMenus = new ArrayList<>();
        List<SysMenu> menus = null;
        // 加载当前用户的所有角色
        List<String> roleList = SecurityUtils.getRoles();
        // 根据角色加载当前用户的所有菜单
        if(roleList.contains("ADMIN") || roleList.contains("SUPER")) {
            menus = sysMenuMapper.listAllMenus();
        } else {
            menus = sysMenuMapper.listMenusByUserId(userId);
        }

        for (SysMenu menu : menus) {
            if (menu.getParentId() == null || menu.getParentId() == 0) {
                menu.setLevel(0);
                if(!exists(sysMenus, menu)) {
                    sysMenus.add(menu);
                }
            }
        }

        sysMenus.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
        findChildren(sysMenus, menus, menuType);
        // 返回设置好child的顶级菜单数组
        return sysMenus;
    }

    @Override
    public int save(SysMenu record) {
        if (record.getId() == null || record.getId() == 0) {
            return sysMenuMapper.insertSelective(record);
        }
        if (record.getParentId() == null) {
            record.setParentId(0L);
        }
        return sysMenuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysMenu record) {
        return sysMenuMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysMenu> records) {
        for (SysMenu record : records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public SysMenu findById(Long id) {
        return sysMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MybatisPageHelper.findPage(pageRequest, sysMenuMapper);
    }

    List<SysMenu> findByUser(String userName) {

        List<SysMenu> menus = new ArrayList<>();
        return menus;
    }

    @Override
    public List<Map<String, Object>> getAllApplicationInfo() {
        return sysMenuMapper.getAllApplicationInfo();
    }

    private void findChildren(List<SysMenu> SysMenus, List<SysMenu> menus, Integer menuType) {
        for (SysMenu SysMenu : SysMenus) {
            List<SysMenu> children = new ArrayList<>();
            for (SysMenu menu : menus) {
                if (menuType == 1 && menu.getType() == 2) {
                    // 如果是获取类型不需要按钮，且菜单类型是按钮的，直接过滤掉
                    continue;
                }
                // 嵌套循环，外层循环sysMenu和menus的parentId一个个进行比较，如果比对上了
                if (SysMenu.getId() != null && SysMenu.getId().equals(menu.getParentId())) {
                    // 设置子菜单的父菜单名
                    menu.setParentName(SysMenu.getName());
                    // 设置子菜单测层级为父菜单的层级+1
                    menu.setLevel(SysMenu.getLevel() + 1);
                    // 判断children中是否有menu  child是一个列表，每个sysmenu都有一个child列表
                    if (!exists(children, menu)) {
                        children.add(menu);
                    }
                }
            }
            SysMenu.setChildren(children);
            children.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
            // 递归调用 判断child是否还有child
            findChildren(children, menus, menuType);
        }
    }

    private boolean exists(List<SysMenu> sysMenus, SysMenu sysMenu) {
        boolean exist = false;
        for (SysMenu menu : sysMenus) {
            if (menu.getId().equals(sysMenu.getId())) {
                exist = true;
            }
        }
        return exist;
    }




}
