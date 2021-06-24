package wiki.scene.lib_base.priview

import androidx.appcompat.app.AppCompatActivity
import cc.shinichi.library.ImagePreview
import com.blankj.utilcode.util.StringUtils
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.hjq.toast.ToastUtils
import com.kongzue.dialogx.DialogX
import com.kongzue.dialogx.dialogs.BottomMenu
import wiki.scene.lib_base.R

/**
 *
 * @Description:    查看大图
 * @Author:         scene
 * @CreateDate:     2020/10/13 4:39 PM
 * @UpdateUser:
 * @UpdateDate:     2020/10/13 4:39 PM
 * @UpdateRemark:
 * @Version:        1.0.0
 */
object ImagePreviewUtils {

    fun preview(
        mActivity: AppCompatActivity,
        imageList: MutableList<String>?,
        index: Int = 0
    ) {
        if (imageList.isNullOrEmpty()) {
            return
        }
        val newIndex = if (imageList.size <= index) {
            0
        } else {
            index
        }
        ImagePreview.getInstance()
            .setContext(mActivity)
            .setIndex(newIndex)
            .setImageList(imageList)
            .setShowCloseButton(false)
            .setEnableClickClose(true)
            .setEnableDragClose(true)
            .setEnableUpDragClose(true)
            .setShowDownButton(false)
            .setBigImageLongClickListener { activity, _, position ->
                XXPermissions.with(activity)
                    .permission(Permission.Group.STORAGE)
                    .request(object : OnPermissionCallback {
                        override fun onGranted(permissions: MutableList<String>, all: Boolean) {
                            if (all) {
                                showLongClickDialog(
                                    activity as AppCompatActivity,
                                    imageList[position]
                                )
                            }
                        }

                        override fun onDenied(permissions: MutableList<String>?, never: Boolean) {
                            super.onDenied(permissions, never)
                            if (never) {
                                XXPermissions.startPermissionActivity(mActivity, permissions)
                            } else {
                                ToastUtils.show(R.string.lib_base_permission_denied)
                            }
                        }

                    })

                true
            }
            .start()

    }

    private fun showLongClickDialog(activity: AppCompatActivity, imagePath: String) {
        BottomMenu.show(
            StringUtils.getStringArray(R.array.lib_base_preview_image_long_click)
        ) { dialog, _, index ->
            if (index == 0) {
                dialog.dismiss()
                DownloadImageUtil.instance.downloadImage(activity, imagePath)
            }
            false
        }.setCancelButton(R.string.lib_base_cancel)
            .theme = DialogX.THEME.LIGHT

    }


}