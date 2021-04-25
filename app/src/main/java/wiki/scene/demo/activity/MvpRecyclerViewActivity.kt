package wiki.scene.demo.activity

import android.graphics.Color
import android.util.TypedValue
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.aries.ui.view.title.TitleBarView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.scwang.smart.refresh.layout.api.RefreshLayout
import org.koin.android.ext.android.inject
import wiki.scene.demo.adapter.RecyclerViewAdapter
import wiki.scene.demo.databinding.ActMvpRecyclerviewBinding
import wiki.scene.demo.mvp.contract.MvpRecyclerViewActContract
import wiki.scene.demo.mvp.presenter.MvpRecyclerViewActPresenter
import wiki.scene.lib_base.adapters.BaseBindingQuickAdapter
import wiki.scene.lib_base.base_mvp.BaseMvpRecyclerViewAc
import wiki.scene.lib_base.base_util.RouterUtil
import wiki.scene.lib_base.constant.RouterPath

@Route(path = RouterPath.Main.ACT_MVP_RECYCLERVIEW)
class MvpRecyclerViewActivity :
    BaseMvpRecyclerViewAc<ActMvpRecyclerviewBinding, wiki.scene.entity.ArticleBean, MvpRecyclerViewActPresenter>(),
    MvpRecyclerViewActContract.IView {

    private val mAdapter: RecyclerViewAdapter by inject()

    override fun injectReturnFirstPage(): Int {
        return 1
    }

    override fun initPresenter() {
        mPresenter = MvpRecyclerViewActPresenter(this)
    }

    override fun initToolBarView(titleBarView: TitleBarView) {
        super.initToolBarView(titleBarView)
        titleBarView.setTitleMainText("MVP RecyclerView")
    }

    override fun initRecyclerView() {
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(mContext)
        mContext.dividerBuilder().size(10, TypedValue.COMPLEX_UNIT_DIP)
            .color(Color.RED)
            .build()
            .addTo(binding.recyclerView)

        mAdapter.setOnItemClickListener { _, _, position ->
            val url = mAdapter.data[position].link
            RouterUtil.launchWeb(url)
        }
    }

    override fun injectAdapter(): BaseQuickAdapter<wiki.scene.entity.ArticleBean, BaseBindingQuickAdapter.BaseBindingHolder> {
        return mAdapter
    }

    override fun injectRefreshLayout(): RefreshLayout {
        return binding.refreshLayout
    }

    override fun getListData(isFirst: Boolean, loadPage: Int) {
        mPresenter?.getArticleList(isFirst, loadPage)
    }
}