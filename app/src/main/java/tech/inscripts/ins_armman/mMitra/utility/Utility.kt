package tech.inscripts.ins_armman.mMitra.utility

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.drawable.LayerDrawable
import android.net.ConnectivityManager
import android.util.Base64
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import org.joda.time.*
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.GraphPoint
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseContract
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseManager
import tech.inscripts.ins_armman.mMitra.utility.Constants.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.charset.Charset
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Utility {
    var dateUtility = DateUtility()
    val obj_telephonyInfo = TelephonyInfo()
    var TAG = Utility::class.java.name
    val COMMAN_PREF_NAME = "CommonPrefs"
    val Language = "LANGUAGE"
    val mDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val mDateTimeFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    val mDateDisplayFormat: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")


    fun setLocaleInPreference(locale: String, context: Context) {
        val prefs = context.getSharedPreferences(COMMAN_PREF_NAME, Activity.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(Language, locale)
        editor.commit()
    }

    fun getLanguagePreferance(context: Context?): String {
        val prefs = context!!.getSharedPreferences(COMMAN_PREF_NAME, Activity.MODE_PRIVATE)
        return prefs.getString(Language, "")
    }

    /**
     * change the language for the app.
     *
     * @param context
     * @param locale
     */
    fun setApplicationLocale(context: Context, locale: String) {
        try {
            setLocaleInPreference(locale, context)

            var res: Resources = context.applicationContext.resources
            // Change locale settings in the app.
            var dm: DisplayMetrics = res.displayMetrics
            val conf: Configuration = res.configuration
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


    /**
     * @param view         View to animate
     * @param toVisibility Visibility at the end of animation
     * @param toAlpha      Alpha at the end of animation
     * @param duration     Animation duration in ms
     */
    fun animateView(view: View, toVisibility: Int, toAlpha: Float, duration: Int) {
        val show = toVisibility == View.VISIBLE
        if (show) {
            view.alpha = 0f
        }
        view.visibility = View.VISIBLE
        view.animate()
            .setDuration(duration.toLong())
            .alpha(if (show) toAlpha else 0F)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = toVisibility
                }
            })
    }

    fun hasInternetConnectivity(context: Context?): Boolean {
        var rc = false
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
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

    fun getDeviceImeiNumber(context: Context): ArrayList<String> {
        val telephonyInfo = obj_telephonyInfo.getInstance(context)
        val imeiArray = ArrayList<String>()
               imeiArray.add("869432026925037");
       /* if (telephonyInfo.isDualSIM()) {
            imeiArray.add(telephonyInfo.imsiSIM1)
            imeiArray.add(telephonyInfo.imsiSIM2)
        } else {
            imeiArray.add(telephonyInfo.imsiSIM1)
        }*/

        return imeiArray
    }

    fun getDatabase(): SQLiteDatabase {
        return DatabaseManager.getInstance().openDatabase()
    }

    fun generateUniqueId(): String {
        var a=System.currentTimeMillis()
        var b=UNIQUE_MEMBER_ID_SEPERATOR
        var c=getUserId()
        var d: String=""
        if(true)
            d=a.toString() +b +c
        return d
    }

    fun getUserId(): String {
        val cursor = getDatabase().rawQuery(
            "SELECT " + DatabaseContract.LoginTable.COLUMN_USER_ID
                    + " FROM " + DatabaseContract.LoginTable.TABLE_NAME, null
        )

        return if (cursor.moveToFirst())
            cursor.getString(cursor.getColumnIndex(DatabaseContract.LoginTable.COLUMN_USER_ID))
        else
            "0"
    }

    /**
     * This is the method which generates the Hash.
     * @param s = JsonResponse
     * @return hash in string format
     */
    fun mdFive(s: String): String {
        val MD5 = "MD5"
        try {
            // Create MD5 Hash
            val digest = MessageDigest
                .getInstance(MD5)
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2)
                    h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }

    fun getStringFromBitmap(bitmapPicture: Bitmap): String {
        return Base64.encodeToString(getImageByteArray(bitmapPicture), Base64.DEFAULT)
    }

    fun getImageByteArray(bitmap: Bitmap): ByteArray {
        val COMPRESSION_QUALITY = 100
        val byteArrayBitmapStream = ByteArrayOutputStream()
        bitmap.compress(
            Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
            byteArrayBitmapStream
        )
        return byteArrayBitmapStream.toByteArray()
    }

    fun setBadgeCount(context: Context, icon: LayerDrawable, count: Int) {

        val badge: BadgeDrawable

        // Reuse drawable if possible
        val reuse = icon.findDrawableByLayerId(
            R.id.ic_badge
        )
        if (reuse != null && reuse is BadgeDrawable) {
            badge = reuse
        } else {
            badge = BadgeDrawable(context)
        }

        badge.setCount(count)
        icon.mutate()
        icon.setDrawableByLayerId(R.id.ic_badge, badge)
    }

    fun getCurrentDateTime(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date())
    }

    fun getDaysBetweenTwoDates(dateOne: String, dateTwo: String): Int {
        return Days.daysBetween(DateTime(dateOne), DateTime(dateTwo)).days
    }

    fun getMonthForInt(num: Int): String {
        val cal = Calendar.getInstance()
        cal.set(Calendar.MONTH, num)
        val month_date = SimpleDateFormat("MMM", Locale.US)
        return month_date.format(cal.time)
    }

    fun getSamMamUsingMUAC(muac: Float, context: Context): String {
        var samMamStatus = ""
        if (muac != -1f && muac <= 11.5) {
            samMamStatus = context.getString(R.string.sam_status)
        } else if (muac > 11.5 && muac <= 12.5) {
            samMamStatus = context.getString(R.string.mam_status)
        }
        return samMamStatus
    }

    fun getAgeInMonths(year: Int, month: Int, day: Int): Int {
        val birthdate = LocalDate(year, month + 1, day)          //Birth date
        val now = LocalDate()                    //Today's date
        val period = Period(birthdate, now, PeriodType.months())
        return period.months
    }

    fun getAgeInMonths(dob: String): Int {
        val year = dateUtility.getYearFromDate(dob)
        val month = dateUtility.getMonthFromDate(dob)
        val day = dateUtility.getDayFromDate(dob)
        return getAgeInMonths(year, month, day)
    }

    fun getWeightCategory(weight: Float, graphPoint: GraphPoint): String {

        var weightType = WEIGHT_TYPE_NORMAL_WEIGHT

        val median = graphPoint.getMedian()
        val _2SD = graphPoint.get_2SD()
        val _3SD = graphPoint.get_3SD()

        if (weight > median) {
            //Log.d("****", "Weight:"+weight + "WeightCategory:"+AWWConstants.WEIGHT_TYPE_HIGH_WEIGHT);
            weightType = WEIGHT_TYPE_HIGH_WEIGHT
        } else if (weight >= _2SD && weight <= median) {
            weightType = WEIGHT_TYPE_NORMAL_WEIGHT
            //Log.d("****", "Weight:"+weight + "WeightCategory:"+AWWConstants.WEIGHT_TYPE_NORMAL_WEIGHT);
        } else if (weight >= _3SD && weight < _2SD) {
            weightType = WEIGHT_TYPE_LOW_WEIGHT
            //Log.d("****", "Weight:"+weight + "WeightCategory:"+AWWConstants.WEIGHT_TYPE_LOW_WEIGHT);
        } else if (weight < _3SD) {
            weightType = WEIGHT_TYPE_SEVERELY_LOW_WEIGHT
            //Log.d("****", "Weight:"+weight + "WeightCategory:"+AWWConstants.WEIGHT_TYPE_SEVERELY_LOW_WEIGHT);
        }

        return weightType
    }

    /**
     * this method is for child
     */
    /*fun getStaticZScorePoints(gender: String, context: Context): ArrayList<GraphPoint>? {
        var gender = gender
        if (gender.equals(null!!, ignoreCase = true) || gender.equals("", ignoreCase = true)) {
            gender = "M"
        }
        var fileName: String? = null
        if (gender.equals(MALE, ignoreCase = true) || gender.equals(
                "M",
                ignoreCase = true
            ) || gender.equals(GENDER_MALE, ignoreCase = true)
        ) {
            fileName = "wfa_boys_0_5_zscores.json"
        } else {
            fileName = "wfa_girls_0_5_zscores.json"
        }

        val jsonString =loadJSONFromAsset(context, fileName)
        val typeOfObjectsList = object : TypeToken<ArrayList<GraphPoint>>() {

        }.type
        return GsonBuilder().create().fromJson<ArrayList<GraphPoint>>(jsonString, typeOfObjectsList)
    }
*/
    fun loadJSONFromAsset(context: Context, jsonFileName: String): String? {
        var json: String? = null
        try {
            val inputStream = context.assets.open(jsonFileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }

    fun getIntForMonth(monthName: String, locale: Locale): Int {
        val simpleDateFormat = SimpleDateFormat("MMM", locale)
        try {
            val date = simpleDateFormat.parse(monthName)
            val cal = Calendar.getInstance()
            cal.time = date
            return cal.get(Calendar.MONTH)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return -1
    }

    fun hideKeyboard(context: Context, view: View) {
        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getVillageName(villageId: String): String {
        val cursor = getDatabase().rawQuery(
            "SELECT * FROM "
                    + DatabaseContract.VillageTable.TABLE_NAME
                    + " WHERE "
                    + DatabaseContract.VillageTable.COLUMN_VILLAGE_ID + " = ? ", arrayOf(villageId)
        )

        return if (cursor.moveToFirst()) cursor.getString(cursor.getColumnIndex(DatabaseContract.VillageTable.COLUMN_VILLAGE_NAME)) else villageId
    }

    fun getAppVersionName(context: Context): String {
        var pInfo: PackageInfo? = null
        try {
            pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return pInfo!!.versionName
    }
}