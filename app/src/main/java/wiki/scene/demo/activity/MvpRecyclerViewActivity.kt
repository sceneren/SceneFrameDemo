package wiki.scene.demo.activity

import android.graphics.Color
import android.util.TypedValue
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.fondesa.recyclerviewdivider.BaseDividerItemDecoration
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.hjq.bar.TitleBar
import com.scwang.smart.refresh.layout.api.RefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import wiki.scene.demo.adapter.RecyclerViewAdapter
import wiki.scene.demo.databinding.ActMvpRecyclerviewBinding
import wiki.scene.demo.mvp.contract.MvpRecyclerViewActContract
import wiki.scene.demo.mvp.presenter.MvpRecyclerViewActPresenter
import wiki.scene.lib_base.adapters.BaseBindingQuickAdapter
import wiki.scene.lib_base.base_mvp.BaseMvpRecyclerViewAc
import wiki.scene.lib_common.router.RouterPath
import wiki.scene.lib_common.router.RouterUtil
import javax.inject.Inject

@AndroidEntryPoint
@Route(path = RouterPath.Main.ACT_MVP_RECYCLERVIEW)
class MvpRecyclerViewActivity :
    BaseMvpRecyclerViewAc<ActMvpRecyclerviewBinding, wiki.scene.entity.ArticleBean, MvpRecyclerViewActPresenter>(),
    MvpRecyclerViewActContract.IView {

    @Inject
    lateinit var mAdapter: RecyclerViewAdapter

    override fun injectReturnFirstPage(): Int {
        return 1
    }

    override fun initPresenter() {
        mPresenter = MvpRecyclerViewActPresenter(this)
    }

    override fun initToolBarView(titleBarView: TitleBar) {
        super.initToolBarView(titleBarView)
        titleBarView.title = ("MVP RecyclerView")
    }

    override fun injectRecyclerView(): RecyclerView {
        return binding.recyclerView
    }

    override fun injectLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(mContext)
    }

    override fun injectDivider(): BaseDividerItemDecoration {
        return mContext.dividerBuilder().size(10, TypedValue.COMPLEX_UNIT_DIP)
            .color(Color.RED)
            .build()
    }
    
    override fun injectAdapter(): BaseQuickAdapter<wiki.scene.entity.ArticleBean, BaseBindingQuickAdapter.BaseBindingHolder> {
        mAdapter.setOnItemClickListener { _, _, position ->
            val url = mAdapter.data[position].link
            RouterUtil.launchWeb(url)
        }
        return mAdapter
    }

    override fun injectRefreshLayout(): RefreshLayout {
        return binding.refreshLayout
    }

    override fun getListData(isFirst: Boolean, loadPage: Int) {
        mPresenter?.getArticleList(isFirst, loadPage)
    }
}