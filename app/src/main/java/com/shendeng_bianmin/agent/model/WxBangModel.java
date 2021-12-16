package com.shendeng_bianmin.agent.model;

import java.util.List;

public class WxBangModel {

    /**
     * msg_code : 0000
     * msg : ok
     * data : [{"wx_store_user_name":"咕哒咕哒王","sms_id":"4759","code":"04184","wx_store_img_url":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqEiba4icVKvHtPyGVNN4qdbqESFkrVYvNKbHMAFeuhYsfagzxnVSKxN5LlO1qAmIxVujpcsjAEQFVw/132","sms_code":"9914","js_code":"071cln1J03hm3d21Jd3J0bpm1J0cln1f","wx_store_app_id":"o6HIyuLQjogPOBpV_jemBl2q168w","key":"20180305124455yu","token":"1595042h35372100q000p000H000Q0"}]
     */

    private String msg_code;
    private String msg;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * wx_store_user_name : 咕哒咕     哒王
         * sms_id : 4759
         * code : 04184
         * wx_store_img_url : http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqEiba4icVKvHtPyGVNN4qdbqESFkrVYvNKbHMAFeuhYsfagzxnVSKxN5LlO1qAmIxVujpcsjAEQFVw/132
         * sms_code : 9914
         * js_code : 071cln1J03hm3d21Jd3J0bpm1J0cln1f
         * wx_store_app_id : o6HIyuLQjogPOBpV_jemBl2q168w
         * key : 20180305124455yu
         * token : 1595042h35372100q000p000H000Q0
         */

        private String wx_store_user_name;
        private String sms_id;
        private String code;
        private String wx_store_img_url;
        private String sms_code;
        private String js_code;
        private String wx_store_app_id;
        private String key;
        private String token;

        public String getWx_store_user_name() {
            return wx_store_user_name;
        }

        public void setWx_store_user_name(String wx_store_user_name) {
            this.wx_store_user_name = wx_store_user_name;
        }

        public String getSms_id() {
            return sms_id;
        }

        public void setSms_id(String sms_id) {
            this.sms_id = sms_id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getWx_store_img_url() {
            return wx_store_img_url;
        }

        public void setWx_store_img_url(String wx_store_img_url) {
            this.wx_store_img_url = wx_store_img_url;
        }

        public String getSms_code() {
            return sms_code;
        }

        public void setSms_code(String sms_code) {
            this.sms_code = sms_code;
        }

        public String getJs_code() {
            return js_code;
        }

        public void setJs_code(String js_code) {
            this.js_code = js_code;
        }

        public String getWx_store_app_id() {
            return wx_store_app_id;
        }

        public void setWx_store_app_id(String wx_store_app_id) {
            this.wx_store_app_id = wx_store_app_id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
