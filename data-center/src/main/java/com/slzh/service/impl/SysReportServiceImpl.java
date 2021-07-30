package com.slzh.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.slzh.dao.SysReportMapper;
import com.slzh.exception.InternalServerException;
import com.slzh.model.SysReport;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.MybatisPageHelper;
import com.slzh.model.page.PageRequest;
import com.slzh.model.page.PageResult;
import com.slzh.service.SysReportService;
import com.slzh.utils.ParamsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

@Service
public class SysReportServiceImpl implements SysReportService {
    @Autowired
    private SysReportMapper sysReportMapper;

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Map<String, Object> params = pageRequest.getParams();
        ParamsUtils.checkParams(params, "startTime", "endTime");
        PageResult pageResult = MybatisPageHelper.findPage(pageRequest, sysReportMapper, "findPage", params);
        List<SysReport> content = (List<SysReport>) pageResult.getContent();
        return pageResult;
    }

    @Override
    public HttpResult receiveReport(SysReport report) throws Exception {
        String reportName = report.getReportName();
        String yDatas = report.getyDatas();
        Integer type = report.getType();
        Assert.hasText(reportName, "缺少必传参数：reportName");
        Assert.hasText(yDatas, "缺少必传参数：yDatas");
        Assert.notNull(type, "缺少必传参数：type");
        Assert.hasText(report.getDataOrigin(), "缺少必传参数：dataOrigin");
        Assert.hasText(report.getDataPusher(), "缺少必传参数：dataPusher");
        if(type == 2 || type == 3) {
            String xData = report.getxData();
            Assert.notNull(xData, "缺少必传参数：xData");
            checkReportDataForType23(report);
        } else {
            checkReportDataForType1(report);
        }
        sysReportMapper.receiveReport(report);
        return HttpResult.ok();
    }

    private void checkReportDataForType1(SysReport report) throws Exception {
        String dataName = "yDatas";
        String ydata = report.getyDatas();
        if(StringUtils.isBlank(ydata)) {
            throw new Exception(dataName + "不能为空");
        }
        JSONObject yDataJson = isJson(ydata, dataName);
        try {
            int size = yDataJson.size();
            if(size != 1) {
                throw new InternalServerException(dataName + "数据不合法!");
            }
            for(String key : yDataJson.keySet()) {
                // 转换异常。抛异常
                JSONArray xDataVals = (JSONArray) yDataJson.get(key);
                // 数组长度 = 0  抛异常；
                int valsSize = xDataVals.size();
                if(valsSize == 0) {
                    throw new InternalServerException(dataName + "数组不合法!");
                }
                // 循环遍历data数组，判断data数组是否是 name 和val的map
                for(int i = 0; i < valsSize; i++) {
                    // 如果转换异常就抛异常
                    JSONObject tempObj = (JSONObject) xDataVals.get(i);
                    String name = (String) tempObj.get("name");
                    Integer value = (Integer) tempObj.get("value");
                    if(StringUtils.isBlank(name)) {
                        throw new InternalServerException(dataName + "中name不能为空！");
                    }
                    if(value == null) {
                        throw new InternalServerException(dataName + "中value不能为空");
                    }
                }
            }
        } catch (InternalServerException e){
            throw e;
        } catch (Exception e) {
            throw new Exception("解析饼图数据出错！");
        }
    }
    /**
     * type1、2为折线图、判断折线图的数据是否合法
     * @param report
     * @return
     * @throws Exception
     */
    private void checkReportDataForType23(SysReport report) throws Exception {
        // 如果可以转为JSON。""可以parse为null  1可以parse为1,{}可以parse为 size=0的
        Integer xSize = validateXData(report.getxData());
        validateYData(report.getyDatas(), xSize);
    }

    /**
     * 判断jsonArray是不是纯数字
     * @param jsonArray
     * @throws Exception
     */
    public void checkJsonArrayIsIntegerList(JSONArray jsonArray) throws InternalServerException {
        for(int i = 0; i < jsonArray.size(); i++) {
            Object element = jsonArray.get(i);
            if(!(element instanceof Number)) {
                throw new InternalServerException("yDatas数组中的元素只能是数字");
            }
        }

    }
    private Boolean validateYData(String yData, Integer xSize) throws Exception {
        String dataName = "yDatas";

        // size 横坐标有且只能有一组数据
        if(StringUtils.isBlank(yData)) {
            throw new Exception(dataName + "不能为空");
        }
        JSONObject yDataJson = isJson(yData, dataName);

        try {
            // 如果是null 抛异常，size 大于1 抛异常
            int size = yDataJson.size();
            if(size == 0) {
                throw new InternalServerException(dataName + "数据不合法!");
            }
            for(String key : yDataJson.keySet()) {
                // 转换异常。抛异常
                JSONArray xDataVals = (JSONArray) yDataJson.get(key);
                // 数组长度 = 0  抛异常；
                int valsSize = xDataVals.size();
                // 检查是不是纯数字
                this.checkJsonArrayIsIntegerList(xDataVals);
                if(valsSize == 0) {
                    throw new InternalServerException(dataName + "数组不合法!");
                }


                // yData 的数组长度要求一样
                if(valsSize != xSize) {
                    throw new InternalServerException(dataName + "所有数据数组长度应一致！且要与xData数组长度一致");
                }

            }

        } catch (InternalServerException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception(dataName + "解析出现异常");
        }
        return true;
    }
    private Integer validateXData(String xData) throws Exception {
        String dataName = "xData";
        int valsSize = 0;
        // size 横坐标有且只能有一组数据
        if(StringUtils.isBlank(xData)) {
            throw new Exception(dataName + "不能为空");
        }
        JSONObject xDataJson = isJson(xData, dataName);

        try {
            // 如果是null 抛异常，size 大于1 抛异常
            int size = xDataJson.size();
            if(size != 1) {
                throw new InternalServerException(dataName + "数据不合法!");
            }
            for(String key : xDataJson.keySet()) {
                // 转换异常。抛异常
                JSONArray xDataVals = (JSONArray) xDataJson.get(key);
                // 数组长度 = 0  抛异常；
                valsSize = xDataVals.size();
                if(valsSize == 0) {
                    throw new InternalServerException(dataName + "数组不合法!");
                }
            }
            return valsSize;
        } catch (InternalServerException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception(dataName + "解析出现异常");
        }

    }
    /**
     * 不能是 ""空串 不能是null  不能是 1 这种
     * 只能接收简单的，横坐标只能是1 个 比如日期
     * @param str
     * @return
     */
    private JSONObject isJson(String str, String dataName) throws Exception {
        try {
            JSONObject parse = (JSONObject) JSONObject.parse(str);
            return parse;
        } catch (Exception e) {
            // 有异常。转换失败。或者 1  这些无法转换的。也会有异常
            throw new Exception(dataName + "解析JSON异常");
        }
    }
}
