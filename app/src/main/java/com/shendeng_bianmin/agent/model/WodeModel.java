package com.shendeng_bianmin.agent.model;

import java.util.List;

public class WodeModel {


    /**
     * msg_code : 0000
     * msg : ok
     * row_num : 0
     * data : [{"wx_user_name":"码农＿Sun__17645185187","inst_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10202","wares_collection_count":"0","pay_pwd_check":"1","inst_owner":"1","wx_pay_check":"1","alipay_uname":"岳建男","alipay_number":"18249030297","alipay_number_check":"1","user_phone":"18249030297","inst_id":"187","inst_name":"神灯科技旗舰店","wx_img_url":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epa62UlUY1tgQ19NOVOZVEXE4ESq7m4n2bHbDkVqmXLpqItzR4lrpkNw2tuNYA368NxcTHDedACibQ/132","shop_collection_count":"6","shop_browsing_count":"100"}]
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
         * wx_user_name : 码农＿Sun__17645185187
         * inst_photo_url : https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10202
         * wares_collection_count : 0
         * pay_pwd_check : 1
         * inst_owner : 1
         * wx_pay_check : 1
         * alipay_uname : 岳建男
         * alipay_number : 18249030297
         * alipay_number_check : 1
         * user_phone : 18249030297
         * inst_id : 187
         * inst_name : 神灯科技旗舰店
         * wx_img_url : http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epa62UlUY1tgQ19NOVOZVEXE4ESq7m4n2bHbDkVqmXLpqItzR4lrpkNw2tuNYA368NxcTHDedACibQ/132
         * shop_collection_count : 6
         * shop_browsing_count : 100
         */

        private String wx_user_name;
        private String inst_photo_url;
        private String wares_collection_count;
        private String pay_pwd_check;
        private String inst_owner;
        private String wx_pay_check;
        private String alipay_uname;
        private String alipay_number;
        private String alipay_number_check;
        private String user_phone;
        private String inst_id;
        private String inst_name;
        private String wx_img_url;
        private String shop_collection_count;
        private String shop_browsing_count;

        private String of_user_id;
        private String subsystem_id;

        public String getOf_user_id() {
            return of_user_id;
        }

        public String getSubsystem_id() {
            return subsystem_id;
        }

        public String getWx_user_name() {
            return wx_user_name;
        }

        public void setWx_user_name(String wx_user_name) {
            this.wx_user_name = wx_user_name;
        }

        public String getInst_photo_url() {
            return inst_photo_url;
        }

        public void setInst_photo_url(String inst_photo_url) {
            this.inst_photo_url = inst_photo_url;
        }

        public String getWares_collection_count() {
            return wares_collection_count;
        }

        public void setWares_collection_count(String wares_collection_count) {
            this.wares_collection_count = wares_collection_count;
        }

        public String getPay_pwd_check() {
            return pay_pwd_check;
        }

        public void setPay_pwd_check(String pay_pwd_check) {
            this.pay_pwd_check = pay_pwd_check;
        }

        public String getInst_owner() {
            return inst_owner;
        }

        public void setInst_owner(String inst_owner) {
            this.inst_owner = inst_owner;
        }

        public String getWx_pay_check() {
            return wx_pay_check;
        }

        public void setWx_pay_check(String wx_pay_check) {
            this.wx_pay_check = wx_pay_check;
        }

        public String getAlipay_uname() {
            return alipay_uname;
        }

        public void setAlipay_uname(String alipay_uname) {
            this.alipay_uname = alipay_uname;
        }

        public String getAlipay_number() {
            return alipay_number;
        }

        public void setAlipay_number(String alipay_number) {
            this.alipay_number = alipay_number;
        }

        public String getAlipay_number_check() {
            return alipay_number_check;
        }

        public void setAlipay_number_check(String alipay_number_check) {
            this.alipay_number_check = alipay_number_check;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getInst_id() {
            return inst_id;
        }

        public void setInst_id(String inst_id) {
            this.inst_id = inst_id;
        }

        public String getInst_name() {
            return inst_name;
        }

        public void setInst_name(String inst_name) {
            this.inst_name = inst_name;
        }

        public String getWx_img_url() {
            return wx_img_url;
        }

        public void setWx_img_url(String wx_img_url) {
            this.wx_img_url = wx_img_url;
        }

        public String getShop_collection_count() {
            return shop_collection_count;
        }

        public void setShop_collection_count(String shop_collection_count) {
            this.shop_collection_count = shop_collection_count;
        }

        public String getShop_browsing_count() {
            return shop_browsing_count;
        }

        public void setShop_browsing_count(String shop_browsing_count) {
            this.shop_browsing_count = shop_browsing_count;
        }
    }
}
