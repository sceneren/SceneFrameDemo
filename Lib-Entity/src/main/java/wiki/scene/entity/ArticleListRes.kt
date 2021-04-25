package wiki.scene.entity

data class ArticleListRes(
    val curPage: Int = 0,
    val pageCount: Int = 0,
    val datas: MutableList<ArticleBean> = mutableListOf()
)