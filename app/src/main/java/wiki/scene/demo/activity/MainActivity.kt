package wiki.scene.demo.activity

import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import wiki.scene.demo.R
import wiki.scene.demo.adapter.ViewPager2Adapter
import wiki.scene.demo.databinding.ActMainBinding
import wiki.scene.lib_base.base_ac.BaseAc
import wiki.scene.lib_base.base_fg.fragmentvisibility.RxVisibilityFragment
import wiki.scene.lib_common.router.RouterPath

@Route(path = RouterPath.Main.ACT_MAIN)
class MainActivity : BaseAc<ActMainBinding>() {

    private var currentTab = 0
    private lateinit var currentLav: LottieAnimationView
    private lateinit var currentTvTitle: TextView

    override fun hasTitleBarView(): Boolean {
        return false
    }

    override fun initViews() {
        super.initViews()
        initTabLayout()
        initViewPager()
    }

    private fun initViewPager() {
        val fragmentList = mutableListOf<RxVisibilityFragment>()
        fragmentList.add(
            ARouter.getInstance()
                .build(RouterPath.Main.FRAG_TAB_2)
                .withString("color", "#BB86FC")
                .navigation() as RxVisibilityFragment
        )
        fragmentList.add(
            ARouter.getInstance()
                .build(RouterPath.Main.FRAG_TAB_2)
                .withString("color", "#FF6200EE")
                .navigation() as RxVisibilityFragment
        )
        fragmentList.add(
            ARouter.getInstance()
                .build(RouterPath.Main.FRAG_TAB_2)
                .withString("color", "#FF3700B3")
                .navigation() as RxVisibilityFragment
        )
        fragmentList.add(
            ARouter.getInstance()
                .build(RouterPath.Main.FRAG_TAB_2)
                .withString("color", "#FF03DAC5")
                .navigation() as RxVisibilityFragment
        )
        fragmentList.add(
            ARouter.getInstance()
                .build(RouterPath.Main.FRAG_TAB_2)
                .withString("color", "#FF018786")
                .navigation() as RxVisibilityFragment
        )
        val mAdapter = ViewPager2Adapter(this, fragmentList)
        binding.viewPager2.adapter = mAdapter

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                selectFragment(position, false)
            }
        })
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
                selectFragment(0)
            }
        }

        binding.tab2.run {
            tvTitle.text = "标题"
            lav.setAnimation(R.raw.main_tab_2)
            layout.setOnClickListener {
                selectFragment(1)
            }
        }

        binding.tab3.run {
            tvTitle.text = "标题"
            lav.setAnimation(R.raw.main_tab_3)
            layout.setOnClickListener {
                selectFragment(2)
            }
        }

        binding.tab4.run {
            tvTitle.text = "标题"
            lav.setAnimation(R.raw.main_tab_4)
            layout.setOnClickListener {
                selectFragment(3)
            }
        }

        binding.tab5.run {
            tvTitle.text = "标题"
            lav.setAnimation(R.raw.main_tab_5)
            layout.setOnClickListener {
                selectFragment(4)
            }
        }
    }


    private fun selectFragment(position: Int, changeViewPage2: Boolean = true) {

        when (position) {
            0 -> {
                resetTab(position, binding.tab1.lav, binding.tab1.tvTitle)
            }
            1 -> {
                resetTab(position, binding.tab2.lav, binding.tab2.tvTitle)
            }
            2 -> {
                resetTab(position, binding.tab3.lav, binding.tab3.tvTitle)
            }
            3 -> {
                resetTab(position, binding.tab4.lav, binding.tab4.tvTitle)
            }
            4 -> {
                resetTab(position, binding.tab5.lav, binding.tab5.tvTitle)
            }
        }
        if (changeViewPage2) {
            binding.viewPager2.setCurrentItem(position,false)
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