package wiki.scene.demo.activity

import android.graphics.Color
import android.util.TypedValue
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.fondesa.recyclerviewdivider.BaseDividerItemDecoration
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.hjq.bar.TitleBar
import com.scwang.smart.refresh.layout.api.RefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import wiki.scene.demo.adapter.RecyclerViewAdapter
import wiki.scene.demo.databinding.ActRecyclerViewDemoBinding
import wiki.scene.entity.ArticleListRes
import wiki.scene.lib_base.base_ac.BaseRecyclerViewAc
import wiki.scene.lib_common.router.RouterPath
import wiki.scene.lib_common.router.RouterUtil
import wiki.scene.lib_network.exception.NetException
import wiki.scene.lib_network.ext.bindLifecycle
import wiki.scene.lib_network.ext.transformData
import wiki.scene.lib_network.manager.ApiManager
import wiki.scene.lib_network.observer.BaseObserver
import javax.inject.Inject

@AndroidEntryPoint
@Route(path = RouterPath.Main.ACT_RECYCLERVIEW)
class RecyclerViewDemoActivity :
    BaseRecyclerViewAc<ActRecyclerViewDemoBinding, wiki.scene.entity.ArticleBean>() {
    @Inject
    lateinit var mAdapter: RecyclerViewAdapter

    override fun initToolBarView(titleBarView: TitleBar) {
        super.initToolBarView(titleBarView)
        titleBarView.title = ("首页")
    }

    override fun injectRecyclerView(): RecyclerView {
        return binding.recyclerView
    }

    override fun injectLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(mContext)
    }

    override fun injectDivider(): BaseDividerItemDecoration {
        return dividerBuilder().size(10, TypedValue.COMPLEX_UNIT_DIP)
            .color(Color.RED)
            .build()
    }

    override fun injectRequestFirstPage(): Int {
        return 0
    }

    override fun injectReturnFirstPage(): Int {
        return 1
    }

    override fun injectAdapter(): RecyclerViewAdapter {
        mAdapter.setOnItemClickListener { _, _, position ->

            val url = mAdapter.data[position].link
            RouterUtil.launchWeb(url)
        }
        return mAdapter
    }

    override fun keyboardEnable(): Boolean {
        return true
    }

    override fun injectRefreshLayout(): RefreshLayout {
        return binding.refreshLayout
    }

    override fun getListData(isFirst: Boolean, loadPage: Int) {

        ApiManager.getInstance()
            .articleApi()
            .listArticle(loadPage)
            .bindLifecycle(getLifecycleTransformer())
            .transformData()
            .subscribe(object : BaseObserver<ArticleListRes>() {
                override fun onStart() {
                    super.onStart()
                    loadListDataStart(isFirst)
                }

                override fun onSuccess(data: ArticleListRes) {
                    loadListDataSuccess(
                        isFirst,
                        data.curPage,
                        data.pageCount,
                        data.datas
                    )
                }

                override fun onFail(e: NetException.ResponseException) {
                    loadListDataFail(isFirst, loadPage)
                }
            })
    }

}