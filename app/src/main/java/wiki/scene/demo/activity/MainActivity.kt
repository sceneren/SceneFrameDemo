package wiki.scene.demo.activity

import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.alibaba.android.arouter.facade.annotation.Route
import wiki.scene.demo.R
import wiki.scene.demo.databinding.ActMainBinding
import wiki.scene.lib_base.base_ac.BaseAc
import wiki.scene.lib_common.router.RouterPath

@Route(path = RouterPath.Main.ACT_MAIN)
class MainActivity : BaseAc<ActMainBinding>() {

    private var currentTab = 0
    private lateinit var currentLav: LottieAnimationView
    private lateinit var currentTvTitle: TextView

    override fun initViews() {
        super.initViews()
        initTabLayout()
    }

    override fun hasTitleBarView(): Boolean {
        return false
    }

    private fun initTabLayout() {

        binding.tab1.run {
            tvTitle.text = "标题"
            lav.setAnimation(R.raw.main_tab_1)

            tvTitle.isSelected = true
            lav.progress = 1F

            currentTab = 0
            currentLav = lav
            currentTvTitle = tvTitle

            layout.setOnClickListener {
                resetTab(0, lav, tvTitle)
            }
        }

        binding.tab2.run {
            tvTitle.text = "标题"
            lav.setAnimation(R.raw.main_tab_2)
            layout.setOnClickListener {
                resetTab(1, lav, tvTitle)
            }
        }

        binding.tab3.run {
            tvTitle.text = "标题"
            lav.setAnimation(R.raw.main_tab_3)
            layout.setOnClickListener {
                resetTab(2, lav, tvTitle)
            }
        }

        binding.tab4.run {
            tvTitle.text = "标题"
            lav.setAnimation(R.raw.main_tab_4)
            layout.setOnClickListener {
                resetTab(3, lav, tvTitle)
            }
        }

        binding.tab5.run {
            tvTitle.text = "标题"
            lav.setAnimation(R.raw.main_tab_5)
            layout.setOnClickListener {
                resetTab(4, lav, tvTitle)
            }
        }
    }

    private fun resetTab(index: Int, cheesedLav: LottieAnimationView, cheesedTvTitle: TextView) {
        if (cheesedLav != currentLav) {
            currentLav.cancelAnimation()
            currentTab = index
            currentLav.progress = 0F
            currentTvTitle.isSelected = false

            cheesedLav.playAnimation()
            cheesedTvTitle.isSelected = true

            currentLav = cheesedLav
            currentTvTitle = cheesedTvTitle
        }
    }

}