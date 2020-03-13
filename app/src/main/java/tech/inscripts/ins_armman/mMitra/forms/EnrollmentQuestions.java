package tech.inscripts.ins_armman.mMitra.forms;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import bsh.EvalError;
import bsh.Interpreter;
import tech.inscripts.ins_armman.mMitra.R;
import tech.inscripts.ins_armman.mMitra.displayform.displayForm;
import tech.inscripts.ins_armman.mMitra.homeactivity.MainActivity;
import tech.inscripts.ins_armman.mMitra.utility.Utility;

import static tech.inscripts.ins_armman.mMitra.utility.Constants.FORM_ID;
import static tech.inscripts.ins_armman.mMitra.utility.Constants.UNIQUE_ID;
import static tech.inscripts.ins_armman.mMitra.utility.Constants.delimeter;
import static tech.inscripts.ins_armman.mMitra.Keywords.ADDRESS;
import static tech.inscripts.ins_armman.mMitra.Keywords.AROGYASAKHI_ALTERNATE_NO;
import static tech.inscripts.ins_armman.mMitra.Keywords.AROGYASAKHI_MOB;
import static tech.inscripts.ins_armman.mMitra.Keywords.AROGYASAKHI_NAME;
import static tech.inscripts.ins_armman.mMitra.Keywords.EDUCATION;
import static tech.inscripts.ins_armman.mMitra.Keywords.LMP_DATE_KEYWORD;
import static tech.inscripts.ins_armman.mMitra.Keywords.MARITAL_STATUS;
import static tech.inscripts.ins_armman.mMitra.Keywords.NAME_KEYWORD;
import static tech.inscripts.ins_armman.mMitra.Keywords.PHC_NAME;
import static tech.inscripts.ins_armman.mMitra.Keywords.PHONE_KEYWORD;
import static tech.inscripts.ins_armman.mMitra.Keywords.VILLAGE_NAME;
import static tech.inscripts.ins_armman.mMitra.Keywords.WOMAN_AGE;
import static tech.inscripts.ins_armman.mMitra.Keywords.WOMAN_DOB;
import static tech.inscripts.ins_armman.mMitra.Keywords.WOMAN_MOB_NO;
import static tech.inscripts.ins_armman.mMitra.Keywords.WOMAN_NAME;
import static tech.inscripts.ins_armman.mMitra.Keywords.*;

/**
 * This class is used to display questions of registration forms dynamically from the localDB.
 * After entering all dataSource the answer's are saved in local dbHelper and based on lmp date she would be directed to either ANC forms or pnc.
 */
public class EnrollmentQuestions extends AppCompatActivity {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final String TAG = "EnrollmentQuestions";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    // directory firstName to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    public static String expec_date, woman_gest_age, current_reg, Server_expected_date;
    private static Utility utility = new Utility();
    public String mAppLanguage = "eng";
    Utility utilityObj = new Utility();
    List<String> chechboxlist = new ArrayList<>();
    int counter;
    //    DBHelper dbhelper;
    FrameLayout Frame;
    Button next, previous;
    HashMap<String, String> storeEnteredData = new HashMap<>();
    LinearLayout.LayoutParams lp1, lp, lpHorizontal, segmentedBtnLp, lparams, layoutParamQuestion;
    ScrollView.LayoutParams sp1;
    LinearLayout ll, ll_sub, ll_4layout, ll_4layout_sub;
    ScrollView scroll, scroll_temp;
    Context ctx = this;
    ArrayList<Integer> scrollId = new ArrayList<>(); // this arraylist is used to store scrollid's of the scroll view
    HashMap<Integer, LinearLayout> parentLayoutchild = new HashMap<>();
    int scrollcounter = 0;
    int textId = 500, edittextID = 600, radiogroup = 800, radio = 400, customespinner = 10, parentid = 1, checkbox = 700, buttonId = 900;
    int skippedVisitID;
    int formid;
    String autoGeneratedECId;
    String formID, editEcStatus;
    String view_check = "";
    String msg, valCondition;
    DecimalFormat decimalFormat = new DecimalFormat("#.00");
    Spinner spinner1, spinner, prefLangSpinner;
    HashMap<String, String> womendetails = new HashMap<String, String>(); // this hashmap is used to insert woman's details in DisplayRegList table
    HashMap<String, String> gplaList = new HashMap<>(4);
    TreeMap<Integer, String> womenanswer = new TreeMap<Integer, String>(); // this Treemap is used to insert woman's answer's in AnswerEntered table
    TreeMap<String, String> validationlist = new TreeMap<String, String>(); // this treemap is used to check whether compulsory question is filled or not.
    TreeMap<String, Integer> NextButtonvalidationlist = new TreeMap<String, Integer>(); // this treemap is used to check how many compulsory questions are there on one single page
    TreeMap<Integer, Integer> runtimevalidationlist = new TreeMap<Integer, Integer>();// this treemap is used to store all scroll id's associated for edittext
    //List<Visit>enrollmentList =null; //this list is used to get questions for enrollment form
    ArrayList<String> ashaList; // this list is used to get villages from localDB
    DateTimeFormatter dateTimeFormat;
    ProgressBar progress;
    String defaultdate;
    String gestationalDate;
    SimpleDateFormat formatter;
    Date SystemDate, selectedDate;
    String Name,womanName="",womanUniqueId,directRegName="",directRegPhone="";
    ArrayList<String> directRegDetails= new ArrayList<>();
    Bundle b;
    int flag,NormalRegFlag;
    String women_in_highrisk;
    Integer mamtaCardPresent;
    Bitmap photo;
    String PreviousQuesAnswertype, pageCountText;
    ArrayAdapter<String> spinnerArrayAdapter;
    ImageView iv;
    byte[] byteArray;
    int layoutcounter = 0;
    JSONObject obj;
    HashMap<String, String> dependantLayout = new HashMap<>();
    HashMap<String, String> dependantKeywordPresent = new HashMap<>();
    //List<ListClass>enrollmentList =null;
    List<Visit> enrollmentList = null; // is used to retrieve questions for anc form
    //List<Visit>optionList=null; // is used to check whether the question contains dependant question
    List<Visit> optionList = new ArrayList<>(); // is used to check whether the question contains dependant question
    List<Visit> dependantList = null; // is used to retireve dependant question for the selected button
    TreeMap<Integer, String> Backup_answerTyped1 = new TreeMap<Integer, String>(); // is used to insert the entered dataSource into localDB
    String Optionlanguage;
    HashMap<String, String> layoutids = new HashMap<>();
    ConcurrentHashMap<String, String> dependantquestion = new ConcurrentHashMap<>();
    boolean lmpvalid = true;
    String registered_asha_mobno;
    List<String> counclingMsgQstnKeywords = new ArrayList<>();
    HashMap<String, String> highrisklist = new HashMap<>(); // this hashmap is used to store entered high risk values in local DB
    HashMap<String, String> referrallist = new HashMap<>(); // this hashmap is used to store entered referral values in local DB
    HashMap<String, String> patientvisitlist = new HashMap<>(); // this hashmap is used to store entered referral values in local DB
    HashMap<String, String> highriskranges = new HashMap<String, String>(); // this hashmap is used to stored multi level conditions for high risk
    HashMap<String, String> ConditionLists = new HashMap<String, String>(); // this hashmap is used to stored multi level conditions for high risk,counselling,referral
    String visitId, villageId, AshaId, womentrimester;
    TreeMap<String, List> Backup_customespinner = new TreeMap<String, List>();
    List<Visits> StoredHighRiskRanges = null;
    List<Visits> StoredCounsellingRanges = null;
    List<Visits> StoredPatientVisitSummaryRanges = null;
    String PregnancyStatus, womenMigrant;
    Double range_min;
    Double range_max;
    String range_lang;
    Double length_min;
    Double length_max;
    String length_lang;
    boolean HighriskStatus = false;
    boolean SaveFormStatus = false;
    String serverDate, ashaName, beneficiaryID;
    List<String> tempdependantStore = new ArrayList<>();
    HashMap<String, List> MainQuestempstoredependant = new HashMap<>();
    List<String> removeDependentQuestion = new ArrayList<>();
    HashMap<String, String> womenpreviousEcdetails = new HashMap<>();
    InputMethodManager inputMethodManager;
    String[] gplaKeywordArray = {"woman_gravida", "woman_parity", "woman_live_child", "woman_abortion"};
    DateFormat timeFormat;
    Calendar calendar;
    HashMap<Integer, Label> labelKeywirdDetails = new HashMap<>();
    ProgressDialog progressDialog;
    TextView textViewTotalPgCount;
    int dueVisitID, MamtaCardStatus, deliveryStatus;
    SimpleDateFormat YMDFormat, DMYFormat;
    TimePickerDialog mTimePicker;
    InputFilter filter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                if (Character.isWhitespace(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        }

    };
    GradientDrawable drawableMainQstn, drawableDependentQstn;
    private Locale myLocale;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat serverdateFormatter;
    private String serverdefaultdateFormatter;
    private SimpleDateFormat dateFormatter1;
    private SimpleDateFormat dateTimeFormatter1;
    private Uri fileUri; // file url to store image/video
    private int mMigrantStatus = 0;
    private String uniqueId = "";
    private QuestionInteractor questionInteractor;
    private HashMap<String, String> hashMapUserDetails = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);

        System.out.println("created on : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date()));

        new DisplayQuestions().execute();
    }

    public void labelCalculation(TextView labelTextView, Label label) throws ParseException {

        String textOnLabel = label.getTextOnLabel();

        labelTextView.setGravity(Gravity.LEFT);

        String allCalculations = label.getCalculation();

        String[] split_calculations = allCalculations.split("((\\,))");

        for (String expression : split_calculations) {
            String[] split_str = expression.split("((\\=)|(?<=\\-)|(?=\\-)|(\\()|(\\))|(?<=\\+)|(?=\\+)|(?<=\\*)|(?=\\*)|(?<=\\/)|(?=\\/))");

            String operand1, operator, operand2, result = null;
            LocalDate date1 = null, date2 = null;

            switch (split_str[1].trim()) {
                case "year":
                case "month":
                case "week":
                case "day":
                case "hour":
                case "minute":
                case "second":

                    operand1 = split_str[2].trim();
                    operator = split_str[3].trim();
                    operand2 = split_str[4].trim();

                    if (!(operand1.equals("todays_date"))) {
                        if (womendetails.containsKey(operand1) && womendetails.containsKey(operand2)) {
                            date1 = new LocalDate(dateTimeFormat.parseDateTime(womendetails.get(operand1)));
                            date2 = new LocalDate(dateTimeFormat.parseDateTime(womendetails.get(operand2)));
                        }
                    } else {
                        if (womendetails.containsKey(operand2)) {
                            date1 = new LocalDate();
                            date2 = new LocalDate(dateTimeFormat.parseDateTime(womendetails.get(operand2)));
                        }
                    }

                    if (date1 != null && date2 != null && operator.equals("-")) {
                        switch (split_str[1].trim()) {
                            case "year":
                                result = (String.valueOf(Years.yearsBetween(date2, date1).getYears()));
                                break;
                            case "month":
                                result = (String.valueOf(Months.monthsBetween(date2, date1).getMonths()));
                                break;
                            case "week":
                                result = (String.valueOf(Weeks.weeksBetween(date2, date1).getWeeks()));
                                break;
                            case "day":
                                result = (String.valueOf(Days.daysBetween(date2, date1).getDays()));
                                break;
                            case "hour":
                                result = (String.valueOf(Hours.hoursBetween(date2, date1).getHours()));
                                break;
                            case "minute":
                                result = (String.valueOf(Minutes.minutesBetween(date2, date1).getMinutes()));
                                break;
                            case "second":
                                result = (String.valueOf(Seconds.secondsBetween(date2, date1).getSeconds()));
                                break;
                        }
                    }

                    if (result != null) {
                        womendetails.put(split_str[0].trim(), result);
                        textOnLabel = textOnLabel.replace(split_str[0].trim(), result);
                    } else
                        textOnLabel = textOnLabel.replace(split_str[0].trim(), getString(R.string.no_data));

                    break;

               /* case "date":

                    if (womendetails.containsKey(split_str[2].trim())) {
                        String date_cal = expression.substring(expression.indexOf("(") + 1, expression.length() - 1);

                        date_cal = date_cal.replace(split_str[2].trim(), womendetails.get(split_str[2].trim()));

                        result = String.valueOf(serverdateFormatter.format(new StringToTime(date_cal)));

                        if (result != null) {
                            String dt = dateFormatter.format(new StringToTime(date_cal));
                            womendetails.put(split_str[0].trim(), dt);
                            textOnLabel = textOnLabel.replace(split_str[0].trim(), dt);
                        } else
                            textOnLabel = textOnLabel.replace(split_str[0].trim(), getString(R.string.no_data));
                    } else
                        textOnLabel = textOnLabel.replace(split_str[0].trim(), getString(R.string.no_data));

                    break;
*/
                case "todays_date":

                    result = serverdefaultdateFormatter;


                    if (result != null) {
                        womendetails.put(split_str[0].trim(), defaultdate);
                        textOnLabel = textOnLabel.replace(split_str[0].trim(), defaultdate);
                    }

                    break;

                case "current_time":

//                    calendar = Calendar.getInstance();
                    result = timeFormat.format(Calendar.getInstance().getTime());

                    if (result != null) {
                        womendetails.put(split_str[0].trim(), result);
                        textOnLabel = textOnLabel.replace(split_str[0].trim(), result);
                    }

                    break;

                default:

                    try {
                        Interpreter interpreter = new Interpreter();

                        for (int z = 0; z < split_str.length; z++) {
                            if (womendetails.containsKey(split_str[z].trim())) {
                                interpreter.set(split_str[z].trim(), Double.parseDouble(womendetails.get(split_str[z].trim())));
                            }
                        }
                        result = String.valueOf(interpreter.eval(expression));

                        if (!result.equals("true") && !result.equals("false")) {
                            result = decimalFormat.format(Double.parseDouble(result));
                            textOnLabel = textOnLabel.replace(split_str[0].trim(), result);
                        } else
                            textOnLabel = textOnLabel.replace(split_str[0].trim(), getString(R.string.no_data));
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }

                    break;

            }

            labelTextView.setText(textOnLabel);

            if (result != null) {
                edittextID++;
                womendetails.put(split_str[0].trim(), result);
                womenanswer.put(edittextID, "" + formID + delimeter + labelTextView.getId() + delimeter + split_str[0].trim() + delimeter + result);
            }

        }
    }

    /**
     * This method is used to initialize variables,dbhelper class,dynamic layouts on create
     */
    public void init() {
        try {


            progress = (ProgressBar) findViewById(R.id.progressBar1);

            Frame = (FrameLayout) findViewById(R.id.frame);
            next = (Button) findViewById(R.id.btnNext);
            previous = (Button) findViewById(R.id.btnpre);

            defaultdate = new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date());
            dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            serverdateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            serverdefaultdateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
            dateFormatter1 = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            dateTimeFormatter1 = new SimpleDateFormat("hh:mm a", Locale.US);
            dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
            timeFormat = new SimpleDateFormat("hh:mm:ss", Locale.US);

            YMDFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            DMYFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

            lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            sp1 = new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 10, 10, 10);
            lp.gravity = Gravity.CENTER_HORIZONTAL;

            layoutParamQuestion = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamQuestion.setMargins(0, (int) getResources().getDimension(R.dimen.questions_top_bottom_margin), 0, 0);

            segmentedBtnLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 3);
//            segmentedBtnLp.setMargins(10, 10, 10, 10);
            segmentedBtnLp.gravity = Gravity.CENTER_HORIZONTAL;

            lparams = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.MATCH_PARENT);
            lparams.weight = 1.0f;

            lpHorizontal = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f);
