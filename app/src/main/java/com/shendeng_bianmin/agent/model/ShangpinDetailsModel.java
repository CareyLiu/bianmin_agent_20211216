package com.shendeng_bianmin.agent.model;

import java.io.Serializable;
import java.util.List;

public class ShangpinDetailsModel implements Serializable {

    /**
     * msg_code : 0000
     * msg : ok
     * row_num : 0
     * data : [{"place_name":"黑龙江省哈尔滨市","wares_photo_id":"10115","imgText_list":[],"shop_product_title":"风水摆件","wares_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10115","wares_money_go":"5.00","is_installable":"","img_list":[],"item_name":"风水摆件风水摆件风水摆件","wares_detail":"<p><img src=\"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10127\" data-filename=\"QQ截图20200109131624.png\" style=\"width: 742px;\"><img src=\"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10128\" data-filename=\"QQ截图20200109131655.png\" style=\"width: 743px;\"><img src=\"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10129\" data-filename=\"QQ截图20200109131735.png\" style=\"width: 743px;\"><img src=\"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10130\" data-filename=\"QQ截图20200109131750.png\" style=\"width: 742px;\"><img src=\"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10131\" data-filename=\"QQ截图20200109131808.png\" style=\"width: 743px;\"><br><\/p>","package_list":[{"lock_count":"0","index_show":"2","money_now_first":"","product_count":"0","display_state":"2","money_old":"2000.00","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10121","red_packet":"0.10","shop_product_order":"1","wares_id":"180","first_level_distribution":"0.10","update_time":"2020-01-09","contribution":"100","make_show":"","state_name":"下架","subsystem_id":"jcz","index_photo_id":"10121","shop_product_id":"92","inst_id":"187","state_id":"2","two_level_distribution":"0.10","product_state":"2","pay_count":"0","create_time":"2020-01-09","shop_product_count":"0","pro_show":"2","product_no":"1","product_title":"套餐一","red_set_type":"","money_now":"1500.00","money_make":""}],"wares_state":"1"}]
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

    public static class DataBean implements Serializable {
        /**
         * place_name : 黑龙江省哈尔滨市
         * wares_photo_id : 10115
         * imgText_list : []
         * shop_product_title : 风水摆件
         * wares_photo_url : https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10115
         * wares_money_go : 5.00
         * is_installable :
         * img_list : []
         * item_name : 风水摆件风水摆件风水摆件
         * wares_detail : <p><img src="https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10127" data-filename="QQ截图20200109131624.png" style="width: 742px;"><img src="https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10128" data-filename="QQ截图20200109131655.png" style="width: 743px;"><img src="https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10129" data-filename="QQ截图20200109131735.png" style="width: 743px;"><img src="https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10130" data-filename="QQ截图20200109131750.png" style="width: 742px;"><img src="https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10131" data-filename="QQ截图20200109131808.png" style="width: 743px;"><br></p>
         * package_list : [{"lock_count":"0","index_show":"2","money_now_first":"","product_count":"0","display_state":"2","money_old":"2000.00","index_photo_url":"https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10121","red_packet":"0.10","shop_product_order":"1","wares_id":"180","first_level_distribution":"0.10","update_time":"2020-01-09","contribution":"100","make_show":"","state_name":"下架","subsystem_id":"jcz","index_photo_id":"10121","shop_product_id":"92","inst_id":"187","state_id":"2","two_level_distribution":"0.10","product_state":"2","pay_count":"0","create_time":"2020-01-09","shop_product_count":"0","pro_show":"2","product_no":"1","product_title":"套餐一","red_set_type":"","money_now":"1500.00","money_make":""}]
         * wares_state : 1
         */

        public String shop_money_now;
        public String wares_number;
        public String wares_count;

        private String place_name;
        private String wares_photo_id;
        private String shop_product_title;
        private String wares_photo_url;
        private String wares_money_go;
        private String is_installable;
        private String install_money;
        private String item_name;
        private String wares_detail;
        private String wares_state;
        private String wares_id;
        private List<ImgTextListBeen> imgText_list;
        private List<ImgListBean> img_list;
        private List<PackageListBean> package_list;

        public String getWares_id() {
            return wares_id;
        }

        public void setWares_id(String wares_id) {
            this.wares_id = wares_id;
        }

        public String getPlace_name() {
            return place_name;
        }

        public void setPlace_name(String place_name) {
            this.place_name = place_name;
        }

        public String getWares_photo_id() {
            return wares_photo_id;
        }

        public void setWares_photo_id(String wares_photo_id) {
            this.wares_photo_id = wares_photo_id;
        }

