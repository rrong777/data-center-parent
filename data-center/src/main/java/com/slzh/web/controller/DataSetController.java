package com.slzh.web.controller;

import com.slzh.model.datasource.DataItemConfig;
import com.slzh.model.datasource.DataItemRelation;
import com.slzh.model.datasource.DataSetConfig;
import com.slzh.model.datasource.DataSetConfig;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.PageRequest;
import com.slzh.service.datasource.DataSetService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dataSet")
public class DataSetController {
    @Autowired
    private DataSetService dataSetService;

    /**
     * 分页查询应用中心
     *
     * @return
     */
    @PostMapping("/findPage")
    public HttpResult listDataSetConfig(@RequestBody PageRequest pageRequest) {
        return dataSetService.findPageDataSetConfig(pageRequest);
    }

    @PostMapping("/add")
    public HttpResult addDataSetConfig(@RequestBody DataSetConfig dataSetConfig) {
        return dataSetService.addDataSet(dataSetConfig);
    }

    @PostMapping("/update")
    public HttpResult updateDataSetConfig(@RequestBody DataSetConfig dataSetConfig) {
        return dataSetService.updateDataSetConfig(dataSetConfig);
    }

    @DeleteMapping("/delete")
    public HttpResult deleteDataSetConfig(@RequestBody List<Long> ids) {
        return dataSetService.deleteDataSetConfig(ids);
    }

    @PostMapping("/manage")
    public HttpResult manageDataSetConfig(@RequestBody Map<String, Object> req) {
        return dataSetService.manageDataItemRelation(req);
    }

    @PostMapping("/dataItemRelation/findPage")
    public HttpResult findPagedataItemRelation(@RequestBody PageRequest pageRequest) {
        return dataSetService.findPageDataItemRelation(pageRequest);
    }

    @DeleteMapping("/dataItem/delete")
    public HttpResult addDataItemRelation(@RequestBody List<Long> ids) {
        return dataSetService.deleteDataItemConfig(ids);
    }
    @PostMapping("/dataItem/findPage")
    public HttpResult findPageDataItemConfig(@RequestBody PageRequest pageRequest) {
        return dataSetService.findPageDataItemConfig(pageRequest);
    }

    @PostMapping("/dataItem/update")
    public HttpResult updateDataItemConfig(@RequestBody DataItemConfig req) {
        return dataSetService.updateDataItemConfig(req);
    }

    @PostMapping("/dataItem/addDataItemConfigBybatch")
    public HttpResult addDataItemConfigBybatch(@RequestBody List<DataItemConfig> dataItemConfigs) {
        return dataSetService.addDataItemConfigBybatch(dataItemConfigs);
    }

}
