/**
 *
 */
package com.shendeng_bianmin.agent.model;

import java.util.List;

/**
 * 登陆用户信息
 * @author yantong
 * 下午3:14:17
 */
public class LoginUser {


    /**
     * msg_code : 0000
     * msg : ok
     * row_num : 0
     * data : [{"of_user_id":"2199","typeList":[{"business_type":"1"},{"business_type":"2"}],"app_token":"1597115k63965100W000L000I000i0","user_name":"juyijia_18249030228","token_rong":"+02RA7sOtVA9Eqa5ksgSgD92dU/6JxiSgOVPlvcfgNXTqmMTNJFbNg==@cd7u.cn.rongnav.com;cd7u.cn.rongcfg.com","accid":"jcz_sub_238","user_img":""}]
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
         * of_user_id : 2199
         * typeList : [{"business_type":"1"},{"business_type":"2"}]
         * app_token : 1597115k63965100W000L000I000i0
         * user_name : juyijia_18249030228
         * token_rong : +02RA7sOtVA9Eqa5ksgSgD92dU/6JxiSgOVPlvcfgNXTqmMTNJFbNg==@cd7u.cn.rongnav.com;cd7u.cn.rongcfg.com
         * accid : jcz_sub_238
         * user_img :
         */

        private String of_user_id;
        private String app_token;
        private String user_name;
        private String token_rong;
        private String accid;
        private String user_img;
        private List<TypeListBean> typeList;

        public String getOf_user_id() {
            return of_user_id;
        }

        public void setOf_user_id(String of_user_id) {
            this.of_user_id = of_user_id;
        }

        public String getApp_token() {
            return app_token;
        }

        public void setApp_token(String app_token) {
            this.app_token = app_token;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getToken_rong() {
            return token_rong;
        }

        public void setToken_rong(String token_rong) {
            this.token_rong = token_rong;
        }

        public String getAccid() {
            return accid;
        }

        public void setAccid(String accid) {
            this.accid = accid;
        }

        public String getUser_img() {
            return user_img;
        }

        public void setUser_img(String user_img) {
            this.user_img = user_img;
        }

        public List<TypeListBean> getTypeList() {
            return typeList;
        }

        public void setTypeList(List<TypeListBean> typeList) {
            this.typeList = typeList;
        }

        public static class TypeListBean {
            /**
             * business_type : 1
             */

            private String business_type;

            public String getBusiness_type() {
                return business_type;
            }

            public void setBusiness_type(String business_type) {
                this.business_type = business_type;
            }
        }
    }
}