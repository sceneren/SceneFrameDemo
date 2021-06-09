package wiki.scene.lib_aop.checklogin.annotation

/**
 *
 * @Description:    登录拦截
 * @Author:         scene
 * @CreateDate:     2021/6/9 16:03
 * @UpdateUser:     更新者：
 * @UpdateDate:     2021/6/9 16:03
 * @UpdateRemark:   更新说明：
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