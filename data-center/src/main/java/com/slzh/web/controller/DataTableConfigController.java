package com.slzh.web.controller;

import com.slzh.model.datasource.DataTableConfig;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.PageRequest;
import com.slzh.service.datasource.DataTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dataTableConfig")
public class DataTableConfigController {
    @Autowired
    private DataTableService dataTableservice;

    /**
     * 分页查询应用中心
     *
     * @return
     */
    @PostMapping("/findPage")
    public HttpResult listDataTableConfig(@RequestBody PageRequest pageRequest) {
        return dataTableservice.findPageDataTableConfig(pageRequest);
    }

    @PostMapping("/add")
    public HttpResult addDataTableConfig(@RequestBody DataTableConfig dataTableConfig) {
        return dataTableservice.addDataTable(dataTableConfig);
    }

    @PostMapping("/update")
    public HttpResult updateDataTableConfig(@RequestBody DataTableConfig dataTableConfig) {
        return dataTableservice.updateDataTableConfig(dataTableConfig);
    }

    @DeleteMapping("/delete")
    public HttpResult deleteDataTableConfig(@RequestBody List<Long> ids) {
        return dataTableservice.deleteDataTableConfig(ids);
    }
}
