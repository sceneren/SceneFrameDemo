package wiki.scene.lib_common.slidelayout

import com.d.lib.slidelayout.SlideLayout

/**
 * Help manage the slide
 * Created by D on 2017/5/30.
 */
class SlideHelper {
    private val mSlides = mutableListOf<SlideLayout>()
    fun onStateChanged(layout: SlideLayout, open: Boolean) {
        if (open) {
            mSlides.add(layout)
        } else {
            mSlides.remove(layout)
        }
    }

    fun closeAll(layout: SlideLayout): Boolean {
        if (mSlides.size <= 0) {
            return false
        }
        var result = false
        var i = 0
        while (i < mSlides.size) {
            val slide = mSlides[i]
            if (slide !== layout) {
                slide.close()
                mSlides.remove(slide) // Unnecessary
                result = true
                i--
            }
            i++
        }
        return result
    }
}