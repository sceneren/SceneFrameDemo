package wiki.scene.lib_aop.checklogin

import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import wiki.scene.lib_aop.checklogin.annotation.CheckLogin
import wiki.scene.lib_base.constant.RouterPath
import wiki.scene.lib_base.database.MMkvHelper

/**
 * FileName:
 * Created by zlx on 2020/9/21 9:27
 * Email: 1170762202@qq.com
 * Description:
 */
@Aspect
class CheckLoginAspect {
    @Pointcut(
        "execution(" +  //执行语句
                "@com.zlx.library_aop.checklogin.annotation.CheckLogin" +  //注解筛选
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
        Log.i("TAG", "登录校验")
        val userInfo = MMkvHelper.getInstance().userInfo
        return if (userInfo == null) {
            ARouter.getInstance().build(RouterPath.Login.ACT_LOGIN).navigation()
            Log.i("TAG", "未登录")
            null
        } else {
            joinPoint.proceed()
        }
    }
}