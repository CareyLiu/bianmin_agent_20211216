package com.shendeng.agent.basicmvp;


public interface BasicView {
    /*******内嵌加载*******/
    void showLoading(String title);

    void stopLoading();

    void showErrorTip(String msg);

}
