package com.shendeng_bianmin.agent.model;

import java.io.Serializable;
import java.util.List;

public class MenDianGuanLiModel implements Serializable {
    /**
     * msg_code : 0000
     * msg : ok
     * data : [{"merchant_type_name":"蛋糕奶茶","inst_photo_url":"http://192.168.1.127:8080/Frame/uploadFile/showImg?file_id=11594","contact_phone":"13091891781","business_status":"1","shop_area":"20","lun_bo_list":[{"inst_state":"1","img_order":"1","create_time":"2020-08-19","img_url":"https://test.hljsdkj.com/Frame/uploadFile/showImg?file_id=11591","inst_id":"262","img_id":"11591","inst_img_id":"123"}],"shop_capacity":"11","inst_hours_begin":"","inst_hours_end":"","is_box":"1","lunbo_img_count":"1","tao_can_list":[{"wares_id":"262","shop_money_now":"80.00","shop_product_title":"好吃不贵，又实惠","shop_money_old":"120.00","wares_name":"测试套餐一","wares_photo_url":"https://test.hljsdkj.com/Frame/uploadFile/showImg?file_id=11588"},{"wares_id":"264","shop_money_now":"1.00","shop_product_title":"烧烤是一种艺术","shop_money_old":"10000.00","wares_name":"红烧程序员","wares_photo_url":"https://test.hljsdkj.com/Frame/uploadFile/showImg?file_id=11590"}],"is_box_name":"有包厢","shop_state_name":"开启门店"}]
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

    public static class DataBean implements Serializable {
        /**
         * merchant_type_name : 蛋糕奶茶
         * inst_photo_url : http://192.168.1.127:8080/Frame/uploadFile/showImg?file_id=11594
         * contact_phone : 13091891781
         * business_status : 1
         * shop_area : 20
         * lun_bo_list : [{"inst_state":"1","img_order":"1","create_time":"2020-08-19","img_url":"https://test.hljsdkj.com/Frame/uploadFile/showImg?file_id=11591","inst_id":"262","img_id":"11591","inst_img_id":"123"}]
         * shop_capacity : 11
         * inst_hours_begin :
         * inst_hours_end :
         * is_box : 1
         * lunbo_img_count : 1
         * tao_can_list : [{"wares_id":"262","shop_money_now":"80.00","shop_product_title":"好吃不贵，又实惠","shop_money_old":"120.00","wares_name":"测试套餐一","wares_photo_url":"https://test.hljsdkj.com/Frame/uploadFile/showImg?file_id=11588"},{"wares_id":"264","shop_money_now":"1.00","shop_product_title":"烧烤是一种艺术","shop_money_old":"10000.00","wares_name":"红烧程序员","wares_photo_url":"https://test.hljsdkj.com/Frame/uploadFile/showImg?file_id=11590"}]
         * is_box_name : 有包厢
         * shop_state_name : 开启门店
         */

        private String merchant_type_name;
        private String inst_photo_url;
        private String contact_phone;
        private String business_status;
        private String shop_area;
        private String shop_capacity;
        private String inst_hours_begin;
        private String inst_hours_end;
        private String is_box;
        private String lunbo_img_count;
        private String is_box_name;
        private String shop_state_name;
        private List<LunBoListBean> lun_bo_list;
        private List<TaoCanListBean> tao_can_list;

        public String getMerchant_type_name() {
            return merchant_type_name;
        }

        public void setMerchant_type_name(String merchant_type_name) {
            this.merchant_type_name = merchant_type_name;
        }

        public String getInst_photo_url() {
            return inst_photo_url;
        }

        public void setInst_photo_url(String inst_photo_url) {
            this.inst_photo_url = inst_photo_url;
        }

        public String getContact_phone() {
            return contact_phone;
        }

        public void setContact_phone(String contact_phone) {
            this.contact_phone = contact_phone;
        }

        public String getBusiness_status() {
            return business_status;
        }

        public void setBusiness_status(String business_status) {
            this.business_status = business_status;
        }

        public String getShop_area() {
            return shop_area;
        }

        public void setShop_area(String shop_area) {
            this.shop_area = shop_area;
        }

        public String getShop_capacity() {
            return shop_capacity;
        }

        public void setShop_capacity(String shop_capacity) {
            this.shop_capacity = shop_capacity;
        }

