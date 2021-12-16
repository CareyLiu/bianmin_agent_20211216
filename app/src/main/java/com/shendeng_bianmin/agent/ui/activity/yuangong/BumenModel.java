package com.shendeng_bianmin.agent.ui.activity.yuangong;

import java.util.List;

public class BumenModel {


    /**
     * msg_code : 0000
     * msg : ok
     * row_num : 2
     * data : [{"order_flag":"first","branch_id":"378","branch":[{"role_name":"系统管理员","branch_id_up":"378","order_flag":"-","role_id":"364","branch":[]}],"branch_name":"系统管理"},{"order_flag":"last","branch_id":"379","branch":[{"role_name":"客服","branch_id_up":"379","order_flag":"-","role_id":"365","branch":[]}],"branch_name":"客服部门"}]
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
         * order_flag : first
         * branch_id : 378
         * branch : [{"role_name":"系统管理员","branch_id_up":"378","order_flag":"-","role_id":"364","branch":[]}]
         * branch_name : 系统管理
         */

        private String order_flag;
        private String branch_id;
        private String branch_name;
        private List<BranchBean> branch;

        public String getOrder_flag() {
            return order_flag;
        }

        public void setOrder_flag(String order_flag) {
            this.order_flag = order_flag;
        }

        public String getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(String branch_id) {
            this.branch_id = branch_id;
        }

        public String getBranch_name() {
            return branch_name;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }

        public List<BranchBean> getBranch() {
            return branch;
        }

        public void setBranch(List<BranchBean> branch) {
            this.branch = branch;
        }

        public static class BranchBean {
            /**
             * role_name : 系统管理员
             * branch_id_up : 378
             * order_flag : -
             * role_id : 364
             * branch : []
             */

            private String role_name;
            private String branch_id_up;
            private String order_flag;
            private String role_id;
            private List<?> branch;

            public String getRole_name() {
                return role_name;
            }

            public void setRole_name(String role_name) {
                this.role_name = role_name;
            }

            public String getBranch_id_up() {
                return branch_id_up;
            }

            public void setBranch_id_up(String branch_id_up) {
                this.branch_id_up = branch_id_up;
            }

            public String getOrder_flag() {
                return order_flag;
            }

            public void setOrder_flag(String order_flag) {
                this.order_flag = order_flag;
            }

            public String getRole_id() {
                return role_id;
            }

            public void setRole_id(String role_id) {
                this.role_id = role_id;
            }

            public List<?> getBranch() {
                return branch;
            }

            public void setBranch(List<?> branch) {
                this.branch = branch;
            }
        }
    }
}
