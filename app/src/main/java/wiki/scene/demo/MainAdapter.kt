package wiki.scene.demo

import wiki.scene.demo.databinding.ActivityMainItemBinding
import wiki.scene.lib_base.adapters.BaseBindingQuickAdapter

class MainAdapter :
    BaseBindingQuickAdapter<String, ActivityMainItemBinding>(ActivityMainItemBinding::inflate) {
    override fun convert(holder: BaseBindingHolder, item: String) {
        holder.getViewBinding<ActivityMainItemBinding>().apply {
            tvName.text = item
        }
    }

}