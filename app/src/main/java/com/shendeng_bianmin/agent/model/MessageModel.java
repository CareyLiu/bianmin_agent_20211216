package com.shendeng_bianmin.agent.model;

import java.util.List;

public class MessageModel {

    /**
     * next : 1
     * msg_code : 0000
     * msg : ok
     * row_num : 12
     * data : [{"notify_text":"越加按钮","create_time":"2020-07-03 15:21:15","lt_user_name":"越加按钮","other_img_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9413","notify_read":"2","lt_user_accid":"wit_379","notify_id":"2297","shop_pay_check":"","user_car_id":"","lt_user_img_url":"","notify_type":"14","notify_state":"","car_code":"","oper_id":"","lt_inst_accid":"jcz_sub_191"},{"notify_text":"阿钊","create_time":"2020-07-03 14:01:35","lt_user_name":"阿钊","other_img_url":"https://shop.hljsdkj.com/commons/easyui/images/portrait86x86.png","notify_read":"2","lt_user_accid":"wit_2032","notify_id":"2293","shop_pay_check":"","user_car_id":"","lt_user_img_url":"","notify_type":"14","notify_state":"","car_code":"","oper_id":"","lt_inst_accid":"jcz_sub_191"},{"notify_text":"神灯科技孙先生","create_time":"2020-07-01 09:57:19","lt_user_name":"神灯科技孙先生","other_img_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10774","notify_read":"2","lt_user_accid":"wit_455","notify_id":"2267","shop_pay_check":"","user_car_id":"","lt_user_img_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10774","notify_type":"14","notify_state":"","car_code":"","oper_id":"","lt_inst_accid":"jcz_sub_191"},{"notify_text":"您售出的横版数码按键开关 还没有发货，请尽快发货","create_time":"2020-06-12 10:48:15","lt_user_name":"","other_img_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9931","notify_read":"2","lt_user_accid":"","notify_id":"2160","shop_pay_check":"10","user_car_id":"","lt_user_img_url":"","notify_type":"12","notify_state":"","car_code":"","oper_id":"2986","lt_inst_accid":""},{"notify_text":"您售出的横版数码按键开关 还没有发货，请尽快发货","create_time":"2020-06-12 09:14:46","lt_user_name":"","other_img_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9931","notify_read":"2","lt_user_accid":"","notify_id":"2159","shop_pay_check":"10","user_car_id":"","lt_user_img_url":"","notify_type":"12","notify_state":"","car_code":"","oper_id":"2970","lt_inst_accid":""},{"notify_text":"您售出的横版数码按键开关 还没有发货，请尽快发货","create_time":"2020-06-06 15:53:12","lt_user_name":"","other_img_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9931","notify_read":"2","lt_user_accid":"","notify_id":"2155","shop_pay_check":"11","user_car_id":"","lt_user_img_url":"","notify_type":"12","notify_state":"","car_code":"","oper_id":"2883","lt_inst_accid":""},{"notify_text":"您售出的横版数码按键开关 还没有发货，请尽快发货","create_time":"2020-06-06 09:22:55","lt_user_name":"","other_img_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9931","notify_read":"2","lt_user_accid":"","notify_id":"2154","shop_pay_check":"11","user_car_id":"","lt_user_img_url":"","notify_type":"12","notify_state":"","car_code":"","oper_id":"2886","lt_inst_accid":""},{"notify_text":"您售出的横版数码按键开关 还没有发货，请尽快发货","create_time":"2020-06-05 15:37:34","lt_user_name":"","other_img_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9931","notify_read":"2","lt_user_accid":"","notify_id":"2153","shop_pay_check":"11","user_car_id":"","lt_user_img_url":"","notify_type":"12","notify_state":"","car_code":"","oper_id":"2880","lt_inst_accid":""},{"notify_text":"您售出的横版数码按键开关 还没有发货，请尽快发货","create_time":"2020-06-05 14:04:58","lt_user_name":"","other_img_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9931","notify_read":"2","lt_user_accid":"","notify_id":"2152","shop_pay_check":"11","user_car_id":"","lt_user_img_url":"","notify_type":"12","notify_state":"","car_code":"","oper_id":"2863","lt_inst_accid":""},{"notify_text":"您售出的横版数码按键开关 还没有发货，请尽快发货","create_time":"2020-06-05 08:38:50","lt_user_name":"","other_img_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9931","notify_read":"2","lt_user_accid":"","notify_id":"2151","shop_pay_check":"11","user_car_id":"","lt_user_img_url":"","notify_type":"12","notify_state":"","car_code":"","oper_id":"2864","lt_inst_accid":""},{"notify_text":"您售出的横版数码按键开关 还没有发货，请尽快发货","create_time":"2020-06-02 11:00:04","lt_user_name":"","other_img_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9931","notify_read":"2","lt_user_accid":"","notify_id":"2149","shop_pay_check":"11","user_car_id":"","lt_user_img_url":"","notify_type":"12","notify_state":"","car_code":"","oper_id":"2814","lt_inst_accid":""},{"notify_text":"您售出的横版数码按键开关 还没有发货，请尽快发货","create_time":"2020-06-02 09:11:42","lt_user_name":"","other_img_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9931","notify_read":"2","lt_user_accid":"","notify_id":"2148","shop_pay_check":"11","user_car_id":"","lt_user_img_url":"","notify_type":"12","notify_state":"","car_code":"","oper_id":"2816","lt_inst_accid":""}]
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
         * notify_text : 越加按钮
         * create_time : 2020-07-03 15:21:15
         * lt_user_name : 越加按钮
         * other_img_url : https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9413
         * notify_read : 2
         * lt_user_accid : wit_379
         * notify_id : 2297
         * shop_pay_check :
         * user_car_id :
         * lt_user_img_url :
         * notify_type : 14
         * notify_state :
         * car_code :
         * oper_id :
         * lt_inst_accid : jcz_sub_191
         */

