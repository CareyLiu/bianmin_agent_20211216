package com.shendeng_bianmin.agent.app;

import android.Manifest;

public class AppConfig {
    public static String WELCOME_PAGE = "0x1111";//是否首次进入app
    public static String IS_FIRST_RUN = "0x1112";//是否首次进入app 十分重要 true 是首次 false 不是首次
    public static String RONGYUN_TOKEN = "0x1113";//融云token

    public static String ROLE_NUMBER = "0x1114";//当前角色的数量
    public static String ROLE = "0x1115";//当前选择角色

    /**
     * 基本权限管理
     */
    public static final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
    };
    /**
     * 基本定位权限管理
     */
    public static final String[] BASIC_PERMISSIONS_LOCATION = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    /**
     * 基本存储权限管理
     */
    public static final String[] BASIC_PERMISSIONS_STORAGE = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
}
