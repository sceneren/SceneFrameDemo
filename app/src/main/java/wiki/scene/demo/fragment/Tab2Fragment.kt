package wiki.scene.demo.fragment

import android.os.Bundle
import wiki.scene.demo.databinding.FragTab2Binding
import wiki.scene.lib_base.base_fg.BaseFg

class Tab2Fragment : BaseFg<FragTab2Binding>() {
    companion object {
        fun newInstance(): Tab2Fragment {
            val args = Bundle()
            val fragment = Tab2Fragment()
            fragment.arguments = args
            return fragment
        }
    }
}