package wiki.scene.lib_base.indicator.adapter

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 *
 * @Description:    viewpage2的adapter实现
 * @Author:         scene
 * @CreateDate:     2021/7/1 15:06
 * @UpdateUser:
 * @UpdateDate:     2021/7/1 15:06
 * @UpdateRemark:
 * @Version:        1.0.0
 */
class ViewPager2Adapter(
    fragmentActivity: FragmentActivity,
    private val fragments: SparseArray<Fragment>
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return fragments.size()
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}