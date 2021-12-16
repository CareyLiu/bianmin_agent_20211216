/**
 *
 */
package com.shendeng_bianmin.agent.config;

import android.content.Context;

import com.shendeng_bianmin.agent.app.PreferenceHelper;
import com.shendeng_bianmin.agent.model.LoginUser;
import com.shendeng_bianmin.agent.model.WodeModel;


/**
 * 登陆用户信息管理类
 */
public class UserManager {

    private static UserManager mUserManager;
    private static Context mContext;

    private UserManager(Context ctx) {
        mContext = ctx;
    }

    public static UserManager getManager(Context ctx) {
        if (mUserManager == null) {
            mUserManager = new UserManager(ctx);
        }
        return mUserManager;
    }


    public String getUserId() {
        return PreferenceHelper.getInstance(mContext).getString("of_user_id", "");
    }

    public String getAppToken() {
        return PreferenceHelper.getInstance(mContext).getString("app_token", "");
    }

    public String getUserName() {
        return PreferenceHelper.getInstance(mContext).getString("user_name", "");
    }

    public String getRongYun() {
        return PreferenceHelper.getInstance(mContext).getString("token_rong", "");
    }

    public String getAccid() {
        return PreferenceHelper.getInstance(mContext).getString("accid", "");
    }

    public String getEnter_type() {
        return PreferenceHelper.getInstance(mContext).getString("enter_type", "");
    }

    public String getUserImg() {
        return PreferenceHelper.getInstance(mContext).getString("user_img", "");
    }

    //保存用户信息
    public void saveUser(LoginUser.DataBean user) {
        if (user != null) {
            PreferenceHelper.getInstance(mContext).putString("of_user_id", user.getOf_user_id());
            PreferenceHelper.getInstance(mContext).putString("app_token", user.getApp_token());
            PreferenceHelper.getInstance(mContext).putString("user_name", user.getUser_name());
            PreferenceHelper.getInstance(mContext).putString("token_rong", user.getToken_rong());
            PreferenceHelper.getInstance(mContext).putString("accid", user.getAccid());
            PreferenceHelper.getInstance(mContext).putString("user_img", user.getUser_img());

        }
    }

    //保存用户信息
    public void saveUserInfoMine(WodeModel.DataBean user) {
        if (user != null) {
            PreferenceHelper.getInstance(mContext).putString("alipay_uname", user.getAlipay_uname());
            PreferenceHelper.getInstance(mContext).putString("alipay_number", user.getAlipay_number());
            PreferenceHelper.getInstance(mContext).putString("alipay_number_check", user.getAlipay_number_check());
            PreferenceHelper.getInstance(mContext).putString("user_phone", user.getUser_phone());
            PreferenceHelper.getInstance(mContext).putString("pay_pwd_check", user.getPay_pwd_check());
            PreferenceHelper.getInstance(mContext).putString("wx_pay_check", user.getWx_pay_check());
            PreferenceHelper.getInstance(mContext).putString("inst_owner", user.getInst_owner());
        }
    }

    /**
     * 删除用户信息
     */
    public void removeUser() {
        PreferenceHelper.getInstance(mContext).removeKey("of_user_id");
        PreferenceHelper.getInstance(mContext).removeKey("app_token");
        PreferenceHelper.getInstance(mContext).removeKey("user_name");
        PreferenceHelper.getInstance(mContext).removeKey("token_rong");
        PreferenceHelper.getInstance(mContext).removeKey("accid");
        PreferenceHelper.getInstance(mContext).removeKey("enter_type");
        PreferenceHelper.getInstance(mContext).removeKey("user_img");


        PreferenceHelper.getInstance(mContext).removeKey("alipay_uname");
        PreferenceHelper.getInstance(mContext).removeKey("alipay_number");
        PreferenceHelper.getInstance(mContext).removeKey("alipay_number_check");
        PreferenceHelper.getInstance(mContext).removeKey("user_phone");
        PreferenceHelper.getInstance(mContext).removeKey("pay_pwd_check");
        PreferenceHelper.getInstance(mContext).removeKey("wx_pay_check");
        PreferenceHelper.getInstance(mContext).removeKey("inst_owner");
    }

    public String getWx_user_name() {//获取微信名称
        return PreferenceHelper.getInstance(mContext).getString("wx_user_name", "");
    }

    public String getAlipay_uname() {//获取支付宝真实姓名
        return PreferenceHelper.getInstance(mContext).getString("alipay_uname", "");
    }

    public String getAlipay_number() {//获取支付宝真实姓名
        return PreferenceHelper.getInstance(mContext).getString("alipay_number", "");
    }

    public String getAlipay_number_check() {//判断是否绑定了支付宝 1绑定 0未绑定
        return PreferenceHelper.getInstance(mContext).getString("alipay_number_check", "");
    }

    public String getUser_phone() {//获取用户手机号
        return PreferenceHelper.getInstance(mContext).getString("user_phone", "");
    }

    public String getPay_pwd_check() {//判断是否绑定了支付密码 1绑定 0未绑定
        return PreferenceHelper.getInstance(mContext).getString("pay_pwd_check", "");
    }

    public String getWx_pay_check() {//判断是否绑定了微信 1绑定 0未绑定
        return PreferenceHelper.getInstance(mContext).getString("wx_pay_check", "");
    }

    public String getInst_owner() {//是否是店主  1是  2否
        return PreferenceHelper.getInstance(mContext).getString("inst_owner", "");
    }

    //保存用户信息
    public void saveUserInfo(WodeModel.DataBean user) {
        if (user != null) {
            PreferenceHelper.getInstance(mContext).putString("alipay_uname", user.getAlipay_uname());
            PreferenceHelper.getInstance(mContext).putString("alipay_number", user.getAlipay_number());
            PreferenceHelper.getInstance(mContext).putString("alipay_number_check", user.getAlipay_number_check());
            PreferenceHelper.getInstance(mContext).putString("user_phone", user.getUser_phone());
            PreferenceHelper.getInstance(mContext).putString("pay_pwd_check", user.getPay_pwd_check());
            PreferenceHelper.getInstance(mContext).putString("wx_pay_check", user.getWx_pay_check());
            PreferenceHelper.getInstance(mContext).putString("inst_owner", user.getInst_owner());
            PreferenceHelper.getInstance(mContext).putString("wx_user_name", user.getWx_user_name());
        }
    }

    //设置是否绑定微信
    public void setWx_pay_check(String wx_pay_check) {
        PreferenceHelper.getInstance(mContext).putString("wx_pay_check", wx_pay_check);
    }

    //设置微信名称
    public void setWx_user_name(String wx_user_name) {
        PreferenceHelper.getInstance(mContext).putString("wx_user_name", wx_user_name);
    }

    //设置是否绑定支付宝
    public void setAlipay_number_check(String alipay_number_check) {
        PreferenceHelper.getInstance(mContext).putString("alipay_number_check", alipay_number_check);
    }

    //设置支付宝账户
    public void setAlipay_number(String alipay_number) {
        PreferenceHelper.getInstance(mContext).putString("alipay_number", alipay_number);
    }

    //设置支付宝真实姓名
    public void setAlipay_name(String alipay_uname) {
        PreferenceHelper.getInstance(mContext).putString("alipay_uname", alipay_uname);
    }


    //设置是否绑定支付密码
    public void setPay_pwd_check(String pay_pwd_check) {
        PreferenceHelper.getInstance(mContext).putString("pay_pwd_check", pay_pwd_check);
    }
}