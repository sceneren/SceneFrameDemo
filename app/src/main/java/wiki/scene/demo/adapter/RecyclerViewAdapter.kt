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
                "https://pic.rmb.bdstatic.com/bjh/down/2331bb86e656e5a574d211113d154325.gif",
            )
            addChildClickViewIds(ivImage.id)
        }
    }

}