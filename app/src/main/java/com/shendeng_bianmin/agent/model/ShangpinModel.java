package com.shendeng_bianmin.agent.model;

import java.util.List;

public class ShangpinModel {


    /**
     * typeNext : 1
     * msg_code : 0000
     * msg : ok
     * row_num : 12
     * data : [{"wares_sales_volume":"0","wares_id":"217","shop_product_title":"野原新之助","wares_photo_url":"","create_time":"2020-07-21","money":"","wares_state":"1"},{"wares_sales_volume":"0","wares_id":"185","shop_product_title":"风水摆件","wares_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10115","create_time":"2019-12-05","money":"1500.00-1500.00","wares_state":"1"},{"wares_sales_volume":"0","wares_id":"186","shop_product_title":"风水摆件","wares_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10115","create_time":"2019-12-05","money":"1500.00-1500.00","wares_state":"1"},{"wares_sales_volume":"0","wares_id":"187","shop_product_title":"风水摆件","wares_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10115","create_time":"2019-12-05","money":"1500.00-1500.00","wares_state":"1"},{"wares_sales_volume":"0","wares_id":"128","shop_product_title":"风水摆件","wares_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10115","create_time":"2019-12-05","money":"1500.00-1500.00","wares_state":"1"},{"wares_sales_volume":"0","wares_id":"180","shop_product_title":"风水摆件","wares_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10115","create_time":"2019-12-05","money":"1500.00-1500.00","wares_state":"1"},{"wares_sales_volume":"0","wares_id":"181","shop_product_title":"风水摆件","wares_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10115","create_time":"2019-12-05","money":"1500.00-1500.00","wares_state":"1"},{"wares_sales_volume":"0","wares_id":"183","shop_product_title":"风水摆件","wares_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10115","create_time":"2019-12-05","money":"1500.00-1500.00","wares_state":"1"},{"wares_sales_volume":"0","wares_id":"184","shop_product_title":"风水摆件","wares_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10115","create_time":"2019-12-05","money":"1500.00-1500.00","wares_state":"1"},{"wares_sales_volume":"0","wares_id":"127","shop_product_title":"风水摆件","wares_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9927","create_time":"2019-12-05","money":"600.00-600.00","wares_state":"1"},{"wares_sales_volume":"0","wares_id":"126","shop_product_title":"风水摆件","wares_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10096","create_time":"2019-12-05","money":"1500.00-1500.00","wares_state":"1"},{"wares_sales_volume":"0","wares_id":"125","shop_product_title":"风水摆件","wares_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10087","create_time":"2019-12-05","money":"180.00-180.00","wares_state":"1"}]
     */

    private String typeNext;
    private String msg_code;
    private String msg;
    private String row_num;
    private List<DataBean> data;

    public String getTypeNext() {
        return typeNext;
    }

    public void setTypeNext(String typeNext) {
        this.typeNext = typeNext;
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
         * wares_sales_volume : 0
         * wares_id : 217
         * shop_product_title : 野原新之助
         * wares_photo_url :
         * create_time : 2020-07-21
         * money :
         * wares_state : 1
         */

        private String wares_sales_volume;
        private String wares_id;
        private String shop_product_title;
        private String wares_photo_url;
        private String create_time;
        private String money;
        private String wares_state;
        public String shop_form_text;

        public String getWares_sales_volume() {
            return wares_sales_volume;
        }

        public void setWares_sales_volume(String wares_sales_volume) {
            this.wares_sales_volume = wares_sales_volume;
        }

        public String getWares_id() {
            return wares_id;
        }

        public void setWares_id(String wares_id) {
            this.wares_id = wares_id;
        }

        public String getShop_product_title() {
            return shop_product_title;
        }

        public void setShop_product_title(String shop_product_title) {
            this.shop_product_title = shop_product_title;
        }

        public String getWares_photo_url() {
            return wares_photo_url;
        }

        public void setWares_photo_url(String wares_photo_url) {
            this.wares_photo_url = wares_photo_url;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getWares_state() {
            return wares_state;
        }

        public void setWares_state(String wares_state) {
            this.wares_state = wares_state;
        }
    }
}
