package wiki.scene.demo

import wiki.scene.demo.databinding.ActivityMainBinding
import wiki.scene.lib_base.base_ac.BaseAc
import wiki.scene.lib_base.base_util.LogUtils
import wiki.scene.lib_base.base_util.RouterUtil

class MainActivity : BaseAc<ActivityMainBinding>() {
    override fun initViews() {
        super.initViews()
        binding.tvTest.setOnClickListener{
            LogUtils.e("xxx")
            RouterUtil.launchWeb("http://www.baidu.com")
        }
    }
}