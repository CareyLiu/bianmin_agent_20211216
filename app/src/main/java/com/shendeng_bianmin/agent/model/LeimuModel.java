package com.shendeng_bianmin.agent.model;

import java.util.List;

public class LeimuModel {


    /**
     * msg_code : 0000
     * msg : ok
     * data : [{"item_id_up":"0","order_flag":"first","next_level":[{"item_id_up":"1657","item_name":"风水摆件","order_flag":"-","next_level":[{"item_id_up":"1658","item_name":"风水摆件","order_flag":"-","next_level":[],"item_id":"1659"}],"item_id":"1658"}],"item_id":"1657","itemtype_id":"1","item_name":"风水摆件","is_install":"0"},{"item_id_up":"0","item_name":"运动户外","next_level":[{"item_id_up":"87","item_name":"热销分类","order_flag":"-","next_level":[],"item_id":"756"}],"item_id":"87","is_install":"0","itemtype_id":"1"},{"item_id_up":"0","item_name":"家具家装","next_level":[{"item_id_up":"91","item_name":"当季热卖","order_flag":"-","next_level":[{"item_id_up":"1103","item_name":"实木床","order_flag":"first","next_level":[],"item_id":"1105"},{"item_id_up":"1103","item_name":"沙发/椅","order_flag":"last","next_level":[],"item_id":"1104"}],"item_id":"1103"}],"item_id":"91","is_install":"1","itemtype_id":"1"},{"item_id_up":"0","item_name":"家居家纺","next_level":[{"item_id_up":"90","item_name":"当季热卖","order_flag":"-","next_level":[{"item_id_up":"980","item_name":"全棉套件","order_flag":"-","next_level":[],"item_id":"981"}],"item_id":"980"}],"item_id":"90","is_install":"0","itemtype_id":"1"},{"item_id_up":"0","item_name":"家用电器","next_level":[{"item_id_up":"89","item_name":"热销分类","order_flag":"-","next_level":[{"item_id_up":"905","item_name":"电压力锅","order_flag":"first","next_level":[],"item_id":"907"},{"item_id_up":"905","item_name":"电饭煲","order_flag":"last","next_level":[],"item_id":"906"}],"item_id":"905"}],"item_id":"89","is_install":"0","itemtype_id":"1"},{"item_id_up":"0","item_name":"护肤彩妆","next_level":[{"item_id_up":"84","item_name":"热销推荐","order_flag":"-","next_level":[{"item_id_up":"512","item_name":"护肤套装","order_flag":"-","next_level":[],"item_id":"513"}],"item_id":"512"}],"item_id":"84","is_install":"0","itemtype_id":"1"},{"item_id_up":"0","item_name":"手表配饰","next_level":[{"item_id_up":"83","item_name":"手表","order_flag":"first","next_level":[{"item_id_up":"436","item_name":"石英男表","order_flag":"first","next_level":[],"item_id":"440"},{"item_id_up":"436","item_name":"机械男表","order_flag":"last","next_level":[],"item_id":"437"}],"item_id":"436"},{"item_id_up":"83","item_name":"精致好物","order_flag":"last","next_level":[{"item_id_up":"417","item_name":"黄金饰品","order_flag":"-","next_level":[],"item_id":"418"}],"item_id":"417"}],"item_id":"83","is_install":"0","itemtype_id":"1"},{"item_id_up":"0","item_name":"箱包皮具","next_level":[{"item_id_up":"82","item_name":"热销分类","order_flag":"-","next_level":[{"item_id_up":"377","item_name":"手提包","order_flag":"first","next_level":[],"item_id":"379"},{"item_id_up":"377","item_name":"男包","order_flag":"last","next_level":[],"item_id":"381"}],"item_id":"377"}],"item_id":"82","is_install":"0","itemtype_id":"1"},{"item_id_up":"0","item_name":"男鞋","next_level":[{"item_id_up":"81","item_name":"热销分类","order_flag":"-","next_level":[{"item_id_up":"350","item_name":"休闲鞋","order_flag":"first","next_level":[],"item_id":"351"},{"item_id_up":"350","item_name":"运动鞋","order_flag":"last","next_level":[],"item_id":"352"}],"item_id":"350"}],"item_id":"81","is_install":"0","itemtype_id":"1"},{"item_id_up":"0","item_name":"女鞋","next_level":[{"item_id_up":"80","item_name":"热销分类","order_flag":"-","next_level":[{"item_id_up":"316","item_name":"运动鞋","order_flag":"first","next_level":[],"item_id":"324"},{"item_id_up":"316","item_name":"单鞋","order_flag":"last","next_level":[],"item_id":"318"}],"item_id":"316"}],"item_id":"80","is_install":"0","itemtype_id":"1"}]
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
         * item_id_up : 0
         * order_flag : first
         * next_level : [{"item_id_up":"1657","item_name":"风水摆件","order_flag":"-","next_level":[{"item_id_up":"1658","item_name":"风水摆件","order_flag":"-","next_level":[],"item_id":"1659"}],"item_id":"1658"}]
         * item_id : 1657
         * itemtype_id : 1
         * item_name : 风水摆件
         * is_install : 0
         */

