package tech.inscripts.ins_armman.mMitra.data.database;

import android.os.Environment;

/**
 * Created by lenovo on 11/10/17.
 */

public final class DatabaseContract {
    public static final String DATABASE_NAME = "mMitra_Cohort.sr";
    public static final int DATABASE_VERSION = 1;
    public static final String DB_LOCATION = Environment.getExternalStorageDirectory() +"/mMitra_Cohort";
    public static final String TEXT_TYPE = " TEXT";
    public static final String BLOB_TYPE = " BLOB";
    public static final String INTEGER_TYPE = " INTEGER";
    public static final String COMMA_SEP = ",";
    public static final String NOT_NULL = " NOT NULL ";
    public static final String VIDEO_PATH = Environment.getExternalStorageDirectory() +"/videos/";
    public static final String USER_GUIDE_DIRECTORY = DB_LOCATION + "/User Guide";
    public static final String ANIMATION_DIRECTORY = DB_LOCATION + "/Animations";
    public static final String M_MITRA_CALLS_DIRECTORY = DB_LOCATION + "/mMitra Calls";

    public static final class LoginTable {
        public static final String TABLE_NAME = "login";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_NAME = "firstName";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_PHONE_NO = "phone_no";


        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "( " +
                COLUMN_USER_ID + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_USERNAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_PASSWORD + TEXT_TYPE + COMMA_SEP +
                COLUMN_PHONE_NO + TEXT_TYPE + COMMA_SEP + ")";
    }

    public static final class FormDetailsTable {
        public static final String TABLE_NAME = "form_details";
        public static final String COLUMN_FORM_ID = "form_id";
        public static final String COLUMN_VISIT_NAME = "visit_name";
        public static final String COLUMN_FROM_WEEKS = "from_weeks";
        public static final String COLUMN_TO_WEEKS = "to_weeks";
        public static final String COLUMN_ORDER_ID = "order_id";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "( " +
                COLUMN_FORM_ID + TEXT_TYPE + COMMA_SEP +
                COLUMN_VISIT_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_FROM_WEEKS + INTEGER_TYPE + COMMA_SEP +
                COLUMN_TO_WEEKS + INTEGER_TYPE + COMMA_SEP +
                COLUMN_ORDER_ID + TEXT_TYPE + ")";
    }

    public static final class MainQuestionsTable {
        public static final String TABLE_NAME = "main_questions";
        public static final String COLUMN_FORM_ID = "form_id";
        public static final String COLUMN_QUESTION_ID = "question_id";
        public static final String COLUMN_KEYWORD = "keyword";
        public static final String COLUMN_QUESTION_TYPE = "question_type";
        public static final String COLUMN_QUESTION_LABEL = "question_label";
        public static final String COLUMN_MESSAGES = "messages";
        public static final String COLUMN_CALCULATION = "calculation";
        public static final String COLUMN_ORIENTATION = "orientation";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                COLUMN_FORM_ID + INTEGER_TYPE + COMMA_SEP +
                COLUMN_QUESTION_ID + INTEGER_TYPE + COMMA_SEP +
                COLUMN_KEYWORD + TEXT_TYPE + COMMA_SEP +
                COLUMN_QUESTION_TYPE + TEXT_TYPE + COMMA_SEP +
                COLUMN_QUESTION_LABEL + TEXT_TYPE + COMMA_SEP +
                COLUMN_MESSAGES + TEXT_TYPE + COMMA_SEP +
                COLUMN_CALCULATION + TEXT_TYPE + COMMA_SEP +
                COLUMN_ORIENTATION + INTEGER_TYPE +
                ")";
    }

    public static final class DependentQuestionsTable {
        public static final String TABLE_NAME = "dependent_questions";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_MAIN_QUESTION_OPTION_KEYWORD = "option_keyword";
        public static final String COLUMN_QUESTION_ID = "question_id";
        public static final String COLUMN_FORM_ID = "form_id";
        public static final String COLUMN_KEYWORD = "keyword";
        public static final String COLUMN_QUESTION_TYPE = "question_type";
        public static final String COLUMN_QUESTION_LABEL = "question_label";
        public static final String COLUMN_MESSAGES = "messages";
        public static final String COLUMN_VALIDATIONS = "validations";
        public static final String COLUMN_ORIENTATION = "orientation";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                COLUMN_MAIN_QUESTION_OPTION_KEYWORD + TEXT_TYPE + COMMA_SEP +
                COLUMN_QUESTION_ID + INTEGER_TYPE + COMMA_SEP +
                COLUMN_FORM_ID + INTEGER_TYPE + COMMA_SEP +
                COLUMN_KEYWORD + TEXT_TYPE + COMMA_SEP +
                COLUMN_QUESTION_TYPE + TEXT_TYPE + COMMA_SEP +
                COLUMN_QUESTION_LABEL + TEXT_TYPE + COMMA_SEP +
                COLUMN_MESSAGES + TEXT_TYPE + COMMA_SEP +
                COLUMN_VALIDATIONS + TEXT_TYPE + COMMA_SEP +
                COLUMN_ORIENTATION + INTEGER_TYPE +
                ")";
    }

