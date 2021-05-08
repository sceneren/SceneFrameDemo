package wiki.scene.lib_base.impl

import com.hjq.bar.TitleBar


interface IAcView {
    fun initToolBarView(titleBarView: TitleBar)
    fun initViews()
    fun initEvents()
    fun beforeOnCreate()
    fun afterOnCreate()
    fun beforeInitView()
    fun loadData()
    fun afterInitViews()
}