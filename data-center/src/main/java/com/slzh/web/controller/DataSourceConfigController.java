package com.slzh.web.controller;

import com.slzh.model.config.datasource.base.AbstractDataSourceConfig;
import com.slzh.model.config.datasource.rdbms.mysql.MySQLDataSourceConfig;
import com.slzh.model.datasource.DataSourceConfig;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.PageRequest;
import com.slzh.model.sso.AppCenter;
import com.slzh.service.datasource.DataSourceService;
import com.slzh.utils.db.DataSourceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/dataSourceConfig")
public class DataSourceConfigController {
    @Autowired
    private DataSourceService dataSourceService;

    @GetMapping("/getDataSourceType")
    public HttpResult getAllDataSourceName() {
        List<String> allDataSource = DataSourceUtils.getDataSources();
        return HttpResult.ok(allDataSource);
    }

    @PostMapping("/getConnectionDetails")
    public HttpResult getConnectionDetails(@RequestBody MySQLDataSourceConfig dataSourceConfig) {
        HttpResult connectionDetails = null;
        try {
            connectionDetails = dataSourceService.getConnectionDetails(dataSourceConfig);
        } catch (Exception e) {
            return HttpResult.error(e.getMessage());
        }
        return connectionDetails;
    }
    /**
     * 分页查询应用中心
     *
     * @return
     */
    @PostMapping("/findPage")
    public HttpResult listDataSourceConfig(@RequestBody PageRequest pageRequest) {
        return dataSourceService.findPageDataSourceConfig(pageRequest);
    }

    @PostMapping("/add")
    public HttpResult addDataSourceConfig(@RequestBody DataSourceConfig dataSourceConfig) {
        return dataSourceService.addDataSource(dataSourceConfig);
    }

    @PostMapping("/update")
    public HttpResult updateDataSourceConfig(@RequestBody DataSourceConfig dataSourceConfig) {
        return dataSourceService.updateDataSourceConfig(dataSourceConfig);
    }

    @DeleteMapping("/delete")
    public HttpResult deleteDataSourceConfig(@RequestBody List<Long> ids) {
        return dataSourceService.deleteDataSourceConfig(ids);
    }
}
