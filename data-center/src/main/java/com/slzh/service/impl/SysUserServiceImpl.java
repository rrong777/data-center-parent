package com.slzh.service.impl;

import com.slzh.config.security.JwtUserDetails;
import com.slzh.dao.SysOrganizationMapper;
import com.slzh.dao.SysUserMapper;
import com.slzh.model.SysOrganization;
import com.slzh.model.SysUser;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.MybatisPageHelper;
import com.slzh.model.page.PageRequest;
import com.slzh.model.page.PageResult;
import com.slzh.service.SysUserService;
import com.slzh.service.sso.util.SM2Util;
import com.slzh.service.sso.util.Util;
import com.slzh.utils.StringUtils;
import com.slzh.utils.login.PasswordUtils;
import com.slzh.utils.login.SecurityUtils;
import io.jsonwebtoken.lang.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.regex.Pattern;

/**
 * TODO 安全问题，一定要控制号，不要传一个userName旧把密码改了
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    private final static Logger logger = LoggerFactory.getLogger(SysUserService.class);

    private static HashMap<String, SysUser> staticUserCache = new HashMap<>();


    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public SysUser findByNameForSecurity(String name, boolean isLogin) {
        // 登录的时候实时去数据库查询，不从缓存里取
        if (staticUserCache.containsKey(name) && !isLogin) {
            return staticUserCache.get(name);
        } else {
            SysUser su = sysUserMapper.findByNameForSecurity(name);
            if (su == null || su.getUsername() == null) {
                logger.error("搜索用户信息出现问题null," + name);
                throw new UsernameNotFoundException("该用户不存在");
            } else {
                staticUserCache.put(name, su);
            }
            return su;
        }

    }

    @Override
    public SysUser findByIdForSecurity(Long userId) {
        return sysUserMapper.findByIdForSecurity(userId);
    }

    @Override
    public List<String> getUserRolesList(Long userId) {
        return sysUserMapper.getRoleListByDB(userId);
    }


    private void judgeExceptionType(Exception e) throws RuntimeException {
        String errMsg = e.getCause().getMessage();
        if (errMsg.indexOf("Duplicate entry") != -1) {
            if (errMsg.indexOf("user_name") != -1) {
                throw new RuntimeException("已存在的用户名！");
            } else if (errMsg.indexOf("mobile") != -1) {
                throw new RuntimeException("已存在的手机号！");
            }
        } else {
            throw new RuntimeException("修改用户错误！");
        }
    }

    public static void main(String[] args) {
        System.out.println(checkUsername("asd"));
    }

    private static boolean checkUsername(String username) {
        String pattern = "[\\u4e00-\\u9fa5]+";
        // 非中文
        return !Pattern.matches(pattern, username);
    }

    private static boolean checkMobile(String mobile) {
        String pattern = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
                "|(18[0-9])|(19[8,9]))\\d{8}$";
        return Pattern.matches(pattern, mobile);
    }

    private static boolean checkEmail(String email) {
        String pattern = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return Pattern.matches(pattern, email);
    }

    private static boolean checkGender(String gender) {
        String pattern = "[/^男$|^女&/]";
        return Pattern.matches(pattern, gender);
    }

    /**
     * 检查敏感属性
     *
     * @param username
     * @param password
     * @throws RuntimeException
     */
    private void checkSensitive(String username, String nickname, String policeId, String password, String mobile) throws RuntimeException {
        if (StringUtils.isEmpty(username)) {
            throw new RuntimeException("用户名为必填项！");
        }
        if (StringUtils.isEmpty(nickname)) {
            throw new RuntimeException("姓名为必填项！");
        }
        if (StringUtils.isEmpty(policeId)) {
            throw new RuntimeException("警员编号为为必填项！");
        }
        if (StringUtils.isEmpty(password)) {
            throw new RuntimeException("用户密码为必填项！");
        }
        if (StringUtils.isEmpty(mobile)) {
            throw new RuntimeException("用户手机号码为必填项！");
        }
        // 新增用户名和密码为必须验证的
        if (!checkUsername(username)) {
            throw new RuntimeException("用户名为非中文!");
        }
        if (!checkUsername(password)) {
            throw new RuntimeException("密码为非中文!");
        }

    }

    /**
     * 检查一般属性
     *
     * @param
     * @return
     * @throws RuntimeException
     */
    private void checkGeneralProperties(String gender, String email, String phone) throws RuntimeException {
        if (gender != null && !checkGender(gender)) {
            throw new RuntimeException("性别不合法!");
        }
        if (phone != null && !checkMobile(phone)) {
            throw new RuntimeException("手机号不合法!");
        }
        if (email != null && !checkEmail(email)) {
            throw new RuntimeException("邮箱不合法!");
        }
    }

    @Override
    public Map<String, String> getRolesByUsername(String username) {
        return sysUserMapper.getRolesByUsername(username);
    }

    /**
     * 设置用户角色
     *
     * @param userId
     * @param roleList
     * @return
     */
    public Integer setUserRole(Long userId, List<Integer> roleList) throws RuntimeException {
        List<String> currentUserRole = SecurityUtils.getRoles();
        // 要修改的用户的角色权限
        List<String> alterUserRole = sysUserService.getUserRolesList(userId);
        if (alterUserRole != null && alterUserRole.contains("SUPER")) {
            throw new RuntimeException("无法修改超管用户的角色权限！");
        }
        if (alterUserRole != null && alterUserRole.contains("ADMIN") && !currentUserRole.contains("SUPER")) {
            throw new RuntimeException("非超管用户无法修改管理员用户角色！");
        }
        if (roleList.contains(1)) {
            throw new RuntimeException("无法对用户进行超管赋权！");
        }
        if (roleList.contains(2) && !(currentUserRole != null && currentUserRole.contains("SUPER"))) {
            throw new RuntimeException("非超管用户无法对用户进行管理员赋权！");
        }
        sysUserMapper.deleteUserRoleMapping(userId);
        if (userId == null || roleList == null || roleList.size() == 0) {
            return null;
        }
        return sysUserMapper.setUserRole(userId, roleList);
    }

    public Integer alterUserPasw(SysUser sysUser) throws RuntimeException {
        // 要修改的用户的权限
        List<String> alterUserRoles = sysUserMapper.getRoleListByDB(sysUser.getId());
        List<String> currentUserRoles = SecurityUtils.getRoles();
        if (alterUserRoles.contains("SUPER")) {
            throw new RuntimeException("无法修改超管密码！");
        }
        Long currentUserId = SecurityUtils.getUserId(sysUserService);
        // 修改的用户包含管理员权限 并且当前用户非超管 且 当前用户不是要修改密码的管理员用户
        if (alterUserRoles.contains("ADMIN") && !currentUserRoles.contains("SUPER") && !currentUserId.equals(sysUser.getId())) {
            throw new RuntimeException("管理员密码只能由自己或者超管进行修改！");
        }

        encodeOutPassword(sysUser);
        String salt = PasswordUtils.getSalt();
        String password = PasswordUtils.encode(sysUser.getPassword(), salt);
        sysUser.setSalt(salt);
        sysUser.setPassword(password);
        return sysUserMapper.alterUserPasw(sysUser);
    }


    @Override
    public Integer alterUserPaswByAdmin(SysUser sysUser) throws RuntimeException {
        // 要修改的用户的权限
        List<String> alterUserRoles = sysUserMapper.getRoleListByDB(sysUser.getId());
        List<String> currentUserRoles = SecurityUtils.getRoles();
        if (alterUserRoles.contains("SUPER")) {
            throw new RuntimeException("无法修改超管密码！");
        }
        Long currentUserId = SecurityUtils.getUserId(sysUserService);
        // 修改的用户包含管理员权限 并且当前用户非超管 且 当前用户不是要修改密码的管理员用户
        if (alterUserRoles.contains("ADMIN") && !currentUserRoles.contains("SUPER") && !currentUserId.equals(sysUser.getId())) {
            throw new RuntimeException("管理员密码只能由自己或者超管进行修改！");
        }

        encodeOutPassword(sysUser);
        String salt = PasswordUtils.getSalt();
        String password = PasswordUtils.encode(sysUser.getPassword(), salt);
        sysUser.setSalt(salt);
        sysUser.setPassword(password);
        return sysUserMapper.alterUserPasw(sysUser);
    }


    @Override
    public Integer delete(long userId) throws RuntimeException {
        List<String> deleteUserRoles = sysUserMapper.getRoleListByDB(userId);
        if (deleteUserRoles.contains("SUPER")) {
            throw new RuntimeException("无法删除超管！");
        }
        List<String> currentUserRoles = SecurityUtils.getRoles();
        if (deleteUserRoles.contains("ADMIN") && !currentUserRoles.contains("SUPER")) {
            throw new RuntimeException("管理员只能由超管进行删除!");
        }
        if (!currentUserRoles.contains("ADMIN")) {
            throw new RuntimeException("只有管理员才具有删除权限！");
        }
        return sysUserMapper.delete(userId);
    }

    @Override
    public Integer batchAlterUserOrganization(Map<String, Object> params) throws Exception {
        List<Integer> userIds = null;
        Integer organizationId = null;

        try {
            userIds = (List<Integer>) params.get("userIds");
            organizationId = (Integer) params.get("organizationId");
            if (Collections.isEmpty(userIds) || organizationId == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("请检查必要参数userIds和organizationId!");
        }
        return sysUserMapper.batchAlterUserOrganization(userIds, organizationId);
    }

    @Override
    public String alterUserStatus(Map<String, Object> params) throws RuntimeException {
        List<Integer> userIds = null;
        if (params.get("userIds") != null) {
            userIds = (List<Integer>) params.get("userIds");
        }

        List<Integer> roleIds = sysUserMapper.findRoleIdsByList(userIds);
        if (roleIds.contains(1) || roleIds.contains(2)) {
            throw new RuntimeException("批量操作中涉及管理员用户！修改失败！");
        }
        Integer status = params.get("status") == null ? 1 : (Integer) params.get("status");
        sysUserMapper.batchAlterStatus(userIds, status);
        return null;
    }


    @Override
    @Transactional
    public Long saveOrUpdate(SysUser sysUser) throws RuntimeException {

        // 调用这个方法的用户权限
        List<String> userRoles = SecurityUtils.getRoles();
        boolean isUpdate = (sysUser.getId() == null ? false : true);
        // 修改和新增都可以
        this.checkGeneralProperties(sysUser.getGender(), sysUser.getEmail(), sysUser.getMobile());
        // 新增

        if (sysUser.getId() == null) {
            // 检查关键属性
            this.checkSensitive(sysUser.getUsername(), sysUser.getNickname(), sysUser.getPoliceId(), sysUser.getPassword(), sysUser.getMobile());
            // 新增给出默认组织
            if (sysUser.getOrganizationId() == null) {
                sysUser.setOrganizationId(2);
            }
            encodeOutPassword(sysUser);
            this.resetPasw(sysUser);
            try {
                sysUserMapper.insertOne(sysUser);
                if (!Collections.isEmpty(sysUser.getRoleList())) {
                    setUserRole(sysUser.getId(), sysUser.getRoleList());
                }
                return sysUser.getId();
            } catch (DuplicateKeyException e) {
                judgeExceptionType(e);
            }
            // 修改
        } else {
            // 无法修改超管，管理员必须超管和自己才可以修改
            this.checkUserAuthority(sysUser, userRoles, sysUserService);
            encodeOutPassword(sysUser);
            try {
                sysUserMapper.update(sysUser);
                if (!Collections.isEmpty(sysUser.getRoleList())) {
                    setUserRole(sysUser.getId(), sysUser.getRoleList());
                }
            } catch (DuplicateKeyException e) {
                judgeExceptionType(e);
            }
        }
        return 1L;
    }

    void encodeOutPassword(SysUser sysUser) {
        try {
            if (!StringUtils.isBlank(sysUser.getPassword())) {
                String encode = SM2Util.encrypt(Util.hexToByte(SM2Util.EXTERNAL_PUBLIC_KEY), sysUser.getPassword().getBytes());
                sysUser.setOutPassword(encode);
                String decode = new String(SM2Util.decrypt(Util.hexToByte(SM2Util.INTERNAL_PRIVATE_KEY), Util.hexToByte(encode)));
            }
        } catch (Exception e) {
            logger.info("加密明文密码报错：{}");
            e.printStackTrace();
        }
    }


    @Override
    public void decodeOutPassword(SysUser sysUser) {
        try {
            if (!StringUtils.isBlank(sysUser.getOutPassword())) {
                String decode = new String(SM2Util.decrypt(Util.hexToByte(SM2Util.INTERNAL_PRIVATE_KEY), Util.hexToByte(sysUser.getOutPassword())));
                sysUser.setPassword(decode);
            }
        } catch (Exception e) {
            logger.info("加密明文密码报错：{}");
            e.printStackTrace();
        }
    }

    /**
     * 判断原来用户的权限是否可以修改
     *
     * @param sysUser
     * @throws Exception
     */
    private void checkUserAuthority(SysUser sysUser, List<String> userRoles, SysUserService sysUserService) throws RuntimeException {
        // 要修改的用户权限
        List<String> alterUserRole = sysUserMapper.getRoleListByDB(sysUser.getId());
        if (alterUserRole.contains("SUPER")) {
            throw new RuntimeException("无法修改超级管理员!");
        }
        if (alterUserRole.contains("ADMIN")) {
            Long userId = SecurityUtils.getUserId(sysUserService);
            // 管理员修改自己
            if (sysUser.getId().equals(userId)) {
                return;
            }
            if (!userRoles.contains("SUPER")) {
                throw new RuntimeException("管理员用户需要超管或者自身进行修改！");
            }
        }
    }

    private void resetPasw(SysUser sysUser) {
        String rawPassword = sysUser.getPassword();
        String salt = PasswordUtils.getSalt();
        String encPassword = PasswordUtils.encode(rawPassword, salt);
        sysUser.setSalt(salt);
        sysUser.setPassword(encPassword);
    }

    @Autowired
    SysOrganizationMapper sysOrganizationMapper;

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Map<String, Object> params = pageRequest.getParams();
        if (params.get("organizationIds") != null) {
            List<Integer> oIds = (List<Integer>) params.get("organizationIds");
            if (!CollectionUtils.isEmpty(oIds)) {
                getAllOId(oIds);
            }
        }
        PageResult pageResult = MybatisPageHelper.findPage(pageRequest, sysUserMapper, "findPage", params);
        return pageResult;
    }

    void getAllOId(List<Integer> oIds) {
        List<SysOrganization> organizations = sysOrganizationMapper.get();
        List<Integer> childIds = new ArrayList<>();
        List<Integer> fatherIds = new ArrayList<>();
        fatherIds.addAll(oIds);
        do {
            for (SysOrganization organization : organizations) {
                if (fatherIds.contains(organization.getParentId().intValue())) {
                    oIds.add(organization.getId());
                    childIds.add(organization.getId());
                }

            }
            fatherIds.clear();
            fatherIds.addAll(childIds);
            childIds.clear();
        } while (!CollectionUtils.isEmpty(fatherIds));
    }


    @Override
    public JwtUserDetails getUserInfo() {
        JwtUserDetails user = (JwtUserDetails) SecurityUtils.getAuthentication().getPrincipal();
        Map<String, String> roleAndOrganization = sysUserService.getRolesByUsername(user.getUsername());
        if (roleAndOrganization != null) {
            String roles = roleAndOrganization.get("roles");
            if (roles != null) {
                user.setRole(Arrays.asList(roles.split(",").clone()));
            }
            user.setOrganization(roleAndOrganization.get("name"));
        }
        return user;
    }

    @Override
    public List<String> getUserAccessUrls(Long userId) {
        return sysUserMapper.getUserAccessUrls(userId);
    }
}
