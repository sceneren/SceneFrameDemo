package wiki.scene.demo.activity

import android.util.TypedValue
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.fondesa.recyclerviewdivider.BaseDividerItemDecoration
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.hjq.bar.TitleBar
import com.scwang.smart.refresh.layout.api.RefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import wiki.scene.demo.adapter.StickyAdapter
import wiki.scene.demo.databinding.ActStickyRecyclerviewBinding
import wiki.scene.entity.base.StickyBean
import wiki.scene.lib_base.base_ac.BaseRecyclerViewAc
import wiki.scene.lib_common.router.RouterPath
import javax.inject.Inject

@AndroidEntryPoint
@Route(path = RouterPath.Main.ACT_RECYCLERVIEW_STICKY_HEADER)
class StickyRecyclerViewActivity :
    BaseRecyclerViewAc<ActStickyRecyclerviewBinding, StickyBean>() {

    @Inject
    lateinit var mAdapter: StickyAdapter

    override fun initToolBarView(titleBarView: TitleBar) {
        super.initToolBarView(titleBarView)
        titleBarView.title = "Sticky RecyclerView"
    }

    override fun injectRecyclerView(): RecyclerView {
        return binding.recyclerView
    }

    override fun injectLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(mContext)
    }

//    override fun injectDivider(): BaseDividerItemDecoration {
//        return dividerBuilder().size(10, TypedValue.COMPLEX_UNIT_DIP)
//            .asSpace()
//            .build()
//    }

    override fun injectAdapter(): BaseQuickAdapter<StickyBean, out BaseViewHolder> {
        return mAdapter
    }

    override fun enableLoadMore(): Boolean {
        return false
    }

    override fun injectRefreshLayout(): RefreshLayout {
        return binding.refreshLayout
    }

    override fun initRecyclerView() {
        super.initRecyclerView()
//        binding.recyclerView.addStickyItemDecorationWithItemType(2)
    }

    override fun getListData(isFirst: Boolean, loadPage: Int) {
        loadListDataStart(isFirst)
        val list = mutableListOf<StickyBean>()
        for (i in 0 until 30) {
            val type = if (i % 9 == 0) {
                1
            } else {
                2
            }
            val bean = StickyBean(3000L + i * 1000, "第${i}条", type)
            list.add(bean)
        }
        loadListDataSuccess(true, list)
    }
}