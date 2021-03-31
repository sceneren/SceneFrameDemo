package wiki.scene.lib_base.constant

object RouterPath {
    //登陆
    private const val LOGIN = "/module_login"

    object Login {
        const val ACT_LOGIN = "$LOGIN/ActLogin"
    }

    //web
    private const val WEB = "/module_web"

    object Web {
        const val ACT_WEB = "$WEB/ActWeb"
    }


    //主页
    private const val MAIN = "/module_main"

    object Main {
        const val ACT_MAIN = "$MAIN/ActMain"
        const val ACT_MAIN_2 = "$MAIN/ActMain2"
        const val ACT_RECYCLERVIEW = "$MAIN/ActRecyclerView"
        const val ACT_RECYCLERVIEW_STICKY_HEADER = "$MAIN/ActRecyclerViewStickyHeader"

        const val FRAG_TAB_1 = "$MAIN/FragTab1"
        const val FRAG_TAB_2 = "$MAIN/FragTab2"
        const val FRAG_TAB_3 = "$MAIN/FragTab3"
    }


}