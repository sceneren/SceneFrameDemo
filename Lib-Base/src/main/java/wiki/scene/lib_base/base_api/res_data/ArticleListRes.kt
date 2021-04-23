package wiki.scene.lib_base.base_api.res_data

data class ArticleListRes(
    val curPage: Int = 0,
    val pageCount: Int = 0,
    val datas: MutableList<ArticleBean> = mutableListOf()
)