package com.shendeng_bianmin.agent.model;

import java.util.List;

public class JiesuanModel {

    /**
     * next : 0
     * msg_code : 0000
     * msg : ok
     * row_num : 7
     * data : [{"shop_form_id":"3046","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9931","pay_money":"0.50","user_name":"阿钊","pay_time":"2020-06-20 12:42:14"},{"shop_form_id":"3044","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9931","pay_money":"0.50","user_name":"阿钊","pay_time":"2020-06-20 12:24:39"},{"shop_form_id":"3043","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9931","pay_money":"0.50","user_name":"阿钊","pay_time":"2020-06-20 11:55:14"},{"shop_form_id":"1870","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=8572","pay_money":"131.00","user_name":"月亮","pay_time":"2020-04-08 17:18:43"},{"shop_form_id":"1869","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=8572","pay_money":"131.00","user_name":"越加按钮","pay_time":"2020-04-08 17:17:53"},{"shop_form_id":"1818","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9839","pay_money":"190.00","user_name":"未设置","pay_time":"2020-03-30 13:14:51"},{"shop_form_id":"1815","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10105","pay_money":"1500.00","user_name":"月亮","pay_time":"2020-03-30 08:56:34"}]
     */

    private String next;
    private String msg_code;
    private String msg;
    private String row_num;
    private List<DataBean> data;

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

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
         * shop_form_id : 3046
         * index_photo_url : https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9931
         * pay_money : 0.50
         * user_name : 阿钊
         * pay_time : 2020-06-20 12:42:14
         */

        private String shop_form_id;
        private String index_photo_url;
        private String pay_money;
        private String user_name;
        private String pay_time;

        public String getShop_form_id() {
            return shop_form_id;
        }

        public void setShop_form_id(String shop_form_id) {
            this.shop_form_id = shop_form_id;
        }

        public String getIndex_photo_url() {
            return index_photo_url;
        }

        public void setIndex_photo_url(String index_photo_url) {
            this.index_photo_url = index_photo_url;
        }

        public String getPay_money() {
            return pay_money;
        }

        public void setPay_money(String pay_money) {
            this.pay_money = pay_money;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }
    }
}
