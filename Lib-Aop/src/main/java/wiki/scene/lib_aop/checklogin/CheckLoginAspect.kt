package wiki.scene.lib_aop.checklogin

import com.alibaba.android.arouter.launcher.ARouter
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import wiki.scene.lib_aop.checklogin.annotation.CheckLogin
import wiki.scene.lib_common.router.RouterPath
import wiki.scene.lib_base.mmkv.MMkvHelper

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
@Aspect
class CheckLoginAspect {
    @Pointcut(
        "execution(" +  //执行语句
                "@wiki.scene.lib_aop.checklogin.annotation.CheckLogin" +  //注解筛选
                " * " +  //类路径,*为任意路径
                "*" +  //方法名,*为任意方法名
                "(..)" +  //方法参数,'..'为任意个任意类型参数
                ")" +
                " && " +  //并集
                "@annotation(checkLogin)"
    )
    fun pointcutCheckLogin(checkLogin: CheckLogin?) {
    }

    @Around("pointcutCheckLogin(checkLogin)")
    @Throws(Throwable::class)
    fun aroundCheckLogin(joinPoint: ProceedingJoinPoint, checkLogin: CheckLogin?): Any? {
        val userInfo = MMkvHelper.getInstance().userInfo
        return if (userInfo == null) {
            ARouter.getInstance().build(RouterPath.Login.ACT_LOGIN).navigation()
            null
        } else {
            joinPoint.proceed()
        }
    }
}