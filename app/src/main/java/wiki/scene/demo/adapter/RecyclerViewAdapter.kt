package wiki.scene.demo.adapter

import com.chad.library.adapter.base.module.LoadMoreModule
import com.d.lib.slidelayout.SlideLayout
import wiki.scene.demo.R
import wiki.scene.demo.databinding.ActRecyclerViewDemoItemBinding
import wiki.scene.lib_base.adapters.BaseBindingQuickAdapter
import wiki.scene.lib_base.glide.GlideUtil
import wiki.scene.lib_common.slidelayout.SlideHelper
import javax.inject.Inject


class RecyclerViewAdapter @Inject constructor() :
    BaseBindingQuickAdapter<wiki.scene.entity.ArticleBean, ActRecyclerViewDemoItemBinding>(
        ActRecyclerViewDemoItemBinding::inflate
    ), LoadMoreModule {

    private val mSlideHelper = SlideHelper()

    init {
        addChildClickViewIds(R.id.ivImage, R.id.tvDelete)
    }

    override fun convert(holder: BaseBindingHolder, item: wiki.scene.entity.ArticleBean) {
        holder.getViewBinding<ActRecyclerViewDemoItemBinding>().run {
            tvName.text = item.title
            GlideUtil.loadRoundImage(
                ivImage,
                "https://pic.rmb.bdstatic.com/bjh/down/2331bb86e656e5a574d211113d154325.gif",
            )
            slideLayout.setOpen(item.isOpen, false)
            slideLayout.setOnStateChangeListener(object : SlideLayout.OnStateChangeListener() {
                override fun onStateChanged(layout: SlideLayout, open: Boolean) {
                    item.isOpen = open
                    mSlideHelper.onStateChanged(layout, open)
                }

                override fun onInterceptTouchEvent(layout: SlideLayout): Boolean {
                    mSlideHelper.closeAll(layout)
                    return false
                }

            })
        }
    }

}