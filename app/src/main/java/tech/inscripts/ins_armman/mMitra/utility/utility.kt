package tech.inscripts.ins_armman.mMitra.utility

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.net.ConnectivityManager
import android.view.View
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseManager
import java.text.SimpleDateFormat
import java.util.*

public class utility {

     val COMMAN_PREF_NAME = "CommonPrefs"
     val Language = "language"
    private var PRIVATE_MODE = 0


     fun setLocaleInPreference(locale: String, context: Context) {
//        var prefs = context.getSharedPreferences(COMMAN_PREF_NAME, Activity.MODE_PRIVATE)
//        var editor = prefs.edit()
//        editor.putString(Language, locale)
//        editor.commit()

        val sharedPref : SharedPreferences = context.getSharedPreferences(COMMAN_PREF_NAME,PRIVATE_MODE)
        val editor = sharedPref.edit()
        editor.putString(Language,locale)
        editor.apply()
    }

    fun getLanguagePreferance(context: Context): String {
        val prefs = context.getSharedPreferences(COMMAN_PREF_NAME, Activity.MODE_PRIVATE)
        return prefs.getString(Language, "")
    }

    /**
     * change the locale for the app.
     * @param context
     * @param locale
     */
    fun setApplicationLocale(context: Context, locale: String) {
        try {
            setLocaleInPreference(locale, context)

            var res = context.applicationContext.resources
            // Change locale settings in the app.
            var dm = res.displayMetrics
            var conf = res.configuration
            var localeArray = locale.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (localeArray.size > 1) {
                conf.locale = Locale(localeArray[0], localeArray[1])
            } else {
                conf.locale = Locale(locale)
            }
            res.updateConfiguration(conf, dm)
        } catch (ex: Exception) {
        }

    }

    fun getDatabase(): SQLiteDatabase {
        return DatabaseManager.getInstance().openDatabase()
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

    /**
     * @param view         View to animate
     * @param toVisibility Visibility at the end of animation
     * @param toAlpha      Alpha at the end of animation
     * @param duration     Animation duration in ms
     */

    fun animateView(view :View,toVisibility : Int,toAlpha : Float,duration : Int)
    {
        val show = toVisibility == View.VISIBLE
        if (show) {
            view.alpha = 0f
        }
        view.visibility = View.VISIBLE
        view.animate()
            .setDuration(duration.toLong())
            .alpha(toAlpha)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = toVisibility
                }
            })
    }

    fun hasInternetConnectivity(context: Context):Boolean
    {
        var rc = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm != null) {
            try {
                val activeNetworkInfo = cm.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isAvailable && activeNetworkInfo.isConnected) {
                    rc = true
                }
            } catch (e: Exception) {
            }

        }
        return rc

    }


}