package com.shendeng_bianmin.agent.model;

import java.io.Serializable;
import java.util.List;

public class ShangpinDetailsModel1 implements Serializable {


    /**
     * msg_code : 0000
     * msg : ok
     * row_num : 1
     * data : [{"img_url":"http://yjn-znjj.oss-cn-hangzhou.aliyuncs.com/20211217141837000001.jpg"}]
     */

    private String msg_code;
    private String msg;
    private String row_num;
    private List<DataBean> data;

    public String getMsg_code() {
        return msg_code;
    }

    public void setMsg_code(String msg_code) {
        this.msg_code = msg_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRow_num() {
        return row_num;
    }

    public void setRow_num(String row_num) {
        this.row_num = row_num;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * img_url : http://yjn-znjj.oss-cn-hangzhou.aliyuncs.com/20211217141837000001.jpg
         */

        private String img_url;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }
}
