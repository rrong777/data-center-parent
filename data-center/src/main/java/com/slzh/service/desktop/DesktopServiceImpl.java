package com.slzh.service.desktop;

import com.alibaba.fastjson.JSONArray;
import com.slzh.dao.AppCenterMapper;
import com.slzh.dao.DesktopMapper;
import com.slzh.model.desktop.AppInfo;
import com.slzh.model.desktop.DesktopInfo;
import com.slzh.model.desktop.DesktopInterface;
import com.slzh.model.desktop.FolderInfo;
import com.slzh.model.http.HttpResult;

import com.slzh.utils.login.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class DesktopServiceImpl {
    private static final Logger log = LoggerFactory.getLogger(DesktopServiceImpl.class);
    private static final String DESKTOP_NAME = "自定义桌面";

    @Autowired
    private DesktopMapper desktopMapper;

    @Autowired
    private AppCenterMapper appCenterMapper;

    /**
     * 通过用户id获取该用户的所有桌面的信息
     *
     * @param request
     */
    public HttpResult getDesktopByUserId(Map<String, Object> request) {
        if (request == null) {
            return null;
        }

        if (!request.containsKey("userId") || StringUtils.isBlank(request.get("userId").toString())) {
            return null;
        }

        Long uid = Long.valueOf(request.get("userId").toString());
        //获取桌面id
        List<String> desktopIdList = desktopMapper.getDesktopIdByUserId(uid);

        //如果该用户没有没有桌面，则初始化一个桌面
        if (CollectionUtils.isEmpty(desktopIdList)) {
            this.addDesktop(request);
            List<String> list = desktopMapper.getDesktopIdByUserId(uid);
            if (CollectionUtils.isEmpty(list)) {
                return null;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("desktopId", list.get(0));
            map.put("desktopName", DESKTOP_NAME);
            map.put("desktopInfoList", null);
            List<Map<String, Object>> initializeList = new ArrayList<>();
            initializeList.add(map);
            return HttpResult.ok(initializeList);
        }

        //通过桌面id获取文件夹信息
        List<DesktopInfo> desktopInfoList = desktopMapper.getDesktopInfo(desktopIdList);
        //通过桌面id获取app信息
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
        List<AppInfo> appInfoList = desktopMapper.getAppInfoByDesktopId(desktopIdList, appIds);
        Map<String, List<DesktopInterface>> resultMap = new HashMap<>();
        Map<String, String> desktopIdAndDesktopNameMap = new HashMap<>();

        //添加桌面的文件夹，并对文件夹中的app排序
        if (!CollectionUtils.isEmpty(desktopInfoList)) {
            for (DesktopInfo desktopInfo : desktopInfoList) {
                List<DesktopInterface> desktopInterfaceList = new ArrayList<>();
                resultMap.put(desktopInfo.getDesktopId(), desktopInterfaceList);
                desktopIdAndDesktopNameMap.put(desktopInfo.getDesktopId(), desktopInfo.getDesktopName());
                if (CollectionUtils.isEmpty(desktopInfo.getFolderInfoList())) {
                    continue;
                }
                Iterator<FolderInfo> iterator = desktopInfo.getFolderInfoList().iterator();
                while (iterator.hasNext()) {
                    FolderInfo folderInfo = iterator.next();
                    if (StringUtils.isBlank(folderInfo.getFolderId())) {
                        iterator.remove();
                        continue;
                    }
                    if (CollectionUtils.isEmpty(folderInfo.getAppInfoList())) {
                        continue;
                    }
                    List<AppInfo> appList = new ArrayList<>(10000);
                    //初始化appList，防止在执行set的时候报异常
                    appList.addAll(folderInfo.getAppInfoList());
                    for (AppInfo appInfo : folderInfo.getAppInfoList()) {
                        try {
                            appList.set(appInfo.getFolderSequence(), appInfo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    folderInfo.setAppInfoList(appList);
                }
                desktopInterfaceList.addAll(desktopInfo.getFolderInfoList());
            }
        }

        //添加桌面的app
        if (!CollectionUtils.isEmpty(appInfoList)) {
            for (AppInfo appInfo : appInfoList) {
                resultMap.get(appInfo.getDesktopId()).add(appInfo);
            }
        }

        List<Map<String, Object>> resultList = new ArrayList<>();
        //排序
        for (String desktopId : resultMap.keySet()) {
            List<DesktopInterface> list = resultMap.get(desktopId);
            if (!CollectionUtils.isEmpty(list)) {
                Collections.sort(list, new Comparator<DesktopInterface>() {
                    @Override
                    public int compare(DesktopInterface o1, DesktopInterface o2) {
                        return o1.getSequence() - o2.getSequence();
                    }
                });
            }

            Map<String, Object> map = new HashMap<>();
            map.put("desktopId", desktopId);
            map.put("desktopName", desktopIdAndDesktopNameMap.get(desktopId));
            map.put("desktopInfoList", list);
            resultList.add(map);
        }
        return HttpResult.ok(resultList);
    }


    /**
     * 通过用户id获取该用户的所有桌面的信息
     *
     * @param request
     */
    public HttpResult getDesktopByDesktopId(Map<String, Object> request) {
        if (request == null) {
            return null;
        }
        if (!request.containsKey("desktopId") || StringUtils.isBlank(request.get("desktopId").toString())) {
            return null;
        }

        //获取桌面id
        String desktopId = request.get("desktopId").toString();
        List<String> desktopIdList = new ArrayList<>();
        desktopIdList.add(desktopId);

        //通过桌面id获取文件夹信息
        List<DesktopInfo> desktopInfoList = desktopMapper.getDesktopInfo(desktopIdList);
        //通过桌面id获取app信息
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
        List<AppInfo> appInfoList = desktopMapper.getAppInfoByDesktopId(desktopIdList, appIds);
        List<DesktopInterface> desktopInterfaceList = new ArrayList<>();
        //添加桌面的文件夹，并对文件夹中的app排序
        if (!CollectionUtils.isEmpty(desktopInfoList)) {
            for (DesktopInfo desktopInfo : desktopInfoList) {
                if (CollectionUtils.isEmpty(desktopInfo.getFolderInfoList())) {
                    continue;
                }
                Iterator<FolderInfo> iterator = desktopInfo.getFolderInfoList().iterator();
                while (iterator.hasNext()) {
                    FolderInfo folderInfo = iterator.next();
                    if (StringUtils.isBlank(folderInfo.getFolderId())) {
                        iterator.remove();
                        continue;
                    }
                    if (CollectionUtils.isEmpty(folderInfo.getAppInfoList())) {
                        continue;
                    }

                    List<AppInfo> appList = new ArrayList<>();
                    //初始化appList，防止在执行set的时候报异常
                    appList.addAll(folderInfo.getAppInfoList());
                    for (AppInfo appInfo : folderInfo.getAppInfoList()) {
                        appList.set(appInfo.getFolderSequence(), appInfo);
                    }
                    folderInfo.setAppInfoList(appList);
                }
                desktopInterfaceList.addAll(desktopInfo.getFolderInfoList());
            }
        }

        //添加桌面的app
        if (!CollectionUtils.isEmpty(appInfoList)) {
            for (AppInfo appInfo : appInfoList) {
                desktopInterfaceList.add(appInfo);
            }
        }

        //排序
        Collections.sort(desktopInterfaceList, new Comparator<DesktopInterface>() {
            @Override
            public int compare(DesktopInterface o1, DesktopInterface o2) {
                return o1.getSequence() - o2.getSequence();
            }
        });
        return HttpResult.ok(desktopInterfaceList);
    }


    /**
     * 通过用户id获取该用户的收藏夹信息
     *
     * @param request
     */
    public HttpResult getFavoritesIdByUserId(Map<String, Object> request) {
        if (request == null) {
            return null;
        }

        if (!request.containsKey("userId") || StringUtils.isBlank(request.get("userId").toString())) {
            return null;
        }

        Long uid = Long.valueOf(request.get("userId").toString());
        //获取收藏夹id
        String favoritesId = desktopMapper.getFavoritesIdByUserId(uid);

        //如果该用户没有没有收藏夹，则初始化收藏夹
        if (StringUtils.isBlank(favoritesId)) {
            String id = generatorId("favorites");
            desktopMapper.insertPortalFavorites(uid, id);
            Map<String, Object> map = new HashMap<>();
            map.put("favoritesId", id);
            map.put("FavoritesInfoList", null);
            List<Map<String, Object>> initializeList = new ArrayList<>();
            initializeList.add(map);
            return HttpResult.ok(initializeList);
        }

        //通过收藏夹id获取app信息
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
        List<Map<String, Object>> appInfoList = desktopMapper.getAppInfoByFavoritesId(favoritesId,appIds);

        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("favoritesId", favoritesId);
        map.put("FavoritesInfoList", appInfoList);
        resultList.add(map);
        return HttpResult.ok(resultList);
    }

    /**
     * 通过用户id获取该用户的工具栏信息
     *
     * @param request
     */
    public HttpResult getToolIdByUserId(Map<String, Object> request) {
        if (request == null) {
            return null;
        }

        if (!request.containsKey("userId") || StringUtils.isBlank(request.get("userId").toString())) {
            return null;
        }

        Long uid = Long.valueOf(request.get("userId").toString());
        //获取工具栏id
        String toolId = desktopMapper.getToolIdByUserId(uid);

        //如果该用户没有没有工具栏，则初始化工具栏
        if (StringUtils.isBlank(toolId)) {
            String id = generatorId("tool");
            desktopMapper.insertPortalTool(uid, id);
            Map<String, Object> map = new HashMap<>();
            map.put("toolId", id);
            map.put("toolInfoList", null);
            List<Map<String, Object>> initializeList = new ArrayList<>();
            initializeList.add(map);
            return HttpResult.ok(initializeList);
        }

        //通过工具栏id获取app信息
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
        List<Map<String, Object>> appInfoList = desktopMapper.getAppInfoByToolId(toolId, appIds);

        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("toolId", toolId);
        map.put("toolInfoList", appInfoList);
        resultList.add(map);
        return HttpResult.ok(resultList);
    }

    /**
     * 新增桌面
     * 1、生成桌面id
     * 2、创建桌面
     * 3、生成收藏夹id
     * 4、创建收藏夹
     *
     * @param request
     */
    public HttpResult addDesktop(Map<String, Object> request) {
        if (request == null) {
            return null;
        }
        if (!request.containsKey("userId") || StringUtils.isBlank(request.get("userId").toString())) {
            return null;
        }

        Long uid = Long.valueOf(request.get("userId").toString());
        List<String> desktopIdByUserId = desktopMapper.getDesktopIdByUserId(uid);
        if (desktopIdByUserId.size() > 5) {
            return HttpResult.error("桌面数量已经达到最大值6，新增桌面失败");
        }
        //生成桌面id
        String desktopId = generatorId("desktop");
        //创建桌面
        int count1 = desktopMapper.insertPortalDesktop(uid, desktopId, DESKTOP_NAME);

        String favoritesId = desktopMapper.getFavoritesIdByUserId(uid);
        if (StringUtils.isBlank(favoritesId)) {
            //创建收藏夹
            desktopMapper.insertPortalFavorites(uid, generatorId("favorites"));
        }
        String toolId = desktopMapper.getToolIdByUserId(uid);
        if (StringUtils.isBlank(toolId)) {
            //创建工具栏
            desktopMapper.insertPortalTool(uid, generatorId("tool"));
        }

        if (count1 < 1) {
            return HttpResult.ok("新增桌面失败");
        }
        return HttpResult.ok("新增桌面成功");
    }

    /**
     * 删除桌面
     *
     * @param request
     */
    public HttpResult deleteDesktop(Map<String, Object> request) {
        if (request == null) {
            return null;
        }
        if (!request.containsKey("userId") || StringUtils.isBlank(request.get("userId").toString()) ||
                !request.containsKey("desktopId") || StringUtils.isBlank(request.get("desktopId").toString())) {
            return null;
        }

        Long uid = Long.valueOf(request.get("userId").toString());
        String desktopId = request.get("desktopId").toString();
        List<String> desktopIdList = desktopMapper.getDesktopIdByUserId(uid);
        if (desktopIdList != null && desktopIdList.size() < 2) {
            return HttpResult.ok("用户:" + request.get("userId").toString() + " 目前只有一个桌面，不能再执行删除桌面的动作");
        }
        //删除桌面
        int count1 = desktopMapper.deletePortalDesktop(uid, desktopId);
        //删除桌面文件夹信息
        desktopMapper.deleteFolderInfoByDesktopId(desktopId);
        //删除app信息
        desktopMapper.deleteAppInfoByDesktopId(desktopId);
        if (count1 < 1) {
            return HttpResult.ok("删除桌面失败");
        }
        return HttpResult.ok("删除桌面成功");
    }

    /**
     * 简单更新桌面信息
     *
     * @param request 桌面信息
     */
    public HttpResult simpleUpdateDesktop(Map<String, Object> request) {
        if (!request.containsKey("desktopId") || StringUtils.isBlank(request.get("desktopId").toString())) {
            return HttpResult.error("desktopId是空值");
        }
        if (!request.containsKey("type") || StringUtils.isBlank(request.get("type").toString())) {
            return HttpResult.error("type是空值");
        }

        if ("folder".equalsIgnoreCase(request.get("type").toString())) {
            if (!request.containsKey("folderId") || StringUtils.isBlank(request.get("folderId").toString())) {
                return HttpResult.error("folderId是空值");
            }
            //更新文件夹信息
            desktopMapper.updateFolderInfoByDesktopId(request);
        }
//        else if ("app".equalsIgnoreCase(request.get("type").toString())) {
//            if (!request.containsKey("appId") || StringUtils.isBlank(request.get("appId").toString())) {
//                return HttpResult.error("appId是空值");
//            }
//            //更新app信息
//            desktopMapper.updateAppInfoByDesktopId(request);
//        }
        return HttpResult.ok("更新成功");
    }

    /**
     * 简单更新桌面名称
     *
     * @param request 桌面信息
     */
    public HttpResult updateDesktopNameByDesktopId(Map<String, Object> request) {
        if (!request.containsKey("desktopId") || StringUtils.isBlank(request.get("desktopId").toString())) {
            return HttpResult.error("desktopId是空值");
        }
        if (!request.containsKey("desktopName") || StringUtils.isBlank(request.get("desktopName").toString())) {
            return HttpResult.error("desktopName是空值");
        }
        desktopMapper.updateDesktopNameByDesktopId(request.get("desktopId").toString(), request.get("desktopName").toString());
        return HttpResult.ok("更新成功");
    }

    /**
     * 更新桌面信息
     * 1、先删除之前的桌面信息
     * 2、将传入的桌面信息插入
     *
     * @param request 桌面信息
     */
    public HttpResult updateDesktop(Map<String, Object> request) {
        if (!request.containsKey("desktopId") || StringUtils.isBlank(request.get("desktopId").toString())) {
            return null;
        }
        if (!request.containsKey("desktopInfoList") || CollectionUtils.isEmpty((List) request.get("desktopInfoList"))) {
            //1、删除桌面信息
            desktopMapper.deleteFolderInfoByDesktopId(request.get("desktopId").toString());
            desktopMapper.deleteAppInfoByDesktopId(request.get("desktopId").toString());
            return HttpResult.ok(new ArrayList<>());
        }
        this.processDesktopInfo(request);
        return this.getDesktopByDesktopId(request);
    }

    @Transactional
    public void processDesktopInfo(Map<String, Object> request) {
        //获取桌面id
        String desktopId = request.get("desktopId").toString();
        //1、删除桌面信息
        desktopMapper.deleteFolderInfoByDesktopId(desktopId);
        desktopMapper.deleteAppInfoByDesktopId(desktopId);

        //2、插入最新的桌面信息
        List desktopInfoList = (List) request.get("desktopInfoList");
        List<Map<String, Object>> folderList = new ArrayList<>();
        List<Map<String, Object>> appList = new ArrayList<>();
        for (int i = 0; i < desktopInfoList.size(); i++) {
            Map<String, Object> dataMap = (Map<String, Object>) desktopInfoList.get(i);

            //1、整理桌面的文件夹信息
            if (dataMap.get("type").equals("folder")) {
                //1.1、处理新建的文件夹
                if (!dataMap.containsKey("folderId") || StringUtils.isBlank(dataMap.get("folderId").toString())) {
                    dataMap.put("folderId", generatorId("folder"));
                    dataMap.put("desktopId", desktopId);
                    dataMap.put("name", "新建文件夹");
                    dataMap.put("width", 430);
                    dataMap.put("height", 350);
                    dataMap.put("type", "folder");
                }
                dataMap.put("open", 0);
                dataMap.put("expand", 1);
                dataMap.put("sequence", i);
                folderList.add(dataMap);

                //1.2、处理文件夹中的app信息
                if (dataMap.containsKey("appInfoList") && !StringUtils.isBlank(dataMap.get("appInfoList").toString())) {
                    JSONArray appInfoList = JSONArray.parseArray(dataMap.get("appInfoList").toString());
                    if (appInfoList.size() > 0) {
                        for (int j = 0; j < appInfoList.size(); j++) {
                            Map<String, Object> appMap = (Map<String, Object>) appInfoList.get(j);
                            appMap.put("folderId", dataMap.get("folderId"));
                            appMap.put("sequence", null);
                            appMap.put("folderSequence", j);
                            appList.add(appMap);
                        }
                    }
                }
            }

            //2、整理桌面上的app信息
            if (dataMap.get("type").equals("app")) {
                //2.1、处理新下载的app
                if (!dataMap.containsKey("appId") || StringUtils.isBlank(dataMap.get("appId").toString())) {
                    dataMap.put("appId", generatorId("app"));
                    dataMap.put("desktopId", desktopId);
//                            dataMap.put("name", "新建文件夹");
                    dataMap.put("type", "app");
                }
                dataMap.put("folderId", null);
                dataMap.put("sequence", i);
                dataMap.put("folderSequence", null);
                appList.add(dataMap);
            }
        }
        if (!CollectionUtils.isEmpty(folderList)) {
            desktopMapper.insertPortalFolder(folderList);
        }
        if (!CollectionUtils.isEmpty(appList)) {
            desktopMapper.insertPortalApp(appList);
        }
    }


    /**
     * 更新收藏夹信息
     * 1、先删除之前的收藏夹信息
     * 2、将传入的收藏夹信息插入
     *
     * @param request 收藏夹信息
     */
    public HttpResult updateFavorites(Map<String, Object> request) {
        if (!request.containsKey("favoritesId") || StringUtils.isBlank(request.get("favoritesId").toString())) {
            return null;
        }
        if (!request.containsKey("FavoritesInfoList") || CollectionUtils.isEmpty((List) request.get("FavoritesInfoList"))) {
            desktopMapper.deleteAppInfoByFavoritesId(request.get("favoritesId").toString());
            return HttpResult.ok(new ArrayList<>());
        }
        //多线程处理，目的是先返回收藏夹信息，再后台更新
        this.processFavoritesInfo(request);
        return HttpResult.ok(request.get("FavoritesInfoList"));
    }

    /**
     * 更新工具栏信息
     * 1、先删除之前的工具栏信息
     * 2、将传入的工具栏信息插入
     *
     * @param request 工具栏信息
     */
    public HttpResult updateTool(Map<String, Object> request) {
        if (!request.containsKey("toolId") || StringUtils.isBlank(request.get("toolId").toString())) {
            return null;
        }
        if (!request.containsKey("appId") || StringUtils.isBlank(request.get("appId").toString())) {
            return null;
        }
        //获取工具栏id
        String toolId = request.get("toolId").toString();
        String appId = request.get("appId").toString();
        Map<String, Object> appInfo = desktopMapper.getAppInfoByToolIdAndAppId(toolId, appId);
        if (appInfo != null) {
            desktopMapper.insertPortalToolApp(appInfo);
        } else {
            int count = desktopMapper.getCountOfAppInfoByToolId(toolId);
            if (count < 5) {
                Map<String, Object> tmp = new HashMap<>();
                tmp.put("appId", appId);
                tmp.put("toolId", toolId);
                tmp.put("sequence", 0);
                tmp.put("type", "app");
                tmp.put("count", 1);
                desktopMapper.insertPortalToolApp(tmp);
            }
        }
        return HttpResult.ok("工具箱更新成功");
    }

    private void processFavoritesInfo(Map<String, Object> request) {
        Runnable runnable = new Runnable() {
            @Transactional
            @Override
            public void run() {
                //获取收藏夹id
                String favoritesId = request.get("favoritesId").toString();
                //1、删除收藏夹信息
                desktopMapper.deleteAppInfoByFavoritesId(favoritesId);
                //2、插入最新的收藏夹信息
                List favoritesInfoList = (List) request.get("FavoritesInfoList");
                for (int i = 0; i < favoritesInfoList.size(); i++) {
                    Map<String, Object> dataMap = (Map<String, Object>) favoritesInfoList.get(i);
                    //设置app的位置
                    dataMap.put("sequence", i);
                    if (!dataMap.containsKey("favoritesId") || StringUtils.isBlank(dataMap.get("favoritesId").toString())) {
                        dataMap.put("favoritesId", favoritesId);
                    }
                }
                desktopMapper.insertPortalFavoritesApp(favoritesInfoList);
            }
        };
        new Thread(runnable).start();
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
            case "tool":
                return "5" + s;
            default:
                return "1" + s;
        }
    }


}
