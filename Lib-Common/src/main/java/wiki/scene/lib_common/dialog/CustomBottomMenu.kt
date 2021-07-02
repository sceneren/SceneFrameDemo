package wiki.scene.lib_common.dialog

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kongzue.dialogx.dialogs.CustomDialog
import com.kongzue.dialogx.interfaces.OnBindView
import wiki.scene.lib_common.R
import wiki.scene.lib_common.databinding.LibCommonCustomBottomMenuBinding
import wiki.scene.lib_common.databinding.LibCommonCustomBottomMenuItemBinding
import wiki.scene.lib_common.dialog.callback.MenuItemClickListener

object CustomBottomMenu {
    fun show(menuList: Array<String>, menuItemClickListener: MenuItemClickListener) {
        show(menuList.toList(), menuItemClickListener)
    }

    fun show(menuList: List<String>, menuItemClickListener: MenuItemClickListener) {
        CustomDialog.build()
            .setCustomView(object :
                OnBindView<CustomDialog>(R.layout.lib_common_custom_bottom_menu) {
                override fun onBind(dialog: CustomDialog, v: View) {
                    val binding = LibCommonCustomBottomMenuBinding.bind(v)
                    for ((index, menu) in menuList.withIndex()) {
                        val menuView = LayoutInflater.from(v.context)
                            .inflate(
                                R.layout.lib_common_custom_bottom_menu_item,
                                v.parent as ViewGroup,
                                false
                            )
                        val itemBinding = LibCommonCustomBottomMenuItemBinding.bind(menuView)
                        itemBinding.tvMenu.text = menu
                        itemBinding.tvMenu.setOnClickListener {
                            menuItemClickListener.onClickMenuItemClick(index, menu)
                            if (dialog.isShow) {
                                dialog.dismiss()
                            }
                        }
                        if (menuList.lastIndex == index) {
                            itemBinding.viewLine.visibility = View.GONE
                        } else {
                            itemBinding.viewLine.visibility = View.VISIBLE
                        }
                        binding.menuLayout.addView(menuView)
                    }

                    binding.tvCancel
                        .setOnClickListener {
                            if (dialog.isShow) {
                                dialog.dismiss()
                            }
                        }

                }

            })
            .setCancelable(true)
            .setMaskColor(Color.parseColor("#4D000000"))
            .setAlign(CustomDialog.ALIGN.BOTTOM)
            .show()
    }
}