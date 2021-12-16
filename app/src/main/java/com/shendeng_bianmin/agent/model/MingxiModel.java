package com.shendeng_bianmin.agent.model;

import java.util.List;

public class MingxiModel {

    /**
     * next : 1
     * msg_code : 0000
     * msg : ok
     * row_num : 12
     * data : [{"pay_cost_type":"3","pay_cost_id":"852","pay_user_state":"2","money":"¥3.00","create_time":"2020-07-08","pay_user_state_name":"完成","title":"余额提现"},{"pay_cost_type":"1","pay_cost_id":"849","pay_user_state":"1","money":"¥0.40","create_time":"2020-07-07","pay_user_state_name":"处理中","title":"商城订单"},{"pay_cost_type":"1","pay_cost_id":"848","pay_user_state":"1","money":"¥0.40","create_time":"2020-07-07","pay_user_state_name":"处理中","title":"商城订单"},{"pay_cost_type":"1","pay_cost_id":"847","pay_user_state":"1","money":"¥0.40","create_time":"2020-07-07","pay_user_state_name":"处理中","title":"商城订单"},{"pay_cost_type":"3","pay_cost_id":"842","pay_user_state":"2","money":"¥3.00","create_time":"2020-07-04","pay_user_state_name":"完成","title":"提现"},{"pay_cost_type":"3","pay_cost_id":"841","pay_user_state":"2","money":"¥3.00","create_time":"2020-07-04","pay_user_state_name":"完成","title":"提现"},{"pay_cost_type":"3","pay_cost_id":"840","pay_user_state":"2","money":"¥1.00","create_time":"2020-07-04","pay_user_state_name":"完成","title":"提现"},{"pay_cost_type":"3","pay_cost_id":"839","pay_user_state":"2","money":"¥3.00","create_time":"2020-07-04","pay_user_state_name":"完成","title":"提现"},{"pay_cost_type":"1","pay_cost_id":"827","pay_user_state":"1","money":"¥0.40","create_time":"2020-07-02","pay_user_state_name":"处理中","title":"商城订单"},{"pay_cost_type":"3","pay_cost_id":"806","pay_user_state":"2","money":"¥1.00","create_time":"2020-06-24","pay_user_state_name":"完成","title":"提现"},{"pay_cost_type":"1","pay_cost_id":"804","pay_user_state":"1","money":"¥0.40","create_time":"2020-06-20","pay_user_state_name":"处理中","title":"商城订单"},{"pay_cost_type":"1","pay_cost_id":"803","pay_user_state":"1","money":"¥0.40","create_time":"2020-06-20","pay_user_state_name":"处理中","title":"商城订单"}]
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
         * pay_cost_type : 3
         * pay_cost_id : 852
         * pay_user_state : 2
         * money : ¥3.00
         * create_time : 2020-07-08
         * pay_user_state_name : 完成
         * title : 余额提现
         */

        private String pay_cost_type;
        private String pay_cost_id;
        private String pay_user_state;
        private String money;
        private String create_time;
        private String pay_user_state_name;
        private String title;

        public String getPay_cost_type() {
            return pay_cost_type;
        }

        public void setPay_cost_type(String pay_cost_type) {
            this.pay_cost_type = pay_cost_type;
        }

        public String getPay_cost_id() {
            return pay_cost_id;
        }

        public void setPay_cost_id(String pay_cost_id) {
            this.pay_cost_id = pay_cost_id;
        }

        public String getPay_user_state() {
            return pay_user_state;
        }

        public void setPay_user_state(String pay_user_state) {
            this.pay_user_state = pay_user_state;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPay_user_state_name() {
            return pay_user_state_name;
        }

        public void setPay_user_state_name(String pay_user_state_name) {
            this.pay_user_state_name = pay_user_state_name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
