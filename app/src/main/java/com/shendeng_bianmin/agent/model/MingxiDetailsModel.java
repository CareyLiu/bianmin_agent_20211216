package com.shendeng_bianmin.agent.model;

import java.util.List;

public class MingxiDetailsModel {


    /**
     * msg_code : 0000
     * msg : ok
     * row_num : 3
     * data : [{"income_money":"10.40","income_name":"商家实际收入","distribution_list":[{"money":"10.50","name":"订单金额"},{"money":"0.08","name":"分润给购买者越加按钮的红包"},{"money":"0.02","name":"分润给南岗区经销商的红包"}]}]
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
         * income_money : 10.40
         * income_name : 商家实际收入
         * distribution_list : [{"money":"10.50","name":"订单金额"},{"money":"0.08","name":"分润给购买者越加按钮的红包"},{"money":"0.02","name":"分润给南岗区经销商的红包"}]
         */

        private String income_money;
        private String income_name;
        private List<DistributionListBean> distribution_list;

        public String getIncome_money() {
            return income_money;
        }

        public void setIncome_money(String income_money) {
            this.income_money = income_money;
        }

        public String getIncome_name() {
            return income_name;
        }

        public void setIncome_name(String income_name) {
            this.income_name = income_name;
        }

        public List<DistributionListBean> getDistribution_list() {
            return distribution_list;
        }

        public void setDistribution_list(List<DistributionListBean> distribution_list) {
            this.distribution_list = distribution_list;
        }

        public static class DistributionListBean {
            /**
             * money : 10.50
             * name : 订单金额
             */

            private String money;
            private String name;

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
