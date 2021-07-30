package com.slzh.service.sso;



import com.slzh.dao.AppCenterMapper;
import com.slzh.dao.SysUserMapper;
import com.slzh.model.SysUser;
import com.slzh.model.sso.AppCenter;
import com.slzh.model.sso.SysUserInfoVO;
import com.slzh.service.SysUserService;
import com.slzh.service.sso.util.RSAUtils;
import com.slzh.service.sso.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 单点登录-自定义serviceImpl
 */
@Service
public class SSOInfoCustomService {

    private final static Logger log = LoggerFactory.getLogger(SSOInfoCustomService.class);

    /**
     * 注册信息-自定义service
     */
    @Autowired
    private AppCenterMapper appCenterMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    SysUserMapper sysUserMapper;

    /**
     * 单点登录-校验验证码
     *
     * @param licenseCode 认证码
     * @param ipAddr      ip
     * @return 用户信息
     * @author chenzj
     */
    public SysUserInfoVO verifyLicenseCode(String licenseCode, String ipAddr, String token) {
        // 获取注册应用信息
        AppCenter appCenter = appCenterMapper.selectRegisterInfoByLience(licenseCode);
        if (appCenter == null) {
            log.error("licenseCode未找到对应的注册子平台，licenseCode:{}", licenseCode);
            throw new RuntimeException("认证码非法");
        }
        // 根据认证码生成内部认证码
        String generateInnerLicenseCode = generateInnerLicenseCode(appCenter.getApplicationType().toString(), licenseCode, ipAddr);
        // 内部认证码比对
        checkInnerLicenseCode(generateInnerLicenseCode, appCenter.getInnerLicenseCode());
        // 获取用户信息
        SysUserInfoVO sysUserInfoVO = getSysUserInfoVO(token);
        return sysUserInfoVO;
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 用户信息
     * @author chenzj
     */
    private SysUserInfoVO getSysUserInfoVO(String token) {
        // 获取当前登录用户
        String[] strs = token.split("--");
        if (strs.length < 1) {
            throw new RuntimeException("token错误");
        }
        String userName;
        try {
            userName = new String(Util.decryBASE64(strs[0]));
        } catch (Exception e) {
            throw new RuntimeException("token错误");
        }
        SysUser user = sysUserMapper.findByNameForSecurity(userName);
        if (user == null) {
            throw new RuntimeException("token获取不到用户");
        }
        SysUserInfoVO sysUserInfoVO = new SysUserInfoVO();
        sysUserInfoVO.setUserId(user.getId());
        sysUserInfoVO.setUserName(user.getUsername());

        sysUserInfoVO.setIdcard(user.getIdCard());
        sysUserInfoVO.setPersonName(user.getNickname());
        sysUserInfoVO.setUserNo(user.getPoliceId());

        return sysUserInfoVO;
    }

    /**
     * 校验内部认证码
     *
     * @param generateInnerLicenseCode 生成的内部认证码
     * @param innerLicenseCode         内部认证码
     * @author chenzj
     */
    private void checkInnerLicenseCode(String generateInnerLicenseCode, String innerLicenseCode) {
        if (!Objects.equals(generateInnerLicenseCode, innerLicenseCode)) {
            log.error("生成的认证码{}不正确", generateInnerLicenseCode);
            throw new RuntimeException("认证码非法");
        }
    }

    /**
     * 生成内部认证码
     *
     * @param applicationType 应用类型
     * @param licenseCode     认证码
     * @param ipAddr          IP
     * @return 生成的内部认证码
     * @author chenzj
     */
    public String generateInnerLicenseCode(String applicationType, String licenseCode, String ipAddr) {
        String generateInnerLicenseCode;
        if (CommonConstant.ApplicationTypeConstant.BS.equals(applicationType)) {
            // BS应用，认证码+IP+内部key
            log.info("IP地址:{}", ipAddr);
            generateInnerLicenseCode = RSAUtils.encipherPrivateKey(licenseCode + ipAddr + CommonConstant.LicenseCodeKey.INTERNAL_KEY,
                    RSAUtils.DEFAULT_PRIVATE_KEY);
        } else {
            // CS应用，认证码+内部key
            generateInnerLicenseCode = RSAUtils.encipherPrivateKey(licenseCode + CommonConstant.LicenseCodeKey.INTERNAL_KEY,
                    RSAUtils.DEFAULT_PRIVATE_KEY);
        }
        return generateInnerLicenseCode;
    }


}