    public static final class QuestionOptionsTable {
        public static final String TABLE_NAME = "question_options";
        public static final String COLUMN_FORM_ID = "form_id";
        public static final String COLUMN_QUESTION_ID = "question_id";
        public static final String COLUMN_KEYWORD = "keyword";
        public static final String COLUMN_OPTION_LABEL = "option_label";
        public static final String COLUMN_MESSAGES = "messages";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                COLUMN_FORM_ID + TEXT_TYPE + COMMA_SEP +
                COLUMN_QUESTION_ID + INTEGER_TYPE + COMMA_SEP +
                COLUMN_KEYWORD + TEXT_TYPE + COMMA_SEP +
                COLUMN_OPTION_LABEL + TEXT_TYPE + COMMA_SEP +
                COLUMN_MESSAGES + TEXT_TYPE +
                ")";
    }

    public static final class ValidationsTable {
        public static final String TABLE_NAME = "validations";
        public static final String COLUMN_FORM_ID = "form_id";
        public static final String COLUMN_QUESTION_ID = "question_id";
        public static final String COLUMN_COMPULSORY_QUESTIONS = "compulsory_question";
        public static final String COLUMN_CUSTOM_VALIDATION_CON = "custom_validation_cond";
        public static final String COLUMN_CUSTOM_VALIDATION_LANG = "custom_validation_lang";
        public static final String COLUMN_MIN_RANGE = "min_range";
        public static final String COLUMN_MAX_RANGE = "max_range";
        public static final String COLUMN_RANGE_ERROR_MESSAGE = "range_error_msg";
        public static final String COLUMN_MIN_LENGTH = "min_length";
        public static final String COLUMN_MAX_LENGTH = "max_length";
        public static final String COLUMN_LENGTH_ERROR_MESSAGE = "length_error_msg";
        public static final String COLUMN_DISPLAY_CONDITION = "display_condition";
        public static final String COLUMN_CALCULATIONS = "calculations";
        public static final String COLUMN_AVOID_REPETETIONS = "avoid_repetition";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                COLUMN_FORM_ID + INTEGER_TYPE + COMMA_SEP +
                COLUMN_QUESTION_ID + INTEGER_TYPE + " PRIMARY KEY " +COMMA_SEP +
                COLUMN_COMPULSORY_QUESTIONS + TEXT_TYPE + COMMA_SEP +
                COLUMN_CUSTOM_VALIDATION_CON + TEXT_TYPE + COMMA_SEP +
                COLUMN_CUSTOM_VALIDATION_LANG + TEXT_TYPE + COMMA_SEP +
                COLUMN_MIN_RANGE + TEXT_TYPE + COMMA_SEP +
                COLUMN_MAX_RANGE + TEXT_TYPE + COMMA_SEP +
                COLUMN_RANGE_ERROR_MESSAGE + TEXT_TYPE + COMMA_SEP +
                COLUMN_MIN_LENGTH + TEXT_TYPE + COMMA_SEP +
                COLUMN_MAX_LENGTH + TEXT_TYPE + COMMA_SEP +
                COLUMN_LENGTH_ERROR_MESSAGE + TEXT_TYPE + COMMA_SEP +
                COLUMN_DISPLAY_CONDITION + TEXT_TYPE + COMMA_SEP +
                COLUMN_CALCULATIONS + TEXT_TYPE + COMMA_SEP +
                COLUMN_AVOID_REPETETIONS + TEXT_TYPE +
                ")";
    }

    public static final class HashTable {
        public static final String TABLE_NAME = "hash_table";
        public static final String COLUMN_ITEM = "item";
        public static final String COLUMN_HASH = "hash";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "( " +
                COLUMN_ITEM + TEXT_TYPE + COMMA_SEP +
                COLUMN_HASH + TEXT_TYPE + ")";
    }

