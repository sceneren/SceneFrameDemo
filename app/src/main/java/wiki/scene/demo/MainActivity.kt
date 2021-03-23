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

        getData()

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

    override fun onRetryBtnClick() {
        super.onRetryBtnClick()
        getData()
    }


}