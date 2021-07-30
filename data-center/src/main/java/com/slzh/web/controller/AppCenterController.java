package com.slzh.web.controller;

import com.slzh.model.desktop.PortalLabel;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.PageRequest;
import com.slzh.model.sso.AppCenter;
import com.slzh.service.appcenter.AppCenterService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appcenter")
public class AppCenterController {
    @Autowired
    private AppCenterService appCenterService;

    /**
     * 分页查询应用中心
     *
     * @return
     */
    @PostMapping("/findPage")
    public HttpResult listRegisterInfo(@RequestBody PageRequest pageRequest) {
        return appCenterService.listRegisterInfo(pageRequest);
    }

    @PostMapping("/findPageByTarget")
    public HttpResult findPageByDesktopId(@RequestBody PageRequest pageRequest) {
        return appCenterService.findPageByTarget(pageRequest);
    }

    @PostMapping("/add")
    public HttpResult addRegisterInfo(@RequestBody AppCenter appCenter) {
        return appCenterService.addRegisterInfo(appCenter);
    }

    @PostMapping("/update")
    public HttpResult updateRegistorInfo(@RequestBody AppCenter appCenter) {
        return appCenterService.updateRegistorInfo(appCenter);
    }

    @GetMapping("/label/getLabels")
    public HttpResult getLabels() {
        return appCenterService.getLabels();
    }


    @PostMapping("/label/getCategoryCount")
    public HttpResult getCategoryCount(@RequestBody Map request) {
        return appCenterService.getCategoryCount(request);
    }

    @PostMapping("/label/addLabel")
    public HttpResult addLabel(@RequestBody PortalLabel portalLabel) {
        return appCenterService.addLabel(portalLabel);
    }

    @PostMapping("/label/deleteLabels")
    public HttpResult deleteLabel(@RequestBody List<Integer> labelIds) {
        return appCenterService.deleteLabels(labelIds);
    }

    @PostMapping("/label/updateLabel")
    public HttpResult updateLabel(@RequestBody PortalLabel portalLabel) {
        return appCenterService.updateLabel(portalLabel);
    }

    @PostMapping("label/findPageLabels")
    public HttpResult getAppByLabel(@RequestBody PageRequest pageRequest) {
        return appCenterService.listLabels(pageRequest);
    }

    @DeleteMapping("/delete")
    public HttpResult delete(@RequestParam String registerId) {
        return appCenterService.deleteRegistorInfo(registerId);
    }

    @PostMapping("/download")
    public HttpResult download(@RequestBody PageRequest pageRequest) {
        return appCenterService.download(pageRequest);
    }

    @PostMapping("/findPlatePage")
    public HttpResult findPlatePage(@RequestBody PageRequest pageRequest) {
        return appCenterService.download(pageRequest);
    }

    @PostMapping("/generatorLience")
    public HttpResult generatorLience(@RequestBody AppCenter appCenter) {
        return appCenterService.updateRegistorInfoAndGeneratorLienceKey(appCenter);
    }

