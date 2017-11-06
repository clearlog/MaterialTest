package com.direct.materialtest.weather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by HeQiang on 2017/10/14 0014.
 */

public class City extends DataSupport {
    private int id;
    private String cityName;//城市的名字
    private  int cityCode;//城市的代号
    private int provinceId;//当前城市所属省的id值

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
