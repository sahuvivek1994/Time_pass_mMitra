package tech.inscripts.ins_armman.mMitra.utility;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import tech.inscripts.ins_armman.mMitra.R;
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseContract;
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseManager;
import tech.inscripts.ins_armman.mMitra.data.GraphPoint;
import org.joda.time.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static tech.inscripts.ins_armman.mMitra.utility.Constants.*;
import static tech.inscripts.ins_armman.mMitra.utility.Keywords.*;


/**
 * Created by Amol on 10/10/17.
 */

public class Utility {
    public static String TAG = Utility.class.getName();
    public static final String COMMAN_PREF_NAME="CommonPrefs";
    public static final String LANGUAGE = "LANGUAGE";
    public static final SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    public static final SimpleDateFormat mDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    public static final SimpleDateFormat mDateDisplayFormat = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * change the locale for the app.
     *
     * @param context
     * @param locale
     */
    public static void setApplicationLocale(Context context, String locale) {
        try {
            setLocaleInPreference(locale, context);

            Resources res = context.getApplicationContext().getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            String[] localeArray = locale.split("_");
            if (localeArray.length > 1) {
                conf.locale = new Locale(localeArray[0], localeArray[1]);
            } else {
                conf.locale = new Locale(locale);
            }
            res.updateConfiguration(conf, dm);
        } catch (Exception ex) {
        }
    }

    private static void setLocaleInPreference(String locale, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(COMMAN_PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LANGUAGE, locale);
        editor.commit();
    }

    public static String getLanguagePreferance(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(COMMAN_PREF_NAME, Activity.MODE_PRIVATE);
        String language = prefs.getString(LANGUAGE, "");
        return language;
    }

