package com.xiaohao.ueditor.entity;

import java.io.Serializable;

/**
 * Created by xiaohao on 2014/8/13.
 *
 */
public class ListImgItemState implements Serializable {


    private String url;

    private String state;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
