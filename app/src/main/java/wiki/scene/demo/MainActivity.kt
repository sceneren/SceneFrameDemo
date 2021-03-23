package wiki.scene.demo

import android.graphics.Color
import android.util.TypedValue
import androidx.recyclerview.widget.LinearLayoutManager
import com.fondesa.recyclerviewdivider.dividerBuilder
import wiki.scene.demo.databinding.ActivityMainBinding
import wiki.scene.lib_base.base_ac.BaseAc
import wiki.scene.lib_base.base_api.res_data.BannerInfo
import wiki.scene.lib_base.base_api.util.ApiUtil
import wiki.scene.lib_base.base_util.LogUtils
import wiki.scene.lib_base.base_util.RouterUtil
import wiki.scene.lib_network.bean.ApiResponse
import wiki.scene.lib_network.livedata.FastObserver

class MainActivity : BaseAc<ActivityMainBinding>() {
    override fun initViews() {
        super.initViews()
        val adapter = MainAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(mContext)
        dividerBuilder().size(10, TypedValue.COMPLEX_UNIT_DIP)
            .color(Color.RED)
            .build()
            .addTo(binding.recyclerView)

        adapter.setOnItemClickListener { _, _, _ ->
            RouterUtil.launchWeb("http://www.baidu.com")
        }

        ApiUtil.articleApi
            .banner
            .observe(this, object : FastObserver<MutableList<BannerInfo>>() {
                override fun onSuccess(data: ApiResponse<MutableList<BannerInfo>>) {
                    LogUtils.e(data.data.toString())
                    adapter.addData(data.data!!)
                }

                override fun onFail(msg: String?) {
                    super.onFail(msg)
                    LogUtils.e("onFail")
                }

                override fun onFinish() {
                    super.onFinish()
                    LogUtils.e("onFinish")
                }

            })

    }


}