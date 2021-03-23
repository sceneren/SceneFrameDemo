package wiki.scene.demo

import wiki.scene.demo.databinding.ActivityMainItemBinding
import wiki.scene.lib_base.adapters.BaseBindingQuickAdapter
import wiki.scene.lib_base.base_util.GlideUtil

class MainAdapter :
    BaseBindingQuickAdapter<String, ActivityMainItemBinding>(ActivityMainItemBinding::inflate) {
    override fun convert(holder: BaseBindingHolder, item: String) {
        holder.getViewBinding<ActivityMainItemBinding>().apply {
            tvName.text = item
            GlideUtil.loadImage(
                ivImage,
                "https://www.baidu.com/img/dong_47f08bb2dd6546c9a788f71d7463ce48.gif"
            )
        }
    }

}