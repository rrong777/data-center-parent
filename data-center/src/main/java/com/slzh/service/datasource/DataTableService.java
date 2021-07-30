package com.slzh.service.datasource;

import com.slzh.dao.datasource.DataTableConfigMapper;
import com.slzh.exception.InternalServerException;
import com.slzh.model.datasource.DataTableConfig;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.MybatisPageHelper;
import com.slzh.model.page.PageRequest;
import com.slzh.model.page.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DataTableService {
    private static final Logger log = LoggerFactory.getLogger(DataTableService.class);

    @Autowired
    DataTableConfigMapper dataTableConfigMapper;

    public HttpResult addDataTable(DataTableConfig dataTableConfig) {
        int i = dataTableConfigMapper.insertSelective(dataTableConfig);
        if (i < 0) {
            throw new InternalServerException("插入失败，请重试");
        }
        return HttpResult.ok();
    }

    public HttpResult findPageDataTableConfig(PageRequest pageRequest) {
        PageResult result = MybatisPageHelper.findPage(pageRequest, dataTableConfigMapper, "listDataTableConfig", pageRequest.getParams());
        return HttpResult.ok(result);
    }

    public HttpResult deleteDataTableConfig(List<Long> ids) {
        dataTableConfigMapper.deleteDataTableConfig(ids);
        return HttpResult.ok();
    }

    public HttpResult updateDataTableConfig(DataTableConfig dataTableConfig) {

        dataTableConfigMapper.updateByPrimaryKeySelective(dataTableConfig);
        return HttpResult.ok();
    }

}
