package com.shendeng_bianmin.agent.util;

import com.bumptech.glide.request.RequestOptions;
import com.shendeng_bianmin.agent.R;


public class GlideShowImageUtils {

    public static RequestOptions showBannerCelve() {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.preview_banner_375)
                .error(R.mipmap.preview_banner_375);
        return requestOptions;
    }

    public static RequestOptions showZhengFangXing() {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.preview_shop_ffffff)
                .error(R.mipmap.preview_shop_ffffff);
        return requestOptions;
    }


}
