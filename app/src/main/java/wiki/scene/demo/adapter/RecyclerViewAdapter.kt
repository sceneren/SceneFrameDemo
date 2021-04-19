package wiki.scene.demo.adapter

import com.chad.library.adapter.base.module.LoadMoreModule
import wiki.scene.demo.databinding.ActRecyclerViewDemoItemBinding
import wiki.scene.lib_base.adapters.BaseBindingQuickAdapter
import wiki.scene.lib_base.base_api.res_data.ArticleBean
import wiki.scene.lib_base.base_util.GlideUtil

class RecyclerViewAdapter :
    BaseBindingQuickAdapter<ArticleBean, ActRecyclerViewDemoItemBinding>(ActRecyclerViewDemoItemBinding::inflate),LoadMoreModule {
    override fun convert(holder: BaseBindingHolder, item: ArticleBean) {
        holder.getViewBinding<ActRecyclerViewDemoItemBinding>().apply {
            tvName.text = item.title
            GlideUtil.loadRoundImage(
                ivImage,
                "https://github.com/wasabeef/glide-transformations/raw/main/art/demo-org.jpg",
            )
        }
    }

}