package com.shendeng_bianmin.agent.model.tuangou;

import java.util.List;

public class EwmModel {

    /**
     * msg_code : 0000
     * msg : ok
     * data : [{"url_app":"https://test.hljsdkj.com/shop_new/business?inst_id=187&shop_type=null","url_wx":"https://test.hljsdkj.com/inst/main/xc/twoCode?content=https://test.hljsdkj.com/shop_new/business?inst_id=187&shop_type=null"}]
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
         * url_app : https://test.hljsdkj.com/shop_new/business?inst_id=187&shop_type=null
         * url_wx : https://test.hljsdkj.com/inst/main/xc/twoCode?content=https://test.hljsdkj.com/shop_new/business?inst_id=187&shop_type=null
         */

        private String url_app;
        private String url_wx;

        public String getUrl_app() {
            return url_app;
        }

        public void setUrl_app(String url_app) {
            this.url_app = url_app;
        }

        public String getUrl_wx() {
            return url_wx;
        }

        public void setUrl_wx(String url_wx) {
            this.url_wx = url_wx;
        }
    }
}
