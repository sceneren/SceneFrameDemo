package wiki.scene.lib_aop.singleclick

import android.view.View
import com.blankj.utilcode.util.LogUtils
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before

/**
 *
 * @Description:    防止重复点击注解
 * @Author:         scene
 * @CreateDate:     2021/6/9 16:02
 * @UpdateUser:     更新者：
 * @UpdateDate:     2021/6/9 16:02
 * @UpdateRemark:   更新说明：
 * @Version:        1.0.0
 */
@Aspect
class SingleClickAspect {

    companion object {
        //拦截所有两次点击时间间隔小于一秒的点击事件
        private const val FILTER_TIME_MILLS = 1000L
    }

    private val mTag = this.javaClass.simpleName

    //上次点击的时间
    private var sLastClick = 0L

    //上次点击事件View
    private var lastView: View? = null

    //是否过滤点击 默认是
    private var checkClick = true


    //---- add content -----
    //拦截所有* android.view.View.OnClickListener.onClick(..) 事件
    //直接setOnclickListener 拦截点击事件
    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
    @Throws(Throwable::class)
    open fun clickFilterHook(joinPoint: ProceedingJoinPoint) {
        //大于指定时间
        if (System.currentTimeMillis() - sLastClick >= FILTER_TIME_MILLS) {
            doClick(joinPoint)
        } else {
            //---- update content -----  判断是否需要过滤点击
            //小于指定秒数 但是不是同一个view 可以点击  或者不过滤点击
            if (!checkClick || lastView == null || lastView !== joinPoint.args[0]) {
                //---- update content -----  判断是否需要过滤点击
                doClick(joinPoint)
            } else {
                //大于指定秒数 且是同一个view
                LogUtils.e(mTag, "Filter duplicate clicks")
            }
        }
    }

    /**
     * 执行原有的 onClick 方法
     */
    @Throws(Throwable::class)
    private fun doClick(joinPoint: ProceedingJoinPoint) {
        //判断 view 是否存在
        if (joinPoint.args.isEmpty()) {
            joinPoint.proceed()
            return
        }
        //记录点击的 view
        lastView = joinPoint.args[0] as View
        //---- add content -----
        //修改默认过滤点击
        checkClick = true
        //---- add content -----
        //记录点击事件
        sLastClick = System.currentTimeMillis()
        //执行点击事件
        try {
            joinPoint.proceed()
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
    }

    //标志不过滤点击
    @Before("execution(@wiki.scene.lib_aop.singleclick.annotation.SingleClick * *(..))")
    @Throws(Throwable::class)
    fun beforeUncheckClick(joinPoint: JoinPoint?) {
        LogUtils.e("beforeUncheckClick")
        //修改为不需要过滤点击
        checkClick = false
    }

}