//            lpHorizontal.setMargins(10, 0, 0, 0);
            lpHorizontal.gravity = Gravity.CENTER_VERTICAL;

            formatter = new SimpleDateFormat("dd-MM-yyyy");
            gestationalDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

            SystemDate = formatter.parse(defaultdate);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * This method is used to check the validations before going to next page.
     */
    public void NextButtonValidations() {

        System.out.println(" scrollcounter " + scrollcounter);

        try {
            int counter = 0;
            int totalpagecondition = 0;
            Boolean isCompulsoryQstnInFocus = false;
            int pageno = scrollId.get(scrollcounter);

            /**
             * before going to next page check if validations field present on that page is filled or not.
             * hashmap iterator is used for this purpose to check how many validations are their on single page.
             */
            Iterator<Map.Entry<String, Integer>> itr2 = NextButtonvalidationlist.entrySet().iterator();
            while (itr2.hasNext()) {
                Map.Entry<String, Integer> entry = itr2.next();
                entry.getKey();
                entry.getValue();

                if (entry.getValue() != null) {

                    if (entry.getValue() == pageno) {
                        totalpagecondition++; // this parameter is used to store total validations on the page
                        /**
                         * validationlist is a TreeMap where questions which are compulsory its's keyword and answer is stored.
                         * if a specific answer is not stored for that keyword that means question is not answered by the user.
                         */
                        if (validationlist.get(entry.getKey()) != null && validationlist.get(entry.getKey()).length() > 0) {
                            //System.out.println("inside if counter");
                            counter++;
                        } else {

                            if (!isCompulsoryQstnInFocus) {
                                LinearLayout ll4_temp = (LinearLayout) scroll_temp.getChildAt(0);
                                for (int i = 0; i < ll4_temp.getChildCount(); i++) {
                                    LinearLayout ll_temp = (LinearLayout) ll4_temp.getChildAt(i);
                                    String keyword = (String) ll_temp.getTag();
                                    System.out.println("valdiation keyword: " + keyword + " validationlist value: " + validationlist.get(keyword));
                                    if (NextButtonvalidationlist.containsKey(keyword) && validationlist.get(keyword).length() == 0) {
                                        scroll_temp.scrollTo(0, (int) ll_temp.getY());
                                        isCompulsoryQstnInFocus = true;

                                        ValueAnimator shakeAnim = ObjectAnimator.ofFloat(ll_temp, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0);
                                        shakeAnim.setDuration(1000);
                                        shakeAnim.start();

                                        if (ll_temp.getChildAt(1) instanceof EditText) {

                                            EditText requiredField = (EditText) ll_temp.getChildAt(1);

                                            if (requiredField.requestFocus())
                                                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                                            requiredField.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));

                                        }

                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("Counter part***" + counter);
            System.out.println("totalpagecondition***" + totalpagecondition);


            /**
             * this if condition is used to check whether total compulsory question on that page is answered or not.
             * if totalpagecondition matches with counter no. that means all the complusory questions are answered.
             */
            if (totalpagecondition == counter) {
                previous.setVisibility(View.VISIBLE);

                scroll_temp = (ScrollView) Frame.findViewById(scrollId.get(scrollcounter));
                scroll_temp.setVisibility(View.INVISIBLE);

                scrollcounter++;
                parentid++;


                /**
                 * this if is used to check whether last layout for the question is reached or not.
                 * scrollcounter gets updated after clicking on next button
                 * if scrollcounter values goes above Total layouts textViewCount(c) then saveform() is called.
                 */
                if (scrollcounter > layoutcounter - 1) {
                    scrollcounter = layoutcounter - 1;
                    scroll_temp = (ScrollView) Frame.findViewById(Integer.parseInt(String.valueOf(scrollId.get(scrollcounter))));
                    scroll_temp.setVisibility(View.VISIBLE);
                    saveform();
//                    ImportantNote_Dialog("Not_Valid");

                } else {
                    scroll_temp = (ScrollView) Frame.findViewById(Integer.parseInt(String.valueOf(scrollId.get(scrollcounter))));

                    if (labelKeywirdDetails.containsKey(scroll_temp.getId())) {

                        TextView labelTextView = (TextView) ((LinearLayout) ((LinearLayout) scroll_temp.getChildAt(0)).getChildAt(0)).getChildAt(0);

                        labelCalculation(labelTextView, labelKeywirdDetails.get(scroll_temp.getId()));

                    }
                    scroll_temp.setVisibility(View.VISIBLE);

                    textViewTotalPgCount.setText((scrollcounter + 1) + pageCountText);
                    progress.setProgress(scrollcounter + 1);

                }


                if (scrollcounter == layoutcounter - 1) {
                    next.setVisibility(View.VISIBLE);

                }


            } else {
                Toast.makeText(getApplicationContext(), EnrollmentQuestions.this.getString(R.string.Toast_msg_for_compulsory), Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method is used to check the validations before going to previous page.
     */
    public void PreviousButtonValidation() {
        try {
            next.setVisibility(View.VISIBLE);

            if (scrollcounter > 0) {
                scroll_temp = (ScrollView) Frame.findViewById(Integer.parseInt(String.valueOf(scrollId.get(scrollcounter))));
                scroll_temp.setVisibility(View.INVISIBLE);
                scrollcounter--;
                if (scrollcounter == 0) previous.setVisibility(View.GONE);
                parentid--;
                scroll_temp = (ScrollView) Frame.findViewById(Integer.parseInt(String.valueOf(scrollId.get(scrollcounter))));
                scroll_temp.setVisibility(View.VISIBLE);

                progress.setProgress(scrollcounter + 1);
                textViewTotalPgCount.setText((scrollcounter + 1) + pageCountText);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to save the entered dataSource by the user in localDB
     */
    public void saveform() {

        System.out.println("EnrollmentQuestions.saveform");

        try {

            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder
                    .setTitle(EnrollmentQuestions.this.getString(R.string.save_form))
                    .setMessage(EnrollmentQuestions.this.getString(R.string.save_form_message))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(EnrollmentQuestions.this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //finish();

                                uniqueId = questionInteractor.saveRegistrationDetails(womanUniqueId,womendetails.get(WOMAN_NAME), womendetails.get(WOMAN_MOB_NO)
                                        , womendetails.get(LMP_DATE_KEYWORD), womendetails.get(ADDRESS), womendetails.get(WOMAN_AGE)
                                        , womendetails.get(EDUCATION), womendetails.get(MARITAL_STATUS), photo, 1, womendetails.get(WOMAN_DOB));
                            /**
                             * Saving the Enrollment form status as complete and its upload status as 0 i.e not uploaded to the server
                             */
                            int referenceId = questionInteractor.saveFilledFormStatus(uniqueId, Integer.parseInt(formID), 1, 0, utilityObj.getCurrentDateTime());

                            questionInteractor.saveQuestionAnswers(womendetails, referenceId, uniqueId, Integer.parseInt(formID), utilityObj.getCurrentDateTime());


                            Toast.makeText(getApplicationContext(), EnrollmentQuestions.this.getString(R.string.Toast_msg_for_formsavesuccessfully), Toast.LENGTH_LONG).show();
                            if(NormalRegFlag==100) {
                                Intent intent = new Intent(EnrollmentQuestions.this, displayForm.class);
                                intent.putExtra(UNIQUE_ID, uniqueId);
                                intent.putExtra(FORM_ID, "2");
                                intent.putExtra("NormalRegFlag", 100);
                                startActivity(intent);
                            }
                            finish();

                        }
                    });
            builder.setNegativeButton(EnrollmentQuestions.this.getString(R.string.no), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //finish();

                    Iterator<Map.Entry<String, String>> itr2 = validationlist.entrySet().iterator();
                    while (itr2.hasNext()) {
                        Map.Entry<String, String> entry = itr2.next();
                        entry.getKey();
                        entry.getValue();
                        System.out.println("validation key" + entry.getKey());
                        System.out.println("validation value" + entry.getValue());
                    }
                }
            });
            builder.show();//Do nothing on no


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method is used to display dialogue box on click of which user will be re-directed to ANC form
     */
    public void AncForm_Dialog() throws JSONException {

    }

    /**
     * THis method is used to create edittext layout dynamically
     *
     * @param i                   = for loop counter
     * @param language            = question text
     * @param formid              = formid
     * @param keyword             = question keyword
     * @param validationfield     = this field describes whether question is compulsory or not
     * @param validationcondition = this field describes whether the question contains any condition for validation
     * @param validationmsg       = this field gives the error msg.
     * @param messages            = this field contains json with multiple highrisk,counselling,referral conditions
     * @param displayCondition
     * @return layout (ll)
     */
    public LinearLayout createEdittext(int i, String language, final String formid, final String setid, final String keyword, final String validationfield, String validationcondition, String validationmsg, String messages, String displayCondition, final int orientation, String lengthmax) {
        System.out.println("inside createEdittext count" + i + "   keyword" + keyword + "set id +++" + setid);
        try {
            JSONObject textobj = new JSONObject(language);
            language = textobj.getString(mAppLanguage);
//			}

        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (messages != null && messages.length() > 0) {
            ConditionLists.put(keyword, messages);
        }


        final TextView tv = new TextView(this);
        tv.setText("" + language);
        tv.setId(textId + 1);
        //tv.setGravity(Gravity.CENTER);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.text_color));
        tv.setLayoutParams(lp);

        edittextID = edittextID + 1;
        final EditText et = new EditText(this);
        et.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_edittext));
        et.setMinLines(1);
        et.setMaxLines(3);

        et.setId(edittextID);
        et.setTag(keyword);
        et.setSingleLine(true);
//        et.setPadding(10, 20, 20, 20);


        et.setLongClickable(false);

        if (lengthmax != null && lengthmax.length() > 0) {
            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.parseInt(lengthmax))});
        }

        runtimevalidationlist.put(et.getId(), scroll.getId());
        /**
         * This if condition is used to check whether the validation is required or not.
         * if validation is present then its answer is stored in validationlist treemap which is used to next when next button is clicked
         * scroll id is saved in NextButtonvalidationlist treemap to identify how many complusory questions are present on that page
         */
        if (validationfield != null && validationfield.equalsIgnoreCase("true")) {
            tv.setError("");
            validationlist.put("" + et.getTag(), et.getText().toString());
            NextButtonvalidationlist.put("" + et.getTag(), scroll.getId());
        }

        if (keyword.equalsIgnoreCase("dor")) {
            et.setText(defaultdate);
            et.setFocusable(false);
            tv.setError(null);
            womendetails.put(keyword, et.getText().toString());
            womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + et.getText().toString().trim());
            validationlist.put("" + et.getTag(), et.getText().toString());
        } else if (keyword.equalsIgnoreCase(PHC_NAME)) {
            et.setText(hashMapUserDetails.get(PHC_NAME));
            et.setFocusable(false);
            tv.setError(null);
            womendetails.put(keyword, et.getText().toString());
            womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + et.getText().toString().trim());
            validationlist.put("" + et.getTag(), et.getText().toString());
        } else if (keyword.equalsIgnoreCase(AROGYASAKHI_NAME)) {
            et.setText(hashMapUserDetails.get(AROGYASAKHI_NAME));
            et.setFocusable(false);
            tv.setError(null);
            womendetails.put(keyword, et.getText().toString());
            womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + et.getText().toString().trim());
            validationlist.put("" + et.getTag(), et.getText().toString());
        }else if(keyword.equals(NAME_KEYWORD)){
            if(NormalRegFlag==101){
                et.setText(directRegName);
            et.setFocusable(false);
            tv.setError(null);
            womendetails.put(keyword, et.getText().toString());
            womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + et.getText().toString().trim());
            validationlist.put("" + et.getTag(), et.getText().toString());
        }
        }


        if (womendetails.containsKey(keyword)) {
            et.setText(womendetails.get(keyword));

            if (validationfield != null && validationfield.equalsIgnoreCase("true")) {

                validationlist.put("" + et.getTag(), womendetails.get(keyword));
                tv.setError(null);

            }
        }

        if (displayCondition != null && displayCondition.length() > 0 && womenpreviousEcdetails.containsKey(keyword)) {
            et.setText(womenpreviousEcdetails.get(keyword));
            if (Integer.parseInt(formid) != 31) {
                et.setEnabled(false);
            }
            tv.setError(null);
            womendetails.put(keyword, womenpreviousEcdetails.get(keyword));
            validationlist.put("" + et.getTag(), womenpreviousEcdetails.get(keyword));
            womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + womenpreviousEcdetails.get(keyword));
            Backup_answerTyped1.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + womenpreviousEcdetails.get(keyword));


        }


        final String finalValidationmsg = validationmsg;
        et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {

                womendetails.put(keyword, et.getText().toString().trim());                                           // this hashmap is used to insert woman's details in DisplayRegList table
                if (keyword.equalsIgnoreCase("edd")) {
                    womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + Server_expected_date);    // this hashmap is used to enter woman's details in AnswerEntered
                } else {
                    womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + et.getText().toString().trim());    // this hashmap is used to enter woman's details in AnswerEntered
                }


                if (validationlist.containsKey(et.getTag())) {
                    if (et.getText().toString().trim().length() <= 0) {

                        if (et.getTag().equals("mother_id_mcts_sys")) {
                            validationlist.remove(et.getTag());
                            NextButtonvalidationlist.remove(et.getTag());
                            tv.setError(null);
                        } else {

                            if (finalValidationmsg != null && finalValidationmsg.length() > 0) {

                                tv.setError("");
                                validationlist.put("" + et.getTag(), "");
                            } else {
                                tv.setError("");

                                validationlist.put("" + et.getTag(), "");
                            }
                        }


                    } else {
                        // validationlist.remove(et.getTag());
                        et.setError(null);
                        tv.setError(null);
                        validationlist.put("" + et.getTag(), et.getText().toString().trim());
                    }
                }


            }
        });

        if (orientation == 1) {
            ll.setOrientation(LinearLayout.HORIZONTAL);
            tv.setLayoutParams(lpHorizontal);
            et.setLayoutParams(lpHorizontal);
        }

        ll.setTag(keyword);
        ll.addView(tv);
        ll.addView(et);

        return ll;
    }


    /**
     * THis method is used to create Date Field dynamically
     *
     * @param i                   = for loop counter
     * @param language            = question text
     * @param formid              = formid
     * @param keyword             = question keyword
     * @param validationfield     = this field describes whether question is compulsory or not
     * @param validationcondition = this field describes whether the question contains any condition for validation
     * @param validationmsg       = this field gives the error msg.
     * @param messages            = this field contains json with multiple highrisk,counselling,referral conditions
     * @param displayCondition
     * @return layout (ll)
     */

    @SuppressLint("ClickableViewAccessibility")
    public LinearLayout createDate(int i, String language, final String formid, final String setid, final String keyword, final String validationfield, final String validationcondition, final String validationmsg, String messages, String displayCondition, final int orientation) {

        System.out.println("inside createEdittext count" + i + "   keyword" + keyword + "set id +++" + setid);


        String SendServerDate;
        try {
            JSONObject obj = new JSONObject(language);
            language = obj.getString(mAppLanguage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final TextView tv = new TextView(this);
        tv.setText("" + language);
        tv.setId(textId + 1);
        // tv.setGravity(Gravity.CENTER);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.text_color));

        tv.setLayoutParams(lp);


        if (messages != null && messages.length() > 0) {
            ConditionLists.put(keyword, messages);
        }

        //String defaultdate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        edittextID = edittextID + 1;
        final EditText et = new EditText(this);
        et.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_edittext));
        //et.setText("edittextNo"+i);
        et.setMinLines(1);
        et.setMaxLines(3);

        et.setId(edittextID);
        //et.setBackgroundResource(android.R.drawableMainQstn.edit_text);
        //et.setBackground(getResources().getDrawable(R.drawableMainQstn.customeborder));
//        et.setPadding(10, 20, 20, 20);
        et.setTag(keyword);
        et.setLongClickable(false);
        et.setSingleLine(true);
        et.setFocusable(false);
        et.setClickable(true);
        //et.setEnabled(true);
        System.out.println("createDate" + et.getId());

        runtimevalidationlist.put(et.getId(), scroll.getId());
/**
 * This if condition is used to check whether the validation is required or not.
 * if validation is present then its answer is stored in validationlist treemap which is used to next when next button is clicked
 * scroll id is saved in NextButtonvalidationlist treemap to identify how many complusory questions are present on that page
 */
        if (validationfield != null && validationfield.equalsIgnoreCase("true")) {

            if (validationmsg != null && validationmsg.length() > 0) {
                et.setError(validationmsg);
            } else {
                et.setError(EnrollmentQuestions.this.getString(R.string.default_validation_msg));
            }

            validationlist.put(keyword, et.getText().toString());
            NextButtonvalidationlist.put(keyword, scroll.getId());
        }


        if (keyword.equalsIgnoreCase("dor")) {
            et.setText(defaultdate);
            et.setFocusable(false);
            et.setError(null);
            et.setClickable(false);

            womendetails.put(keyword, serverdefaultdateFormatter);
            womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + serverdefaultdateFormatter);
            validationlist.put("" + et.getTag(), serverdefaultdateFormatter);
        }

        if (womendetails.containsKey(keyword)) {
            et.setText(womendetails.get(keyword));
            if (validationfield != null && validationfield.equalsIgnoreCase("true"))
                if (validationmsg == null && validationmsg.length() < 0)
                    et.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear, 0);
            if (validationfield != null && validationfield.equalsIgnoreCase("true")) {

                validationlist.put("" + et.getTag(), womendetails.get(keyword));
                tv.setError(null);

            }
        }

        if (displayCondition != null && displayCondition.length() > 0 && womenpreviousEcdetails.containsKey(keyword)) {
            try {

                Date dt = serverdateFormatter.parse(womenpreviousEcdetails.get(keyword));

                et.setText(formatter.format(dt));
                if (validationfield != null && validationfield.equalsIgnoreCase("true"))
                    if (validationmsg == null && validationmsg.length() < 0)
                        et.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear, 0);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (Integer.parseInt(formid) != 31) {
                et.setEnabled(false);
            }
            et.setError(null);
            validationlist.put("" + et.getTag(), womenpreviousEcdetails.get(keyword));
            womendetails.put(keyword, womenpreviousEcdetails.get(keyword));
            womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + womenpreviousEcdetails.get(keyword));

            Backup_answerTyped1.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + womenpreviousEcdetails.get(keyword));

        }


        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (keyword.equalsIgnoreCase("lmp_date")) {
                    et.setFocusable(false);
                }
            }


        });

        et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (validationfield != null && validationfield.equalsIgnoreCase("true"))
                            if (validationmsg == null && validationmsg.length() < 0)
                                if (et.getCompoundDrawables()[DRAWABLE_RIGHT] != null && motionEvent.getRawX() >= (et.getRight() - et.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                    // your action here
                                    et.setText("");
                                    et.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                    validationlist.put("" + et.getTag(), "");
                                    return true;
                                }
                        if (fromDatePickerDialog == null || (!fromDatePickerDialog.isShowing())) {
                            showDatePicker();
//
                            fromDatePickerDialog.show();
                        }

                        break;
                }


                return false;


            }

            public void showDatePicker() {
                //fromDatePickerDialog.show();

                Calendar newCalendar = Calendar.getInstance();
                fromDatePickerDialog = new DatePickerDialog(ctx, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);

                        et.setText(dateFormatter.format(newDate.getTime()));
                        et.setFocusable(false);
                        String getDate = dateFormatter.format(newDate.getTime());
                        if (validationfield != null && validationfield.equalsIgnoreCase("true"))
                            if (validationmsg == null && validationmsg.length() < 0)
                                et.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear, 0);
                        //SendServerDate=serverdateFormatter.format(newDate.getTime());
                        try {

                            //System.out.println("SendServerDate====" + SendServerDate);
                            selectedDate = formatter.parse(getDate);
                            serverDate = serverdateFormatter.format(newDate.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (SystemDate.compareTo(selectedDate) >= 0) {
                            /*if (keyword.equalsIgnoreCase("lmp_date")) {

                                Date minDate = new StringToTime(serverdefaultdateFormatter + " - 280 days");
                                Date maxDate = new StringToTime(serverdefaultdateFormatter + " - 028 days");

                                if ((selectedDate.after(minDate) || selectedDate.equals(minDate)) && (selectedDate.before(maxDate) || selectedDate.equals(maxDate))) {
                                    lmpvalid = true;
                                    validationlist.put("" + et.getTag(), et.getText().toString());
                                    et.setError(null);
                                } else {
                                    lmpvalid = false;
                                    System.out.println("lmpvalid***" + lmpvalid);
                                    Toast.makeText(getApplicationContext(), R.string.enter_valid_lmp, Toast.LENGTH_LONG).show();
                                    et.setError("LMP date should not be less then 1 year or more than 1 year from current date");
                                    validationlist.put("" + et.getTag(), "");
                                }
                            } else {*/
                            lmpvalid = true;
                            validationlist.put("" + et.getTag(), et.getText().toString());
                            et.setError(null);
                            //}

                        } else {
                            lmpvalid = false;

                            Toast.makeText(getApplicationContext(), R.string.exceed_curnt_dt, Toast.LENGTH_LONG).show();
                            et.setError(getApplicationContext().getString(R.string.exceed_curnt_dt));
                            validationlist.put("" + et.getTag(), "");
                            NextButtonvalidationlist.put("" + et.getTag(), scroll.getId());

                        }

                        womendetails.put(keyword, serverDate);
                        womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + serverDate);


                        if (validationlist.containsKey(et.getTag())) {
                            if (lmpvalid) {
                                validationlist.put("" + et.getTag(), et.getText().toString());
                                NextButtonvalidationlist.put("" + et.getTag(), runtimevalidationlist.get(et.getId()));
                                et.setError(null);
                            } else {
                                System.out.println("lmpvalid inside else afterTextChanged" + lmpvalid);
                                validationlist.put("" + et.getTag(), "");
                                NextButtonvalidationlist.put("" + et.getTag(), runtimevalidationlist.get(et.getId()));
                            }
                        }


                    }


                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

            }


        });

        if (orientation == 1) {
            ll.setOrientation(LinearLayout.HORIZONTAL);
            tv.setLayoutParams(lpHorizontal);
            et.setLayoutParams(lpHorizontal);
        }

        ll.setTag(keyword);
        ll.addView(tv);
        ll.addView(et);

        return ll;
    }


    /**
     * THis method is used to create time Field dynamically
     *
     * @param i               = for loop counter
     * @param language        = question text
     * @param formid          = formid
     * @param setid           = setid of the question
     * @param keyword         = question keyword
     * @param validationfield = this field describes whether question is compulsory or not
     * @param messages        = this field contains json with multiple highrisk,counselling,referral conditions
     * @return layout (ll)
     */
    public LinearLayout createTime(int i, String language, final String formid, final String setid, final String keyword, final String validationfield, String messages, String displayCondition, int scrollID, final int orientation) {

        System.out.println("inside method count" + i + "   keyword" + keyword);

        try {
            JSONObject obj = new JSONObject(language);
            language = obj.getString(mAppLanguage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final TextView tv = new TextView(this);
        tv.setText("" + language);
        tv.setId(textId + 1);
        // tv.setGravity(Gravity.CENTER);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.text_color));
        tv.setLayoutParams(lp);


        String defaultdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        edittextID = edittextID + 1;
        final EditText et = new EditText(this);
        et.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_edittext));
        //et.setText("edittextNo"+i);
        et.setMinLines(1);
        et.setMaxLines(3);
        //et.setHint(defaultdate);
        et.setId(edittextID);
        et.setTag(keyword);
        et.setFocusable(false);
        et.setClickable(true);
        et.setSingleLine(true);
        //System.out.println("createDate"+et.getId());

        runtimevalidationlist.put(et.getId(), scrollID);

        /**
         * This if condition is used to check whether the validation is required or not.
         * if validation is present then its answer is stored in validationlist treemap which is used to next when next button is clicked
         * scroll id is saved in NextButtonvalidationlist treemap to identify how many complusory questions are present on that page
         */
        if (validationfield != null && validationfield.equalsIgnoreCase("true")) {
            tv.setError("");
            validationlist.put("" + et.getTag(), et.getText().toString());
            NextButtonvalidationlist.put("" + et.getTag(), scroll.getId());

        }

        /**
         * this if condition is used to display the dataSource which is already entered by the woman
         */
        if (womendetails.containsKey(keyword)) {
            et.setText(womendetails.get(keyword));

            if (validationfield != null && validationfield.equalsIgnoreCase("true")) {

                validationlist.put("" + et.getTag(), womendetails.get(keyword));
                tv.setError(null);

            }
        }


        if (messages != null && messages.length() > 0) {
            ConditionLists.put(keyword, messages);
        }


//
//        if(keyword.equalsIgnoreCase("Time_of_Birth"))
//        {
//
//            String deliveryTime = dbhelper.getWomenDeliveryTime(uniqueId);
//
//            et.setText(deliveryTime);
//            et.setFocusable(false);
//            et.setEnabled(false);
//            et.setError(null);
//
////			womenanswer.put(et.getId(), "" + formid + Common.womenanswerdelimeter + setid + Common.womenanswerdelimeter + keyword + Common.womenanswerdelimeter + defaultServerdate);
////			Backup_answerTyped1.put(et.getId(), "" + formid + Common.womenanswerdelimeter + setid + Common.womenanswerdelimeter + keyword + Common.womenanswerdelimeter + defaultServerdate);
////			validationlist.put(et.getId(), et.getText().toString());
//        }

        et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if (mTimePicker == null || (!mTimePicker.isShowing())) {
                            showDatePicker();
                            mTimePicker.show();
                        }
//
                        //mTimePicker.show();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }

                return false;


            }

            public void showDatePicker() {
                //fromDatePickerDialog.show();

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                mTimePicker = new TimePickerDialog(EnrollmentQuestions.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        //et.setText( selectedHour + ":" + selectedMinute);
                        boolean isPM = (selectedHour >= 12);
                        et.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, selectedMinute, isPM ? "PM" : "AM"));

                    }
                }, hour, minute, false);//Yes 12 hour time
                mTimePicker.setTitle("Select Time");


            }


        });


        et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                System.out.println("createDate onFocusChange" + et.getId());
                womendetails.put(keyword, et.getText().toString());
                womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + et.getText().toString()); // this hashmap is used to insert dataSource in AnswerEntered table
                Backup_answerTyped1.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + et.getText().toString()); // this hashmap is used to insert dataSource in Backup_AnswerEntered

                if (et.getText().toString().length() <= 0) {
                    tv.setError("");
                } else {
                    tv.setError(null);
                }

                for (Map.Entry<String, String> entry : validationlist.entrySet()) {

                    if (et.getTag().equals(entry.getKey())) {
                        validationlist.put("" + et.getTag(), et.getText().toString());

                    }
                }
            }
        });

        if (displayCondition != null && displayCondition.length() > 0 && womenpreviousEcdetails.containsKey(keyword)) {
            et.setText(womenpreviousEcdetails.get(keyword));
            et.setEnabled(false);

            validationlist.put("" + et.getTag(), womenpreviousEcdetails.get(keyword));
            womendetails.put(keyword, et.getText().toString());
            womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + et.getText().toString());
            Backup_answerTyped1.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + et.getText().toString());

        }

        if (orientation == 1) {
            ll.setOrientation(LinearLayout.HORIZONTAL);
            tv.setLayoutParams(lpHorizontal);
            et.setLayoutParams(lpHorizontal);
        }

        ll.setTag(keyword);
        ll.addView(tv);
        ll.addView(et);

        return ll;
    }

    /**
     * @param SystemDate  Sysdate
     * @param EnteredDate woman's date of birth
     * @return age
     */
    private int getYear(Date SystemDate, Date EnteredDate) {

        SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
        Integer.parseInt(simpleDateformat.format(SystemDate));

        System.out.println("SystemDate.compareTo(EnteredDate) = " + SystemDate.compareTo(EnteredDate));
        ;

        return Integer.parseInt(simpleDateformat.format(SystemDate)) - Integer.parseInt(simpleDateformat.format(EnteredDate));
    }


    /**
     * THis method is used to create integer Field dynamically
     *
     * @param answerType          = for loop counter
     * @param language            = question text
     * @param formid              = formid
     * @param keyword             = question keyword
     * @param validationfield     = this field describes whether question is compulsory or not
     * @param validationcondition = this field describes whether the question contains any condition for validation
     * @param validationmsg       = this field gives the error msg.
     * @param messages            = this field contains json with multiple highrisk,counselling,referral conditions
     * @param displayCondition
     * @return layout (ll)
     */
    public LinearLayout createInt(String answerType, String language, final String formid, final String setid, final String keyword, final String validationfield, final String validationcondition, String validationmsg, final String Lengthmin, final String Lengthmax, String Lengthmsg, final String Rangemin, final String Rangemax, String Rangemsg, final String Counsellingmsg, String messages, String displayCondition, final int orientation, final String calculations) {


        try {
            JSONObject textobj = new JSONObject(language);

            language = textobj.getString(mAppLanguage);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            if (Lengthmsg != null && Lengthmsg.length() > 0) {
                JSONObject vali_lengthmsg_obj = new JSONObject(Lengthmsg);
                Lengthmsg = vali_lengthmsg_obj.getString(mAppLanguage);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (Rangemsg != null && Rangemsg.length() > 0) {
                JSONObject vali_rangemsg_obj = new JSONObject(Rangemsg);
                Rangemsg = vali_rangemsg_obj.getString(mAppLanguage);
                System.out.println("Rangemsg" + Rangemsg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (messages != null && messages.length() > 0) {
            ConditionLists.put(keyword, messages);
        }


        final TextView tv = new TextView(this);
        tv.setText("" + language);
        tv.setId(textId + 1);
        // tv.setGravity(Gravity.CENTER);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.text_color));

        tv.setLayoutParams(lp);


        edittextID = edittextID + 1;
        final EditText et = new EditText(this);
        et.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_edittext));
        //et.setText("edittextNo"+i);
        et.setMinLines(1);
        et.setMaxLines(3);

        et.setId(edittextID);
        et.setTag(keyword);
        //et.setBackground(getResources().getDrawable(R.drawableMainQstn.customeborder));
        //System.out.println("createDate" + et.getId());

        et.setSingleLine(true);
        et.setLongClickable(false);

//        et.setPadding(10, 20, 20, 20);

        if (Lengthmax != null && Lengthmax.length() > 0) {
            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.parseInt(Lengthmax))});
        }


        switch (answerType) {
            case "int":
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;

            default:
                et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                et.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(3, 2)});
                break;
        }

        /**
         * This hashmap is used to store the scroll id's for edittext while creating.
         * so if there is some validation which is not send through server but is called on runtime then this hashmap is used to get the scroll id
         * stored for that edittext and hence validation can be performed.
         */
        runtimevalidationlist.put(et.getId(), scroll.getId());

        if (validationfield != null && validationfield.equalsIgnoreCase("true")) {
            if (validationmsg != null && validationmsg.length() > 0) {
                et.setError(validationmsg);
            } else {
                et.setError(EnrollmentQuestions.this.getString(R.string.default_validation_msg));
            }

            validationlist.put("" + et.getTag(), et.getText().toString());
            NextButtonvalidationlist.put("" + et.getTag(), scroll.getId());
        }

        if (womendetails.containsKey(keyword)) {
            et.setText(womendetails.get(keyword));

            if (validationfield != null && validationfield.equalsIgnoreCase("true")) {

                validationlist.put("" + et.getTag(), womendetails.get(keyword));
                tv.setError(null);
                et.setError(null);
            }
        }

        if (displayCondition != null && displayCondition.length() > 0 && womenpreviousEcdetails.containsKey(keyword)) {
            et.setText(womenpreviousEcdetails.get(keyword));
            if (Integer.parseInt(formid) != 31) {
                et.setEnabled(false);
            }
            et.setError(null);
            womendetails.put(keyword, womenpreviousEcdetails.get(keyword));
            validationlist.put("" + et.getTag(), womenpreviousEcdetails.get(keyword));
            womendetails.put(keyword, et.getText().toString());
            womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + et.getText().toString());
            Backup_answerTyped1.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + et.getText().toString());

        }

        if (keyword.equalsIgnoreCase(AROGYASAKHI_MOB)) {
            et.setText(hashMapUserDetails.get(AROGYASAKHI_MOB));
            et.setFocusable(false);
            tv.setError(null);
            womendetails.put(keyword, et.getText().toString());
            womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + et.getText().toString().trim());
            validationlist.put("" + et.getTag(), et.getText().toString());
        } else if (keyword.equalsIgnoreCase(AROGYASAKHI_ALTERNATE_NO)) {
            et.setText(hashMapUserDetails.get(AROGYASAKHI_ALTERNATE_NO));
            et.setFocusable(false);
            tv.setError(null);
            womendetails.put(keyword, et.getText().toString());
            womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + et.getText().toString().trim());
            validationlist.put("" + et.getTag(), et.getText().toString());
        }else if(keyword.equals(PHONE_KEYWORD)){
            if(NormalRegFlag==101){
            et.setText(directRegPhone);
            et.setFocusable(false);
            tv.setError(null);
            et.setError(null);
            womendetails.put(keyword, et.getText().toString());
            womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + et.getText().toString().trim());
            validationlist.put("" + et.getTag(), et.getText().toString());
            NextButtonvalidationlist.remove("" + et.getTag());
            }
        }else if (keyword.equals("participant_study_id")) {
            if (NormalRegFlag != 100) {
                NextButtonvalidationlist.remove("" + et.getTag());
            }
        }


        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                String messages = ConditionLists.get(keyword);
                StoredHighRiskRanges = new ArrayList<>();
                StoredCounsellingRanges = new ArrayList<>();
                StoredPatientVisitSummaryRanges = new ArrayList<>();

                System.out.println("keyword = " + keyword);
                System.out.println("ConditionLists = " + ConditionLists);
                System.out.println("messages = " + messages);

                StorePVSmsgs(messages);


            }
        });

        final String finalValidationmsg = validationmsg;
        final String finalLengthmsg = Lengthmsg;
        final String finalRangemsg = Rangemsg;
        et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                try {

                    System.out.println(" create Int et.getText().toString() = " + et.getText().toString());
                    System.out.println("keyword = " + keyword);

                    if (validationlist.containsKey(et.getTag())) {
                        if (et.getText().toString().length() <= 0) {

                            validationlist.put("" + et.getTag(), "");
                            et.setError(EnrollmentQuestions.this.getString(R.string.default_validation_msg));
                        } else {
                            validationlist.put("" + et.getTag(), et.getText().toString());
                        }
                    } else {
                        if (et.getText().toString().equals("")) {
                            et.setError(null);
                            validationlist.remove("" + et.getTag());
                            NextButtonvalidationlist.remove("" + et.getTag());

                            womendetails.remove(keyword);
                            womenanswer.remove(et.getId());

                        }

                    }


                    StorePatientVisitHighRiskDiagnosticValues(et.getText().toString(), et, keyword, setid);
                    womendetails.put(keyword, et.getText().toString());
                    womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + et.getText().toString());
                    Backup_answerTyped1.put(et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + et.getText().toString()); // this hashmap is used to insert dataSource in Backup_AnswerEntered


                    /**
                     * This Code is use to check Length of the text field if it is less then it will show
                     * Error message
                     */

                    if (Lengthmin != null && Lengthmin.length() > 0 && et.getText().toString().length() > 0) {

                        if ((Double.parseDouble(Lengthmin) <= et.getText().toString().length()) && (et.getText().toString().length() <= Double.parseDouble(Lengthmax))) {
                            et.setError(null);
                            //next.setEnabled(true);

                            validationlist.remove("" + et.getTag());
                            NextButtonvalidationlist.remove("" + et.getTag());

                        } else {
                            et.setError(finalLengthmsg);
                            //next.setEnabled(false);
                            validationlist.put("" + et.getTag(), "");
                            NextButtonvalidationlist.put("" + et.getTag(), runtimevalidationlist.get(et.getId()));

                        }
                    } else {
                        et.setError(null);
                        //next.setEnabled(true);createIn

                        validationlist.remove("" + et.getTag());
                        NextButtonvalidationlist.remove("" + et.getTag());

                        /**
                         * This if condition is used for validations of mobile number or any range applyed for questions
                         * i.e (between 10 to 12) To check the range in text Field.
                         */

                        if (Rangemin != null && Rangemin.length() > 0 && et.getText().toString().length() > 0) {
                            if ((Double.parseDouble(Rangemin) <= Double.parseDouble(et.getText().toString())) && (Double.parseDouble(et.getText().toString()) <= Double.parseDouble(Rangemax))) {
                                et.setError(null);
                                //next.setEnabled(true);

                                validationlist.put("" + et.getTag(), et.getText().toString());
                                System.out.println("CHECK DATA" + et.getText().toString());
//						NextButtonvalidationlist.put(et.getId(), scroll.getId());

                            } else {

                                et.setError(finalRangemsg);
                                //next.setEnabled(false);
                                validationlist.put("" + et.getTag(), "");
                                //NextButtonvalidationlist.put(""+et.getTag(),runtimevalidationlist.get(et.getId()));
                                NextButtonvalidationlist.put("" + et.getTag(), scroll.getId());
                            }
                        } else {
                            et.setError(null);
                            //next.setEnabled(true);

                            validationlist.remove("" + et.getTag());
                            NextButtonvalidationlist.remove("" + et.getTag());
                        }
                    }

