package wiki.scene.lib_base.base_ac

import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smart.refresh.layout.api.RefreshLayout
import wiki.scene.lib_base.adapters.BaseBindingQuickAdapter

/**
 * 普通分页列表封装
 */
abstract class BaseRecyclerViewAc<VB : ViewBinding, T> :
    BaseAc<VB>() {
    open var pageNo = 0
    private var hasMore = false

    open fun injectPageStart(): Int {
        return 1
    }

    abstract fun injectAdapter(): BaseQuickAdapter<T, BaseBindingQuickAdapter.BaseBindingHolder>

    abstract fun injectRefreshLayout(): RefreshLayout

    override fun afterInitViews() {
        super.afterInitViews()
        injectRefreshLayout().setEnableAutoLoadMore(false)
    }

    /**
     * @param isFirst 是否是第一次加载
     * @param page 服务器返回的当前页数
     * @param pageTotal 总页数
     * @param data 数据
     */

    fun loadListDataSuccess(isFirst: Boolean, page: Int, pageTotal: Int, data: MutableList<T>) {
        //给标记的页数赋值
        pageNo = page
        hasMore = page < pageTotal
        if (isFirst) {
            showSuccess()
        } else {
            injectRefreshLayout().finishRefresh(true)
        }
        if (hasMore) {
            injectAdapter().loadMoreModule.loadMoreComplete()
        } else {
            injectAdapter().loadMoreModule.loadMoreEnd()
        }
        if (pageNo == injectPageStart()) {
            injectAdapter().setNewInstance(data)
        } else {
            injectAdapter().addData(data)
        }
    }

    fun loadListDataFail(isFirst: Boolean, page: Int) {
        if (isFirst) {
            showError()
        } else {
            if (page == injectPageStart()) {
                injectRefreshLayout().finishRefresh(false)
            } else {
                injectAdapter().loadMoreModule.loadMoreFail()
            }
        }
    }
}