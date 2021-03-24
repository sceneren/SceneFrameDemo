package wiki.scene.demo

import android.graphics.Color
import android.util.TypedValue
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import wiki.scene.demo.databinding.ActivityMainBinding
import wiki.scene.lib_base.adapters.BaseBindingQuickAdapter
import wiki.scene.lib_base.base_ac.BaseRecyclerViewAc
import wiki.scene.lib_base.base_api.res_data.ArticleBean
import wiki.scene.lib_base.base_api.res_data.ArticleListRes
import wiki.scene.lib_base.base_api.util.ApiUtil
import wiki.scene.lib_base.base_util.RouterUtil
import wiki.scene.lib_network.bean.ApiResponse
import wiki.scene.lib_network.livedata.FastObserver

class MainActivity : BaseRecyclerViewAc<ActivityMainBinding, ArticleBean>(),
    OnRefreshListener,
    OnLoadMoreListener {
    private val mAdapter = MainAdapter()

    override fun initViews() {
        super.initViews()

        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(mContext)
        dividerBuilder().size(10, TypedValue.COMPLEX_UNIT_DIP)
            .color(Color.RED)
            .build()
            .addTo(binding.recyclerView)

        mAdapter.setOnItemClickListener { _, _, position ->

            val url = mAdapter.data[position].link
            RouterUtil.launchWeb(url)
        }

        binding.refreshLayout.setOnRefreshListener(this)
        mAdapter.loadMoreModule.setOnLoadMoreListener(this)
    }

    override fun loadData() {
        super.loadData()
        getData(0, true)
    }

    private fun getData(loadPage: Int, isFirst: Boolean) {
        ApiUtil.articleApi
            .listArticle(loadPage)
            .observe(this, object : FastObserver<ArticleListRes>() {
                override fun onStart() {
                    super.onStart()
                    if (isFirst) {
                        showLoading(binding.refreshLayout)
                    }
                }

                override fun onSuccess(data: ApiResponse<ArticleListRes>) {
                    data.data?.let {
                        loadListDataSuccess(isFirst, it.curPage, it.total, it.datas!!)
                    }
                }

                override fun onFail(msg: String?) {
                    super.onFail(msg)
                    loadListDataFail(isFirst, loadPage)
                }

            })
    }

    override fun onRetryBtnClick() {
        super.onRetryBtnClick()
        getData(0, true)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        getData(0, false)
    }

    override fun onLoadMore() {
        getData(pageNo, false)
    }

    override fun injectAdapter(): BaseQuickAdapter<ArticleBean, BaseBindingQuickAdapter.BaseBindingHolder> {
        return mAdapter
    }

    override fun injectRefreshLayout(): RefreshLayout {
        return binding.refreshLayout
    }


}