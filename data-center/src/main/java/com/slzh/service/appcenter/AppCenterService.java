package com.slzh.service.appcenter;

import com.slzh.dao.AppCenterMapper;
import com.slzh.dao.DesktopMapper;
import com.slzh.dao.PortalLabelMapper;
import com.slzh.model.desktop.PortalLabel;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.MybatisPageHelper;
import com.slzh.model.page.PageRequest;
import com.slzh.model.page.PageResult;
import com.slzh.model.sso.AppCenter;
import com.slzh.service.SysUserService;
import com.slzh.service.sso.CommonConstant;
import com.slzh.service.sso.util.RSAUtils;
import com.slzh.utils.DateTimeUtils;
import com.slzh.utils.StringUtils;
import com.slzh.utils.login.SecurityUtils;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;


@Service
public class AppCenterService {
    private static final Logger log = LoggerFactory.getLogger(AppCenterService.class);

    @Autowired(required = false)
    private AppCenterMapper appCenterMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DesktopMapper desktopMapper;

    public HttpResult listRegisterInfo(PageRequest pageRequest) {
        Map<String, Object> params = pageRequest.getParams();
        Integer labelId = (Integer) params.get("labelId");
        if (labelId.equals(1)) {
            // 如果是最新，要有时间
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -3);
            String startTime = DateTimeUtils.getDateTime(calendar.getTime());
            params.put("startTime", startTime);
        }
        return HttpResult.ok(MybatisPageHelper.findPage(pageRequest, appCenterMapper, "listRegisterInfo", pageRequest.getParams()));
    }

    public HttpResult getAppByLabel(Map<String, Object> params) {
        Integer labelId = (Integer) params.get("labelId");
        List<AppCenter> result = null;
        // 如果标签是最新，需要时间
        if (labelId.equals(1)) {
            // 如果是最新，要有时间
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -3);
            String startTime = DateTimeUtils.getDateTime(calendar.getTime());
            params.put("startTime", startTime);
        }
        result = appCenterMapper.getAppByLabel(params);
        return HttpResult.ok(result);
    }

    /**
     * targetKey desktop 请求的是桌面
     * favorite 请求的是收藏夹
     * targetId 如果targetKey是destop  targetId就是desktopId 不然就是favoriteId
     *
     * @param pageRequest
     * @return
     */
    public HttpResult findPageByTarget(PageRequest pageRequest) {

        Map<String, Object> params = pageRequest.getParams();
        // targetKey : desktop or favorite
        String targetKey = (String) params.get("targetKey");
        // targetId : desktopId or favoriteId
        String targetId = (String) params.get("targetId");
        try {
            Assert.notNull(targetKey, "缺少必传参数：targetKey");
            Assert.notNull(targetId, "缺少必传参数：targetId");
        } catch (IllegalArgumentException e) {
            return HttpResult.error(e.getMessage());
        }
        // 分页查询  如果有传labelId 就会去join portal_label表和portal_app_label表， 这两张表是把app_center里面的app打上标签。
        // 分页结果
        List<String> roles = SecurityUtils.getRoles();

        if (!CollectionUtils.isEmpty(roles) && !roles.contains("SUPER") && !roles.contains("ADMIN")) {
            params.put("roles", roles);
        } else if (roles == null || CollectionUtils.isEmpty(roles)) {
            roles = new ArrayList<>();
            roles.add("-1");
        }
        PageResult result = MybatisPageHelper.findPage(pageRequest, appCenterMapper, "listRegisterInfo", params);
        List<String> appIdsForQuery = new ArrayList<>();
        for (AppCenter temp : (List<AppCenter>) result.getContent()) {
            String registerInfoId = temp.getRegisterInfoId();
            appIdsForQuery.add(registerInfoId);
        }
        if (!CollectionUtils.isEmpty(appIdsForQuery)) {
            List<AppCenter> labelIds = appCenterMapper.getLabelIdsByAppIds(appIdsForQuery);
            List<AppCenter> menuIds = appCenterMapper.getMenuIdsByAppIds(appIdsForQuery);
            if (menuIds != null || labelIds != null) {
                for (AppCenter app : (List<AppCenter>) result.getContent()) {
                    String appId = app.getAppId();
                    if (labelIds != null) {
                        for (AppCenter temp : labelIds) {
                            String tempAppId = temp.getAppId();
                            if (appId.equals(tempAppId)) {
                                app.setLabelIds(temp.getLabelIds());
                            }
                        }
                    }
                    if (menuIds != null) {
                        for (AppCenter temp : menuIds) {
                            String tempAppId = temp.getAppId();
                            if (appId.equals(tempAppId)) {
                                app.setMenuIds(temp.getMenuIds());
                            }
                        }
                    }
                }
            }
        }


        List<AppCenter> content = (List<AppCenter>) result.getContent();
        // 请求目标（桌面，收藏夹）中的所有appId
        List<String> appIds = this.getAppIdsFromTarget(targetKey, targetId);
        for (AppCenter temp : content) {
            String appId = temp.getAppId();
            if (appIds.contains(appId)) {
                temp.setIsDownload(1);
            }
        }
        return HttpResult.ok(result);
    }

    /**
     * 从目标中（desktop、favorite） 中获取 appIds
     *
     * @return
     */
    private List<String> getAppIdsFromTarget(String targetKey, String targetId) {
        List<String> appIds = null;
        if (targetKey.equals("desktop")) {
            appIds = appCenterMapper.getAppIdByDesktopId(targetId);
        } else {
            appIds = appCenterMapper.getAppIdByFavoriteId(targetId);
        }
        return appIds;
    }

    @Transactional
    public HttpResult addRegisterInfo(AppCenter appCenter) {
        if (StringUtils.isBlank(appCenter.getIpAddress())) {
            return HttpResult.error("ipaddress 不能为空");
        }
        if (StringUtils.isBlank(appCenter.getApplicationName())) {
            return HttpResult.error("应用名称 不能为空");
        }
        if (appCenter.getApplicationType() == null) {
            return HttpResult.error("应用类型不能为空");
        }
        if (StringUtils.isBlank(appCenter.getIcon())) {
            appCenter.setIcon("data:image/png;base64,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" +
                    "pznPvPhwDwJSIPg3D3Mum/K9olAP8BtRoLU2v/Dg0AAAAASUVORK5CYII=");
        }
        String id = generatorId("app");
        appCenter.setRegisterInfoId(id);
        appCenter.setCreateUser(SecurityUtils.getUserId(sysUserService));
        appCenter.setUpdateUser(SecurityUtils.getUserId(sysUserService));
        if (!StringUtils.isBlank(appCenter.getDevelopers())) {
            AppCenter appCenter1 = appCenterMapper.selectRegisterInfoBygetDeveloper(appCenter.getDevelopers());
            if (appCenter1 != null && !StringUtils.isBlank(appCenter1.getLicenseCode()) && !StringUtils.isBlank(appCenter1.getInnerLicenseCode())) {
                appCenter.setLicenseCode(appCenter1.getLicenseCode());
                appCenter.setInnerLicenseCode(appCenter1.getLicenseCode());
            }
        }
        generateLicenseCode(appCenter);
        appCenter.setIsDelete(0);
        appCenter.setIsEnabled(1);

        appCenterMapper.insertSelective(appCenter);
        // 新增标签
        if (appCenter.getLabelIds() != null && appCenter.getLabelIds().size() != 0) {
            if (appCenter.getMenuIds() != null) {
                appCenter.getLabelIds().addAll(appCenter.getMenuIds());

            }
            appCenterMapper.addLabelForApp(appCenter.getRegisterInfoId(), appCenter.getLabelIds());
        }
        return HttpResult.ok();
    }

    private String generatorId(String type) {
        String s = UUID.randomUUID().toString();
        switch (type) {
            case "app":
                return "2" + s;
            case "folder":
                return "3" + s;
            case "favorites":
                return "4" + s;
            default:
                return "1" + s;
        }
    }

    public HttpResult updateRegistorInfo(AppCenter appCenter) {
        appCenter.setLicenseCode(null);
        appCenter.setInnerLicenseCode(null);
        appCenter.setUpdateUser(SecurityUtils.getUserId(sysUserService));
        appCenter.setIsDelete(null);
        appCenterMapper.deleteLabelForApp(appCenter.getRegisterInfoId());
        List<Integer> insertList = new ArrayList<>();
        if (appCenter.getLabelIds() != null && appCenter.getLabelIds().size() != 0) {
            insertList.addAll(appCenter.getLabelIds());
        }
        if (appCenter.getMenuIds() != null && appCenter.getMenuIds().size() != 0) {
            insertList.addAll(appCenter.getMenuIds());
        }
        if (insertList.size() != 0) {
            appCenterMapper.addLabelForApp(appCenter.getRegisterInfoId(), insertList);
        }
        appCenterMapper.updateByPrimaryKeySelective(appCenter);
        return HttpResult.ok(appCenter);
    }


    public HttpResult updateRegistorInfoAndGeneratorLienceKey(AppCenter appCenter) {
        appCenter.setLicenseCode(null);
        appCenter.setInnerLicenseCode(null);
        appCenter.setUpdateUser(SecurityUtils.getUserId(sysUserService));
        appCenter.setIsDelete(null);
        if (!StringUtils.isBlank(appCenter.getDevelopers())) {
            AppCenter appCenter1 = appCenterMapper.selectRegisterInfoBygetDeveloper(appCenter.getDevelopers());
            if (appCenter1 != null && !StringUtils.isBlank(appCenter1.getLicenseCode()) && !StringUtils.isBlank(appCenter1.getInnerLicenseCode())) {
                appCenter.setLicenseCode(appCenter1.getLicenseCode());
                appCenter.setInnerLicenseCode(appCenter1.getLicenseCode());
            }
        }
        generateLicenseCode(appCenter);
        appCenterMapper.deleteLabelForApp(appCenter.getRegisterInfoId());
        List<Integer> insertList = new ArrayList<>();
        if (appCenter.getLabelIds() != null && appCenter.getLabelIds().size() != 0) {
            insertList.addAll(appCenter.getLabelIds());
        }
        if (appCenter.getMenuIds() != null && appCenter.getMenuIds().size() != 0) {
            insertList.addAll(appCenter.getMenuIds());
        }
        if (insertList.size() != 0) {
            appCenterMapper.addLabelForApp(appCenter.getRegisterInfoId(), insertList);
        }
        appCenterMapper.updateByPrimaryKeySelective(appCenter);
        appCenter.setInnerLicenseCode(null);
        return HttpResult.ok(appCenter);
    }

    public HttpResult deleteRegistorInfo(String registerInfoId) {
        //逻辑删除应用中心的app
        AppCenter appCenter = new AppCenter();
        appCenter.setRegisterInfoId(registerInfoId);
        appCenter.setIsDelete(1);
        appCenter.setUpdateUser(SecurityUtils.getUserId(sysUserService));
        appCenterMapper.updateByPrimaryKeySelective(appCenter);


        List<Map<String, Object>> appInfoList = desktopMapper.getAppInfoByAppId(registerInfoId);
        if (!CollectionUtils.isEmpty(appInfoList)) {
            for (Map<String, Object> map : appInfoList) {
                if (map.containsKey("folderSequence") && map.get("folderSequence") != null) {
                    List<Map<String, Object>> needUpdateFolderSequenceAppInfoList = desktopMapper.getNeedUpdateFolderSequenceAppInfo(map.get("desktopId").toString(), map.get("folderId").toString(),
                            Integer.valueOf(map.get("folderSequence").toString()));
                    if (!CollectionUtils.isEmpty(needUpdateFolderSequenceAppInfoList)) {
                        for (Map<String, Object> needUpdateMap : needUpdateFolderSequenceAppInfoList) {
                            needUpdateMap.put("folderSequence", Integer.valueOf(needUpdateMap.get("folderSequence").toString()) - 1);
                        }
                        desktopMapper.insertPortalApp(needUpdateFolderSequenceAppInfoList);
                    }
                } else if (map.containsKey("sequence") && map.get("sequence") != null) {
                    List<Map<String, Object>> needUpdateSequenceAppInfoList = desktopMapper.getNeedUpdateSequenceAppInfo(map.get("desktopId").toString(),
                            Integer.valueOf(map.get("sequence").toString()));
                    if (!CollectionUtils.isEmpty(needUpdateSequenceAppInfoList)) {
                        for (Map<String, Object> needUpdateMap : needUpdateSequenceAppInfoList) {
                            needUpdateMap.put("sequence", Integer.valueOf(needUpdateMap.get("sequence").toString()) - 1);
                        }
                        desktopMapper.insertPortalApp(needUpdateSequenceAppInfoList);
                    }
                }

            }
        }

        List<Map<String, Object>> favoritesAppInfoList = desktopMapper.getFavoritesAppInfoByAppId(registerInfoId);
        if (!CollectionUtils.isEmpty(favoritesAppInfoList)) {
            for (Map<String, Object> map : favoritesAppInfoList) {
                if (map.containsKey("sequence") && map.get("sequence") != null) {
                    List<Map<String, Object>> needUpdateSequenceFavoritesAppInfoList = desktopMapper.getNeedUpdateSequenceFavoritesAppInfo(map.get("favoritesId").toString(),
                            Integer.valueOf(map.get("sequence").toString()));
                    if (!CollectionUtils.isEmpty(needUpdateSequenceFavoritesAppInfoList)) {
                        for (Map<String, Object> needUpdateMap : needUpdateSequenceFavoritesAppInfoList) {
                            needUpdateMap.put("sequence", Integer.valueOf(needUpdateMap.get("sequence").toString()) - 1);
                        }
                        desktopMapper.insertPortalFavoritesApp(needUpdateSequenceFavoritesAppInfoList);
                    }
                }
            }
        }

        //物理删除所有用户的桌面或者收藏夹中该app
        desktopMapper.deleteAppInfoByAppIdForDesktop(registerInfoId);
        desktopMapper.deleteAppInfoByAppIdForFavorites(registerInfoId);
        desktopMapper.deleteAppInfoByAppIdForTool(registerInfoId);
        return HttpResult.ok();
    }

    public static void main(String[] args) {

    }


    public HttpResult download(PageRequest pageRequest) {
        Map params = pageRequest.getParams();
        String desktopId = (String) params.get("desktopId");
        String favoritesId = (String) params.get("favoritesId");
        String appId = (String) params.get("appId");
        //下载app到桌面
        if (desktopId != null) {
            if (appId == null) {
                HttpResult.error("appId为空，下载失败");
            }
            //下载的app在桌面的位置
            int sequence = -1;
            List<Integer> folderSequenceList = desktopMapper.getFolderSequenceByDesktopId(desktopId);
            List<Integer> appSequenceList = desktopMapper.getAppSequenceByDesktopId(desktopId);
            if (!CollectionUtils.isEmpty(folderSequenceList)) {
                sequence = folderSequenceList.get(folderSequenceList.size() - 1);
            }
            if (!CollectionUtils.isEmpty(appSequenceList)) {
                if (appSequenceList.get(appSequenceList.size() - 1) > sequence) {
                    sequence = appSequenceList.get(appSequenceList.size() - 1);
                }
            }
            if (sequence == -1) { //桌面上没有文件夹或app
                sequence = 0;
            } else {
                sequence = sequence + 1;
            }
            //构建app信息
            List<Map<String, Object>> appList = new ArrayList<>();
            Map<String, Object> appMap = new HashMap<>();
            appMap.put("appId", appId);
            appMap.put("desktopId", desktopId);
            appMap.put("sequence", sequence);
            appMap.put("type", "app");
            appList.add(appMap);
            desktopMapper.insertPortalApp(appList);
        } else if (favoritesId != null) { //下载app到收藏夹
            if (appId == null) {
                HttpResult.error("appId为空，下载失败");
            }
            //下载的app在收藏夹的位置
            int sequence = -1;
            List<Integer> favoritesAppSequenceList = desktopMapper.getFavoritesAppSequenceByFavoritesId(desktopId);
            if (!CollectionUtils.isEmpty(favoritesAppSequenceList)) {
                sequence = favoritesAppSequenceList.get(favoritesAppSequenceList.size() - 1);
            }
            if (sequence == -1) { //桌面上没有文件夹或app
                sequence = 0;
            } else {
                sequence = sequence + 1;
            }
            //构建app信息
            List<Map<String, Object>> appList = new ArrayList<>();
            Map<String, Object> appMap = new HashMap<>();
            appMap.put("appId", appId);
            appMap.put("favoritesId", favoritesId);
            appMap.put("sequence", sequence);
            appMap.put("type", "app");
            appList.add(appMap);
            desktopMapper.insertPortalFavoritesApp(appList);
        }
        AppCenter appCenter = appCenterMapper.selectByPrimaryKey(appId);
        if (appCenter != null) {
            appCenter.setDownloadCount(appCenter.getDownloadCount() + 1);
            appCenter.setUpdateUser(SecurityUtils.getUserId(sysUserService));
            appCenterMapper.updateByPrimaryKeySelective(appCenter);
        }
        return HttpResult.ok("下载成功");
    }

    /**
     * 注册信息-生成授权码
     *
     * @param appCenter 注册信息
     * @author chenzj
     */
    public AppCenter generateLicenseCode(AppCenter appCenter) {
        String externalLicenseCode = RSAUtils.encipherPrivateKey(appCenter.getApplicationName() + CommonConstant.LicenseCodeKey.EXTERNAL_KEY,
                RSAUtils.DEFAULT_PRIVATE_KEY);
        if (!StringUtils.isBlank(appCenter.getLicenseCode())) {
            externalLicenseCode = appCenter.getLicenseCode();
        }
        String internalLicenseCode;
        if (CommonConstant.ApplicationTypeConstant.BS.equals(appCenter.getApplicationType().toString())) {
            internalLicenseCode = RSAUtils.encipherPrivateKey(externalLicenseCode + appCenter.getIpAddress() + CommonConstant.LicenseCodeKey.INTERNAL_KEY,
                    RSAUtils.DEFAULT_PRIVATE_KEY);
        } else {
            internalLicenseCode = RSAUtils.encipherPrivateKey(externalLicenseCode + CommonConstant.LicenseCodeKey.INTERNAL_KEY,
                    RSAUtils.DEFAULT_PRIVATE_KEY);
        }
        appCenter.setLicenseCode(externalLicenseCode);
        appCenter.setInnerLicenseCode(internalLicenseCode);
        return appCenter;
    }


    public HttpResult getLabels() {

        List<String> roles = SecurityUtils.getRoles();
        List<String> appIds = new ArrayList<>();
        if (CollectionUtils.isEmpty(roles)) {
            appIds.add("-1");
        } else if (roles.contains("SUPER") || roles.contains("ADMIN")) {
            //超管拥有全部权限
        } else {
            List<String> roleAppIds = appCenterMapper.listAppIdsByRoles(roles);
            appIds.addAll(roleAppIds);
        }
        List<Map<String, Object>> tempRs = appCenterMapper.getLabels();
        List<Map<String, Object>> labels = new ArrayList<>();
        List<Map<String, Object>> menus = new ArrayList<>();
        List<Map<String, Integer>> numsForLabelApp = appCenterMapper.getNumsForLabelApp(appIds);
        Integer countForHot = appCenterMapper.getCountForHot(appIds);
        Integer allCount = appCenterMapper.getAllCount(appIds);
        for (Map<String, Object> temp : tempRs) {
            Integer type = (Integer) temp.get("type");
            Integer labelId = (Integer) temp.get("id");
            if (labelId == 0) {
                temp.put("count", allCount);
            } else if (labelId == 2) {
                temp.put("count", countForHot);
            } else {
                for (Map<String, Integer> tempNums : numsForLabelApp) {
                    Integer tempNumsLabelId = tempNums.get("labelId");
                    if (tempNumsLabelId == labelId) {
                        temp.put("count", tempNums.get("count"));
                    }
                }
            }

            if (type == 1) {
                labels.add(temp);
            } else {
                menus.add(temp);
            }
        }
        sortListMap(labels);
        sortListMap(menus);
        Map<String, List<Map<String, Object>>> res = new HashMap<>();
        res.put("labels", labels);
        res.put("menus", menus);
        return HttpResult.ok(res);
    }

    private void sortListMap(List<Map<String, Object>> list) {
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Integer id1 = (Integer) o1.get("id");
                Integer id2 = (Integer) o2.get("id");
                return id1.compareTo(id2);
            }
        });
    }

    public HttpResult deleteLabel(Integer labelId) {
        appCenterMapper.deleteLabel(labelId);
        return HttpResult.ok();
    }

    public HttpResult updateLabel(PortalLabel portalLabel) {
        if (portalLabel.getId().equals(0) || portalLabel.getId().equals(1) || portalLabel.getId().equals(2)) {
            return HttpResult.error("此菜单为默认菜单，不可修改");
        }
        portalLabelMapper.updateByPrimaryKeySelective(portalLabel);
        return HttpResult.ok();
    }

    public HttpResult addLabel(PortalLabel params) {
        params.setId(null);
//        PortalLabel portalLabel = portalLabelMapper.selectLabelByNameAndType(params.getLabelName(), params.getType());
//        if (portalLabel != null) {
//            throw new RuntimeException("已存在同名标签，新增标签失败");
//        }
        portalLabelMapper.insertSelective(params);
        return HttpResult.ok();
    }


    public HttpResult getCategoryCount(Map request) {
        String keyword = null;
        String label = null;
        if (request.containsKey("keyword") && !request.get("keyword").equals("")) {
            keyword = (String) request.get("keyword");
        }
        if (request.containsKey("label") && !request.get("label").equals("")) {
            label = (String) request.get("label");
        }
        List<Map> categortCountList = new ArrayList<>();
        List<String> appIdList = appCenterMapper.getAppIdByLabel(label);

        List<String> roles = SecurityUtils.getRoles();
        if (CollectionUtils.isEmpty(roles)) {
            appIdList.clear();
        } else if (roles.contains("SUPER") || roles.contains("ADMIN")) {
            //超管拥有全部权限
        } else {
            List<String> roleAppIds = appCenterMapper.listAppIdsByRoles(roles);
            if (CollectionUtils.isEmpty(roleAppIds)) {
                appIdList.clear();
            } else {
                appIdList.retainAll(roleAppIds);
            }

        }

        if (!CollectionUtils.isEmpty(appIdList)) {
            categortCountList = appCenterMapper.getCategoryCount(keyword, appIdList);
            Integer count = appCenterMapper.getAppCountByLabel(label, keyword, appIdList);
            Map total = new HashMap();
            total.put("labelName", "全部");
            total.put("count", count);
            total.put("id", 0);
            if (!CollectionUtils.isEmpty(categortCountList)) {
                categortCountList.add(0, total);
            } else {
                categortCountList.add(total);
            }
        }
        return HttpResult.ok(categortCountList);
    }

    public List<Map<String, Object>> listAppGroupLabel(Map<String, Object> req) {

        List<Map<String, Object>> labels = appCenterMapper.listLables();
        List<AppCenter> apps = appCenterMapper.listAllApplicationInfo(req);
        if (!CollectionUtils.isEmpty(labels)) {
            Map<Integer, List<AppCenter>> groups = new HashMap<>();
            for (Map<String, Object> label : labels) {
                groups.put((Integer) label.get("id"), new ArrayList<AppCenter>());
            }
            List<AppCenter> group0 = groups.get(0);
            for (AppCenter app : apps) {
                group0.add(app);
                List<AppCenter> group = groups.get(app.getLabelId());
                if (group != null) {
                    group.add(app);
                }
            }
            for (Map<String, Object> label : labels) {
                List<AppCenter> group = groups.get((Integer) label.get("id"));
                if (group != null) {
                    label.put("children", group);
                }
            }
            return labels;
        }
        return null;
    }

    @Autowired
    PortalLabelMapper portalLabelMapper;

    public HttpResult listLabels(PageRequest pageRequest) {
        return HttpResult.ok(MybatisPageHelper.findPage(pageRequest, portalLabelMapper, "listAllLabels", pageRequest.getParams()));
    }

    public HttpResult deleteLabels(List<Integer> ids) {
        ids.remove(new Integer(0));
        ids.remove(new Integer(1));
        ids.remove(new Integer(2));
        portalLabelMapper.deleteLabels(ids);
        return HttpResult.ok();
    }

}
