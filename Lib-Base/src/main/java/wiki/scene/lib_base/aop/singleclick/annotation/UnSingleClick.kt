package wiki.scene.lib_base.aop.singleclick.annotation

/**
 *
 * @Description:    不需要控制重复点击
 * @Author:         scene
 * @CreateDate:     2021/6/10 10:10
 * @UpdateUser:
 * @UpdateDate:     2021/6/10 10:10
 * @UpdateRemark:
 * @Version:        1.0.0
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.TYPE
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class UnSingleClick()