package com.shendeng_bianmin.agent.model.tuangou;

import java.util.List;

public class TuanMingxiModel {


    /**
     * next : 1
     * msg_code : 0000
     * msg : ok
     * data : [{"income":"收入¥270.32","cunsumerList":[{"pay_cost_id":"577","time":"05月27日   17:06","title":"直接下单","money":"8.50"},{"pay_cost_id":"567","time":"05月24日   12:02","title":"直接下单","money":"0.85"},{"pay_cost_id":"523","time":"05月16日   22:03","title":"直接下单","money":"0.85"},{"pay_cost_id":"521","time":"05月16日   18:01","title":"直接下单","money":"68.00"},{"pay_cost_id":"519","time":"05月16日   17:44","title":"直接下单","money":"68.00"},{"pay_cost_id":"515","time":"05月16日   15:22","title":"直接下单","money":"0.10"},{"pay_cost_id":"508","time":"05月16日   15:08","title":"直接下单","money":"0.10"},{"pay_cost_id":"507","time":"05月16日   15:02","title":"直接下单","money":"68.00"}],"month":"2020-05"}]
     */

    private String next;
    private String msg_code;
    private String msg;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * income : 收入¥270.32
         * cunsumerList : [{"pay_cost_id":"577","time":"05月27日   17:06","title":"直接下单","money":"8.50"},{"pay_cost_id":"567","time":"05月24日   12:02","title":"直接下单","money":"0.85"},{"pay_cost_id":"523","time":"05月16日   22:03","title":"直接下单","money":"0.85"},{"pay_cost_id":"521","time":"05月16日   18:01","title":"直接下单","money":"68.00"},{"pay_cost_id":"519","time":"05月16日   17:44","title":"直接下单","money":"68.00"},{"pay_cost_id":"515","time":"05月16日   15:22","title":"直接下单","money":"0.10"},{"pay_cost_id":"508","time":"05月16日   15:08","title":"直接下单","money":"0.10"},{"pay_cost_id":"507","time":"05月16日   15:02","title":"直接下单","money":"68.00"}]
         * month : 2020-05
         */

        private String income;
        private String month;
        private List<CunsumerListBean> cunsumerList;

        public String getIncome() {
            return income;
        }

        public void setIncome(String income) {
            this.income = income;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public List<CunsumerListBean> getCunsumerList() {
            return cunsumerList;
        }

        public void setCunsumerList(List<CunsumerListBean> cunsumerList) {
            this.cunsumerList = cunsumerList;
        }

        public static class CunsumerListBean {
            /**
             * pay_cost_id : 577
             * time : 05月27日   17:06
             * title : 直接下单
             * money : 8.50
             */

            private String pay_cost_id;
            private String time;
            private String title;
            private String money;

            public String getPay_cost_id() {
                return pay_cost_id;
            }

            public void setPay_cost_id(String pay_cost_id) {
                this.pay_cost_id = pay_cost_id;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }
        }
    }
}
