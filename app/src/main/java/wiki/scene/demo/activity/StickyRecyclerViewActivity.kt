package wiki.scene.demo.activity

import android.util.TypedValue
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.fondesa.recyclerviewdivider.BaseDividerItemDecoration
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.github.panpf.recycler.sticky.addStickyItemDecorationWithItemType
import com.hjq.bar.TitleBar
import com.scwang.smart.refresh.layout.api.RefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import wiki.scene.demo.adapter.StickyAdapter
import wiki.scene.demo.databinding.ActStickyRecyclerviewBinding
import wiki.scene.entity.base.StickyBean
import wiki.scene.lib_base.base_ac.BaseRecyclerViewAc
import wiki.scene.lib_common.router.RouterPath
import wiki.scene.lib_network.exception.NetException
import wiki.scene.lib_network.ext.bindLifecycle
import wiki.scene.lib_network.ext.changeIO2MainThread
import wiki.scene.lib_network.observer.BaseObserver
import javax.inject.Inject

/**
 *
 * @Description:    《慎用》列表分组吸顶，目前问题是如果需要响应吸顶的点击事件的话会导致下拉刷新功能被挡住
 * @Author:         scene
 * @CreateDate:     2021/11/4 14:54
 * @UpdateUser:
 * @UpdateDate:     2021/11/4 14:54
 * @UpdateRemark:
 * @Version:        1.0.0
 */
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

    override fun injectDivider(): BaseDividerItemDecoration {
        return dividerBuilder().size(10, TypedValue.COMPLEX_UNIT_DIP)
            .asSpace()
            .build()
    }

    override fun injectAdapter(): BaseQuickAdapter<StickyBean, out BaseViewHolder> {
        mAdapter.setOnItemClickListener { _, _, position ->
            LogUtils.e(mAdapter.data[position].title)
        }
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
        binding.recyclerView.addStickyItemDecorationWithItemType(2) {
            //需要响应吸顶的才加这个东西
            showInContainer(binding.layoutStickyView)
        }
    }

    override fun getListData(isFirst: Boolean, loadPage: Int) {
        Observable.create<MutableList<StickyBean>> {
            val list = mutableListOf<StickyBean>()
            for (i in 1..30) {
                val type = if (i % 8 == 1) {
                    2
                } else {
                    1
                }
                val bean = StickyBean(System.currentTimeMillis(), "第${i}条", type)
                list.add(bean)
            }
            it.onNext(list)
        }.changeIO2MainThread()
            .bindLifecycle(getLifecycleTransformer())
            .subscribe(object : BaseObserver<MutableList<StickyBean>>() {
                override fun onStart() {
                    super.onStart()
                    loadListDataStart(isFirst)
                }

                override fun onSuccess(data: MutableList<StickyBean>) {
                    loadListDataSuccess(isFirst, data)
                }

                override fun onFail(e: NetException.ResponseException) {
                    loadListDataFail(isFirst)
                }

            })
    }
}