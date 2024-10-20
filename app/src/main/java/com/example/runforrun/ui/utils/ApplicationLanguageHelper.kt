package com.example.runforrun.ui.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.view.ContextThemeWrapper
import com.example.runforrun.R
import java.util.Locale

class ApplicationLanguageHelper (base: Context) : ContextThemeWrapper(base, R.style.Theme_RunForRun) {

    companion object {
        fun wrap(context: Context, language: String): ContextThemeWrapper {
            var fcontext = context
            val config = fcontext.resources.configuration
            if (language.isNotEmpty()) {
                val locale = Locale.forLanguageTag(language)
                Locale.setDefault(locale)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setSystemLocale(config, locale)
                } else {
                    setSystemLocaleLegacy(config, locale)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    config.setLayoutDirection(locale)
                    fcontext = fcontext.createConfigurationContext(config)
                } else {
                    fcontext = fcontext.createConfigurationContext(config)
                }
            }
            return ApplicationLanguageHelper(fcontext, )
        }

        fun setSystemLocaleLegacy(config: Configuration, locale: Locale) {
            config.setLocales(LocaleList(locale))
        }

        fun setSystemLocale(config: Configuration, locale: Locale) {
            config.setLocales(LocaleList(locale))
        }
    }
}