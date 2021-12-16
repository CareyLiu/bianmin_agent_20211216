package com.shendeng_bianmin.agent.model;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.util.List;

public class WaresListBean extends SectionEntity {

    public String pay_count;
    public String user_accid;
    public String user_name;
    public String form_id;
    public String user_img_url;
    public String pay_money;
    public String shop_pay_check;
    public String shop_pay_check_name;
    public String express_url;
    public String wares_type;
    public String receiver_text;
    public String receiver_name;
    public String user_addr_all;
    public String receiver_phone;
    public String shop_form_id;

    public String shop_form_text;
    public String zuiHouYige = "0";
    /**
     * wares_id : 290
     * shop_product_title : 百威啤酒
     * pay_count : 1
     * index_photo_url : http://bz-goods.oss-cn-hangzhou.aliyuncs.com/20210505084823000001.jpg
     * form_product_money : 20.00
     */

    public String wares_id;
    public String shop_product_title;
    public String pay_count_jutishangpin;
    public String index_photo_url;
    public String form_product_money;

    public WaresListBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public String getWares_id() {
        return wares_id;
    }

    public void setWares_id(String wares_id) {
        this.wares_id = wares_id;
    }

    public String getShop_product_title() {
        return shop_product_title;
    }

    public void setShop_product_title(String shop_product_title) {
        this.shop_product_title = shop_product_title;
    }

    public String getPay_count() {
        return pay_count;
    }

    public void setPay_count(String pay_count) {
        this.pay_count = pay_count;
    }

    public String getIndex_photo_url() {
        return index_photo_url;
    }

    public void setIndex_photo_url(String index_photo_url) {
        this.index_photo_url = index_photo_url;
    }

    public String getForm_product_money() {
        return form_product_money;
    }

    public void setForm_product_money(String form_product_money) {
        this.form_product_money = form_product_money;
    }
}