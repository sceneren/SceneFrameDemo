package wiki.scene.lib_base.aop.singleclick

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
        private const val FILTER_TIME_MILLS = 500L
        private const val AROUND_ARGS = "execution(* android.view.View.OnClickListener.onClick(..))"
        private const val BEFORE_ARGS =
            "execution(@wiki.scene.lib_base.aop.singleclick.annotation.UnSingleClick * *(..))"
    }

    private val mTag = this.javaClass.simpleName

    //上次点击的时间
    private var sLastClick = 0L

    //是否过滤点击 默认是
    private var checkClick = true


    /**
     * 直接setOnclickListener 拦截点击事件
     */
    @Around(AROUND_ARGS)
    @Throws(Throwable::class)
    fun clickFilterHook(joinPoint: ProceedingJoinPoint) {

        if (System.currentTimeMillis() - sLastClick >= FILTER_TIME_MILLS || !checkClick) {
            //大于指定时间
            doClick(joinPoint)
        } else {
            //小于指定时间
            LogUtils.e(mTag, "Filter duplicate clicks")
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
        //修改默认过滤点击
        checkClick = true
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
    @Before(BEFORE_ARGS)
    @Throws(Throwable::class)
    fun beforeUncheckClick(joinPoint: JoinPoint?) {
        LogUtils.e("UncheckClick")
        //修改为不需要过滤点击
        checkClick = false
    }

}