    /**
     * @param view         View to animate
     * @param toVisibility Visibility at the end of animation
     * @param toAlpha      Alpha at the end of animation
     * @param duration     Animation duration in ms
     */
    public static void animateView(final View view, final int toVisibility, float toAlpha, int duration) {
        boolean show = toVisibility == View.VISIBLE;
        if (show) {
            view.setAlpha(0);
        }
        view.setVisibility(View.VISIBLE);
        view.animate()
                .setDuration(duration)
                .alpha(show ? toAlpha : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(toVisibility);
                    }
                });
    }

    public static boolean hasInternetConnectivity(Context context) {
        boolean rc = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            try {
                NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected()) {
                    rc = true;
                }
            } catch (Exception e) {
            }
        }
        return rc;
    }

    public static ArrayList<String> getDeviceImeiNumber(Context context) {
        TelephonyInfo telephonyInfo = TelephonyInfo.getInstance(context);
        ArrayList<String> imeiArray = new ArrayList<>();
//        imeiArray.add("867375022232910");
        if (telephonyInfo.isDualSIM()) {
            imeiArray.add(telephonyInfo.getImsiSIM1());
            imeiArray.add(telephonyInfo.getImsiSIM2());
        } else {
            imeiArray.add(telephonyInfo.getImsiSIM1());
        }

        return imeiArray;
    }

    public static SQLiteDatabase getDatabase() {
        return DatabaseManager.getInstance().openDatabase();
    }

    public static String generateUniqueId() {
        return System.currentTimeMillis() + UNIQUE_MEMBER_ID_SEPERATOR + getUserId();
    }

    public static String getUserId() {
       Cursor cursor = getDatabase().rawQuery("SELECT " + DatabaseContract.LoginTable.COLUMN_USER_ID
        + " FROM " + DatabaseContract.LoginTable.TABLE_NAME, null);

        if (cursor.moveToFirst()) return cursor.getString(cursor.getColumnIndex(DatabaseContract.LoginTable.COLUMN_USER_ID));
        else return "0";
    }

    /**
     * This is the method which generates the Hash.
     * @param s = JsonResponse
     * @return hash in string format
     */
    public static String mdFive(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getStringFromBitmap(Bitmap bitmapPicture) {
        return Base64.encodeToString(getImageByteArray(bitmapPicture), Base64.DEFAULT);
    }

    public static byte[] getImageByteArray(Bitmap bitmap){
        final int COMPRESSION_QUALITY = 100;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        return byteArrayBitmapStream.toByteArray();
    }

    public static void setBadgeCount(Context context, LayerDrawable icon, int count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(
                R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    public static String getCurrentDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
    }

    public static int getDaysBetweenTwoDates(String dateOne, String dateTwo) {
        return Days.daysBetween(new DateTime(dateOne), new DateTime(dateTwo)).getDays();
    }

    public static String getMonthForInt(int num) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, num);
        SimpleDateFormat month_date = new SimpleDateFormat("MMM", Locale.US);
        String month_name = month_date.format(cal.getTime());
        return month_name;
    }

    public static String getSamMamUsingMUAC(float muac, Context context) {
        String samMamStatus = "";
        if (muac != -1 && muac <= 11.5) {
            samMamStatus = context.getString(R.string.sam_status);
        } else if (muac > 11.5 && muac <= 12.5) {
            samMamStatus = context.getString(R.string.mam_status);
        }
        return samMamStatus;
    }

    public static int getAgeInMonths(int year, int month, int day) {
        LocalDate birthdate = new LocalDate(year, month + 1, day);          //Birth date
        LocalDate now = new LocalDate();                    //Today's date
        Period period = new Period(birthdate, now, PeriodType.months());
        int months = period.getMonths();
        return months;
    }

    public static int getAgeInMonths(String dob) {
        int year = DateUtility.getYearFromDate(dob);
        int month = DateUtility.getMonthFromDate(dob);
        int day = DateUtility.getDayFromDate(dob);
        int ageInMonths = getAgeInMonths(year, month, day);
        return ageInMonths;
    }

    public static String getWeightCategory(float weight, GraphPoint graphPoint) {

        String weightType = WEIGHT_TYPE_NORMAL_WEIGHT;

        Float median = graphPoint.getMedian();
        Float _2SD = graphPoint.get_2SD();
        Float _3SD = graphPoint.get_3SD();

        if (weight > median) {
            //Log.d("****", "Weight:"+weight + "WeightCategory:"+AWWConstants.WEIGHT_TYPE_HIGH_WEIGHT);
            weightType = WEIGHT_TYPE_HIGH_WEIGHT;
        } else if (weight >= _2SD && weight <= median) {
            weightType = WEIGHT_TYPE_NORMAL_WEIGHT;
            //Log.d("****", "Weight:"+weight + "WeightCategory:"+AWWConstants.WEIGHT_TYPE_NORMAL_WEIGHT);
        } else if (weight >= _3SD && weight < _2SD) {
            weightType = WEIGHT_TYPE_LOW_WEIGHT;
            //Log.d("****", "Weight:"+weight + "WeightCategory:"+AWWConstants.WEIGHT_TYPE_LOW_WEIGHT);
        } else if (weight < _3SD) {
            weightType = WEIGHT_TYPE_SEVERELY_LOW_WEIGHT;
            //Log.d("****", "Weight:"+weight + "WeightCategory:"+AWWConstants.WEIGHT_TYPE_SEVERELY_LOW_WEIGHT);
        }

        return weightType;
    }

    public static ArrayList<GraphPoint> getStaticZScorePoints(String gender, Context context) {
        if(gender.equalsIgnoreCase(null) || gender.equalsIgnoreCase(""))
        {
            gender = "M";
        }
        String fileName = null;
        if (gender.equalsIgnoreCase(MALE) || gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase(GENDER_MALE)) {
            fileName = "wfa_boys_0_5_zscores.json";
        } else {
            fileName = "wfa_girls_0_5_zscores.json";
        }

        String jsonString = Utility.loadJSONFromAsset(context, fileName);
        Type typeOfObjectsList = new TypeToken<ArrayList<GraphPoint>>() {
        }.getType();
        ArrayList<GraphPoint> graphPointArrayList = new GsonBuilder().create().fromJson(jsonString, typeOfObjectsList);
        return graphPointArrayList;
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static int getIntForMonth(String monthName, Locale locale) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM", locale);
        try {
            Date date = simpleDateFormat.parse(monthName);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int month = cal.get(Calendar.MONTH);
            return month;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return -1;
    }
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getVillageName(String villageId) {
        Cursor cursor = getDatabase().rawQuery("SELECT * FROM "
                + DatabaseContract.VillageTable.TABLE_NAME
                + " WHERE "
                + DatabaseContract.VillageTable.COLUMN_VILLAGE_ID + " = ? ", new String[]{villageId});

        if(cursor.moveToFirst()) return cursor.getString(cursor.getColumnIndex(DatabaseContract.VillageTable.COLUMN_VILLAGE_NAME));
        return villageId;
    }

    public static String getAppVersionName(Context context) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pInfo.versionName;
    }
}
