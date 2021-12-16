package com.shendeng_bianmin.agent.model.tuangou;

import java.util.List;

public class TOrderDetailsModel {

    /**
     * msg_code : 0000
     * msg : ok
     * row_num : 1
     * data : [{"shop_form_id":"3235","wares_id":"262","shop_product_title":"测试套餐一","index_photo_url":"https://test.hljsdkj.com/Frame/uploadFile/showImg?file_id=11558","taocan_list":[{"menu_detail_id":"40","menu_pay":"28.00","menu_text":"发文字呀","menu_count":"1"},{"menu_detail_id":"44","menu_pay":"12.00","menu_text":"QWERTY","menu_count":"1"}],"rules_list":[{"prompt_detail_id":"20","prompt_text":"请按时就位"},{"prompt_detail_id":"21","prompt_text":"什么时候整完"}],"shop_pay_check":"8","shop_pay_check_name":"退款申请"}]
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
         * shop_form_id : 3235
         * wares_id : 262
         * shop_product_title : 测试套餐一
         * index_photo_url : https://test.hljsdkj.com/Frame/uploadFile/showImg?file_id=11558
         * taocan_list : [{"menu_detail_id":"40","menu_pay":"28.00","menu_text":"发文字呀","menu_count":"1"},{"menu_detail_id":"44","menu_pay":"12.00","menu_text":"QWERTY","menu_count":"1"}]
         * rules_list : [{"prompt_detail_id":"20","prompt_text":"请按时就位"},{"prompt_detail_id":"21","prompt_text":"什么时候整完"}]
         * shop_pay_check : 8
         * shop_pay_check_name : 退款申请
         */

        private String shop_form_id;
        private String wares_id;
        private String shop_product_title;
        private String index_photo_url;
        private String shop_pay_check;
        private String shop_pay_check_name;
        private List<TaocanListBean> taocan_list;
        private List<RulesListBean> rules_list;

        public String getShop_form_id() {
            return shop_form_id;
        }

        public void setShop_form_id(String shop_form_id) {
            this.shop_form_id = shop_form_id;
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

        public static class TaocanListBean {
            /**
             * menu_detail_id : 40
             * menu_pay : 28.00
             * menu_text : 发文字呀
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
             * prompt_detail_id : 20
             * prompt_text : 请按时就位
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