        public String getInst_hours_begin() {
            return inst_hours_begin;
        }

        public void setInst_hours_begin(String inst_hours_begin) {
            this.inst_hours_begin = inst_hours_begin;
        }

        public String getInst_hours_end() {
            return inst_hours_end;
        }

        public void setInst_hours_end(String inst_hours_end) {
            this.inst_hours_end = inst_hours_end;
        }

        public String getIs_box() {
            return is_box;
        }

        public void setIs_box(String is_box) {
            this.is_box = is_box;
        }

        public String getLunbo_img_count() {
            return lunbo_img_count;
        }

        public void setLunbo_img_count(String lunbo_img_count) {
            this.lunbo_img_count = lunbo_img_count;
        }

        public String getIs_box_name() {
            return is_box_name;
        }

        public void setIs_box_name(String is_box_name) {
            this.is_box_name = is_box_name;
        }

        public String getShop_state_name() {
            return shop_state_name;
        }

        public void setShop_state_name(String shop_state_name) {
            this.shop_state_name = shop_state_name;
        }

        public List<LunBoListBean> getLun_bo_list() {
            return lun_bo_list;
        }

        public void setLun_bo_list(List<LunBoListBean> lun_bo_list) {
            this.lun_bo_list = lun_bo_list;
        }

        public List<TaoCanListBean> getTao_can_list() {
            return tao_can_list;
        }

        public void setTao_can_list(List<TaoCanListBean> tao_can_list) {
            this.tao_can_list = tao_can_list;
        }

        public static class LunBoListBean implements Serializable {
            /**
             * inst_state : 1
             * img_order : 1
             * create_time : 2020-08-19
             * img_url : https://test.hljsdkj.com/Frame/uploadFile/showImg?file_id=11591
             * inst_id : 262
             * img_id : 11591
             * inst_img_id : 123
             */

            private String inst_state;
            private String img_order;
            private String create_time;
            private String img_url;
            private String inst_id;
            private String img_id;
            private String inst_img_id;

            public String getInst_state() {
                return inst_state;
            }

            public void setInst_state(String inst_state) {
                this.inst_state = inst_state;
            }

            public String getImg_order() {
                return img_order;
            }

            public void setImg_order(String img_order) {
                this.img_order = img_order;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getInst_id() {
                return inst_id;
            }

            public void setInst_id(String inst_id) {
                this.inst_id = inst_id;
            }

            public String getImg_id() {
                return img_id;
            }

            public void setImg_id(String img_id) {
                this.img_id = img_id;
            }

            public String getInst_img_id() {
                return inst_img_id;
            }

            public void setInst_img_id(String inst_img_id) {
                this.inst_img_id = inst_img_id;
            }
        }

        public static class TaoCanListBean {
            /**
             * wares_id : 262
             * shop_money_now : 80.00
             * shop_product_title : 好吃不贵，又实惠
             * shop_money_old : 120.00
             * wares_name : 测试套餐一
             * wares_photo_url : https://test.hljsdkj.com/Frame/uploadFile/showImg?file_id=11588
             */

            private String wares_id;
            private String shop_money_now;
            private String shop_product_title;
            private String shop_money_old;
            private String wares_name;
            private String wares_photo_url;


            public String getWares_id() {
                return wares_id;
            }

            public void setWares_id(String wares_id) {
                this.wares_id = wares_id;
            }

            public String getShop_money_now() {
                return shop_money_now;
            }

            public void setShop_money_now(String shop_money_now) {
                this.shop_money_now = shop_money_now;
            }

            public String getShop_product_title() {
                return shop_product_title;
            }

            public void setShop_product_title(String shop_product_title) {
                this.shop_product_title = shop_product_title;
            }

            public String getShop_money_old() {
                return shop_money_old;
            }

            public void setShop_money_old(String shop_money_old) {
                this.shop_money_old = shop_money_old;
            }

            public String getWares_name() {
                return wares_name;
            }

            public void setWares_name(String wares_name) {
                this.wares_name = wares_name;
            }

            public String getWares_photo_url() {
                return wares_photo_url;
            }

            public void setWares_photo_url(String wares_photo_url) {
                this.wares_photo_url = wares_photo_url;
            }
        }
    }
}
