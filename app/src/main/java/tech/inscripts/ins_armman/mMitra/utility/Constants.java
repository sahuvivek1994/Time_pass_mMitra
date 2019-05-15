package tech.inscripts.ins_armman.mMitra.utility;



/**
 * Created by lenovo on 24/10/17.
 */

public interface Constants {

    String DATE_FORMAT = "dd/MM/yyyy";
    String DATE_FORMAT_DOB = "dd-MM-yyyy";

    String QUERY_ARGS_DISTINCT = "distinct";
    String QUERY_ARGS_TABLE_NAME = "tablename";
    String QUERY_ARGS_PROJECTION = "projection";
    String QUERY_ARGS_SELECTION = "selection";
    String QUERY_ARGS_SELECTION_ARGS = "selectionargs";
    String QUERY_ARGS_GROUP_BY = "groupby";
    String QUERY_ARGS_HAVING = "having";
    String QUERY_ARGS_ORDER_BY = "orderby";
    String QUERY_ARGS_LIMIT = "limit";
    String QUERY_ARGS_VALUES = "values";
    String RAW_QUERY = "rawQuery";
    String QUERY_TYPE = "query_type";
    String FORM_SYNC_LIMIT = "10";

    String delimeter="#";
    String UNIQUE_MEMBER_ID_SEPERATOR = ".";

    String HASH_ITEM_FORM = "form";
    String HASH_ITEM_HELP_MANUAL = "help";
    String DEFAULT_HASH = "abc";
    String UNIQUE_ID = "unique_id";
    String MOTHER_NAME = "mother_name";
    String AGE_IN_MONTHS = "age_in_months";
    String VILLAGE_NAME = "village_name";
    String NAME = "name";

    // Form syncing keys
    String FORM_ID = "form_id";
    String QUESTION_KEYWORD = "q_keyword";
    String ANSWER = "ans_keyword";
    String CREATED_ON = "created_on";
    String DATA = "data";
    String REFERRAL = "referral";
    String TYPE = "type";
    String BENEFICIARIES = "beneficiaries";
    String STATUS = "status";
    String LMP_DATE = "lmp_date";

    String DATA_MODE = "date_mode";
    String DATA_MODE_CREATE = "date_mode_create";
    String DATA_MODE_EDIT = "date_mode_edit";

    String AWC_AREA = "awc_area";
    String AWC_AREA_RESIDENT = "resident_of_awc_yes";
    String AWC_AREA_MIGRANT = "resident_of_awc_no";
    String AWC_AREA_ALL = "";

    String CHILD_OTHER_FILTER = "child_other_filter";
    String CHILD_OTHER_FILTER_ALL = "";
    String WEIGHT_TYPE_HIGH_WEIGHT = "high_weight";
    String WEIGHT_TYPE_NORMAL_WEIGHT = "normal_weight";
    String WEIGHT_TYPE_LOW_WEIGHT = "low_weight";
    String WEIGHT_TYPE_SEVERELY_LOW_WEIGHT = "severely_low_weight";
    String DUE_VISIT_YES = "due_visit_yes";
    String DUE_VISIT_NO = "due_visit_no";
    String SAM = "sam";
    String MAM = "mam";
    String DAYS_IN_3_YEARS = String.valueOf(DateUtility.getNoOfDaysInYearsPassed(3));
    String NAME_SEARCH = "name_search";
    String CHILD_DATA_CHANGED = "child_data_changed";
    String EXTRA_CHILD_DOB = "dob";
    String EXTRA_CHILD_GENDER = "gender";
    String EXTRA_CHILD_NAME = "extra_child_name";
    String EXTRA_FROM = "from";
    String EXTRA_CHILD_MEMBER_ID = "child_member_id";
    String EXTRA_TIMESTAMP_ID = "unique_timestamp_id";
    String EXTRA_MEMBER_ID = "memberId";
    String EXTRA_SELECTED_MONTH = "month";
    String EXTRA_SELECTED_YEAR = "year";
    String EXTRA_HEIGHT = "height";
    String EXTRA_WEIGHT = "weight";
    String EXTRA_MUAC = "muac";
    String EXTRA_AWW_ID = "anganwadi_id";
    String LOGIN_RESPONSE_ID = "login_response_id";
    String LOGIN_TYPE = "login_type";
    String EXTRA_CREATED_ON = "created_on";
    String FROM = "from";
    String FROM_GROWTH_GRAPH_ACTIVTY = "from_growth_graph_activty";

