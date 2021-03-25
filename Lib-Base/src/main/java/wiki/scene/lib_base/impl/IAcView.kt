package wiki.scene.lib_base.impl;

/**
 * Created by zlx on 2020/9/22 13:58
 * Email: 1170762202@qq.com
 * Description:
 */
public interface IAcView {
    void initToolBarView();

    void initViews();

    void initEvents();

    void beforeOnCreate();

    void afterOnCreate();

    void beforeInitView();

    void loadData();

    void afterInitViews();
}
