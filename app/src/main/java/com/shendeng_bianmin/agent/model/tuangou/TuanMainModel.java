package com.shendeng_bianmin.agent.model.tuangou;

import java.util.List;

public class TuanMainModel {

    /**
     * msg_code : 0000
     * msg : ok
     * data : [{"p_actual_use":"1811.00","b_actual_use":"270.32","p_order_count":"40","p_receivable":"1520.90","actual_use":"0.00","receivable":"0.00","b_order_count":"311","b_receivable":"0.00"}]
     */

    private String msg_code;
    private String msg;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * p_actual_use : 1811.00
         * b_actual_use : 270.32
         * p_order_count : 40
         * p_receivable : 1520.90
         * actual_use : 0.00
         * receivable : 0.00
         * b_order_count : 311
         * b_receivable : 0.00
         */

        private String p_actual_use;
        private String b_actual_use;
        private String p_order_count;
        private String p_receivable;
        private String actual_use;
        private String receivable;
        private String b_order_count;
        private String b_receivable;

        public String getP_actual_use() {
            return p_actual_use;
        }

        public void setP_actual_use(String p_actual_use) {
            this.p_actual_use = p_actual_use;
        }

        public String getB_actual_use() {
            return b_actual_use;
        }

        public void setB_actual_use(String b_actual_use) {
            this.b_actual_use = b_actual_use;
        }

        public String getP_order_count() {
            return p_order_count;
        }

        public void setP_order_count(String p_order_count) {
            this.p_order_count = p_order_count;
        }

        public String getP_receivable() {
            return p_receivable;
        }

        public void setP_receivable(String p_receivable) {
            this.p_receivable = p_receivable;
        }

        public String getActual_use() {
            return actual_use;
        }

        public void setActual_use(String actual_use) {
            this.actual_use = actual_use;
        }

        public String getReceivable() {
            return receivable;
        }

        public void setReceivable(String receivable) {
            this.receivable = receivable;
        }

        public String getB_order_count() {
            return b_order_count;
        }

        public void setB_order_count(String b_order_count) {
            this.b_order_count = b_order_count;
        }

        public String getB_receivable() {
            return b_receivable;
        }

        public void setB_receivable(String b_receivable) {
            this.b_receivable = b_receivable;
        }
    }
}
