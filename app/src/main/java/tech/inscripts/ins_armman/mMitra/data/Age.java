package tech.inscripts.ins_armman.mMitra.data;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import tech.inscripts.ins_armman.mMitra.utility.DateUtility;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static tech.inscripts.ins_armman.mMitra.utility.DateUtility.convertStringToDate;


/**
 * Created by lenovo on 28/11/17.
 */

public final class Age {

    private final String dob;
    private String mDateFormat;
    private final Period period;

    public Age(String dob, String inputDateFormat) {
        this(getPeriod(convertStringToDate(dob, inputDateFormat)), dob);
        this.mDateFormat = inputDateFormat;
    }

    public Age(String dob) {
        this(getPeriod(convertStringToDate(dob)), dob);
    }

    public Age(int year, int month, int day) {
        this(new GregorianCalendar(year, month, day).getTime());
    }

    public Age(Date dob) {
        this(getPeriod(dob), DateUtility.convertCaldroidDateFormat(dob));
    }

    public Age(Period period, String dob) {
        this.period = period;
        this.dob = dob;
    }

    public int getYears() {
        return period.getYears();
    }

    public int getMonths() {
        return period.getMonths();
    }

    public int getDays() {
        return period.getDays();
    }

    public int getAgeInDays(){
        return period.toStandardDays().getDays();
    }

    public String getDob() {
        return dob;
    }

    public Date getBirthDate() {
        return convertStringToDate(dob, mDateFormat);
    }


    public boolean isValidAge() {
        Calendar calendar = Calendar.getInstance();
        DateUtility.setTimePartAsZero(calendar);
        Date now = calendar.getTime();
        return !getBirthDate().after(now);
    }

    private static Period getPeriod(Date dob) {
        LocalDate localDateDob = LocalDate.fromDateFields(dob);
        LocalDate localDateNow = new LocalDate();
        Period period = new Period(localDateDob, localDateNow, PeriodType.yearMonthDay());
        return period;
    }

    @Override
    public String toString() {
        return "Age{ years=" + getYears() +
                ", months=" + getMonths() +
                ", days=" + getDays() +
                '}';
    }
}