        private String item_id_up;
        private String order_flag;
        private String item_id;
        private String itemtype_id;
        private String item_name;
        private String is_install;
        private List<NextLevelBeanX> next_level;

        public String getItem_id_up() {
            return item_id_up;
        }

        public void setItem_id_up(String item_id_up) {
            this.item_id_up = item_id_up;
        }

        public String getOrder_flag() {
            return order_flag;
        }

        public void setOrder_flag(String order_flag) {
            this.order_flag = order_flag;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getItemtype_id() {
            return itemtype_id;
        }

        public void setItemtype_id(String itemtype_id) {
            this.itemtype_id = itemtype_id;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getIs_install() {
            return is_install;
        }

        public void setIs_install(String is_install) {
            this.is_install = is_install;
        }

        public List<NextLevelBeanX> getNext_level() {
            return next_level;
        }

        public void setNext_level(List<NextLevelBeanX> next_level) {
            this.next_level = next_level;
        }

        public static class NextLevelBeanX {
            /**
             * item_id_up : 1657
             * item_name : 风水摆件
             * order_flag : -
             * next_level : [{"item_id_up":"1658","item_name":"风水摆件","order_flag":"-","next_level":[],"item_id":"1659"}]
             * item_id : 1658
             */

            private String item_id_up;
            private String item_name;
            private String order_flag;
            private String item_id;
            private List<NextLevelBean> next_level;

            public String getItem_id_up() {
                return item_id_up;
            }

            public void setItem_id_up(String item_id_up) {
                this.item_id_up = item_id_up;
            }

            public String getItem_name() {
                return item_name;
            }

            public void setItem_name(String item_name) {
                this.item_name = item_name;
            }

            public String getOrder_flag() {
                return order_flag;
            }

            public void setOrder_flag(String order_flag) {
                this.order_flag = order_flag;
            }

            public String getItem_id() {
                return item_id;
            }

            public void setItem_id(String item_id) {
                this.item_id = item_id;
            }

            public List<NextLevelBean> getNext_level() {
                return next_level;
            }

            public void setNext_level(List<NextLevelBean> next_level) {
                this.next_level = next_level;
            }

            public static class NextLevelBean {
                /**
                 * item_id_up : 1658
                 * item_name : 风水摆件
                 * order_flag : -
                 * next_level : []
                 * item_id : 1659
                 */

                private String item_id_up;
                private String item_name;
                private String order_flag;
                private String item_id;
                private List<?> next_level;

                public String getItem_id_up() {
                    return item_id_up;
                }

                public void setItem_id_up(String item_id_up) {
                    this.item_id_up = item_id_up;
                }

                public String getItem_name() {
                    return item_name;
                }

                public void setItem_name(String item_name) {
                    this.item_name = item_name;
                }

                public String getOrder_flag() {
                    return order_flag;
                }

                public void setOrder_flag(String order_flag) {
                    this.order_flag = order_flag;
                }

                public String getItem_id() {
                    return item_id;
                }

                public void setItem_id(String item_id) {
                    this.item_id = item_id;
                }

                public List<?> getNext_level() {
                    return next_level;
                }

                public void setNext_level(List<?> next_level) {
                    this.next_level = next_level;
                }
            }
        }
    }
}
