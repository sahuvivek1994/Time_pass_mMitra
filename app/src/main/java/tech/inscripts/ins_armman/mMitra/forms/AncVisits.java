package tech.inscripts.ins_armman.mMitra.forms;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.*;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.*;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import bsh.EvalError;
import bsh.Interpreter;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tech.inscripts.ins_armman.mMitra.R;
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseContract;
import tech.inscripts.ins_armman.mMitra.utility.Utility;

import java.io.File;
import java.text.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static tech.inscripts.ins_armman.mMitra.forms.EnrollmentQuestions.MEDIA_TYPE_IMAGE;
import static tech.inscripts.ins_armman.mMitra.forms.EnrollmentQuestions.MEDIA_TYPE_VIDEO;
import static tech.inscripts.ins_armman.mMitra.utility.Constants.*;
import static tech.inscripts.ins_armman.mMitra.utility.Keywords.*;


/**
 * This class is used to display ANC Form Questions with answer type dynamically from localDB
 */
public class AncVisits extends AppCompatActivity {

    private static final String TAG = "AncVisits";

    Boolean isIucdRemoved = false, isTTDoseActive = false, isTT1Active = false;
    Boolean isParticipantMissLmp = false;
    Boolean isParticipantPregnant = false;
    Boolean isParticipantInfertile = false;
    FrameLayout Frame;

    Button next, previous, Hash;
    LinearLayout.LayoutParams lp1, lp, lpHorizontal, segmentedBtnLp, lparams, layoutParamQuestion;
    ScrollView.LayoutParams sp1;
    LinearLayout ll, ll_sub, ll_4layout, ll_4layout_sub;
    ScrollView scroll, scroll_temp;
    Context ctx = this;
    int counter;
    private QuestionInteractor questionInteractor;
    int scrollcounter = 0;
    Date SystemDate, selectedDate;
    SimpleDateFormat formatter;
    int radio = 400, textId = 500, edittextID = 600, checkbox = 700, radiogroup = 800, customespinner = 10, buttonId = 900;
    ArrayList<Integer> scrollId = new ArrayList<>();
    HashMap<String, String> womendetails = new HashMap<>();
    HashMap<String, String> previousVisitDetails = new HashMap<>();
    HashMap<String, String> storeEnteredData = new HashMap<>();
    HashMap<String, String> Backup_answerTyped1 = new HashMap<>(); // is used to insert the entered dataSource into localDB
    TreeMap<String, String> validationlist = new TreeMap<>();    //TreeMap contains compulsory questions keyword and their answers
    TreeMap<String, Integer> NextButtonvalidationlist = new TreeMap<>();  // TreeMap is used to check how many compulsory questions are there on one single page
    ConcurrentHashMap<String, String> dependantquestion = new ConcurrentHashMap<>();
    HashMap<String, String> dependantLayout = new HashMap<>();
    List<Visit> alertList = null; // is used to retrieve questions for anc form
    List<Visit> optionList = null; // is used to check whether the question contains dependant question
    List<Visit> dependantoptionList = null; // is used to check whether the question contains dependant question
    List<Visit> dependantList = null; // is used to retireve dependant question for the selected button

    List<String> removeDependentQuestion = new ArrayList<>();
    String formid, language;
    String womanLmp, mamtaCardID;
    public boolean isAncPncEdit = false;
    public boolean noDialogFirst = false;
    int progresscount, womenDeliveryDays, Migrant;
    ProgressBar progress;
    List<String> chechboxlist = new ArrayList<>();
    HashMap<String, String> layoutids = new HashMap<>();
    Pattern pattern;
    String Optionlanguage,regOption;
    int layoutcounter = 0,clickedFormId,current_form_id;;
    String clickedForm;
    String PreviousQuesAnswertype;
    TreeMap<Integer, Integer> runtimevalidationlist = new TreeMap<>();// this treemap is used to store all scroll id's associated for edittext
    List<CalculateVisit> calculatevisitList = null;
    List<CalculateVisit> calculatePncVisitList = null;
    List<CalculateVisit> calculateChildCareVisitList = null;
    List<CalculateVisit> calculatevisitList1 = null;
    int Highriskmin, Highriskmax, Refferriskmin, Refferriskmax;
    HashMap<String, String> highrisklist = new HashMap<>();
    HashMap<String, String> referrallist = new HashMap<>();
    HashMap<String, String> patientvisitlist = new HashMap<>();
    HashMap<String, String> HighRiskRangelist = new HashMap<>();
    HashMap<String, String> ReferralRangelist = new HashMap<>();
    List<String> counclingMsgQstnKeywords = new ArrayList<>();
    TreeMap<String, List> Backup_customespinner = new TreeMap<>();
    String defaultdate;
    // this hashmap is used to stored multi level conditions for high risk,counselling,referral
    List<Visits> StoredHighRiskRanges = null;
    HashMap<String, String> ConditionLists = new HashMap<>();
    List<Visits> StoredCounsellingRanges = null;
    List<Visits> StoredPatientVisitSummaryRanges = null;
    String deliveryDate, motherClosureDate, childClosureDate,TT1Dose="",TT2Dose="",TTDose1Date="";
    Double range_min;
    Double range_max;
    String range_lang;
    Double length_min;
    Double length_max;
    String length_lang;
    String view_check = "";
    boolean HighriskStatus = false;
    boolean RefferalStatus = false;
    boolean SaveFormStatus = false;
    int wages_status;
    boolean ImportantDialogStatus = false;
    String serverDate;
    List<String> tempdependantStore = new ArrayList<>();
    HashMap<String, List> MainQuestempstoredependant = new HashMap<>();
    HashMap<String, String> dependantKeywordPresent = new HashMap<>();
    int maxautoId, storePrevAncId;
    String mctsMotherID;
    Boolean isCompulsory;
    int parentid = 1, FormID;
    HashMap<Integer, LinearLayout> parentLayoutchild = new HashMap<>();
    String PregnancyStatus;
    int formUploadId, referenceId;
    Double womanHeight, womanWeight;
    //	DecimalFormat decimalFormat = new DecimalFormat("#.00");
    InputMethodManager inputMethodManager;
    Bundle b;
    HashMap<Integer, Label> labelKeywirdDetails = new HashMap<>();
    DateTimeFormatter dateTimeFormat;
    DateFormat timeFormat;
    Calendar calendar;
    Boolean isdataFromRegChildList;
    SimpleDateFormat newDateFormat;
    TimePickerDialog mTimePicker;
    Integer mamtaCardPresent;
    Integer firstForm;
    ProgressDialog progressDialog;
    NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
    DecimalFormat decimalFormat = (DecimalFormat) nf;
    SimpleDateFormat YMDFormat, DMYFormat;
    String pageCountText;
    TextView textViewTotalPgCount;
    String visitId;
    List<ListClass> womenData = null;
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
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat serverdateFormatter;
    private String serverdefaultdateFormatter;
    private List<ListClass> recieveListOverDueCount = new ArrayList<>();
    private List<ListClass> recieveListDueCount = new ArrayList<>();
    private int overDueVisitsCount, dueVisitsCount;
    private String uniqueId = "";
    private String mAppLanguage;
    GradientDrawable drawableMainQstn, drawableDependentQstn;
    HashMap<String, String> hashMapVideoNamePath = new HashMap<>();
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    String Name;
    private Uri fileUri;
    Bitmap photo;
    ImageView iv;
    /**
     * This method gives the next visit date of the ANM
     *
     * @param lmp      = lmp of the woman
     * @param no_weeks = days of the next visit
     * @return next visit date in string format
     */
    public static String nextvisitdate(String lmp, String no_weeks) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat DMYFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String fromDate = lmp;
        String next_visit = null;


        try {
            Date startDate = df.parse(fromDate);
            Calendar c1 = Calendar.getInstance();
            //Change to Calendar Date
            c1.setTime(startDate);
            c1.add(Calendar.WEEK_OF_MONTH, (Integer.parseInt(no_weeks) / 7));
            //System.out.println("startDate" + df.format(c1.getTime()));
            next_visit = DMYFormat.format(c1.getTime());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return next_visit;
    }

    public static boolean isStringValid(String str) {
        boolean isValid = false;
        String expression = "[" + "-/@#!*$%^&.'_+={}()" + "]+";
        CharSequence inputStr = str;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anc_cc_visit);

        new DisplayQuestions().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public void labelCalculation(TextView labelTextView, Label label) throws ParseException {

        String textOnLabel = label.getTextOnLabel();

        labelTextView.setGravity(Gravity.START);

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

                case "date":

                    if (womendetails.containsKey(split_str[2].trim())) {
                        String date_cal = expression.substring(expression.indexOf("(") + 1, expression.length() - 1);

                        date_cal = date_cal.replace(split_str[2].trim(), serverdateFormatter.format(dateFormatter.parse(womendetails.get(split_str[2].trim()))));

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                        Calendar c= Calendar.getInstance();
                        try {
                            c.setTime(simpleDateFormat.parse(date_cal));
                        }
                        catch(Exception e){
                        }

                        //Date maxDate = new StringToTime(serverdefaultdateFormatter + " - 028 days");
                        result=serverdateFormatter.format(c.getTime());
                      //  result = String.valueOf(serverdateFormatter.format(new StringToTime(date_cal)));

                        if (result != null) {
                            String dt = dateFormatter.format(c.getTime());
                            womendetails.put(split_str[0].trim(), dt);
                            textOnLabel = textOnLabel.replace(split_str[0].trim(), dt);
                        } else
                            textOnLabel = textOnLabel.replace(split_str[0].trim(), getString(R.string.no_data));
                    } else
                        textOnLabel = textOnLabel.replace(split_str[0].trim(), getString(R.string.no_data));

                    break;

                case "todays_date":

                    result = serverdefaultdateFormatter;

                    if (result != null) {
                        womendetails.put(split_str[0].trim(), defaultdate);
                        textOnLabel = textOnLabel.replace(split_str[0].trim(), defaultdate);
                    }

                    break;

                case "current_time":

                    calendar = Calendar.getInstance();
                    result = timeFormat.format(calendar.getTime());

                    if (result != null) {
                        womendetails.put(split_str[0].trim(), result);
                        textOnLabel = textOnLabel.replace(split_str[0].trim(), result);
                    }

                    break;

                default:

                    try {
                        Interpreter interpreter = new Interpreter();

                        for (String aSplit_str : split_str) {
                            if (womendetails.containsKey(aSplit_str.trim())) {
                                interpreter.set(aSplit_str.trim(), Double.parseDouble(womendetails.get(aSplit_str.trim())));
                            }
                        }
                        result = String.valueOf(interpreter.eval(expression));

                        if (!result.equals("true") && !result.equals("false")) {
                            result = decimalFormat.format(Double.parseDouble(result));
                            textOnLabel = textOnLabel.replace(split_str[0].trim(), result);
                        } else
                            textOnLabel = textOnLabel.replace(split_str[0].trim(), getString(R.string.no_data));
                    } catch (EvalError evalError) {
                        questionInteractor.deleteAnswer(uniqueId, formid, split_str[0].trim());
                        evalError.printStackTrace();
                    }

                    break;
            }

            labelTextView.setText(textOnLabel);