        public String getShop_product_title() {
            return shop_product_title;
        }

        public void setShop_product_title(String shop_product_title) {
            this.shop_product_title = shop_product_title;
        }

        public String getWares_photo_url() {
            return wares_photo_url;
        }

        public void setWares_photo_url(String wares_photo_url) {
            this.wares_photo_url = wares_photo_url;
        }

        public String getWares_money_go() {
            return wares_money_go;
        }

        public void setWares_money_go(String wares_money_go) {
            this.wares_money_go = wares_money_go;
        }

        public String getIs_installable() {
            return is_installable;
        }

        public void setIs_installable(String is_installable) {
            this.is_installable = is_installable;
        }

        public String getInstall_money() {
            return install_money;
        }

        public void setInstall_money(String install_money) {
            this.install_money = install_money;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getWares_detail() {
            return wares_detail;
        }

        public void setWares_detail(String wares_detail) {
            this.wares_detail = wares_detail;
        }

        public String getWares_state() {
            return wares_state;
        }

        public void setWares_state(String wares_state) {
            this.wares_state = wares_state;
        }

        public List<ImgTextListBeen> getImgText_list() {
            return imgText_list;
        }

        public void setImgText_list(List<ImgTextListBeen> imgText_list) {
            this.imgText_list = imgText_list;
        }

        public List<ImgListBean> getImg_list() {
            return img_list;
        }

        public void setImg_list(List<ImgListBean> img_list) {
            this.img_list = img_list;
        }

        public List<PackageListBean> getPackage_list() {
            return package_list;
        }

        public void setPackage_list(List<PackageListBean> package_list) {
            this.package_list = package_list;
        }

        public static class ImgListBean implements Serializable {
            /**
             * wares_img_id : 323
             * img_url : https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=9928
             * img_id : 9928
             */

            private String wares_img_id;
            private String img_url;
            private String img_id;

            public String getWares_img_id() {
                return wares_img_id;
            }

            public void setWares_img_id(String wares_img_id) {
                this.wares_img_id = wares_img_id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getImg_id() {
                return img_id;
            }

            public void setImg_id(String img_id) {
                this.img_id = img_id;
            }
        }

        public static class ImgTextListBeen implements Serializable {

            /**
             * img_width : 800
             * wares_detail_img_id : 156
             * img_url : https://test.hljsdkj.com/Frame/uploadFile/showImg?file_id=11522
             * img_height : 800
             */

            private String img_width;
            private String wares_detail_img_id;
            private String img_url;
            private String img_height;

            public String getImg_width() {
                return img_width;
            }

            public void setImg_width(String img_width) {
                this.img_width = img_width;
            }

            public String getWares_detail_img_id() {
                return wares_detail_img_id;
            }

            public void setWares_detail_img_id(String wares_detail_img_id) {
                this.wares_detail_img_id = wares_detail_img_id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getImg_height() {
                return img_height;
            }

            public void setImg_height(String img_height) {
                this.img_height = img_height;
            }
        }

        public static class PackageListBean implements Serializable {
            /**
             * lock_count : 0
             * index_show : 2
             * money_now_first :
             * product_count : 0
             * display_state : 2
             * money_old : 2000.00
             * index_photo_url : https://shop.hljsdkj.com/Frame/uploadFile/showImg?file_id=10121
             * red_packet : 0.10
             * shop_product_order : 1
             * wares_id : 180
             * first_level_distribution : 0.10
             * update_time : 2020-01-09
             * contribution : 100
             * make_show :
             * state_name : 下架
             * subsystem_id : jcz
             * index_photo_id : 10121
             * shop_product_id : 92
             * inst_id : 187
             * state_id : 2
             * two_level_distribution : 0.10
             * product_state : 2
             * pay_count : 0
             * create_time : 2020-01-09
             * shop_product_count : 0
             * pro_show : 2
             * product_no : 1
             * product_title : 套餐一
             * red_set_type :
             * money_now : 1500.00
             * money_make :
             */

            private String lock_count;
            private String index_show;
            private String money_now_first;
            private String product_count;
            private String display_state;
            private String money_old;
            private String index_photo_url;
            private String red_packet;
            private String shop_product_order;
            private String wares_id;
            private String first_level_distribution;
            private String update_time;
            private String contribution;
            private String make_show;
            private String state_name;
            private String subsystem_id;
            private String index_photo_id;
            private String shop_product_id;
            private String inst_id;
            private String state_id;
            private String two_level_distribution;
            private String product_state;
            private String pay_count;
            private String create_time;
            private String shop_product_count;
            private String pro_show;
            private String product_no;
            private String product_title;
            private String red_set_type;
            private String money_now;
            private String money_make;

            public String getLock_count() {
                return lock_count;
            }

            public void setLock_count(String lock_count) {
                this.lock_count = lock_count;
            }

            public String getIndex_show() {
                return index_show;
            }

            public void setIndex_show(String index_show) {
                this.index_show = index_show;
            }

            public String getMoney_now_first() {
                return money_now_first;
            }

            public void setMoney_now_first(String money_now_first) {
                this.money_now_first = money_now_first;
            }

            public String getProduct_count() {
                return product_count;
            }

            public void setProduct_count(String product_count) {
                this.product_count = product_count;
            }

            public String getDisplay_state() {
                return display_state;
            }

            public void setDisplay_state(String display_state) {
                this.display_state = display_state;
            }

            public String getMoney_old() {
                return money_old;
            }

            public void setMoney_old(String money_old) {
                this.money_old = money_old;
            }

            public String getIndex_photo_url() {
                return index_photo_url;
            }

            public void setIndex_photo_url(String index_photo_url) {
                this.index_photo_url = index_photo_url;
            }

            public String getRed_packet() {
                return red_packet;
            }

            public void setRed_packet(String red_packet) {
                this.red_packet = red_packet;
            }

            public String getShop_product_order() {
                return shop_product_order;
            }

            public void setShop_product_order(String shop_product_order) {
                this.shop_product_order = shop_product_order;
            }

            public String getWares_id() {
                return wares_id;
            }

            public void setWares_id(String wares_id) {
                this.wares_id = wares_id;
            }

            public String getFirst_level_distribution() {
                return first_level_distribution;
            }

            public void setFirst_level_distribution(String first_level_distribution) {
                this.first_level_distribution = first_level_distribution;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getContribution() {
                return contribution;
            }

            public void setContribution(String contribution) {
                this.contribution = contribution;
            }

            public String getMake_show() {
                return make_show;
            }

            public void setMake_show(String make_show) {
                this.make_show = make_show;
            }

            public String getState_name() {
                return state_name;
            }

            public void setState_name(String state_name) {
                this.state_name = state_name;
            }

            public String getSubsystem_id() {
                return subsystem_id;
            }

            public void setSubsystem_id(String subsystem_id) {
                this.subsystem_id = subsystem_id;
            }

            public String getIndex_photo_id() {
                return index_photo_id;
            }

            public void setIndex_photo_id(String index_photo_id) {
                this.index_photo_id = index_photo_id;
            }

            public String getShop_product_id() {
                return shop_product_id;
            }

            public void setShop_product_id(String shop_product_id) {
                this.shop_product_id = shop_product_id;
            }

            public String getInst_id() {
                return inst_id;
            }

            public void setInst_id(String inst_id) {
                this.inst_id = inst_id;
            }

            public String getState_id() {
                return state_id;
            }

            public void setState_id(String state_id) {
                this.state_id = state_id;
            }

            public String getTwo_level_distribution() {
                return two_level_distribution;
            }

            public void setTwo_level_distribution(String two_level_distribution) {
                this.two_level_distribution = two_level_distribution;
            }

            public String getProduct_state() {
                return product_state;
            }

            public void setProduct_state(String product_state) {
                this.product_state = product_state;
            }

            public String getPay_count() {
                return pay_count;
            }

            public void setPay_count(String pay_count) {
                this.pay_count = pay_count;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getShop_product_count() {
                return shop_product_count;
            }

            public void setShop_product_count(String shop_product_count) {
                this.shop_product_count = shop_product_count;
            }

            public String getPro_show() {
                return pro_show;
            }

            public void setPro_show(String pro_show) {
                this.pro_show = pro_show;
            }

            public String getProduct_no() {
                return product_no;
            }

            public void setProduct_no(String product_no) {
                this.product_no = product_no;
            }

            public String getProduct_title() {
                return product_title;
            }

            public void setProduct_title(String product_title) {
                this.product_title = product_title;
            }

            public String getRed_set_type() {
                return red_set_type;
            }

            public void setRed_set_type(String red_set_type) {
                this.red_set_type = red_set_type;
            }

            public String getMoney_now() {
                return money_now;
            }

            public void setMoney_now(String money_now) {
                this.money_now = money_now;
            }

            public String getMoney_make() {
                return money_make;
            }

            public void setMoney_make(String money_make) {
                this.money_make = money_make;
            }
        }
    }
}