    //    {
//      "code": 200,
//      "data": [
    //    {
    //      "children": [
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "20192694c-4f4b-47d8-b15f-2c6bcbcbc0e1",
    //          "appId": "20192694c-4f4b-47d8-b15f-2c6bcbcbc0e1",
    //          "isDownload": 0,
    //          "developers": "4G执法记录仪",
    //          "applicationName": "4G执法记录仪"
    //        },
    //        {
    //          "labelId": 10,
    //          "registerInfoId": "207411205-c33d-4bcf-9205-7e963e9af6dc",
    //          "appId": "207411205-c33d-4bcf-9205-7e963e9af6dc",
    //          "isDownload": 0,
    //          "developers": "可视化指挥平台",
    //          "applicationName": "可视化指挥平台"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "209867f64-596b-434a-aa5c-7d19d557824f",
    //          "appId": "209867f64-596b-434a-aa5c-7d19d557824f",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "实时过车"
    //        },
    //        {
    //          "registerInfoId": "20bea1d6e-66c9-44a3-b6dc-2a85b638d88d",
    //          "appId": "20bea1d6e-66c9-44a3-b6dc-2a85b638d88d",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "团伙分析"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "20c600072-8e0e-429c-a6f5-768a169c8c6a",
    //          "appId": "20c600072-8e0e-429c-a6f5-768a169c8c6a",
    //          "isDownload": 0,
    //          "developers": "多维实战平台",
    //          "applicationName": "平台应用管理"
    //        },
    //        {
    //          "labelId": 8,
    //          "registerInfoId": "20c600072-8e0e-429c-a6f5-768a169c8c6a",
    //          "appId": "20c600072-8e0e-429c-a6f5-768a169c8c6a",
    //          "isDownload": 0,
    //          "developers": "多维实战平台",
    //          "applicationName": "平台应用管理"
    //        },
    //        {
    //          "labelId": 9,
    //          "registerInfoId": "2117c1098-758c-4e21-af6f-fc9317911089",
    //          "appId": "2117c1098-758c-4e21-af6f-fc9317911089",
    //          "isDownload": 0,
    //          "developers": "视频监控实战应用",
    //          "applicationName": "视频监控实战应用"
    //        },
    //        {
    //          "labelId": 8,
    //          "registerInfoId": "216c566f0-6c89-44cb-a12d-2a4c53dfcc01",
    //          "appId": "216c566f0-6c89-44cb-a12d-2a4c53dfcc01",
    //          "isDownload": 0,
    //          "developers": "多维实战平台",
    //          "applicationName": "全息搜索"
    //        },
    //        {
    //          "registerInfoId": "216c566f0-6c89-44cb-a12d-2a4c53dfcc02",
    //          "appId": "216c566f0-6c89-44cb-a12d-2a4c53dfcc02",
    //          "isDownload": 0,
    //          "developers": "多维实战平台",
    //          "applicationName": "重点对象管控"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "216c566f0-6c89-44cb-a12d-2a4c53dfcc03",
    //          "appId": "216c566f0-6c89-44cb-a12d-2a4c53dfcc03",
    //          "isDownload": 0,
    //          "developers": "多维实战平台",
    //          "applicationName": "视频行为分析"
    //        },
    //        {
    //          "registerInfoId": "216c566f0-6c89-44cb-a12d-2a4c53dfcc04",
    //          "appId": "216c566f0-6c89-44cb-a12d-2a4c53dfcc04",
    //          "isDownload": 0,
    //          "developers": "多维实战平台",
    //          "applicationName": "人群计算"
    //        },
    //        {
    //          "registerInfoId": "216c566f0-6c89-44cb-a12d-2a4c53dfcc05",
    //          "appId": "216c566f0-6c89-44cb-a12d-2a4c53dfcc05",
    //          "isDownload": 0,
    //          "developers": "多维实战平台",
    //          "applicationName": "拥堵预警"
    //        },
    //        {
    //          "registerInfoId": "219fdea90-f3e3-48a5-a7c9-cf2ceff81217",
    //          "appId": "219fdea90-f3e3-48a5-a7c9-cf2ceff81217",
    //          "isDownload": 0,
    //          "developers": "多维实战平台",
    //          "applicationName": "全信息检索"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "21b9eaea8-d508-4d58-989c-14a9cb406209",
    //          "appId": "21b9eaea8-d508-4d58-989c-14a9cb406209",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "车辆布控审批"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "21f96d855-cb2a-4099-8725-1419b919fdf0",
    //          "appId": "21f96d855-cb2a-4099-8725-1419b919fdf0",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "隐匿车挖掘"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2213a4ff0-0e5e-455f-9874-f0d025870f30",
    //          "appId": "2213a4ff0-0e5e-455f-9874-f0d025870f30",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "脸部遮挡"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "224ddc7b9-0838-44a5-b1db-e75e264553e9",
    //          "appId": "224ddc7b9-0838-44a5-b1db-e75e264553e9",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "人像布控管理"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "22e0f7f95-e2a4-4a8b-a7e6-70c5c03c19f0",
    //          "appId": "22e0f7f95-e2a4-4a8b-a7e6-70c5c03c19f0",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "同行车辆"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2406be7a8-fa72-4de9-a194-56c70ce88151",
    //          "appId": "2406be7a8-fa72-4de9-a194-56c70ce88151",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "行人闯红灯"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2497ad3f5-391d-4d54-9f97-992acef1f369",
    //          "appId": "2497ad3f5-391d-4d54-9f97-992acef1f369",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "快捷搜车"
    //        },
    //        {
    //          "labelId": 8,
    //          "registerInfoId": "24c1c20cf-5164-4779-9bdd-d3cd811a485e",
    //          "appId": "24c1c20cf-5164-4779-9bdd-d3cd811a485e",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "全要素检索"
    //        },
    //        {
    //          "labelId": 8,
    //          "registerInfoId": "24ffca988-922d-4956-9a1a-ee1a371f87f1",
    //          "appId": "24ffca988-922d-4956-9a1a-ee1a371f87f1",
    //          "isDownload": 0,
    //          "developers": "响水全生命周期管理平台",
    //          "applicationName": "响水全生命周期管理平台"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "256eb5287-d57c-47c0-aaf5-df93362993c0",
    //          "appId": "256eb5287-d57c-47c0-aaf5-df93362993c0",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "火眼"
    //        },
    //        {
    //          "registerInfoId": "258a780ba-b93f-44c8-bd4c-8965c992b9a9",
    //          "appId": "258a780ba-b93f-44c8-bd4c-8965c992b9a9",
    //          "isDownload": 0,
    //          "developers": "运维平台",
    //          "applicationName": "运维平台"
    //        },
    //        {
    //          "labelId": 9,
    //          "registerInfoId": "25c2ad197-4fd2-408a-9016-7aaf5bcd0e98",
    //          "appId": "25c2ad197-4fd2-408a-9016-7aaf5bcd0e98",
    //          "isDownload": 0,
    //          "developers": "视频图像侦查",
    //          "applicationName": "视频图像侦查"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2672553d3-1705-4f6e-8aba-1969056d8f19",
    //          "appId": "2672553d3-1705-4f6e-8aba-1969056d8f19",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "图片编辑工具"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "26a33c2b7-4f0f-4443-9add-0e59651b858b",
    //          "appId": "26a33c2b7-4f0f-4443-9add-0e59651b858b",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "车辆告警"
    //        },
    //        {
    //          "labelId": 9,
    //          "registerInfoId": "26b267a0a-3072-4cdd-9cc9-dced6709156b",
    //          "appId": "26b267a0a-3072-4cdd-9cc9-dced6709156b",
    //          "isDownload": 0,
    //          "developers": "视频联网平台",
    //          "applicationName": "视频联网平台"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "26cd93173-7699-45af-a696-f79a5f27ef35",
    //          "appId": "26cd93173-7699-45af-a696-f79a5f27ef35",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "人像历史报警"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "27e5f2688-f4ee-4dd0-9bf6-43016f881630",
    //          "appId": "27e5f2688-f4ee-4dd0-9bf6-43016f881630",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "轨迹重现"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2851d7e24-34c9-4dec-b5dc-612c8992b95e",
    //          "appId": "2851d7e24-34c9-4dec-b5dc-612c8992b95e",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "人像实时报警"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "286404627-1451-4cb2-8dbc-84683fc26536",
    //          "appId": "286404627-1451-4cb2-8dbc-84683fc26536",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "人像大数据"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "28df555ba-a657-4caa-a0bd-34a499fb741c",
    //          "appId": "28df555ba-a657-4caa-a0bd-34a499fb741c",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "落脚点分析"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "28e2da213-250c-4a38-b890-7debd4ec601f",
    //          "appId": "28e2da213-250c-4a38-b890-7debd4ec601f",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "以图搜图"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "292c4f05f-2144-441a-81ff-50c6f5d7beec",
    //          "appId": "292c4f05f-2144-441a-81ff-50c6f5d7beec",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "昼伏夜出"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2952c2a1e-f352-4458-bcdc-36446353c4c2",
    //          "appId": "2952c2a1e-f352-4458-bcdc-36446353c4c2",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "同行分析"
    //        },
    //        {
    //          "labelId": 10,
    //          "registerInfoId": "2a859e848-577d-401c-99e1-564f735c05de",
    //          "appId": "2a859e848-577d-401c-99e1-564f735c05de",
    //          "isDownload": 0,
    //          "developers": "主题库管理",
    //          "applicationName": "主题库管理"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2ac03cbd6-5819-4caa-9aa6-f82db8ae6685",
    //          "appId": "2ac03cbd6-5819-4caa-9aa6-f82db8ae6685",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "违章查询"
    //        },
    //        {
    //          "labelId": 10,
    //          "registerInfoId": "2ad3aef94-6c53-453f-b8ad-b7f741f1b88e",
    //          "appId": "2ad3aef94-6c53-453f-b8ad-b7f741f1b88e",
    //          "isDownload": 0,
    //          "developers": "接口服务管理",
    //          "applicationName": "接口服务管理"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2b3e2f92d-ed18-4d62-a842-e31f39a1d6a6",
    //          "appId": "2b3e2f92d-ed18-4d62-a842-e31f39a1d6a6",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "危险车识别"
    //        },
    //        {
    //          "labelId": 9,
    //          "registerInfoId": "2b454d1bf-36be-4cf8-882c-537ae1a5cb05",
    //          "appId": "2b454d1bf-36be-4cf8-882c-537ae1a5cb05",
    //          "isDownload": 0,
    //          "developers": "视频结构化",
    //          "applicationName": "视频结构化"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2c583efad-992c-4bcb-a5d6-14bb1a668b4a",
    //          "appId": "2c583efad-992c-4bcb-a5d6-14bb1a668b4a",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "频繁过车"
    //        },
    //        {
    //          "labelId": 10,
    //          "registerInfoId": "2c7df305d-556f-4493-a7c4-9ce8b59d9418",
    //          "appId": "2c7df305d-556f-4493-a7c4-9ce8b59d9418",
    //          "isDownload": 0,
    //          "developers": "数据治理",
    //          "applicationName": "数据治理"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2c8fa447d-6423-4778-9c59-fa03238ce93a",
    //          "appId": "2c8fa447d-6423-4778-9c59-fa03238ce93a",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "人像区域碰撞"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2c93351fe-756c-4c0e-96ee-769293b3fd36",
    //          "appId": "2c93351fe-756c-4c0e-96ee-769293b3fd36",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "人像底库碰撞"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2d3f7732d-cbbd-4bec-ad58-c1b7998af376",
    //          "appId": "2d3f7732d-cbbd-4bec-ad58-c1b7998af376",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "车辆布控"
    //        },
    //        {
    //          "labelId": 10,
    //          "registerInfoId": "2d745a4fe-cce8-48e6-8b90-8c2bfc69244f",
    //          "appId": "2d745a4fe-cce8-48e6-8b90-8c2bfc69244f",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "车辆卡口流量统计"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2d7c6d01a-b013-4ade-8f05-7d5b5b72f47a",
    //          "appId": "2d7c6d01a-b013-4ade-8f05-7d5b5b72f47a",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "车辆平台"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2d994a50a-cad2-4a6e-8657-9ae39b8e0b44",
    //          "appId": "2d994a50a-cad2-4a6e-8657-9ae39b8e0b44",
    //          "isDownload": 0,
    //          "developers": "空中大数据",
    //          "applicationName": "空中大数据"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2e0d253c0-06f7-43f9-b26b-25cda4a40d23",
    //          "appId": "2e0d253c0-06f7-43f9-b26b-25cda4a40d23",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "黄标车识别"
    //        },
    //        {
    //          "labelId": 8,
    //          "registerInfoId": "2e13b1ecd-cf87-4cb0-8839-2e159f7da026",
    //          "appId": "2e13b1ecd-cf87-4cb0-8839-2e159f7da026",
    //          "isDownload": 0,
    //          "developers": "罗普特",
    //          "applicationName": "视图库"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2e669bacb-2e3e-4d20-be74-8eb2e6f6ea06",
    //          "appId": "2e669bacb-2e3e-4d20-be74-8eb2e6f6ea06",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "频次分析"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2e8168f7d-09f7-4ed4-bbd1-98fd1c21f5d2",
    //          "appId": "2e8168f7d-09f7-4ed4-bbd1-98fd1c21f5d2",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "首次入城"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2ea459c6f-7394-4d4c-ad3c-2040e1a5ea71",
    //          "appId": "2ea459c6f-7394-4d4c-ad3c-2040e1a5ea71",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "嫌疑人库"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2ebd0aa5b-b165-4a57-8d31-06c2014f8a18",
    //          "appId": "2ebd0aa5b-b165-4a57-8d31-06c2014f8a18",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "1-1人脸比对"
    //        },
    //        {
    //          "registerInfoId": "2eca0da6e-a004-4df4-aa00-0375e6b95ec3",
    //          "appId": "2eca0da6e-a004-4df4-aa00-0375e6b95ec3",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "图片抓拍人脸"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2ee58acfa-f571-4a1c-a7af-929b0ed081a7",
    //          "appId": "2ee58acfa-f571-4a1c-a7af-929b0ed081a7",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "重点关注人员"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2f6f30da9-49cc-4b10-a8ae-bf42f81f5636",
    //          "appId": "2f6f30da9-49cc-4b10-a8ae-bf42f81f5636",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "一牌多车"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2fbe55427-016d-4edc-8ac5-2edb6dd22f50",
    //          "appId": "2fbe55427-016d-4edc-8ac5-2edb6dd22f50",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "鱼眼矫正"
    //        }
    //      ],
    //      "id": 0,
    //      "label": "全部"
    //    },
    //    {
    //      "children": [
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "20192694c-4f4b-47d8-b15f-2c6bcbcbc0e1",
    //          "appId": "20192694c-4f4b-47d8-b15f-2c6bcbcbc0e1",
    //          "isDownload": 0,
    //          "developers": "4G执法记录仪",
    //          "applicationName": "4G执法记录仪"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "209867f64-596b-434a-aa5c-7d19d557824f",
    //          "appId": "209867f64-596b-434a-aa5c-7d19d557824f",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "实时过车"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "20c600072-8e0e-429c-a6f5-768a169c8c6a",
    //          "appId": "20c600072-8e0e-429c-a6f5-768a169c8c6a",
    //          "isDownload": 0,
    //          "developers": "多维实战平台",
    //          "applicationName": "平台应用管理"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "216c566f0-6c89-44cb-a12d-2a4c53dfcc03",
    //          "appId": "216c566f0-6c89-44cb-a12d-2a4c53dfcc03",
    //          "isDownload": 0,
    //          "developers": "多维实战平台",
    //          "applicationName": "视频行为分析"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "21b9eaea8-d508-4d58-989c-14a9cb406209",
    //          "appId": "21b9eaea8-d508-4d58-989c-14a9cb406209",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "车辆布控审批"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "21f96d855-cb2a-4099-8725-1419b919fdf0",
    //          "appId": "21f96d855-cb2a-4099-8725-1419b919fdf0",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "隐匿车挖掘"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2213a4ff0-0e5e-455f-9874-f0d025870f30",
    //          "appId": "2213a4ff0-0e5e-455f-9874-f0d025870f30",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "脸部遮挡"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "224ddc7b9-0838-44a5-b1db-e75e264553e9",
    //          "appId": "224ddc7b9-0838-44a5-b1db-e75e264553e9",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "人像布控管理"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "22e0f7f95-e2a4-4a8b-a7e6-70c5c03c19f0",
    //          "appId": "22e0f7f95-e2a4-4a8b-a7e6-70c5c03c19f0",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "同行车辆"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2406be7a8-fa72-4de9-a194-56c70ce88151",
    //          "appId": "2406be7a8-fa72-4de9-a194-56c70ce88151",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "行人闯红灯"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2497ad3f5-391d-4d54-9f97-992acef1f369",
    //          "appId": "2497ad3f5-391d-4d54-9f97-992acef1f369",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "快捷搜车"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "256eb5287-d57c-47c0-aaf5-df93362993c0",
    //          "appId": "256eb5287-d57c-47c0-aaf5-df93362993c0",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "火眼"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2672553d3-1705-4f6e-8aba-1969056d8f19",
    //          "appId": "2672553d3-1705-4f6e-8aba-1969056d8f19",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "图片编辑工具"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "26a33c2b7-4f0f-4443-9add-0e59651b858b",
    //          "appId": "26a33c2b7-4f0f-4443-9add-0e59651b858b",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "车辆告警"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "26cd93173-7699-45af-a696-f79a5f27ef35",
    //          "appId": "26cd93173-7699-45af-a696-f79a5f27ef35",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "人像历史报警"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "27e5f2688-f4ee-4dd0-9bf6-43016f881630",
    //          "appId": "27e5f2688-f4ee-4dd0-9bf6-43016f881630",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "轨迹重现"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2851d7e24-34c9-4dec-b5dc-612c8992b95e",
    //          "appId": "2851d7e24-34c9-4dec-b5dc-612c8992b95e",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "人像实时报警"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "286404627-1451-4cb2-8dbc-84683fc26536",
    //          "appId": "286404627-1451-4cb2-8dbc-84683fc26536",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "人像大数据"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "28df555ba-a657-4caa-a0bd-34a499fb741c",
    //          "appId": "28df555ba-a657-4caa-a0bd-34a499fb741c",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "落脚点分析"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "28e2da213-250c-4a38-b890-7debd4ec601f",
    //          "appId": "28e2da213-250c-4a38-b890-7debd4ec601f",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "以图搜图"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "292c4f05f-2144-441a-81ff-50c6f5d7beec",
    //          "appId": "292c4f05f-2144-441a-81ff-50c6f5d7beec",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "昼伏夜出"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2952c2a1e-f352-4458-bcdc-36446353c4c2",
    //          "appId": "2952c2a1e-f352-4458-bcdc-36446353c4c2",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "同行分析"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2ac03cbd6-5819-4caa-9aa6-f82db8ae6685",
    //          "appId": "2ac03cbd6-5819-4caa-9aa6-f82db8ae6685",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "违章查询"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2b3e2f92d-ed18-4d62-a842-e31f39a1d6a6",
    //          "appId": "2b3e2f92d-ed18-4d62-a842-e31f39a1d6a6",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "危险车识别"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2c583efad-992c-4bcb-a5d6-14bb1a668b4a",
    //          "appId": "2c583efad-992c-4bcb-a5d6-14bb1a668b4a",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "频繁过车"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2c8fa447d-6423-4778-9c59-fa03238ce93a",
    //          "appId": "2c8fa447d-6423-4778-9c59-fa03238ce93a",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "人像区域碰撞"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2c93351fe-756c-4c0e-96ee-769293b3fd36",
    //          "appId": "2c93351fe-756c-4c0e-96ee-769293b3fd36",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "人像底库碰撞"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2d3f7732d-cbbd-4bec-ad58-c1b7998af376",
    //          "appId": "2d3f7732d-cbbd-4bec-ad58-c1b7998af376",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "车辆布控"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2d7c6d01a-b013-4ade-8f05-7d5b5b72f47a",
    //          "appId": "2d7c6d01a-b013-4ade-8f05-7d5b5b72f47a",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "车辆平台"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2d994a50a-cad2-4a6e-8657-9ae39b8e0b44",
    //          "appId": "2d994a50a-cad2-4a6e-8657-9ae39b8e0b44",
    //          "isDownload": 0,
    //          "developers": "空中大数据",
    //          "applicationName": "空中大数据"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2e0d253c0-06f7-43f9-b26b-25cda4a40d23",
    //          "appId": "2e0d253c0-06f7-43f9-b26b-25cda4a40d23",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "黄标车识别"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2e669bacb-2e3e-4d20-be74-8eb2e6f6ea06",
    //          "appId": "2e669bacb-2e3e-4d20-be74-8eb2e6f6ea06",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "频次分析"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2e8168f7d-09f7-4ed4-bbd1-98fd1c21f5d2",
    //          "appId": "2e8168f7d-09f7-4ed4-bbd1-98fd1c21f5d2",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "首次入城"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2ea459c6f-7394-4d4c-ad3c-2040e1a5ea71",
    //          "appId": "2ea459c6f-7394-4d4c-ad3c-2040e1a5ea71",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "嫌疑人库"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2ebd0aa5b-b165-4a57-8d31-06c2014f8a18",
    //          "appId": "2ebd0aa5b-b165-4a57-8d31-06c2014f8a18",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "1-1人脸比对"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2ee58acfa-f571-4a1c-a7af-929b0ed081a7",
    //          "appId": "2ee58acfa-f571-4a1c-a7af-929b0ed081a7",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "重点关注人员"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2f6f30da9-49cc-4b10-a8ae-bf42f81f5636",
    //          "appId": "2f6f30da9-49cc-4b10-a8ae-bf42f81f5636",
    //          "isDownload": 0,
    //          "developers": "车辆大数据平台",
    //          "applicationName": "一牌多车"
    //        },
    //        {
    //          "labelId": 7,
    //          "registerInfoId": "2fbe55427-016d-4edc-8ac5-2edb6dd22f50",
    //          "appId": "2fbe55427-016d-4edc-8ac5-2edb6dd22f50",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "鱼眼矫正"
    //        }
    //      ],
    //      "id": 7,
    //      "label": "实战应用"
    //    },
    //    {
    //      "children": [
    //        {
    //          "labelId": 8,
    //          "registerInfoId": "20c600072-8e0e-429c-a6f5-768a169c8c6a",
    //          "appId": "20c600072-8e0e-429c-a6f5-768a169c8c6a",
    //          "isDownload": 0,
    //          "developers": "多维实战平台",
    //          "applicationName": "平台应用管理"
    //        },
    //        {
    //          "labelId": 8,
    //          "registerInfoId": "216c566f0-6c89-44cb-a12d-2a4c53dfcc01",
    //          "appId": "216c566f0-6c89-44cb-a12d-2a4c53dfcc01",
    //          "isDownload": 0,
    //          "developers": "多维实战平台",
    //          "applicationName": "全息搜索"
    //        },
    //        {
    //          "labelId": 8,
    //          "registerInfoId": "24c1c20cf-5164-4779-9bdd-d3cd811a485e",
    //          "appId": "24c1c20cf-5164-4779-9bdd-d3cd811a485e",
    //          "isDownload": 0,
    //          "developers": "人像大数据",
    //          "applicationName": "全要素检索"
    //        },
    //        {
    //          "labelId": 8,
    //          "registerInfoId": "24ffca988-922d-4956-9a1a-ee1a371f87f1",
    //          "appId": "24ffca988-922d-4956-9a1a-ee1a371f87f1",
    //          "isDownload": 0,
    //          "developers": "响水全生命周期管理平台",
    //          "applicationName": "响水全生命周期管理平台"
    //        },
    //        {
    //          "labelId": 8,
    //          "registerInfoId": "2e13b1ecd-cf87-4cb0-8839-2e159f7da026",
    //          "appId": "2e13b1ecd-cf87-4cb0-8839-2e159f7da026",
    //          "isDownload": 0,
    //          "developers": "罗普特",
    //          "applicationName": "视图库"
    //        }
    //      ],
    //      "id": 8,
    //      "label": "运维管理"
    //    },
    //    {
    //      "children": [
    //        {
    //          "labelId": 9,
    //          "registerInfoId": "2117c1098-758c-4e21-af6f-fc9317911089",
    //          "appId": "2117c1098-758c-4e21-af6f-fc9317911089",
    //          "isDownload": 0,
    //          "developers": "视频监控实战应用",
    //          "applicationName": "视频监控实战应用"
    //        },
    //        {
    //          "labelId": 9,
    //          "registerInfoId": "25c2ad197-4fd2-408a-9016-7aaf5bcd0e98",
    //          "appId": "25c2ad197-4fd2-408a-9016-7aaf5bcd0e98",
    //          "isDownload": 0,
    //          "developers": "视频图像侦查",
    //          "applicationName": "视频图像侦查"
    //        },
    //        {
    //          "labelId": 9,
    //          "registerInfoId": "26b267a0a-3072-4cdd-9cc9-dced6709156b",
    //          "appId": "26b267a0a-3072-4cdd-9cc9-dced6709156b",
    //          "isDownload": 0,
    //          "developers": "视频联网平台",
    //          "applicationName": "视频联网平台"
    //        },
    //        {
    //          "labelId": 9,
    //          "registerInfoId": "2b454d1bf-36be-4cf8-882c-537ae1a5cb05",
    //          "appId": "2b454d1bf-36be-4cf8-882c-537ae1a5cb05",
    //          "isDownload": 0,
    //          "developers": "视频结构化",
    //          "applicationName": "视频结构化"
    //        }
    //      ],
    //      "id": 9,
    //      "label": "视频应用"
    //    },
//        {
//          "children": [
//            {
//              "labelId": 10,
//              "registerInfoId": "207411205-c33d-4bcf-9205-7e963e9af6dc",
//              "appId": "207411205-c33d-4bcf-9205-7e963e9af6dc",
//              "isDownload": 0,
//              "developers": "可视化指挥平台",
//              "applicationName": "可视化指挥平台"
//            },
//            {
//              "labelId": 10,
//              "registerInfoId": "2a859e848-577d-401c-99e1-564f735c05de",
//              "appId": "2a859e848-577d-401c-99e1-564f735c05de",
//              "isDownload": 0,
//              "developers": "主题库管理",
//              "applicationName": "主题库管理"
//            },
//            {
//              "labelId": 10,
//              "registerInfoId": "2ad3aef94-6c53-453f-b8ad-b7f741f1b88e",
//              "appId": "2ad3aef94-6c53-453f-b8ad-b7f741f1b88e",
//              "isDownload": 0,
//              "developers": "接口服务管理",
//              "applicationName": "接口服务管理"
//            },
//            {
//              "labelId": 10,
//              "registerInfoId": "2c7df305d-556f-4493-a7c4-9ce8b59d9418",
//              "appId": "2c7df305d-556f-4493-a7c4-9ce8b59d9418",
//              "isDownload": 0,
//              "developers": "数据治理",
//              "applicationName": "数据治理"
//            },
//            {
//              "labelId": 10,
//              "registerInfoId": "2d745a4fe-cce8-48e6-8b90-8c2bfc69244f",
//              "appId": "2d745a4fe-cce8-48e6-8b90-8c2bfc69244f",
//              "isDownload": 0,
//              "developers": "车辆大数据平台",
//              "applicationName": "车辆卡口流量统计"
//            }
//          ],
//          "id": 10,
//          "label": "数据治理"
//        },
//        {
//          "children": [],
//          "id": 11,
//          "label": "系统管理"
//        }
//      ]
//    }
    //http://localhost:8999/portal/appcenter/listAppGroupLabel
    //{"applicationName":"车"}
    @PostMapping("/listAppGroupLabel")
    public HttpResult listAppGroupLabel(@RequestBody Map req) {

        return HttpResult.ok(appCenterService.listAppGroupLabel(req));
    }
}
