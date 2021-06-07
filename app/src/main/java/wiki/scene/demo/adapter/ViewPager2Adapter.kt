package wiki.scene.demo.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import wiki.scene.lib_base.base_fg.fragmentvisibility.RxVisibilityFragment

class ViewPager2Adapter constructor(
    mActivity: AppCompatActivity,
    private val fragmentList: MutableList<RxVisibilityFragment> = mutableListOf()
) : FragmentStateAdapter(mActivity) {
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}