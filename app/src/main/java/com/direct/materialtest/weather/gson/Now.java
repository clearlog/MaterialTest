package com.direct.materialtest.weather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/9/19 0019.
 */

public class Now {
    @SerializedName("tmp")
    public String tmperature;
    @SerializedName("cond")
    public More more;
    public class More{
        @SerializedName("txt")
        public String info;
    }
}
