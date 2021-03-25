package wiki.scene.demo

import android.graphics.Color
import android.util.TypedValue
import androidx.recyclerview.widget.LinearLayoutManager
import com.aries.ui.view.title.TitleBarView
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.scwang.smart.refresh.layout.api.RefreshLayout
import wiki.scene.demo.databinding.ActivityMainBinding
import wiki.scene.lib_base.adapters.BaseBindingQuickAdapter
import wiki.scene.lib_base.base_ac.BaseRecyclerViewAc
import wiki.scene.lib_base.base_api.res_data.ArticleBean
import wiki.scene.lib_base.base_api.res_data.ArticleListRes
import wiki.scene.lib_base.base_api.util.ApiUtil
import wiki.scene.lib_base.base_util.RouterUtil
import wiki.scene.lib_network.bean.ApiResponse
import wiki.scene.lib_network.livedata.FastObserver

class MainActivity : BaseRecyclerViewAc<ActivityMainBinding, ArticleBean>() {
    private val mAdapter = MainAdapter()

    override fun initToolBarView(titleBarView: TitleBarView) {
        super.initToolBarView(titleBarView)
        titleBarView.setTitleMainText("首页")
    }

    override fun hasTitleBarBack(): Boolean {
        return false
    }

    override fun initRecyclerView() {
        super.initRecyclerView()
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

        getVideoData()
    }

    override fun injectLoadPageStart(): Int {
        return 0
    }

    override fun injectReturnPageStart(): Int {
        return 1
    }

    override fun injectAdapter(): BaseQuickAdapter<ArticleBean, BaseBindingQuickAdapter.BaseBindingHolder> {
        return mAdapter
    }

    override fun injectRefreshLayout(): RefreshLayout {
        return binding.refreshLayout
    }

    override fun getListData(isFirst: Boolean, loadPage: Int) {
        ApiUtil.articleApi
            .listArticle(loadPage)
            .observe(this, object : FastObserver<ArticleListRes>() {
                override fun onStart() {
                    super.onStart()
                    loadListDataStart(isFirst, binding.refreshLayout)
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

    private fun getVideoData() {
        ApiUtil.videoApi
            .getChannelInfo()
            .observe(this, object : FastObserver<String>() {
                override fun onSuccess(data: ApiResponse<String>) {
                    LogUtils.e(data.toString())
                }

                override fun onFail(msg: String?) {
                    super.onFail(msg)
                    LogUtils.e(msg)
                }

            })
    }


}