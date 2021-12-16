package com.shendeng_bianmin.agent.model;

import java.util.List;

public class QainbaoModel {


    /**
     * msg_code : 0000
     * msg : ok
     * row_num : 0
     * data : [{"wx_user_name":"码农＿Sun__17645185187","inst_money_cash":"52.10","inst_money_ready":"648.50","pay_name":"岳建男","userName":"神灯科技旗舰店","wx_pay_check":"1","inst_money_access":"1886.78","score_zd":"1","inst_id":"187","pay_num":"18249030297","wx_img_url":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epa62UlUY1tgQ19NOVOZVEXE4ESq7m4n2bHbDkVqmXLpqItzR4lrpkNw2tuNYA368NxcTHDedACibQ/132","pwd_pay":"1","score_tx":"0.1%"}]
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
         * inst_money_cash : 52.10
         * inst_money_ready : 648.50
         * pay_name : 岳建男
         * userName : 神灯科技旗舰店
         * wx_pay_check : 1
         * inst_money_access : 1886.78
         * score_zd : 1
         * inst_id : 187
         * pay_num : 18249030297
         * wx_img_url : http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epa62UlUY1tgQ19NOVOZVEXE4ESq7m4n2bHbDkVqmXLpqItzR4lrpkNw2tuNYA368NxcTHDedACibQ/132
         * pwd_pay : 1
         * score_tx : 0.1%
         */

        private String wx_user_name;
        private String inst_money_cash;
        private String inst_money_ready;
        private String pay_name;
        private String userName;
        private String wx_pay_check;
        private String inst_money_access;
        private String score_zd;
        private String inst_id;
        private String pay_num;
        private String wx_img_url;
        private String pwd_pay;
        private String score_tx;

        public String getWx_user_name() {
            return wx_user_name;
        }

        public void setWx_user_name(String wx_user_name) {
            this.wx_user_name = wx_user_name;
        }

        public String getInst_money_cash() {
            return inst_money_cash;
        }

        public void setInst_money_cash(String inst_money_cash) {
            this.inst_money_cash = inst_money_cash;
        }

        public String getInst_money_ready() {
            return inst_money_ready;
        }

        public void setInst_money_ready(String inst_money_ready) {
            this.inst_money_ready = inst_money_ready;
        }

        public String getPay_name() {
            return pay_name;
        }

        public void setPay_name(String pay_name) {
            this.pay_name = pay_name;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getWx_pay_check() {
            return wx_pay_check;
        }

        public void setWx_pay_check(String wx_pay_check) {
            this.wx_pay_check = wx_pay_check;
        }

        public String getInst_money_access() {
            return inst_money_access;
        }

        public void setInst_money_access(String inst_money_access) {
            this.inst_money_access = inst_money_access;
        }

        public String getScore_zd() {
            return score_zd;
        }

        public void setScore_zd(String score_zd) {
            this.score_zd = score_zd;
        }

        public String getInst_id() {
            return inst_id;
        }

        public void setInst_id(String inst_id) {
            this.inst_id = inst_id;
        }

        public String getPay_num() {
            return pay_num;
        }

        public void setPay_num(String pay_num) {
            this.pay_num = pay_num;
        }

        public String getWx_img_url() {
            return wx_img_url;
        }

        public void setWx_img_url(String wx_img_url) {
            this.wx_img_url = wx_img_url;
        }

        public String getPwd_pay() {
            return pwd_pay;
        }

        public void setPwd_pay(String pwd_pay) {
            this.pwd_pay = pwd_pay;
        }

        public String getScore_tx() {
            return score_tx;
        }

        public void setScore_tx(String score_tx) {
            this.score_tx = score_tx;
        }
    }
}