            if (result != null) {
                edittextID++;
                womendetails.put(split_str[0].trim(), result);
                Backup_answerTyped1.put(split_str[0].trim(), result);
            }

        }
    }

    public void NextButtonValidations() {
        try {
            int counter = 0;
            int totalpagecondition = 0;
            Boolean isCompulsoryQstnInFocus = false;
            int pageno = scrollId.get(scrollcounter);

            /**
             * before going to next page check if validations field present on that page is filled or not.
             * Hashmap iterator is used for this purpose to check how many validations are their on single page.
             */
            for (Map.Entry<String, Integer> entry : NextButtonvalidationlist.entrySet()) {
                entry.getKey();
                entry.getValue();

                if (entry.getValue() != null) {

                    if (entry.getValue() == pageno) {
                        totalpagecondition++;

                        /**
                         * validationlist is a TreeMap where questions which are compulsory its's keyword and answer is stored.
                         * if a specific answer is not stored for that keyword that means question is not answered by the user.
                         */
                        if (validationlist.get(entry.getKey()) != null && validationlist.get(entry.getKey()).length() > 0) {
                            counter++;
                        } else {

                            if (!isCompulsoryQstnInFocus) {
                                LinearLayout ll4_temp = (LinearLayout) scroll_temp.getChildAt(0);
                                for (int i = 0; i < ll4_temp.getChildCount(); i++) {
                                    if (ll4_temp.getChildAt(i) instanceof LinearLayout) {
                                        LinearLayout ll_temp = (LinearLayout) ll4_temp.getChildAt(i);
                                        String keyword = (String) ll_temp.getTag();

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
            }

            /**
             * this if condition is used to check whether total compulsory question on that page is answered or not.
             * if totalpagecondition matches with counter no. that means all the complusory questions are answered.
             */

            if (totalpagecondition == counter) {
                previous.setVisibility(View.VISIBLE);

                questionInteractor.saveQuestionAnswers(Backup_answerTyped1, maxautoId, uniqueId, Integer.parseInt(formid), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date()));

                Backup_answerTyped1.clear();

                scroll_temp = (ScrollView) Frame.findViewById(Integer.parseInt(String.valueOf(scrollId.get(scrollcounter))));
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
                    System.out.println("inside if condition+++++++" + scrollcounter);
                    scroll_temp = (ScrollView) Frame.findViewById(Integer.parseInt(String.valueOf(scrollId.get(scrollcounter))));
                    scroll_temp.setVisibility(View.VISIBLE);

                    if (!ImportantDialogStatus) {
                        ImportantNote_Dialog();
                    } else if (!HighriskStatus) {
                        highriskdialog();
                    } else if (!RefferalStatus) {
                        referraldialog();
                    } else if (!SaveFormStatus) {
                        saveForm();
                    } else {
                        CalculateVisit calculateVisit;
                        calculateVisit = questionInteractor.getNextVisitOf(formid);
                        if (calculateVisit != null ) {
                            if (nextvisitdate(womanLmp, Integer.toString(calculateVisit.getFromWeek())) != null && nextvisitdate(womanLmp, Integer.toString(calculateVisit.getFromWeek())).length() > 0) {
                                NextVisit_dialog(calculateVisit.getANCVisit(), nextvisitdate(womanLmp, Integer.toString(calculateVisit.getFromWeek())), "NextVisit");
                            }
                        } else {
                            NextVisit_dialog("", "", "");
                        }
                    }



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
                Toast.makeText(getApplicationContext(), AncVisits.this.getString(R.string.Toast_msg_for_compulsory), Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * THis method is used to create edittext dynamically
     *
     *
     * @param i = for loop counter
     * @param language = question text
     * @param formid = formid
     * @param setid = setid of the question
     * @param keyword = question keyword
     * @param validationfield = this field describes whether question is compulsory or not
     * @param messages = this field contains json with multiple highrisk,counselling,referral conditions
     * @return layout (ll)
     */

    public LinearLayout createEdittext(int i, String language, final String formid, final String setid, final String keyword, final String validationfield, String messages, String displayCondition, int scrollID, final int orientation, String lengthmax)
    {


        System.out.println("inside createEdittext count" + i + "   keyword" + keyword);
        Boolean isCompulsory=false;

        try {
            JSONObject obj = new JSONObject(language);
            language=obj.getString(mAppLanguage);
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
        edittextID=edittextID+1;
        final EditText et = new EditText(this);

        //et.setText("edittextNo"+i);
        et.setMinLines(1);
        et.setMaxLines(3);
        et.setTag(keyword);
        et.setId(edittextID);
        et.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_edittext));
        et.setLongClickable(false);
        et.setSingleLine(true);
//		et.setPadding(20, 20, 20, 20);

        if(lengthmax != null && lengthmax.length()>0)
        {
            et.setFilters(new InputFilter[] {new InputFilter.LengthFilter(Integer.parseInt(lengthmax))});
        }

        runtimevalidationlist.put(et.getId(), scrollID);



        /**
         * This if condition is used to check whether the validation is required or not.
         * if validation is present then its answer is stored in validationlist treemap which is used to next when next button is clicked
         * scroll id is saved in NextButtonvalidationlist treemap to identify how many complusory questions are present on that page
         */
        if (validationfield != null && validationfield.equalsIgnoreCase("true")) {
            tv.setError("");
            validationlist.put("" + et.getTag(), et.getText().toString().trim());
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
                womendetails.put(keyword, et.getText().toString().trim());   // this hashmap is used to store
                Backup_answerTyped1.put(keyword, et.getText().toString().trim()); // this hashmap is used to insert dataSource in Backup_AnswerEntered


                if (validationlist.containsKey(keyword)) {
                    if (et.getText().toString().trim().length() <= 0) {
                        validationlist.put("" + et.getTag(), "");
                        tv.setError("");
                    } else {
                        validationlist.put("" + et.getTag(), et.getText().toString().trim());
                        tv.setError(null);
                    }
                }


                if (et.getText().toString().trim().equals("")) {
                    Backup_answerTyped1.remove(keyword);
                    womendetails.remove(et.getId());

                    questionInteractor.deleteAnswer(uniqueId, formid, keyword);
                }
            }
        });

        if (displayCondition != null && displayCondition.length() > 0 && previousVisitDetails.containsKey(keyword)) {
            et.setText(previousVisitDetails.get(keyword));
            et.setEnabled(false);
            tv.setError(null);
            validationlist.put("" + et.getTag(), previousVisitDetails.get(keyword));
            womendetails.put(keyword, et.getText().toString().trim());
            Backup_answerTyped1.put(keyword, et.getText().toString().trim());

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
     * THis method is used to create Date Field dynamically
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
    @SuppressLint("ClickableViewAccessibility")
    public LinearLayout createDate(int i, String language, final String formid, final String setid, final String keyword, final String validationfield, String messages, String displayCondition, int scrollID, final int orientation) {

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

        String defaultServerdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        edittextID = edittextID + 1;
        final EditText et = new EditText(this);
        //et.setText("edittextNo"+i);
        et.setMinLines(1);
        et.setMaxLines(3);
        //et.setHint(defaultdate);
        et.setId(edittextID);
        et.setTag(keyword);
        et.setLongClickable(false);
        et.setSingleLine(true);
        et.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_edittext));
        et.setFocusable(false);
        et.setClickable(true);
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
            Date dt = null;
            try {
                dt = serverdateFormatter.parse(womendetails.get(keyword));
                et.setText(formatter.format(dt));
                if (validationfield == null || !validationfield.equalsIgnoreCase("true"))
                    et.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear, 0);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            if (validationfield != null && validationfield.equalsIgnoreCase("true")) {
                validationlist.put("" + et.getTag(), womendetails.get(keyword));
                tv.setError(null);

            }
        }

        switch (keyword) {
            case "mother_closure_date":

                et.setText(defaultdate);
                et.setEnabled(false);
                tv.setError(null);
                womendetails.put(keyword, defaultServerdate);
                Backup_answerTyped1.put(keyword, defaultServerdate);
                validationlist.put("" + et.getTag(), et.getText().toString());

                break;
            case "child_closure_date":
                et.setText(defaultdate);
                et.setEnabled(false);

                tv.setError(null);

                womendetails.put(keyword, defaultServerdate);
                Backup_answerTyped1.put(keyword, defaultServerdate);
                validationlist.put("" + et.getTag(), et.getText().toString());
                break;

            case CHILD_DOB:

                String deliveryStatus = questionInteractor.getDob(uniqueId);
                try {

                    et.setText(DMYFormat.format(YMDFormat.parse(deliveryStatus)));
                    et.setFocusable(false);
                    et.setError(null);
                    et.setEnabled(false);
                    tv.setError(null);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                womendetails.put(keyword, deliveryStatus);
                Backup_answerTyped1.put(keyword, deliveryStatus);
                validationlist.put("" + et.getTag(), et.getText().toString());

                break;

            case "visit_date":
                et.setText(defaultdate);
                et.setEnabled(false);

                tv.setError(null);

                womendetails.put(keyword, defaultServerdate);
                Backup_answerTyped1.put(keyword, defaultServerdate);
                validationlist.put("" + et.getTag(), et.getText().toString());
                break;

            default:
                break;
        }

        if (messages != null && messages.length() > 0) {
            ConditionLists.put(keyword, messages);
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

                        //System.out.println("newDate.getTime()******"+dateFormatter1.format(newDate.getTime()));
                        et.setText(dateFormatter.format(newDate.getTime()));
                        if (validationfield == null || !validationfield.equalsIgnoreCase("true"))
                            et.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear, 0);
                        serverDate = serverdateFormatter.format(newDate.getTime());

                        String getDate = dateFormatter.format(newDate.getTime());

                        try {
                            selectedDate = formatter.parse(getDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (SystemDate.compareTo(selectedDate) >= 0) {
                            String myFormat = "yyyy-MM-dd"; //In which you need put here
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                            et.setError(null);
                            System.out.println("keyword = " + keyword);
                            storeEnteredData.put(keyword, sdf.format(newDate.getTime()).toString());
                            System.out.println("storeEnteredData = " + storeEnteredData.get(keyword));
                            validationlist.remove("" + et.getTag());
                            NextButtonvalidationlist.remove("" + et.getTag());
                            tv.setError(null);
                        } else {
                            et.setError(getApplicationContext().getString(R.string.exceed_curnt_dt));
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.exceed_curnt_dt), Toast.LENGTH_LONG).show();
                            validationlist.put("" + et.getTag(), "");
                            NextButtonvalidationlist.put("" + et.getTag(), runtimevalidationlist.get(et.getId()));
                        }

                        womendetails.put(keyword, serverDate);
                        Backup_answerTyped1.put(keyword, serverDate); // this hashmap is used to insert dataSource in Backup_AnswerEntered

//						if (validationlist.containsKey(et.getTag()))
//						{
//							if (lmpvalid) {
//								validationlist.put(""+et.getTag(), et.getText().toString());
//								NextButtonvalidationlist.put(""+et.getTag(), runtimevalidationlist.get(et.getId()));
//							} else {
//								System.out.println("lmpvalid inside else afterTextChanged" + lmpvalid);
//								validationlist.put(""+et.getTag(), "");
//								NextButtonvalidationlist.put(""+et.getTag(), runtimevalidationlist.get(et.getId()));
//							}
//						}

                    }


                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


            }


        });

        if (displayCondition != null && displayCondition.length() > 0 && previousVisitDetails.containsKey(keyword)) {
            try {
                Date dt = serverdateFormatter.parse(previousVisitDetails.get(keyword));
                et.setText(formatter.format(dt));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            et.setEnabled(false);
            tv.setError(null);
            validationlist.put("" + et.getTag(), previousVisitDetails.get(keyword));
            womendetails.put(keyword, previousVisitDetails.get(keyword));
            Backup_answerTyped1.put(keyword, previousVisitDetails.get(keyword));

        }

        ll.setTag(keyword);

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
        //et.setText("edittextNo"+i);
        et.setMinLines(1);
        et.setMaxLines(3);
        //et.setHint(defaultdate);
        et.setId(edittextID);
        et.setTag(keyword);
        et.setFocusable(false);
        et.setClickable(true);
        et.setSingleLine(true);
        et.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_edittext));
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

                mTimePicker = new TimePickerDialog(AncVisits.this, new TimePickerDialog.OnTimeSetListener() {
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
                womendetails.put(keyword, et.getText().toString());
                Backup_answerTyped1.put(keyword, et.getText().toString()); // this hashmap is used to insert dataSource in Backup_AnswerEntered

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

        if (displayCondition != null && displayCondition.length() > 0 && previousVisitDetails.containsKey(keyword)) {
            et.setText(previousVisitDetails.get(keyword));
            et.setEnabled(false);

            validationlist.put("" + et.getTag(), previousVisitDetails.get(keyword));
            womendetails.put(keyword, et.getText().toString());
            Backup_answerTyped1.put(keyword, et.getText().toString());

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
     * This method is used to create int dynamically
     *
     * @param answerType          = for loop counter
     * @param language            = question text
     * @param formid              = formid
     * @param setid               = setid of the question
     * @param keyword             = keyword of the question
     * @param validationfield     = = this field describes whether question is compulsory or not
     * @param validationcondition = this field is used to get validation conditions if any
     * @param validationmsg       = validation msg which is to shown it the condition matches
     * @param Lengthmin           = minimum length validation condition
     * @param Lengthmax           = maximum length validation condition
     * @param Lengthmsg           = error message if the length condition is not matched
     * @param Rangemin            == minimum range validation condition
     * @param Rangemax            = maximum range validation condition
     * @param Rangemsg            = error message if the range condition is not matched
     * @param HighRiskRange       = range validations for high risk
     * @param HighRiskLang        = error msg of high risk
     * @param RefferalRange       = range validations for referral
     * @param ReferralLang        = error msg of referral
     * @param Counsellingmsg      = counselling range and messages
     * @param messages            = = this field contains json with multiple highrisk,counselling,referral conditions
     * @return ll
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public LinearLayout createInt(String answerType, String language, final String formid, final String setid, final String keyword,
                                  final String validationfield, final String validationcondition, String validationmsg,
                                  final String Lengthmin, final String Lengthmax, String Lengthmsg, final String Rangemin,
                                  final String Rangemax, String Rangemsg, final String HighRiskRange, final String HighRiskLang,
                                  final String RefferalRange, final String ReferralLang, final String Counsellingmsg,
                                  String messages, String displayCondition, int scrollID, final int orientation) {

        System.out.println("inside method count" + answerType + "   keyword" + keyword);

        try {
            JSONObject obj = new JSONObject(language);
            language = obj.getString(mAppLanguage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (Lengthmsg != null && Lengthmsg.length() > 0) {
                JSONObject vali_lengthmsg_obj = new JSONObject(Lengthmsg);
                Lengthmsg = vali_lengthmsg_obj.getString(mAppLanguage);
                System.out.println("Lengthmsg" + Lengthmsg);
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


        final TextView tv = new TextView(this);
        tv.setText("" + language);
        tv.setId(textId + 1);
        // tv.setGravity(Gravity.CENTER);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.text_color));
        tv.setLayoutParams(lp);

        edittextID = edittextID + 1;
        final EditText et = new EditText(this);
        //et.setText("edittextNo"+i);
        et.setMinLines(1);
        et.setMaxLines(3);
        //et.setHint("edittextNo" + i);
        et.setId(edittextID);
        et.setTag(keyword);
        et.setLongClickable(false);
        et.setSingleLine(true);
        et.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_edittext));
        //System.out.println("createDate"+et.getId());

        if (orientation == 1) {
            ll.setOrientation(LinearLayout.HORIZONTAL);
            tv.setLayoutParams(lpHorizontal);
            et.setLayoutParams(lpHorizontal);
        }

        ll.setTag(keyword);
        ll.addView(tv);
        ll.addView(et);

        if (Lengthmax != null && Lengthmax.length() > 0) {
            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.parseInt(Lengthmax))});
        }

        switch (answerType) {
            case "int":
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;

            default:
                et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                et.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(4, 2)});
                break;
        }

        switch (keyword) {
            case "gestational_age":

                int gestAge = getGestationalAgeWeek(defaultdate, womanLmp);
                et.setText("" + gestAge);
                et.setEnabled(false);
                et.setError(null);
                tv.setError(null);

                womendetails.put(keyword, "" + gestAge);
                Backup_answerTyped1.put(keyword, ""+ gestAge);
                validationlist.put(keyword, et.getText().toString());
                break;

            case "bmi":
                et.setEnabled(false);
                break;

            case "height_units":
            et.setEnabled(false);
            tv.setError(null);
            ArrayList<String> womanhHeightData=questionInteractor.womenHeight(uniqueId,formid);
            String height= womanhHeightData.get(0);
            String height_units=womanhHeightData.get(1);
                if(height.equals("-1")){
                    et.setText("NA");
                }
                else {
                    et.setText(height);
                }

            if(height_units.equals("height_units_cm")){
                tv.setText("height in cm");
                tv.setError(null);

                womendetails.put("height_of_women",""+height);
            }
            else{
                tv.setText("height in feet");
                tv.setError(null);
                womendetails.put("height_in_feet",""+height);
            }
                break;

        }


        runtimevalidationlist.put(et.getId(), scrollID);

        HighRiskRangelist.put("" + et.getId(), HighRiskRange);
        ReferralRangelist.put("" + et.getId(), RefferalRange);
        /**
         * This if condition is used to check whether the validation is required or not.
         * if validation is present then its answer is stored in validationlist treemap which is used to next when next button is clicked
         * scroll id is saved in NextButtonvalidationlist treemap to identify how many complusory questions are present on that page
         */
       if (validationfield != null && validationfield.equalsIgnoreCase("true")) {
            if(keyword.equals("height_units")|| keyword.equals("height_of_women")
                    || keyword.equals("height_in_feet")) {
                et.setError(null);
                tv.setError(null);
                validationlist.remove("height_units");
                NextButtonvalidationlist.remove("height_units");
            }
            else {
                if (validationmsg != null && validationmsg.length() > 0) {
                    et.setError(validationmsg);
                } else {
                    et.setError(AncVisits.this.getString(R.string.default_validation_msg));
                }
                tv.setError("");
                validationlist.put("" + et.getTag(), et.getText().toString());
                NextButtonvalidationlist.put("" + et.getTag(), scroll.getId());
            }
        }

        if (messages != null && messages.length() > 0) {
            ConditionLists.put(keyword, messages);
        }

        /**
         * this if condition is used to display the dataSource which is already entered by the woman
         */
        if (womendetails.containsKey(keyword)) {
            et.setText(womendetails.get(keyword));
            if (validationfield != null && validationfield.equalsIgnoreCase("true")) {
                validationlist.put("" + et.getTag(), womendetails.get(keyword));
                tv.setError(null);
                et.setError(null);
            }

            messages = ConditionLists.get(keyword);

            StoredHighRiskRanges = new ArrayList<>();
            StoredCounsellingRanges = new ArrayList<>();
            StoredPatientVisitSummaryRanges = new ArrayList<>();

            StorePVSmsgs(messages);
            StorePatientVisitHighRiskDiagnosticValues(womendetails.get(keyword), et, keyword, setid);
        }




        if (displayCondition != null && displayCondition.length() > 0 && previousVisitDetails.containsKey(keyword)) {
            et.setText(previousVisitDetails.get(keyword));
            et.setEnabled(false);
            et.setError(null);
            validationlist.put("" + et.getTag(), previousVisitDetails.get(keyword));
            womendetails.put(keyword, et.getText().toString());
            Backup_answerTyped1.put(keyword, et.getText().toString());

        }


        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                String messages = ConditionLists.get(keyword);

                StoredHighRiskRanges = new ArrayList<>();
                StoredCounsellingRanges = new ArrayList<>();
                StoredPatientVisitSummaryRanges = new ArrayList<>();

                StorePVSmsgs(messages);


            }
        });

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
                        if(keyword.equals("bmi"))
                        {
                            //remove bmi calculator
                        }
                        else
                            {
                                StorePatientVisitHighRiskDiagnosticValues(et.getText().toString(), et, keyword, setid);
                            }
                    System.out.println("createInt onFocusChange" + et.getId());
                    womendetails.put(keyword, et.getText().toString());
                    Backup_answerTyped1.put(keyword, et.getText().toString());

                    if (et.getText().toString().length() > 0 && Lengthmin != null && Lengthmin.length() > 0) {
                        System.out.println("women_mob_no length max:  " + Lengthmax + " min: " + Lengthmin + " et.length: " + et.getText().toString().length());
                        if ((Integer.parseInt(Lengthmin) <= et.getText().toString().length()) &&
                                (et.getText().toString().length() <= Integer.parseInt(Lengthmax))) {
                            System.out.println("women_mob_no length max:  " + Lengthmax + " min: " + Lengthmin + " et.length: " + et.getText().toString().length() + " LENGTH validation: true");
                            et.setError(null);
                            tv.setError(null);
                            //next.setEnabled(true);

                            validationlist.put("" + et.getTag(), et.getText().toString());
//						NextButtonvalidationlist.put(et.getId(), scroll.getId());

                        } else {
                            System.out.println("women_mob_no length max:  " + Lengthmax + " min: " + Lengthmin + " et.length: " + et.getText().toString().length() + " LENGTH validation: false");
                            et.setError(finalLengthmsg);
                            //next.setEnabled(false);

                            validationlist.put("" + et.getTag(), "");
                            NextButtonvalidationlist.put("" + et.getTag(), runtimevalidationlist.get(et.getId()));
                        }
                    } else {
                        checkIsCompulsory(et, keyword, tv, validationfield);
//						et.setError(null);
//						validationlist.remove(""+et.getTag());
//						NextButtonvalidationlist.remove(""+et.getTag());
                    }

                    if (et.getText().toString().length() > 0 && Rangemin != null && Rangemin.length() > 0 && et.getText().toString().length() > 0) {
                        if ((Double.parseDouble(Rangemin) <= Double.parseDouble(et.getText().toString())) && (Double.parseDouble(et.getText().toString()) <= Double.parseDouble(Rangemax))) {
                            et.setError(null);
                            tv.setError(null);
                            //next.setEnabled(true);

                            validationlist.put("" + et.getTag(), et.getText().toString());
//						NextButtonvalidationlist.put(et.getId(), scroll.getId());

                        } else {
                            et.setError(finalRangemsg);
                            //next.setEnabled(false);

                            validationlist.put("" + et.getTag(), "");
                            NextButtonvalidationlist.put("" + et.getTag(), runtimevalidationlist.get(et.getId()));
                        }
                    } else {
                        checkIsCompulsory(et, keyword, tv, validationfield);
//						et.setError(null);
//						validationlist.remove(""+et.getTag());
//						NextButtonvalidationlist.remove(""+et.getTag());
                    }

                    System.out.println("HighRiskLang**" + HighRiskLang);
                    if (HighRiskLang != null && HighRiskLang.length() > 0 && et.getText().toString().length() > 0) {
                        System.out.println("Highriskmin**" + Highriskmin);
                        System.out.println("Highriskmax**" + Highriskmax);
                        if ((Highriskmin <= Double.parseDouble(et.getText().toString())) && (Double.parseDouble(et.getText().toString()) <= Highriskmax)) {

                            System.out.println("Not In High Risk");
                            highrisklist.remove("" + et.getId());

                            LinearLayout ll_1 = (LinearLayout) et.getParent();
                            ll_1.setBackgroundColor(ctx.getResources().getColor(R.color.anc_form));

                            if (counclingMsgQstnKeywords.contains(keyword)) {
                                LinearLayout ll4_1 = (LinearLayout) ll_1.getParent();
//							LinearLayout ll_1= (LinearLayout) v.getParent().getParent();

                                ll4_1.removeViewAt((ll4_1.indexOfChild(ll_1)) + 1);
                                counclingMsgQstnKeywords.remove(keyword);
                            }


//						et.setError(null);
//						//next.setEnabled(true);
//
//						validationlist.put(et.getId(), et.getText().toString());
////						NextButtonvalidationlist.put(et.getId(), scroll.getId());

                        } else {
                            LinearLayout ll_1 = (LinearLayout) et.getParent();
                            ll_1.setBackgroundColor(ctx.getResources().getColor(R.color.highrisk));

                            try {
                                if (Counsellingmsg != null && Counsellingmsg.length() > 0) {
                                    if (!counclingMsgQstnKeywords.contains(keyword)) {
                                        JSONObject obj = new JSONObject(Counsellingmsg);
                                        String language = obj.getString(mAppLanguage);
                                        LinearLayout ll4_1 = (LinearLayout) ll_1.getParent();
//									LinearLayout ll_1= (LinearLayout) et.getParent();

                                        language = language.replace("\\n ", "\n");
                                        //language=language.replace("\\n");

                                        System.out.println("counsel_message................" + language + "....tag.........." + keyword);

                                        TextView counslingMsgTextView = new TextView(ctx);
                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                            counslingMsgTextView.setText(Html.fromHtml(language, Html.FROM_HTML_MODE_LEGACY));
                                        } else {
                                            counslingMsgTextView.setText(Html.fromHtml(language));
                                        }
                                        counslingMsgTextView.setBackgroundColor(ctx.getResources().getColor(R.color.dependent_question_background));
                                        counslingMsgTextView.setTextColor(Color.RED);
                                        counslingMsgTextView.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
                                        counslingMsgTextView.setLayoutParams(layoutParamQuestion);

                                        counslingMsgTextView.setPadding(20, 0, 20, 20);

                                        ll4_1.addView(counslingMsgTextView, ((ll4_1.indexOfChild(ll_1)) + 1));

                                        counclingMsgQstnKeywords.add(keyword);
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //System.out.println(HighRiskLang);
                            //et.setError("In High Risk");

                            highrisklist.put("" + et.getId(), "" + formid + delimeter + keyword + delimeter + et.getId() +delimeter + HighRiskLang);

                            //	highrisklist.put(quesid, "" + formid + Common.delimeter + setid + Common.delimeter + keyword + Common.delimeter + rb.getTag() + Common.delimeter + main_ques_options_key.optString("languages").toString() + Common.delimeter + "highrisk" + Common.delimeter + "0");

                        }

                    }


                    System.out.println("HighRiskLang**" + ReferralLang);
                    if (ReferralLang != null && ReferralLang.length() > 0 && et.getText().toString().length() > 0) {
                        System.out.println("Highriskmin**" + Refferriskmin);
                        System.out.println("Highriskmax**" + Refferriskmax);
                        if ((Refferriskmin <= Double.parseDouble(et.getText().toString())) && (Double.parseDouble(et.getText().toString()) <= Refferriskmax)) {

                            //System.out.println("Not In High Risk");
                            referrallist.remove("" + et.getId());

//						et.setError(null);
//						//next.setEnabled(true);
//
//						validationlist.put(et.getId(), et.getText().toString());
////						NextButtonvalidationlist.put(et.getId(), scroll.getId());

                        } else {
                            //System.out.println(HighRiskLang);
                            //et.setError("Reffer");

                            referrallist.put("" + et.getId(), "" + ReferralLang);
                        }

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (et.getText().toString().equals("")) {
                    womendetails.remove(keyword);
                    Backup_answerTyped1.remove(keyword);

                    questionInteractor.deleteAnswer(uniqueId, formid, keyword);
                }

                switch ("" + et.getTag()) {
                    case "weight_of_women":
                    case "height_of_women":
                    case "height_in_feet":
                    case "height_units":

                        if (womendetails.containsKey("weight_of_women") && womendetails.containsKey("height_of_women")) {
                            womanWeight = Double.parseDouble(womendetails.get("weight_of_women"));
                            womanHeight = Double.parseDouble(womendetails.get("height_of_women"));
                            bmiCalculation(true, setid);
                        } else if (womendetails.containsKey("weight_of_women") && womendetails.containsKey("height_in_feet")) {
                            womanWeight = Double.parseDouble(womendetails.get("weight_of_women"));
                            Double heightInFeet = Double.parseDouble(womendetails.get("height_in_feet"));
                            Double heightInInch = heightInFeet * 12;
                            womanHeight = heightInInch * 2.54;
                            bmiCalculation(true, setid);
                        } else {
                            bmiCalculation(false, setid);
                        }

                        break;

                    default:
                        break;
                }
            }
        });

        return ll;
    }

    public void bmiCalculation(Boolean calculatn, String setid) {
        LinearLayout ll = (LinearLayout) Frame.findViewWithTag("bmi");
        EditText bmiEditText = (EditText) ll.getChildAt(1);

        if (calculatn) {

            womanHeight = Math.pow((womanHeight / 100), 2);

            String result = decimalFormat.format(womanWeight / womanHeight);
            bmiEditText.setText(result);

            womendetails.put("bmi", result);
            Backup_answerTyped1.put("bmi", result);
            bmiEditText.setError(null);
            validationlist.remove("bmi");
            NextButtonvalidationlist.remove("bmi");
        } else
            bmiEditText.setText("");


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
        iv = new ImageView(this);
        final Button capture = new Button(this);
        TextView tv = new TextView(this);

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

        tv.setText("" + language);
        tv.setId(textId + 1);
        // tv.setGravity(Gravity.CENTER);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.text_color));
        tv.setTag(keyword);
        tv.setLayoutParams(lp);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 450);
        layoutParams.setMargins(30, 30, 30, 30);
        layoutParams.gravity = Gravity.CENTER;
        iv.setLayoutParams(layoutParams);

        capture.setText(AncVisits.this.getString(R.string.capture_photo_text));
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
//                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
//                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
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
     * THis method is used to create RAdio field dynamically
     *
     * @param i               = for loop counter
     * @param quesid          = this field contains the question id
     * @param language        = question text
     * @param setid           = setid of the question
     * @param keyword         = question keyword
     * @param validationfield = this field describes whether question is compulsory or not
     * @param formid          = formid of the question
     * @param messages        = = this field contains json with multiple highrisk,counselling,referral conditions
     * @param avoidRepetition
     * @return layout (ll)
     */
    public LinearLayout createRadio(int i, final String quesid, String language, final String setid, final String keyword, String validationfield, final String formid, String messages, final String displayCondition, final int scrollID, final int orientation, String avoidRepetition) {

        //iron_sucrose_injection_given || hb_level

        System.out.println("inside createRadio count " + i + " keyword " + keyword + " set id +++" + setid + " +++quesid+++ " + quesid);


        final Integer includechild = Integer.parseInt(quesid);

        try {
            System.out.println("language = " + language);
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
//        optionList = dbhelper.getANCEnglishoptions(quesid);  // this db statement gets q.keyword,q.answer_type,qi.keyword,qi.dependants,qi.depend_lang_eng,qi.depend_lang_mara,qi.action for that specific quesid in english
        optionList = questionInteractor.getQuestionOptions(quesid, String.valueOf(FormID));

        final RadioGroup rg = new RadioGroup(this); //create the RadioGroup
        rg.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
        rg.setId(radiogroup);
//        rg.setPadding(20, 10, 20, 10);
        rg.setLayoutParams(segmentedBtnLp);

        runtimevalidationlist.put(rg.getId(), scrollID);

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
            if (keyword.equals("tt_1_dose_given")) {
                if(TT1Dose!=null) {
                    if (TT1Dose.equals("tt_1_given_yes") && FormID > firstForm && firstForm != 1 && FormID != firstForm)
                        isCompulsory = false;
                }
            } else if (keyword.equals("tt_2_dose_given")) {
                if(FormID==firstForm){
                    isCompulsory = false;
                }
                else if(TT1Dose==null || TT1Dose.equals("tt_1_given_no")  && FormID>firstForm && firstForm!=1 && FormID!=firstForm){
                    isCompulsory = false;
                }
            }
            else if(keyword.equals("tt_booster_given")){
                if(FormID==firstForm){
                    isCompulsory = false;
                }
                else if(TT1Dose.equals("tt_1_given_no") && TT2Dose.equals("tt_2_given_no") ){
                    isCompulsory = false;
                }
                else if(TT1Dose.equals("tt_1_given_yes") && TT2Dose.equals("tt_2_given_no")){
                    isCompulsory = false;
                }
            }

            else {
                tv.setError("");
                validationlist.put(keyword, "");
                NextButtonvalidationlist.put(keyword, scroll.getId());
                isCompulsory = true;
            }
        } else {
            isCompulsory = false;
        }

        System.out.println("createRadio optionList.size() ***" + optionList.size());


        if (optionList.size() < 4) {
            ll.addView(rg);

            /**
             * This for loop is used to display the radio buttons for the given question
             */
            for (int k = 1; k < optionList.size(); k++) {
                radio = radio + 1;

                try {
                    JSONObject obj = new JSONObject(optionList.get(k).getQuestionText());
                    Optionlanguage = obj.getString(mAppLanguage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final RadioButton rb = new RadioButton(ctx);
                rb.setText(Optionlanguage);
                rb.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
                rg.addView(rb);
                rb.setTextColor(getResources().getColor(R.color.text_color));
                rb.setTag(optionList.get(k).getKeyword());
                rb.setPadding(20, 10, 20, 10);

                if (orientation == 0) rb.setLayoutParams(lparams);

                rb.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        hideSoftKeyboard(v);

                        PregnancyStatus = rb.getTag().toString();
                        int id = rg.getCheckedRadioButtonId();
                        int parentId = (((ViewGroup) rb.getParent()).getId()); //parentTag
                        final String clickedRB=rb.getTag().toString();
                        Log.d("RadioButtonClicked : ",""+rb.getTag().toString());


                        /** this logic is for checking whether woman has migrated or not
                         and if yes then to close her current ANC visit.*/
                        if(keyword.equals("has_women_migrated")){
                            switch(clickedRB)
                            {
                                case "has_women_migrated_yes":
                                    Backup_answerTyped1.put(keyword,clickedRB);
                                    Log.d("RadioButtonClicked : ",""+rb.getTag().toString() +"  checkedId  "+clickedRB);
                                    next.setEnabled(false);
                                    questionInteractor.saveQuestionAnswers(Backup_answerTyped1, maxautoId, uniqueId, Integer.parseInt(formid), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date()));
                                    saveForm();
                                    break;

                                case "has_women_migrated_no":
                                    Backup_answerTyped1.put(keyword,clickedRB);
                                    next.setEnabled(true);
                                    break;

                            }
                        }
                        if (PregnancyStatus.equals("close_no") || PregnancyStatus.equals("erase_the_case_no") || PregnancyStatus.equals("ec_close_no"))
                        {
                            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(AncVisits.this, R.style.AppCompatAlertDialogStyle);
                            builder.setCancelable(false);
                            builder.setTitle(R.string.back_form);
                            builder.setMessage(R.string.back_form_message);
                            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    rb.setChecked(false);
                                    RadioButton yes_btn = (RadioButton) rg.getChildAt(0);
                                    yes_btn.performClick();
                                }
                            });


                            builder.show();
                        }

                        onClickButtonFunctionality(rb, v, quesid, keyword, setid, id, tv, formid, optionList, displayCondition, "" + runtimevalidationlist.get(rg.getId()), isCompulsory, orientation);


                       /* if (v.getTag().toString().equals("FA_stock_yes")) {
                            //get
                            // IfaTablets((LinearLayout) v.getParent().getParent().getParent());


                            formulaFATablet(getGestationalAgeWeek(defaultdate, womanLmp), (LinearLayout) v.getParent().getParent().getParent());
                        }*/

                        if (v.getTag().toString().equals("do_you_have_enough_stock_of_ifa_tablet_yes")) {
                            getIfaTablets((LinearLayout) v.getParent().getParent().getParent());

                        }

                    }

                });

                if (womendetails.containsKey(keyword)) {

                    if (womendetails.get(keyword).equalsIgnoreCase(rb.getTag().toString())) {
                        rb.setChecked(true);
                        rb.performClick();

                    }

                    if (validationfield != null && validationfield.equalsIgnoreCase("true")) {

                        validationlist.put(keyword, womendetails.get(keyword));
                        tv.setError(null);
                    }

                }


                if (avoidRepetition != null &&
                        avoidRepetition.equalsIgnoreCase("true") &&
                        previousVisitDetails.containsKey(keyword) &&
                        previousVisitDetails.get(keyword).equalsIgnoreCase(keyword + "_yes")) {
                    System.out.println("vaccinations " + keyword + "true");
                    ll.setVisibility(View.GONE);
                } else {
                    System.out.println("vaccinations " + keyword + " false");
                }
                System.out.println("keyword "+keyword+" ##1");
                if (displayCondition != null && displayCondition.length() > 0 && previousVisitDetails.containsKey(keyword)) {
                    if (optionList.get(k).getKeyword().equals(previousVisitDetails.get(keyword))) {
                        rb.setChecked(true);
                        rb.performClick();
                    }

                    validationlist.put(keyword, previousVisitDetails.get(keyword));
                    rb.setEnabled(true);
                }

            }

            if (previousVisitDetails.containsKey("hb_level")) {

                double hb_level = Double.parseDouble(previousVisitDetails.get("hb_level"));

                if (hb_level >= 7 && ll.getTag().equals("iron_sucrose_injection_given")) {

                    ll_4layout.removeView(ll);

                    if (!validationlist.get(ll.getTag()).isEmpty())
                        validationlist.remove(ll.getTag());

                    NextButtonvalidationlist.remove(ll.getTag());

                }

            }

        } else if (optionList.size() == 4) {
            createButton(quesid, keyword, setid, rg.getId(), tv, formid, optionList, displayCondition, "" + runtimevalidationlist.get(rg.getId()), isCompulsory, orientation);
        } else {
            multipleRadioButton(quesid, keyword, setid, rg.getId(), tv, formid, optionList, displayCondition, "" + runtimevalidationlist.get(rg.getId()), isCompulsory, orientation);
        }

        return ll;
    }

    private void removeQuestion() {
        ll_4layout.removeView(ll);
        validationlist.remove(ll.getTag());
        NextButtonvalidationlist.remove(ll.getTag());
    }

    /**
     * THis method is used to create customize dropdown widget dynamically
     *
     * @param quesid             = question id of the registration
     * @param keyword            = question keyword
     * @param setid              = setid of the question
     * @param id                 = radio button id
     * @param tv                 = textview of the question
     * @param formid             = formid
     * @param multipleoptionlist = list of options which is to be displayed in dropdown
     * @param isCompulsory
     */
    public void multipleRadioButton(final String quesid, final String keyword, final String setid, final int id, final TextView tv, final String formid, List<Visit> multipleoptionlist, String displayCondition, final String pageScrollId, final Boolean isCompulsory, final int orientation) {

        final int[] multiplecounter = {0};
        System.out.println(" multipleRadioButton optionList list size" + multipleoptionlist.size());
        System.out.println("womendetails.containsKey(keyword)" + womendetails.containsKey(keyword));

        List<String> optionsList = new ArrayList<>();
        optionsList.add(AncVisits.this.getString(R.string.dropdown_text));

        try {
            for (int k = 1; k <= multipleoptionlist.size(); k++) {
                JSONObject obj = new JSONObject(multipleoptionlist.get(k).getQuestionText());

                Optionlanguage = obj.getString(mAppLanguage);
                optionsList.add(Optionlanguage);
            }
        } catch (Exception e) {
        }

        System.out.println("options list............" + optionsList);

        final Spinner spinner = new Spinner(this);
        spinner.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_spinner));

        customespinner = customespinner + 1;
        spinner.setId(customespinner);

        Backup_customespinner.put("" + spinner.getId(), multipleoptionlist);
        runtimevalidationlist.put(spinner.getId(), Integer.parseInt(pageScrollId));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, optionsList);
        spinner.setAdapter(arrayAdapter);

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
                hideSoftKeyboard(view);

                try {

                    ll_sub = (LinearLayout) view.getParent().getParent();

                    List<Visit> dependantList = null;

                    Visit m = (Visit) Backup_customespinner.get("" + spinner.getId()).get(pos);

                    if (m.getKeyword() != null && m.getKeyword().length() > 0) {

                        validationlist.put(keyword, m.getQuestionText());
                        tv.setError(null);
                        womendetails.put(keyword, m.getKeyword());
                        Backup_answerTyped1.put(keyword, m.getKeyword());

//                        dependantList = dbhelper.getDependantQuestionsListAncPnc("" + womendetails.get(keyword), formid, ll_sub, keyword, "" + runtimevalidationlist.get(spinner.getId()));  // this list is used to get dependant question in english format (parameter"s received are form_id,keyword,answer_type,english_lang)
                        dependantList = questionInteractor.getDependantQuesList("" + womendetails.get(keyword), formid, ll_sub, keyword, "" + runtimevalidationlist.get(spinner.getId()));  // this list is used to get dependant question in english format (parameter"s received are form_id,keyword,answer_type,english_lang)
                    } else {
                        if (validationlist.containsKey(keyword)) {
                            validationlist.put(keyword, "");
                            tv.setError("");
                            womendetails.remove(keyword);
                            Backup_answerTyped1.remove(keyword);
                        }

                    }

                    /**
                     * I am an alien and i have manipulated the system enough to cause problems.
                     * If you can detect those, you are saved or else,
                     * you will see something when you "try" to release the app which will definitely not serve your purpose
                     *
                     * Someone has said - "The problem is not the problem; the problem is your attitude about the problem."
                     */
//                    String Messages = dbhelper.getHighRiskConditionForRadio("" + m.getKeyword());
                    String Messages = questionInteractor.getHighRiskCondition("" + m.getKeyword());
                    String counsel_message = null;

                    /**
                     * This logic is used to check whether there is any High risk,Counselling or Diagnostic referral on button click
                     */

                    if (Messages != null && Messages.length() > 0) {
                        StoredHighRiskRanges = new ArrayList<>();

                        JSONObject highRisk_conditions = new JSONObject(Messages);
                        if (highRisk_conditions.optJSONArray("highrisk") != null) {
                            JSONArray highRisk_conditionsArray = highRisk_conditions.optJSONArray("highrisk");

                            //System.out.println("jsonArray3 options"+jsonArray3.length());
                            for (int k = 0; k < highRisk_conditionsArray.length(); k++) {
                                JSONObject main_ques_options_key = highRisk_conditionsArray.getJSONObject(0);
                                //System.out.println("main_ques_options_key High risk lang = " + main_ques_options_key.optString("languages").toString());

                                highrisklist.put(quesid, "" + formid + delimeter + setid + delimeter + keyword + delimeter + m.getKeyword() + delimeter + main_ques_options_key.optString("languages").toString() + delimeter + "highrisk" + delimeter + "0");

                            }
                        } else {
                            highrisklist.remove(quesid);
                        }

                        if (highRisk_conditions.optJSONArray("counselling") != null) {
                            JSONArray counseling_conditionsArray = highRisk_conditions.optJSONArray("counselling");
                            for (int k = 0; k < counseling_conditionsArray.length(); k++) {
                                JSONObject main_ques_options_key = counseling_conditionsArray.getJSONObject(0);
                                counsel_message = main_ques_options_key.optString("languages").toString();

                                if (main_ques_options_key.has("show_popup")) {
                                    JSONObject obj = new JSONObject(counsel_message);
                                    String language = obj.getString(mAppLanguage);
                                    if(!noDialogFirst) {
                                        criticalCounsellingMsg(language);
                                    }
                                }
                            }
                        }

                        if (highRisk_conditions.optJSONArray("diagnosticrefer") != null) {
                            JSONArray diagnosticrefer_conditionsArray = highRisk_conditions.optJSONArray("diagnosticrefer");
                            for (int k = 0; k < diagnosticrefer_conditionsArray.length(); k++) {
                                JSONObject diagnosticrefer_options_key = diagnosticrefer_conditionsArray.getJSONObject(0);
                                highrisklist.put(quesid, "" + formid + delimeter + setid + delimeter + keyword + delimeter + m.getKeyword() + delimeter + diagnosticrefer_options_key.optString("languages").toString() + delimeter + "diagnosticReffer" + delimeter + "0");
                                referrallist.put(quesid, diagnosticrefer_options_key.optString("languages").toString());
                            }
                        } else {
                            referrallist.remove(quesid);
                        }

                        if (highRisk_conditions.optJSONArray("patientVisitSummary") != null) {
                            JSONArray patientVisitSummary_conditionsArray = highRisk_conditions.optJSONArray("patientVisitSummary");
                            for (int k = 0; k < patientVisitSummary_conditionsArray.length(); k++) {
                                JSONObject patientVisitSummary_options_key = patientVisitSummary_conditionsArray.getJSONObject(0);
                                patientvisitlist.put(keyword, patientVisitSummary_options_key.optString("languages").toString());

                            }

                        } else {
//
                            patientvisitlist.remove(keyword);
                        }
                    } else {
                        highrisklist.remove(quesid);
                        referrallist.remove(quesid);
                        patientvisitlist.remove(keyword);
                    }

                    /**
                     * This if condition is used to check whether dependant question is present or not
                     * if dependantList size is 0 or null means question does'nt have any dependant question.
                     */
                    if (dependantList != null && dependantList.size() > 0) {

                        if (!dependantKeywordPresent.containsValue("" + m.getKeyword())) {

                            LinearLayout ll_4 = (LinearLayout) view.getParent().getParent().getParent();

                            removeDependent(ll_4, keyword);

                            dependantKeywordPresent.put(keyword, "" + m.getKeyword());

                            /**
                             * This if condition is used to check whether dependant layout is displayed or not
                             * this is used as a validation that if once the layout is displayed then again
                             * clicking on the same button twice the layout should be displayed only once
                             * for eg. if tt2yes is clicked for the first time then dependant layout should be
                             * displayed but again clicking on it dependant layout should not be displayed.
                             */

                            if (!(MainQuestempstoredependant.containsKey(keyword))) {
                                // this hashmap is used to store the layout id for
                                // the clicked button (for eg: tt2yes,android.widget.LinearLayout
                                // {21abf298 V.E...C. ......ID 40,646-560,769})

                                layoutids.put("" + m.getKeyword(), "" + ll_sub);

                                displayCounsellingForSpinner(counsel_message, view, keyword);

                                DisplayDependantQuestions(dependantList);

                            }

                        }

                    }

                    displayCounsellingMsg(counsel_message, view, keyword);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (pos == 0) {
                    LinearLayout ll_4 = (LinearLayout) view.getParent().getParent().getParent();

                    removeDependent(ll_4, keyword);

                    dependantKeywordPresent.remove(keyword);

                    womendetails.remove(keyword);
                    Backup_answerTyped1.remove(keyword);

                    questionInteractor.deleteAnswer(uniqueId, formid, keyword);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (womendetails.containsKey(keyword)) {
            JSONObject obj;
            String option = questionInteractor.getSelectedOptionText(womendetails.get(keyword));

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

        if (displayCondition != null && displayCondition.length() > 0 && previousVisitDetails.containsKey(keyword)) {
            JSONObject obj;
            String option = questionInteractor.getSelectedOptionText(previousVisitDetails.get(keyword));
            if (option != null) {

                try {
                    obj = new JSONObject(option);
                    Optionlanguage = obj.getString(mAppLanguage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                spinner.setSelection(arrayAdapter.getPosition(Optionlanguage));
            }

            spinner.setEnabled(false);

        }

//		spinner.setSelection(multipleoptionlist.indexOf());

        if (orientation == 1) {
            spinner.setLayoutParams(lpHorizontal);
        }

        ll.addView(spinner);

    }

    /**
     * THis method is used to create Checkbox field dynamically
     *
     * @param i               = for loop counter
     * @param quesid          = this field contains the question id
     * @param formid          = formid
     * @param setid           = setid of the question
     * @param language        = question text
     * @param keyword         = question keyword
     * @param validationfield = this field describes whether question is compulsory or not
     * @param messages        = = this field contains json with multiple highrisk,counselling,referral conditions
     * @return layout (ll)
     */


    public LinearLayout createCheckbox(int i, String quesid, final String formid, final String setid, String language, final String keyword, final String validationfield, String messages, String displayCondition, int scrollID, final int orientation) {

        //System.out.println("inside method textViewCount"+i+"   keyword"+keyword);

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
        tv.setText("" + language);
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

       /* if (i == 1) {
            optionList = dbhelper.dependantgetANCEnglishoptions(quesid);  // this db statement gets q.keyword,q.answer_type,qi.keyword,qi.dependants,qi.depend_lang_eng,qi.depend_lang_mara,qi.action for that specific quesid in english
        } else {
//            optionList = dbhelper.getANCEnglishoptions(quesid);
        }*/

        optionList = questionInteractor.getQuestionOptions(quesid, String.valueOf(FormID));


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
                            womendetails.remove(keyword);
                            Backup_answerTyped1.remove(keyword);

                            questionInteractor.deleteAnswer(uniqueId, formid, keyword);
                        }
                    }

                    womendetails.put(keyword, "" + chechboxlist);
                    view_check = keyword;

                    Backup_answerTyped1.put(keyword, ""+ chechboxlist); // this treemap is used to insert dataSource in Backup_entered table.

                    ll_sub = (LinearLayout) v.getParent().getParent();

                }
            });


            if (womendetails.containsKey(keyword)) {
                System.out.println("Retireve Checkbox list ****" + womendetails.get(keyword));
//
                chechboxlist = new ArrayList<String>();
                pattern = Pattern.compile("\\W");
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

            if (displayCondition != null && displayCondition.length() > 0 && previousVisitDetails.containsKey(keyword)) {
                pattern = Pattern.compile("\\W");
                String[] words = pattern.split(previousVisitDetails.get(keyword));
                System.out.println("words = " + words);
                for (String s : words) {
                    if (s.equalsIgnoreCase(cb.getTag().toString())) {
                        chechboxlist.add("" + cb.getTag());
                        cb.performClick();
                    }
                }
                validationlist.put(keyword, previousVisitDetails.get(keyword));
                cb.setEnabled(false);
            }
        }

        return ll;

    }

    /**
     * This method is used to display video questions and link dynamically
     *
     * @param i=        for loop counter
     * @param language= question text
     * @param Keyword=  question keyword
     * @return ll
     */
    public LinearLayout createVideo(int i, String language, String Keyword, int scrollID) {
        try {
            JSONObject obj = new JSONObject(language);
            language = obj.getString(mAppLanguage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView tv = new TextView(this);
        tv.setText("" + language);
        tv.setId(textId + 1);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.text_color));
        tv.setLayoutParams(lp);


        final Button b = new Button(this);
        b.setTag(Keyword);
        b.setBackgroundResource(R.drawable.video_player);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hashMapVideoNamePath.containsKey(b.getTag())) {
                    Intent target = new Intent(Intent.ACTION_VIEW);
                    target.setDataAndType(Uri.fromFile(new File(hashMapVideoNamePath.get(b.getTag()))), "video/*");
                    target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                    Intent intent = Intent.createChooser(target, getString(R.string.choose_video_player));
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), R.string.no_handler_for_type, Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(getApplicationContext(), R.string.file_not_exit_txt, Toast.LENGTH_LONG).show();
            }

        });

        ll.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout baseLayoutText = new LinearLayout(ctx);
        baseLayoutText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.90f));

        LinearLayout baseLayoutButon = new LinearLayout(ctx);
        baseLayoutButon.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.15f));


        baseLayoutText.addView(tv);
        baseLayoutButon.addView(b);
        ll.setTag(Keyword);
        ll.addView(baseLayoutText);
        ll.addView(baseLayoutButon);

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
     * @return layout (ll)
     */
    public LinearLayout createLabel(int i, String language, final String formid, final String keyword, final String validationfield, final String validationcondition, final String validationmsg, String displayCondition, int scrollID, String calculations, String setid) {

        System.out.println("inside createEdittext count" + i + "   keyword" + keyword);

        try {
            JSONObject obj = new JSONObject(language);
            language = obj.getString(mAppLanguage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (!calculations.equals("")) {
            labelKeywirdDetails.put(scroll.getId(), new Label(language, calculations));
        }

        TextView tv = new TextView(this);
        tv.setText("" + language);
//		tv.setId(textHhId + 1);
        // tv.setGravity(Gravity.CENTER);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.text_color));
        tv.setId(Integer.parseInt(setid));

//		tv.setLayoutParams(lp);
        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        //villageList=dbhelper.getVillageList();

        LinearLayout.LayoutParams lp_label = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Frame.getLayoutParams().height));
        lp_label.setMargins(10, 10, 10, 10);
        ll.setLayoutParams(lp_label);
        ll.setGravity(Gravity.CENTER);


        ll.setTag(keyword);
        ll.addView(tv);

        return ll;
    }

    public LinearLayout createSubLabel(int i, String language, final String formid, final String keyword, final String validationfield, final String validationcondition, final String validationmsg, String displayCondition, int scrollID) {

        System.out.println("inside createEdittext count" + i + "   keyword" + keyword);

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

//		tv.setLayoutParams(lp);
        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        //villageList=dbhelper.getVillageList();

		/*LinearLayout.LayoutParams lp_label=new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Frame.getLayoutParams().height));
        lp_label.setMargins(10, 10, 10, 10);
		ll.setLayoutParams(lp_label);
		ll.setGravity(Gravity.CENTER);*/


        ll.setTag(keyword);
        ll.addView(tv);

        return ll;
    }

    /**
     * This method is used to display dependant questions dynamically
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

                case "video":
                    ll=createVideo(1, qstnData.getQuestionText(), qstnData.getKeyword(),scroll.getId());
                    break;

                case "capturephoto":
                    //System.out.println("answer type..........");
                    //ll=(answerTypeCaptureImage(ll, keyword, qstnNumber, qstnLanguage, question_language));

                    break;

                case "radio":

                    ll = createdependantRadio(1, String.valueOf(qstnData.getParentQstnId()), qstnData.getQuestionText(), qstnData.getParentQstnKeyword(), qstnData.getFormid(), qstnData.getSetId(), qstnData.getKeyword(), qstnData.getValidationCondition(), qstnData.getMessages(), displayCondition, qstnData.getPageScrollId(), qstnData.getOrientation());
//					ll=createdependantRadio(1, qstnData.getParentQstnKeyword(), qstnData.getQuestionText(), qstnData.getParentQstnKeyword(), qstnData.getFormid(), qstnData.getSetid(), qstnData.getKeyword(),qstnData.getValidationCondition(),qstnData.getMessages(),displayCondition,qstnData.getPageScrollId());
                    break;
                case "select":
                    //ll=createCheckbox(1, text, keyword);
                    ll = createCheckbox(1, String.valueOf(qstnData.getParentQstnId()), qstnData.getFormid(), qstnData.getSetId(), qstnData.getQuestionText(), qstnData.getKeyword(), qstnData.getValidationCondition(), qstnData.getMessages(), displayCondition, Integer.parseInt(qstnData.getPageScrollId()), qstnData.getOrientation());
                    break;

                case "date":
                    ll = createdependentDate(1, qstnData.getQuestionText(), qstnData.getAnswerType(), qstnData.getParentQstnKeyword(), qstnData.getFormid(), qstnData.getSetId(), qstnData.getKeyword(), qstnData.getValidationCondition(), qstnData.getMessages(), displayCondition, qstnData.getPageScrollId(), qstnData.getOrientation());

                    break;

                case "label":
                    //ll=(answerTypeLabel(ll, keyword, qstnNumber, qstnLanguage, question_language));
                    //ll = createSubLabel(1, qstnData.getQuestionText(), qstnData.getFormid(),qstnData.getSetId(), qstnData.getKeyword(), "", "", "", "","");
                    break;

                case "int":
                    ll = createdependentInt(1, qstnData.getQuestionText(), qstnData.getAnswerType(), qstnData.getParentQstnKeyword(), qstnData.getFormid(), qstnData.getSetId(), qstnData.getKeyword(), qstnData.getValidationCondition(), qstnData.getMessages(), displayCondition, qstnData.getPageScrollId(), qstnData.getOrientation());
                    break;

                case "float":
                    ll=createdependentInt(1, qstnData.getQuestionText(), qstnData.getAnswerType(),qstnData.getParentQstnKeyword(),qstnData.getFormid(),qstnData.getSetId(),qstnData.getKeyword(),qstnData.getValidationCondition(),qstnData.getMessages(),displayCondition,qstnData.getPageScrollId(),qstnData.getOrientation());
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
     * @return ll
     */
    public LinearLayout createdependentEdittext(int i, String language, final String keyword, final String radiotag, final String formid, final String setid, final String dependantQuesKeyword, final String validationConditions, final String messages, String displayCondition, final String PageScrollID, final int orientation) {


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

        edittextID = edittextID + 1;
        final EditText et = new EditText(this);
        //et.setText("edittextNo"+i);
        et.setMinLines(1);
        et.setMaxLines(3);
        //et.setHint("edittextNo" + i);
        et.setId(edittextID);
        et.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_edittext));
        et.setSingleLine(true);
        et.setLongClickable(false);
