package com.xiaohao.ueditor.entity;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaohao on 2014/8/12.
 * 用来描述多图上传框中 查看图片列表的返回json
 */
public class ListImgState {

    /**
     * 状态
     */
    private String state;

    /**
     * 开始
     */
    private int start;

    /**
     * 总数
     */
    private int total;

    /**
     * 列表显示的文件信息
     */
    private List<ListImgItemState> list = new ArrayList<ListImgItemState>();

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListImgItemState> getList() {
        return list;
    }

    public void setList(List<ListImgItemState> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListImgState that = (ListImgState) o;

        if (start != that.start) return false;
        if (total != that.total) return false;
        if (!list.equals(that.list)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = start;
        result = 31 * result + total;
        result = 31 * result + list.hashCode();
        return result;
    }

    public String toJsonString(){
        JSONObject jsonObject= JSONObject.fromObject(this);
        return jsonObject.toString();
    }

}


