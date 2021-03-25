package wiki.scene.lib_base.impl

import android.view.View

/**
 * FileName: INetView
 * Created by zlx on 2020/9/22 10:38
 * Email: 1170762202@qq.com
 * Description:
 */
interface INetView {
    fun showLoading()
    fun showLoading(view: View)
    fun showSuccess()
    fun showEmpty()
    fun onRetryBtnClick()
    fun showError()
}