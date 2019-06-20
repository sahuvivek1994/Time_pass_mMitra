package tech.inscripts.ins_armman.mMitra.utility

import android.content.Context
import android.util.Log
import org.joda.time.*
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.Age
import tech.inscripts.ins_armman.mMitra.utility.Constants.*
import java.lang.IllegalArgumentException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateUtility {
    fun calculateDOBfromAge(ageYears : Int, ageMonths : Int): String? {
        var cal : Calendar = Calendar.getInstance()
        cal.add(Calendar.YEAR,-ageYears)
        cal.add(Calendar.MONTH,-ageMonths)
        var dateFormat = SimpleDateFormat(DATE_FORMAT)
        return dateFormat.format(cal.time)
    }

    fun calculateAgeFromDOB(_year : Int, _month : Int, _day : Int): Int {
        var cal = GregorianCalendar()
        val y: Int = cal.get(Calendar.YEAR)
        val m: Int=cal.get(Calendar.MONTH)
        val d: Int=cal.get(Calendar.DAY_OF_MONTH)
        cal.set(_year,_month,_day)
        var age: Int = y - cal.get(Calendar.YEAR)
        if((m < cal.get(Calendar.MONTH)) || (m == cal.get(Calendar.MONTH)) && (d < cal.get(Calendar.DAY_OF_MONTH))){
            --age
        }
        if (age < 0)
            throw IllegalArgumentException("Age < 0, Age can not be -ve. Please check. It could be happening due to past getBirthDate set in the device.")
        return age
    }

    fun getDateFromCalender(year : Int, monthOfYear : Int, dayOfMonth : Int) : String?{
        var calender= Calendar.getInstance()
        var myformat= DATE_FORMAT
        var sdFormat = SimpleDateFormat(myformat)
        calender.set(Calendar.YEAR,year)
        calender.set(Calendar.MONTH,monthOfYear)
        calender.set(Calendar.DAY_OF_MONTH,dayOfMonth)
        return sdFormat.format(calender.time)
    }

    fun calculateAgeFromDOB(dateString: String): Int {
        return calculateAgeFromDOB(getYearFromDate(dateString), getMonthFromDate(dateString), getDayFromDate(dateString))
    }
    fun getDayFromDate(dateString: String) : Int{
        val myFormat = SimpleDateFormat(DATE_FORMAT_DOB)
        var day = 0
        try {
            val date = myFormat.parse(dateString)
            val cal = Calendar.getInstance()
            cal.time = date
            day = cal.get(Calendar.DAY_OF_MONTH)

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return day
    }
    fun getMonthFromDate(dateString : String) :Int{
        var myformat = SimpleDateFormat(DATE_FORMAT_DOB)
        var month = 0
        try{
            var date : Date= myformat.parse(dateString)
            var cal = Calendar.getInstance()
            cal.time = date

            var year = cal.get(Calendar.YEAR)
            month= cal.get(Calendar.MONTH)

        }catch ( e : ParseException){
            e.printStackTrace()
        }
        return month
    }

    fun getYearFromDate(dateString: String) : Int{
        val myFormat = SimpleDateFormat(DATE_FORMAT_DOB)
        var year = 0

        try {
            val date = myFormat.parse(dateString)
            val cal = Calendar.getInstance()
            cal.time = date
            year = cal.get(Calendar.YEAR)

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return year
    }

    fun getMonthFromDate(dateString: String, format: String): Int {
        //SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        val myFormat = SimpleDateFormat(format)
        var month = 0

        try {
            val date = myFormat.parse(dateString)
            val cal = Calendar.getInstance()
            cal.time = date
            val year = cal.get(Calendar.YEAR)
            month = cal.get(Calendar.MONTH)

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return month
    }


    fun getDayFromDate(dateString: String, format: String): Int {
        //SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        val myFormat = SimpleDateFormat(format)
        var day = 0

        try {
            val date = myFormat.parse(dateString)
            val cal = Calendar.getInstance()
            cal.time = date
            day = cal.get(Calendar.DAY_OF_MONTH)

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return day
    }

    fun getYearFromDate(dateString: String, format: String): Int {
        //SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        val myFormat = SimpleDateFormat(format)
        var year = 0

        try {
            val date = myFormat.parse(dateString)
            val cal = Calendar.getInstance()
            cal.time = date
            year = cal.get(Calendar.YEAR)

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return year
    }


    fun convertCaldroidDateFormat(date: String): String {
        val originalFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        val targetFormat = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
        var targetDate: Date? = null
        try {
            targetDate = originalFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return targetFormat.format(targetDate)
    }


    fun convertCaldroidDateFormat(date: Date): String {
        val targetFormat = SimpleDateFormat(DATE_FORMAT, Locale.US)
        return targetFormat.format(date)
    }

    fun convertDateFormat(dateString: String?, targetFormat: String, originalFormat: String): String? {

        if (dateString == null) {
            return dateString
        }

        val format = SimpleDateFormat(originalFormat, Locale.US)
        var date: Date? = null
        try {
            date = format.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val convertedFormat = SimpleDateFormat(targetFormat, Locale.US)
        return convertedFormat.format(date)
    }


    fun convertCreatedONDateFormat(date: String): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val targetFormat = SimpleDateFormat(DATE_FORMAT)
        var targetDate: Date? = null
        try {
            targetDate = originalFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return targetFormat.format(targetDate)
    }

    fun getCurrentDate(): String {
        return getCurrentDate(DATE_FORMAT)
    }

    fun getCurrentDate(format: String): String {
        val mCurrentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat(format)
        return dateFormat.format(mCurrentDate)
    }

    fun getCurrentYear(): Int {
        val calendar = Calendar.getInstance()
        calendar.time
        return calendar.get(Calendar.YEAR)
    }

    fun getCurrentMonth(): Int {
        val calendar = Calendar.getInstance()
        calendar.time
        return calendar.get(Calendar.MONTH)
    }

    fun getLastMonth(): Int {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        calendar.time
        return calendar.get(Calendar.MONTH)
    }

    fun getLastMonthsYear(): Int {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        calendar.time
        return calendar.get(Calendar.YEAR)
    }

    fun convertStringToDate(dateString: String): Date? {
        val dateFormat = SimpleDateFormat(DATE_FORMAT_DOB)
        var date: Date? = null
        try {
            date = dateFormat.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date
    }

    fun convertStringToDate(dateString: String, inputDateFormat: String): Date? {
        val dateFormat = SimpleDateFormat(inputDateFormat)
        var date: Date? = null
        try {
            date = dateFormat.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date
    }

    fun getDateInStringFormat(calendar: Calendar): String {
        val monthFormat = SimpleDateFormat(DATE_FORMAT)
        return monthFormat.format(calendar.time)
    }

    fun getDateDiffInDays(startDate: Calendar, currentDate: Calendar): Int {
        /* for diff in month use this
         int diffMonths= currentDate.get(Calendar.MONTH) - startDate.get(Calendar.MONTH);
         for difference in dates use this
         int diffDays= currentDate.get(Calendar.DATE) - startDate.get(Calendar.MONTH);
         */
        val diffDays = Days.daysBetween(DateTime(startDate.time), DateTime(currentDate.time)).days
        Log.e("Attendance", "-------------getDateDiffInMonth start date -------- $startDate end date $currentDate")
        // Log.e("Attendance","-------------getDateDiffInMonth function Difference in months -------- "+diffMonths);
        Log.e("Attendance", "-------------getDateDiffInMonth function Difference in dates -------- $diffDays")
        /* Log.e("Attendance","-------------getDateDiffInMonth function Difference in dates -------- "+monthss);
        Log.e("AttendanceDays","-------------getDateDiffInDays function Difference in dates -------- "+daysdiffnew);
        */
        return diffDays
    }

    fun getDateInStringFormat(calendar: Calendar, format: String): String {
        val monthFormat = SimpleDateFormat(format)
        return monthFormat.format(calendar.time)
    }


    fun convertDateToString(date: Date, format: String): String {
        val df = SimpleDateFormat(format)
        return df.format(date)
    }

    fun convertStringFromToDateFormat(
        lastVisitedDate: String,
        dateFormat: String,
        date_format_yyyy_mm_dd: String
    ): String {
        val originalFormat = SimpleDateFormat(date_format_yyyy_mm_dd)
        val targetFormat = SimpleDateFormat(dateFormat)
        var targetDate: Date? = null
        try {
            targetDate = originalFormat.parse(lastVisitedDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return targetFormat.format(targetDate)
    }

    fun getAppLocale(language: String?): Locale {
        val locale: Locale
        if (language != null) {
            locale = Locale(language)
        } else {
            locale = Locale.getDefault()
        }

        return locale
    }

    fun getAgeinYearAndMonths(context: Context, dob: String): String {
        var ageText = ""
        var year = 0
        var months = 0
        var days = 0
        var yearString = context.getString(R.string.years)
        var monthString = context.getString(R.string.months)
        var dayString = context.getString(R.string.days)
        val dateofBirth = Calendar.getInstance()
        dateofBirth.time = convertStringToDate(dob)!!

        val currentDate = Calendar.getInstance()
        currentDate.time

        if (currentDate.get(Calendar.YEAR) >= dateofBirth.get(Calendar.YEAR)) {
            year =
                currentDate.get(Calendar.YEAR) - dateofBirth.get(Calendar.YEAR) //calculate year difference from current year and dob year
            if (currentDate.get(Calendar.MONTH) < dateofBirth.get(Calendar.MONTH)) {
                year--
                months = 12 - dateofBirth.get(Calendar.MONTH) + currentDate.get(Calendar.MONTH)
                if (currentDate.get(Calendar.DAY_OF_MONTH) < dateofBirth.get(Calendar.DAY_OF_MONTH)) {
                    months--
                }
            } else if (currentDate.get(Calendar.MONTH) == dateofBirth.get(Calendar.MONTH) && currentDate.get(Calendar.DAY_OF_MONTH) < dateofBirth.get(
                    Calendar.DAY_OF_MONTH
                )
            ) {
                year--
                months = 11
            } else {
                months = currentDate.get(Calendar.MONTH) - dateofBirth.get(Calendar.MONTH)

            }
        }
        //calculate days
        if (currentDate.get(Calendar.DAY_OF_MONTH) > dateofBirth.get(Calendar.DAY_OF_MONTH)) {
            days = currentDate.get(Calendar.DAY_OF_MONTH) - dateofBirth.get(Calendar.DAY_OF_MONTH)
        } else if (currentDate.get(Calendar.DAY_OF_MONTH) < dateofBirth.get(Calendar.DAY_OF_MONTH)) {
            val today = currentDate.get(Calendar.DAY_OF_MONTH)
            currentDate.add(Calendar.MONTH, -1)
            days = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH) - dateofBirth.get(Calendar.DAY_OF_MONTH) + today
        } else {
            days = 0
            if (months == 12) {
                year++
                months = 0
            }
        }
        if (year <= 1) {
            yearString = context.getString(R.string.year)
        }
        if (months <= 1) {
            monthString = context.getString(R.string.month)
        }
        if (days <= 1) {
            dayString = context.getString(R.string.day)
        }
        ageText = "$year $yearString $months $monthString $days $dayString"
        return ageText
    }

    fun getAge(dob: String): Age {
        val date = convertStringToDate(dob)
        return getAge(date)
    }

    fun getAge(dob: String, inputDateFormat: String): Age {
        val date = convertStringToDate(dob, inputDateFormat)
        return getAge(date)
    }

    fun getAge(year: Int, month: Int, day: Int): Age {
        val cal = GregorianCalendar()
        cal.set(year, month, day)
        val date = cal.time
        return getAge(date)
    }

    fun getAge(dob: Date?): Age {
        return Age(dob)
    }


    fun setTimePartAsZero(calendar: Calendar) {
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
    }

    fun getOneIncrementedValue(noOfDays: String): String {
        return (Integer.parseInt(DAYS_IN_3_YEARS) + 1).toString()
        // for 3 to 6 exclusive 3 value
    }

    fun getNoOfDaysInYearsPassed(years: Int): Int {
        return getNoOfDaysInYearsAndMonthsPassed(years, 0)
    }

    fun getNoOfDaysInMonthsPassed(months: Int): Int {
        return getNoOfDaysInYearsAndMonthsPassed(0, months)
    }

    fun getNoOfDaysInYearsAndMonthsPassed(years: Int, months: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, -years)
        calendar.add(Calendar.MONTH, -months)
        val pastDate = calendar.time
        return getNoOfDaysPassedFromDate(pastDate)
    }

    fun getNoOfDaysPassedFromDate(pastDate: Date): Int {
        val localDatePast = LocalDate.fromDateFields(pastDate)
        val localDateNow = LocalDate()
        val period = Period(localDatePast, localDateNow, PeriodType.days())
        return period.days
    }

    fun getNoOfDaysPassedFromDateToToDate(startDate: Date, endDate: Date): Int {
        val localStartDate = LocalDate.fromDateFields(startDate)
        val localEndDate = LocalDate.fromDateFields(endDate)
        val period = Period(localStartDate, localEndDate, PeriodType.days())
        return period.days
    }

    fun getSundaysBetweenDays(startDate: Date, endDate: Date): Int {
        var count = 0
        val localStartDate = LocalDate.fromDateFields(startDate)
        val localEndDate = LocalDate.fromDateFields(endDate)

        val result = ArrayList<LocalDate>()
        var date = localStartDate
        while (date.isBefore(localEndDate)) {
            val day = date.dayOfWeek
            // These could be passed in...
            if (day == DateTimeConstants.SUNDAY) {
                count = count + 1
            }
            date = date.plusDays(1)
        }

        return count
    }


    fun isThursday(calendar: Calendar): Boolean {
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return Calendar.THURSDAY == dayOfWeek
    }

}