    public static final class RegistrationTable {
        public static final String TABLE_NAME = "registration";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_UNIQUE_ID = "unique_id";
        public static final String COLUMN_NAME = "name";
       // public static final String COLUMN_MNAME = "middle_name";
       // public static final String COLUMN_LNAME = "last_name";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_DOB = "dob";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_MOBILE_NO = "phone_no";
        public static final String COLUMN_EDUCATION = "education";
        public static final String COLUMN_LMP_DATE = "lmp_date";
        public static final String COLUMN_MARITAL_STATUS = "marital_status";
        public static final String COLUMN_CREATED_ON = "created_on";
        public static final String COLUMN_CLOSE_STATUS = "close_status";
        public static final String COLUMN_CLOSE_DATE = "close_date";
        public static final String COLUMN_CLOSE_REASON = "close_reason";
        public static final String COLUMN_EXPIRED_DATE = "expired_date";
        public static final String COLUMN_EXPIRED_REASON = "expired_reason";
        public static final String COLUMN_SYNC_STATUS= "sync_status";
        public static final String COLUMN_REGISTRATION_STATUS= "registration_status";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_UPDATE_IMAGE_STATUS = "update_image_status";
        /*COLUMN_FAILURE_STATUS and COLUMN_FAILURE_REASON column is to maintain
            dataSource sync failure status and reason
         */
        public static final String COLUMN_FAILURE_STATUS = "failure_status";
        public static final String COLUMN_FAILURE_REASON = "failure_reason";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                COLUMN_UNIQUE_ID + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
               // COLUMN_MNAME + TEXT_TYPE + COMMA_SEP +
              //  COLUMN_LNAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_ADDRESS + TEXT_TYPE + COMMA_SEP +
                COLUMN_DOB + TEXT_TYPE + COMMA_SEP +
                COLUMN_AGE + TEXT_TYPE + COMMA_SEP +
                COLUMN_MOBILE_NO + TEXT_TYPE + COMMA_SEP +
                COLUMN_EDUCATION + TEXT_TYPE + COMMA_SEP +
                COLUMN_LMP_DATE + TEXT_TYPE + COMMA_SEP +
                COLUMN_MARITAL_STATUS + TEXT_TYPE + COMMA_SEP +
                COLUMN_CREATED_ON + TEXT_TYPE + COMMA_SEP +
                COLUMN_CLOSE_STATUS + INTEGER_TYPE + " DEFAULT 0" + COMMA_SEP +
                COLUMN_CLOSE_DATE + TEXT_TYPE + COMMA_SEP +
                COLUMN_CLOSE_REASON + TEXT_TYPE + COMMA_SEP +
                COLUMN_EXPIRED_DATE + TEXT_TYPE + COMMA_SEP +
                COLUMN_EXPIRED_REASON + TEXT_TYPE + COMMA_SEP +
                COLUMN_SYNC_STATUS + INTEGER_TYPE + " DEFAULT 0" + COMMA_SEP +
                COLUMN_REGISTRATION_STATUS + INTEGER_TYPE + " DEFAULT 0" + COMMA_SEP +
                COLUMN_IMAGE + BLOB_TYPE + COMMA_SEP +
                COLUMN_UPDATE_IMAGE_STATUS + INTEGER_TYPE + " DEFAULT 1" + COMMA_SEP +
                COLUMN_FAILURE_STATUS + INTEGER_TYPE + " DEFAULT 0 " + COMMA_SEP +
                COLUMN_FAILURE_REASON + TEXT_TYPE +
                ")";
    }



    public static final class QuestionAnswerTable {
        public static final String TABLE_NAME = "question_answers";
        public static final String COLUMN_REFERENCE_ID = "reference_id";
        public static final String COLUMN_UNIQUE_ID = "unique_id";
        public static final String COLUMN_FORM_ID = "form_id";
        public static final String COLUMN_QUESTION_KEYWORD = "question_keyword";
        public static final String COLUMN_ANSWER_KEYWORD = "answer_keyword";
        public static final String COLUMN_CREATED_ON = "created_on";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                COLUMN_REFERENCE_ID + INTEGER_TYPE + COMMA_SEP +
                COLUMN_UNIQUE_ID + TEXT_TYPE + COMMA_SEP +
                COLUMN_FORM_ID + INTEGER_TYPE + COMMA_SEP +
                COLUMN_QUESTION_KEYWORD + TEXT_TYPE + COMMA_SEP +
                COLUMN_ANSWER_KEYWORD + TEXT_TYPE + COMMA_SEP +
                COLUMN_CREATED_ON + TEXT_TYPE +
                ")";
    }

    public static final class FilledFormStatusTable {
        public static final String TABLE_NAME = "filled_forms_status";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_UNIQUE_ID = "unique_id";
        public static final String COLUMN_FORM_ID = "form_id";
        public static final String COLUMN_FORM_COMPLETION_STATUS = "form_completion_status";
        public static final String COLUMN_FORM_SYNC_STATUS = "form_sync_status";
        public static final String COLUMN_CREATED_ON = "created_on";
       // public static final String COLUMN_WAGES_STATUS = "wages_status";
        /*COLUMN_FAILURE_STATUS and COLUMN_FAILURE_REASON column is to maintain
            dataSource sync failure status and reason
         */
        public static final String COLUMN_FAILURE_STATUS = "failure_status";
        public static final String COLUMN_FAILURE_REASON = "failure_reason";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                COLUMN_UNIQUE_ID + TEXT_TYPE + COMMA_SEP +
                COLUMN_FORM_ID + INTEGER_TYPE + COMMA_SEP +
                COLUMN_FORM_COMPLETION_STATUS + INTEGER_TYPE + COMMA_SEP +
                COLUMN_FORM_SYNC_STATUS + INTEGER_TYPE + COMMA_SEP +
                COLUMN_CREATED_ON + TEXT_TYPE + COMMA_SEP +
                COLUMN_FAILURE_STATUS + INTEGER_TYPE + " DEFAULT 0 " + COMMA_SEP +
                COLUMN_FAILURE_REASON + TEXT_TYPE +
                ")";
    }


    public static final class FaqTable {
        public static final String TABLE_NAME = "faq";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER = "answer";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                COLUMN_QUESTION + TEXT_TYPE + COMMA_SEP +
                COLUMN_ANSWER + TEXT_TYPE +
                ")";
    }
    }
