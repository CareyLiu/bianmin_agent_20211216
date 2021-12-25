package com.shendeng_bianmin.agent.app;

/**
 * Created by Administrator on 2016/11/18 0018.
 */
public interface ConstanceValue {
    int MSG_RONGYUN_STATE = 0x10001;//融云连接状态


    int WX_SET_S = 0x10002;//微信绑定成功
    int WX_SET_F = 0x10003;//微信绑定失败
    int WX_JIECHU_S = 0x10004;//微信绑定解除成功
    int WX_JIECHU_F = 0x10005;//微信绑定解除失败


    int shangpin_details_use = 0x10006;//商家端 详情
    int shangpin_edit_use = 0x10007;//商家端 编辑
    int shangpin_frag = 0x10008;//商家端 图片

    int order_fahuo = 0x10009;//订单发货
    int ADD_YUANGONG = 0x10010;//团购  添加员工

    int MSG_RONGYUN_REVICE = 0x10011;//接收聊天消息
    int MSG_TIANJIALEIMU = 0x10012;//接收商品类目
    int MSG_LEIMU_ID = 0x10013;//图片
}
