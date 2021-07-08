package wiki.scene.lib_base.base_mvp.i


interface IRecyclerViewBaseView<T> : IBaseView {

    fun loadListDataStart(isFirst: Boolean)

    fun loadListDataSuccess(
        isFirst: Boolean,
        currentPage: Int,
        totalPage: Int,
        list: MutableList<T>
    )

    fun loadListDataSuccess(
        isFirst: Boolean,
        list: MutableList<T>
    )

    fun loadListDataFail(isFirst: Boolean, loadPage: Int)

    fun loadListDataFail(isFirst: Boolean)
}