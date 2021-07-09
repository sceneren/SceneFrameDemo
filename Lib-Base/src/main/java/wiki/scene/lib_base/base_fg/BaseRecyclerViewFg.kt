package wiki.scene.lib_base.base_fg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.fondesa.recyclerviewdivider.BaseDividerItemDecoration
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import wiki.scene.lib_base.R
import wiki.scene.lib_base.adapters.BaseBindingQuickAdapter
import wiki.scene.lib_base.base_mvp.i.IRecyclerViewBaseView
import wiki.scene.lib_common.constant.Constant

/**
 * 普通分页列表封装
 */
abstract class BaseRecyclerViewFg<VB : ViewBinding, T> : BaseFg<VB>(), OnRefreshListener,
    OnLoadMoreListener, IRecyclerViewBaseView<T> {
    open var currentPageNo = Constant.API_PAGE_REQUEST_START
    private var hasMore = false

    /**
     * 返回的第一页的page
     */
    open fun injectReturnFirstPage(): Int {
        return Constant.API_PAGE_RESPONSE_START
    }

    /**
     * 请求第一页的page
     */
    open fun injectRequestFirstPage(): Int {
        return Constant.API_PAGE_REQUEST_START
    }

    abstract fun injectRecyclerView(): RecyclerView

    abstract fun injectLayoutManager(): RecyclerView.LayoutManager

    abstract fun injectDivider(): BaseDividerItemDecoration

    abstract fun injectAdapter(): BaseQuickAdapter<T, BaseBindingQuickAdapter.BaseBindingHolder>

    abstract fun injectRefreshLayout(): RefreshLayout

    override fun initViews() {
        super.initViews()
        injectRefreshLayout().setEnableLoadMore(false)
        if (enableRefresh()) {
            injectRefreshLayout().setOnRefreshListener(this)
        }

        initRecyclerView()
        injectRecyclerView().layoutManager = injectLayoutManager()
        injectDivider().addTo(injectRecyclerView())
        injectRecyclerView().adapter = injectAdapter()
        injectAdapter().setEmptyView(injectEmptyView())
        injectAdapter().headerWithEmptyEnable = injectHeaderWithEmptyEnable()
        injectAdapter().isUseEmpty = false

        if (injectHeaderView() != 0) {
            val headerView = LayoutInflater.from(mContext)
                .inflate(injectHeaderView(), injectRecyclerView().parent as ViewGroup, false)
            initHeaderView(headerView)
            injectAdapter().addHeaderView(headerView)
        }

        if (enableLoadMore()) {
            injectAdapter().loadMoreModule.setOnLoadMoreListener(this)
        }

    }

    open fun initRecyclerView() {

    }

    override fun loadData() {
        getListData(true, injectRequestFirstPage())
    }

    abstract fun getListData(isFirst: Boolean, loadPage: Int)

    override fun onRetryBtnClick() {
        super.onRetryBtnClick()
        getListData(true, injectRequestFirstPage())
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        getListData(false, injectRequestFirstPage())
    }

    override fun onLoadMore() {
        getListData(false, currentPageNo + 1)
    }

    open fun enableRefresh(): Boolean {
        return true
    }

    open fun enableLoadMore(): Boolean {
        return true
    }

    open fun injectEmptyView(): Int {
        return R.layout.lib_base_layout_empty
    }

    open fun injectHeaderWithEmptyEnable(): Boolean {
        return true
    }


    open fun injectHeaderView(): Int {
        return 0
    }

    open fun initHeaderView(headerView: View) {

    }


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
        injectAdapter().isUseEmpty = true
    }

    override fun loadListDataSuccess(isFirst: Boolean, list: MutableList<T>) {
        if (isFirst) {
            showSuccess()
        } else {
            injectRefreshLayout().finishRefresh(true)
        }
        injectAdapter().setNewInstance(list)
        injectAdapter().isUseEmpty = true
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

    override fun loadListDataFail(isFirst: Boolean) {
        if (isFirst) {
            showError()
        } else {
            injectRefreshLayout().finishRefresh(false)
        }
    }

    override fun injectLoadServiceView(): View? {
        return injectRefreshLayout() as View?
    }

}