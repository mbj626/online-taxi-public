package com.mbj.servicemap.service;

import com.mbj.internalcommmon.constant.AmapConstant;
import com.mbj.internalcommmon.constant.CommonStatusEnum;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.servicemap.dto.DicDistrict;
import com.mbj.servicemap.mapper.DicDistrictMapper;
import com.mbj.servicemap.remote.MapDicDistrictClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-27 22:29
 * @Description:
 * @Version:
 */
@Service
public class DicDistrictService {

    @Autowired
    MapDicDistrictClient mapDicDistrictClient;

    @Autowired
    DicDistrictMapper dicDistrictMapper;

    public ResponseResult dicDistrict(String keywords){
        // 请求地区
        String dicDistrictResult = mapDicDistrictClient.initDicDistrict(keywords);
        // 解析结果
        JSONObject countryJSONObject = JSONObject.fromObject(dicDistrictResult);
        int status = countryJSONObject.getInt(AmapConstant.STATUS);
        if (status != 1){
            return ResponseResult.fail(CommonStatusEnum.MAP_DISTRICT_ERROR.getCode(),CommonStatusEnum.MAP_DISTRICT_ERROR.getValue());
        }
        JSONArray countryJsonArray = countryJSONObject.getJSONArray(AmapConstant.DISTRICTS);
        for (int i = 0; i < countryJsonArray.size(); i++) {
            JSONObject countryObject = countryJsonArray.getJSONObject(i);
            String countryAddressCode = countryObject.getString(AmapConstant.ADCODE);
            String countryName = countryObject.getString(AmapConstant.NAME);
            String countryParentAddressCode = "0";
            String countryLevel = countryObject.getString(AmapConstant.LEVEL);
            // 存入数据库
            insertDicDistrict(countryAddressCode,countryName,countryLevel,countryParentAddressCode);

            JSONArray provinceJsonArray = countryObject.getJSONArray(AmapConstant.DISTRICTS);
            for (int p = 0; p < provinceJsonArray.size(); p++) {
                JSONObject provinceObject = provinceJsonArray.getJSONObject(p);
                String provinceAddressCode = provinceObject.getString(AmapConstant.ADCODE);
                String provinceName = provinceObject.getString(AmapConstant.NAME);
                String provinceParentAddressCode = countryAddressCode;
                String provinceLevel = provinceObject.getString(AmapConstant.LEVEL);
                // 存入数据库
                insertDicDistrict(provinceAddressCode, provinceName, provinceLevel, provinceParentAddressCode);

                JSONArray cityJsonArray = provinceObject.getJSONArray(AmapConstant.DISTRICTS);
                for (int c = 0; c < cityJsonArray.size(); c++) {
                    JSONObject cityObject = cityJsonArray.getJSONObject(c);
                    String cityAddressCode = cityObject.getString(AmapConstant.ADCODE);
                    String cityName = cityObject.getString(AmapConstant.NAME);
                    String cityParentAddressCode = provinceAddressCode;
                    String cityLevel = cityObject.getString(AmapConstant.LEVEL);

                    // 存入数据库
                    insertDicDistrict(cityAddressCode, cityName, cityLevel, cityParentAddressCode);

                    JSONArray districtJsonArray = cityObject.getJSONArray(AmapConstant.DISTRICTS);
                    for (int d = 0; d < districtJsonArray.size(); d++) {
                        JSONObject districtObject = districtJsonArray.getJSONObject(d);
                        String districtAddressCode = districtObject.getString(AmapConstant.ADCODE);
                        String districtName = districtObject.getString(AmapConstant.NAME);
                        String districtParentAddressCode = cityAddressCode;
                        String districtLevel = districtObject.getString(AmapConstant.LEVEL);

                        if(districtLevel.equals(AmapConstant.STREET)){
                            continue;
                        }

                        // 存入数据库
                        insertDicDistrict(districtAddressCode, districtName, districtLevel, districtParentAddressCode);
                    }
                }
            }
        }


        return ResponseResult.success(dicDistrictResult);
    }

    public void insertDicDistrict(String addressCode,String name,String level,String parentAddressCode){
        DicDistrict dicDistrict = new DicDistrict();
        dicDistrict.setAddressCode(addressCode);
        dicDistrict.setAddressName(name);
        int levelInt = generateLevel(level);
        dicDistrict.setLevel(levelInt);
        dicDistrict.setParentAddressCode(parentAddressCode);
        dicDistrictMapper.insert(dicDistrict);
    }

    public int generateLevel(String level){
        int levelInt = 0;
        switch (level.trim()) {
            case "country":
                levelInt = 0;
                break;
            case "province":
                levelInt = 1;
                break;
            case "city":
                levelInt = 2;
                break;
            case "district":
                levelInt = 3;
                break;
        }
        return levelInt;
    }

}
