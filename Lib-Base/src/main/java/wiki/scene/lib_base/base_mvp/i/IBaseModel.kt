package wiki.scene.lib_base.base_mvp.i

interface IBaseModel {
    /**
     * 在框架中 [] 时会默认调用 [IBaseModel.onDestroy]
     */
    fun onDestroy()

}