package wiki.scene.demo.fragment

import android.os.Bundle
import wiki.scene.demo.databinding.FragTab3Binding
import wiki.scene.lib_base.base_fg.BaseFg

class Tab3Fragment : BaseFg<FragTab3Binding>() {
    companion object {
        fun newInstance(): Tab3Fragment {
            val args = Bundle()
            val fragment = Tab3Fragment()
            fragment.arguments = args
            return fragment
        }
    }
}