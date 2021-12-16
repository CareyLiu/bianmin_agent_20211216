package com.shendeng_bianmin.agent.model;

import java.io.Serializable;
import java.util.List;

public class OrderDetailsModel implements Serializable {

    /**
     * msg_code : 0000
     * msg : ok
     * row_num : 1
     * data : [{"shop_form_id":"3124","form_no":"20200708092056000001","shop_product_title":"横版数码按键开关","user_name":"越加按钮","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9931","express_no":"","pay_check_index":"3","pay_money":"10.50","form_money_go":"10.00","pay_check_arr":["已拍下","已付款","已发货","交易成功","已评价"],"express_code":"","express_url":"","receiver_name":"大仙","user_addr_all":"黑龙江省哈尔滨市南岗区胜多负少的","pay_count":"1","product":[{"shop_form_id":"3124","shop_product_title":"横版数码按键开关","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9931","product_title":"套餐1","form_product_money":"0.50","pay_count":"1","shop_form_text":""}],"create_time":"2020-07-08 09:20:56","user_accid":"wit_379","express_name":"","pay_name":"支付宝","shop_pay_check":"6","product_title":"套餐1","shop_form_text":"","express_id":"","total_money":"0.50","receiver_phone":"13233445566"}]
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

    public static class DataBean implements Serializable {
        /**
         * shop_form_id : 3124
         * form_no : 20200708092056000001
         * shop_product_title : 横版数码按键开关
         * user_name : 越加按钮
         * index_photo_url : https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9931
         * express_no :
         * pay_check_index : 3
         * pay_money : 10.50
         * form_money_go : 10.00
         * pay_check_arr : ["已拍下","已付款","已发货","交易成功","已评价"]
         * express_code :
         * express_url :
         * receiver_name : 大仙
         * user_addr_all : 黑龙江省哈尔滨市南岗区胜多负少的
         * pay_count : 1
         * product : [{"shop_form_id":"3124","shop_product_title":"横版数码按键开关","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9931","product_title":"套餐1","form_product_money":"0.50","pay_count":"1","shop_form_text":""}]
         * create_time : 2020-07-08 09:20:56
         * user_accid : wit_379
         * express_name :
         * pay_name : 支付宝
         * shop_pay_check : 6
         * product_title : 套餐1
         * shop_form_text :
         * express_id :
         * total_money : 0.50
         * receiver_phone : 13233445566
         */

        private String shop_form_id;
        private String form_no;
        private String shop_product_title;
        private String user_name;
        private String index_photo_url;
        private String express_no;
        private String pay_check_index;
        private String pay_money;
        private String form_money_go;
        private String express_code;
        private String express_url;
        private String receiver_name;
        private String user_addr_all;
        private String pay_count;
        private String create_time;
        private String user_accid;
        private String express_name;
        private String pay_name;
        private String shop_pay_check;
        private String product_title;
        private String shop_form_text;
        private String express_id;
        private String total_money;
        private String receiver_phone;
        private List<String> pay_check_arr;
        private List<ProductBean> product;

        public String getShop_form_id() {
            return shop_form_id;
        }

        public void setShop_form_id(String shop_form_id) {
            this.shop_form_id = shop_form_id;
        }

        public String getForm_no() {
            return form_no;
        }

        public void setForm_no(String form_no) {
            this.form_no = form_no;
        }

        public String getShop_product_title() {
            return shop_product_title;
        }

