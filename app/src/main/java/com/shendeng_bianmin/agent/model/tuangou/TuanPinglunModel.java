package com.shendeng_bianmin.agent.model.tuangou;

import java.util.List;

public class TuanPinglunModel {

    /**
     * next : 0
     * msg_code : 0000
     * msg : ok
     * row_num : 1
     * data : [{"total_assess_count":"1","high_assess_percent":"100.0%","assess_list":[{"user_to_text":"。。。","user_to_time":"2020-07-06 09:16:22","of_user_id":"520","shop_to_text":"","reply_state":"2","user_name":"🌝🌝","user_img_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9763","user_to_score":"4.00"}],"high_assess_count":"1","low_assess_count":"0","low_assess_percent":"0.0%","assess_reply_percent":"0.0%","low_assess_reply_percent":"0%"}]
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
         * total_assess_count : 1
         * high_assess_percent : 100.0%
         * assess_list : [{"user_to_text":"。。。","user_to_time":"2020-07-06 09:16:22","of_user_id":"520","shop_to_text":"","reply_state":"2","user_name":"🌝🌝","user_img_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9763","user_to_score":"4.00"}]
         * high_assess_count : 1
         * low_assess_count : 0
         * low_assess_percent : 0.0%
         * assess_reply_percent : 0.0%
         * low_assess_reply_percent : 0%
         */

        private String total_assess_count;
        private String high_assess_percent;
        private String high_assess_count;
        private String low_assess_count;
        private String low_assess_percent;
        private String assess_reply_percent;
        private String low_assess_reply_percent;
        private String average_score;
        private List<AssessListBean> assess_list;

        public String getAverage_score() {
            return average_score;
        }

        public void setAverage_score(String average_score) {
            this.average_score = average_score;
        }

        public String getTotal_assess_count() {
            return total_assess_count;
        }

        public void setTotal_assess_count(String total_assess_count) {
            this.total_assess_count = total_assess_count;
        }

        public String getHigh_assess_percent() {
            return high_assess_percent;
        }

        public void setHigh_assess_percent(String high_assess_percent) {
            this.high_assess_percent = high_assess_percent;
        }

        public String getHigh_assess_count() {
            return high_assess_count;
        }

        public void setHigh_assess_count(String high_assess_count) {
            this.high_assess_count = high_assess_count;
        }

        public String getLow_assess_count() {
            return low_assess_count;
        }

        public void setLow_assess_count(String low_assess_count) {
            this.low_assess_count = low_assess_count;
        }

        public String getLow_assess_percent() {
            return low_assess_percent;
        }

        public void setLow_assess_percent(String low_assess_percent) {
            this.low_assess_percent = low_assess_percent;
        }

        public String getAssess_reply_percent() {
            return assess_reply_percent;
        }

        public void setAssess_reply_percent(String assess_reply_percent) {
            this.assess_reply_percent = assess_reply_percent;
        }

        public String getLow_assess_reply_percent() {
            return low_assess_reply_percent;
        }

        public void setLow_assess_reply_percent(String low_assess_reply_percent) {
            this.low_assess_reply_percent = low_assess_reply_percent;
        }

        public List<AssessListBean> getAssess_list() {
            return assess_list;
        }

        public void setAssess_list(List<AssessListBean> assess_list) {
            this.assess_list = assess_list;
        }

        public static class AssessListBean {
            /**
             * user_to_text : 。。。
             * user_to_time : 2020-07-06 09:16:22
             * of_user_id : 520
             * shop_to_text :
             * reply_state : 2
             * user_name : 🌝🌝
             * user_img_url : https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9763
             * user_to_score : 4.00
             */

            private String user_to_text;
            private String user_to_time;
            private String of_user_id;
            private String shop_to_text;
            private String reply_state;
            private String user_name;
            private String user_img_url;
            private String user_to_score;
            private String shop_form_id;

            public String getShop_form_id() {
                return shop_form_id;
            }

            public void setShop_form_id(String shop_form_id) {
                this.shop_form_id = shop_form_id;
            }

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

            public String getOf_user_id() {
                return of_user_id;
            }

            public void setOf_user_id(String of_user_id) {
                this.of_user_id = of_user_id;
            }

            public String getShop_to_text() {
                return shop_to_text;
            }

            public void setShop_to_text(String shop_to_text) {
                this.shop_to_text = shop_to_text;
            }

            public String getReply_state() {
                return reply_state;
            }

            public void setReply_state(String reply_state) {
                this.reply_state = reply_state;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getUser_img_url() {
                return user_img_url;
            }

            public void setUser_img_url(String user_img_url) {
                this.user_img_url = user_img_url;
            }

            public String getUser_to_score() {
                return user_to_score;
            }

            public void setUser_to_score(String user_to_score) {
                this.user_to_score = user_to_score;
            }
        }
    }
}
