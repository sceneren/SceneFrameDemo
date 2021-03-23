package wiki.scene.demo

import wiki.scene.demo.databinding.ActivityMainItemBinding
import wiki.scene.lib_base.adapters.BaseBindingQuickAdapter
import wiki.scene.lib_base.base_api.res_data.BannerInfo
import wiki.scene.lib_base.base_util.GlideUtil

class MainAdapter :
    BaseBindingQuickAdapter<BannerInfo, ActivityMainItemBinding>(ActivityMainItemBinding::inflate) {
    override fun convert(holder: BaseBindingHolder, item: BannerInfo) {
        holder.getViewBinding<ActivityMainItemBinding>().apply {
            tvName.text = item.title
            GlideUtil.loadImage(
                ivImage,
                item.imagePath
            )
        }
    }

}