package wiki.scene.demo

import android.graphics.Color
import android.util.TypedValue
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.fondesa.recyclerviewdivider.dividerBuilder
import wiki.scene.demo.databinding.ActivityMainBinding
import wiki.scene.lib_base.base_ac.BaseAc
import wiki.scene.lib_base.base_api.res_data.BannerInfo
import wiki.scene.lib_base.base_api.util.ApiUtil
import wiki.scene.lib_base.base_util.RouterUtil
import wiki.scene.lib_network.bean.ApiResponse
import wiki.scene.lib_network.livedata.FastObserver

class MainActivity : BaseAc<ActivityMainBinding>() {
    private val adapter = MainAdapter()

    override fun initViews() {
        super.initViews()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(mContext)
        dividerBuilder().size(10, TypedValue.COMPLEX_UNIT_DIP)
            .color(Color.RED)
            .build()
            .addTo(binding.recyclerView)

        adapter.setOnItemClickListener { _, _, _ ->
            RouterUtil.launchWeb("http://www.baidu.com")
        }

        getTestData()

    }

    private fun getData() {
        ApiUtil.articleApi
            .banner
            .observe(this, object : FastObserver<MutableList<BannerInfo>>() {
                override fun onStart() {
                    super.onStart()
                    LogUtils.e("onStart")
                    showLoading(binding.recyclerView)
                }

                override fun onSuccess(data: ApiResponse<MutableList<BannerInfo>>) {
                    LogUtils.e(data.data.toString())
                    adapter.addData(data.data!!)
                    showSuccess()
                }

                override fun onFail(msg: String?) {
                    super.onFail(msg)
                    LogUtils.e("onFail")
                    showError()
                }

            })
    }

    private fun getTestData() {
        for (i in 1..50) {
            val info = BannerInfo(
                "https://pics4.baidu.com/feed/77094b36acaf2eddeeefda82c5793de139019334.png?token=5e92a93c3fdd8e8d7fc537aa0ca25a80",
                "标题${i}"
            )
            adapter.addData(info)
        }
    }

    override fun onRetryBtnClick() {
        super.onRetryBtnClick()
        getTestData()
    }


}