//        et.setPadding(20, 20, 20, 20);
        et.setTag(dependantQuesKeyword);

        Boolean isCompulsory = false;

        final int ScrollPageId = Integer.parseInt(PageScrollID);

        try {
            JSONObject validationobj = new JSONObject(validationConditions);

            if (validationobj.optString("required") != null && validationobj.optString("required").length() > 0) {

                isCompulsory = true;
                //compulsory=true;
                tv.setError("");
                validationlist.put("" + et.getTag(), "");
                //NextButtonvalidationlist.put(""+et.getTag(),runtimevalidationlist.get(et.getId()));
                NextButtonvalidationlist.put("" + et.getTag(), ScrollPageId);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (womendetails.containsKey(dependantQuesKeyword)) {
            et.setText(womendetails.get(dependantQuesKeyword));

            validationlist.put(dependantQuesKeyword, womendetails.get(dependantQuesKeyword));
            tv.setError(null);

        }

        et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                System.out.println("createdependentEdittext validationConditions = " + validation_Conditions);
                System.out.println("createdependentEdittext messages = " + messages);

                return false;
            }
        });


        final Boolean finalIsCompulsory = isCompulsory;
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
                Backup_answerTyped1.put(dependantQuesKeyword, et.getText().toString().trim());

                if (et.getText().toString().trim().equals("")) {
                    if (finalIsCompulsory) {
                        tv.setError("");
                        validationlist.remove(dependantQuesKeyword);
                        NextButtonvalidationlist.remove(dependantQuesKeyword);
                        womendetails.remove(dependantQuesKeyword);
                        Backup_answerTyped1.remove(dependantQuesKeyword);

                        questionInteractor.deleteAnswer(uniqueId, formid, keyword);
                    } else {
                        womendetails.remove(dependantQuesKeyword);
                        Backup_answerTyped1.remove(dependantQuesKeyword);

                        questionInteractor.deleteAnswer(uniqueId, formid, keyword);
                    }

                } else {
                    validationlist.put("" + et.getTag(), et.getText().toString().trim());
                    tv.setError(null);
                }

            }
        });

        if (displayCondition != null && displayCondition.length() > 0 && previousVisitDetails.containsKey(dependantQuesKeyword)) {
            et.setText(previousVisitDetails.get(dependantQuesKeyword));
            et.setEnabled(false);

            womendetails.put(dependantQuesKeyword, et.getText().toString().trim());
            Backup_answerTyped1.put(dependantQuesKeyword, et.getText().toString().trim());

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
     * @return ll
     */
    public LinearLayout createdependentDate(int i, String language, final String keyword, final String radiotag, final String formid, final String setid, final String dependantQuesKeyword, final String validationConditions, final String messages, String displayCondition, final String PageScrollID, final int orientation) {

        System.out.println("inside method count**" + i + "*** keyword**" + keyword + "***radiotag***" + radiotag + "***dependantQuesKeyword**" + dependantQuesKeyword + "***validationConditions***" + validationConditions);

        try {
            JSONObject obj = new JSONObject(language);
            language = obj.getString(mAppLanguage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String validation_Conditions;

        validation_Conditions = validationConditions;

        final int ScrollPageId = Integer.parseInt(PageScrollID);

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
        //et.setText("edittextNo"+i);
        et.setMinLines(1);
        et.setMaxLines(3);
        //et.setHint(defaultdate);
        et.setId(edittextID);
        et.setLongClickable(false);
        et.setSingleLine(true);
        et.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_edittext));
        et.setFocusable(false);
        et.setClickable(true);
        et.setTag(dependantQuesKeyword);
        //System.out.println("createDate"+et.getId());

        Date date = new Date();

        //runtimevalidationlist.put(et.getId(), scroll.getId());
        boolean required = false;
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

        if (womendetails.containsKey(dependantQuesKeyword)) {
            try {
                Date dt = serverdateFormatter.parse(womendetails.get(dependantQuesKeyword));
                et.setText(formatter.format(dt));
                tv.setError(null);
                validationlist.put(dependantQuesKeyword, womendetails.get(dependantQuesKeyword));
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

        if (displayCondition != null && displayCondition.length() > 0 && previousVisitDetails.containsKey(dependantQuesKeyword)) {
            try {
                Date dt = serverdateFormatter.parse(previousVisitDetails.get(dependantQuesKeyword));
                et.setText(formatter.format(dt));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            et.setEnabled(false);

            womendetails.put(dependantQuesKeyword, previousVisitDetails.get(dependantQuesKeyword));
            Backup_answerTyped1.put(dependantQuesKeyword, previousVisitDetails.get(dependantQuesKeyword));

        }

        final boolean finalRequired = required;
        et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if ((et.getCompoundDrawables()[DRAWABLE_RIGHT] != null) && (motionEvent.getRawX() >= (et.getRight() - et.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()))) {
                            // your action here
                            et.setText("");
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

                Calendar newCalendar = Calendar.getInstance();
                fromDatePickerDialog = new DatePickerDialog(ctx, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        //System.out.println("newDate.getTime()" + newDate.getTime());

                        //System.out.println("newDate.getTime()******"+dateFormatter1.format(newDate.getTime()));
                        et.setText(dateFormatter.format(newDate.getTime()));
                        if (!finalRequired)
                            et.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear, 0);
                        serverDate = serverdateFormatter.format(newDate.getTime());
                        et.setFocusable(false);
                        String getDate = dateFormatter.format(newDate.getTime());

                        LinearLayout parent = ((LinearLayout) (et.getParent()).getParent());

                        Log.d(TAG, "onDateSet: Child Count " + parent.getChildCount());

                        if (et.getTag().equals("mention_tt_1_given_date")) {
                            for (int i = 0; i < parent.getChildCount(); i++) {
                                if (parent.getChildAt(i) instanceof LinearLayout) {
                                    LinearLayout childLinearlayout = (LinearLayout) parent.getChildAt(i);
                                    Log.d(TAG, "onDateSet: " + childLinearlayout.getTag());
                                    if (childLinearlayout.getTag().equals("tt_2_dose_given")) {
                                        showTT2DoseQuestion(parent, childLinearlayout, getDate, ScrollPageId);
                                    }
                                }
                            }
                        }

                        try {
//                            System.out.println("getDate" + getDate);
                            selectedDate = formatter.parse(getDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Boolean dateOfBirthResult= true;
                        String dateErrorMsg=getApplicationContext().getString(R.string.exceed_curnt_dt);

                        if (SystemDate.compareTo(selectedDate) >= 0 && dateOfBirthResult) {
                            String myFormat = "yyyy-MM-dd"; //In which you need put here
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                            et.setError(null);
                            System.out.println("dependantQuesKeyword = " + dependantQuesKeyword);
                            storeEnteredData.put(dependantQuesKeyword, sdf.format(newDate.getTime()).toString());
                            System.out.println("storeEnteredData = " + storeEnteredData.get(dependantQuesKeyword));
                            validationlist.remove("" + et.getTag());
                            NextButtonvalidationlist.remove("" + et.getTag());
                            tv.setError(null);
                        } else {
                            et.setError(dateErrorMsg);
                            Toast.makeText(getApplicationContext(), dateErrorMsg, Toast.LENGTH_LONG).show();
                            validationlist.put("" + et.getTag(), "");
                            NextButtonvalidationlist.put("" + et.getTag(), ScrollPageId);
                        }

                        womendetails.put(dependantQuesKeyword, serverDate);
                        Backup_answerTyped1.put(dependantQuesKeyword, serverDate);

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

    public void showTT2DoseQuestion(LinearLayout parent, LinearLayout childLinearlayout, String selectedDate, int ScrollPageId) {

        LocalDate currentDate = LocalDate.now();

        LocalDate todayMinusOneMonth = currentDate.minusMonths(1);

        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");

        LocalDate dt = dtf.parseLocalDate(selectedDate);

        Log.d(TAG, "onDateSet: todayMinusOneMonth " + todayMinusOneMonth);

        Log.d(TAG, "onDateSet: entered date in LocalDate object " + dt);

        Log.d(TAG, "onDateSet: compare " + dt.isBefore(todayMinusOneMonth));

        if (dt.isBefore(todayMinusOneMonth)) {
            if (!childLinearlayout.isShown()) {
                childLinearlayout.setVisibility(View.VISIBLE);
                RadioGroup radioGroup = (RadioGroup) childLinearlayout.getChildAt(1);
                radioGroup.clearCheck();
                radioGroup.clearFocus();
                radioGroup.setSelected(false);
                // this list is used to get dependant question in english format (parameter"s received are form_id,keyword,answer_type,english_lang)

                if (dependantKeywordPresent.containsKey("tt_2_dose_given")) {
                    dependantList = questionInteractor.getDependantQuesList("tt_2_given_yes", formid, ll_sub, "tt_2_dose_given", "" + scroll_temp.getId());
                    DisplayDependantQuestions(dependantList);
                }
                Log.d(TAG, "showTT2DoseQuestion: dependantKeywordPresent " + dependantKeywordPresent.containsKey("tt_2_dose_given"));
                validationlist.put(childLinearlayout.getTag().toString(), "");
                NextButtonvalidationlist.put(childLinearlayout.getTag().toString(), ScrollPageId);
                TextView textView = (TextView) childLinearlayout.getChildAt(0);
                textView.setError("");
            }
        } else {
            if (childLinearlayout.isShown()) {
                childLinearlayout.setVisibility(View.GONE);
                removeDependent(parent, String.valueOf(childLinearlayout.getTag()));
                if (dependantKeywordPresent.containsKey("tt_2_dose_given"))
                    dependantKeywordPresent.remove("tt_2_dose_given");
                validationlist.remove(childLinearlayout.getTag());
                NextButtonvalidationlist.remove(childLinearlayout.getTag());
                Log.d(TAG, "showTT2DoseQuestion: Child Count " + parent.getChildCount());

                if (counclingMsgQstnKeywords.contains("tt_2_dose_given")) {
                    Log.d(TAG, "showTT2DoseQuestion: contains ");
                    LinearLayout linearLayout = (LinearLayout) parent.findViewWithTag("tt_2_dose_given");
                    Log.d(TAG, "showTT2DoseQuestion: TAG " + linearLayout.getTag());
                    Log.d(TAG, "showTT2DoseQuestion: Child Index " + parent.indexOfChild(linearLayout));
                    if (linearLayout.isShown()) {
                        Log.d(TAG, "showTT2DoseQuestion: shown");
                    }
                    parent.removeViewAt(3);
                    counclingMsgQstnKeywords.remove("tt_2_dose_given");
                }

                for (int i = 0; i < counclingMsgQstnKeywords.size(); i++) {
                    Log.d(TAG, "if dataSource : " + counclingMsgQstnKeywords.get(i));
                }

            }

        }

    }

    public LinearLayout createdependentTime(int i, String language, final String keyword, final String radiotag, final String formid, final String setid, final String dependantQuesKeyword, final String validationConditions, final String messages, final String PageScrollID, final int orientation) {

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
        //et.setText("edittextNo"+i);
        et.setMinLines(1);
        et.setMaxLines(3);
        //et.setHint(defaultdate);
        et.setId(edittextID);
        et.setFocusable(false);
        et.setClickable(true);
        et.setLongClickable(false);
        et.setSingleLine(true);
        et.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_edittext));
        et.setTag(dependantQuesKeyword);
        //System.out.println("createDate"+et.getId());

        runtimevalidationlist.put(et.getId(), Integer.parseInt(PageScrollID));


        final int ScrollPageId = Integer.parseInt(PageScrollID);


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


        if (womendetails.containsKey(dependantQuesKeyword)) {
            et.setText(womendetails.get(dependantQuesKeyword));

            validationlist.put("" + et.getTag(), womendetails.get(dependantQuesKeyword));
            tv.setError(null);

        }

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
                mTimePicker = new TimePickerDialog(AncVisits.this, new TimePickerDialog.OnTimeSetListener() {
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
                Backup_answerTyped1.put(keyword, et.getText().toString());

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
     * THis method is used to create dependant int dynamically
     *
     * @param i                    = loop id
     * @param language             = dependant question text
     * @param answerType              = main question keyword
     * @param radiotag             = radio tag on which the question is dependant
     * @param formid               = form id current used
     * @param setid                = setid of the question
     * @param dependantQuesKeyword = dependant question keyword
     * @param validationConditions = validations if present on dependant question
     * @param messages             = this field contains json with multiple highrisk,counselling,referral conditions
     * @return ll
     */
    public LinearLayout createdependentInt(int i, String language, final String answerType, final String radiotag, final String formid, final String setid, final String dependantQuesKeyword, final String validationConditions, final String messages, String displayCondition, final String PageScrollID, final int orientation) {

        System.out.println("inside method count createdependentInt **" + i + "*** keyword**" + answerType + "***radiotag***" + radiotag + "***dependantQuesKeyword**" + dependantQuesKeyword + "***validationConditions***" + validationConditions + "*** pageScrollID***" + PageScrollID);

        try {
            JSONObject obj = new JSONObject(language);
            language = obj.getString(mAppLanguage);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String validation_Conditions;


        Boolean isCompulsory = false;

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
        et.setTag(dependantQuesKeyword);
        //et.setText("edittextNo"+i);
        et.setMinLines(1);
        et.setMaxLines(3);
        //et.setHint("edittextNo" + i);
        et.setId(edittextID);
        et.setLongClickable(false);
        et.setSingleLine(true);
        et.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_edittext));
        //System.out.println("createDate"+et.getId());
        et.setInputType(InputType.TYPE_CLASS_NUMBER);

        if (orientation == 1) {
            ll.setOrientation(LinearLayout.HORIZONTAL);
            tv.setLayoutParams(lpHorizontal);
            et.setLayoutParams(lpHorizontal);
        }

        switch (answerType)
        {
            case "int" :
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;

            default:
                et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                et.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,2)});
                break;
        }


        ll.addView(tv);
        ll.addView(et);

        final int ScrollPageId = Integer.parseInt(PageScrollID);



        try {
            JSONObject validationobj = new JSONObject(validationConditions);

            if (validationobj.optString("required") != null && validationobj.optString("required").length() > 0) {

                if (validationobj.optString("languages") != null && validationobj.optString("languages").length() > 0) {
                    et.setError(validationobj.optString("languages"));
                } else {
                    et.setError(AncVisits.this.getString(R.string.default_validation_msg));
                }

                tv.setError("");
                isCompulsory = true;

                validationlist.put("" + et.getTag(), "");
                //NextButtonvalidationlist.put(""+et.getTag(),runtimevalidationlist.get(et.getId()));
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


            //messages = ConditionLists.get(keyword);

            StoredHighRiskRanges = new ArrayList<>();
            StoredCounsellingRanges = new ArrayList<>();
            StoredPatientVisitSummaryRanges = new ArrayList<>();


            StorePVSmsgs(messages);
            StorePatientVisitHighRiskDiagnosticValues(womendetails.get(dependantQuesKeyword), et, dependantQuesKeyword, setid);
        }

        if (displayCondition != null && displayCondition.length() > 0 && previousVisitDetails.containsKey(dependantQuesKeyword)) {
            et.setText(previousVisitDetails.get(dependantQuesKeyword));
            et.setEnabled(false);

            womendetails.put(dependantQuesKeyword, et.getText().toString());
            Backup_answerTyped1.put(dependantQuesKeyword, et.getText().toString());
            validationlist.put("" + et.getTag(), previousVisitDetails.get(dependantQuesKeyword));

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

        final Boolean finalIsCompulsory = isCompulsory;

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


                    if (validation_Conditions != null && validation_Conditions.length() > 0) {

//						if (et.getText().toString().equals("")) {
//							et.setError(null);
//							validationlist.remove("" + et.getTag());
//							NextButtonvalidationlist.remove("" + et.getTag());
//						}

                        if (et.getText().toString() != null && et.getText().length() > 0) {


//							if ((length_min < et.getText().toString().length()) && (et.getText().toString().length() <= length_max)) {
//								et.setError(null);
//								validationlist.put("" + et.getTag(), et.getText().toString());
//								NextButtonvalidationlist.put("" + et.getTag(), ScrollPageId);
//
//							} else {
////
//								et.setError(length_lang);
//								//next.setEnabled(false);
//
//								validationlist.put("" + et.getTag(), "");
//								NextButtonvalidationlist.put("" + et.getTag(), ScrollPageId);
//							}


                            if (range_max > 0) {

                                if ((range_min <= Double.parseDouble(et.getText().toString())) && (Double.parseDouble(et.getText().toString()) <= range_max)) {
                                    et.setError(null);
                                    tv.setError(null);
                                    //next.setEnabled(true);

                                    validationlist.put("" + et.getTag(), et.getText().toString());
//						NextButtonvalidationlist.put(et.getId(), scroll.getId());

                                } else {

                                    et.setError(range_lang);
                                    tv.setError("");
                                    validationlist.put("" + et.getTag(), "");
                                    NextButtonvalidationlist.put("" + et.getTag(), ScrollPageId);
                                }
                            }

                        }
                    }
                    StorePVSmsgs(messages);
                    StorePatientVisitHighRiskDiagnosticValues(et.getText().toString(), et, dependantQuesKeyword, setid);


                    womendetails.put(dependantQuesKeyword, et.getText().toString());
                    Backup_answerTyped1.put(dependantQuesKeyword, et.getText().toString());


                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (et.getText().toString().equals("")) {
                    if (finalIsCompulsory) {
                        tv.setError("");
                        validationlist.put("" + et.getTag(), "");
                        NextButtonvalidationlist.put("" + et.getTag(), ScrollPageId);
                        womendetails.remove(dependantQuesKeyword);
                        Backup_answerTyped1.remove(dependantQuesKeyword);

                    } else {
                        tv.setError(null);
                        et.setError(null);
                        validationlist.remove(dependantQuesKeyword);
                        NextButtonvalidationlist.remove(dependantQuesKeyword);
                        womendetails.remove(dependantQuesKeyword);
                        Backup_answerTyped1.remove(dependantQuesKeyword);
                    }

                }

                if (!et.getText().toString().equals("")) {

                    if (et.getTag().equals("infant_alive")) {

                        String outcomeKeyword = womendetails.get("number_of_children_born");
                        int outcome = Integer.parseInt(outcomeKeyword.substring(outcomeKeyword.length() - 1));
                        int enteredValue = Integer.parseInt(String.valueOf(et.getText()));
                        if (enteredValue <= outcome) {
                            tv.setError(null);
                            et.setError(null);
                            validationlist.remove(dependantQuesKeyword);
                            NextButtonvalidationlist.remove(dependantQuesKeyword);
                        } else {
                            tv.setError("");
                            validationlist.put("" + et.getTag(), "");
                            NextButtonvalidationlist.put("" + et.getTag(), ScrollPageId);
                            womendetails.remove(dependantQuesKeyword);
                            Backup_answerTyped1.remove(dependantQuesKeyword);
                        }
                    } else if (et.getTag().equals("infant_breastfeed") && womendetails.containsKey("infant_alive")) {
                        if (!womendetails.get("infant_alive").equals("")) {
                            int outcome = Integer.parseInt(womendetails.get("infant_alive"));
                            int enteredValue = Integer.parseInt(String.valueOf(et.getText()));
                            if (enteredValue <= outcome) {
                                tv.setError(null);
                                et.setError(null);
                                validationlist.remove(dependantQuesKeyword);
                                NextButtonvalidationlist.remove(dependantQuesKeyword);
                            } else {
                                tv.setError("");
                                validationlist.put("" + et.getTag(), "");
                                NextButtonvalidationlist.put("" + et.getTag(), ScrollPageId);
                                womendetails.remove(dependantQuesKeyword);
                                Backup_answerTyped1.remove(dependantQuesKeyword);
                            }
                        }
                    }
                }

                switch ("" + et.getTag()) {
                    case "weight_of_women":
                    case "height_of_women":
                    case "height_in_feet":

                        if (womendetails.containsKey("weight_of_women") && womendetails.containsKey("height_of_women")) {
                            womanWeight = Double.parseDouble(womendetails.get("weight_of_women"));
                            womanHeight = Double.parseDouble(womendetails.get("height_of_women"));
                            bmiCalculation(true, setid);
                        } else if (womendetails.containsKey("weight_of_women") && womendetails.containsKey("height_in_feet")) {
                            womanWeight = Double.parseDouble(womendetails.get("weight_of_women"));
                            Double heightInFeet = Double.parseDouble(womendetails.get("height_in_feet"));
                            Double heightInInch = heightInFeet * 12;
                            womanHeight = heightInInch * 2.54;
                            bmiCalculation(true, setid);
                        } else {
                            bmiCalculation(false, setid);
                        }

                        break;

                    default:
                        break;
                }

            }


        });

        return ll;
    }

    /**
     * THis method is used to create dependant date dynamically
     *
     * @param i                    = loop id
     * @param quesid               = question id
     * @param language             = dependant question text
     * @param keyword              = main question keyword
     * @param formid               = form id current used
     * @param setid                = setid of the question
     * @param dependantQuesKeyword = dependant question keyword
     * @param validationConditions = validations if present on dependant question
     * @param messages             = this field contains json with multiple highrisk,counselling,referral conditions
     * @return ll
     */

    public LinearLayout createdependantRadio(int i, final String quesid, String language, final String keyword, final String formid, final String setid, final String dependantQuesKeyword, final String validationConditions, final String messages, String displayCondition, final String PageScrollID, final int orientation) {

        System.out.println("inside createdependantRadio method count**" + i + "*** keyword**" + keyword + "***dependantQuesKeyword**" + dependantQuesKeyword + "***validationConditions***" + validationConditions + "****messages***" + messages);

//		final Integer includechild=Integer.parseInt(quesid);
        boolean isCompulsory = false;

        try {
            JSONObject obj = new JSONObject(language);
            Optionlanguage = obj.getString(mAppLanguage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final TextView tv = new TextView(this);
        tv.setText("" + Optionlanguage);
        tv.setId(textId + 1);
        // tv.setGravity(Gravity.CENTER);
        tv.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
        tv.setTextColor(getResources().getColor(R.color.text_color));
        //tv.setError("compulsory");
        tv.setLayoutParams(lp);

        final int ScrollPageId = Integer.parseInt(PageScrollID);

        //radio=radio+1;
//        dependantoptionList = dbhelper.dependantgetANCEnglishoptions(quesid);
        dependantoptionList = questionInteractor.getQuestionOptions(quesid, String.valueOf(FormID));
//		if(mAppLanguage.equalsIgnoreCase("en") || mAppLanguage.equalsIgnoreCase(""))
//		{
//			System.out.println("dependant createRadio with quesid**"+quesid);
//		}else
//		{
//			dependantoptionList=dbhelper.dependantgetANCEnglishoptions(quesid);
//		}


        JSONObject validationJsonObject = null;

        try {
            if (validationConditions != null && validationConditions.length() > 0) {
                validationJsonObject = new JSONObject(validationConditions);
                System.out.println("validationJsonObject = " + validationJsonObject);

                if (validationJsonObject.optString("required") != null && validationJsonObject.optString("required").length() > 0) {
                    tv.setError("");
                    validationlist.put(dependantQuesKeyword, "");
                    NextButtonvalidationlist.put(dependantQuesKeyword, ScrollPageId);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //final RadioButton[] rb = new RadioButton[2];
        final RadioGroup rg = new RadioGroup(this); //create the RadioGroup
//        rg.setPadding(20, 10, 20, 10);
        rg.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
        radiogroup = radiogroup + 1;
        rg.setId(radiogroup);
        runtimevalidationlist.put(rg.getId(), ScrollPageId);
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


        System.out.println("dependant createRadio optionList**" + dependantoptionList.size());

        if (dependantoptionList.size() < 4) {
            ll.addView(rg);

            for (int k = 1; k < dependantoptionList.size(); k++) {
                radio = radio + 1;

                try {
                    if (dependantoptionList.get(k).getQuestionText() != null && dependantoptionList.get(k).getQuestionText().length() > 0) {
                        JSONObject obj = new JSONObject(dependantoptionList.get(k).getQuestionText());
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
                rb.setTag(dependantoptionList.get(k).getKeyword());
                dependantquestion.put(dependantoptionList.get(k).getKeyword(), dependantoptionList.get(k).getQuesid());
                rb.setId(radio);
                rb.setPadding(20, 10, 20, 10);
                rg.addView(rb);

                if (orientation == 0) rb.setLayoutParams(lparams);
                //rb.setFocusableInTouchMode(true);
                //rg.setGravity(Gravity.CENTER);

                System.out.println("createdependantRadio Optionlanguage = " + Optionlanguage);

                rb.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        hideSoftKeyboard(v);
                        int id = rg.getCheckedRadioButtonId();
                        int parentId = (((ViewGroup) rb.getParent()).getId());
                        List<Visit> dependantList = null;


                        if (FormID == 32) {
                            String tag = rb.getTag().toString();

                            switch (tag) {
                                case "is_iucd_remove_yes":
                                    isIucdRemoved = true;
                                    break;

                                case "is_iucd_remove_no":
                                    isIucdRemoved = false;
                                    break;

                                case "miss_lmp_yes":
                                case "miss_lmp_tubo_vas_yes":
                                    isParticipantMissLmp = true;
                                    break;

                                case "miss_lmp_no":
                                case "miss_lmp_tubo_vas_no":
                                    isParticipantMissLmp = false;
                                    break;

                                case "is_women_pregnant_yes":
                                case "is_women_pregnant_for_Tubo_vase_yes":
                                    isParticipantPregnant = true;
                                    break;

                                case "is_women_pregnant_no":
                                case "is_women_pregnant_for_Tubo_vase_no":
                                    isParticipantPregnant = false;
                                    break;

                                case "ec_infertile_yes":
                                    isParticipantInfertile = true;
                                    break;

                                case "ec_infertile_no":
                                    isParticipantInfertile = false;
                                    break;

                                default:
                                    break;
                            }
                        }

                        rb.setChecked(true);
                        womendetails.put(dependantQuesKeyword, "" + rb.getTag());
                        Backup_answerTyped1.put(dependantQuesKeyword, "" + rb.getTag());


                        if (validationlist.containsKey(dependantQuesKeyword)) {
                            tv.setError(null);
                            validationlist.put(dependantQuesKeyword, rb.getTag().toString());
                        }

//                        String Messages = dbhelper.getHighRiskConditionForRadio("" + rb.getTag());
                        String Messages = questionInteractor.getHighRiskCondition("" + rb.getTag());

                        System.out.println("Messages = " + Messages);

                        String counsel_message = null;
                        try {
                            if (Messages != null && Messages.length() > 0) {
                                StoredHighRiskRanges = new ArrayList<>();

                                JSONObject highRisk_conditions = new JSONObject(Messages);
                                if (highRisk_conditions.optJSONArray("highrisk") != null) {
                                    JSONArray highRisk_conditionsArray = highRisk_conditions.optJSONArray("highrisk");

                                    //System.out.println("jsonArray3 options"+jsonArray3.length());
                                    for (int k = 0; k < highRisk_conditionsArray.length(); k++) {
                                        JSONObject main_ques_options_key = highRisk_conditionsArray.getJSONObject(0);
                                        //System.out.println("main_ques_options_key High risk lang = " + main_ques_options_key.optString("languages").toString());;


                                        highrisklist.put(quesid, "" + formid + delimeter + setid + delimeter + keyword + delimeter + rb.getTag() + delimeter + main_ques_options_key.optString("languages").toString() + delimeter + "highrisk" + delimeter + "0");
                                        //System.out.println("highrisklist = " + highrisklist);
                                    }
                                }
                                //System.out.println("highrisklist.size() = " + highrisklist.size());
                                //System.out.println("highRisk_conditions.optJSONArray(\"counselling\") = " + highRisk_conditions.optJSONArray("counselling"));

                                if (highRisk_conditions.optJSONArray("counselling") != null) {
                                    JSONArray counseling_conditionsArray = highRisk_conditions.optJSONArray("counselling");
                                    for (int k = 0; k < counseling_conditionsArray.length(); k++) {
                                        JSONObject main_ques_options_key = counseling_conditionsArray.getJSONObject(0);
                                        //System.out.println("main_ques_options_key Counseling lang = " + main_ques_options_key.optString("languages").toString());;
                                        counsel_message = main_ques_options_key.optString("languages").toString();

                                        if (main_ques_options_key.has("show_popup")) {
                                            JSONObject obj = new JSONObject(counsel_message);
                                            String language = obj.getString(mAppLanguage);
                                            if(!noDialogFirst) {
                                                criticalCounsellingMsg(language);
                                            }
                                        }
                                    }
                                }

                                if (highRisk_conditions.optJSONArray("diagnosticrefer") != null) {
                                    JSONArray diagnosticrefer_conditionsArray = highRisk_conditions.optJSONArray("diagnosticrefer");
                                    for (int k = 0; k < diagnosticrefer_conditionsArray.length(); k++) {
                                        JSONObject diagnosticrefer_options_key = diagnosticrefer_conditionsArray.getJSONObject(0);
                                        //System.out.println("main_ques_options_key Counseling lang = " + diagnosticrefer_options_key.optString("languages").toString());;
                                        highrisklist.put(quesid, "" + formid + delimeter + setid + delimeter + keyword + delimeter + rb.getTag() + delimeter + diagnosticrefer_options_key.optString("languages").toString() + delimeter + "diagnosticReffer" + delimeter + "0");
                                        referrallist.put(quesid, diagnosticrefer_options_key.optString("languages").toString());
                                        //System.out.println("diagnosticrefer highrisklist = " + highrisklist);
                                    }
                                }


                                if (highRisk_conditions.optJSONArray("patientVisitSummary") != null) {
                                    JSONArray patientVisitSummary_conditionsArray = highRisk_conditions.optJSONArray("patientVisitSummary");
                                    for (int k = 0; k < patientVisitSummary_conditionsArray.length(); k++) {
                                        JSONObject patientVisitSummary_options_key = patientVisitSummary_conditionsArray.getJSONObject(0);
                                        //System.out.println("patientVisitSummary_options_key Counseling lang = " + patientVisitSummary_options_key.optString("languages").toString());;
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


                        displayCounsellingMsg(counsel_message, v, dependantQuesKeyword);


                        ll_sub = (LinearLayout) v.getParent().getParent();

                        dependantList = questionInteractor.getDependantQuesList("" + rb.getTag(), formid, ll_sub, dependantQuesKeyword, "" + scroll_temp.getId());


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

                            System.out.println("ca.. dependantQuesKeyword = " + dependantQuesKeyword + " map..." + MainQuestempstoredependant);

                            LinearLayout ll_4 = (LinearLayout) v.getParent().getParent().getParent();
                            removeDependent(ll_4, dependantQuesKeyword);

                            dependantKeywordPresent.remove(dependantQuesKeyword);
                        }
                    }
                });


            }

            System.out.println("dependantQuesKeyword before if = " + dependantQuesKeyword);
            if (womendetails.containsKey(dependantQuesKeyword)) {
                for (int j = 0; j < rg.getChildCount(); j++) {
                    RadioButton rBtn = (RadioButton) rg.getChildAt(j);

                    if (rBtn.getTag().equals(womendetails.get(dependantQuesKeyword))) {
                        System.out.println("key = " + dependantQuesKeyword + " tag = " + rBtn.getTag());

                        rBtn.setChecked(true);
                        rBtn.performClick();
                        break;
                    }
                }

            }

        } else if (dependantoptionList.size() == 4) {
            createButton(quesid, dependantQuesKeyword, setid, rg.getId(), tv, formid, dependantoptionList, displayCondition, "" + ScrollPageId, isCompulsory, orientation);
        } else {
            multipleRadioButton(quesid, dependantQuesKeyword, setid, rg.getId(), tv, formid, dependantoptionList, displayCondition, "" + ScrollPageId, isCompulsory, orientation);
        }

        return ll;
    }

    /**
     * THis method is used to create dependant label dynamically
     *
     * @param i        = loop id
     * @param language = dependant question text
     * @param keyword  = main question keyword
     * @return ll
     */
    public LinearLayout createCounsellingLabel(int i, String language, final String keyword, final String PageScrollID) {

        //System.out.println("inside createEdittext textViewCount" + i + "   keyword" + keyword);

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

        final int ScrollPageId = Integer.parseInt(PageScrollID);

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
     * This method is used to save the answer's of the woman in local DB.
     */
    public void saveForm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder
                .setTitle(AncVisits.this.getString(R.string.save_form))
                .setMessage(AncVisits.this.getString(R.string.save_form_message))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(AncVisits.this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();


                        SaveFormStatus = true;

                        if (FormID <= DELIVERY_FORM_ID) {
                            StoreANCForm();
                        }  else if (FormID == CHILD_CLOSURE_FORM_ID) {
                            StoreClosureForm("Child");
                        } else if (FormID == WOMAN_CLOSURE_FORM_ID) {
                            StoreClosureForm("Mother");
                        } else if (FormID == CC_FIRST_VISIT_ID) {
                            if(regOption.equals("direct_reg_child")){
                                if(FormID==clickedFormId){
                            StoreChildCareRegForm();
                                } else{
                                    StoreChildCareRegForm();
                                Intent intent=new Intent(AncVisits.this,AncVisits.class);
                                    intent.putExtra(clickedForm,clickedFormId);
                                intent.putExtra(UNIQUE_ID,uniqueId);
                                intent.putExtra(FORM_ID,formid);
                                startActivity(intent);
                            }
                            }
                            else{StoreChildCareRegForm();
                            }
                        } else if (FormID > CC_FIRST_VISIT_ID) {
                            if(regOption.equals("direct_reg_child")){
                                storeChildCareForm();
                               if(FormID<clickedFormId) {
                                   Intent intent = new Intent(AncVisits.this, AncVisits.class);
                                   intent.putExtra(UNIQUE_ID, uniqueId);
                                   intent.putExtra(FORM_ID, formid);
                                   intent.putExtra(clickedForm,clickedFormId);
                                    startActivity(intent);
                               }if(FormID==clickedFormId){
                                    storeChildCareForm();
                                }
                        }else{
                                storeChildCareForm();
                            }}

                    }
                })
                .setNegativeButton(AncVisits.this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();

                    }
                })

                .show();
    }

    /**
     * This dialog is used to display the next visit date of the ANM
     *
     * @param Next_Visit_name = which is the next vist
     * @param Next_Visit_date = next visit date
     * @param Nextvisit       = Name of the next visit
     */
    // TODO change next visit logic - ArogyaSakhi
    public void NextVisit_dialog(String Next_Visit_name, String Next_Visit_date, String Nextvisit) throws JSONException {
        if (Nextvisit != null && Nextvisit.length() > 0 && Nextvisit.equalsIgnoreCase("NextVisit")) {

            if (Next_Visit_name.contains("en")) {
                JSONObject visitNameObject = new JSONObject(Next_Visit_name);
                Next_Visit_name = visitNameObject.getString(mAppLanguage);
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(AncVisits.this.getString(R.string.anc_next_visit))
                    .setMessage(AncVisits.this.getString(R.string.nextvisit_text_part1) + Next_Visit_name + AncVisits.this.getString(R.string.nextvisit_text_part2) + Next_Visit_date + AncVisits.this.getString(R.string.nextvisit_text_part3))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(AncVisits.this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //finish();
                            Toast.makeText(getApplicationContext(), AncVisits.this.getString(R.string.Toast_msg_for_formsavesuccessfully), Toast.LENGTH_LONG).show();

                            finish();

                        }
                    })
                    //Do nothing on no
                    .show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder
                    .setTitle(R.string.all_anc_visits_comp_title)
                    .setMessage(R.string.all_anc_visits_comp_msg)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(AncVisits.this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            finish();

                        }
                    })
                    //Do nothing on no
                    .show();
        }

    }

    /**
     * This method is used to display patient visit summary dialog with its list
     *
     * @throws JSONException
     */
    public void ImportantNote_Dialog() throws JSONException {
        HashMap<String, String> dependentAnswerMap = questionInteractor.getOptionsLabel(String.valueOf(FormID));

        try {

            final Dialog dialog = new Dialog(AncVisits.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custome_pvs);
            dialog.setTitle(AncVisits.this.getString(R.string.Important_Note));
            final LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.linearLayout2);

            patientvisitlist.remove("bmi");

            for (Map.Entry<String, String> entry : patientvisitlist.entrySet()) {

                try {

                    JSONObject obj = new JSONObject(entry.getValue());

                    Optionlanguage = obj.getString(mAppLanguage);

                    Log.d(TAG, "OptionLanguage: " + Optionlanguage + " key " + entry.getKey());

                    if (Optionlanguage.contains(entry.getKey())) {

                        if (Optionlanguage.matches(".*[/,=:;-].*")) {

                            String[] splited = Optionlanguage.split("[\\s,/=:;-]+");

                            for (String s : splited) {

                                if (womendetails.containsKey(s.trim())) {

                                    Optionlanguage = Optionlanguage.replace(s.trim(), womendetails.get(s.trim())).trim();

                                    String answerKeyword = Optionlanguage.split(":")[1];

                                    for (Map.Entry answerSet : dependentAnswerMap.entrySet()) {

                                        if (answerKeyword.trim().equals(answerSet.getKey())) {

                                            JSONObject dependentAnsObject = new JSONObject(dependentAnswerMap.get(answerKeyword.trim()));

                                            String answer = dependentAnsObject.getString(mAppLanguage);

                                            Optionlanguage = Optionlanguage.replaceAll(answerKeyword, "  " + answer);
                                            break;

                                        }

                                    }

                                }
                            }
                        } else {
                            Optionlanguage = Optionlanguage.replace(entry.getKey(), womendetails.get(entry.getKey()));
                            Log.e("ANCVisit"," In the Important Dialog else "+Optionlanguage);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                CheckBox cb = new CheckBox(AncVisits.this);

                cb.setText(Optionlanguage);
                cb.setTextSize(20);
                cb.setPadding(10, 10, 10, 10);
                ll.addView(cb);

            }

            if (ll.getChildCount() == 0) {
                ll.setVisibility(View.GONE);
                final LinearLayout ll1 = (LinearLayout) dialog.findViewById(R.id.linearLayout3);
                ll1.setVisibility(View.VISIBLE);
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
                        Log.e("ANCVisit"," In the Important Dialog in flag "+flag);
                        if (!HighriskStatus) {
                            Log.e("ANCVisit"," In the Important Dialog !highriskstatus "+HighriskStatus);
                            highriskdialog();
                            dialog.dismiss();
                            ImportantDialogStatus = true;
                        }

                    } else {
                        Toast.makeText(AncVisits.this, AncVisits.this.getString(R.string.checkbox_select_msg), Toast.LENGTH_SHORT).show();
                    }

                }
            });


            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to display high risk dialog with high risk list
     */
    public void highriskdialog() {
        try {
            Log.e("ANCVisit"," In the highriskdialog ");

            final Dialog dialog = new Dialog(AncVisits.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custome_highrisk);
            dialog.setTitle(AncVisits.this.getString(R.string.Diagnostic_Test));
            final LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.linearLayout2);

            final Iterator<Map.Entry<String, String>> itr2 = highrisklist.entrySet().iterator();
            while (itr2.hasNext()) {
                final Map.Entry<String, String> entry = itr2.next();
                entry.getKey();
                entry.getValue();

//				System.out.println("entry.getKey() = " + entry.getKey());
//				System.out.println("entry.getValue() = " + entry.getValue());

                String myString = entry.getValue();

                System.out.println("myString" + myString);
                String[] a = myString.split(delimeter);

                System.out.println("a[5] = " + a[5]);
                System.out.println("a[4] = " + a[4]);
                if (a[5].equalsIgnoreCase("highrisk")) {
                    try {
                        JSONObject obj = new JSONObject(a[4]);
                        Optionlanguage = obj.getString(mAppLanguage);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    CheckBox cb = new CheckBox(AncVisits.this);

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

            Button referbut = (Button) dialog.findViewById(R.id.high_risk_button);
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
                        Log.e("ANCVisit"," In the if flag "+flag);

                        referraldialog();
                        dialog.dismiss();
                        HighriskStatus = true;
                        System.out.println("inside of if flag");

                    } else {
                        Toast.makeText(AncVisits.this, AncVisits.this.getString(R.string.checkbox_select_msg), Toast.LENGTH_SHORT).show();
                    }

                }
            });


            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is used to display diagnostic referral dialog with its list
     */
    public void referraldialog() {
        try {
            Log.e("ANCVisit"," In the referraldialog ");

            final Dialog dialog = new Dialog(AncVisits.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custome_referrallist);
            dialog.setTitle(R.string.Diagnostic_Test);
            final LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.linearLayout2);
            System.out.println("ll value" + ll);
            //View openDialog = (View) findViewById(R.id.linearLayout2);
            //add checkboxes
//    for(int i = 0; i < 6; i++) {
//       CheckBox cb = new CheckBox(EnrollmentQuestions.this);
//       cb.setText("Dynamic Checkbox " + i);
//       cb.setId(i + 6);
//       System.out.println("CheckBox value" + cb);
//       ll.addView(cb);
//    }
            System.out.println("referrallist.size() = " + referrallist.size());
            Log.e("ANCVisit"," In the referraldialog referrallist.size() "+referrallist.size() +" -- list -- "+referrallist);
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

                CheckBox cb = new CheckBox(ctx);

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
                        Log.e("ANCVisit"," In the referraldialog flag "+ flag);

                        saveForm();
                        dialog.dismiss();
                        RefferalStatus = true;
                    } else {
                        Toast.makeText(AncVisits.this, AncVisits.this.getString(R.string.checkbox_select_msg), Toast.LENGTH_SHORT).show();
                    }

                }
            });


            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to store ANC related dataSource in local DB.
     */
    public long StoreANCForm() {
        long insertedRowIdReferralWomenTable = -1;

        try {
            wages_status=1;
            questionInteractor.updateFormCompletionStatus(maxautoId,wages_status);

            if (highrisklist.size() > 0) {
                questionInteractor.saveReferralData(highrisklist, uniqueId, formid);
               /* insertedRowIdReferralWomenTable = dbhelper.inserthighriskwomen(highrisklist, "" + uniqueId, dbhelper.getANMInfo("ANMSubCenterId"), "");
                dbhelper.updatehighrisklist(uniqueId, "1");*/
            }

            if (FormID == DELIVERY_FORM_ID) {
                String deliveryDate =  womendetails.get(DELIVERY_DATE);
                questionInteractor.updateDeliveryDetails(uniqueId, deliveryDate);
                int childCount = Integer.parseInt(womendetails.get(LIVE_CHILDREN_COUNT));

                Cursor cursor = Utility.getDatabase().rawQuery("SELECT * FROM "
                        + DatabaseContract.RegistrationTable.TABLE_NAME
                        + " WHERE "
                        + DatabaseContract.RegistrationTable.COLUMN_UNIQUE_ID + " = ? ", new String[]{uniqueId});
                String address = "", villageId = "", mobNo = "", alternateNo = "";
                if (cursor.moveToFirst()) {
                    address = cursor.getString(cursor.getColumnIndex(DatabaseContract.RegistrationTable.COLUMN_ADDRESS));
                    villageId = cursor.getString(cursor.getColumnIndex(DatabaseContract.RegistrationTable.COLUMN_VILLAGE_ID));
                    mobNo = cursor.getString(cursor.getColumnIndex(DatabaseContract.RegistrationTable.COLUMN_MOBILE_NO));
                    alternateNo = cursor.getString(cursor.getColumnIndex(DatabaseContract.RegistrationTable.COLUMN_ALTERNATE_NO));
                }

                for (int i = 0; i < childCount; i++) {
                    questionInteractor.saveRegistrationDetails("", "", "", mobNo, alternateNo, villageId, "", "", address, deliveryDate, "", "", "", photo, uniqueId, 0);
                }


            }

            Toast.makeText(getApplicationContext(), AncVisits.this.getString(R.string.Toast_msg_for_formsavesuccessfully), Toast.LENGTH_LONG).show();

            finish();


           /* calculatevisitList = dbhelper.getNextVisit("" + (Integer.parseInt(formid) + 1));
            calculatevisitList1 = dbhelper.getVisitWeek_list();
             if(!isAncPncEdit) {
                 if (calculatevisitList != null && calculatevisitList.size() > 0) {
                     if (nextvisitdate(womanLmp, Integer.toString(calculatevisitList.get(0).getFromWeek())) != null && nextvisitdate(womanLmp, Integer.toString(calculatevisitList.get(0).getFromWeek())).length() > 0) {
                         NextVisit_dialog(calculatevisitList.get(0).getANCVisit(), nextvisitdate(womanLmp, Integer.toString(calculatevisitList.get(0).getFromWeek())), "NextVisit");
                     }
                 } else {
                     NextVisit_dialog("", "", "");
                 }
             }else{
                 finish();
             }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertedRowIdReferralWomenTable;
    }

    /**
     * This methos is used to store Closure form related dataSource in local DB.
     *
     * @param closureType
     */
    public void StoreClosureForm(String closureType) {

            if (closureType.equalsIgnoreCase("Mother")) {
                questionInteractor.updateClosureDetails(uniqueId, Utility.getCurrentDateTime(), womendetails.get(MOTHER_CLOSE_REASON), womendetails.get(MOTHER_DEATH_DATE), womendetails.get(MOTHER_DEATH_REASON));

            } else if (closureType.equalsIgnoreCase("Child")) {
                questionInteractor.updateClosureDetails(uniqueId, Utility.getCurrentDateTime(), womendetails.get(CHILD_CLOSE_REASON), womendetails.get(CHILD_DEATH_DATE), womendetails.get(CHILD_DEATH_REASON));
            }
            wages_status=1;
            questionInteractor.updateFormCompletionStatus(maxautoId,wages_status);
            finish();
    }

    /**
     * This methos is used to store registration of the newly born children related dataSource in local DB.
     */
    public long StoreChildCareRegForm() {
        long insertedRowIdReferralWomenTable = -1;
        wages_status=0;
        questionInteractor.updateFormCompletionStatus(maxautoId,wages_status);

        if (highrisklist.size() > 0) {
            questionInteractor.saveReferralData(highrisklist, uniqueId, formid);
        }

        questionInteractor.updateChildRegistrationDetails(uniqueId, womendetails.get(FIRST_NAME), womendetails.get(MIDDLE_NAME), womendetails.get(LAST_NAME), womendetails.get(GENDER),photo);
        finish();
        return insertedRowIdReferralWomenTable;

    }

    public long storeChildCareForm() {

        long insertedRowIdReferralWomenTable = -1;
        if(FormID<clickedFormId) {
            wages_status=0;
            questionInteractor.updateFormCompletionStatus(maxautoId,wages_status);
        }
        else if(FormID>=clickedFormId){
            wages_status=1;
            questionInteractor.updateFormCompletionStatus(maxautoId,wages_status);
        }
        else{
            wages_status=1;
            questionInteractor.updateFormCompletionStatus(maxautoId,wages_status);
        }
        if (highrisklist.size() > 0) {
            questionInteractor.saveReferralData(highrisklist, uniqueId, formid);

        }
        finish();
        return insertedRowIdReferralWomenTable;
    }

    /**
     * This method is used to calculate ANM's visit and delivery date of the women
     *
     * @param currentDate=Sysdate
     * @param WomanLmp=lmp        of the women
     * @return ANM's visit
     */
    public int date(String currentDate, String WomanLmp) {
        String ancvisit = null;

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        DateFormat datetime = new SimpleDateFormat("hh:mm a");
        String fromDate = currentDate;
        String toDate = WomanLmp;
        long diff = 0;

        try {

            //Convert to Date
            Date startDate = df.parse(fromDate);
            Calendar c1 = Calendar.getInstance();
            //Change to Calendar Date
            c1.setTime(startDate);

            //Convert to Date
            Date endDate = df.parse(toDate);
            Calendar c2 = Calendar.getInstance();
            //Change to Calendar Date
            c2.setTime(endDate);

            Date expecDate = df.parse(toDate);
            Calendar c3 = Calendar.getInstance();
            //Change to Calendar Date
            c3.setTime(expecDate);
            c3.add(Calendar.MONTH, 9);
            System.out.println("^^^^^^^^^^^^^" + df.format(c3.getTime()));
//			expec_date=df.format(c3.getTime());
//
//			Calendar c4 = Calendar.getDataSource();
//			current_reg = datetime.format(c4.getTime());

            //get Time in milli seconds
            long ms1 = c1.getTimeInMillis();
            long ms2 = c2.getTimeInMillis();
            //get difference in milli seconds
            diff = ms1 - ms2;

        } catch (ParseException e) {
            e.printStackTrace();
        }


        int diffInDays = (int) (diff / (24 * 60 * 60 * 1000));
        int months = diffInDays / 30;
        int weeks = diffInDays / 7;
        System.out.println("Number of days difference is: " + diffInDays);
//		System.out.println("Number of Months difference is: " + months);


        System.out.println("weeks = " + weeks);

        //int maxAncWeek=dbhelper.getMaxANCWeek();


//		for(int i=0;i<calculatePncVisitList.size()-1;i++)
//			{
//				//System.out.println("weeks=="+weeks);
//				if((calculatePncVisitList.get(i).getFromWeek() <= diffInDays) && (diffInDays <= ((calculatePncVisitList.get(i).getToWeek()))))
//				{
//					//System.out.println("Visit^^^^^^"+calculatevisitList.get(i).getANCVisit());
//					ancvisit=calculatePncVisitList.get(i).getANCVisit();
//					//visitId=calculatevisitList.get(i).getVisit_id();
//				}
//
//			}


//		if(diffInDays<=maxAncWeek)
//		{
//
//			for(int i=1;i<calculatevisitList.size();i++)
//			{
//				//System.out.println("weeks=="+weeks);
//				if((calculatevisitList.get(i).getFromWeek() <= diffInDays) && (diffInDays <= ((calculatevisitList.get(i).getToWeek()))))
//				{
//					//System.out.println("Visit^^^^^^"+calculatevisitList.get(i).getANCVisit());
//					ancvisit=calculatevisitList.get(i).getANCVisit();
//					//visitId=calculatevisitList.get(i).getVisit_id();
//				}
//
//			}
//		}else
//		{
//			//visitId="7";
//		}


        return diffInDays;
    }

    public void backForm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder
                .setTitle(AncVisits.this.getString(R.string.back_form))
                .setMessage(AncVisits.this.getString(R.string.back_form_message))
                .setIcon(R.mipmap.ic_exitalert)
                .setPositiveButton(AncVisits.this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                })
                .setNegativeButton(AncVisits.this.getString(R.string.no), new DialogInterface.OnClickListener() {
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


            System.out.println("removeDependentQuestion = " + removeDependentQuestion);


            for (int i = 0; i < removeDependentQuestion.size(); i++) {
                LinearLayout tp = (LinearLayout) ll_4.findViewWithTag(removeDependentQuestion.get(i));

                ll_4.removeView(tp);

                questionInteractor.deleteAnswer(uniqueId, formid, removeDependentQuestion.get(i));

                dependantKeywordPresent.remove(removeDependentQuestion.get(i));

                womendetails.remove(removeDependentQuestion.get(i));
                Backup_answerTyped1.remove(removeDependentQuestion.get(i));
                validationlist.remove(removeDependentQuestion.get(i));
                NextButtonvalidationlist.remove(removeDependentQuestion.get(i));
                patientvisitlist.remove(removeDependentQuestion.get(i));

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


            //System.out.println("validationConditions = " + validation_Conditions);


            JSONObject validationJsonObject = null;

            try {
                if (validationConditions != null && validationConditions.length() > 0) {
                    validationJsonObject = new JSONObject(validationConditions);
                    System.out.println("validationJsonObject = " + validationJsonObject);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (validationJsonObject.optString("range") != null && validationJsonObject.optString("range").length() > 0) {
                JSONObject rangeobject = null;
                try {
                    rangeobject = validationJsonObject.getJSONObject("range");
                    System.out.println("rangeobject = " + rangeobject);
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


            //	System.out.println("Validation length condition ***" + validationobject.optString("length").toString());

            if (validationJsonObject.optString("length") != null && validationJsonObject.optString("length").length() > 0) {

                JSONObject lengthobject = null;
                try {
                    lengthobject = validationJsonObject.getJSONObject("length");
                    System.out.println("lengthobject = " + lengthobject);
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


            System.out.println("setOnTouchListener length_min = " + length_min);
            System.out.println("setOnTouchListener length_max = " + length_max);
            System.out.println("setOnTouchListener length_lang = " + length_lang);

//						System.out.println("keyword is........"+dependantQuesKeyword);
//						System.out.println("womenanswer.containsKey(women_dob)........"+(storeEnteredData.containsKey("women_dob")));
//						System.out.println("get value........"+storeEnteredData);
//
//						System.out.println("storeEnteredData.get(\"women_dob\").equals(\"know_woman_dob_yes\") = " + storeEnteredData.get("women_dob").equals("know_woman_dob_yes"));

            if (dependantQuesKeyword.equals("women_age") && storeEnteredData.containsKey("women_dob")) {
                if (storeEnteredData.get("know_woman_dob").equals("know_woman_dob_yes")) //auto calculate
                {
                    if (storeEnteredData.containsKey("women_dob")) {
                        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd");
                        DateTime dateTime = dtfOut.parseDateTime(storeEnteredData.get("women_dob"));
                        LocalDate oprand2 = new LocalDate(dateTime);
                        LocalDate today = new LocalDate();
                        String result = (String.valueOf(Years.yearsBetween(oprand2, today).getYears()));

                        System.out.println("year diff is............" + result);
                        et.setText(result);

                        et.setFocusable(false);
                        et.setClickable(false);

                        StorePVSmsgs(messages);
                    } else {
                        et.setFocusable(true);
                        et.setClickable(true);
                        Toast.makeText(getApplicationContext(), "Enter date of birth", Toast.LENGTH_SHORT).show();
                    }
                }


            } else {
                StorePVSmsgs(messages);
            }
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
            System.out.println("StorePVSmsgs messages = " + messages);
            if (messages != null && messages.length() > 0) {
                JSONObject highRisk_conditions = new JSONObject(messages);


                if (highRisk_conditions.optJSONArray("highrisk") != null) {
                    JSONArray highRisk_conditionsArray = highRisk_conditions.optJSONArray("highrisk");


                    for (int k = 0; k < highRisk_conditionsArray.length(); k++) {
                        JSONObject main_ques_options_key = highRisk_conditionsArray.getJSONObject(k);

                        //System.out.println("main_ques_options_key range = " +" key....."+keyword+"    "+ main_ques_options_key.optString("range").toString());;

                        if (main_ques_options_key.getJSONObject("range") != null && main_ques_options_key.getJSONObject("range").length() > 0) {
                            JSONObject parseRangeminmax = main_ques_options_key.getJSONObject("range");

                            Visits highriskcond =new Visits(parseRangeminmax.optDouble("min"),parseRangeminmax.optDouble("max"),main_ques_options_key.optString("languages").toString(),"");
                            //System.out.println("main_ques_options_key languages = " + main_ques_options_key.optString("languages").toString());;


                            System.out.println("highriskcond = " + highriskcond);
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

                            Visits counsellingcond =new Visits(parseRangeminmax.optDouble("min"),parseRangeminmax.optDouble("max"),main_ques_options_key.optString("languages").toString(),main_ques_options_key.optString("show_popup"));
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

                            Visits patientVisitSummarycond =new Visits(parseRangeminmax.optDouble("min"),parseRangeminmax.optDouble("max"),patientVisitSummary_conditionsArray_options_key.optString("languages").toString(),"");
                            StoredPatientVisitSummaryRanges.add(patientVisitSummarycond);
                        }else
                        {
                            Visits patientVisitSummarycond =new Visits(0,0,patientVisitSummary_conditionsArray_options_key.optString("languages").toString(),"");
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

                System.out.println("StoredCounsellingRanges Entered value = " + value);
                for (int i = 0; i < StoredCounsellingRanges.size(); i++) {

                    Double rangeMax = StoredCounsellingRanges.get(i).getRangemax();
                    Double rangeMin = StoredCounsellingRanges.get(i).getRangemin();
                    String counsellinglang = StoredCounsellingRanges.get(i).getRangeLang();

                    System.out.println("counselling Range : " + rangeMin + " : " + rangeMax);

                    if (value >= rangeMin && value <= rangeMax) {
                        System.out.println("Val = " + value);
                        System.out.println("counsellinglang = " + counsellinglang);
                        System.out.println("et.getId() = " + et.getId());
                        System.out.println("et = " + et);
                        System.out.println("et.getParent() = " + et.getParent());

                        LinearLayout ll_1 = (LinearLayout) et.getParent();
                        ll_1.setBackgroundColor(ctx.getResources().getColor(R.color.highrisk));

                        try {
                            if (counsellinglang != null && counsellinglang.length() > 0) {
                                if (!counclingMsgQstnKeywords.contains(keyword)) {
                                    JSONObject obj = new JSONObject(counsellinglang);
                                    String language = obj.getString(mAppLanguage);
                                    LinearLayout ll4_1 = (LinearLayout) ll_1.getParent();
//									LinearLayout ll_1= (LinearLayout) et.getParent();

                                    language = language.replace("\\n ", "\n");//
                                    //language=language.replace("\\n");

                                    System.out.println("counsel_message................" + language + "....tag.........." + keyword);

                                    TextView counslingMsgTextView = new TextView(ctx);
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                        counslingMsgTextView.setText(Html.fromHtml(language, Html.FROM_HTML_MODE_LEGACY));
                                    } else {
                                        counslingMsgTextView.setText(Html.fromHtml(language));
                                    }
                                    counslingMsgTextView.setBackgroundColor(ctx.getResources().getColor(R.color.dependent_question_background));
                                    counslingMsgTextView.setTextColor(Color.RED);
                                    counslingMsgTextView.setTextSize(getResources().getDimension(R.dimen.text_size_medium));
                                    counslingMsgTextView.setLayoutParams(layoutParamQuestion);

                                    counslingMsgTextView.setPadding(20, 0, 20, 20);

                                    ll4_1.addView(counslingMsgTextView, ((ll4_1.indexOfChild(ll_1)) + 1));

                                    counclingMsgQstnKeywords.add(keyword);

                                    if(StoredCounsellingRanges.get(i).getShowPopUp().equalsIgnoreCase("1"))
                                        if(!noDialogFirst) {
                                            criticalCounsellingMsg(language);
                                        }
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        break;
                    } else {
                        System.out.println("et.getId() = " + et.getId());
                        System.out.println("et.getParent() = " + et);

                        LinearLayout ll_1 = (LinearLayout) et.getParent();
                        ll_1.setBackgroundColor(ctx.getResources().getColor(R.color.anc_form));

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
                ll_1.setBackgroundColor(ctx.getResources().getColor(R.color.anc_form));
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

                System.out.println("StoredHighRiskRanges Entered value = " + value);
                for (int i = 0; i < StoredHighRiskRanges.size(); i++) {

                    Double rangeMax = StoredHighRiskRanges.get(i).getRangemax();
                    Double rangeMin = StoredHighRiskRanges.get(i).getRangemin();
                    String rangelang = StoredHighRiskRanges.get(i).getRangeLang();

                    System.out.println("Range : " + rangeMin + " : " + rangeMax);

                    if (value >= rangeMin && value <= rangeMax) {
                        System.out.println("Val = " + value);
                        System.out.println("rangelang = " + rangelang);

                        LinearLayout ll_1 = (LinearLayout) et.getParent();
                        ll_1.setBackgroundColor(ctx.getResources().getColor(R.color.highrisk));

                        highrisklist.put("" + et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + value + delimeter + rangelang + delimeter + "highrisk" + delimeter + "0");
                        break;
                    } else {

                        if (previousVisitDetails.get(keyword) != null) {

                            if (previousVisitDetails.containsKey("weight_of_women") && previousVisitDetails.get(keyword).equalsIgnoreCase("weight_of_women")) {
                                if ((value - Double.parseDouble(previousVisitDetails.get("weight_of_women"))) <= 2) {
                                    System.out.println("weight of women not increased by 2 kgs");
                                    LinearLayout ll_1 = (LinearLayout) et.getParent();
                                    ll_1.setBackgroundColor(ctx.getResources().getColor(R.color.highrisk));

                                    highrisklist.put("" + et.getId(), "" + formid + delimeter + setid + delimeter + keyword + delimeter + value + delimeter + rangelang + delimeter + "highrisk" + delimeter + "0");

                                } else {
                                    LinearLayout ll_1 = (LinearLayout) et.getParent();
                                    ll_1.setBackgroundColor(ctx.getResources().getColor(R.color.anc_form));
                                    System.out.println("et.getId() = " + et.getId());
                                    highrisklist.remove("" + et.getId());
                                }
                            } else {
                                LinearLayout ll_1 = (LinearLayout) et.getParent();
                                ll_1.setBackgroundColor(ctx.getResources().getColor(R.color.anc_form));
                                System.out.println("et.getId() = " + et.getId());
                                highrisklist.remove("" + et.getId());
                            }

                        } else {
                            System.out.println("***** above high risk ***");
                            LinearLayout ll_1 = (LinearLayout) et.getParent();
                            ll_1.setBackgroundColor(ctx.getResources().getColor(R.color.anc_form_dependent));
                            System.out.println("et.getId() = " + et.getId());
                            highrisklist.remove("" + et.getId());
                        }
                    }

                }

            } else {

                LinearLayout ll_1 = (LinearLayout) et.getParent();
                ll_1.setBackgroundColor(ctx.getResources().getColor(R.color.anc_form));
                System.out.println("et.getId() = " + et.getId());
                highrisklist.remove("" + et.getId());
            }
        }


        if (StoredPatientVisitSummaryRanges != null && StoredPatientVisitSummaryRanges.size() > 0) {

            if (editTextValue != null && editTextValue.length() > 0) {

                Double value = Double.parseDouble(editTextValue);

                System.out.println("StoredPatientVisitSummaryRanges Entered value = " + value);
                for (int i = 0; i < StoredPatientVisitSummaryRanges.size(); i++) {

                    Double rangeMax = StoredPatientVisitSummaryRanges.get(i).getRangemax();
                    Double rangeMin = StoredPatientVisitSummaryRanges.get(i).getRangemin();
                    String rangelang = StoredPatientVisitSummaryRanges.get(i).getRangeLang();

                    System.out.println("Range : " + rangeMin + " : " + rangeMax);

                    if (value >= rangeMin && value <= rangeMax) {
//									System.out.println("Val = " + value);
//									System.out.println("inside if StoredPatientVisitSummaryRanges rangelang = " + rangelang);

                        patientvisitlist.put(keyword, rangelang);
                        break;
                    } else {
//									System.out.println("Val = " + value);
//									System.out.println("inside else StoredPatientVisitSummaryRanges rangelang = " + rangelang);

                        patientvisitlist.put(keyword, rangelang);
                    }

                }

            } else {

                patientvisitlist.remove(keyword);
            }

        }

    }

    public void displayCounsellingForSpinner(String counsel_message, View v, String keyword) {

        if (counclingMsgQstnKeywords.contains(keyword)) {

            LinearLayout ll4_1 = (LinearLayout) v.getParent().getParent().getParent();

            LinearLayout linearLayout = (LinearLayout) ll4_1.findViewWithTag(keyword);

            int indexQuestionlayout = ll4_1.indexOfChild(linearLayout);

            ll4_1.removeViewAt(indexQuestionlayout + 1);

            counclingMsgQstnKeywords.remove(keyword);

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

        if (counclingMsgQstnKeywords.contains(keyword)) {

            LinearLayout ll4_1 = (LinearLayout) v.getParent().getParent().getParent();

            LinearLayout linearLayout = (LinearLayout) ll4_1.findViewWithTag(keyword);

            int indexQuestionlayout = ll4_1.indexOfChild(linearLayout);

            ll4_1.removeViewAt(indexQuestionlayout + 1);

            counclingMsgQstnKeywords.remove(keyword);

        }

        try {

            if (counsel_message != null && counsel_message.length() > 0) {

                if (!counclingMsgQstnKeywords.contains(keyword)) {

                    JSONObject obj = new JSONObject(counsel_message);

                    String language = obj.getString(mAppLanguage);

                    LinearLayout ll4_1 = (LinearLayout) v.getParent().getParent().getParent();

                    LinearLayout linearLayout = (LinearLayout) ll4_1.findViewWithTag(keyword);

                    int indexQuestionlayout = ll4_1.indexOfChild(linearLayout);

                    language = language.replace("\\n ", "\n");

                    TextView counslingMsgTextView = new TextView(ctx);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        counslingMsgTextView.setText(Html.fromHtml(language, Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        counslingMsgTextView.setText(Html.fromHtml(language));
                    }
                    counslingMsgTextView.setBackgroundColor(ctx.getResources().getColor(R.color.dependent_question_background));
                    counslingMsgTextView.setTextColor(Color.RED);
                    counslingMsgTextView.setTextSize(getResources().getDimension(R.dimen.text_size_medium));

                    counslingMsgTextView.setLayoutParams(layoutParamQuestion);

                    counslingMsgTextView.setPadding(20, 0, 20, 20);

                    ll4_1.addView(counslingMsgTextView, (indexQuestionlayout + 1));

                    counclingMsgQstnKeywords.add(keyword);

                }

            } else {

                if (counclingMsgQstnKeywords.contains(keyword)) {

                    LinearLayout ll4_1 = (LinearLayout) v.getParent().getParent().getParent();

                    LinearLayout linearLayout = (LinearLayout) ll4_1.findViewWithTag(keyword);

                    int indexQuestionlayout = ll4_1.indexOfChild(linearLayout);

                    ll4_1.removeViewAt(indexQuestionlayout + 1);

                    counclingMsgQstnKeywords.remove(keyword);

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to calculate gestational age of the women in weeks in every ANC visit.
     *
     * @param currentDate = System date
     * @param WomanLmp    = lmp date of the women
     * @return = age in weeks
     */
    public int getGestationalAgeWeek(String currentDate, String WomanLmp) {
        String ancvisit = null;

        System.out.println("currentDate : "+currentDate);
        System.out.println("WomanLmp : "+WomanLmp);

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        DateFormat sever_expec_date = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        String fromDate = currentDate;
        String toDate = WomanLmp;
        long diff = 0;

        try {

            //Convert to Date
            Date startDate = df.parse(fromDate);
            Calendar c1 = Calendar.getInstance();
            //Change to Calendar Date
            c1.setTime(startDate);

            //Convert to Date
            Date endDate = df.parse(toDate);
            Calendar c2 = Calendar.getInstance();
            //Change to Calendar Date
            c2.setTime(endDate);

//			System.out.println("toDate = " + toDate);
//			Date expecDate = df.parse(toDate);
//			Calendar c3 = Calendar.getDataSource();
//			//Change to Calendar Date
//			c3.setTime(expecDate);
//			c3.add(Calendar.MONTH, 9);
//			System.out.println("^^^^^^^^^^^^^" + df.format(c3.getTime()));
//			expec_date=df.format(c3.getTime());
//
//			System.out.println("expec_date = " + expec_date);
//			Server_expected_date=sever_expec_date.format(c3.getTime());
//
//			Calendar c4 = Calendar.getDataSource();
//			current_reg = datetime.format(c4.getTime());

            //get Time in milli seconds
            long ms1 = c1.getTimeInMillis();
            long ms2 = c2.getTimeInMillis();
            //get difference in milli seconds
            diff = ms1 - ms2;

        } catch (ParseException e) {
            e.printStackTrace();
        }


        int diffInDays = (int) (diff / (24 * 60 * 60 * 1000));
        int months = diffInDays / 30;
        int weeks = diffInDays / 7;
        System.out.println("Number of days difference is: " + diffInDays);
//		System.out.println("Number of Months difference is: " + months);
        //woman_gest_age =""+weeks;

        System.out.println("weeks = " + weeks);


        return weeks;

    }

    /**
     * This method is used to calculate the formula for FA tablet
     *
     * @param gestionalAge = women gestational age in integer
     * @param ll4          = linear layout on which the calculated value is to be displayed.
     */
    public void formulaFATablet(int gestionalAge, LinearLayout ll4) {
        int FATablet = 98 - (gestionalAge * 7);
        LinearLayout faLayout = (LinearLayout) ll4.findViewWithTag("number_of_fa_tablet");
        EditText faEditText = (EditText) faLayout.getChildAt(1);
        TextView faTextview = (TextView) faLayout.getChildAt(0);
        System.out.println("FATablet = " + FATablet);
        faEditText.setText("" + FATablet);
        faEditText.setEnabled(false);
        faEditText.setError(null);
        faTextview.setError(null);
        validationlist.put("number_of_fa_tablet", "" + FATablet);
    }

    /**
     * This method is used to calculate the formula for FA tablet
     *
     * @param ll4 = linear layout on which the calculated value is to be displayed
     */
    public void getIfaTablets(LinearLayout ll4) {
        double hbLevel = Double.parseDouble(womendetails.get("hb_level"));

        LinearLayout ifaLayout = (LinearLayout) ll4.findViewWithTag("count_of_ifa_tablet_given_to_women");
        EditText ifaEditText = (EditText) ifaLayout.getChildAt(1);
        TextView ifaTextview = (TextView) ifaLayout.getChildAt(0);
        if (hbLevel > 10) {
            ifaEditText.setText("100");
            validationlist.put("count_of_ifa_tablet_given_to_women", "100");
        } else {
            ifaEditText.setText("200");
            validationlist.put("count_of_ifa_tablet_given_to_women", "200");
        }

        ifaEditText.setEnabled(false);
        ifaEditText.setError(null);
        ifaTextview.setError(null);
    }

    public void checkIsCompulsory(EditText et, String keyword, TextView tv, String validationfield) {

        if (validationfield != null && validationfield.equalsIgnoreCase("true")) {
            if (validationlist.containsKey(keyword)) {
                if (et.getText().toString().length() <= 0) {

                    validationlist.put(keyword, "");
                    tv.setError("");
                } else {
                    validationlist.put(keyword, et.getText().toString());
                    tv.setError(null);
                }
            }
        } else {
            tv.setError(null);
            et.setError(null);
            validationlist.remove(keyword);
            NextButtonvalidationlist.remove(keyword);
        }

    }

    public void hideSoftKeyboard(View view) {
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void createButton(final String quesid, final String keyword, final String setid, final int id, final TextView tv, final String formid, final List<Visit> listOptions, final String displayCondition, final String pageScrollId, final Boolean isCompulsory, final int orientation) {

        LinearLayout buttonLayout = new LinearLayout(ctx);
        buttonLayout.setTag(keyword);
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

                    System.out.println("v.getTag() = " + v.getTag());
                    setButtonColor(AncVisits.this, v, (LinearLayout) v.getParent());
                    onClickButtonFunctionality(button, v, quesid, keyword, setid, id, tv, formid, listOptions, displayCondition, pageScrollId, isCompulsory, orientation);
                }
            });

            if (womendetails.containsKey(keyword)) {

                if (womendetails.get(keyword).equalsIgnoreCase(button.getTag().toString())) {
                    button.performClick();

                }

            }

            if (displayCondition != null && displayCondition.length() > 0 && previousVisitDetails.containsKey(keyword)) {
                if (listOptions.get(i).getKeyword().equals(previousVisitDetails.get(keyword))) {
                    button.performClick();
                }
                tv.setError(null);

                validationlist.put(keyword, previousVisitDetails.get(keyword));
                button.setEnabled(false);
            }

        }

    }

    public void onClickButtonFunctionality(Button button, View v, final String quesid, final String keyword, final String setid, int id, final TextView tv, String formid, List<Visit> optionList, String displayCondition, final String pageScrollId, Boolean isCompulsory, int orientation) {
        List<Visit> dependantList = null;

        tv.setError(null);

        storeEnteredData.put(keyword, button.getTag().toString());

        String counsel_message = null;

//        String Messages = dbhelper.getHighRiskConditionForRadio("" + button.getTag());
        String Messages = questionInteractor.getHighRiskCondition("" + button.getTag());
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
                        //System.out.println("main_ques_options_key High risk lang = " + main_ques_options_key.optString("languages").toString());;


                        highrisklist.put(quesid, "" + AncVisits.this.formid + delimeter + setid + delimeter + keyword + delimeter + button.getTag() + delimeter + main_ques_options_key.optString("languages").toString() + delimeter + "highrisk" + delimeter + "0");
                        //System.out.println("highrisklist = " + highrisklist);
                    }
                }
                //System.out.println("highrisklist.size() = " + highrisklist.size());
                //System.out.println("highRisk_conditions.optJSONArray(\"counselling\") = " + highRisk_conditions.optJSONArray("counselling"));

                if (highRisk_conditions.optJSONArray("counselling") != null) {
                    JSONArray counseling_conditionsArray = highRisk_conditions.optJSONArray("counselling");
                    for (int k = 0; k < counseling_conditionsArray.length(); k++) {
                        JSONObject main_ques_options_key = counseling_conditionsArray.getJSONObject(0);

                        //System.out.println("main_ques_options_key Counseling lang = " + main_ques_options_key.optString("languages").toString());;
                        counsel_message = main_ques_options_key.optString("languages").toString();

                        if (main_ques_options_key.has("show_popup")) {
                            JSONObject obj = new JSONObject(counsel_message);
                            String language = obj.getString(mAppLanguage);
                            if(!noDialogFirst) {
                                criticalCounsellingMsg(language);
                            }
                        }
                    }
                }

                if (highRisk_conditions.optJSONArray("diagnosticrefer") != null) {
                    JSONArray diagnosticrefer_conditionsArray = highRisk_conditions.optJSONArray("diagnosticrefer");
                    for (int k = 0; k < diagnosticrefer_conditionsArray.length(); k++) {
                        JSONObject diagnosticrefer_options_key = diagnosticrefer_conditionsArray.getJSONObject(0);
                        //System.out.println("main_ques_options_key Counseling lang = " + diagnosticrefer_options_key.optString("languages").toString());;
                        highrisklist.put(quesid, "" + AncVisits.this.formid + delimeter + setid + delimeter + keyword + delimeter + button.getTag() + delimeter + diagnosticrefer_options_key.optString("languages").toString() + delimeter + "diagnosticReffer" + delimeter + "0");
                        referrallist.put(quesid, diagnosticrefer_options_key.optString("languages").toString());
                        //System.out.println("diagnosticrefer highrisklist = " + highrisklist);
                    }
                }


                if (highRisk_conditions.optJSONArray("patientVisitSummary") != null) {
                    JSONArray patientVisitSummary_conditionsArray = highRisk_conditions.optJSONArray("patientVisitSummary");
                    for (int k = 0; k < patientVisitSummary_conditionsArray.length(); k++) {
                        JSONObject patientVisitSummary_options_key = patientVisitSummary_conditionsArray.getJSONObject(0);
                        //System.out.println("patientVisitSummary_options_key Counseling lang = " + patientVisitSummary_options_key.optString("languages").toString());;
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

        womendetails.put(keyword, "" + button.getTag());
        Backup_answerTyped1.put(keyword, "" + button.getTag());
        dependantList = questionInteractor.getDependantQuesList("" + button.getTag(), AncVisits.this.formid, ll_sub, keyword, "" + pageScrollId);  // this list is used to get dependant question in english format (parameter"s received are form_id,keyword,answer_type,english_lang)

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

            if (!dependantKeywordPresent.containsValue("" + button.getTag())) {

                LinearLayout ll_4 = (LinearLayout) v.getParent().getParent().getParent();
                removeDependent(ll_4, keyword);
                dependantKeywordPresent.put(keyword, "" + button.getTag());

                System.out.println("dependantKeywordPresent 2=....." + dependantKeywordPresent);

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

            Log.d(TAG, "ELSE : LinearLayout ll_4 " + ll_4.getTag() + " keyword " + keyword);

        }

        displayCounsellingMsg(counsel_message, v, keyword);

        if (validationlist.containsKey(keyword))
            validationlist.put(keyword, button.getTag().toString());


    }

    public void criticalCounsellingMsg(String popupMessage) {
        System.out.println("criticalCounsellingMsg");
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder
                .setTitle(R.string.counselling_msg_txt)
                .setMessage(popupMessage)
                .setIcon(R.mipmap.ic_exitalert)
                .setPositiveButton(AncVisits.this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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

    public String feetToCmConversion(String feetValue) {
        String centimeterValue = "";

        centimeterValue = String.valueOf(Double.parseDouble(feetValue) / 0.032808);

        System.out.println("centimeterValue = " + centimeterValue);
        return centimeterValue;
    }

    public class DisplayQuestions extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AncVisits.this);
            progressDialog.setMessage(getString(R.string.loading_question));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            Intent in = getIntent();
            b = in.getExtras();
            decimalFormat.applyPattern("#.00");
            //System.out.println("ANC Visit NAME++++++"+b.getString("ANCVisit"));

            //formid= dbhelper.getSelectedAncForm(b.getString("ANCVisit")); // gets id of the form after passing visit_name (eg.ANC Visit1 id is 2 in db)
            uniqueId = b.getString(UNIQUE_ID);
            clickedFormId = b.getInt(clickedForm);
                formid = b.getString(FORM_ID);
                current_form_id = Integer.valueOf(formid);
                if(clickedFormId == 0) {
                    clickedFormId = Integer.valueOf(formid);
                }
            /**if the registration option is direct child then the*/
                regOption= QuestionInteractor.getRegistrationOption(uniqueId);
            if(regOption.equals("direct_reg_child")) {
                if(clickedFormId!=22){
                do {
                    int a = QuestionInteractor.getformStatus(uniqueId);
                    if (a == 0) {
                            FormID = 10;
                            formid = "10";
                            current_form_id = 10;
                    } else {
                        FormID = a + 1;
                        formid = String.valueOf(FormID);
                    }
                    storePrevAncId = FormID;
                } while (current_form_id > clickedFormId);
            } else{
                    formid="22";
                    FormID=22;
                }
            } else{
                formid = b.getString(FORM_ID);
                storePrevAncId = Integer.parseInt(formid);
                FormID = Integer.parseInt(formid);
            }

            questionInteractor = new QuestionInteractor(AncVisits.this);

            mAppLanguage = Utility.getLanguagePreferance(getApplicationContext());

            womanLmp = b.getString(LMP_DATE);

            isAncPncEdit = b.getBoolean("isAncPncEdit");

            Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            String[] projection = {MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.VideoColumns.DATA };
            Cursor c = AncVisits.this.getContentResolver().query(uri, projection, null, null, null);
            if (c != null) {
                while (c.moveToNext()) {
                    hashMapVideoNamePath.put(c.getString(0).substring(0, c.getString(0).lastIndexOf(".")), c.getString(1));
                }
                c.close();
            }


            if(isAncPncEdit){
                noDialogFirst = true;
            }
            defaultdate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            serverdateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
            timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);
            serverdefaultdateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
            newDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            YMDFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            DMYFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

            maxautoId = questionInteractor.getFilledFormReferenceId(uniqueId, String.valueOf(FormID));
            if (maxautoId == -1) {
                maxautoId = questionInteractor.saveFilledFormStatus(uniqueId, FormID, 0, 0, Utility.getCurrentDateTime(),0);
            }

            previousVisitDetails = questionInteractor.getFormFilledData(uniqueId, (FormID - 1));
            womendetails = questionInteractor.getFormFilledData(uniqueId, FormID);

            Frame = (FrameLayout) findViewById(R.id.frame);
            next = (Button) findViewById(R.id.btnNext);
            previous = (Button) findViewById(R.id.btnpre);

            progress = (ProgressBar) findViewById(R.id.progressBar1);

            formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            System.out.println("formatter***" + formatter);


            lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
            sp1 = new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
            lp.setMargins(10, 10, 10, 10);

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

            try {
                SystemDate = formatter.parse(defaultdate);
                System.out.println("SystemDate***" + SystemDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            try {
                System.out.println("womendetails.get(\"date_of_delivery\") = " + womendetails.get("date_of_delivery"));
                if (womendetails.get("date_of_delivery") != null && womendetails.get("date_of_delivery").length() > 0) {
                    Date d = serverdateFormatter.parse(womendetails.get("date_of_delivery"));
                    System.out.println("formatter.parse(defaultdate); = " + serverdateFormatter.parse(womendetails.get("date_of_delivery")));
                    System.out.println("formatter.format(d)== " + formatter.format(d));

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            alertList = questionInteractor.getMainQuestions(String.valueOf(FormID),uniqueId,clickedFormId);

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            int layout_control_counter = 0;

            drawableMainQstn = new GradientDrawable();
            drawableMainQstn.setShape(GradientDrawable.RECTANGLE);
            drawableMainQstn.setStroke(2, Color.WHITE);
            drawableMainQstn.setColor(ContextCompat.getColor(AncVisits.this, R.color.main_question_background));

            drawableDependentQstn = new GradientDrawable();
            drawableDependentQstn.setShape(GradientDrawable.RECTANGLE);
            drawableDependentQstn.setStroke(1, Color.WHITE);
            drawableDependentQstn.setColor(ContextCompat.getColor(AncVisits.this, R.color.dependent_question_background));

/**this if loop is to check if the child is registered directly.*/
            if(!regOption.equals("direct_reg_child")) {
                String firstForm1 = questionInteractor.firstFilledForm(uniqueId);
                firstForm = Integer.valueOf(firstForm1);
            }
            int prevChildAge= Integer.valueOf(questionInteractor.getPrevChildAge(uniqueId));//if value of prevChildAge comes as -1 then that means woman does not have child
            TT1Dose = questionInteractor.TT1Dose(uniqueId, FormID);
            TTDose1Date = questionInteractor.TTDoseDate(uniqueId, formid);
            TT2Dose = questionInteractor.TT2Dose(uniqueId, FormID);

            /**
             * For loop for displaying questions for enrollment form which is stored in localDB.
             */
            for (int j = counter; j < alertList.size(); j++) {

/**
 * Display only 4 questions on the layout if counter goes above 4 break loop and create new layout for further questions
 * if answer_type contains capturephoto then break and loop and include that question on new layout
 *
 */
                if (layout_control_counter == 0 || layout_control_counter % 4 == 0 || alertList.get(j).getAnswerType().equalsIgnoreCase("capturephoto") || alertList.get(j).getAnswerType().equalsIgnoreCase("sublabel") || alertList.get(j).getAnswerType().equalsIgnoreCase("label")|| PreviousQuesAnswertype.equals("label"))
                {
                    layout_control_counter=0;
                    layoutcounter++;
                    System.out.println("counter layoutcounter value" + layoutcounter);
                    ll_4layout = new LinearLayout(ctx);
                    ll_4layout.setLayoutParams(lp1);
                    ll_4layout.setOrientation(LinearLayout.VERTICAL);
                    ll_4layout.setId(j + 100000);
//					ll_4layout.setPadding(30, 4, 30, 0);
                    ll_4layout.setBackgroundColor(Color.parseColor("#fafafa"));

                    ll_4layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {
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

                    PreviousQuesAnswertype=alertList.get(j).getAnswerType();

                }


                ll = new LinearLayout(ctx);
                ll.setLayoutParams(layoutParamQuestion);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.setBackground(drawableMainQstn);
                ll.setPadding(20, 30, 20, 40);

                ll_4layout.addView(ll);

                /** This  if condition is to check the customised conditions for specific keywords  */

                if (alertList.get(j).keyword.equals("height_units") && FormID>firstForm && firstForm!=1 && FormID!=firstForm ) {
                    ArrayList<String> heightDetails= questionInteractor.womenHeight(uniqueId,formid);
                    String womanHeight=heightDetails.get(0);
                    if(womanHeight.equals("-1") || womanHeight.isEmpty()) {
                        alertList.get(j).answerType = "radio";
                    } else {
                        alertList.get(j).answerType = "int";
                    }
                }
                else if (alertList.get(j).keyword.equals("tt_1_dose_given")) {
                    if( prevChildAge>3 && prevChildAge!=-1){
                        ll_4layout.removeView(ll);
                        Log.d("TTDose","the woman needs to be given TT booster since her previous child is "+prevChildAge+"");
                    }
                    else if(FormID>firstForm && firstForm!=1 && FormID!=firstForm){
                        if(TT1Dose!=null) {
                            if (TT1Dose.equals("tt_1_given_yes") && FormID > firstForm && firstForm != 1 && FormID != firstForm)
                                ll_4layout.removeView(ll);
                        }
                    }
                }

                else if (alertList.get(j).keyword.equals("tt_2_dose_given")) {
                    if(TT1Dose==null || TT1Dose.equals("tt_1_given_no") && firstForm!=1 && FormID==firstForm){
                        ll_4layout.removeView(ll);
                    }
                    else if (TT1Dose.equals("tt_1_given_yes") && TT2Dose.equals("tt_2_given_yes")) {
                        Log.d("TTDose","the woman has given both TT1 and TT2 dose");
                        ll_4layout.removeView(ll);
                        validationlist.remove(alertList.get(j).keyword);
                        NextButtonvalidationlist.remove(alertList.get(j).keyword);
                    }
                    else if (TT1Dose.equals("tt_1_given_yes") &&  TT2Dose.equals("tt_2_given_no")) {
                        if( prevChildAge<3 && prevChildAge!=-1){
                            ll_4layout.removeView(ll);
                            Log.d("TTDose","the woman needs to be given TT booster since her previous child is "+prevChildAge+"");
                        }
                        else if (FormID > firstForm && firstForm != 1 && FormID != firstForm) {

                            /**to display TTDose2 question after 30 days of TTDose1 is given*/
                            Log.d("TTDose ","tt1date" + TTDose1Date);
                            DateFormat format= new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                            Date todayDate = new Date();
                            String currentDate = format.format(todayDate);
                            String newDate="";
                            Date newDateToCompare = null;
                            try {
                                Date date=format.parse(TTDose1Date);
                                Calendar calendar= Calendar.getInstance();
                                calendar.setTime(date);
                                calendar.add(Calendar.DAY_OF_MONTH,30);
                                newDate = format.format(calendar.getTime());
                                Log.d("TTDose ","tt2date" + newDate);
                                newDateToCompare=format.parse(newDate);
                                todayDate=format.parse(currentDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (!newDateToCompare.before(todayDate))
                            {
                                Log.d("TTDose ","tt2 should not be given" + newDate+" > "+todayDate);
                                if (TT1Dose.equals("tt_1_given_yes") && TT2Dose.equals("tt_2_given_yes")
                                        && FormID > firstForm && firstForm != 1 && FormID != firstForm) {
                                    ll_4layout.removeView(ll);
                                }
                            }
                            else{
                                Log.d("TTDose ","tt2 should be given" + newDate+" < "+todayDate);
                            }
                        }
                    }

                }
                else if(alertList.get(j).keyword.equals("tt_booster_given")){
                    //either if woman does not have child(-1) or her child's age is below 3 years
                    if(prevChildAge==-1 || prevChildAge<3){
                        Log.d("TTDose ", "TTBooster : either woman does not have prev child or the child's age is below 3 years ");
                        ll_4layout.removeView(ll);
                    }
                    //if woman has given TT1 but not TT2 yet
                    else if( TT1Dose!=null && TT2Dose!=null && prevChildAge>3 ) {
                        if(TT1Dose.equals("tt_1_given_yes") && TT2Dose.equals("tt_2_given_no")){
                            Log.d("TTDose ", "TTBooster : woman has not recieved TT2 yet");
                            ll_4layout.removeView(ll);
                        }
                        //if woman has given both TT1 and TT2 but her child's age is below 3 years then she does not need to get TT booster
                        else if (TT1Dose.equals("tt_1_given_yes") && TT2Dose.equals("tt_2_given_yes") && prevChildAge<3) {
                            ll_4layout.removeView(ll);
                            Log.d("TTDose ", "TTBooster : woman has already given TT1 and TT2 dose.");
                        }
                    }
                }



                switch(alertList.get(j).getAnswerType())
                {
                    case "text":
                        ll=createEdittext(j,alertList.get(j).getQuestionText(),alertList.get(j).getFormid(),alertList.get(j).getSetid(),alertList.get(j).getKeyword(),alertList.get(j).getValidationfield(),alertList.get(j).getMessages(),alertList.get(j).getDisplayCondition(),scroll.getId(),alertList.get(j).getOrientation(),alertList.get(j).getLengthmax());
                        break;

                    case "date":
                        ll=createDate(j, alertList.get(j).getQuestionText(), alertList.get(j).getFormid(), alertList.get(j).getSetid(), alertList.get(j).getKeyword(), alertList.get(j).getValidationfield(),alertList.get(j).getMessages(),alertList.get(j).getDisplayCondition(),scroll.getId(),alertList.get(j).getOrientation());
                        break;

                    case "time":
                        ll=createTime(j, alertList.get(j).getQuestionText(), alertList.get(j).getFormid(), alertList.get(j).getSetid(), alertList.get(j).getKeyword(), alertList.get(j).getValidationfield(),alertList.get(j).getMessages(),alertList.get(j).getDisplayCondition(),scroll.getId(),alertList.get(j).getOrientation());
                        break;

                    case "int":
                        ll = createInt(alertList.get(j).getAnswerType(), alertList.get(j).getQuestionText(),alertList.get(j).getFormid(),alertList.get(j).getSetid(), alertList.get(j).getKeyword(), alertList.get(j).getValidationfield(), alertList.get(j).getValidationCondition(), alertList.get(j).getValidationengmsg(), alertList.get(j).getLengthmin(), alertList.get(j).getLengthmax(), alertList.get(j).getLengthvalidationmsg(), alertList.get(j).getRangemin(), alertList.get(j).getRangemax(), alertList.get(j).getRangevalidationmsg(), alertList.get(j).getHighrisk_range(), alertList.get(j).getHighrisk_lang(), alertList.get(j).getReferral_range(), alertList.get(j).getReferral_lang(), alertList.get(j).getCounselling_lang(),alertList.get(j).getMessages(),alertList.get(j).getDisplayCondition(),scroll.getId(),alertList.get(j).getOrientation());
                        break;

                    case "float":
                        ll = createInt(alertList.get(j).getAnswerType(), alertList.get(j).getQuestionText(),alertList.get(j).getFormid(),alertList.get(j).getSetid(), alertList.get(j).getKeyword(), alertList.get(j).getValidationfield(), alertList.get(j).getValidationCondition(), alertList.get(j).getValidationengmsg(), alertList.get(j).getLengthmin(), alertList.get(j).getLengthmax(), alertList.get(j).getLengthvalidationmsg(), alertList.get(j).getRangemin(), alertList.get(j).getRangemax(), alertList.get(j).getRangevalidationmsg(), alertList.get(j).getHighrisk_range(), alertList.get(j).getHighrisk_lang(), alertList.get(j).getReferral_range(), alertList.get(j).getReferral_lang(), alertList.get(j).getCounselling_lang(),alertList.get(j).getMessages(),alertList.get(j).getDisplayCondition(),scroll.getId(),alertList.get(j).getOrientation());
                        break;

                    case "radio":
                        ll = createRadio(j, alertList.get(j).getQuesid(), alertList.get(j).getQuestionText(), alertList.get(j).getSetid(), alertList.get(j).getKeyword(), alertList.get(j).getValidationfield(), alertList.get(j).getFormid(), alertList.get(j).getMessages(), alertList.get(j).getDisplayCondition(), scroll.getId(), alertList.get(j).getOrientation(), alertList.get(j).getAvoidRepetition());
                        break;

                    case "select":
                        ll=createCheckbox(0, alertList.get(j).getQuesid(),alertList.get(j).getFormid(),alertList.get(j).getSetid(), alertList.get(j).getQuestionText(), alertList.get(j).getKeyword(), alertList.get(j).getValidationfield(),alertList.get(j).getMessages(),alertList.get(j).getDisplayCondition(),scroll.getId(),alertList.get(j).getOrientation());
                        break;

                    case "video":
                        ll=createVideo(j, alertList.get(j).getQuestionText(), alertList.get(j).getKeyword(),scroll.getId());
                        break;

                    case "label":
                        ll = createLabel(j, alertList.get(j).getQuestionText(), alertList.get(j).getFormid(), alertList.get(j).getKeyword(), alertList.get(j).getValidationfield(), alertList.get(j).getValidationCondition(), alertList.get(j).getValidationengmsg(),alertList.get(j).getDisplayCondition(),scroll.getId(),alertList.get(j).getCalculations(),alertList.get(j).getSetid());
                        break;

                    case "sublabel":
                        ll = createSubLabel(j, alertList.get(j).getQuestionText(), alertList.get(j).getFormid(), alertList.get(j).getKeyword(), alertList.get(j).getValidationfield(), alertList.get(j).getValidationCondition(), alertList.get(j).getValidationengmsg(),alertList.get(j).getDisplayCondition(),scroll.getId());
                        break;

                    case "capturephoto":

                        ll = createCapturePhoto(j, alertList.get(j).getQuestionText(), alertList.get(j).getFormid(), alertList.get(j).getSetid(), alertList.get(j).getKeyword(), alertList.get(j).getValidationfield(), alertList.get(j).getValidationCondition(), alertList.get(j).getValidationengmsg(), alertList.get(j).getMessages(), alertList.get(j).getDisplayCondition());
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

            if (scrollId.isEmpty()) {
                progressDialog.dismiss();

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(AncVisits.this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle(getString(R.string.title_data_not_found));
                builder.setMessage(getString(R.string.sync_forms_message));
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                        finish();
                    }
                });
                builder.show();
            } else {
                scroll_temp = (ScrollView) findViewById(Integer.parseInt(String.valueOf(scrollId.get(scrollcounter))));
                scroll_temp.setVisibility(View.VISIBLE);
                previous.setVisibility(View.GONE);

                progress.setMax(layoutcounter);
                progress.setProgress(1);

                textViewTotalPgCount = (TextView) findViewById(R.id.text_total_count);
                pageCountText = "/" + layoutcounter;
                textViewTotalPgCount.setText(1 + pageCountText);

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

                }
            });

            progressDialog.dismiss();
        }

    }

}
