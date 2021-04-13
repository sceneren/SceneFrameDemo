package wiki.scene.lib_aop.singleclick.annotation

/**
 * Created by Leo on 2018/5/21.
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class SingleClick(
    val value: Int = 2000,
    val except: IntArray = [],
    val exceptIdName: Array<String> = []
)