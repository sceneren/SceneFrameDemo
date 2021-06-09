package wiki.scene.demo.fragment

import android.graphics.Color
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.hjq.bar.TitleBar
import com.scwang.smart.refresh.layout.api.RefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import wiki.scene.demo.R
import wiki.scene.demo.adapter.RecyclerViewAdapter
import wiki.scene.demo.databinding.FragTab3Binding
import wiki.scene.demo.mvp.contract.Tab3Contract
import wiki.scene.demo.mvp.presenter.Tab3Presenter
import wiki.scene.entity.ArticleBean
import wiki.scene.lib_base.adapters.BaseBindingQuickAdapter
import wiki.scene.lib_base.base_mvp.BaseMvpRecyclerViewFg
import wiki.scene.lib_base.priview.ImagePreviewUtils
import wiki.scene.lib_common.router.RouterPath
import wiki.scene.lib_common.router.RouterUtil
import javax.inject.Inject

@AndroidEntryPoint
@Route(path = RouterPath.Main.FRAG_TAB_3)
class Tab3Fragment :
    BaseMvpRecyclerViewFg<FragTab3Binding, ArticleBean, Tab3Presenter>(), Tab3Contract.IView {

    @Inject
    lateinit var mAdapter: RecyclerViewAdapter

    override fun injectReturnFirstPage(): Int {
        return 1
    }

    override fun hasTitleBarView(): Boolean {
        return true
    }

    override fun initToolBarView(titleBarView: TitleBar) {
        super.initToolBarView(titleBarView)
        titleBarView.title = ("MVP RecyclerView Fragment")
    }

    override fun initPresenter() {
        mPresenter = Tab3Presenter(this)
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

        mAdapter.setOnItemChildClickListener { _, view, _ ->
            when (view.id) {
                R.id.ivImage -> {
                    ImagePreviewUtils.preview(
                        mActivity,
                        mutableListOf("https://pic.rmb.bdstatic.com/bjh/down/2331bb86e656e5a574d211113d154325.gif")
                    )
                }
            }
        }
    }

    override fun injectAdapter(): BaseQuickAdapter<ArticleBean, BaseBindingQuickAdapter.BaseBindingHolder> {
        return mAdapter
    }

    override fun injectRefreshLayout(): RefreshLayout {
        return binding.refreshLayout
    }

    override fun getListData(isFirst: Boolean, loadPage: Int) {
        mPresenter?.getArticleList(isFirst, loadPage)
    }

    override fun injectLoadServiceView(): View {
        return binding.refreshLayout
    }
}