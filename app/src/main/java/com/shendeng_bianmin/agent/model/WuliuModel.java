package com.shendeng_bianmin.agent.model;

import java.util.List;

public class WuliuModel {


    /**
     * msg_code : 0000
     * msg : ok
     * data : [{"name":"aae全球专递","text7":"","id":"1","val2":"","text1":"","val1":"aae"},{"name":"安捷快递","text7":"","id":"2","val2":"","text1":"","val1":"anjie"},{"name":"安信达快递","text7":"","id":"3","val2":"","text1":"","val1":"anxindakuaixi"},{"name":"彪记快递","text7":"","id":"4","val2":"","text1":"","val1":"biaojikuaidi"},{"name":"bht","text7":"","id":"5","val2":"","text1":"","val1":"bht"},{"name":"百福东方国际物流","text7":"","id":"6","val2":"","text1":"","val1":"baifudongfang"},{"name":"中国东方（COE）","text7":"","id":"7","val2":"","text1":"","val1":"coe"},{"name":"长宇物流","text7":"","id":"8","val2":"","text1":"","val1":"changyuwuliu"},{"name":"大田物流","text7":"","id":"9","val2":"","text1":"","val1":"datianwuliu"},{"name":"德邦物流","text7":"","id":"10","val2":"","text1":"","val1":"debangwuliu"},{"name":"dhl","text7":"","id":"11","val2":"","text1":"","val1":"dhl"},{"name":"dpex","text7":"","id":"12","val2":"","text1":"","val1":"dpex"},{"name":"d速快递","text7":"","id":"13","val2":"","text1":"","val1":"dsukuaidi"},{"name":"递四方","text7":"","id":"14","val2":"","text1":"","val1":"disifang"},{"name":"ems快递","text7":"","id":"15","val2":"","text1":"","val1":"ems"},{"name":"fedex（国外）","text7":"","id":"16","val2":"","text1":"","val1":"fedex"},{"name":"飞康达物流","text7":"","id":"17","val2":"","text1":"","val1":"feikangda"},{"name":"飞快达","text7":"","id":"18","val2":"","text1":"","val1":"feikuaida"},{"name":"国通快递","text7":"","id":"19","val2":"","text1":"","val1":"guotongkuaidi"},{"name":"港中能达物流","text7":"","id":"20","val2":"","text1":"","val1":"ganzhongnengda"},{"name":"广东邮政物流","text7":"","id":"21","val2":"","text1":"","val1":"guangdongyouzhengwuliu"},{"name":"共速达","text7":"","id":"22","val2":"","text1":"","val1":"gongsuda"},{"name":"汇通快运","text7":"","id":"23","val2":"","text1":"","val1":"huitongkuaidi"},{"name":"恒路物流","text7":"","id":"24","val2":"","text1":"","val1":"hengluwuliu"},{"name":"华夏龙物流","text7":"","id":"25","val2":"","text1":"","val1":"huaxialongwuliu"},{"name":"海红","text7":"","id":"26","val2":"","text1":"","val1":"haihongwangsong"},{"name":"海外环球","text7":"","id":"27","val2":"","text1":"","val1":"haiwaihuanqiu"},{"name":"佳怡物流","text7":"","id":"28","val2":"","text1":"","val1":"jiayiwuliu"},{"name":"京广速递","text7":"","id":"29","val2":"","text1":"","val1":"jinguangsudikuaijian"},{"name":"急先达","text7":"","id":"30","val2":"","text1":"","val1":"jixianda"},{"name":"佳吉物流","text7":"","id":"31","val2":"","text1":"","val1":"jjwl"},{"name":"加运美物流","text7":"","id":"32","val2":"","text1":"","val1":"jymwl"},{"name":"金大物流","text7":"","id":"33","val2":"","text1":"","val1":"jindawuliu"},{"name":"嘉里大通","text7":"","id":"34","val2":"","text1":"","val1":"jialidatong"},{"name":"晋越快递","text7":"","id":"35","val2":"","text1":"","val1":"jykd"},{"name":"快捷速递","text7":"","id":"36","val2":"","text1":"","val1":"kuaijiesudi"},{"name":"联邦快递（国内）","text7":"","id":"37","val2":"","text1":"","val1":"lianb"},{"name":"联昊通物流","text7":"","id":"38","val2":"","text1":"","val1":"lianhaowuliu"},{"name":"龙邦物流","text7":"","id":"39","val2":"","text1":"","val1":"longbanwuliu"},{"name":"立即送","text7":"","id":"40","val2":"","text1":"","val1":"lijisong"},{"name":"乐捷递","text7":"","id":"41","val2":"","text1":"","val1":"lejiedi"},{"name":"凤凰快递","text7":"","id":"42","val2":"","text1":"","val1":"fenghuangkuaidi"},{"name":"民航快递","text7":"","id":"43","val2":"","text1":"","val1":"minghangkuaidi"},{"name":"美国快递","text7":"","id":"44","val2":"","text1":"","val1":"meiguokuaidi"},{"name":"门对门","text7":"","id":"45","val2":"","text1":"","val1":"menduimen"},{"name":"OCS","text7":"","id":"46","val2":"","text1":"","val1":"ocs"},{"name":"配思货运","text7":"","id":"47","val2":"","text1":"","val1":"peisihuoyunkuaidi"}]
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
         * name : aae全球专递
         * text7 :
         * id : 1
         * val2 :
         * text1 :
         * val1 : aae
         */

        private String name;
        private String text7;
        private String id;
        private String val2;
        private String text1;
        private String val1;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getText7() {
            return text7;
        }

        public void setText7(String text7) {
            this.text7 = text7;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVal2() {
            return val2;
        }

        public void setVal2(String val2) {
            this.val2 = val2;
        }

        public String getText1() {
            return text1;
        }

        public void setText1(String text1) {
            this.text1 = text1;
        }

        public String getVal1() {
            return val1;
        }

        public void setVal1(String val1) {
            this.val1 = val1;
        }
    }
}
