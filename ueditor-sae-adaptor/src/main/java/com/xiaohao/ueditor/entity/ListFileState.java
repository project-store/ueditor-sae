package com.xiaohao.ueditor.entity;

import net.sf.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaohao on 2014/8/13
 * 用来返回描述附件列表url
 */
public class ListFileState implements Serializable {

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
     *列表显示的文件
     */
    private List<ListFileItemState> list = new ArrayList<ListFileItemState>();


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

    public List<ListFileItemState> getList() {
        return list;
    }

    public void setList(List<ListFileItemState> list) {
        this.list = list;
    }
    public String toJsonString(){
        JSONObject jsonObject= JSONObject.fromObject(this);
        return jsonObject.toString();
    }
}