        private String notify_text;
        private String create_time;
        private String lt_user_name;
        private String other_img_url;
        private String notify_read;
        private String lt_user_accid;
        private String notify_id;
        private String shop_pay_check;
        private String user_car_id;
        private String lt_user_img_url;
        private String notify_type;
        private String notify_state;
        private String car_code;
        private String oper_id;
        private String lt_inst_accid;

        public String getNotify_text() {
            return notify_text;
        }

        public void setNotify_text(String notify_text) {
            this.notify_text = notify_text;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getLt_user_name() {
            return lt_user_name;
        }

        public void setLt_user_name(String lt_user_name) {
            this.lt_user_name = lt_user_name;
        }

        public String getOther_img_url() {
            return other_img_url;
        }

        public void setOther_img_url(String other_img_url) {
            this.other_img_url = other_img_url;
        }

        public String getNotify_read() {
            return notify_read;
        }

        public void setNotify_read(String notify_read) {
            this.notify_read = notify_read;
        }

        public String getLt_user_accid() {
            return lt_user_accid;
        }

        public void setLt_user_accid(String lt_user_accid) {
            this.lt_user_accid = lt_user_accid;
        }

        public String getNotify_id() {
            return notify_id;
        }

        public void setNotify_id(String notify_id) {
            this.notify_id = notify_id;
        }

        public String getShop_pay_check() {
            return shop_pay_check;
        }

        public void setShop_pay_check(String shop_pay_check) {
            this.shop_pay_check = shop_pay_check;
        }

        public String getUser_car_id() {
            return user_car_id;
        }

        public void setUser_car_id(String user_car_id) {
            this.user_car_id = user_car_id;
        }

        public String getLt_user_img_url() {
            return lt_user_img_url;
        }

        public void setLt_user_img_url(String lt_user_img_url) {
            this.lt_user_img_url = lt_user_img_url;
        }

        public String getNotify_type() {
            return notify_type;
        }

        public void setNotify_type(String notify_type) {
            this.notify_type = notify_type;
        }

        public String getNotify_state() {
            return notify_state;
        }

        public void setNotify_state(String notify_state) {
            this.notify_state = notify_state;
        }

        public String getCar_code() {
            return car_code;
        }

        public void setCar_code(String car_code) {
            this.car_code = car_code;
        }

        public String getOper_id() {
            return oper_id;
        }

        public void setOper_id(String oper_id) {
            this.oper_id = oper_id;
        }

        public String getLt_inst_accid() {
            return lt_inst_accid;
        }

        public void setLt_inst_accid(String lt_inst_accid) {
            this.lt_inst_accid = lt_inst_accid;
        }
    }
}
