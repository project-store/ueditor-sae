package com.xiaohao.ueditor.entity;

import java.io.Serializable;

/**
 * Created by xiaohao on 2014/8/13.
 */
public class ListFileItemState implements Serializable {

    /**
     * 地址
     */
    private String url;


    /**
     * 时间
     */
    private long mtime;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getMtime() {
        return mtime;
    }

    public void setMtime(long mtime) {
        this.mtime = mtime;
    }
}
