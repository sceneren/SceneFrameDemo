package wiki.scene.lib_aop.singleclick.annotation

/**
 * Created by Leo on 2018/5/21.
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.TYPE
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class UnSingleClick()