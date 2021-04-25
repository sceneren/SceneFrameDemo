package wiki.scene.lib_base.base_util

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.LocaleList
import wiki.scene.lib_base.mmkv.MMkvHelper
import wiki.scene.lib_common.provider.app.AppProvider.Companion.instance
import java.util.*

object LanguageUtil {
    val systemLanguage: String
        get() = Locale.getDefault().language + "-" + Locale.getDefault().country
    private val currentLanguage: Locale?
        get() {
            var locale = MMkvHelper.getInstance().language
            if (locale == null) {
                locale = Locale.getDefault()
            }
            return locale
        }

    fun switchChinese() {
        changeLanguage(Locale.SIMPLIFIED_CHINESE)
    }

    fun switchEnglish() {
        changeLanguage(Locale.US)
    }

    fun switchLanguage(locale: Locale) {
        changeLanguage(locale)
    }

    private fun changeLanguage(locale: Locale) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            setConfiguration(instance.app, locale)
        }
        MMkvHelper.getInstance().saveLanguage(locale)
    }

    /**
     * @param context
     * @param locale  想要切换的语言类型 比如 "en" ,"zh"
     */
    @SuppressLint("NewApi")
    fun setConfiguration(context: Context, locale: Locale?) {
        if (locale == null) {
            return
        }
        //获取应用程序的所有资源对象
        val resources = context.resources
        //获取设置对象
        val configuration = resources.configuration
        //如果API < 17
        when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1 -> {
                configuration.setLocale(locale)
            }
            else -> { //API 25  Android 7.7.1
                configuration.setLocale(locale)
                configuration.setLocales(LocaleList(locale))
            }
        }
        context.createConfigurationContext(configuration)
    }

    fun attachBaseContext(context: Context): Context {
        val locale = currentLanguage
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, locale)
        } else {
            context
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, locale: Locale?): Context {
        val resources = context.resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        configuration.setLocales(LocaleList(locale))
        return context.createConfigurationContext(configuration)
    }
}