/**
 * This is Dynamic calculation used for age to check
 */
/*
                    if(calculations!=null && calculations.length()>0)
                    {
                        if(!dynamicCalculation(calculations))
                        {
                            et.setError(getString(R.string.present_age_msg));
                            validationlist.put(""+et.getTag(), "");
                            NextButtonvalidationlist.put(""+et.getTag(),runtimevalidationlist.get(et.getId()));
                        }else
                        {
                            et.setError(null);
                            validationlist.remove(""+et.getTag());
                        }
                    }
*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        if (orientation == 1) {
            ll.setOrientation(LinearLayout.HORIZONTAL);
            tv.setLayoutParams(lpHorizontal);
            et.setLayoutParams(lpHorizontal);
        }

        ll.setTag(keyword);
        ll.addView(tv);
        ll.addView(et);

        return ll;
    }


    /**
     * THis method is used to create Spinner Field dynamically
     *
     * @param i                   = for loop counter
     * @param language            = question text
     * @param formid              = formid
     * @param keyword             = question keyword
     * @param validationfield     = this field describes whether question is compulsory or not
     * @param validationcondition = this field describes whether the question contains any condition for validation
     * @param validationmsg       = this field gives the error msg.
     * @param messages            = this field contains json with multiple highrisk,counselling,referral conditions
     * @param displayCondition
     * @return layout (ll)
     */
    public LinearLayout createSpinner(int i, String language, final String formid, final String setid, final String keyword, final String validationfield, final String validationcondition, final String validationmsg, String messages, String displayCondition, final int orientation) {

        try {
            JSONObject obj = new JSONObject(language);
            language = obj.getString(mAppLanguage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (messages != null && messages.length() > 0) {
            ConditionLists.put(keyword, messages);
        }


        final TextView tv = new TextView(this);
        tv.setText("" + language);
        tv.setId(textId + 1);
        // tv.setGravity(Gravity.CENTER);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.text_color));

        tv.setLayoutParams(lp);

        ll.setTag(keyword);

        if (orientation == 1) {
            ll.setOrientation(LinearLayout.HORIZONTAL);
            tv.setLayoutParams(lpHorizontal);
        }


        ArrayList<SpinnerItems> spinnerArray = new ArrayList<>();

        if (keyword.equalsIgnoreCase(VILLAGE_NAME)) {
            //    spinnerArray = questionInteractor.fetchVillages();
        }

        NextButtonvalidationlist.put(keyword, scroll.getId());

        SpinnerItems placeHolderItem = new SpinnerItems("-1", getResources().getString(R.string.select));
        spinnerArray.add(0, placeHolderItem);


        Spinner spinner = new Spinner(this);
        spinner.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_spinner));
        ArrayAdapter<SpinnerItems> spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_text, spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setTag(keyword);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {


                SpinnerItems items = (SpinnerItems) adapterView.getSelectedItem();

                villageId = items.getId();

                String answer = items.getValue();

                if (position == 0) {
                    validationlist.put(keyword, "");
                    tv.setError("");
                    return;
                }

                tv.setError(null);
                validationlist.put(keyword, answer);
                womendetails.put(keyword, answer);
                womenanswer.put(position, "" + formid
                        + delimeter + setid
                        + delimeter + keyword
                        + delimeter + items.getValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ll.addView(tv);
        ll.addView(spinner);

        return ll;

    }

    /**
     * THis method is used to create Label Field dynamically
     *
     * @param i                   = for loop counter
     * @param language            = question text
     * @param formid              = formid
     * @param keyword             = question keyword
     * @param validationfield     = this field describes whether question is compulsory or not
     * @param validationcondition = this field describes whether the question contains any condition for validation
     * @param validationmsg       = this field gives the error msg.
     * @param messages            = this field contains json with multiple highrisk,counselling,referral conditions
     * @param displayCondition
     * @return layout (ll)
     */
    public LinearLayout createLabel(int i, String language, final String formid, final String setid, final String keyword, final String validationfield, final String validationcondition, final String validationmsg, String messages, String displayCondition, String calculations) {

        try {
            JSONObject obj = new JSONObject(language);
            language = obj.getString(mAppLanguage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        language = language.replace("\\n ", "\n");

        if (messages != null && messages.length() > 0) {
            ConditionLists.put(keyword, messages);
        }

        if (calculations != null && !calculations.equals("")) {
            labelKeywirdDetails.put(scroll.getId(), new Label(language, calculations));
        }

        TextView tv = new TextView(this);
        tv.setText("" + language);
//        tv.setId(textId + 1);
        // tv.setGravity(Gravity.CENTER);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        tv.setId(Integer.parseInt(setid));

        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

       /* LinearLayout.LayoutParams lp_label = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Frame.getLayoutParams().height));
//        lp_label.setMargins(10, 10, 10, 10);
        ll.setLayoutParams(lp_label);
        ll.setGravity(Gravity.CENTER);*/


        ll.setTag(keyword);
        ll.addView(tv);

        return ll;
    }

    public LinearLayout createSubLabel(int i, String language, final String formid, final String setid, final String keyword, final String validationfield, final String validationcondition, final String validationmsg, String messages, String displayCondition) {

        System.out.println("inside createSubLabel count" + i + "   keyword" + keyword + "language +++" + language);

        try {
            JSONObject obj = new JSONObject(language);
            language = obj.getString(mAppLanguage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        language = language.replace("\\n ", "\n");
        ;

        if (messages != null && messages.length() > 0) {
            ConditionLists.put(keyword, messages);
        }

        final TextView tv = new TextView(this);
        tv.setText("" + language);
        tv.setId(textId + 1);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        ll.setTag(keyword);
        ll.addView(tv);

        return ll;
    }

    /**
     * THis method is used to create Radio's button dynamically
     *
     * @param i                = for loop counter
     * @param quesid           = question id of the registration
     * @param language         = question text
     * @param keyword          = question keyword
     * @param validationfield  = this field describes whether question is compulsory or not
     * @param formid           = formid
     * @param messages         = this field contains json with multiple highrisk,counselling,referral conditions
     * @param displayCondition
     * @return layout (ll)
     */
    public LinearLayout createRadio(int i, final String quesid, String language, final String setid, final String keyword, String validationfield, final String formid, String messages, final String displayCondition, final int orientation) {

        try {
            JSONObject obj = new JSONObject(language);
            language = obj.getString(mAppLanguage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (messages != null && messages.length() > 0) {
            ConditionLists.put(keyword, messages);
        }

        final TextView tv = new TextView(this);
        tv.setText("" + language);
        tv.setId(textId + 1);
        // tv.setGravity(Gravity.CENTER);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.text_color));
        tv.setLayoutParams(lp);


        radiogroup = radiogroup + 1;

        /**
         * This if condition is used to check if the question contains any dependant question.
         */
//        optionList = dbhelper.getANCEnglishoptions(quesid);  // this dbHelper statement gets q.keyword,q.answer_type,qi.keyword,qi.dependants,qi.depend_lang_eng,qi.depend_lang_mara,qi.action for that specific quesid in english
        optionList = questionInteractor.getQuestionOptions(quesid, formID);

        //final RadioButton[] rb = new RadioButton[2];
        final RadioGroup rg = new RadioGroup(this); //create the RadioGroup
        rg.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
        rg.setId(radiogroup);
//        rg.setPadding(20, 10, 20, 10);
        rg.setLayoutParams(segmentedBtnLp);

        //rg.setTag(keyword);

        ll.setTag(keyword);

        if (orientation == 1) {
//            rg.setPadding(20, 0, 0, 0);
            tv.setLayoutParams(lpHorizontal);
            rg.setLayoutParams(lpHorizontal);
            rg.setOrientation(RadioGroup.VERTICAL);
            ll.setOrientation(LinearLayout.HORIZONTAL);
        }

        ll.addView(tv);


        /**
         * This if condition is used to check whether the validation is required or not.
         * if validation is present then its answer is stored in validationlist treemap which is used to next when next button is clicked
         * scroll id is saved in NextButtonvalidationlist treemap to identify how many complusory questions are present on that page
         */
        if (validationfield != null && validationfield.equalsIgnoreCase("true")) {
            tv.setError("");
            validationlist.put(keyword, "");
            NextButtonvalidationlist.put(keyword, scroll.getId());

            //System.out.println("radio grp validation***" + validationlist.get(rg.getTag()));
        }

        runtimevalidationlist.put(rg.getId(), scroll.getId());

        if (optionList.size() < 4) {

            ll.addView(rg);

            /**
             * This for loop is used to display the radio buttons for the given question
             */
            int length = optionList.size();
            for (int k = 1; k < length; k++) {
                radio = radio + 1;

                try {
                    JSONObject obj = new JSONObject(optionList.get(k).getQuestionText());
                    System.out.println();
                    Optionlanguage = obj.getString(mAppLanguage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final RadioButton rb = new RadioButton(ctx);
                // rb[k]  = new RadioButton(this);
                rb.setText(Optionlanguage);
                //rb.setText(optionList.get(k).getEnglishlang());
                rb.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
                //rb.setText(optionList.get(k).getQuestionText());
                rg.addView(rb);
                rb.setTextColor(getResources().getColor(R.color.text_color));
                rb.setTag(optionList.get(k).getKeyword());
                rb.setPadding(20, 10, 20, 10);

                if (orientation == 0) rb.setLayoutParams(lparams);

                rb.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        hideSoftKeyboard(v);

                        int id = rg.getCheckedRadioButtonId();
                        int parentId = (((ViewGroup) rb.getParent()).getId()); //parentTag

                        storeEnteredData.put(keyword, rb.getTag().toString());

                        rb.setChecked(true);


                        onClickButtonFunctionality(rb, v, quesid, keyword, setid, id, tv, formid, optionList, displayCondition, "" + runtimevalidationlist.get(rg.getId()), orientation);

                    }
                });


                if (womendetails.containsKey(keyword)) {

                    if (womendetails.get(keyword).equalsIgnoreCase(rb.getTag().toString())) {

                        rb.setChecked(true);
                        rb.performClick();

                    }

                    if (validationlist.containsKey(keyword)) {
                        validationlist.put(keyword, womendetails.get(keyword));
                        tv.setError(null);
                    }

                }

                if (displayCondition != null && displayCondition.length() > 0 && womenpreviousEcdetails.containsKey(keyword)) {
                    if (optionList.get(k).getKeyword().equals(womenpreviousEcdetails.get(keyword))) {
                        rb.setChecked(true);
                        rb.performClick();
                    }
                    tv.setError(null);

                    validationlist.put(keyword, womenpreviousEcdetails.get(keyword));
                    if (Integer.parseInt(formid) != 31) {
                        rb.setEnabled(false);
                    }
                }

            }

        } else if (optionList.size() == 4) {
            createButton(quesid, keyword, setid, rg.getId(), tv, formid, optionList, displayCondition, "" + runtimevalidationlist.get(rg.getId()), orientation);
        } else {
            multipleRadioButton(quesid, keyword, setid, rg.getId(), tv, formid, orientation);
        }
        return ll;
    }

    /**
     * THis method is used to create customize dropdown widget dynamically
     *
     * @param quesid  = question id of the registration
     * @param id      = radio button id
     * @param formid  = formid
     * @param keyword = question keyword
     * @param tv      = textview of the question
     * @param setid   = setid of the question
     */
    public void multipleRadioButton(final String quesid, final String keyword, final String setid, final int id, final TextView tv, final String formid, final int orientation) {
        {

            System.out.println("multipleRadioButton optionList list size" + optionList.size());

            final Spinner spinner = new Spinner(this);
            spinner.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_spinner));
            customespinner = customespinner + 1;
            spinner.setId(customespinner);
           /* adapter = new CustomizeRecievedList(this, optionList,"");
            spinner.setAdapter(adapter);*/

            List<String> optionsList = new ArrayList<>();
            optionsList.add(EnrollmentQuestions.this.getString(R.string.dropdown_text));

            try {
                for (int k = 1; k <= optionList.size(); k++) {
                    JSONObject obj = new JSONObject(optionList.get(k).getQuestionText());

                    Optionlanguage = obj.getString(mAppLanguage);
                    optionsList.add(Optionlanguage);
                }
            } catch (Exception e) {
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, optionsList);
            spinner.setAdapter(arrayAdapter);


            System.out.println("Spinner getId" + spinner.getId());
            Backup_customespinner.put("" + spinner.getId(), optionList);
//
            spinner.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(v);
                    return false;
                }
            });

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {

                    try {

                        ll_sub = (LinearLayout) view.getParent().getParent();

                        System.out.println("onItemSelected Spinner getId" + spinner.getId());

                        String keyword1 = null;
                        Visit m = (Visit) Backup_customespinner.get("" + spinner.getId()).get(pos);

                        PregnancyStatus = m.getKeyword();
                        if (m.getKeyword() != null && m.getKeyword().equalsIgnoreCase("pregnancy_test_negative")) {
                        }

                        if (m.getKeyword() != null && m.getKeyword().length() > 0) {
                            validationlist.put(keyword, m.getQuestionText());
                            tv.setError(null);
                            womendetails.put(keyword, m.getKeyword());

                            System.out.println("multipleRadioButton formid = " + formid);
                            womenanswer.put(spinner.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + m.getKeyword());
                        } else {
                            if (validationlist.containsKey(keyword)) {
                                validationlist.put(keyword, "");
                                tv.setError("");
                                womendetails.remove(keyword);
                                womenanswer.remove(spinner.getId());
                            }

                        }
//						System.out.println("keyword 2 " + keyword);


//                        dependantList = dbhelper.getDependant_Eng_queslist("" + m.getKeyword(), formid, ll_sub, keyword, "" + scroll_temp.getId());  // this list is used to get dependant question in english format (parameter"s received are form_id,keyword,answer_type,english_lang)
                        dependantList = questionInteractor.getDependantQuesList(m.getKeyword(), formID, ll_sub, keyword, "" + scroll_temp.getId());

                        if (dependantList != null && dependantList.size() > 0) {

                            if (dependantKeywordPresent.containsValue("" + m.getKeyword())) {

                            } else {

                                System.out.println("key = " + keyword + " called..." + MainQuestempstoredependant);

                                LinearLayout ll_4 = (LinearLayout) view.getParent().getParent().getParent();

                                removeDependent(ll_4, keyword);
                                dependantKeywordPresent.put(keyword, "" + m.getKeyword());

                                System.out.println("dependantKeywordPresent 1=....." + dependantKeywordPresent);

                                /**
                                 * This if condition is used to check whether dependant layout is displayed or not
                                 * this is used as a validation that if once the layout is displayed then again clicking on the same button twice the layout should be displayed only once
                                 * for eg. if tt2yes is clicked for the first time then dependant layout should be displayed but again clicking on it dependant layout should not be displayed.
                                 */

                                System.out.println("MainQuestempstoredependant.containsKey(keyword) = " + MainQuestempstoredependant.containsKey(keyword));

                                if (!(MainQuestempstoredependant.containsKey(keyword))) {
                                    ArrayList tempdependantStore = new ArrayList<String>();
                                    layoutids.put("" + m.getKeyword(), "" + ll_sub);    // this hashmap is used to store the layout id for the clicked button (for eg: tt2yes,android.widget.LinearLayout{21abf298 V.E...C. ......ID 40,646-560,769})

                                    DisplayDependantQuestions(dependantList);

                                }

                            }

                        } else {

                            LinearLayout ll_4 = (LinearLayout) view.getParent().getParent().getParent();

                            removeDependent(ll_4, keyword);

                            dependantKeywordPresent.remove(keyword);

                        }


                        String counsel_message = null;
//                        String Messages = dbhelper.getHighRiskConditionForRadio("" + m.getKeyword());
                        String Messages = questionInteractor.getHighRiskCondition(m.getKeyword());

                        //System.out.println("Messages = " + Messages);

                        /**
                         * This logic is used to check whether there is any High risk,Counselling or Diagnostic referral on button click
                         */
                        try {
                            if (Messages != null && Messages.length() > 0) {
                                StoredHighRiskRanges = new ArrayList<>();

                                JSONObject highRisk_conditions = new JSONObject(Messages);
                                if (highRisk_conditions.optJSONArray("highrisk") != null) {
                                    JSONArray highRisk_conditionsArray = highRisk_conditions.optJSONArray("highrisk");

                                    //System.out.println("jsonArray3 options"+jsonArray3.length());
                                    for (int k = 0; k < highRisk_conditionsArray.length(); k++) {
                                        JSONObject main_ques_options_key = highRisk_conditionsArray.getJSONObject(0);
                                        System.out.println("main_ques_options_key High risk lang = " + main_ques_options_key.optString("languages").toString());
                                        ;

                                        highrisklist.put(quesid, "" + formid + delimeter + setid + delimeter + keyword + delimeter + m.getKeyword() + delimeter + main_ques_options_key.optString("languages").toString() + delimeter + "highrisk" + delimeter + "0");

                                    }
                                }
                                System.out.println("highrisklist.size() = " + highrisklist.size());
                                //System.out.println("highRisk_conditions.optJSONArray(\"counselling\") = " + highRisk_conditions.optJSONArray("counselling"));

                                if (highRisk_conditions.optJSONArray("counselling") != null) {
                                    JSONArray counseling_conditionsArray = highRisk_conditions.optJSONArray("counselling");
                                    for (int k = 0; k < counseling_conditionsArray.length(); k++) {
                                        JSONObject main_ques_options_key = counseling_conditionsArray.getJSONObject(0);
                                        System.out.println("main_ques_options_key Counseling lang = " + main_ques_options_key.optString("languages").toString());

                                        counsel_message = main_ques_options_key.optString("languages").toString();
                                        if (main_ques_options_key.has("show_popup")) {
                                            JSONObject obj = new JSONObject(counsel_message);
                                            String language = obj.getString(mAppLanguage);
                                            criticalCounsellingMsg(language);
                                        }
                                    }
                                }

                                if (highRisk_conditions.optJSONArray("diagnosticrefer") != null) {
                                    JSONArray diagnosticrefer_conditionsArray = highRisk_conditions.optJSONArray("diagnosticrefer");
                                    for (int k = 0; k < diagnosticrefer_conditionsArray.length(); k++) {
                                        JSONObject diagnosticrefer_options_key = diagnosticrefer_conditionsArray.getJSONObject(0);
                                        System.out.println("main_ques_options_key Counseling lang = " + diagnosticrefer_options_key.optString("languages").toString());
                                        ;
                                        highrisklist.put(quesid, "" + formid + delimeter + setid + delimeter + keyword + delimeter + m.getKeyword() + delimeter + diagnosticrefer_options_key.optString("languages").toString() + delimeter + "diagnosticReffer" + delimeter + "0");
                                        referrallist.put(quesid, diagnosticrefer_options_key.optString("languages").toString());
                                    }
                                }

                                if (highRisk_conditions.optJSONArray("patientVisitSummary") != null) {
                                    JSONArray patientVisitSummary_conditionsArray = highRisk_conditions.optJSONArray("patientVisitSummary");
                                    for (int k = 0; k < patientVisitSummary_conditionsArray.length(); k++) {
                                        JSONObject patientVisitSummary_options_key = patientVisitSummary_conditionsArray.getJSONObject(0);
                                        System.out.println("patientVisitSummary_options_key Counseling lang = " + patientVisitSummary_options_key.optString("languages").toString());
                                        ;
                                        patientvisitlist.put(keyword, patientVisitSummary_options_key.optString("languages").toString());
                                    }
                                }

                            } else {
                                highrisklist.remove(quesid);
                                referrallist.remove(quesid);
                                patientvisitlist.remove(keyword);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            if (womendetails.containsKey(keyword)) {
                JSONObject obj;
//                String option = dbhelper.getKeywordlang(womendetails.get(keyword));
                String option = questionInteractor.getDependentQuestionLabel(womendetails.get(keyword));

                if (option != null) {

                    try {
                        obj = new JSONObject(option);
                        Optionlanguage = obj.getString(mAppLanguage);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    spinner.setSelection(arrayAdapter.getPosition(Optionlanguage));
                }

            }

            if (orientation == 1) {
                spinner.setLayoutParams(lpHorizontal);
            }

            ll.addView(spinner);
        }
    }

    public LinearLayout createCheckbox(int i, String quesid, final String formid, final String setid, String language, final String keyword, final String validationfield, String messages, String displayCondition, final int orientation) {

        try {
            JSONObject obj = new JSONObject(language);
            language = obj.getString(mAppLanguage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (messages != null && messages.length() > 0) {
            ConditionLists.put(keyword, messages);
        }

        textId++;
        final TextView tv = new TextView(this);
        tv.setText(language);
        tv.setId(textId);
        // tv.setGravity(Gravity.CENTER);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.text_color));
        tv.setLayoutParams(lp);

        ll.setTag(keyword);

        LinearLayout chkBoxView = new LinearLayout(this);

        if (orientation == 1) {
            ll.setOrientation(LinearLayout.HORIZONTAL);
            chkBoxView.setOrientation(LinearLayout.VERTICAL);
            chkBoxView.setLayoutParams(lpHorizontal);
            tv.setLayoutParams(lpHorizontal);
            ll.addView(tv);
            ll.addView(chkBoxView);

        } else {
            ll.addView(tv);
        }

        if (validationfield != null && validationfield.equalsIgnoreCase("true")) {
            tv.setError("");
            validationlist.put(keyword, "");
            NextButtonvalidationlist.put(keyword, scroll.getId());
        }

//        if (i == 1) {
//            optionList = dbhelper.dependantgetANCEnglishoptions(quesid);  // this dbHelper statement gets q.keyword,q.answer_type,qi.keyword,qi.dependants,qi.depend_lang_eng,qi.depend_lang_mara,qi.action for that specific quesid in english
//        } else {
//            optionList = dbhelper.getANCEnglishoptions(quesid);
//        }

        optionList = questionInteractor.getQuestionOptions(quesid, formID);


        for (int k = 1; k < optionList.size(); k++) {
            final CheckBox cb = new CheckBox(ctx);
            //System.out.println("inside loop"+optionList.get(k).getTagID());
            checkbox = checkbox + 1;


            if (optionList.get(k).getQuestionText() != null && optionList.get(k).getQuestionText().length() > 0) {
                try {
                    JSONObject obj = new JSONObject(optionList.get(k).getQuestionText());
                    Optionlanguage = obj.getString(mAppLanguage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                cb.setText(Optionlanguage);
                cb.setId(checkbox);
                cb.setTextColor(getResources().getColor(R.color.text_color));
                cb.setTag(optionList.get(k).getKeyword());
                cb.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
                cb.setPadding(20, 10, 20, 10);

                if (orientation == 1)
                    chkBoxView.addView(cb);
                else
                    ll.addView(cb);
            }

            //dependantquestion.put(optionList.get(k).getKeyword(), optionList.get(k).getQuesid());


            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    hideSoftKeyboard(v);
                    int parentId = (((ViewGroup) cb.getParent()).getId());

                    if (!view_check.equals(keyword)) {
                        chechboxlist = new ArrayList<String>();

                        if (womendetails.containsKey(keyword)) {
                            String getArrayList = String.valueOf(womendetails.get(keyword));
                            getArrayList = getArrayList.replace("[", "");
                            getArrayList = getArrayList.replace("]", "");

                            String[] words = getArrayList.split("\\, ");

                            for (String s : words) {
                                chechboxlist.add("" + s);

                                System.out.println("s is..... = " + s);
                            }
                        }
                    }

                    if (!(chechboxlist.contains(cb.getTag().toString()))) {
                        chechboxlist.add(cb.getTag().toString());

                        validationlist.remove(keyword);
                        NextButtonvalidationlist.remove(keyword);
                        tv.setError(null);

                    } else {
                        chechboxlist.remove(cb.getTag().toString());

                        if (chechboxlist.isEmpty()) {
                            if (validationfield != null && validationfield.equalsIgnoreCase("true")) {
                                tv.setError("");
                                validationlist.put(keyword, "");
                                NextButtonvalidationlist.put(keyword, scroll_temp.getId());
                            }
                            Backup_answerTyped1.remove(tv.getId());
                            womenanswer.remove(tv.getId());
                            womendetails.remove(keyword);

                        }
                    }

                    womendetails.put(keyword, "" + chechboxlist);
                    view_check = keyword;


                    womenanswer.put(tv.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + chechboxlist);
                    Backup_answerTyped1.put(tv.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + chechboxlist); // this treemap is used to insert dataSource in Backup_entered table.
                    ll_sub = (LinearLayout) v.getParent().getParent();
                }
            });


            if (womendetails.containsKey(keyword)) {
                System.out.println("Retireve Checkbox list ****" + womendetails.get(keyword));

                chechboxlist = new ArrayList<String>();
                Pattern pattern = Pattern.compile("\\W");
                String[] words = pattern.split(womendetails.get(keyword));
                System.out.println("words = " + words);
                for (String s : words) {
                    if (s.equalsIgnoreCase(cb.getTag().toString())) {
                        chechboxlist.add("" + cb.getTag());
                        cb.setChecked(true);
                    }
                    System.out.println("Split using Pattern.split(): " + s);
                }

                if (validationfield != null && validationfield.equalsIgnoreCase("true")) {

                    validationlist.put(keyword, womendetails.get(keyword));
                    tv.setError(null);
                }
            }

            if (displayCondition != null && displayCondition.length() > 0 && womenpreviousEcdetails.containsKey(keyword)) {
                Pattern pattern = Pattern.compile("\\W");
                String[] words = pattern.split(womenpreviousEcdetails.get(keyword));
                System.out.println("words = " + words);
                for (String s : words) {
                    if (s.equalsIgnoreCase(cb.getTag().toString())) {
                        chechboxlist.add("" + cb.getTag());
                        cb.performClick();
                    }
                }
                validationlist.put(keyword, womenpreviousEcdetails.get(keyword));
                //cb.setEnabled(false);
            }
        }
        return ll;

    }

    /**
     * THis method is used to create photo capture Field dynamically
     *
     * @param i                   = for loop counter
     * @param language            = question text
     * @param formid              = formid
     * @param keyword             = question keyword
     * @param validationfield     = this field describes whether question is compulsory or not
     * @param validationcondition = this field describes whether the question contains any condition for validation
     * @param validationmsg       = this field gives the error msg.
     * @param displayCondition
     * @return layout (ll)
     */

    public LinearLayout createCapturePhoto(int i, String language, final String formid, final String setid, final String keyword, final String validationfield, final String validationcondition, final String validationmsg, String messages, String displayCondition) {

        System.out.println("inside createEdittext count" + i + "   keyword" + keyword + "set id +++" + setid + "language==" + language);

        if (messages != null && messages.length() > 0) {
            ConditionLists.put(keyword, messages);
        }

        try {
            System.out.println("language = " + language);
            JSONObject obj = new JSONObject(language);
            language = obj.getString(mAppLanguage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final TextView tv = new TextView(this);
        tv.setText("" + language);
        tv.setId(textId + 1);
        // tv.setGravity(Gravity.CENTER);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.text_color));
        tv.setTag(keyword);
        tv.setLayoutParams(lp);

        iv = new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 450);
        layoutParams.setMargins(30, 30, 30, 30);
        layoutParams.gravity = Gravity.CENTER;
        iv.setLayoutParams(layoutParams);

        final Button capture = new Button(this);
        capture.setText(EnrollmentQuestions.this.getString(R.string.capture_photo_text));
        capture.setTextColor(Color.WHITE);
        capture.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        capture.setPadding(32, 16, 32, 16);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });

        LinearLayout.LayoutParams btnlayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnlayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        capture.setLayoutParams(btnlayoutParams);

//        ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 710));

        ll.addView(tv);
        ll.addView(iv);
        ll.addView(capture);

        return ll;
    }


    /**
     * This method is used to call camera function
     */
    private void captureImage() {

        Name = "" + womendetails.get("lname") + " " + womendetails.get("hname") + " " + womendetails.get("surname");

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }


    /**
     * This methos is used to create and display dependent questions
     *
     * @param dependantList = list of questions
     */
    public void DisplayDependantQuestions(List<Visit> dependantList) {


        List<String> tempdependantStore = new ArrayList<>();

        Visit qstnData = null;

        for (int i = 0; i < dependantList.size(); i++) {
            qstnData = dependantList.get(i);

            ll = new LinearLayout(ctx);
            ll.setLayoutParams(layoutParamQuestion);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setBackground(drawableDependentQstn);
            ll.setPadding(20, 30, 20, 40);
            ll.setTag(dependantList.get(i).getKeyword());


            ll_4layout_sub = (LinearLayout) qstnData.getLl_sub().getParent();
            ll_4layout_sub.addView(ll, ll_4layout_sub.indexOfChild(qstnData.getLl_sub()) + (1));

            String displayCondition = null;

            try {
                if (qstnData.getValidationCondition() != null && qstnData.getValidationCondition().length() > 0) {
                    JSONObject validationobject = null;

                    validationobject = new JSONObject(qstnData.getValidationCondition());

                    if (validationobject.optString("display_condition") != null && validationobject.optString("display_condition").length() > 0) {
                        displayCondition = validationobject.optString("display_condition").toString();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            switch (qstnData.getAnswerType()) {
                case "radio":
                    ll = createdependantRadio(1, String.valueOf(qstnData.getParentQstnId()), qstnData.getQuestionText(), qstnData.getParentQstnKeyword(), qstnData.getFormid(), qstnData.getSetId(), qstnData.getKeyword(), qstnData.getValidationCondition(), qstnData.getMessages(), displayCondition, qstnData.getPageScrollId(), qstnData.getOrientation());
                    break;

                case "select":
                    //ll=createCheckbox(1, text, keyword);
                    ll = createCheckbox(1, String.valueOf(qstnData.getParentQstnId()), qstnData.getFormid(), qstnData.getSetId(), qstnData.getQuestionText(), qstnData.getKeyword(), qstnData.getValidationCondition(), qstnData.getMessages(), displayCondition, qstnData.getOrientation());
                    break;

                case "date":
                    ll = createdependentDate(1, qstnData.getQuestionText(), qstnData.getAnswerType(), qstnData.getParentQstnKeyword(), qstnData.getFormid(), qstnData.getSetId(), qstnData.getKeyword(), qstnData.getValidationCondition(), qstnData.getMessages(), displayCondition, qstnData.getPageScrollId(), qstnData.getOrientation());

                    break;

                case "label":
                case "sublabel":
                    ll = createSubLabel(1, qstnData.getQuestionText(), qstnData.getFormid(), qstnData.getSetId(), qstnData.getKeyword(), "", "", "", "", "");
                    break;

                case "int":
                    ll = createdependentInt(1, qstnData.getQuestionText(), qstnData.getAnswerType(), qstnData.getParentQstnKeyword(), qstnData.getFormid(), qstnData.getSetId(), qstnData.getKeyword(), qstnData.getValidationCondition(), qstnData.getMessages(), displayCondition, qstnData.getPageScrollId(), qstnData.getOrientation());
                    break;

                case "float":
                    ll = createdependentInt(1, qstnData.getQuestionText(), qstnData.getAnswerType(), qstnData.getParentQstnKeyword(), qstnData.getFormid(), qstnData.getSetId(), qstnData.getKeyword(), qstnData.getValidationCondition(), qstnData.getMessages(), displayCondition, qstnData.getPageScrollId(), qstnData.getOrientation());
                    break;

                case "text":
                    ll = createdependentEdittext(1, qstnData.getQuestionText(), qstnData.getAnswerType(), qstnData.getParentQstnKeyword(), qstnData.getFormid(), qstnData.getSetId(), qstnData.getKeyword(), qstnData.getValidationCondition(), qstnData.getMessages(), displayCondition, qstnData.getPageScrollId(), qstnData.getOrientation());

                    break;

                case "time":
                    ll = createdependentTime(1, qstnData.getQuestionText(), qstnData.getAnswerType(), qstnData.getParentQstnKeyword(), qstnData.getFormid(), qstnData.getSetId(), qstnData.getKeyword(), qstnData.getValidationCondition(), qstnData.getMessages(), qstnData.getPageScrollId(), qstnData.getOrientation());

                    break;

                case "counselmsg":
                    ll = createCounsellingLabel(1, qstnData.getQuestionText(), qstnData.getAnswerType(), qstnData.getPageScrollId());
                default:

                    break;
            }

            tempdependantStore.add(qstnData.getKeyword());
        }
        MainQuestempstoredependant.put(qstnData.getParentQstnKeyword(), tempdependantStore);
    }


    /**
     * THis method is used to create dependant edittext dynamically
     *
     * @param i                    = loop id
     * @param language             = dependant question text
     * @param keyword              = main question keyword
     * @param radiotag             = radio tag on which the question is dependant
     * @param formid               = form id current used
     * @param setid                = setid of the question
     * @param dependantQuesKeyword = dependant question keyword
     * @param validationConditions = validations if present on dependant question
     * @param messages             = this field contains json with multiple highrisk,counselling,referral condition.
     * @param displayCondition
     * @param pageScrollId         @return ll
     */
    public LinearLayout createdependentEdittext(int i, String language, final String keyword, final String radiotag, final String formid, final String setid, final String dependantQuesKeyword, final String validationConditions, final String messages, String displayCondition, String pageScrollId, final int orientation) {

        try {
            JSONObject obj = new JSONObject(language);
            language = obj.getString(mAppLanguage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String validation_Conditions;
        validation_Conditions = validationConditions;

        final int ScrollPageId = Integer.parseInt(pageScrollId);


        final TextView tv = new TextView(this);
        tv.setText("" + language);
        tv.setId(textId + 1);
        // tv.setGravity(Gravity.CENTER);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.text_color));

        tv.setLayoutParams(lp);


        edittextID = edittextID + 1;
        final EditText et = new EditText(this);
        et.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_edittext));
        et.setId(edittextID);
        et.setSingleLine(true);
        et.setLongClickable(false);
        et.setTag(dependantQuesKeyword);

//        et.requestFocus();

        if (validationConditions != null && validationConditions.length() > 0) {


            try {
                JSONObject validationobj = new JSONObject(validationConditions);

                if (validationobj.optString("required") != null && validationobj.optString("required").length() > 0) {

                    tv.setError("");
                    validationlist.put("" + et.getTag(), "");
                    NextButtonvalidationlist.put("" + et.getTag(), ScrollPageId);

                }

                if (validationobj.optString("length") != null && validationobj.optString("length").length() > 0) {

                    JSONObject lengthobject = validationobj.getJSONObject("length");
//
                    int maxLength = lengthobject.optInt("max");

                    if (maxLength > 0) {
                        et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        if (womendetails.containsKey(dependantQuesKeyword)) {
            et.setText(womendetails.get(dependantQuesKeyword));

            validationlist.put("" + et.getTag(), womendetails.get(dependantQuesKeyword));
            tv.setError(null);

        }

        et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                System.out.println("createEdittext onFocusChange" + et.getId());
                womendetails.put(dependantQuesKeyword, et.getText().toString().trim());
                womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + dependantQuesKeyword + delimeter + et.getText().toString().trim());        //
                Backup_answerTyped1.put(et.getId(), "" + formid + delimeter + setid + delimeter + dependantQuesKeyword + delimeter + et.getText().toString().trim());


                if (validationlist.containsKey(et.getTag())) {
                    if (et.getText().toString().length() <= 0) {
                        et.setError(EnrollmentQuestions.this.getString(R.string.default_validation_msg));
                        validationlist.put("" + et.getTag(), "");
                        tv.setError("");
                    } else {
                        // validationlist.remove(et.getTag());
                        et.setError(null);
                        tv.setError(null);
                        validationlist.put("" + et.getTag(), et.getText().toString().trim());
                    }
                }


            }
        });


        if (displayCondition != null && displayCondition.length() > 0 && womenpreviousEcdetails.containsKey(dependantQuesKeyword)) {
            et.setText(womenpreviousEcdetails.get(dependantQuesKeyword));
            //et.setEnabled(false);
            womendetails.put(dependantQuesKeyword, et.getText().toString().trim());
            womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + dependantQuesKeyword + delimeter + et.getText().toString().trim());
            Backup_answerTyped1.put(et.getId(), "" + formid + delimeter + setid + delimeter + dependantQuesKeyword + delimeter + et.getText().toString().trim());
            validationlist.put("" + et.getTag(), et.getText().toString().trim());
        }
        //ll.setTag(radiotag);

        if (orientation == 1) {
            ll.setOrientation(LinearLayout.HORIZONTAL);
            tv.setLayoutParams(lpHorizontal);
            et.setLayoutParams(lpHorizontal);
        }

        ll.addView(tv);
        ll.addView(et);

        return ll;
    }


    /**
     * THis method is used to create dependant date dynamically
     *
     * @param i                    = loop id
     * @param language             = dependant question text
     * @param keyword              = main question keyword
     * @param radiotag             = radio tag on which the question is dependant
     * @param formid               = form id current used
     * @param setid                = setid of the question
     * @param dependantQuesKeyword = dependant question keyword
     * @param validationConditions = validations if present on dependant question
     * @param messages             = this field contains json with multiple highrisk,counselling,referral conditions
     * @param PageScrollID         = scroll id of the page on which dependant question is displayed.
     * @return ll
     */
    @SuppressLint("ClickableViewAccessibility")
    public LinearLayout createdependentDate(int i, String language, final String keyword, final String radiotag, final String formid, final String setid, final String dependantQuesKeyword, final String validationConditions, final String messages, final String displayCondition, String PageScrollID, final int orientation) {

        System.out.println("inside method count**" + i + "*** keyword**" + keyword + "***radiotag***" + radiotag + "***dependantQuesKeyword**" + dependantQuesKeyword + "***validationConditions***" + validationConditions);


        final int ScrollPageId = Integer.parseInt(PageScrollID);
        try {
            JSONObject obj = new JSONObject(language);
            language = obj.getString(mAppLanguage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String validation_Conditions;

        validation_Conditions = validationConditions;


        final TextView tv = new TextView(this);
        tv.setText("" + language);
        tv.setId(textId + 1);
        // tv.setGravity(Gravity.CENTER);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.text_color));
        tv.setLayoutParams(lp);


        final String defaultdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        edittextID = edittextID + 1;
        final EditText et = new EditText(this);
        et.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_edittext));
        //et.setText("edittextNo"+i);
        et.setMinLines(1);
        et.setMaxLines(3);
        et.setTag(dependantQuesKeyword);
        et.setId(edittextID);
        et.setLongClickable(false);
        et.setSingleLine(true);
        et.setFocusable(false);
        et.setClickable(true);
//        et.requestFocus();
        Boolean required = false;
        try {
            JSONObject validationobj = new JSONObject(validationConditions);

            if (validationobj.optString("required") != null && validationobj.optString("required").length() > 0) {
                required = true;
                //compulsory=true;
                tv.setError("");
                validationlist.put(dependantQuesKeyword, "");
                //NextButtonvalidationlist.put(""+et.getTag(),runtimevalidationlist.get(et.getId()));
                NextButtonvalidationlist.put(dependantQuesKeyword, ScrollPageId);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final Boolean finalRequired = required;

        if (womendetails.containsKey(dependantQuesKeyword)) {
            et.setText(womendetails.get(dependantQuesKeyword));
            if (!finalRequired)
                et.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear, 0);
            validationlist.put("" + et.getTag(), womendetails.get(dependantQuesKeyword));
            tv.setError(null);

        }

        if (displayCondition != null && displayCondition.length() > 0 && womenpreviousEcdetails.containsKey(dependantQuesKeyword)) {
            try {
                Date dt = serverdateFormatter.parse(womenpreviousEcdetails.get(dependantQuesKeyword));
                et.setText(formatter.format(dt));
                if (!finalRequired)
                    et.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear, 0);
                validationlist.put(dependantQuesKeyword, et.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //et.setEnabled(false);
            womendetails.put(dependantQuesKeyword, et.getText().toString());
            womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + dependantQuesKeyword + delimeter + womenpreviousEcdetails.get(dependantQuesKeyword));
            Backup_answerTyped1.put(et.getId(), "" + formid + delimeter + setid + delimeter + dependantQuesKeyword + delimeter + womenpreviousEcdetails.get(dependantQuesKeyword));

        }


        et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

//				System.out.println("onTouch clicked");
//				showDatePicker();
//
//				fromDatePickerDialog.show();
//
//				return true;
                final int DRAWABLE_RIGHT = 2;
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if ((et.getCompoundDrawables()[DRAWABLE_RIGHT] != null) && (motionEvent.getRawX() >= (et.getRight() - et.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()))) {
                            // your action here
                            et.getText().clear();
                            et.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                            return true;
                        }
                        if (fromDatePickerDialog == null || (!fromDatePickerDialog.isShowing())) {
                            showDatePicker();
//
                            fromDatePickerDialog.show();
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }

                return false;


            }


            public void showDatePicker() {
                //fromDatePickerDialog.show();

                System.out.println("showDatePicker clicked");

                Calendar newCalendar = Calendar.getInstance();
                fromDatePickerDialog = new DatePickerDialog(ctx, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        //System.out.println("newDate.getTime()" + newDate.getTime());

                        et.setText(dateFormatter.format(newDate.getTime()));
                        if (!finalRequired)
                            et.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear, 0);
                        et.setFocusable(false);
                        String getDate = dateFormatter.format(newDate.getTime());
                        //SendServerDate=serverdateFormatter.format(newDate.getTime());
                        try {

                            //System.out.println("SendServerDate====" + SendServerDate);
                            selectedDate = formatter.parse(getDate);
                            serverDate = serverdateFormatter.format(newDate.getTime());
                            System.out.println("serverDate====" + serverDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (SystemDate.compareTo(selectedDate) >= 0) {

                           /* if (dependantQuesKeyword.equalsIgnoreCase("lmp_date")) {

                                Date minDate = new StringToTime(serverdefaultdateFormatter + " - 280 days");
                                Date maxDate = new StringToTime(serverdefaultdateFormatter + " - 028 days");

                                if ((selectedDate.after(minDate) || selectedDate.equals(minDate)) && (selectedDate.before(maxDate) || selectedDate.equals(maxDate))) {
                                    lmpvalid = true;
                                    validationlist.put("" + et.getTag(), et.getText().toString());
                                    et.setError(null);
                                    tv.setError(null);
                                } else {
                                    lmpvalid = false;
                                    System.out.println("lmpvalid***" + lmpvalid);
                                    Toast.makeText(getApplicationContext(), R.string.enter_valid_lmp, Toast.LENGTH_LONG).show();
                                    et.setError("LMP date should not be less then 1 year or more than 1 year from current date");
                                    validationlist.put("" + et.getTag(), "");
                                }
                            } else {*/
                            lmpvalid = true;
                            String myFormat = "yyyy-MM-dd"; //In which you need put here
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                            System.out.println("dependantQuesKeyword = " + dependantQuesKeyword);
                            storeEnteredData.put(dependantQuesKeyword, sdf.format(newDate.getTime()).toString());
                            System.out.println("storeEnteredData = " + storeEnteredData.get(dependantQuesKeyword));
                            validationlist.remove("" + et.getTag());
                            NextButtonvalidationlist.remove("" + et.getTag());
                            tv.setError(null);
                            et.setError(null);
                            //}

                        } else {
                            tv.setError("");
                            et.setError(getApplicationContext().getString(R.string.exceed_curnt_dt));
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.exceed_curnt_dt), Toast.LENGTH_LONG).show();
                            validationlist.put("" + et.getTag(), "");
                            NextButtonvalidationlist.put("" + et.getTag(), ScrollPageId);
                        }

                        womendetails.put(dependantQuesKeyword, serverdateFormatter.format(newDate.getTime()));
                        womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + dependantQuesKeyword + delimeter + serverDate);        //

                        Backup_answerTyped1.put(et.getId(), "" + formid + delimeter + setid + delimeter + dependantQuesKeyword + delimeter + et.getText().toString());

                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

            }


        });

        if (orientation == 1) {
            ll.setOrientation(LinearLayout.HORIZONTAL);
            tv.setLayoutParams(lpHorizontal);
            et.setLayoutParams(lpHorizontal);
        }

        ll.addView(tv);
        ll.addView(et);

        return ll;
    }


    /**
     * THis method is used to create dependant int dynamically
     *
     * @param i                    = loop id
     * @param language             = dependant question text
     * @param answerType           = main question keyword
     * @param radiotag             = radio tag on which the question is dependant
     * @param formid               = form id current used
     * @param setid                = setid of the question
     * @param dependantQuesKeyword = dependant question keyword
     * @param validationConditions = validations if present on dependant question
     * @param messages             = this field contains json with multiple highrisk,counselling,referral conditions
     * @param PageScrollID         = scroll id of the page on which dependant question is displayed.
     * @return ll
     */
    public LinearLayout createdependentInt(int i, String language, final String answerType, final String radiotag, final String formid, final String setid, final String dependantQuesKeyword, final String validationConditions, final String messages, final String displayCondition, String PageScrollID, final int orientation) {
        final int ScrollPageId = Integer.parseInt(PageScrollID);

        try {
            JSONObject obj = new JSONObject(language);
            language = obj.getString(mAppLanguage);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String validation_Conditions;
        Boolean compulsory = false;

        validation_Conditions = validationConditions;

        final TextView tv = new TextView(this);
        tv.setText("" + language);
        tv.setId(textId + 1);
        // tv.setGravity(Gravity.CENTER);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.text_color));
        tv.setLayoutParams(lp);


        edittextID = edittextID + 1;
        final EditText et = new EditText(this);
        et.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_edittext));
        et.setTag(dependantQuesKeyword);
        et.setId(edittextID);
        et.setLongClickable(false);
        et.setSingleLine(true);
        et.setInputType(InputType.TYPE_CLASS_NUMBER);
//        et.requestFocus();

        switch (answerType) {
            case "int":
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;

            default:
                et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                et.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(4, 2)});
                break;
        }

        try {
            JSONObject validationobj = new JSONObject(validationConditions);

            if (validationobj.optString("required") != null && validationobj.optString("required").length() > 0) {

                compulsory = true;
                tv.setError("");
                validationlist.put("" + et.getTag(), "");
                NextButtonvalidationlist.put("" + et.getTag(), ScrollPageId);

            }

            if (validationobj.optString("length") != null && validationobj.optString("length").length() > 0) {

                JSONObject lengthobject = validationobj.getJSONObject("length");
//
                int maxLength = lengthobject.optInt("max");

                if (maxLength > 0) {
                    et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (womendetails.containsKey(dependantQuesKeyword)) {

            et.setText(womendetails.get(dependantQuesKeyword));

            validationlist.put("" + et.getTag(), womendetails.get(dependantQuesKeyword));
            tv.setError(null);
            et.setError(null);

        }

        if (displayCondition != null && displayCondition.length() > 0 && womenpreviousEcdetails.containsKey(dependantQuesKeyword)) {
            et.setText(womenpreviousEcdetails.get(dependantQuesKeyword));
            //et.setEnabled(false);
            tv.setError(null);
            womendetails.put(dependantQuesKeyword, et.getText().toString());
            validationlist.put("" + et.getTag(), womenpreviousEcdetails.get(dependantQuesKeyword));
            womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + dependantQuesKeyword + delimeter + et.getText().toString());
            Backup_answerTyped1.put(et.getId(), "" + formid + delimeter + setid + delimeter + dependantQuesKeyword + delimeter + et.getText().toString());
            womendetails.put(dependantQuesKeyword, et.getText().toString());
        }

        et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        try {

                            calculations(validationConditions, messages, dependantQuesKeyword, et);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });


        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                calculations(validationConditions, messages, dependantQuesKeyword, et);

                if (dependantQuesKeyword.equals(WOMAN_AGE) && !hasFocus) {
                    if (et.getText().toString().isEmpty()) {
                        womendetails.remove(WOMAN_DOB);
                    } else {
                        LocalDate date = LocalDate.now().minusYears(Integer.parseInt(et.getText().toString()));
                        womendetails.put(WOMAN_DOB, String.valueOf(date));
                    }
                }
            }
        });

        final Boolean finalIsCompulsory = compulsory;

        et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {

                try {

                    if (validationlist.containsKey(et.getTag())) {
                        if (et.getText().toString().equals("")) {
                            //et.setError("");
                            tv.setError("");

                            validationlist.put("" + et.getTag(), "");
                            NextButtonvalidationlist.put("" + et.getTag(), ScrollPageId);
                        } else {
                            validationlist.put("" + et.getTag(), et.getText().toString());
                        }
                    }

                    if (validation_Conditions != null && validation_Conditions.length() > 0) {


                        if (et.getText().toString().length() > 0 && (length_min <= et.getText().toString().length()) && (et.getText().toString().length() <= length_max)) {
                            et.setError(null);
                            tv.setError(null);

                            validationlist.remove("" + et.getTag());
                            NextButtonvalidationlist.remove("" + et.getTag());
                        } else {
                            et.setError(length_lang);
                            tv.setError("");
                            //next.setEnabled(false);

                            validationlist.put("" + et.getTag(), "");
                            NextButtonvalidationlist.put("" + et.getTag(), ScrollPageId);
                        }

                        if (range_min != 0 && et.getText().toString().length() > 0) {
                            if ((range_min <= Double.parseDouble(et.getText().toString())) && (Double.parseDouble(et.getText().toString()) <= range_max)) {
                                et.setError(null);
                                tv.setError(null);

                                validationlist.put("" + et.getTag(), et.getText().toString());

                            } else {
                                tv.setError("");
                                et.setError(range_lang);

                                validationlist.put("" + et.getTag(), "");
                                NextButtonvalidationlist.put("" + et.getTag(), ScrollPageId);
                            }
                        }
                    }

                    StorePatientVisitHighRiskDiagnosticValues(et.getText().toString(), et, dependantQuesKeyword, setid);

                    if (dependantQuesKeyword.equalsIgnoreCase(gplaKeywordArray[0]) || dependantQuesKeyword.equalsIgnoreCase(gplaKeywordArray[1]) ||
                            dependantQuesKeyword.equalsIgnoreCase(gplaKeywordArray[2]) || dependantQuesKeyword.equalsIgnoreCase(gplaKeywordArray[3])) {
                        if (!et.getText().toString().isEmpty())
                            gplaList.put(dependantQuesKeyword, et.getText().toString());
                        else
                            gplaList.remove(dependantQuesKeyword);
                        int g, p, l, a;
                        if (gplaList.containsKey(gplaKeywordArray[0])) {
                            g = Integer.parseInt(gplaList.get(gplaKeywordArray[0]));
                            if (gplaList.containsKey(gplaKeywordArray[3])) {
                                a = Integer.parseInt(gplaList.get(gplaKeywordArray[3]));
                                if (g >= a) {
                                    gplaValidationTrue(gplaKeywordArray[3]);
                                } else {
                                    gplaValidationFalse(gplaKeywordArray[3], ScrollPageId, "A can not be greater than G");
                                }
                            }
                            if (gplaList.containsKey(gplaKeywordArray[1])) {
                                p = Integer.parseInt(gplaList.get(gplaKeywordArray[1]));
                                if (g >= p) {
                                    gplaValidationTrue(gplaKeywordArray[1]);
                                } else {
                                    gplaValidationFalse(gplaKeywordArray[1], ScrollPageId, "P can not be greater than G");
                                }
                                if (gplaList.containsKey(gplaKeywordArray[3])) {
                                    a = Integer.parseInt(gplaList.get(gplaKeywordArray[3]));
                                    if (g >= p + a) {
                                        gplaValidationTrue(gplaKeywordArray[1]);
                                        gplaValidationTrue(gplaKeywordArray[3]);
                                        if (g >= p) {
                                            gplaValidationTrue(gplaKeywordArray[1]);
                                        } else {
                                            gplaValidationFalse(gplaKeywordArray[1], ScrollPageId, "P can not be greater than G");
                                        }
                                    } else {
                                        gplaValidationFalse(gplaKeywordArray[1], ScrollPageId, "P+A can not be greater than G");
                                        gplaValidationFalse(gplaKeywordArray[3], ScrollPageId, "P+A can not be greater than G");
                                    }
                                }
                            }
                            if (gplaList.containsKey(gplaKeywordArray[2])) {
                                l = Integer.parseInt(gplaList.get(gplaKeywordArray[2]));
                                if (g >= l) {
                                    gplaValidationTrue(gplaKeywordArray[2]);
                                } else {
                                    gplaValidationFalse(gplaKeywordArray[2], ScrollPageId, "L can not be greater than G");
                                }
                                if (gplaList.containsKey(gplaKeywordArray[1])) {
                                    p = Integer.parseInt(gplaList.get(gplaKeywordArray[1]));
                                    if (p >= l) {
                                        gplaValidationTrue(gplaKeywordArray[2]);
                                        if (g >= l) {
                                            gplaValidationTrue(gplaKeywordArray[2]);
                                        } else {
                                            gplaValidationFalse(gplaKeywordArray[2], ScrollPageId, "L can not be greater than G");
                                        }
                                    } else {
                                        gplaValidationFalse(gplaKeywordArray[2], ScrollPageId, "L can not be greater than P");
                                    }
                                }

                            }


                        }

                    }


                    if (et.getText().toString().equals("")) {
                        if (finalIsCompulsory) {
                            tv.setError("");
                            validationlist.put("" + et.getTag(), "");
                            NextButtonvalidationlist.put("" + et.getTag(), ScrollPageId);
                            Backup_answerTyped1.remove(et.getId());
                            womenanswer.remove(et.getId());
                            womendetails.remove(dependantQuesKeyword);

                        } else {
                            tv.setError(null);
                            et.setError(null);
                            validationlist.remove(dependantQuesKeyword);
                            NextButtonvalidationlist.remove(dependantQuesKeyword);
                            Backup_answerTyped1.remove(et.getId());
                            womenanswer.remove(et.getId());
                            womendetails.remove(dependantQuesKeyword);
                        }

                    }

                    womendetails.put(dependantQuesKeyword, et.getText().toString());
                    womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + dependantQuesKeyword + delimeter + et.getText().toString());        //

                    Backup_answerTyped1.put(et.getId(), "" + formid + delimeter + setid + delimeter + dependantQuesKeyword + delimeter + et.getText().toString());


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        });

        if (orientation == 1) {
            ll.setOrientation(LinearLayout.HORIZONTAL);
            tv.setLayoutParams(lpHorizontal);
            et.setLayoutParams(lpHorizontal);
        }

        ll.addView(tv);
        ll.addView(et);


        return ll;
    }


    private void gplaValidationFalse(String keyword, Integer scrollPageId, String errorText) {
        validationlist.put(keyword, "");
        NextButtonvalidationlist.put(keyword, scrollPageId);
        LinearLayout linearLayout = (LinearLayout) Frame.findViewWithTag(keyword);
        EditText tempEditText = (EditText) linearLayout.getChildAt(1);
        TextView tempTextView = (TextView) linearLayout.getChildAt(0);
        tempEditText.setError(errorText);
        tempTextView.setError("");
    }

    private void gplaValidationTrue(String keyword) {
        validationlist.remove(keyword);
        NextButtonvalidationlist.remove(keyword);
        LinearLayout linearLayout = (LinearLayout) Frame.findViewWithTag(keyword);
        EditText tempEditText = (EditText) linearLayout.getChildAt(1);
        TextView tempTextView = (TextView) linearLayout.getChildAt(0);
        tempEditText.setError(null);
        tempTextView.setError(null);
    }

    /**
     * THis method is used to create dependant date dynamically
     *
     * @param i                    = loop id
     * @param language             = dependant question text
     * @param keyword              = main question keyword
     * @param formid               = form id current used
     * @param setid                = setid of the question
     * @param dependantQuesKeyword = dependant question keyword
     * @param validationConditions = validations if present on dependant question
     * @param messages             = this field contains json with multiple highrisk,counselling,referral conditions
     * @param displayCondition
     */
    public LinearLayout createdependantRadio(int i, final String quesid, String language, final String keyword, final String formid, final String setid, final String dependantQuesKeyword, final String validationConditions, final String messages, final String displayCondition, final String PageScrollID, final int orientation) {

        final Integer includechild = Integer.parseInt(quesid);

        final int ScrollPageId = Integer.parseInt(PageScrollID);

        try {
            JSONObject obj = new JSONObject(language);
            Optionlanguage = obj.getString(mAppLanguage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final TextView tv = new TextView(this);
        tv.setText("" + Optionlanguage);
        tv.setId(textId + 1);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.text_color));
        tv.setLayoutParams(lp);


        //radio=radio+1;
        radiogroup = radiogroup + 1;


//        optionList = dbhelper.dependantgetANCEnglishoptions(quesid);
        optionList = questionInteractor.getQuestionOptions(quesid, formID);

        JSONObject validationJsonObject = null;
        try {
            if (validationConditions != null && validationConditions.length() > 0) {
                validationJsonObject = new JSONObject(validationConditions);

                if (validationJsonObject.optString("required") != null && validationJsonObject.optString("required").length() > 0) {

                    tv.setError("");
                    validationlist.put(dependantQuesKeyword, "");
                    NextButtonvalidationlist.put(dependantQuesKeyword, ScrollPageId);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final RadioGroup rg = new RadioGroup(this); //create the RadioGroup
        rg.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
        rg.setId(radiogroup);
//        rg.setPadding(20, 10, 20, 10);
        rg.setLayoutParams(segmentedBtnLp);


        if (orientation == 1) {
//            rg.setPadding(20, 0, 0, 0);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            tv.setLayoutParams(lpHorizontal);
            rg.setLayoutParams(lpHorizontal);
            rg.setOrientation(RadioGroup.VERTICAL);
        }

        ll.setTag(dependantQuesKeyword);
        ll.addView(tv);

        if (optionList.size() < 4) {
            ll.addView(rg);

            for (int k = 1; k < optionList.size(); k++) {
                radio = radio + 1;

                try {
                    if (optionList.get(k).getQuestionText() != null && optionList.get(k).getQuestionText().length() > 0) {
                        JSONObject obj = new JSONObject(optionList.get(k).getQuestionText());
                        Optionlanguage = obj.getString(mAppLanguage);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final RadioButton rb = new RadioButton(ctx);
                // rb[k]  = new RadioButton(this);
                rb.setText("" + Optionlanguage);
                rb.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
                rb.setTextColor(getResources().getColor(R.color.text_color));
                rb.setTag(optionList.get(k).getKeyword());
                dependantquestion.put(optionList.get(k).getKeyword(), optionList.get(k).getQuesid());
                rb.setId(radio);
                rb.setPadding(20, 10, 20, 10);

                rg.addView(rb);

                if (orientation == 0) rb.setLayoutParams(lparams);


                rb.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        hideSoftKeyboard(v);

                        int id = rg.getCheckedRadioButtonId();
                        int parentId = (((ViewGroup) rb.getParent()).getId());


                        rb.setChecked(true);

                        womendetails.put(dependantQuesKeyword, "" + rb.getTag());

                        womenanswer.put(rg.getId(), "" + formid + delimeter + setid + delimeter + dependantQuesKeyword + delimeter + rb.getTag());        //

//                        String Messages = dbhelper.getHighRiskConditionForRadio("" + rb.getTag());
                        String Messages = questionInteractor.getHighRiskCondition("" + rb.getTag());

                        if (validationlist.containsKey(dependantQuesKeyword)) {
                            tv.setError(null);
                            validationlist.put(dependantQuesKeyword, rb.getTag().toString());
                        }


                        /**
                         * This logic is used to check whether there is any High risk,Counselling or Diagnostic referral on button click
                         */
                        try {
                            if (Messages != null && Messages.length() > 0) {
                                StoredHighRiskRanges = new ArrayList<>();

                                JSONObject highRisk_conditions = new JSONObject(Messages);
                                if (highRisk_conditions.optJSONArray("highrisk") != null) {
                                    JSONArray highRisk_conditionsArray = highRisk_conditions.optJSONArray("highrisk");

                                    //System.out.println("jsonArray3 options"+jsonArray3.length());
                                    for (int k = 0; k < highRisk_conditionsArray.length(); k++) {
                                        JSONObject main_ques_options_key = highRisk_conditionsArray.getJSONObject(0);

                                        highrisklist.put(quesid, "" + formid + delimeter + setid + delimeter + keyword + delimeter + rb.getTag() + delimeter + main_ques_options_key.optString("languages").toString() + delimeter + "highrisk" + delimeter + "0");
                                    }
                                }

                                if (highRisk_conditions.optJSONArray("counselling") != null) {
                                    JSONArray counseling_conditionsArray = highRisk_conditions.optJSONArray("counselling");
                                    for (int k = 0; k < counseling_conditionsArray.length(); k++) {
                                        JSONObject main_ques_options_key = counseling_conditionsArray.getJSONObject(0);
                                    }
                                }

                                if (highRisk_conditions.optJSONArray("diagnosticrefer") != null) {
                                    JSONArray diagnosticrefer_conditionsArray = highRisk_conditions.optJSONArray("diagnosticrefer");
                                    for (int k = 0; k < diagnosticrefer_conditionsArray.length(); k++) {
                                        JSONObject diagnosticrefer_options_key = diagnosticrefer_conditionsArray.getJSONObject(0);
                                        ;
                                        highrisklist.put(quesid, "" + formid + delimeter + setid + delimeter + keyword + delimeter + rb.getTag() + delimeter + diagnosticrefer_options_key.optString("languages").toString() + delimeter + "diagnosticReffer" + delimeter + "0");
                                        referrallist.put(quesid, diagnosticrefer_options_key.optString("languages").toString());
                                    }
                                }


                                if (highRisk_conditions.optJSONArray("patientVisitSummary") != null) {
                                    JSONArray patientVisitSummary_conditionsArray = highRisk_conditions.optJSONArray("patientVisitSummary");
                                    for (int k = 0; k < patientVisitSummary_conditionsArray.length(); k++) {
                                        JSONObject patientVisitSummary_options_key = patientVisitSummary_conditionsArray.getJSONObject(0);
                                        ;
                                        patientvisitlist.put(keyword, patientVisitSummary_options_key.optString("languages").toString());
                                    }
                                }


                            } else {
                                highrisklist.remove(quesid);
                                referrallist.remove(quesid);
                                patientvisitlist.remove(keyword);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        ll_sub = (LinearLayout) v.getParent().getParent();

//                        dependantList = dbhelper.getDependant_Eng_queslist("" + rb.getTag(), formid, ll_sub, dependantQuesKeyword, String.valueOf(scroll_temp.getId()));
                        dependantList = questionInteractor.getDependantQuesList("" + rb.getTag(), formID, ll_sub, dependantQuesKeyword, String.valueOf(scroll_temp.getId()));

                        /**
                         * This if condition is used to check whether dependant question is present or not
                         * if dependantList size is 0 or null means question dosen't have any depandant question.
                         */
                        if (dependantList != null && dependantList.size() > 0) {

                            if (dependantKeywordPresent.containsValue("" + rb.getTag())) {

                            } else {
                                LinearLayout ll_4 = (LinearLayout) v.getParent().getParent().getParent();
                                removeDependent(ll_4, dependantQuesKeyword);
                                dependantKeywordPresent.put(dependantQuesKeyword, "" + rb.getTag());

                                /**
                                 * This if condition is used to check whether dependant layout is displayed or not
                                 * this is used as a validation that if once the layout is displayed then again clicking on the same button twice the layout should be displayed only once
                                 * for eg. if tt2yes is clicked for the first time then dependant layout should be displayed but again clicking on it dependant layout should not be displayed.
                                 */

                                if (!dependantLayout.containsKey(dependantQuesKeyword) && !(MainQuestempstoredependant.containsKey(dependantQuesKeyword))) {
                                    ArrayList tempdependantStore = new ArrayList<String>();
                                    layoutids.put("" + rb.getTag(), "" + ll_sub);    // this hashmap is used to store the layout id for the clicked button (for eg: tt2yes,android.widget.LinearLayout{21abf298 V.E...C. ......ID 40,646-560,769})

                                    DisplayDependantQuestions(dependantList);

                                }

                            }

                        } else {

                            LinearLayout ll_4 = (LinearLayout) v.getParent().getParent().getParent();
                            removeDependent(ll_4, dependantQuesKeyword);

                            dependantKeywordPresent.remove(dependantQuesKeyword);
                        }
                    }
                });
            }

            if (womendetails.containsKey(dependantQuesKeyword)) {
                for (int j = 0; j < rg.getChildCount(); j++) {
                    RadioButton rBtn = (RadioButton) rg.getChildAt(j);

                    if (rBtn.getTag().equals(womendetails.get(dependantQuesKeyword))) {
                        rBtn.setChecked(true);
                        rBtn.performClick();
                        break;
                    }
                }

            }

        } else if (optionList.size() == 4) {
            createButton(quesid, dependantQuesKeyword, setid, rg.getId(), tv, formid, optionList, displayCondition, String.valueOf(scroll_temp.getId()), orientation);
        } else {
            multipleRadioButton(quesid, dependantQuesKeyword, setid, rg.getId(), tv, formid, orientation);
        }
        return ll;
    }


    /**
     * THis method is used to create dependant label dynamically
     *
     * @param i            = loop id
     * @param language     = dependant question text
     * @param keyword      = dependent question keyword
     * @param pageScrollId = scroll id of the page on which dependant question is displayed
     * @return ll
     */
    public LinearLayout createCounsellingLabel(int i, String language, final String keyword, String pageScrollId) {

        try {
            JSONObject obj = new JSONObject(language);
            language = obj.getString(mAppLanguage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final TextView tv = new TextView(this);
        tv.setText("" + language);
        tv.setId(textId + 1);
        // tv.setGravity(Gravity.CENTER);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.text_color));

        tv.setLayoutParams(lp);

        //villageList=dbhelper.getVillageList();

        final Button b = new Button(this);
        b.setText("click here to squueze");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("");
            }
        });


        ll.addView(tv);
        ll.addView(b);
        return ll;
    }


    /**
     * This method is used to create dependant time layout dynamically
     *
     * @param i                    = loop id
     * @param language             = dependant question text
     * @param keyword              = dependent question keyword
     * @param radiotag             = not in use for this
     * @param formid               = form id current used
     * @param setid                = setid of the question
     * @param dependantQuesKeyword = dependant question keyword
     * @param validationConditions = validations if present on dependant question
     * @param messages             = this field contains json with multiple highrisk,counselling,referral conditions
     * @param PageScrollID         = scroll id of the page on which dependant question is displayed
     * @return ll
     */
    public LinearLayout createdependentTime(int i, String language, final String keyword, final String radiotag, final String formid, final String setid, final String dependantQuesKeyword, final String validationConditions, final String messages, final String PageScrollID, final int orientation) {

        final int ScrollPageId = Integer.parseInt(PageScrollID);

        try {
            JSONObject obj = new JSONObject(language);
            language = obj.getString(mAppLanguage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final TextView tv = new TextView(this);
        tv.setText("" + language);
        tv.setId(textId + 1);
        // tv.setGravity(Gravity.CENTER);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.text_color));
        tv.setLayoutParams(lp);


        String defaultdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        edittextID = edittextID + 1;
        final EditText et = new EditText(this);
        et.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_edittext));
        //et.setText("edittextNo"+i);
        et.setMinLines(1);
        et.setMaxLines(3);
        //et.setHint(defaultdate);
        et.setId(edittextID);
        et.setFocusable(false);
        et.setClickable(true);
        et.setLongClickable(false);
        et.setSingleLine(true);
        et.setTag(dependantQuesKeyword);
        //System.out.println("createDate"+et.getId());
//        et.requestFocus();

        runtimevalidationlist.put(et.getId(), Integer.parseInt(PageScrollID));


        if (messages != null && messages.length() > 0) {
            ConditionLists.put(keyword, messages);
        }

        try {
            JSONObject validationobj = new JSONObject(validationConditions);
            if (validationobj.optString("required") != null && validationobj.optString("required").length() > 0) {

                //compulsory=true;
                tv.setError("");
                validationlist.put("" + et.getTag(), "");
                NextButtonvalidationlist.put("" + et.getTag(), ScrollPageId);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        showDatePicker();
//
                        //mTimePicker.show();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }

                return false;


            }

            public void showDatePicker() {
                //fromDatePickerDialog.show();

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EnrollmentQuestions.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        //et.setText( selectedHour + ":" + selectedMinute);
                        boolean isPM = (selectedHour >= 12);
                        et.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, selectedMinute, isPM ? "PM" : "AM"));

                        validationlist.remove("" + et.getTag());
                        NextButtonvalidationlist.remove("" + et.getTag());
                        tv.setError(null);
                    }
                }, hour, minute, false);//Yes 12 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }


        });


        et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                womendetails.put(keyword, et.getText().toString());
                womenanswer.put(et.getId(), "" + formid + delimeter + setid + delimeter + dependantQuesKeyword + delimeter + et.getText().toString()); // this hashmap is used to insert dataSource in AnswerEntered table
                Backup_answerTyped1.put(et.getId(), "" + formid + delimeter + setid + delimeter + dependantQuesKeyword + delimeter + et.getText().toString()); // this hashmap is used to insert dataSource in Backup_AnswerEntered

                if (et.getText().toString().length() <= 0) {
                    tv.setError("");
                } else {
                    tv.setError(null);
                }


            }
        });

        if (orientation == 1) {
            ll.setOrientation(LinearLayout.HORIZONTAL);
            tv.setLayoutParams(lpHorizontal);
            et.setLayoutParams(lpHorizontal);
        }

        ll.addView(tv);
        ll.addView(et);

        return ll;
    }


    /**
     * Receiving activity result method will be called after closing the camera
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                photo = (Bitmap) data.getExtras().get("dataSource");
                iv.setImageBitmap(photo);

                LinearLayout sanket = (LinearLayout) iv.getParent();
                TextView tvs = (TextView) sanket.getChildAt(0);
                tvs.setError(null);
                validationlist.put("woman_capture_photo", "photo Captured");


            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                /*Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();*/
            } else {
                // failed to capture image
                /*Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();*/
            }
        } else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // video successfully recorded
                // preview the recorded video
                previewVideo();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled recording
                /*Toast.makeText(getApplicationContext(),
                        "User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();*/
            } else {
                // failed to record video
                /*Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();*/
            }
        }
    }

    /**
     * Display image from a path to ImageView
     */
    private void previewCapturedImage() {
        try {
            // hide video preview


            iv.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;


//			Matrix matrix = new Matrix();
//			matrix.postRotate(90);
////			bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(HomePage._uri));
////			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
////			i.setImageBitmap(bitmap);

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);


            iv.setImageBitmap(bitmap);
            iv.setRotation(90);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Previewing recorded video
     */
    private void previewVideo() {
        try {
            // hide image preview
            iv.setVisibility(View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ------------ Helper Methods ----------------------
     * */

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory() + "/MCTS/Photos");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file firstName
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + Name + ".png");


        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


    /**
     * This method is used parsed high risk ranges,counselling ranges and patient visit summary ranges.
     *
     * @param validationConditions = validations present on the questions
     * @param messages             = high risk,counselling,referral ranges
     * @param dependantQuesKeyword = keyword of the dependant question
     * @param et                   = edittext field
     */
    public void calculations(String validationConditions, String messages, String dependantQuesKeyword, EditText et) {
        try {

            StoredHighRiskRanges = new ArrayList<>();
            StoredCounsellingRanges = new ArrayList<>();
            StoredPatientVisitSummaryRanges = new ArrayList<>();

            JSONObject validationJsonObject = null;

            try {
                if (validationConditions != null && validationConditions.length() > 0) {
                    validationJsonObject = new JSONObject(validationConditions);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (validationJsonObject.optString("range") != null && validationJsonObject.optString("range").length() > 0) {
                JSONObject rangeobject = null;
                try {
                    rangeobject = validationJsonObject.getJSONObject("range");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//
                range_min = rangeobject.optDouble("min");
                range_max = rangeobject.optDouble("max");
                range_lang = rangeobject.optString("languages").toString();
                try {
                    JSONObject obj = new JSONObject(range_lang);
                    range_lang = obj.getString(mAppLanguage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                range_min = Double.valueOf(0);
                range_max = Double.valueOf(0);
                range_lang = "";
            }

            if (validationJsonObject.optString("length") != null && validationJsonObject.optString("length").length() > 0) {

                JSONObject lengthobject = null;
                try {
                    lengthobject = validationJsonObject.getJSONObject("length");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//
                length_min = lengthobject.optDouble("min");
                length_max = lengthobject.optDouble("max");
                length_lang = lengthobject.optString("languages").toString();
                try {
                    JSONObject obj = new JSONObject(length_lang);
                    length_lang = obj.getString(mAppLanguage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                length_min = Double.valueOf(0);
                length_max = Double.valueOf(0);
                length_lang = "";
            }

            StorePVSmsgs(messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to store high risk,counselling, referral ranges and it's error messages after parsing in their respective hash list
     *
     * @param messages = json that contains high risk,counselling, referral ranges and it's error messages.
     */
    public void StorePVSmsgs(String messages) {
        try {
            if (messages != null && messages.length() > 0) {
                JSONObject highRisk_conditions = new JSONObject(messages);


                if (highRisk_conditions.optJSONArray("highrisk") != null) {
                    JSONArray highRisk_conditionsArray = highRisk_conditions.optJSONArray("highrisk");


                    for (int k = 0; k < highRisk_conditionsArray.length(); k++) {
                        JSONObject main_ques_options_key = highRisk_conditionsArray.getJSONObject(k);

                        //System.out.println("main_ques_options_key range = " +" key....."+keyword+"    "+ main_ques_options_key.optString("range").toString());;

                        if (main_ques_options_key.getJSONObject("range") != null && main_ques_options_key.getJSONObject("range").length() > 0) {
                            JSONObject parseRangeminmax = main_ques_options_key.getJSONObject("range");

                            Visits highriskcond = new Visits(parseRangeminmax.optDouble("min"), parseRangeminmax.optDouble("max"), main_ques_options_key.optString("languages").toString(), "");

                            //System.out.println("main_ques_options_key languages = " + main_ques_options_key.optString("languages").toString());;

                            StoredHighRiskRanges.add(highriskcond);
                        }

                    }
                }

                if (highRisk_conditions.optJSONArray("counselling") != null) {
                    JSONArray counselling_conditionsArray = highRisk_conditions.optJSONArray("counselling");


                    for (int k = 0; k < counselling_conditionsArray.length(); k++) {
                        JSONObject main_ques_options_key = counselling_conditionsArray.getJSONObject(k);
                        //System.out.println("main_ques_options_key range = " +" key....."+keyword+"    "+ main_ques_options_key.optString("range").toString());;
                        if (main_ques_options_key.getJSONObject("range") != null && main_ques_options_key.getJSONObject("range").length() > 0) {
                            JSONObject parseRangeminmax = main_ques_options_key.getJSONObject("range");

                            Visits counsellingcond = new Visits(parseRangeminmax.optDouble("min"), parseRangeminmax.optDouble("max"), main_ques_options_key.optString("languages").toString(), main_ques_options_key.optString("show_popup"));

                            //System.out.println("main_ques_options_key languages = " + main_ques_options_key.optString("languages").toString());;

                            StoredCounsellingRanges.add(counsellingcond);
                        }

                    }
                }


                if (highRisk_conditions.optJSONArray("patientVisitSummary") != null) {
                    JSONArray patientVisitSummary_conditionsArray = highRisk_conditions.optJSONArray("patientVisitSummary");


                    for (int k = 0; k < patientVisitSummary_conditionsArray.length(); k++) {
                        JSONObject patientVisitSummary_conditionsArray_options_key = patientVisitSummary_conditionsArray.getJSONObject(k);


                        if (patientVisitSummary_conditionsArray_options_key.has("range")) {
                            JSONObject parseRangeminmax = patientVisitSummary_conditionsArray_options_key.getJSONObject("range");

                            Visits patientVisitSummarycond = new Visits(parseRangeminmax.optDouble("min"), parseRangeminmax.optDouble("max"), patientVisitSummary_conditionsArray_options_key.optString("languages").toString(), "");
                            StoredPatientVisitSummaryRanges.add(patientVisitSummarycond);
                        } else {
                            Visits patientVisitSummarycond = new Visits(0, 0, patientVisitSummary_conditionsArray_options_key.optString("languages").toString(), "");

                            StoredPatientVisitSummaryRanges.add(patientVisitSummarycond);
                        }

                    }
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to store high risk,counselling, referral  messages in their respective lists so as to display the values in Patient Visit Summary,Patient Visit Summary-HighRisk Referral,Patient Visit Summary-Diagnostic Referral dialogs
     *
     * @param editTextValue = enter value in editText field
     * @param et            = edittext widget
     * @param keyword       = keyword of the question
     * @param setid         = setid of the question.
     */
    public void StorePatientVisitHighRiskDiagnosticValues(String editTextValue, EditText et, String keyword, String setid) {
        if (StoredCounsellingRanges != null && StoredCounsellingRanges.size() > 0) {
            if (editTextValue != null && editTextValue.length() > 0) {


                Double value = Double.parseDouble(editTextValue);

                for (int i = 0; i < StoredCounsellingRanges.size(); i++) {

                    Double rangeMax = StoredCounsellingRanges.get(i).getRangemax();
                    Double rangeMin = StoredCounsellingRanges.get(i).getRangemin();
                    String counsellinglang = StoredCounsellingRanges.get(i).getRangeLang();


                    if (value >= rangeMin && value <= rangeMax) {
                        LinearLayout ll_1 = (LinearLayout) et.getParent();
//                        ll_1.setBackgroundColor(ctx.getResources().getColor(R.color.highrisk));

                        try {
                            if (counsellinglang != null && counsellinglang.length() > 0) {
                                if (!counclingMsgQstnKeywords.contains(keyword)) {
                                    JSONObject obj = new JSONObject(counsellinglang);
                                    String language = obj.getString(mAppLanguage);
                                    LinearLayout ll4_1 = (LinearLayout) ll_1.getParent();
//									LinearLayout ll_1= (LinearLayout) et.getParent();

                                    language = language.replace("\\n ", "\n");//
                                    TextView counslingMsgTextView = new TextView(ctx);
                                    counslingMsgTextView.setText(language);
                                    counslingMsgTextView.setBackgroundColor(ctx.getResources().getColor(R.color.dependent_question_background));
                                    counslingMsgTextView.setTextColor(Color.RED);
                                    counslingMsgTextView.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
                                    counslingMsgTextView.setLayoutParams(layoutParamQuestion);

                                    counslingMsgTextView.setPadding(20, 0, 20, 20);

                                    ll4_1.addView(counslingMsgTextView, ((ll4_1.indexOfChild(ll_1)) + 1));

                                    counclingMsgQstnKeywords.add(keyword);

                                    if (StoredCounsellingRanges.get(i).getShowPopUp().equalsIgnoreCase("1"))
                                        criticalCounsellingMsg(language);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        break;
                    } else {

                        LinearLayout ll_1 = (LinearLayout) et.getParent();
//                        ll_1.setBackgroundColor(ctx.getResources().getColor(R.color.dependent_question_background));
                        //ll.setBackgroundColor(Color.parseColor("#0d47a1"));
                        if (counclingMsgQstnKeywords.contains(keyword)) {
                            LinearLayout ll4_1 = (LinearLayout) ll_1.getParent();
//							LinearLayout ll_1= (LinearLayout) v.getParent().getParent();

                            ll4_1.removeViewAt((ll4_1.indexOfChild(ll_1)) + 1);
                            counclingMsgQstnKeywords.remove(keyword);
                        }

                    }


                }

            } else {
                LinearLayout ll_1 = (LinearLayout) et.getParent();
//                ll.setBackgroundColor(Color.parseColor("#0d47a1"));
                if (counclingMsgQstnKeywords.contains(keyword)) {
                    LinearLayout ll4_1 = (LinearLayout) ll_1.getParent();
//							LinearLayout ll_1= (LinearLayout) v.getParent().getParent();

                    ll4_1.removeViewAt((ll4_1.indexOfChild(ll_1)) + 1);
                    counclingMsgQstnKeywords.remove(keyword);
                }

            }
        }


        if (StoredHighRiskRanges != null && StoredHighRiskRanges.size() > 0) {
            if (editTextValue != null && editTextValue.length() > 0) {

                Double value = Double.parseDouble(editTextValue);

                for (int i = 0; i < StoredHighRiskRanges.size(); i++) {

                    Double rangeMax = StoredHighRiskRanges.get(i).getRangemax();
                    Double rangeMin = StoredHighRiskRanges.get(i).getRangemin();
                    String rangelang = StoredHighRiskRanges.get(i).getRangeLang();


                    if (value >= rangeMin && value <= rangeMax) {

                        LinearLayout ll_1 = (LinearLayout) et.getParent();
//                        ll_1.setBackgroundColor(ctx.getResources().getColor(R.color.highrisk));

                        highrisklist.put("" + et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + value + delimeter + rangelang + delimeter + "highrisk" + delimeter + "0");
                        break;
                    } else {
                        LinearLayout ll_1 = (LinearLayout) et.getParent();
//                        ll_1.setBackgroundColor(ctx.getResources().getColor(R.color.dependent_question_background));
                        //ll.setBackgroundColor(Color.parseColor("#0d47a1"));
                        System.out.println("et.getId() = " + et.getId());
                        highrisklist.remove("" + et.getId());
                    }

                }

            } else {

                LinearLayout ll_1 = (LinearLayout) et.getParent();
//                ll_1.setBackgroundColor(ctx.getResources().getColor(R.color.dependent_question_background));
                highrisklist.remove("" + et.getId());
            }
        }


        if (StoredPatientVisitSummaryRanges != null && StoredPatientVisitSummaryRanges.size() > 0) {

            if (editTextValue != null && editTextValue.length() > 0) {

                Double value = Double.parseDouble(editTextValue);

                for (int i = 0; i < StoredPatientVisitSummaryRanges.size(); i++) {

                    Double rangeMax = StoredPatientVisitSummaryRanges.get(i).getRangemax();
                    Double rangeMin = StoredPatientVisitSummaryRanges.get(i).getRangemin();
                    String rangelang = StoredPatientVisitSummaryRanges.get(i).getRangeLang();

                    if (value >= rangeMin && value <= rangeMax) {
                        patientvisitlist.put(keyword, rangelang);
                        break;
                    } else {
                        patientvisitlist.put(keyword, rangelang);
                    }
                }

            } else {
                patientvisitlist.remove(keyword);
            }

        }

    }


    /**
     * This method is used to display high risk dialog with high risk list
     */
    public void highriskdialog() {
        /*try {

            final Dialog dialog = new Dialog(EnrollmentQuestions.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custome_highrisk);

            //dialog.setTitle(EnrollmentQuestions.this.getString(R.string.Diagnostic_Test));
            final LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.linearLayout2);

            final Iterator<Map.Entry<String, String>> itr2 = highrisklist.entrySet().iterator();
            while (itr2.hasNext()) {
                final Map.Entry<String, String> entry = itr2.next();
                entry.getKey();
                entry.getValue();

                String myString = entry.getValue();

                String[] a = myString.split(delimeter);

                if (a[5].equalsIgnoreCase("highrisk")) {
                    try {
                        JSONObject obj = new JSONObject(a[4]);
                        Optionlanguage = obj.getString(mAppLanguage);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    CheckBox cb = new CheckBox(EnrollmentQuestions.this);

                    cb.setText(Optionlanguage);
                    cb.setTextSize(20);
                    cb.setPadding(10, 10, 10, 10);
                    ll.addView(cb);
                }
            }

            if (ll.getChildCount() == 0) {
                ll.setVisibility(View.GONE);
                final LinearLayout ll1 = (LinearLayout) dialog.findViewById(R.id.linearLayout3);
                ll1.setVisibility(View.VISIBLE);
                TextView tv = (TextView) dialog.findViewById(R.id.textView);
                tv.setVisibility(View.VISIBLE);
            }

            Button highriskbut = (Button) dialog.findViewById(R.id.high_risk_button);
            highriskbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int count = ll.getChildCount();
                    Boolean flag = true;

                    for (int i = 0; i < count; i++) {
                        if (ll.getChildAt(i) instanceof CheckBox) {
                            CheckBox cb = (CheckBox) ll.getChildAt(i);
                            if (!cb.isChecked()) {
                                flag = false;
                                break;
                            }
                        }
                    }

                    if (flag) {
                        Diagnostic_TestDialog();
                        dialog.dismiss();

                    } else {
                        Toast.makeText(EnrollmentQuestions.this, EnrollmentQuestions.this.getString(R.string.checkbox_select_msg), Toast.LENGTH_SHORT).show();
                    }

                }
            });


            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
*/
    }

    /**
     * This method is used to display diagnostic referral dialog with its list
     */
    public void Diagnostic_TestDialog() {
       /* try {


            final Dialog dialog = new Dialog(EnrollmentQuestions.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custome_referrallist);
            dialog.setTitle(EnrollmentQuestions.this.getString(R.string.Diagnostic_Test));

            final LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.linearLayout2);

            final Iterator<Map.Entry<String, String>> itr2 = referrallist.entrySet().iterator();
            while (itr2.hasNext()) {
                final Map.Entry<String, String> entry = itr2.next();
                entry.getKey();
                entry.getValue();

                try {
                    JSONObject obj = new JSONObject(entry.getValue());
                    Optionlanguage = obj.getString(mAppLanguage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                CheckBox cb = new CheckBox(EnrollmentQuestions.this);

                cb.setText(Optionlanguage);
                cb.setTextSize(20);
                cb.setPadding(10, 10, 10, 10);
                ll.addView(cb);

            }

            if (ll.getChildCount() == 0) {
                ll.setVisibility(View.GONE);
                TextView tv = (TextView) dialog.findViewById(R.id.textView);
                tv.setVisibility(View.VISIBLE);
            }

            Button referbut = (Button) dialog.findViewById(R.id.referral_button);
            referbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int count = ll.getChildCount();
                    Boolean flag = true;

                    for (int i = 0; i < count; i++) {
                        if (ll.getChildAt(i) instanceof CheckBox) {
                            CheckBox cb = (CheckBox) ll.getChildAt(i);
                            if (!cb.isChecked()) {
                                flag = false;
                                break;
                            }
                        }
                    }

                    if (flag) {

                        saveform(mMigrantStatus);

                        dialog.dismiss();
                    } else {
                        Toast.makeText(EnrollmentQuestions.this, EnrollmentQuestions.this.getString(R.string.checkbox_select_msg), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    /**
     * This method is used to display patient visit summary dialog with its list
     *
     * @param visit_name = visit id of the form in which woman is registered.
     * @throws JSONException
     */
    public void ImportantNote_Dialog(String visit_name) throws JSONException {
//
        try {

            final Dialog dialog = new Dialog(EnrollmentQuestions.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custome_pvs);
            dialog.setTitle(EnrollmentQuestions.this.getString(R.string.Important_Note));

            final LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.linearLayout2);

            System.out.println("visit_name = " + visit_name);
            if (visit_name.equalsIgnoreCase("Not_Valid")) {
                System.out.println("inside not_valid called");
                JSONObject jsonObjdata = new JSONObject();
                jsonObjdata.put("en", "Women is Registering After ANC, Her Previous Visits will be skipped");
                jsonObjdata.put("mr", "महिला रजिस्टर करात अहे ANC नंतर , तीचे पहिल्याचे विजिट स्किप होनर ");
                jsonObjdata.put("hi", "महिला रजिस्टर कर रही है ANC के बाद , उसके पहले के विजिट स्किप होंगे ");
                patientvisitlist.put("women_after_anc", "" + jsonObjdata);

                deliveryStatus = 1;
            } else {
                System.out.println("inside else called");

                if (visit_name.contains("en")) {
                    JSONObject visitNameObject = new JSONObject(visit_name);
                    visit_name = visitNameObject.getString(mAppLanguage);
                }

                JSONObject jsonObjdata = new JSONObject();
                jsonObjdata.put("en", "Women is Registering in " + visit_name + "");
                jsonObjdata.put("mr", "महिला नोंदणी करत आहे " + visit_name + "");
                jsonObjdata.put("hi", "महिला रजिस्टर कर रही है " + visit_name + "");
                patientvisitlist.put("women_in_reg", "" + jsonObjdata);

                deliveryStatus = 0;
            }

            final Iterator<Map.Entry<String, String>> itr2 = patientvisitlist.entrySet().iterator();
            while (itr2.hasNext()) {
                final Map.Entry<String, String> entry = itr2.next();
                entry.getKey();
                entry.getValue();

                System.out.println("entry.getKey() = " + entry.getKey());
                System.out.println("entry.getValue() = " + entry.getValue());
                try {
                    JSONObject obj = new JSONObject(entry.getValue());
                    Optionlanguage = obj.getString(mAppLanguage);

                    if (Optionlanguage.contains(entry.getKey())) {

                        if (Optionlanguage.matches(".*[/,=:;-].*")) {
                            String[] splited = Optionlanguage.split("[\\s,/=:;-]+");

                            for (String s : splited) {
                                if (womendetails.containsKey(s.trim())) {
                                    Optionlanguage = Optionlanguage.replace(s.trim(), womendetails.get(s.trim()));
                                }
                            }
                        } else {
                            Optionlanguage = Optionlanguage.replace(entry.getKey(), womendetails.get(entry.getKey()));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                CheckBox cb = new CheckBox(EnrollmentQuestions.this);

                cb.setText(Optionlanguage);
                cb.setTextSize(20);
                cb.setPadding(10, 10, 10, 10);
                ll.addView(cb);

            }

            if (ll.getChildCount() == 0) {
                ll.setVisibility(View.GONE);
                TextView tv = (TextView) dialog.findViewById(R.id.textView);
                tv.setVisibility(View.VISIBLE);
            }

            Button referbut = (Button) dialog.findViewById(R.id.referral_button);
            referbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    int count = ll.getChildCount();
//                    Boolean flag = true;
//
//                    for (int i = 0; i < count; i++) {
//                        if (ll.getChildAt(i) instanceof CheckBox) {
//                            CheckBox cb = (CheckBox) ll.getChildAt(i);
//                            if (!cb.isChecked()) {
//                                flag = false;
//                                break;
//                            }
//                        }
//                    }
//
//                    if (flag) {
//                        if (!HighriskStatus) {
//                            highriskdialog();
//                            dialog.dismiss();
////                            ImportantDialogStatus=true;
//                        }
//
//                    } else {
//                        Toast.makeText(EnrollmentQuestions.this, EnrollmentQuestions.this.getString(R.string.checkbox_select_msg), Toast.LENGTH_SHORT).show();
//                    }
                    dialog.dismiss();
                    //   saveform();

                }
            });


            dialog.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void criticalCounsellingMsg(String popupMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder
                .setTitle(R.string.counselling_msg_txt)
                .setMessage(popupMessage)
                .setIcon(R.mipmap.ic_exitalert)
                .setPositiveButton(EnrollmentQuestions.this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    /**
     * This method is used to exit from the current page
     */
    public void backForm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder
                .setTitle(EnrollmentQuestions.this.getString(R.string.back_form))
                .setMessage(EnrollmentQuestions.this.getString(R.string.back_form_message))
                .setIcon(R.mipmap.ic_exitalert)
                .setPositiveButton(EnrollmentQuestions.this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            woman_gest_age = null;
                            expec_date = null;
                            current_reg = null;
                            Intent intent = new Intent(EnrollmentQuestions.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                })
                .setNegativeButton(EnrollmentQuestions.this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();

                    }
                }).show();

    }

    @Override
    public void onBackPressed() {

        backForm();
    }

    /**
     * This method is used to remove depend questions
     *
     * @param ll_4    = linear layout which is to be removed
     * @param keyword = question keyword
     */

    public void removeDependent(LinearLayout ll_4, String keyword) {
        if (MainQuestempstoredependant.containsKey(keyword)) {
            removeDependentQuestion = MainQuestempstoredependant.get(keyword);


            for (int i = 0; i < removeDependentQuestion.size(); i++) {
                LinearLayout tp = (LinearLayout) ll_4.findViewWithTag(removeDependentQuestion.get(i));

                ll_4.removeView(tp);

                dependantKeywordPresent.remove(removeDependentQuestion.get(i));

                womendetails.remove(removeDependentQuestion.get(i));
                validationlist.remove(removeDependentQuestion.get(i));
                NextButtonvalidationlist.remove(removeDependentQuestion.get(i));
                patientvisitlist.remove(removeDependentQuestion.get(i));

                Iterator<Map.Entry<Integer, String>> itr2 = Backup_answerTyped1.entrySet().iterator();
                while (itr2.hasNext()) {
                    Map.Entry<Integer, String> entry = itr2.next();

                    String myString = entry.getValue();
                    String[] a = myString.split(delimeter);


                    if (a[2].trim().equals(removeDependentQuestion.get(i))) {
                        Backup_answerTyped1.remove(entry.getKey());
                        womenanswer.remove(entry.getKey());
                        womendetails.remove(keyword);
                        break;
                    }
                }

                if (MainQuestempstoredependant.containsKey(removeDependentQuestion.get(i))) {
                    removeDependentQuestion.addAll(MainQuestempstoredependant.get(removeDependentQuestion.get(i)));
                    MainQuestempstoredependant.remove(removeDependentQuestion.get(i));
                }

            }

            removeDependentQuestion.clear();
            MainQuestempstoredependant.remove(keyword);

        }
    }

    /**
     * This method is used to display counselling message on radio button's click.
     *
     * @param counsel_message = counselling message text which is to be displayed
     * @param v               = view on which the layout is dependent on.
     * @param keyword         = keyword of the question.
     */
    public void displayCounsellingMsg(String counsel_message, View v, String keyword) {
        if (!(counsel_message != null && counsel_message.length() > 0)) {
            //System.out.println("counclingMsgQstnKeywords.contains(keyword) = " + counclingMsgQstnKeywords.contains(keyword));
            if (counclingMsgQstnKeywords.contains(keyword)) {
                LinearLayout ll4_1 = (LinearLayout) v.getParent().getParent().getParent();
                LinearLayout ll_1 = (LinearLayout) v.getParent().getParent();

                ll4_1.removeViewAt((ll4_1.indexOfChild(ll_1)) + 1);
                counclingMsgQstnKeywords.remove(keyword);
            }
        }


        try {
            if (counsel_message != null && counsel_message.length() > 0) {
                if (!counclingMsgQstnKeywords.contains(keyword)) {
                    JSONObject obj = new JSONObject(counsel_message);
                    String language = obj.getString(mAppLanguage);
                    LinearLayout ll4_1 = (LinearLayout) v.getParent().getParent().getParent();
                    LinearLayout ll_1 = (LinearLayout) v.getParent().getParent();

                    language = language.replace("\\n ", "\n");

                    TextView counslingMsgTextView = new TextView(ctx);
                    counslingMsgTextView.setText(language);
                    counslingMsgTextView.setBackgroundColor(ctx.getResources().getColor(R.color.dependent_question_background));
                    counslingMsgTextView.setTextColor(Color.RED);
                    counslingMsgTextView.setTextSize(getResources().getDimension(R.dimen.text_size_medium));

                    counslingMsgTextView.setLayoutParams(layoutParamQuestion);

                    counslingMsgTextView.setPadding(20, 0, 20, 20);

                    ll4_1.addView(counslingMsgTextView, ((ll4_1.indexOfChild(ll_1)) + 1));

                    counclingMsgQstnKeywords.add(keyword);
                }
            } else {
                if (counclingMsgQstnKeywords.contains(keyword)) {
                    LinearLayout ll4_1 = (LinearLayout) v.getParent().getParent().getParent();
                    LinearLayout ll_1 = (LinearLayout) v.getParent().getParent();

                    ll4_1.removeViewAt((ll4_1.indexOfChild(ll_1)) + 1);
                    counclingMsgQstnKeywords.remove(keyword);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void hideSoftKeyboard(View view) {
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public void createButton(final String quesid, final String keyword, final String setid, final int id, final TextView tv, final String formid, final List<Visit> listOptions, final String displayCondition, final String pageScrollId, final int orientation) {

        LinearLayout buttonLayout = new LinearLayout(ctx);
        buttonLayout.setLayoutParams(segmentedBtnLp);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);


        ll.addView(buttonLayout);

        for (int i = 1; i < listOptions.size(); i++) {
            buttonId = buttonId + 1;


            try {
                JSONObject obj = new JSONObject(listOptions.get(i).getQuestionText());
                Optionlanguage = obj.getString(mAppLanguage);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final Button button = new Button(this);
            button.setLayoutParams(lparams);
            button.setText(Optionlanguage);
            button.setId(buttonId);
            button.setPressed(true);
            button.setPadding(10, 10, 10, 10);
            button.setTextSize(15);
            button.setTag(listOptions.get(i).getKeyword());
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_normal));
            button.setTextColor(ContextCompat.getColor(this, R.color.text_color));
            buttonLayout.addView(button);

            runtimevalidationlist.put(button.getId(), Integer.parseInt(pageScrollId));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setButtonColor(EnrollmentQuestions.this, v, (LinearLayout) v.getParent());
                    onClickButtonFunctionality(button, v, quesid, keyword, setid, id, tv, formid, listOptions, displayCondition, pageScrollId, orientation);
                }
            });

            if (womendetails.containsKey(keyword)) {
                if (womendetails.get(keyword).equalsIgnoreCase(button.getTag().toString())) {
                    button.performClick();

                }

            }

            if (displayCondition != null && displayCondition.length() > 0 && womenpreviousEcdetails.containsKey(keyword)) {
                if (listOptions.get(i).getKeyword().equals(womenpreviousEcdetails.get(keyword))) {
                    button.performClick();
                }
                tv.setError(null);

                validationlist.put(keyword, womenpreviousEcdetails.get(keyword));
                //button.setEnabled(false);
            }

        }

    }

    public void onClickButtonFunctionality(Button button, View v, final String quesid, final String keyword, final String setid, int id, final TextView tv, String formid, List<Visit> optionList, String displayCondition, final String pageScrollId, int orientation) {

        String counsel_message = null;

//        String Messages = dbhelper.getHighRiskConditionForRadio("" + button.getTag());
        String Messages = questionInteractor.getHighRiskCondition("" + button.getTag());

        if (validationlist.containsKey(keyword))
            validationlist.put(keyword, button.getTag().toString());
        /**
         * This logic is used to check whether there is any High risk,Counselling or Diagnostic referral on button click
         */
        try {
            if (Messages != null && Messages.length() > 0) {
                StoredHighRiskRanges = new ArrayList<>();

                JSONObject highRisk_conditions = new JSONObject(Messages);
                if (highRisk_conditions.optJSONArray("highrisk") != null) {
                    JSONArray highRisk_conditionsArray = highRisk_conditions.optJSONArray("highrisk");

                    //System.out.println("jsonArray3 options"+jsonArray3.length());
                    for (int k = 0; k < highRisk_conditionsArray.length(); k++) {
                        JSONObject main_ques_options_key = highRisk_conditionsArray.getJSONObject(0);
                        highrisklist.put(quesid, "" + formid + delimeter + setid + delimeter + keyword + delimeter + button.getTag() + delimeter + main_ques_options_key.optString("languages").toString() + delimeter + "highrisk" + delimeter + "0");
                    }
                }

                if (highRisk_conditions.optJSONArray("counselling") != null) {
                    JSONArray counseling_conditionsArray = highRisk_conditions.optJSONArray("counselling");
                    for (int k = 0; k < counseling_conditionsArray.length(); k++) {
                        JSONObject main_ques_options_key = counseling_conditionsArray.getJSONObject(0);
                        counsel_message = main_ques_options_key.optString("languages").toString();
                        if (main_ques_options_key.has("show_popup")) {
                            JSONObject obj = new JSONObject(counsel_message);
                            String language = obj.getString(mAppLanguage);
                            criticalCounsellingMsg(language);
                        }
                    }
                }

                if (highRisk_conditions.optJSONArray("diagnosticrefer") != null) {
                    JSONArray diagnosticrefer_conditionsArray = highRisk_conditions.optJSONArray("diagnosticrefer");
                    for (int k = 0; k < diagnosticrefer_conditionsArray.length(); k++) {
                        JSONObject diagnosticrefer_options_key = diagnosticrefer_conditionsArray.getJSONObject(0);
                        highrisklist.put(quesid, "" + formid + delimeter + setid + delimeter + keyword + delimeter + button.getTag() + delimeter + diagnosticrefer_options_key.optString("languages").toString() + delimeter + "diagnosticReffer" + delimeter + "0");
                        referrallist.put(quesid, diagnosticrefer_options_key.optString("languages").toString());
                    }
                }

                if (highRisk_conditions.optJSONArray("patientVisitSummary") != null) {
                    JSONArray patientVisitSummary_conditionsArray = highRisk_conditions.optJSONArray("patientVisitSummary");
                    for (int k = 0; k < patientVisitSummary_conditionsArray.length(); k++) {
                        JSONObject patientVisitSummary_options_key = patientVisitSummary_conditionsArray.getJSONObject(0);
                        patientvisitlist.put(keyword, patientVisitSummary_options_key.optString("languages").toString());
                    }
                }

            } else {
                highrisklist.remove(quesid);
                referrallist.remove(quesid);
                patientvisitlist.remove(keyword);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ll_sub = (LinearLayout) v.getParent().getParent();

        //System.out.println("ll_sub" + ll_sub.getParent());

        womenanswer.put(Integer.parseInt(quesid), "" + formid + delimeter + setid + delimeter + keyword + delimeter + button.getTag());        //
        womendetails.put(keyword, "" + button.getTag());


        tv.setError(null);

        Backup_answerTyped1.put(button.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + button.getTag());        // this hashmap is used to insert answered dataSource into Backup_entered table


//        dependantList = dbhelper.getDependant_Eng_queslist("" + button.getTag(), formid, ll_sub, keyword, "" + scroll_temp.getId());  // this list is used to get dependant question in english format (parameter"s received are form_id,keyword,answer_type,english_lang)
        dependantList = questionInteractor.getDependantQuesList("" + button.getTag(), formID, ll_sub, keyword, "" + scroll_temp.getId());  // this list is used to get dependant question in english format (parameter"s received are form_id,keyword,answer_type,english_lang)

        if (counclingMsgQstnKeywords.contains(keyword)) {
            LinearLayout ll4_1 = (LinearLayout) v.getParent().getParent().getParent();
            LinearLayout ll_1 = (LinearLayout) v.getParent().getParent();

            ll4_1.removeViewAt((ll4_1.indexOfChild(ll_1)) + 1);
            counclingMsgQstnKeywords.remove(keyword);
        }

        /**
         * This if condition is used to check whether dependant question is present or not
         * if dependantList size is 0 or null means question dosen't have any depandant question.
         */
        if (dependantList != null && dependantList.size() > 0) {

            if (dependantKeywordPresent.containsValue("" + button.getTag())) {

            } else {

                LinearLayout ll_4 = (LinearLayout) v.getParent().getParent().getParent();
                removeDependent(ll_4, keyword);
                dependantKeywordPresent.put(keyword, "" + button.getTag());


                /**
                 * This if condition is used to check whether dependant layout is displayed or not
                 * this is used as a validation that if once the layout is displayed then again clicking on the same button twice the layout should be displayed only once
                 * for eg. if tt2yes is clicked for the first time then dependant layout should be displayed but again clicking on it dependant layout should not be displayed.
                 */

                if (!dependantLayout.containsKey(keyword) && !(MainQuestempstoredependant.containsKey(keyword))) {
                    tempdependantStore = new ArrayList<String>();
                    layoutids.put("" + button.getTag(), "" + ll_sub);    // this hashmap is used to store the layout id for the clicked button (for eg: tt2yes,android.widget.LinearLayout{21abf298 V.E...C. ......ID 40,646-560,769})

                    DisplayDependantQuestions(dependantList);

                }

            }

        } else {


            LinearLayout ll_4 = (LinearLayout) v.getParent().getParent().getParent();
            removeDependent(ll_4, keyword);

            dependantKeywordPresent.remove(keyword);
        }


        displayCounsellingMsg(counsel_message, v, keyword);
    }

    public void setButtonColor(Context context, View view, LinearLayout linearLayout) {

        Drawable btnNormalDrawable = ContextCompat.getDrawable(context, R.drawable.btn_normal);
        Drawable btnSelectedDrawable = ContextCompat.getDrawable(context, R.drawable.btn_selected);

        Button button = (Button) view;

        String keyword = button.getTag().toString();

        System.out.println("Tag " + button.getTag());

        int childCount = linearLayout.getChildCount();

        for (int index = 0; index < childCount; index++) {
            //System.out.println("Child = " + linearLayout.getChildAt(index).getTag());
            String key = linearLayout.getChildAt(index).getTag().toString();

            Button unSelectedbtn = (Button) linearLayout.getChildAt(index);

            if (keyword.equalsIgnoreCase(key)) {
                button.setBackground(btnSelectedDrawable);
                button.setTextColor(Color.WHITE);
                System.out.println("Selected: " + key);
            } else {
                linearLayout.getChildAt(index).setBackground(btnNormalDrawable);
                unSelectedbtn.setTextColor(ContextCompat.getColor(context, R.color.color_btn_normal));
                System.out.println("Unselected: " + key);
            }

        }

    }

    public Boolean dynamicCalculation(String calculations) {
        Boolean result = false;
        try {

            String[] split_calculations = calculations.split("((\\,))");

            for (String expression : split_calculations) {
                String[] split_str = expression.split("((\\=)|(?<=\\-)|(?=\\-)|(\\()|(\\))|(?<=\\+)|(?=\\+)|(?<=\\*)|(?=\\*)|(?<=\\/)|(?=\\/)|(?<=\\<)|(?=\\<))");

                for (int z = 0; z < split_str.length; z++) {
                    switch (split_str[1].trim()) {
                        case "<":

                            if (Integer.parseInt(womendetails.get(split_str[0].trim())) < Integer.parseInt(womendetails.get(split_str[2].trim()))) {
                                result = false;

                            } else {
                                result = true;

                            }
                            break;
                    }

                }
            }


        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }


    class DisplayQuestions extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(EnrollmentQuestions.this);
            progressDialog.setMessage(getString(R.string.loading_question));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            questionInteractor = new QuestionInteractor(EnrollmentQuestions.this);

            formID = "1";
            Intent in = getIntent();
            b = in.getExtras();
            /**
             * NormalRegFlag = to track whether woman's rwgistration form is filled first or not.
             * if NormalRegFlag = 100 that means woman's registration form is filled first ie normal registration (flow from @MAinActivity)
             * if NormalRegFlag = 101 that means woman's other forms are filled first and her name and phone_no already present
             * while filling form 2.(flow from @ CompleteFormListActivity)
             */
           NormalRegFlag = b.getInt("NormalRegFlag");
                    if(NormalRegFlag==101) {
                        womanUniqueId = b.getString("id");
                        womanName = b.getString("firstName");
                        // flag = b.getInt("flag");
                        if (womanUniqueId == null) {
                            womanUniqueId = "0"; //womanId= 0 means womam's reistration form is being filled as first form
                            //so uniqueID is not generated that means normal registration
                        }else{
                        /**get name and phone number of direct registration woman to autodisplay and store it in registration visit.*/

                        directRegDetails = questionInteractor.getDirectWomanDetails(womanUniqueId);
                        directRegName = directRegDetails.get(0);
                        directRegPhone = directRegDetails.get(1);
                    }
                    }
                    else
                        womanUniqueId = "0"; //womanId= 0 means womam's reistration form is being filled as first form

            mAppLanguage = utilityObj.getLanguagePreferance(getApplicationContext());
            init();

            ashaList = new ArrayList<>(Arrays.asList("a"));
            hashMapUserDetails = questionInteractor.fetchUserDetails();

//            enrollmentList = dbhelper.getEnglishEnrollment(formID);
            enrollmentList = questionInteractor.getMainQuestions(formID);

            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            int layout_control_counter = 0;

            drawableMainQstn = new GradientDrawable();
            drawableMainQstn.setShape(GradientDrawable.RECTANGLE);
            drawableMainQstn.setStroke(2, Color.WHITE);
            drawableMainQstn.setColor(ContextCompat.getColor(EnrollmentQuestions.this, R.color.main_question_background));

            drawableDependentQstn = new GradientDrawable();
            drawableDependentQstn.setShape(GradientDrawable.RECTANGLE);
            drawableDependentQstn.setStroke(1, Color.WHITE);
            drawableDependentQstn.setColor(ContextCompat.getColor(EnrollmentQuestions.this, R.color.dependent_question_background));
/**
 * For loop for displaying questions for enrollment form which is stored in localDB.
 */
            for (int j = counter; j < enrollmentList.size(); j++) {
/**
 * Display only 4 questions on the layout if counter goes above 4 break loop and create new layout for further questions
 * if answer_type contains capturephoto then break and loop and include that question on new layout
 *
 */
                if (layout_control_counter == 0 || layout_control_counter % 4 == 0 || enrollmentList.get(j).getAnswerType().equalsIgnoreCase("capturephoto") || enrollmentList.get(j).getAnswerType().equalsIgnoreCase("sublabel") || enrollmentList.get(j).getAnswerType().equalsIgnoreCase("label") || PreviousQuesAnswertype.equals("label")) {
                    layout_control_counter = 0;
                    layoutcounter++;
                    ll_4layout = new LinearLayout(ctx);
                    ll_4layout.setLayoutParams(lp1);
                    ll_4layout.setOrientation(LinearLayout.VERTICAL);
                    ll_4layout.setId(j + 100000);
                    ll_4layout.setBackgroundColor(Color.parseColor("#fafafa"));

                    ll_4layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            hideSoftKeyboard(view);
                        }
                    });

                    scroll = new ScrollView(ctx);
                    scroll.setLayoutParams(sp1);
                    scroll.setId(j + 200000);
                    scroll.setVisibility(View.INVISIBLE);
                    scroll.addView(ll_4layout);
                    scrollId.add(scroll.getId());
                    parentLayoutchild.put(layoutcounter, ll_4layout);
                    Frame.addView(scroll);
                    scroll_temp = scroll;

                    scroll.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            hideSoftKeyboard(v);
                            return false;
                        }
                    });

                    PreviousQuesAnswertype = enrollmentList.get(j).getAnswerType();
                }

                ll = new LinearLayout(ctx);
                ll.setLayoutParams(layoutParamQuestion);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.setBackground(drawableMainQstn);
                ll.setPadding(20, 30, 20, 40);
                ll_4layout.addView(ll);

                /**
                 * following if loop is for certain questions to skip on specific conditions
                 */
                if(enrollmentList.get(j).keyword.equals("participant_study_id")){
                    if( NormalRegFlag!=100){
                        ll_4layout.removeView(ll);
                    }
                }

                switch (enrollmentList.get(j).getAnswerType()) {
                    case "text":
                        //System.out.println("answer type..........");
                        ll = createEdittext(j, enrollmentList.get(j).getQuestionText(), enrollmentList.get(j).getFormid(), enrollmentList.get(j).getSetid(), enrollmentList.get(j).getKeyword(), enrollmentList.get(j).getValidationfield(), enrollmentList.get(j).getValidationCondition(), enrollmentList.get(j).getValidationengmsg(), enrollmentList.get(j).getMessages(), enrollmentList.get(j).getDisplayCondition(), enrollmentList.get(j).getOrientation(), enrollmentList.get(j).getLengthmax());


                        break;

                    case "date":
                        ll = createDate(j, enrollmentList.get(j).getQuestionText(), enrollmentList.get(j).getFormid(), enrollmentList.get(j).getSetid(), enrollmentList.get(j).getKeyword(), enrollmentList.get(j).getValidationfield(), enrollmentList.get(j).getValidationCondition(), enrollmentList.get(j).getValidationengmsg(), enrollmentList.get(j).getMessages(), enrollmentList.get(j).getDisplayCondition(), enrollmentList.get(j).getOrientation());
                        break;

                    case "int":
                        ll = createInt(enrollmentList.get(j).getAnswerType(), enrollmentList.get(j).getQuestionText(), enrollmentList.get(j).getFormid(), enrollmentList.get(j).getSetid(), enrollmentList.get(j).getKeyword(), enrollmentList.get(j).getValidationfield(), enrollmentList.get(j).getValidationCondition(), enrollmentList.get(j).getValidationengmsg(), enrollmentList.get(j).getLengthmin(), enrollmentList.get(j).getLengthmax(), enrollmentList.get(j).getLengthvalidationmsg(), enrollmentList.get(j).getRangemin(), enrollmentList.get(j).getRangemax(), enrollmentList.get(j).getRangevalidationmsg(), enrollmentList.get(j).getCounselling_lang(), enrollmentList.get(j).getMessages(), enrollmentList.get(j).getDisplayCondition(), enrollmentList.get(j).getOrientation(), enrollmentList.get(j).getCalculations());

                        break;

                    case "time":
                        ll = createTime(j, enrollmentList.get(j).getQuestionText(), enrollmentList.get(j).getFormid(), enrollmentList.get(j).getSetid(), enrollmentList.get(j).getKeyword(), enrollmentList.get(j).getValidationfield(), enrollmentList.get(j).getMessages(), enrollmentList.get(j).getDisplayCondition(), scroll.getId(), enrollmentList.get(j).getOrientation());
                        break;

                    case "float":

                        ll = createInt(enrollmentList.get(j).getAnswerType(), enrollmentList.get(j).getQuestionText(), enrollmentList.get(j).getFormid(), enrollmentList.get(j).getSetid(), enrollmentList.get(j).getKeyword(), enrollmentList.get(j).getValidationfield(), enrollmentList.get(j).getValidationCondition(), enrollmentList.get(j).getValidationengmsg(), enrollmentList.get(j).getLengthmin(), enrollmentList.get(j).getLengthmax(), enrollmentList.get(j).getLengthvalidationmsg(), enrollmentList.get(j).getRangemin(), enrollmentList.get(j).getRangemax(), enrollmentList.get(j).getRangevalidationmsg(), enrollmentList.get(j).getCounselling_lang(), enrollmentList.get(j).getMessages(), enrollmentList.get(j).getDisplayCondition(), enrollmentList.get(j).getOrientation(), enrollmentList.get(j).getCalculations());

                        break;

                    case "radio":
                        if (autoGeneratedECId != null && !autoGeneratedECId.equals("") && enrollmentList.get(j).getKeyword().equalsIgnoreCase("is_woman_migrant")) {
                            layout_control_counter = -1;

                            parentLayoutchild.remove(layoutcounter);
                            layoutcounter--;
                            scrollId.remove(scrollId.size() - 1);
                            Frame.removeView(scroll);

                        } else {
                            ll = createRadio(j, enrollmentList.get(j).getQuesid(), enrollmentList.get(j).getQuestionText(), enrollmentList.get(j).getSetid(), enrollmentList.get(j).getKeyword(), enrollmentList.get(j).getValidationfield(), enrollmentList.get(j).getFormid(), enrollmentList.get(j).getMessages(), enrollmentList.get(j).getDisplayCondition(), enrollmentList.get(j).getOrientation());
                        }

                        // ll_4layout.addView(ll);
                        break;

                    case "select":
                        ll = createCheckbox(0, enrollmentList.get(j).getQuesid(), enrollmentList.get(j).getFormid(), enrollmentList.get(j).getSetid(), enrollmentList.get(j).getQuestionText(), enrollmentList.get(j).getKeyword(), enrollmentList.get(j).getValidationfield(), enrollmentList.get(j).getMessages(), enrollmentList.get(j).getDisplayCondition(), enrollmentList.get(j).getOrientation());

                        break;

                    case "capturephoto":

                        ll = createCapturePhoto(j, enrollmentList.get(j).getQuestionText(), enrollmentList.get(j).getFormid(), enrollmentList.get(j).getSetid(), enrollmentList.get(j).getKeyword(), enrollmentList.get(j).getValidationfield(), enrollmentList.get(j).getValidationCondition(), enrollmentList.get(j).getValidationengmsg(), enrollmentList.get(j).getMessages(), enrollmentList.get(j).getDisplayCondition());
                        break;

                    case "custom":

                        ll = createSpinner(j, enrollmentList.get(j).getQuestionText(), enrollmentList.get(j).getFormid(), enrollmentList.get(j).getSetid(), enrollmentList.get(j).getKeyword(), enrollmentList.get(j).getValidationfield(), enrollmentList.get(j).getValidationCondition(), enrollmentList.get(j).getValidationengmsg(), enrollmentList.get(j).getMessages(), enrollmentList.get(j).getDisplayCondition(), enrollmentList.get(j).getOrientation());
                        break;

                    case "label":

                        ll = createLabel(j, enrollmentList.get(j).getQuestionText(), enrollmentList.get(j).getFormid(), enrollmentList.get(j).getSetid(), enrollmentList.get(j).getKeyword(), enrollmentList.get(j).getValidationfield(), enrollmentList.get(j).getValidationCondition(), enrollmentList.get(j).getValidationengmsg(), enrollmentList.get(j).getMessages(), enrollmentList.get(j).getDisplayCondition(), enrollmentList.get(j).getCalculations());
                        break;

                    case "sublabel":

                        ll = createSubLabel(j, enrollmentList.get(j).getQuestionText(), enrollmentList.get(j).getFormid(), enrollmentList.get(j).getSetid(), enrollmentList.get(j).getKeyword(), enrollmentList.get(j).getValidationfield(), enrollmentList.get(j).getValidationCondition(), enrollmentList.get(j).getValidationengmsg(), enrollmentList.get(j).getMessages(), enrollmentList.get(j).getDisplayCondition());
                        break;

                    default:

                        break;
                }

                counter++;

                if (PreviousQuesAnswertype.equals("sublabel"))
                    layout_control_counter = 1;
                else
                    layout_control_counter++;

            }

        /*validationlist.clear();
        NextButtonvalidationlist.clear();*/

            if (scrollId.isEmpty()) {
                progressDialog.dismiss();

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(EnrollmentQuestions.this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle(getString(R.string.title_data_not_found));
                builder.setMessage(getString(R.string.sync_forms_message));
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent intent = new Intent(EnrollmentQuestions.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.show();
            } else {
                String jsonFormName = questionInteractor.getFormNameFromId(Integer.valueOf(formID));
                try {
                    JSONObject textobj = new JSONObject(jsonFormName);
                    jsonFormName = textobj.getString(mAppLanguage);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setTitle(jsonFormName);

                scroll_temp = (ScrollView) findViewById(Integer.parseInt(String.valueOf(scrollId.get(scrollcounter))));
                scroll_temp.setVisibility(View.VISIBLE);
                previous.setVisibility(View.GONE);

                progress.setMax(layoutcounter);
                progress.setProgress(1);

                textViewTotalPgCount = (TextView) findViewById(R.id.text_total_count);
                pageCountText = "/" + layoutcounter;
                textViewTotalPgCount.setText(1 + pageCountText);

                /*nextstaticcount=progresscount=100/layoutcounter;
                progress.setProgress(progresscount);*/
            }

            next.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    NextButtonValidations();

                }
            });


            previous.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    PreviousButtonValidation();

                }
            });

            progressDialog.dismiss();

        }
    }

}

