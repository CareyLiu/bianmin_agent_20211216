package com.shendeng_bianmin.agent.model;

import java.io.Serializable;
import java.util.List;

public class TaoCanListModel {

    /**
     * typeNext : 0
     * msg_code : 0000
     * msg : ok
     * row_num : 3
     * data : [{"buyed_count":"0","taocan_list":[{"wares_id":"241","shop_product_title":"重庆小面","wares_photo_url":"","create_time":"2020-08-05","shop_money_now":"100.00","shop_money_old":"100.00","wares_name":"重庆小面","wares_state":"2"},{"wares_id":"243","shop_product_title":"1111","wares_photo_url":"","create_time":"2020-08-05","shop_money_now":"100.00","shop_money_old":"100.00","wares_name":"11111","wares_state":"2"},{"wares_id":"244","shop_product_title":"1111","wares_photo_url":"","create_time":"2020-08-05","shop_money_now":"100.00","shop_money_old":"100.00","wares_name":"11111","wares_state":"2"}],"pull_off_count":"3"}]
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
         * buyed_count : 0
         * taocan_list : [{"wares_id":"241","shop_product_title":"重庆小面","wares_photo_url":"","create_time":"2020-08-05","shop_money_now":"100.00","shop_money_old":"100.00","wares_name":"重庆小面","wares_state":"2"},{"wares_id":"243","shop_product_title":"1111","wares_photo_url":"","create_time":"2020-08-05","shop_money_now":"100.00","shop_money_old":"100.00","wares_name":"11111","wares_state":"2"},{"wares_id":"244","shop_product_title":"1111","wares_photo_url":"","create_time":"2020-08-05","shop_money_now":"100.00","shop_money_old":"100.00","wares_name":"11111","wares_state":"2"}]
         * pull_off_count : 3
         */

        private String buyed_count;
        private String pull_off_count;
        private List<TaocanListBean> taocan_list;

        public String getBuyed_count() {
            return buyed_count;
        }

        public void setBuyed_count(String buyed_count) {
            this.buyed_count = buyed_count;
        }

        public String getPull_off_count() {
            return pull_off_count;
        }

        public void setPull_off_count(String pull_off_count) {
            this.pull_off_count = pull_off_count;
        }

        public List<TaocanListBean> getTaocan_list() {
            return taocan_list;
        }

        public void setTaocan_list(List<TaocanListBean> taocan_list) {
            this.taocan_list = taocan_list;
        }

        public static class TaocanListBean implements Serializable {
            /**
             * wares_id : 241
             * shop_product_title : 重庆小面
             * wares_photo_url :
             * create_time : 2020-08-05
             * shop_money_now : 100.00
             * shop_money_old : 100.00
             * wares_name : 重庆小面
             * wares_state : 2
             */

            private String wares_id;
            private String shop_product_title;
            private String wares_photo_url;
            private String create_time;
            private String shop_money_now;
            private String shop_money_old;
            private String wares_name;
            private String wares_state;

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

            public String getShop_money_now() {
                return shop_money_now;
            }

            public void setShop_money_now(String shop_money_now) {
                this.shop_money_now = shop_money_now;
            }

            public String getShop_money_old() {
                return shop_money_old;
            }

            public void setShop_money_old(String shop_money_old) {
                this.shop_money_old = shop_money_old;
            }

            public String getWares_name() {
                return wares_name;
            }

            public void setWares_name(String wares_name) {
                this.wares_name = wares_name;
            }

            public String getWares_state() {
                return wares_state;
            }

            public void setWares_state(String wares_state) {
                this.wares_state = wares_state;
            }
        }
    }
}
