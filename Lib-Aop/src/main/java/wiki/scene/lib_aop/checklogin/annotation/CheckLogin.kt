package wiki.scene.lib_aop.checklogin.annotation

/**
 * FileName: CheckLogin
 * Created by zlx on 2020/9/21 9:25
 * Email: 1170762202@qq.com
 * Description:
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class CheckLogin