    String DAYS_IN_6_YEARS = String.valueOf(DateUtility.getNoOfDaysInYearsPassed(6));//"2160";
    String AGE_IN_DAYS = "age_in_days";

    String MALE = "male";
    String MALE_CONSTANT = "M";
    String FEMALE_CONSTANT = "F";
    String TRANSGENDER_CONSTANT = "T";


    String TYPE_VIDEO = "video";
    String TYPE_ANIMATION = "anim";
    String FILE_TYPE = "file_type";

    String HIGHRISK_ONE = "highrisk_one";
    String HIGHRISK_TWO = "highrisk_two";
    String HIGHRISK_THREE = "highrisk_three";

    String SELECT = "select";
    String REFERRED_TO_PHC = "phc";
    String REFERRED_TO_PUBLIC_CLINIC = "public_clinic";
    String REFERRED_TO_PRIVATE_CLINIC = "private_clinic";


    int FORM_DOWNLOAD_LIMIT = 10;

    int TOTAL_FORM_COUNT = 22;

    int CHILD_REGISTRATION_VISIT_ID = 0;
    int WOMAN_REGISTRATION_VISIT_ID = 1;
    int ANC_ONE_VISIT_ID = 2;
    int ANC_TWO_VISIT_ID = 3;
    int ANC_THREE_VISIT_ID = 4;
    int ANC_FOUR_VISIT_ID = 5;
    int ANC_FIVE_VISIT_ID = 6;
    int ANC_SIX_VISIT_ID = 7;
    int DELIVERY_FORM_ID = 8;
    int WOMAN_CLOSURE_FORM_ID = 9;
    int CC_FIRST_VISIT_ID = 10;
    int CC_SECOND_VISIT_ID = 11;
    int CC_THIRD_VISIT_ID = 12;
    int CC_FOURTH_VISIT_ID = 13;
    int CC_FIFTH_VISIT_ID = 14;
    int CC_SIXTH_VISIT_ID = 15;
    int CC_SEVENTH_VISIT_ID = 16;
    int CC_EIGHTH_VISIT_ID = 17;
    int CC_NINETH_VISIT_ID = 18;
    int CC_TENTH_VISIT_ID = 19;
    int CC_ELEVENTH_VISIT_ID = 20;
    int CC_TWELTH_VISIT_ID = 21;
    int CHILD_CLOSURE_FORM_ID = 22;

    String PROFILE_IMAGE_NAME = "PROFILE_IMAGE";
    String CHILD_NAME = "CHILD_NAME";
    String COMPLETE_VISIT = "complete_status";
    String REFERRAL_PENDING = "referral_pending";
    String TOTAL_WOMEN = "total_women";
    String TOTAL_CHILD = "total_child";
    String TOTAL_VISITS = "total_visits";
    String INCOMPLETE_VISITS = "incomplete_visits";
    String TOTAL_REFERRAL = "TOTAL_REFERRAL";

    //Diff Error messages
    String CHILD_UNREGISTERED_ERROR = "Error: child unregistered";
    String MOTHER_DELIVERY_UNSYNCED_ERROR = "Error: mother delivery not synced";

    String AUTHENTICATION_FAILED = "AUTHENTICATION_FAILED";
    String RESPONSE = "response";
    String INVALID_DATA = "INVALID_DATA";
    String INVALID_IMEI = "INVALID_IMEI";

    //for wages
    String ANC_COUNT="anc_count";
    String PNC_COUNT="pnc_count";
    String REG_COUNT="reg_count";
    String BUTTON_CLICKED="buttonClicked";
    String BUTTON_GRAPH="graph";
    String BUTTON_WAGES="wages";
    String CHILD_STATUS="child_status"; // it is wages_status
 }
