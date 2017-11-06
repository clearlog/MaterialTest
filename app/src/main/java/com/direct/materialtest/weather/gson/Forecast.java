package com.direct.materialtest.weather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/9/19 0019.
 */

public class Forecast {
    public String date;
    @SerializedName("tmp")
    public Temperture temperture;
    @SerializedName("cond")
    public More more;

    public class Temperture {
        public String max;
        public String min;
    }

    public class More {
        @SerializedName("txt_d")
        public String info;
    }
}
