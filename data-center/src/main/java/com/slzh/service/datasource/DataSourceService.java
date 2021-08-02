package com.slzh.service.datasource;

import com.alibaba.fastjson.JSON;
import com.slzh.dao.datasource.DataSourceConfigMapper;
import com.slzh.exception.ForbiddenException;
import com.slzh.exception.InternalServerException;

import com.slzh.model.DataBase;
import com.slzh.model.config.datasource.rdbms.mysql.MySQLDataSourceConfig;
import com.slzh.model.datasource.DataSourceConfig;
import com.slzh.model.http.HttpRequest;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.MybatisPageHelper;
import com.slzh.model.page.PageRequest;
import com.slzh.model.page.PageResult;
import com.slzh.utils.CodeUtils;
import com.slzh.utils.HttpClientUtils;
import com.slzh.utils.ParamCheckUtils;
import com.slzh.utils.StringUtils;

import com.slzh.utils.db.DataSourceUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;


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
            case 4:
            case 5:
                // 文件，直接插入
                int j = dataSourceConfigMapper.insertSelective(dataSourceConfig);
                if (j < 0) {
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

    public HttpResult interfaceCall(HttpRequest request) throws Exception {
        String method = request.getMethod();
        String url = request.getUrl();
        ParamCheckUtils.stringBlankCheck(method, "缺少请求方法");
        ParamCheckUtils.stringBlankCheck(url, "缺少请求url");
        HttpResult res;
        String responseMsg = "";
        if("get".equals(method.toLowerCase(Locale.ROOT))) {
            responseMsg = HttpClientUtils.doGet(request.getUrl(), request.getHeaders(), request.getParams());
        } else if("post".equals(method.toLowerCase(Locale.ROOT))) {
            responseMsg = HttpClientUtils.doPost(request.getUrl(), request.getHeaders(), request.getParams(), ContentType.APPLICATION_JSON);
        }
        // 解析json 如果解析异常直接将response返回
        try {
            Object parse = JSON.parse(responseMsg);
            return HttpResult.ok(parse);
        } catch (Exception e) {
            return HttpResult.ok(responseMsg);
        }
    }
}
