package com.shendeng_bianmin.agent.model;

import java.util.List;

public class PingjiaModel {

    /**
     * msg_code : 0000
     * msg : ok
     * row_num : 0
     * data : [{"user_to_text":"","user_to_time":"","inst_img_url":"http://192.168.1.127:8080/manage/subsystem/main/toInstUrl?inst_id=187","shop_product_title":"横版数码按键开关","shop_to_score":"","user_name":"越加按钮","end_time":"2020-07-08 09:23:00","user_img_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9413","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9931","shop_pay_check":"6","product_title":"套餐1","shop_to_text":"","shop_to_time":"","inst_name":"神灯科技旗舰店","user_to_score":""}]
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
         * user_to_text :
         * user_to_time :
         * inst_img_url : http://192.168.1.127:8080/manage/subsystem/main/toInstUrl?inst_id=187
         * shop_product_title : 横版数码按键开关
         * shop_to_score :
         * user_name : 越加按钮
         * end_time : 2020-07-08 09:23:00
         * user_img_url : https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9413
         * index_photo_url : https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9931
         * shop_pay_check : 6
         * product_title : 套餐1
         * shop_to_text :
         * shop_to_time :
         * inst_name : 神灯科技旗舰店
         * user_to_score :
         */

        private String user_to_text;
        private String user_to_time;
        private String inst_img_url;
        private String shop_product_title;
        private String shop_to_score;
        private String user_name;
        private String end_time;
        private String user_img_url;
        private String index_photo_url;
        private String shop_pay_check;
        private String product_title;
        private String shop_to_text;
        private String shop_to_time;
        private String inst_name;
        private String user_to_score;

        public String getUser_to_text() {
            return user_to_text;
        }

        public void setUser_to_text(String user_to_text) {
            this.user_to_text = user_to_text;
        }

        public String getUser_to_time() {
            return user_to_time;
        }

        public void setUser_to_time(String user_to_time) {
            this.user_to_time = user_to_time;
        }

        public String getInst_img_url() {
            return inst_img_url;
        }

        public void setInst_img_url(String inst_img_url) {
            this.inst_img_url = inst_img_url;
        }

        public String getShop_product_title() {
            return shop_product_title;
        }

        public void setShop_product_title(String shop_product_title) {
            this.shop_product_title = shop_product_title;
        }

        public String getShop_to_score() {
            return shop_to_score;
        }

        public void setShop_to_score(String shop_to_score) {
            this.shop_to_score = shop_to_score;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getUser_img_url() {
            return user_img_url;
        }

        public void setUser_img_url(String user_img_url) {
            this.user_img_url = user_img_url;
        }

        public String getIndex_photo_url() {
            return index_photo_url;
        }

        public void setIndex_photo_url(String index_photo_url) {
            this.index_photo_url = index_photo_url;
        }

        public String getShop_pay_check() {
            return shop_pay_check;
        }

        public void setShop_pay_check(String shop_pay_check) {
            this.shop_pay_check = shop_pay_check;
        }

        public String getProduct_title() {
            return product_title;
        }

        public void setProduct_title(String product_title) {
            this.product_title = product_title;
        }

        public String getShop_to_text() {
            return shop_to_text;
        }

        public void setShop_to_text(String shop_to_text) {
            this.shop_to_text = shop_to_text;
        }

        public String getShop_to_time() {
            return shop_to_time;
        }

        public void setShop_to_time(String shop_to_time) {
            this.shop_to_time = shop_to_time;
        }

        public String getInst_name() {
            return inst_name;
        }

        public void setInst_name(String inst_name) {
            this.inst_name = inst_name;
        }

        public String getUser_to_score() {
            return user_to_score;
        }

        public void setUser_to_score(String user_to_score) {
            this.user_to_score = user_to_score;
        }
    }
}
