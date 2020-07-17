package com.shendeng.agent.util;

public class Urls {
    public static String SERVER_URL = "https://shop.hljsdkj.com/";//基本地址
//    public static String SERVER_URL = "https://test.hljsdkj.com/";//基本地址

    public static String APP = SERVER_URL + "shop_new/app/";//APP端
    public static String WORKER = APP + "worker/";//卖家端

    public static String KEY = "20180305124455yu";//全局请求key

    public static String LOGIN = WORKER + "login";//卖家端登录接口
    public static String MSG = SERVER_URL + "msg";//发送短信验证接口


    public final static String code_00001 = "00001";//发送验证码
    public final static String code_04310 = "04310";//登录接口
    public final static String code_04336 = "04336";//修改密码卖家端
    public final static String code_04311 = "04311";//订单列表（普通）
    public final static String code_04312 = "04312";//退款订单详情
    public final static String code_04313 = "04313";//订单详情
    public final static String code_04314 = "04314";//卖家端：修改快递费
    public final static String code_04315 = "04315";//退款审核
    public final static String code_04316 = "04316";//发货（填写快递单）
    public final static String code_04317 = "04317";//评价买家
    public final static String code_04318 = "04318";//到店消费验证（扫一扫）
    public final static String code_04319 = "04319";//退货确认收货
    public final static String code_04320 = "04320";//订单评价详情
    public final static String code_04333 = "04333";//收支明細
    public final static String code_04189 = "04189";//收支明細詳情
}
