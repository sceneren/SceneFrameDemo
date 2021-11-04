package wiki.scene.demo.adapter

import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import wiki.scene.demo.R
import wiki.scene.demo.databinding.ActRecyclerViewDemoItemBinding
import wiki.scene.demo.databinding.ActStickyRecyclerviewBinding
import wiki.scene.demo.databinding.ActStickyRecyclerviewItemBinding
import wiki.scene.entity.ArticleBean
import wiki.scene.entity.base.StickyBean
import wiki.scene.lib_base.adapters.BaseBindingQuickAdapter
import javax.inject.Inject

class StickyAdapter @Inject constructor() :
    BaseBindingQuickAdapter<StickyBean, ActStickyRecyclerviewItemBinding>(
        ActStickyRecyclerviewItemBinding::inflate
    ) {
    override fun convert(holder: BaseBindingHolder, item: StickyBean) {
        holder.setText(R.id.tv_title, item.title)
        holder.setText(R.id.tv_time, TimeUtils.millis2String(item.terminalTime))
    }


//    init {
//        setMultiTypeDelegate(MyMultiTypeDelegate())
//    }

//    override fun convert(holder: BaseViewHolder, item: StickyBean) {
//        when (holder.itemViewType) {
//            1 -> {
//                holder.setText(R.id.tv_title, item.title)
//                holder.setText(R.id.tv_time, TimeUtils.millis2String(item.terminalTime))
//            }
//
//            2 -> {
//                holder.setText(R.id.tv_title, item.title)
//            }
//        }

}


//    inner class MyMultiTypeDelegate : BaseMultiTypeDelegate<StickyBean>() {
//
//        init {
//            addItemType(1, R.layout.act_sticky_recyclerview_item)
//            addItemType(2, R.layout.act_sticky_recyclerview_item_header)
//        }
//
//        override fun getItemType(data: List<StickyBean>, position: Int): Int {
//            return data[position].type
//        }
//
//    }

//}