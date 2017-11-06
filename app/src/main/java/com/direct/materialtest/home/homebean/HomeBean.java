package com.direct.materialtest.home.homebean;

/**
 * Created by HeQiang on 2017/10/23 0023.
 */

public class HomeBean {
    private int classImg;
    private String className;

    public HomeBean(String name,int imageId){
        this.className=name;
        this.classImg=imageId;
    }

    public int getClassImg() {
        return classImg;
    }

    public void setClassImg(int classImg) {
        this.classImg = classImg;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
