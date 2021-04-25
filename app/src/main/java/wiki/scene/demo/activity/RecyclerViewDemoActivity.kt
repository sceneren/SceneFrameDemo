package wiki.scene.demo.activity

import android.graphics.Color
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.aries.ui.view.title.TitleBarView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.scwang.smart.refresh.layout.api.RefreshLayout
import org.koin.android.ext.android.inject
import wiki.scene.demo.adapter.RecyclerViewAdapter
import wiki.scene.demo.databinding.ActRecyclerViewDemoBinding
import wiki.scene.entity.ArticleListRes
import wiki.scene.entity.base.BaseResponse
import wiki.scene.lib_base.adapters.BaseBindingQuickAdapter
import wiki.scene.lib_base.base_ac.BaseRecyclerViewAc
import wiki.scene.lib_base.base_util.RouterUtil
import wiki.scene.lib_base.constant.RouterPath
import wiki.scene.lib_base.ext.bindLifecycle
import wiki.scene.lib_base.ext.changeIO2MainThread
import wiki.scene.lib_network.exception.NetException
import wiki.scene.lib_network.manager.ApiManager
import wiki.scene.lib_network.observer.BaseObserver

@Route(path = RouterPath.Main.ACT_RECYCLERVIEW)
class RecyclerViewDemoActivity :
    BaseRecyclerViewAc<ActRecyclerViewDemoBinding, wiki.scene.entity.ArticleBean>() {
    private val mAdapter: RecyclerViewAdapter by inject()

    override fun initToolBarView(titleBarView: TitleBarView) {
        super.initToolBarView(titleBarView)
        titleBarView.setTitleMainText("首页")
    }

    override fun initRecyclerView() {
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(mContext)
        dividerBuilder().size(10, TypedValue.COMPLEX_UNIT_DIP)
            .color(Color.RED)
            .build()
            .addTo(binding.recyclerView)

        mAdapter.setOnItemClickListener { _, _, position ->

            val url = mAdapter.data[position].link
            RouterUtil.launchWeb(url)
        }

    }

    override fun injectRequestFirstPage(): Int {
        return 0
    }

    override fun injectReturnFirstPage(): Int {
        return 1
    }

    override fun injectAdapter(): BaseQuickAdapter<wiki.scene.entity.ArticleBean, BaseBindingQuickAdapter.BaseBindingHolder> {
        return mAdapter
    }

    override fun injectRefreshLayout(): RefreshLayout {
        return binding.refreshLayout
    }

    override fun getListData(isFirst: Boolean, loadPage: Int) {
        ApiManager.getInstance()
            .articleApi()
            .listArticle(loadPage)
            .changeIO2MainThread()
            .bindLifecycle(getLifecycleTransformer())
            .subscribe(object : BaseObserver<BaseResponse<ArticleListRes>>() {
                override fun onStart() {
                    super.onStart()
                    loadListDataStart(isFirst)
                }

                override fun onSuccess(t: BaseResponse<ArticleListRes>) {
                    loadListDataSuccess(
                        isFirst,
                        t.data!!.curPage,
                        t.data!!.pageCount,
                        t.data!!.datas
                    )
                }

                override fun onFail(e: NetException.ResponseException) {
                    loadListDataFail(isFirst, loadPage)
                }
            })
    }

    override fun injectLoadServiceView(): View {
        return binding.refreshLayout
    }

}