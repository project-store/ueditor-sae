package com.xiaohao.ueditor.entity;


import net.sf.json.JSONObject;

import java.io.Serializable;

/**
 * Created by xiaohao on 2014/8/12.
 * 用于描述图片上传后返回的状态 上传视频和附件的返回值也是这个 涂鸦的返回值也是这个
 */
public class FileState implements Serializable {

    /**
     * 状态
     */
    private String state;

    /**
     * 标题
     */
    private String title;

    /**
     * 原名
     */
    private String original;

    /**
     * 类型
     */
    private String type;

    /**
     * 地址
     */
    private String url;

    /**
     * 大小
     */
    private long size;


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileState fileState = (FileState) o;

        if (size != fileState.size) return false;
        if (!original.equals(fileState.original)) return false;
        if (!title.equals(fileState.title)) return false;
        if (!type.equals(fileState.type)) return false;
        if (!url.equals(fileState.url)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = url.hashCode();
        result = 31 * result + original.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }

    /**
     * 生成自身的json字符串
     * @return
     */
    public String toJsonString(){
       JSONObject jsonObject= JSONObject.fromObject(this);
        return jsonObject.toString();
    }
}
