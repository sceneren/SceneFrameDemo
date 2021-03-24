package wiki.scene.demo

import android.graphics.Color
import android.util.TypedValue
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import wiki.scene.demo.databinding.ActivityMainBinding
import wiki.scene.lib_base.base_ac.BaseAc
import wiki.scene.lib_base.base_api.res_data.ArticleListRes
import wiki.scene.lib_base.base_api.util.ApiUtil
import wiki.scene.lib_base.base_util.RouterUtil
import wiki.scene.lib_network.bean.ApiResponse
import wiki.scene.lib_network.livedata.FastObserver

class MainActivity : BaseAc<ActivityMainBinding>(), OnRefreshListener, OnLoadMoreListener {
    private val mAdapter = MainAdapter()

    private var pageNo = 0

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
                        pageNo = it.curPage
                        val hasMore = it.curPage < it.pageCount
                        if (isFirst) {
                            showSuccess()
                        } else {
                            binding.refreshLayout.finishRefresh(true)
                        }
                        if (hasMore) {
                            mAdapter.loadMoreModule.loadMoreComplete()
                        } else {
                            mAdapter.loadMoreModule.loadMoreEnd()
                        }
                        if (pageNo == 1) {
                            mAdapter.setNewInstance(it.datas!!)
                        } else {
                            mAdapter.addData(it.datas!!)
                        }
                    }
                }

                override fun onFail(msg: String?) {
                    super.onFail(msg)
                    if (isFirst) {
                        showError()
                    } else {
                        if (loadPage == 1) {
                            binding.refreshLayout.finishRefresh(false)
                        } else {
                            mAdapter.loadMoreModule.loadMoreFail()
                        }
                    }
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


}