package wiki.scene.entity

data class ArticleBean(
    val id: String = "",
    val title: String = "",
    val link: String = "",
    var isOpen: Boolean = false
)