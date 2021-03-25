package wiki.scene.lib_base.impl

import com.aries.ui.view.title.TitleBarView


interface IAcView {
    fun initToolBarView(titleBarView: TitleBarView)
    fun initViews()
    fun initEvents()
    fun beforeOnCreate()
    fun afterOnCreate()
    fun beforeInitView()
    fun loadData()
    fun afterInitViews()
}