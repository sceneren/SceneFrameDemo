package wiki.scene.lib_base.base_ac

import android.view.View
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import wiki.scene.lib_base.adapters.BaseBindingQuickAdapter

/**
 * 普通分页列表封装
 */
abstract class BaseRecyclerViewAc<VB : ViewBinding, T> : BaseAc<VB>(), OnRefreshListener,
    OnLoadMoreListener {
    open var currentPageNo = 0
    private var hasMore = false

    /**
     * 返回的第一页的page
     */
    open fun injectReturnPageStart(): Int {
        return 0
    }

    /**
     * 请求第一页的page
     */
    open fun injectLoadPageStart(): Int {
        return 0
    }

    abstract fun injectAdapter(): BaseQuickAdapter<T, BaseBindingQuickAdapter.BaseBindingHolder>

    abstract fun injectRefreshLayout(): RefreshLayout

    abstract fun getListData(isFirst: Boolean, loadPage: Int)

    override fun initViews() {
        super.initViews()
        initRecyclerView()
        injectRefreshLayout().setEnableAutoLoadMore(false)

        if (isAllowLoadMore()) {
            injectAdapter().loadMoreModule.setOnLoadMoreListener(this)
        }

        if (isAllowRefresh()) {
            injectRefreshLayout().setOnRefreshListener(this)
        }
    }

    override fun loadData() {
        super.loadData()
        getListData(true, injectLoadPageStart())
    }

    override fun onRetryBtnClick() {
        super.onRetryBtnClick()
        getListData(true, injectLoadPageStart())
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        getListData(false, injectLoadPageStart())
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

    open fun initRecyclerView() {

    }

    fun loadListDataStart(isFirst: Boolean, view: View) {
        if (isFirst) {
            showLoading(view)
        }
    }

    /**
     * @param isFirst 是否是第一次加载
     * @param page 服务器返回的当前页数
     * @param pageTotal 总页数
     * @param data 数据
     */

    fun loadListDataSuccess(isFirst: Boolean, page: Int, pageTotal: Int, data: MutableList<T>) {
        //给标记的页数赋值
        currentPageNo = page
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
        if (currentPageNo == injectReturnPageStart()) {
            injectAdapter().setNewInstance(data)
        } else {
            injectAdapter().addData(data)
        }
    }

    fun loadListDataFail(isFirst: Boolean, page: Int) {
        if (isFirst) {
            showError()
        } else {
            if (page == injectLoadPageStart()) {
                injectRefreshLayout().finishRefresh(false)
            } else {
                injectAdapter().loadMoreModule.loadMoreFail()
            }
        }
    }

}