package com.shendeng_bianmin.agent.model;

import java.util.List;

public class ChandiModel {


    /**
     * msg_code : 0000
     * msg : ok
     * data : [{"code_name":"北京","city":[{"code_name":"北京市","code_id_up":"11","city":[],"code_id":"1101"}],"code_id":"11"},{"code_name":"天津","city":[{"code_name":"天津市","code_id_up":"12","city":[],"code_id":"1201"},{"code_name":"市辖县","code_id_up":"12","city":[],"code_id":"1202"}],"code_id":"12"},{"code_name":"河北省","city":[{"code_name":"石家庄市","code_id_up":"13","city":[],"code_id":"1301"},{"code_name":"唐山市","code_id_up":"13","city":[],"code_id":"1302"},{"code_name":"秦皇岛市","code_id_up":"13","city":[],"code_id":"1303"},{"code_name":"邯郸市","code_id_up":"13","city":[],"code_id":"1304"},{"code_name":"邢台市","code_id_up":"13","city":[],"code_id":"1305"},{"code_name":"保定市","code_id_up":"13","city":[],"code_id":"1306"},{"code_name":"张家口市","code_id_up":"13","city":[],"code_id":"1307"},{"code_name":"承德市","code_id_up":"13","city":[],"code_id":"1308"},{"code_name":"沧州市","code_id_up":"13","city":[],"code_id":"1309"},{"code_name":"廊坊市","code_id_up":"13","city":[],"code_id":"1310"},{"code_name":"衡水市","code_id_up":"13","city":[],"code_id":"1311"}],"code_id":"13"},{"code_name":"山西","city":[{"code_name":"太原市","code_id_up":"14","city":[],"code_id":"1401"},{"code_name":"大同市","code_id_up":"14","city":[],"code_id":"1402"},{"code_name":"阳泉市","code_id_up":"14","city":[],"code_id":"1403"},{"code_name":"长治市","code_id_up":"14","city":[],"code_id":"1404"},{"code_name":"晋城市","code_id_up":"14","city":[],"code_id":"1405"},{"code_name":"朔州市","code_id_up":"14","city":[],"code_id":"1406"},{"code_name":"晋中市","code_id_up":"14","city":[],"code_id":"1407"},{"code_name":"运城市","code_id_up":"14","city":[],"code_id":"1408"},{"code_name":"忻州市","code_id_up":"14","city":[],"code_id":"1409"},{"code_name":"临汾市","code_id_up":"14","city":[],"code_id":"1410"},{"code_name":"吕梁市","code_id_up":"14","city":[],"code_id":"1411"}],"code_id":"14"},{"code_name":"内蒙古自治区","city":[{"code_name":"呼和浩特市","code_id_up":"15","city":[],"code_id":"1501"},{"code_name":"包头市","code_id_up":"15","city":[],"code_id":"1502"},{"code_name":"乌海市","code_id_up":"15","city":[],"code_id":"1503"},{"code_name":"赤峰市","code_id_up":"15","city":[],"code_id":"1504"},{"code_name":"通辽市","code_id_up":"15","city":[],"code_id":"1505"},{"code_name":"鄂尔多斯市","code_id_up":"15","city":[],"code_id":"1506"},{"code_name":"呼伦贝尔市","code_id_up":"15","city":[],"code_id":"1507"},{"code_name":"巴彦淖尔市","code_id_up":"15","city":[],"code_id":"1508"},{"code_name":"乌兰察布市","code_id_up":"15","city":[],"code_id":"1509"},{"code_name":"兴安盟","code_id_up":"15","city":[],"code_id":"1522"},{"code_name":"锡林郭勒盟","code_id_up":"15","city":[],"code_id":"1525"},{"code_name":"阿拉善盟","code_id_up":"15","city":[],"code_id":"1529"}],"code_id":"15"},{"code_name":"辽宁省","city":[{"code_name":"沈阳市","code_id_up":"21","city":[],"code_id":"2101"},{"code_name":"大连市","code_id_up":"21","city":[],"code_id":"2102"},{"code_name":"鞍山市","code_id_up":"21","city":[],"code_id":"2103"},{"code_name":"抚顺市","code_id_up":"21","city":[],"code_id":"2104"},{"code_name":"本溪市","code_id_up":"21","city":[],"code_id":"2105"},{"code_name":"丹东市","code_id_up":"21","city":[],"code_id":"2106"},{"code_name":"锦州市","code_id_up":"21","city":[],"code_id":"2107"},{"code_name":"营口市","code_id_up":"21","city":[],"code_id":"2108"},{"code_name":"阜新市","code_id_up":"21","city":[],"code_id":"2109"},{"code_name":"辽阳市","code_id_up":"21","city":[],"code_id":"2110"},{"code_name":"盘锦市","code_id_up":"21","city":[],"code_id":"2111"},{"code_name":"铁岭市","code_id_up":"21","city":[],"code_id":"2112"},{"code_name":"朝阳市","code_id_up":"21","city":[],"code_id":"2113"},{"code_name":"葫芦岛市","code_id_up":"21","city":[],"code_id":"2114"}],"code_id":"21"}]
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
         * code_name : 北京
         * city : [{"code_name":"北京市","code_id_up":"11","city":[],"code_id":"1101"}]
         * code_id : 11
         */

        private String code_name;
        private String code_id;
        private List<CityBean> city;

        public String getCode_name() {
            return code_name;
        }

        public void setCode_name(String code_name) {
            this.code_name = code_name;
        }

        public String getCode_id() {
            return code_id;
        }

        public void setCode_id(String code_id) {
            this.code_id = code_id;
        }

        public List<CityBean> getCity() {
            return city;
        }

        public void setCity(List<CityBean> city) {
            this.city = city;
        }

        public static class CityBean {
            /**
             * code_name : 北京市
             * code_id_up : 11
             * city : []
             * code_id : 1101
             */

            private String code_name;
            private String code_id_up;
            private String code_id;
            private List<?> city;

            public String getCode_name() {
                return code_name;
            }

            public void setCode_name(String code_name) {
                this.code_name = code_name;
            }

            public String getCode_id_up() {
                return code_id_up;
            }

            public void setCode_id_up(String code_id_up) {
                this.code_id_up = code_id_up;
            }

            public String getCode_id() {
                return code_id;
            }

            public void setCode_id(String code_id) {
                this.code_id = code_id;
            }

            public List<?> getCity() {
                return city;
            }

            public void setCity(List<?> city) {
                this.city = city;
            }
        }
    }
}
