package com.slzh.service.datasource;

import com.slzh.dao.datasource.DataItemConfigMapper;
import com.slzh.dao.datasource.DataItemRelationMapper;
import com.slzh.dao.datasource.DataSetConfigMapper;
import com.slzh.exception.ForbiddenException;
import com.slzh.exception.InternalServerException;
import com.slzh.model.datasource.DataItemConfig;
import com.slzh.model.datasource.DataItemRelation;
import com.slzh.model.datasource.DataSetConfig;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.MybatisPageHelper;
import com.slzh.model.page.PageRequest;
import com.slzh.model.page.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class DataSetService {
    private static final Logger log = LoggerFactory.getLogger(DataSetService.class);

    @Autowired
    private DataSetConfigMapper dataSetConfigMapper;


    @Autowired
    private DataItemRelationMapper dataItemRelationMapper;

    @Autowired
    private DataItemConfigMapper dataItemConfigMapper;

    /**
     * 添加数据集
     *
     * @param dataSetConfig
     * @return
     */

    public HttpResult addDataSet(DataSetConfig dataSetConfig) {
        int i = dataSetConfigMapper.insertSelective(dataSetConfig);
        if (i < 0) {
            throw new InternalServerException("插入失败，请重试");
        }
        if (!CollectionUtils.isEmpty(dataSetConfig.getDataItemRelationList())) {
            for (DataItemRelation dataItemRelation : dataSetConfig.getDataItemRelationList()) {
                dataItemRelation.setDataSet(dataSetConfig.getDataSet());
            }
            dataItemRelationMapper.insertDataItemRelationBybatch(dataSetConfig.getDataItemRelationList());
        }
        return HttpResult.ok("插入成功");
    }


    public HttpResult findPageDataSetConfig(PageRequest pageRequest) {
        PageResult result = MybatisPageHelper.findPage(pageRequest, dataSetConfigMapper, "listDataSetConfig", pageRequest.getParams());

        return HttpResult.ok(result);
    }

    public HttpResult deleteDataSetConfig(List<Long> ids) {
        dataSetConfigMapper.deleteDataSetConfig(ids);
        return HttpResult.ok();
    }

    public HttpResult updateDataSetConfig(DataSetConfig dataSetConfig) {

        dataSetConfigMapper.updateByPrimaryKeySelective(dataSetConfig);
        return HttpResult.ok();
    }

    /**
     * @param { dataSetId:
     *          dataItemRelationIdList:
     *          dataItemRelationList:
     *          status:1：刪除，2：添加，3：修改
     *          }
     * @return
     */
    public HttpResult manageDataItemRelation(Map<String, Object> req) {

        if (req.get("status") == null) {
            throw new ForbiddenException("未传入修改状态位，修改失败");
        }


        int status = (Integer) req.get("status");
        switch (status) {
            case 1:
                if (req.get("dataItemRelationIdList") == null) {
                    throw new ForbiddenException("未传入修改的itemId，操作失败");
                }
                List<Long> ids = (List<Long>) req.get("dataItemRelationIdList");
                dataItemRelationMapper.deleteDataItemRelationBybatch(ids);
                break;
            case 2:
                if (req.get("dataItemRelationList") == null) {
                    throw new ForbiddenException("未传入dataItemList，操作失败");
                }
                List<DataItemRelation> dataItemRelationList = (List<DataItemRelation>) req.get("dataItemRelationList");
                dataItemRelationMapper.insertDataItemRelationBybatch(dataItemRelationList);
                break;
            case 3:
                if (req.get("dataItemRelationList") == null) {
                    throw new ForbiddenException("未传入dataItemList，操作失败");
                }
                List<DataItemRelation> dataItemRelationList2 = (List<DataItemRelation>) req.get("dataItemRelationList");
                dataItemRelationMapper.updateDataItemRelationBybatch(dataItemRelationList2);
                break;
            default:
                throw new InternalServerException("传入：" + status + " 修改状态位有误，操作失败");
        }
        return HttpResult.ok();
    }


    public HttpResult findPageDataItemRelation(PageRequest pageRequest) {
        PageResult result = MybatisPageHelper.findPage(pageRequest, dataItemConfigMapper, "listDataItemRelation", pageRequest.getParams());

        return HttpResult.ok(result);
    }


    public HttpResult addDataItemConfig(DataItemConfig dataItemConfig) {
        dataItemConfigMapper.insertSelective(dataItemConfig);
        return HttpResult.ok();
    }

    public HttpResult findPageDataItemConfig(PageRequest pageRequest) {
        PageResult result = MybatisPageHelper.findPage(pageRequest, dataItemConfigMapper, "listDataItemConfig", pageRequest.getParams());

        return HttpResult.ok(result);
    }

    public HttpResult addDataItemConfigBybatch(List<DataItemConfig> dataItemConfigs) {
        dataItemConfigMapper.insertDataItemBybatch(dataItemConfigs);
        return HttpResult.ok();
    }

    public HttpResult updateDataItemConfig(DataItemConfig dataItemConfig) {
        dataItemConfig.setDel(0);
        dataItemConfigMapper.updateByPrimaryKeySelective(dataItemConfig);
        return HttpResult.ok();
    }

    public HttpResult deleteDataItemConfig(List<Long> ids) {
        dataItemConfigMapper.deleteDataItemConfigBybatch(ids);
        return HttpResult.ok();
    }
}
