package com.shendeng_bianmin.agent.model;

import java.util.List;

public class TaoCanDetailsModel {


    /**
     * msg_code : 0000
     * msg : ok
     * row_num : 0
     * data : [{"wares_photo_id":"11580","shop_product_title":"水煮肥牛是一个非常好的东西，嘎嘎地好太好了咋这么好呢","wares_photo_url":"https://test.hljsdkj.com/Frame/uploadFile/showImg?file_id=11580","img_list":[{"wares_img_id":"579","img_url":"https://test.hljsdkj.com/Frame/uploadFile/showImg?file_id=11580","img_id":"11580"}],"shop_money_now":"80.00","item_name":"美食烧烤烤肉","shop_money_old":"100.00","taocan_list":[{"menu_detail_id":"19","menu_pay":"1.00","menu_text":"嗯","menu_count":"1"}],"wares_name":"水煮肥牛","rules_list":[{"prompt_detail_id":"19","prompt_text":"轻轻巧巧www望闻问切"}],"wares_state":"2"}]
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
         * wares_photo_id : 11580
         * shop_product_title : 水煮肥牛是一个非常好的东西，嘎嘎地好太好了咋这么好呢
         * wares_photo_url : https://test.hljsdkj.com/Frame/uploadFile/showImg?file_id=11580
         * img_list : [{"wares_img_id":"579","img_url":"https://test.hljsdkj.com/Frame/uploadFile/showImg?file_id=11580","img_id":"11580"}]
         * shop_money_now : 80.00
         * item_name : 美食烧烤烤肉
         * shop_money_old : 100.00
         * taocan_list : [{"menu_detail_id":"19","menu_pay":"1.00","menu_text":"嗯","menu_count":"1"}]
         * wares_name : 水煮肥牛
         * rules_list : [{"prompt_detail_id":"19","prompt_text":"轻轻巧巧www望闻问切"}]
         * wares_state : 2
         */

        private String wares_photo_id;
        private String shop_product_title;
        private String wares_photo_url;
        private String shop_money_now;
        private String item_name;
        private String shop_money_old;
        private String wares_name;
        private String wares_state;
        private List<ImgListBean> img_list;
        private List<TaocanListBean> taocan_list;
        private List<RulesListBean> rules_list;

        public String getWares_photo_id() {
            return wares_photo_id;
        }

        public void setWares_photo_id(String wares_photo_id) {
            this.wares_photo_id = wares_photo_id;
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

        public String getShop_money_now() {
            return shop_money_now;
        }

        public void setShop_money_now(String shop_money_now) {
            this.shop_money_now = shop_money_now;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
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

        public List<ImgListBean> getImg_list() {
            return img_list;
        }

        public void setImg_list(List<ImgListBean> img_list) {
            this.img_list = img_list;
        }

        public List<TaocanListBean> getTaocan_list() {
            return taocan_list;
        }

        public void setTaocan_list(List<TaocanListBean> taocan_list) {
            this.taocan_list = taocan_list;
        }

        public List<RulesListBean> getRules_list() {
            return rules_list;
        }

        public void setRules_list(List<RulesListBean> rules_list) {
            this.rules_list = rules_list;
        }

        public static class ImgListBean {
            /**
             * wares_img_id : 579
             * img_url : https://test.hljsdkj.com/Frame/uploadFile/showImg?file_id=11580
             * img_id : 11580
             */

            private String wares_img_id;
            private String img_url;
            private String img_id;
            public String type;//0 点击无效 1 选中弹出相册

            public String getWares_img_id() {
                return wares_img_id;
            }

            public void setWares_img_id(String wares_img_id) {
                this.wares_img_id = wares_img_id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getImg_id() {
                return img_id;
            }

            public void setImg_id(String img_id) {
                this.img_id = img_id;
            }
        }

        public static class TaocanListBean {
            /**
             * menu_detail_id : 19
             * menu_pay : 1.00
             * menu_text : 嗯
             * menu_count : 1
             */

            private String menu_detail_id;
            private String menu_pay;
            private String menu_text;
            private String menu_count;

            public String getMenu_detail_id() {
                return menu_detail_id;
            }

            public void setMenu_detail_id(String menu_detail_id) {
                this.menu_detail_id = menu_detail_id;
            }

            public String getMenu_pay() {
                return menu_pay;
            }

            public void setMenu_pay(String menu_pay) {
                this.menu_pay = menu_pay;
            }

            public String getMenu_text() {
                return menu_text;
            }

            public void setMenu_text(String menu_text) {
                this.menu_text = menu_text;
            }

            public String getMenu_count() {
                return menu_count;
            }

            public void setMenu_count(String menu_count) {
                this.menu_count = menu_count;
            }
        }

        public static class RulesListBean {
            /**
             * prompt_detail_id : 19
             * prompt_text : 轻轻巧巧www望闻问切
             */

            private String prompt_detail_id;
            private String prompt_text;

            public String getPrompt_detail_id() {
                return prompt_detail_id;
            }

            public void setPrompt_detail_id(String prompt_detail_id) {
                this.prompt_detail_id = prompt_detail_id;
            }

            public String getPrompt_text() {
                return prompt_text;
            }

            public void setPrompt_text(String prompt_text) {
                this.prompt_text = prompt_text;
            }
        }
    }
}
