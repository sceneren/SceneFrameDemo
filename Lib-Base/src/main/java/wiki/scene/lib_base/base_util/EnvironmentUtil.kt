package wiki.scene.lib_base.base_util

import com.blankj.utilcode.util.MetaDataUtils

object EnvironmentUtil {
    fun isBeta(): Boolean {
        val isBeta = MetaDataUtils.getMetaDataInApp("isBeta")
        return isBeta == "true"
    }
}