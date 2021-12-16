package com.shendeng_bianmin.agent.model.tuangou;

import java.util.List;

public class TOrderModel {


    /**
     * next : 0
     * msg_code : 0000
     * msg : ok
     * row_num : 1
     * data : [{"shop_form_id":"3235","pay_count":"1","shop_product_title":"测试套餐一","create_time":"2020-08-07","total_money_all":"80.00","wares_type":"3","index_photo_url":"https://test.hljsdkj.com/Frame/uploadFile/showImg?file_id=11558","shop_pay_check":"8","shop_pay_check_name":"退款申请"}]
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
         * shop_form_id : 3235
         * pay_count : 1
         * shop_product_title : 测试套餐一
         * create_time : 2020-08-07
         * total_money_all : 80.00
         * wares_type : 3
         * index_photo_url : https://test.hljsdkj.com/Frame/uploadFile/showImg?file_id=11558
         * shop_pay_check : 8
         * shop_pay_check_name : 退款申请
         */

        private String shop_form_id;
        private String pay_count;
        private String shop_product_title;
        private String create_time;
        private String total_money_all;
        private String wares_type;
        private String index_photo_url;
        private String shop_pay_check;
        private String shop_pay_check_name;

        public String getShop_form_id() {
            return shop_form_id;
        }

        public void setShop_form_id(String shop_form_id) {
            this.shop_form_id = shop_form_id;
        }

        public String getPay_count() {
            return pay_count;
        }

        public void setPay_count(String pay_count) {
            this.pay_count = pay_count;
        }

        public String getShop_product_title() {
            return shop_product_title;
        }

        public void setShop_product_title(String shop_product_title) {
            this.shop_product_title = shop_product_title;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getTotal_money_all() {
            return total_money_all;
        }

        public void setTotal_money_all(String total_money_all) {
            this.total_money_all = total_money_all;
        }

        public String getWares_type() {
            return wares_type;
        }

        public void setWares_type(String wares_type) {
            this.wares_type = wares_type;
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

        public String getShop_pay_check_name() {
            return shop_pay_check_name;
        }

        public void setShop_pay_check_name(String shop_pay_check_name) {
            this.shop_pay_check_name = shop_pay_check_name;
        }
    }
}
