package tech.inscripts.ins_armman.mMitra.utility

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.view.View
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.AttributedCharacterIterator.Attribute.LANGUAGE
import java.text.SimpleDateFormat
import java.util.*

public class utility {

     val COMMAN_PREF_NAME = "CommonPrefs"
     val Language = "language"



     fun setLocaleInPreference(locale: String, context: Context) {
        val prefs = context.getSharedPreferences(COMMAN_PREF_NAME, Activity.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(Language, locale)
        editor.commit()
    }

    fun getLanguagePreferance(context: Context): String {
        val prefs = context.getSharedPreferences(COMMAN_PREF_NAME, Activity.MODE_PRIVATE)
        return prefs.getString(Language, "")
    }

    /**
     * change the locale for the app.
     *
     * @param context
     * @param locale
     */
    fun setApplicationLocale(context: Context, locale: String) {
        try {
            setLocaleInPreference(locale, context)

            val res = context.applicationContext.resources
            // Change locale settings in the app.
            val dm = res.displayMetrics
            val conf = res.configuration
            val localeArray = locale.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (localeArray.size > 1) {
                conf.locale = Locale(localeArray[0], localeArray[1])
            } else {
                conf.locale = Locale(locale)
            }
            res.updateConfiguration(conf, dm)
        } catch (ex: Exception) {
        }

    }



    fun getCurrentDateTime():String
    {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US).format(Date())
    }


//
//    fun setBadgeCount(context: Context, icon: LayerDrawable, count: Int) {
//
//        val badge: BadgeDrawable
//
//        // Reuse drawable if possible
//        val reuse = icon.findDrawableByLayerId(
//            R.id.ic_badge
//        )
//        if (reuse != null && reuse is BadgeDrawable) {
//            badge = reuse as BadgeDrawable
//        } else {
//            badge = BadgeDrawable(context)
//        }
//
//        badge.setCount(count)
//        icon.mutate()
//        icon.setDrawableByLayerId(R.id.ic_badge, badge)
//    }

}