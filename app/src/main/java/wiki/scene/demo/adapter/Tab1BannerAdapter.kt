package wiki.scene.demo.adapter

import com.blankj.utilcode.util.SizeUtils
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder
import wiki.scene.demo.R
import wiki.scene.demo.databinding.FragTab1BannerItemBinding
import wiki.scene.entity.BannerInfo
import wiki.scene.lib_base.glide.GlideUtil

class Tab1BannerAdapter : BaseBannerAdapter<BannerInfo>() {
    override fun bindData(
        holder: BaseViewHolder<BannerInfo>?,
        data: BannerInfo,
        position: Int,
        pageSize: Int
    ) {
        holder?.run {
            val itemViewBinding = FragTab1BannerItemBinding.bind(holder.itemView)
            GlideUtil.loadImage(
                itemViewBinding.imageView,
                data.imagePath,
                R.drawable.ic_default_image
            )

            GlideUtil.loadRoundImage(
                itemViewBinding.imageView,
                data.imagePath,
                SizeUtils.dp2px(10F), R.drawable.ic_default_image
            )
        }
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.frag_tab_1_banner_item
    }
}