        public void setShop_product_title(String shop_product_title) {
            this.shop_product_title = shop_product_title;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getIndex_photo_url() {
            return index_photo_url;
        }

        public void setIndex_photo_url(String index_photo_url) {
            this.index_photo_url = index_photo_url;
        }

        public String getExpress_no() {
            return express_no;
        }

        public void setExpress_no(String express_no) {
            this.express_no = express_no;
        }

        public String getPay_check_index() {
            return pay_check_index;
        }

        public void setPay_check_index(String pay_check_index) {
            this.pay_check_index = pay_check_index;
        }

        public String getPay_money() {
            return pay_money;
        }

        public void setPay_money(String pay_money) {
            this.pay_money = pay_money;
        }

        public String getForm_money_go() {
            return form_money_go;
        }

        public void setForm_money_go(String form_money_go) {
            this.form_money_go = form_money_go;
        }

        public String getExpress_code() {
            return express_code;
        }

        public void setExpress_code(String express_code) {
            this.express_code = express_code;
        }

        public String getExpress_url() {
            return express_url;
        }

        public void setExpress_url(String express_url) {
            this.express_url = express_url;
        }

        public String getReceiver_name() {
            return receiver_name;
        }

        public void setReceiver_name(String receiver_name) {
            this.receiver_name = receiver_name;
        }

        public String getUser_addr_all() {
            return user_addr_all;
        }

        public void setUser_addr_all(String user_addr_all) {
            this.user_addr_all = user_addr_all;
        }

        public String getPay_count() {
            return pay_count;
        }

        public void setPay_count(String pay_count) {
            this.pay_count = pay_count;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUser_accid() {
            return user_accid;
        }

        public void setUser_accid(String user_accid) {
            this.user_accid = user_accid;
        }

        public String getExpress_name() {
            return express_name;
        }

        public void setExpress_name(String express_name) {
            this.express_name = express_name;
        }

        public String getPay_name() {
            return pay_name;
        }

        public void setPay_name(String pay_name) {
            this.pay_name = pay_name;
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

        public String getShop_form_text() {
            return shop_form_text;
        }

        public void setShop_form_text(String shop_form_text) {
            this.shop_form_text = shop_form_text;
        }

        public String getExpress_id() {
            return express_id;
        }

        public void setExpress_id(String express_id) {
            this.express_id = express_id;
        }

        public String getTotal_money() {
            return total_money;
        }

        public void setTotal_money(String total_money) {
            this.total_money = total_money;
        }

        public String getReceiver_phone() {
            return receiver_phone;
        }

        public void setReceiver_phone(String receiver_phone) {
            this.receiver_phone = receiver_phone;
        }

        public List<String> getPay_check_arr() {
            return pay_check_arr;
        }

        public void setPay_check_arr(List<String> pay_check_arr) {
            this.pay_check_arr = pay_check_arr;
        }

        public List<ProductBean> getProduct() {
            return product;
        }

        public void setProduct(List<ProductBean> product) {
            this.product = product;
        }

        public static class ProductBean implements Serializable{
            /**
             * shop_form_id : 3124
             * shop_product_title : 横版数码按键开关
             * index_photo_url : https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9931
             * product_title : 套餐1
             * form_product_money : 0.50
             * pay_count : 1
             * shop_form_text :
             */

            private String shop_form_id;
            private String shop_product_title;
            private String index_photo_url;
            private String product_title;
            private String form_product_money;
            private String pay_count;
            private String shop_form_text;

            public String getShop_form_id() {
                return shop_form_id;
            }

            public void setShop_form_id(String shop_form_id) {
                this.shop_form_id = shop_form_id;
            }

            public String getShop_product_title() {
                return shop_product_title;
            }

            public void setShop_product_title(String shop_product_title) {
                this.shop_product_title = shop_product_title;
            }

            public String getIndex_photo_url() {
                return index_photo_url;
            }

            public void setIndex_photo_url(String index_photo_url) {
                this.index_photo_url = index_photo_url;
            }

            public String getProduct_title() {
                return product_title;
            }

            public void setProduct_title(String product_title) {
                this.product_title = product_title;
            }

            public String getForm_product_money() {
                return form_product_money;
            }

            public void setForm_product_money(String form_product_money) {
                this.form_product_money = form_product_money;
            }

            public String getPay_count() {
                return pay_count;
            }

            public void setPay_count(String pay_count) {
                this.pay_count = pay_count;
            }

            public String getShop_form_text() {
                return shop_form_text;
            }

            public void setShop_form_text(String shop_form_text) {
                this.shop_form_text = shop_form_text;
            }
        }
    }
}
