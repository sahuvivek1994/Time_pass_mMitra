package tech.inscripts.ins_armman.mMitra.utility;

import android.content.Context;
import android.util.Log;
import org.joda.time.*;
import tech.inscripts.ins_armman.mMitra.R;
import tech.inscripts.ins_armman.mMitra.data.Age;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

import static tech.inscripts.ins_armman.mMitra.utility.Constants.*;


/**
 * Created by lenovo on 28/11/17.
 */

public class DateUtility {
    public static String calculateDOBfromAge(int ageYears, int ageMonths) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -ageYears);
        cal.add(Calendar.MONTH, -ageMonths);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(cal.getTime());
    }

    public static int calculateAgeFromDOB(int _year, int _month, int _day) {
        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, age;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, _month, _day);
        age = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH)) || ((m == cal.get(Calendar.MONTH)) && (d < cal.get(Calendar.DAY_OF_MONTH)))) {
            --age;
        }
        if (age < 0)
            throw new IllegalArgumentException("Age < 0, Age can not be -ve. Please check. It could be happening due to past getBirthDate set in the device.");
        return age;
    }

    public static String getDateFromCalendar(int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        String myFormat = DATE_FORMAT;
        SimpleDateFormat sdformat = new SimpleDateFormat(myFormat);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return sdformat.format(calendar.getTime());
    }

    public static int calculateAgeFromDOB(String dateString) {
        int age = calculateAgeFromDOB(getYearFromDate(dateString), getMonthFromDate(dateString), getDayFromDate(dateString));
        return age;
    }

    public static int getDayFromDate(String dateString) {
        SimpleDateFormat myFormat = new SimpleDateFormat(DATE_FORMAT_DOB);
        int day = 0;

        try {
            Date date = myFormat.parse(dateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            day = cal.get(Calendar.DAY_OF_MONTH);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    public static int getMonthFromDate(String dateString) {
        SimpleDateFormat myFormat = new SimpleDateFormat(DATE_FORMAT_DOB);
        int month = 0;

        try {
            Date date = myFormat.parse(dateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return month;
    }

    public static int getYearFromDate(String dateString) {
        SimpleDateFormat myFormat = new SimpleDateFormat(DATE_FORMAT_DOB);
        int year = 0;

        try {
            Date date = myFormat.parse(dateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            year = cal.get(Calendar.YEAR);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return year;
    }

    public static int getMonthFromDate(String dateString, String format) {
        //SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat(format);
        int month = 0;

        try {
            Date date = myFormat.parse(dateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return month;
    }


    public static int getDayFromDate(String dateString, String format) {
        //SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat(format);
        int day = 0;

        try {
            Date date = myFormat.parse(dateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            day = cal.get(Calendar.DAY_OF_MONTH);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    public static int getYearFromDate(String dateString, String format) {
        //SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat(format);
        int year = 0;

        try {
            Date date = myFormat.parse(dateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            year = cal.get(Calendar.YEAR);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return year;
    }


    public static String convertCaldroidDateFormat(String date) {
        DateFormat originalFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        Date targetDate = null;
        try {
            targetDate = originalFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(targetDate);
        return formattedDate;
    }


    public static String convertCaldroidDateFormat(Date date) {
        DateFormat targetFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        String formattedDate = targetFormat.format(date);
        return formattedDate;
    }

    public static String convertDateFormat(String dateString, String targetFormat, String originalFormat) {

        if (dateString == null) {
            return dateString;
        }

        SimpleDateFormat format = new SimpleDateFormat(originalFormat, Locale.US);
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat convertedFormat = new SimpleDateFormat(targetFormat, Locale.US);
        String formattedDate = convertedFormat.format(date);
        return formattedDate;
    }


    public static String convertCreatedONDateFormat(String date) {
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat targetFormat = new SimpleDateFormat(DATE_FORMAT);
        Date targetDate = null;
        try {
            targetDate = originalFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(targetDate);
        return formattedDate;
    }

    public static String getCurrentDate() {
        return getCurrentDate(DATE_FORMAT);
    }

    public static String getCurrentDate(String format) {
        Date mCurrentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String currentDate = dateFormat.format(mCurrentDate);
        return currentDate;
    }

    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.getTime();
        int year = calendar.get(Calendar.YEAR);
        return year;
    }

    public static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.getTime();
        int month = calendar.get(Calendar.MONTH);
        return month;
    }

    public static int getLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.getTime();
        int month = calendar.get(Calendar.MONTH);
        return month;
    }

    public static int getLastMonthsYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.getTime();
        int year = calendar.get(Calendar.YEAR);
        return year;
    }

    public static Date convertStringToDate(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_DOB);
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date convertStringToDate(String dateString, String inputDateFormat) {
        DateFormat dateFormat = new SimpleDateFormat(inputDateFormat);
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

   /* public static String getDayName(Calendar calendar) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", getAppLocale(Utility.getLanguagePreferance()));

        String weekDay = dayFormat.format(calendar.getTime());
        return weekDay;
    }

    public static String getDate(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", getAppLocale(Utility.getLanguagePreferance()));
        String monthDate = dateFormat.format(calendar.getTime());
        return monthDate;
    }

    public static String getMonthName(Calendar calendar) {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", getAppLocale(Utility.getLanguagePreferance()));
        String monthName = monthFormat.format(calendar.getTime());
        return monthName;
    }*/

    public static String getDateInStringFormat(Calendar calendar) {
        SimpleDateFormat monthFormat = new SimpleDateFormat(DATE_FORMAT);
        String date = monthFormat.format(calendar.getTime());
        return date;
    }
  /*  public static int getDateDiffInMonth(Calendar startDate, Calendar currentDate){
     // for diff in month use this
        int diffMonths= currentDate.get(Calendar.MONTH) - startDate.get(Calendar.MONTH);
       // for difference in dates use this
        int diffDays= currentDate.get(Calendar.DATE) - startDate.get(Calendar.MONTH);
        int monthss = Months.monthsBetween(new DateTime(startDate.getTime()),new DateTime(currentDate.getTime())).getMonths();
       int daysdiffnew = Days.daysBetween(new DateTime(startDate.getTime()),new DateTime(currentDate.getTime())).getDays();
        Log.e("Attendance","-------------getDateDiffInMonth start date -------- "+startDate + " end date "+currentDate);
        Log.e("Attendance","-------------getDateDiffInMonth function Difference in months -------- "+diffMonths);
        Log.e("Attendance","-------------getDateDiffInMonth function Difference in dates -------- "+diffDays);
        Log.e("Attendance","-------------getDateDiffInMonth function Difference in dates -------- "+monthss);
        Log.e("AttendanceDays","-------------getDateDiffInDays function Difference in dates -------- "+daysdiffnew);

        return diffMonths;
    }*/

    public static int getDateDiffInDays(Calendar startDate, Calendar currentDate){
        // for diff in month use this
//        int diffMonths= currentDate.get(Calendar.MONTH) - startDate.get(Calendar.MONTH);
        // for difference in dates use this
//        int diffDays= currentDate.get(Calendar.DATE) - startDate.get(Calendar.MONTH);

        int diffDays = Days.daysBetween(new DateTime(startDate.getTime()),new DateTime(currentDate.getTime())).getDays();
        Log.e("Attendance","-------------getDateDiffInMonth start date -------- "+startDate + " end date "+currentDate);
//        Log.e("Attendance","-------------getDateDiffInMonth function Difference in months -------- "+diffMonths);
        Log.e("Attendance","-------------getDateDiffInMonth function Difference in dates -------- "+diffDays);
     /*   Log.e("Attendance","-------------getDateDiffInMonth function Difference in dates -------- "+monthss);
        Log.e("AttendanceDays","-------------getDateDiffInDays function Difference in dates -------- "+daysdiffnew);
*/
        return diffDays;
    }

    public static String getDateInStringFormat(Calendar calendar, String format) {
        SimpleDateFormat monthFormat = new SimpleDateFormat(format);
        String date = monthFormat.format(calendar.getTime());
        return date;
    }


    public static String convertDateToString(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        String reportDate = df.format(date);
        return reportDate;
    }

    public static String convertStringFromToDateFormat(String lastVisitedDate, String dateFormat, String date_format_yyyy_mm_dd) {
        DateFormat originalFormat = new SimpleDateFormat(date_format_yyyy_mm_dd);
        DateFormat targetFormat = new SimpleDateFormat(dateFormat);
        Date targetDate = null;
        try {
            targetDate = originalFormat.parse(lastVisitedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(targetDate);
        return formattedDate;
    }

    public static Locale getAppLocale(String language) {
        Locale locale;
        if (language != null) {
            locale = new Locale(language);
        } else {
            locale = Locale.getDefault();
        }

        return locale;
    }

    public static String getAgeinYearAndMonths(Context context, String dob) {
        String ageText = "";
        int year = 0, months = 0, days = 0;
        String yearString = context.getString(R.string.years);
        String monthString = context.getString(R.string.months);
        String dayString = context.getString(R.string.days);
        Calendar dateofBirth = Calendar.getInstance();
        dateofBirth.setTime(convertStringToDate(dob));

        Calendar currentDate = Calendar.getInstance();
        currentDate.getTime();

        if (currentDate.get(Calendar.YEAR) >= dateofBirth.get(Calendar.YEAR)) {
            year = currentDate.get(Calendar.YEAR) - dateofBirth.get(Calendar.YEAR); //calculate year difference from current year and dob year
            if (currentDate.get(Calendar.MONTH) < dateofBirth.get(Calendar.MONTH)) {
                year--;
                months = (12 - dateofBirth.get(Calendar.MONTH)) + currentDate.get(Calendar.MONTH);
                if (currentDate.get(Calendar.DAY_OF_MONTH) < dateofBirth.get(Calendar.DAY_OF_MONTH)) {
                    months--;
                }
            } else if (currentDate.get(Calendar.MONTH) == dateofBirth.get(Calendar.MONTH) && currentDate.get(Calendar.DAY_OF_MONTH) < dateofBirth.get(Calendar.DAY_OF_MONTH)) {
                year--;
                months = 11;
            } else {
                months = currentDate.get(Calendar.MONTH) - dateofBirth.get(Calendar.MONTH);

            }
        }
        //calculate days
        if (currentDate.get(Calendar.DAY_OF_MONTH) > dateofBirth.get(Calendar.DAY_OF_MONTH)) {
            days = currentDate.get(Calendar.DAY_OF_MONTH) - dateofBirth.get(Calendar.DAY_OF_MONTH);
        } else if (currentDate.get(Calendar.DAY_OF_MONTH) < dateofBirth.get(Calendar.DAY_OF_MONTH)) {
            int today = currentDate.get(Calendar.DAY_OF_MONTH);
            currentDate.add(Calendar.MONTH, -1);
            days = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH) - dateofBirth.get(Calendar.DAY_OF_MONTH) + today;
        } else {
            days = 0;
            if (months == 12) {
                year++;
                months = 0;
            }
        }
        if (year <= 1) {
            yearString = context.getString(R.string.year);
        }
        if (months <= 1) {
            monthString = context.getString(R.string.month);
        }
        if (days <= 1) {
            dayString = context.getString(R.string.day);
        }
        ageText = year + " " + yearString + " " + months + " " + monthString + " " + days + " " + dayString;
        return ageText;
    }

    public static Age getAge(String dob){
        Date date = convertStringToDate(dob);
        return getAge(date);
    }

    public static Age getAge(String dob, String inputDateFormat){
        Date date = convertStringToDate(dob, inputDateFormat);
        return getAge(date);
    }

    public static Age getAge(int year, int month, int day){
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(year, month, day);
        Date date = cal.getTime();
        return getAge(date);
    }

    public static Age getAge(Date dob){
        Age age = new Age(dob);
        return age;
    }



    public static void setTimePartAsZero(Calendar calendar){
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
    }

    public static String getOneIncrementedValue(String noOfDays) {
        return String.valueOf(Integer.parseInt(DAYS_IN_3_YEARS) + 1);
        // for 3 to 6 exclusive 3 value
    }

    public static int getNoOfDaysInYearsPassed(int years){
        return getNoOfDaysInYearsAndMonthsPassed(years, 0);
    }

    public static int getNoOfDaysInMonthsPassed(int months){
        return getNoOfDaysInYearsAndMonthsPassed(0, months);
    }

    public static int getNoOfDaysInYearsAndMonthsPassed(int years, int months){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -years);
        calendar.add(Calendar.MONTH, -months);
        Date pastDate = calendar.getTime();
        return getNoOfDaysPassedFromDate(pastDate);
    }

    public static int getNoOfDaysPassedFromDate(Date pastDate){
        LocalDate localDatePast = LocalDate.fromDateFields(pastDate);
        LocalDate localDateNow = new LocalDate();
        Period period = new Period(localDatePast, localDateNow, PeriodType.days());
        return period.getDays();
    }

    public static int getNoOfDaysPassedFromDateToToDate(Date startDate, Date endDate){
        LocalDate localStartDate = LocalDate.fromDateFields(startDate);
        LocalDate localEndDate  = LocalDate.fromDateFields(endDate);
        Period period = new Period(localStartDate, localEndDate, PeriodType.days());
        return period.getDays();
    }

    public static int getSundaysBetweenDays(Date startDate, Date endDate){
        int count=0;
        LocalDate localStartDate = LocalDate.fromDateFields(startDate);
        LocalDate localEndDate  = LocalDate.fromDateFields(endDate);

        List<LocalDate> result = new ArrayList<LocalDate>();
        for (LocalDate date = localStartDate;
             date.isBefore(localEndDate);
             date = date.plusDays(1))
        {
            int day = date.getDayOfWeek();
            // These could be passed in...
            if (day == DateTimeConstants.SUNDAY)
            {
                count = count +1;
            }
        }

        return count;
    }


    public static boolean isThursday(Calendar calendar){
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return Calendar.THURSDAY == dayOfWeek;
    }
}
