package com.shendeng_bianmin.agent.model;

import java.util.List;

public class TuanGouOrderListModel {
    /**
     * next : 1
     * msg_code : 0000
     * msg : ok
     * row_num : 12
     * data : [{"wares_type":"3","product":[{"shop_form_id":"3165","index_photo_url":"https://jy.hljsdkj.com/Frame/uploadFile/showImg?file_id=6480","create_time":"2020-07-16 17:24:11","total_money_all":"1.00","pay_count":"1"}],"shop_product_title":"美食三","shop_pay_check":"6","shop_pay_check_name":"待评价"},{"wares_type":"3","product":[{"shop_form_id":"3164","index_photo_url":"https://jy.hljsdkj.com/Frame/uploadFile/showImg?file_id=6480","create_time":"2020-07-16 17:09:08","total_money_all":"1.00","pay_count":"1"}],"shop_product_title":"美食三","shop_pay_check":"6","shop_pay_check_name":"待评价"},{"wares_type":"4","product":[{"shop_form_id":"2872","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9077","create_time":"2020-06-04 16:41:32","total_money_all":"1.00","pay_count":"1"}],"shop_product_title":"麦香村西饼屋（平房店）(直接下单)","shop_pay_check":"6","shop_pay_check_name":"待评价"},{"wares_type":"3","product":[{"shop_form_id":"2868","index_photo_url":"https://jy.hljsdkj.com/Frame/uploadFile/showImg?file_id=6480","create_time":"2020-06-03 17:40:08","total_money_all":"258.19","pay_count":"1"}],"shop_product_title":"美食二","shop_pay_check":"5","shop_pay_check_name":"到店消费"},{"wares_type":"4","product":[{"shop_form_id":"2861","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9077","create_time":"2020-06-03 17:06:12","total_money_all":"1.00","pay_count":"1"}],"shop_product_title":"麦香村西饼屋（平房店）(直接下单)","shop_pay_check":"6","shop_pay_check_name":"待评价"},{"wares_type":"4","product":[{"shop_form_id":"2817","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9077","create_time":"2020-06-01 18:15:08","total_money_all":"10.00","pay_count":"1"}],"shop_product_title":"麦香村西饼屋（平房店）(直接下单)","shop_pay_check":"11","shop_pay_check_name":"订单失效"},{"wares_type":"4","product":[{"shop_form_id":"2473","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9077","create_time":"2020-05-16 15:12:00","total_money_all":"20.00","pay_count":"1"}],"shop_product_title":"麦香村西饼屋（平房店）(直接下单)","shop_pay_check":"6","shop_pay_check_name":"待评价"},{"wares_type":"4","product":[{"shop_form_id":"2139","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9077","create_time":"2020-05-07 11:30:52","total_money_all":"51.00","pay_count":"1"}],"shop_product_title":"麦香村西饼屋（平房店）(直接下单)","shop_pay_check":"6","shop_pay_check_name":"待评价"},{"wares_type":"4","product":[{"shop_form_id":"2130","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9077","create_time":"2020-05-07 09:22:30","total_money_all":"0.01","pay_count":"1"}],"shop_product_title":"麦香村西饼屋（平房店）(直接下单)","shop_pay_check":"6","shop_pay_check_name":"待评价"},{"wares_type":"3","product":[{"shop_form_id":"2125","index_photo_url":"https://jy.hljsdkj.com/Frame/uploadFile/showImg?file_id=6480","create_time":"2020-05-06 15:50:07","total_money_all":"258.00","pay_count":"1"}],"shop_product_title":"美食三","shop_pay_check":"11","shop_pay_check_name":"订单失效"},{"wares_type":"4","product":[{"shop_form_id":"2122","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9077","create_time":"2020-05-06 14:19:13","total_money_all":"0.01","pay_count":"1"}],"shop_product_title":"麦香村西饼屋（平房店）(直接下单)","shop_pay_check":"6","shop_pay_check_name":"待评价"},{"wares_type":"4","product":[{"shop_form_id":"2120","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9077","create_time":"2020-05-06 14:18:37","total_money_all":"0.01","pay_count":"1"}],"shop_product_title":"麦香村西饼屋（平房店）(直接下单)","shop_pay_check":"6","shop_pay_check_name":"待评价"}]
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
         * wares_type : 3
         * product : [{"shop_form_id":"3165","index_photo_url":"https://jy.hljsdkj.com/Frame/uploadFile/showImg?file_id=6480","create_time":"2020-07-16 17:24:11","total_money_all":"1.00","pay_count":"1"}]
         * shop_product_title : 美食三
         * shop_pay_check : 6
         * shop_pay_check_name : 待评价
         */

        private String wares_type;
        private String shop_product_title;
        private String shop_pay_check;
        private String shop_pay_check_name;

        public String getPay_count() {
            return pay_count;
        }

        public void setPay_count(String pay_count) {
            this.pay_count = pay_count;
        }

        private String pay_count;
        public String getTotal_money_all() {
            return total_money_all;
        }

        public void setTotal_money_all(String total_money_all) {
            this.total_money_all = total_money_all;
        }

        private String total_money_all;

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        private String create_time;

        public String getIndex_photo_url() {
            return index_photo_url;
        }

        public void setIndex_photo_url(String index_photo_url) {
            this.index_photo_url = index_photo_url;
        }

        private String index_photo_url;
        private List<ProductBean> product;

        public String getWares_type() {
            return wares_type;
        }

        public void setWares_type(String wares_type) {
            this.wares_type = wares_type;
        }

        public String getShop_product_title() {
            return shop_product_title;
        }

        public void setShop_product_title(String shop_product_title) {
            this.shop_product_title = shop_product_title;
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

        public List<ProductBean> getProduct() {
            return product;
        }

        public void setProduct(List<ProductBean> product) {
            this.product = product;
        }

        public static class ProductBean {
            /**
             * shop_form_id : 3165
             * index_photo_url : https://jy.hljsdkj.com/Frame/uploadFile/showImg?file_id=6480
             * create_time : 2020-07-16 17:24:11
             * total_money_all : 1.00
             * pay_count : 1
             */

            private String shop_form_id;
            private String index_photo_url;
            private String create_time;
            private String total_money_all;
            private String pay_count;

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

            public String getPay_count() {
                return pay_count;
            }

            public void setPay_count(String pay_count) {
                this.pay_count = pay_count;
            }
        }
    }
}
