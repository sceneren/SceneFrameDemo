package wiki.scene.lib_base.aop.checklogin.annotation

/**
 *
 * @Description:    登录拦截
 * @Author:         scene
 * @CreateDate:     2021/6/9 16:03
 * @UpdateUser:
 * @UpdateDate:     2021/6/9 16:03
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
annotation class CheckLogin