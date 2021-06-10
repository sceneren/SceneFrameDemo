package wiki.scene.lib_base.impl

import com.hjq.bar.TitleBar


interface IAcView {
    fun beforeOnCreate()
    fun afterOnCreate()
    fun beforeSetContentView()
    fun afterSetContentView()
    fun initToolBarView(titleBarView: TitleBar)
    fun beforeInitView()
    fun initViews()
    fun initListener()
    fun afterInitViews()
    fun loadData()
}