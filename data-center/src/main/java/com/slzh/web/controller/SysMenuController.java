package com.slzh.web.controller;


import com.slzh.model.SysMenu;
import com.slzh.model.http.HttpResult;
import com.slzh.service.SysMenuService;
import com.slzh.service.SysUserService;
import com.slzh.utils.login.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 菜单控制器
 * @author lanb
 * @date Jan 13, 2019
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController {

	@Autowired
	private SysMenuService sysMenuService;

	@Autowired
	private SysUserService sysUserService;

	public static Map<String, String> params = null;

	/**
	 * 获取本人所有菜单
	 * @return
	 */
	@GetMapping(value="/findMenuTree")
	public HttpResult findMenuTree(){

		Long userId= SecurityUtils.getUserId(sysUserService);
		return HttpResult.ok(sysMenuService.findMenuTree(userId,1));
	}

	@GetMapping("/listAllMenuAuthority")
	public HttpResult listAllMenuAuthority() {
		return HttpResult.ok(sysMenuService.listAllMenuAuthority());
	}
	@GetMapping("/findAllMenus")
	public HttpResult findAllMenus() {
		return HttpResult.ok(sysMenuService.findAllMenus());
	}

	@GetMapping("/getHaveFunctionRoleIds")
	public HttpResult getHaveFunctionRoleIds() {
		return HttpResult.ok(sysMenuService.getHaveFunctionRoleIds());
	}
	/**
	 * 添加或修改菜单
	 * @return
	 */
//	@PostMapping(value="/save")
//	public HttpResult saveMenu(@RequestBody SysMenu menu){
//		return HttpResult.ok(sysMenuService.save(menu));
//	}
	@GetMapping("/getAllApplicationInfo")
	public HttpResult getAllApplicationInfo() {
		return HttpResult.ok(sysMenuService.getAllApplicationInfo());
	}


}
