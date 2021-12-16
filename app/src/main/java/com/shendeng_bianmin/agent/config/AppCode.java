package com.shendeng_bianmin.agent.config;

public class AppCode {
    public final static String SMS_ID = "SMS_ID";//smsId;//临时使用  短信验证码id
    public final static String SMS_CODE = "SMS_CODE";//临时使用     短信验证码
    public final static String WX_TYPE = "WX_TYPE";//临时使用     判断微信解绑 绑定  1绑定  2解绑

    public final static String weixinOrZhiFuBao = "weixinOrZhiFuBao";//weixinOrZhiFuBao 1支付宝  2微信 3微信解绑 4修改支付密码

    public final static String code_zhifubao = "1";//1支付宝
    public final static String code_weixin = "2";//2微信
    public final static String code_weixin_jie = "3";//微信解绑
    public final static String code_pwd_zhifu = "4";//修改支付密码
    public final static String code_pwd_login = "5";//修改密码

    public final static String msg_maijia = "1";//商家端消息
    public final static String msg_tuangou = "2";//团购端消息
    public final static String mingxi_tuangou = "3";//团购团购明细
    public final static String mingxi_maidan = "4";//团购买单明细

    public final static String mod_zhifu_admin = "0111";//修改 支付宝  微信
    public final static String mod_login_pwd = "0112";//修改登录密码
    public final static String mod_zhifu_pwd = "0113";//修改支付密码
    public final static String mod_login_pwd_zhaohui = "0001";//找回登录密码


    public static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x01;//相机权限
    public static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x02;//相册权限
    public static final int REQUEST_CROP_PHOTO = 0x03;//剪切

}
