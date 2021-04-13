package wiki.scene.lib_aop.singleclick

import android.view.View
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import wiki.scene.lib_aop.singleclick.annotation.SingleClick

/**
 * date: 2019\10\10 0010
 * author: zlx
 * email: 1170762202@qq.com
 * description: 防止重复点击注解
 */
@Aspect
class SingleClickAspect {
    @Pointcut(POINTCUT_METHOD)
    fun methodPointcut() {
    }

    @Pointcut(POINTCUT_ANNOTATION)
    fun annotationPointcut() {
    }

    @Pointcut(POINTCUT_BUTTER_KNIFE)
    fun butterKnifePointcut() {
    }

    @Pointcut(POINTCUT_SINGLE_CLICK)
    fun singleClickPointcut() {
    }

    @Around("butterKnifePointcut()")
    @Throws(Throwable::class)
    fun aroundJoinPoint(joinPoint: ProceedingJoinPoint) {
        try {
            val signature = joinPoint.signature as MethodSignature
            val method = signature.method
            //检查方法是否有注解
            val hasAnnotation =
                method != null && method.isAnnotationPresent(SingleClick::class.java)
            //计算点击间隔，没有注解默认500，有注解按注解参数来，注解参数为空默认500；
            var interval = 500
            if (hasAnnotation) {
                val annotation = method!!.getAnnotation(
                    SingleClick::class.java
                )
                interval = annotation.value
            }
            //获取被点击的view对象
            val args = joinPoint.args
            val view = findViewInMethodArgs(args)
            if (view != null) {
                val id = view.id
                //注解排除某个控件不防止双击
                if (hasAnnotation) {
                    val annotation = method!!.getAnnotation(
                        SingleClick::class.java
                    )
                    //按id值排除不防止双击的按钮点击
                    val except = annotation.except
                    for (i in except) {
                        if (i == id) {
                            mLastClickTime = System.currentTimeMillis()
                            joinPoint.proceed()
                            return
                        }
                    }
                    //按id名排除不防止双击的按钮点击（非app模块）
                    val idName = annotation.exceptIdName
                    val resources = view.resources
                    for (name in idName) {
                        val resId = resources.getIdentifier(name, "id", view.context.packageName)
                        if (resId == id) {
                            mLastClickTime = System.currentTimeMillis()
                            joinPoint.proceed()
                            return
                        }
                    }
                }
                if (canClick(interval)) {
                    mLastClickTime = System.currentTimeMillis()
                    joinPoint.proceed()
                    return
                }
            }

            //检测间隔时间是否达到预设时间并且线程空闲
            if (canClick(interval)) {
                mLastClickTime = System.currentTimeMillis()
                joinPoint.proceed()
            }
        } catch (e: Exception) {
            //出现异常不拦截点击事件
            joinPoint.proceed()
        }
    }

    fun findViewInMethodArgs(args: Array<Any?>?): View? {
        if (args == null || args.isEmpty()) {
            return null
        }
        for (arg in args) {
            if (arg is View) {
                if (arg.id != View.NO_ID) {
                    return arg
                }
            }
        }
        return null
    }

    fun canClick(interval: Int): Boolean {
        val l = System.currentTimeMillis() - mLastClickTime
        if (l > interval) {
            mLastClickTime = System.currentTimeMillis()
            return true
        }
        return false
    }

    companion object {
        private var mLastClickTime: Long = 0
        private const val POINTCUT_METHOD = "execution(* onClick(..))"
        private const val POINTCUT_ANNOTATION = "execution(@cn.leo.click.SingleClick * *(..))"
        private const val POINTCUT_BUTTER_KNIFE = "execution(@butterknife.OnClick * *(..))"
        private const val POINTCUT_SINGLE_CLICK =
            "execution(@wiki.scene.lib_aop.singleclick.annotation.SingleClick * *(..))"
    }
}