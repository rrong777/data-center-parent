package com.slzh.service.datasource;

import com.slzh.dao.datasource.DataSourceConfigMapper;
import com.slzh.exception.ForbiddenException;
import com.slzh.exception.InternalServerException;

import com.slzh.model.DataBase;
import com.slzh.model.config.datasource.rdbms.mysql.MySQLDataSourceConfig;
import com.slzh.model.datasource.DataSourceConfig;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.MybatisPageHelper;
import com.slzh.model.page.PageRequest;
import com.slzh.model.page.PageResult;
import com.slzh.utils.CodeUtils;
import com.slzh.utils.StringUtils;

import com.slzh.utils.db.DataSourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


@Service
public class DataSourceService {
    private static final Logger log = LoggerFactory.getLogger(DataSourceService.class);

    @Autowired
    DataSourceConfigMapper dataSourceConfigMapper;

    public HttpResult addDataSource(DataSourceConfig dataSourceConfig) {
        int sourceType = dataSourceConfig.getDataSourceType();
        switch (sourceType) {
            case 1:
                if (StringUtils.isBlank(dataSourceConfig.getPassWord())) {
                    throw new ForbiddenException("入参错误，连接密码不能为空");
                }
                try {
                    dataSourceConfig.setPassWord(CodeUtils.strEncode(dataSourceConfig.getPassWord()));
                } catch (Exception e) {
                    log.error("加密密码报错：");
                    e.printStackTrace();
                }
                int i = dataSourceConfigMapper.insertSelective(dataSourceConfig);
                if (i < 0) {
                    throw new InternalServerException("插入失败，请重试");
                }
                break;
        }

        return HttpResult.ok();
    }


    public HttpResult findPageDataSourceConfig(PageRequest pageRequest) {
        PageResult result = MybatisPageHelper.findPage(pageRequest, dataSourceConfigMapper, "listDataSourceConfig", pageRequest.getParams());
        if (result != null && !CollectionUtils.isEmpty(result.getContent())) {
            List<DataSourceConfig> dataSourceConfigs = (List<DataSourceConfig>) result.getContent();
            for (DataSourceConfig dataSourceConfig : dataSourceConfigs) {
                try {
                    dataSourceConfig.setPassWord(CodeUtils.strDecode(dataSourceConfig.getPassWord()));
                } catch (Exception e) {
                    log.info("解密密码错误：");
                    e.printStackTrace();
                }
            }
        }
        return HttpResult.ok(result);
    }

    public HttpResult deleteDataSourceConfig(List<Long> ids) {
        dataSourceConfigMapper.deleteDataSourceConfig(ids);
        return HttpResult.ok();
    }

    public HttpResult updateDataSourceConfig(DataSourceConfig dataSourceConfig) {

        dataSourceConfigMapper.updateByPrimaryKeySelective(dataSourceConfig);
        return HttpResult.ok();
    }


    public HttpResult getConnectionDetails(MySQLDataSourceConfig dataSourceConfig) throws SQLException, ClassNotFoundException {
        String url = dataSourceConfig.getUrl();
        String user = dataSourceConfig.getUser();
        String password = dataSourceConfig.getPassword();
        Connection connection = DataSourceUtils.getConnection(dataSourceConfig.getDriverClassName(), url, user, password);
        List<DataBase> connectionDetails = DataSourceUtils.getConnectionDetails(connection);
        return HttpResult.ok(connectionDetails);
    }
}
