package wiki.scene.demo.adapter

import com.chad.library.adapter.base.module.LoadMoreModule
import wiki.scene.demo.databinding.ActRecyclerViewDemoItemBinding
import wiki.scene.lib_base.adapters.BaseBindingQuickAdapter
import wiki.scene.lib_base.glide.GlideUtil

class RecyclerViewAdapter :
    BaseBindingQuickAdapter<wiki.scene.entity.ArticleBean, ActRecyclerViewDemoItemBinding>(
        ActRecyclerViewDemoItemBinding::inflate
    ), LoadMoreModule {
    override fun convert(holder: BaseBindingHolder, item: wiki.scene.entity.ArticleBean) {
        holder.getViewBinding<ActRecyclerViewDemoItemBinding>().apply {
            tvName.text = item.title
            GlideUtil.loadRoundImage(
                ivImage,
                "https://github.com/wasabeef/glide-transformations/raw/main/art/demo-org.jpg",
            )
            addChildClickViewIds(ivImage.id)
        }
    }

}