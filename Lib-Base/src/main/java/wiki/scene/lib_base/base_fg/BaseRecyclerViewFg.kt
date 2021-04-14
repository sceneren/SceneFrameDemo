package wiki.scene.lib_base.base_fg

import android.view.View
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import wiki.scene.lib_base.R
import wiki.scene.lib_base.adapters.BaseBindingQuickAdapter
import wiki.scene.lib_base.base_mvp.i.IRecyclerViewBaseView

/**
 * 普通分页列表封装
 */
abstract class BaseRecyclerViewFg<VB : ViewBinding, T> : BaseFg<VB>(), OnRefreshListener,
    OnLoadMoreListener, IRecyclerViewBaseView<T> {
    open var currentPageNo = 0
    private var hasMore = false

    /**
     * 返回的第一页的page
     */
    open fun injectReturnFirstPage(): Int {
        return 0
    }

    /**
     * 请求第一页的page
     */
    open fun injectRequestFirstPage(): Int {
        return 0
    }

    abstract fun injectAdapter(): BaseQuickAdapter<T, BaseBindingQuickAdapter.BaseBindingHolder>

    abstract fun injectRefreshLayout(): RefreshLayout

    abstract fun getListData(isFirst: Boolean, loadPage: Int)

    override fun initViews() {
        super.initViews()
        initRecyclerView()
        injectRefreshLayout().setEnableLoadMore(false)
        injectAdapter().setEmptyView(R.layout.lib_base_layout_empty)

        if (isAllowLoadMore()) {
            injectAdapter().loadMoreModule.setOnLoadMoreListener(this)
        }

        if (isAllowRefresh()) {
            injectRefreshLayout().setOnRefreshListener(this)
        }
    }

    override fun loadData() {
        getListData(true, injectRequestFirstPage())
    }

    override fun onRetryBtnClick() {
        super.onRetryBtnClick()
        getListData(true, injectRequestFirstPage())
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        getListData(false, injectRequestFirstPage())
    }

    override fun onLoadMore() {
        getListData(false, currentPageNo)
    }

    open fun isAllowRefresh(): Boolean {
        return true
    }

    open fun isAllowLoadMore(): Boolean {
        return true
    }

    abstract fun initRecyclerView()

    override fun loadListDataStart(isFirst: Boolean) {
        if (isFirst) {
            showLoading()
        }
    }

    /**
     * @param isFirst 是否是第一次加载
     * @param currentPage 服务器返回的当前页数
     * @param totalPage 总页数
     * @param list 数据
     */

    override fun loadListDataSuccess(
        isFirst: Boolean,
        currentPage: Int,
        totalPage: Int,
        list: MutableList<T>
    ) {
        //给标记的页数赋值
        currentPageNo = currentPage
        hasMore = currentPage < totalPage
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
        if (currentPageNo == injectReturnFirstPage()) {
            injectAdapter().setNewInstance(list)
        } else {
            injectAdapter().addData(list)
        }
    }

    override fun loadListDataFail(isFirst: Boolean, loadPage: Int) {
        if (isFirst) {
            showError()
        } else {
            if (loadPage == injectRequestFirstPage()) {
                injectRefreshLayout().finishRefresh(false)
            } else {
                injectAdapter().loadMoreModule.loadMoreFail()
            }
        }
    }

    override fun injectLoadServiceView(): View? {
        return injectRefreshLayout() as View?
    }

}