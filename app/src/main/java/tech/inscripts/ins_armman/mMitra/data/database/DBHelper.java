package tech.inscripts.ins_armman.mMitra.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import tech.inscripts.ins_armman.mMitra.utility.Utility;

import java.io.File;

import static tech.inscripts.ins_armman.mMitra.data.database.DatabaseContract.*;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = DBHelper.class.getSimpleName();

    Utility utility = new Utility();

    public DBHelper(Context context) {
        super(context, DB_LOCATION
                + File.separator + DATABASE_NAME, null, DATABASE_VERSION);

        File fileGuide = new File(USER_GUIDE_DIRECTORY);
        if (!fileGuide.exists()) fileGuide.mkdirs();

        /*File fileAnim = new File(ANIMATION_DIRECTORY);
        if (!fileAnim.exists()) fileAnim.mkdirs();

        File fileCalls = new File(M_MITRA_CALLS_DIRECTORY);
        if (!fileCalls.exists()) fileCalls.mkdirs();*/
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LoginTable.CREATE_TABLE);
        db.execSQL(VillageTable.CREATE_TABLE);
        db.execSQL(FormDetailsTable.CREATE_TABLE);
        db.execSQL(MainQuestionsTable.CREATE_TABLE);
        db.execSQL(DependentQuestionsTable.CREATE_TABLE);
        db.execSQL(QuestionOptionsTable.CREATE_TABLE);
        db.execSQL(ValidationsTable.CREATE_TABLE);
        db.execSQL(HashTable.CREATE_TABLE);
        db.execSQL(RegistrationTable.CREATE_TABLE);
        db.execSQL(ReferralTable.CREATE_TABLE);
        db.execSQL(QuestionAnswerTable.CREATE_TABLE);
        db.execSQL(FilledFormStatusTable.CREATE_TABLE);
        db.execSQL(ChildGrowthTable.CREATE_TABLE);
        db.execSQL(VideoAnimationTable.CREATE_TABLE);
        db.execSQL(FaqTable.CREATE_TABLE);
        db.execSQL(mMitraCallsTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion <= 2) {
            Log.i(TAG, "onUpgrade: version: 2");
            db.execSQL("DROP TABLE IF EXISTS " + FilledFormStatusTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + ChildGrowthTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + QuestionAnswerTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + ReferralTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + RegistrationTable.TABLE_NAME);
            onCreate(db);
        }

        if (oldVersion < 3)
            if (!isColumnExist(db, RegistrationTable.TABLE_NAME, RegistrationTable.COLUMN_UPDATE_IMAGE_STATUS))
                db.execSQL("ALTER TABLE " + RegistrationTable.TABLE_NAME +
                        " ADD COLUMN " + RegistrationTable.COLUMN_UPDATE_IMAGE_STATUS + TEXT_TYPE + " DEFAULT 1");

        if (oldVersion < 4)
            upgradeVersion4(db);


        if (oldVersion < 5)
            upgradeVersion5(db);
        if(oldVersion<=7)
            upgradeVersion7(db);
    }

    private void upgradeVersion4(SQLiteDatabase db) {
        db.beginTransaction();

        String query = " SELECT * FROM "
                + RegistrationTable.TABLE_NAME
                + " WHERE "
                + RegistrationTable.COLUMN_CLOSE_STATUS + " = 0 "
                + " AND "
                + RegistrationTable.COLUMN_CLOSE_DATE + " IS NOT NULL "
                + " AND "
                + RegistrationTable.COLUMN_CLOSE_REASON + " IS NOT NULL ";


        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {

            do {
                String uniqueId = cursor.getString(
                        cursor.getColumnIndex(RegistrationTable.COLUMN_UNIQUE_ID));

                ContentValues values = new ContentValues();
                values.put(RegistrationTable.COLUMN_CLOSE_STATUS, 1);

                db.update(RegistrationTable.TABLE_NAME,
                        values,
                        RegistrationTable.COLUMN_UNIQUE_ID + " =? ",
                        new String[]{uniqueId});

            } while (cursor.moveToNext());
            cursor.close();
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        if (!isColumnExist(db, RegistrationTable.TABLE_NAME, RegistrationTable.COLUMN_FAILURE_STATUS)) {
            db.execSQL("ALTER TABLE " + RegistrationTable.TABLE_NAME +
                    " ADD COLUMN " + RegistrationTable.COLUMN_FAILURE_STATUS + INTEGER_TYPE + " DEFAULT 0");
        }

        if (!isColumnExist(db, RegistrationTable.TABLE_NAME, RegistrationTable.COLUMN_FAILURE_REASON)) {
            db.execSQL("ALTER TABLE " + RegistrationTable.TABLE_NAME +
                    " ADD COLUMN " + RegistrationTable.COLUMN_FAILURE_REASON + TEXT_TYPE);
        }

        if (!isColumnExist(db, FilledFormStatusTable.TABLE_NAME, FilledFormStatusTable.COLUMN_FAILURE_STATUS)) {
            db.execSQL("ALTER TABLE " + FilledFormStatusTable.TABLE_NAME +
                    " ADD COLUMN " + FilledFormStatusTable.COLUMN_FAILURE_STATUS + INTEGER_TYPE + " DEFAULT 0");
        }

        if (!isColumnExist(db, FilledFormStatusTable.TABLE_NAME, FilledFormStatusTable.COLUMN_FAILURE_REASON)) {
            db.execSQL("ALTER TABLE " + FilledFormStatusTable.TABLE_NAME +
                    " ADD COLUMN " + FilledFormStatusTable.COLUMN_FAILURE_REASON + TEXT_TYPE);
        }

        if (!isColumnExist(db, ReferralTable.TABLE_NAME, ReferralTable.COLUMN_FAILURE_STATUS)) {
            db.execSQL("ALTER TABLE " + ReferralTable.TABLE_NAME +
                    " ADD COLUMN " + ReferralTable.COLUMN_FAILURE_STATUS + INTEGER_TYPE + " DEFAULT 0");
        }

        if (!isColumnExist(db, ReferralTable.TABLE_NAME, ReferralTable.COLUMN_FAILURE_REASON)) {
            db.execSQL("ALTER TABLE " + ReferralTable.TABLE_NAME +
                    " ADD COLUMN " + ReferralTable.COLUMN_FAILURE_REASON + TEXT_TYPE);
        }

        if (!isColumnExist(db, ChildGrowthTable.TABLE_NAME, ChildGrowthTable.COLUMN_FAILURE_STATUS)) {
            db.execSQL("ALTER TABLE " + ChildGrowthTable.TABLE_NAME +
                    " ADD COLUMN " + ChildGrowthTable.COLUMN_FAILURE_STATUS + INTEGER_TYPE + " DEFAULT 0");
        }

        if (!isColumnExist(db, ChildGrowthTable.TABLE_NAME, ChildGrowthTable.COLUMN_FAILURE_REASON)) {
            db.execSQL("ALTER TABLE " + ChildGrowthTable.TABLE_NAME +
                    " ADD COLUMN " + ChildGrowthTable.COLUMN_FAILURE_REASON + TEXT_TYPE);
        }

        if (!isColumnExist(db, RegistrationTable.TABLE_NAME, RegistrationTable.COLUMN_UPDATE_IMAGE_STATUS))
            db.execSQL("ALTER TABLE " + RegistrationTable.TABLE_NAME +
                    " ADD COLUMN " + RegistrationTable.COLUMN_UPDATE_IMAGE_STATUS + TEXT_TYPE + " DEFAULT 1");

    }


    private boolean isColumnExist(SQLiteDatabase db, String tableName, String fieldName) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        int isColumnExist = cursor.getColumnIndex(fieldName);
        cursor.close();

        return isColumnExist >= 0;
    }


    private void upgradeVersion5(SQLiteDatabase db) {
        if (!isColumnExist(db, FormDetailsTable.TABLE_NAME, FormDetailsTable.COLUMN_ORDER_ID))
            db.execSQL("ALTER TABLE " + FormDetailsTable.TABLE_NAME +
                    " ADD COLUMN " + FormDetailsTable.COLUMN_ORDER_ID + TEXT_TYPE);

        ContentValues values = new ContentValues();
        values.put(HashTable.COLUMN_HASH, "111111111");

        db.update(HashTable.TABLE_NAME,
                values,
                HashTable.COLUMN_ITEM + " = ? ",
                new String[]{"form"});
    }
    private void upgradeVersion7(SQLiteDatabase db){
        if (!isColumnExist(db, FilledFormStatusTable.TABLE_NAME, FilledFormStatusTable.COLUMN_WAGES_STATUS))
            db.execSQL("ALTER TABLE " + FilledFormStatusTable.TABLE_NAME +
                    " ADD COLUMN " + FilledFormStatusTable.COLUMN_WAGES_STATUS + INTEGER_TYPE);
        db.execSQL("update filled_forms_status set wages_status=1 where wages_status is null");
    }

    public Cursor getIncompleteFormListList() {

        return utility.getDatabase().rawQuery("SELECT * FROM " +
                "(SELECT current.unique_id,current.form_id,reg.name, current.form_completion_status " +
                "FROM filled_forms_status AS current " +
                " JOIN registration AS reg on current.unique_id = reg.unique_id AND (reg.mother_id is null OR reg.mother_id = '') " +
                " AND current.unique_id NOT IN (SELECT unique_id FROM filled_forms_status WHERE form_id = 10 AND form_completion_status = 1))" +
                " GROUP BY unique_id", null);
    }
    public String fetchCount() {
        String status = null;

        String query = "SELECT COUNT(*) AS remaining FROM filled_forms_status WHERE form_sync_status = 0 AND form_completion_status = 1";

        Cursor cursor = utility.getDatabase().rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            status = cursor.getString(cursor.getColumnIndex("remaining"));
            cursor.close();
        } else {
            status = "0";
        }
        return status;
    }

    public Cursor fetchUserDetails() {
        String query = "SELECT name,phone_no FROM " + LoginTable.TABLE_NAME;
        return utility.getDatabase().rawQuery(query, null);
    }

    public Cursor getcompleteFormListList() {

        return utility.getDatabase().rawQuery("SELECT first_name,middle_name,last_name,unique_id from registration WHERE unique_id IN (SELECT unique_id FROM filled_forms_status WHERE form_completion_status = 1 )", null);
    }

    public Cursor getChildIdFromMotherId(String motherId) {
        return utility.getDatabase().rawQuery("SELECT first_name,middle_name,last_name,unique_id FROM " + RegistrationTable.TABLE_NAME + " WHERE mother_id ='" + motherId + "'", null);
    }

    public Cursor getuniqueIdFormId(String uniqueId) {
        return utility.getDatabase().rawQuery("SELECT max(form_id) as form_id FROM " + FilledFormStatusTable.TABLE_NAME + " WHERE unique_id = '" + uniqueId + "' AND form_completion_status = 1", null);
    }
    /*public Cursor getCompleteFormDetails(String unique_id, int form_id) {
        return utility.getDatabase().rawQuery("SELECT main_questions.question_label," +
                "question_options.option_label,question_answers.unique_id " +
                "FROM question_answers" +
                " JOIN main_questions" +
                " ON question_answers.question_keyword=main_questions.keyword " +
                "JOIN question_options" +
                " ON question_answers.answer_keyword=question_options.keyword " +
                "WHERE question_answers.unique_id='"+unique_id+"' " +
                "and question_answers.form_id="+form_id,null);
    }*/

    /**
     *form 6 contains question label but does'nt contain answer label so only
     main_questions and question_answers involved in query and not question_option
     * @param unique_id=child unique_id
     * @param form_id=child form_id
     * @return
     */
    public Cursor getForm6Details(String unique_id,int form_id){
        return utility.getDatabase().rawQuery("SELECT main_questions.question_label,question_answers.answer_keyword,question_answers.unique_id " +
                "FROM question_answers" +
                " JOIN main_questions ON question_answers.question_keyword=main_questions.keyword " +
                "WHERE question_answers.unique_id='"+unique_id+"' and question_answers.form_id="+form_id,null);
    }
    public Cursor getFormsList(String unique_id){
        //return utility.getDatabase().rawQuery("select visit_name,form_id from form_details group by(form_id) order by cast(form_id as int) asc",null);
        return utility.getDatabase().rawQuery("select form_id,visit_name from form_details \n" +
                "where form_id in(select form_id from filled_forms_status where form_completion_status=1 " +
                "and unique_id='"+ unique_id +"') order by cast(form_id as int) asc",null);
    }
    public Cursor getIncompleteFormList(String unique_id){
        return utility.getDatabase().rawQuery("select filled_forms_status.form_id,visit_name" +
                " from filled_forms_status join form_details on" +
                " filled_forms_status.form_id=form_details.form_id" +
                " where unique_id='"+unique_id+"' and form_completion_status=1" +
                " group by(filled_forms_status.form_id)",null);
    }
    public Cursor getCompleteFormDetails(String unique_id, int Form_id)
    {
        return utility.getDatabase().rawQuery( "select r.unique_id ,mq.question_label,qo.option_label,qa.answer_keyword,qa.question_keyword" +
                " from question_answers as qa" +
                " left join registration as r on r.unique_id=qa.unique_id" +
                " left join main_questions as mq on mq.keyword = qa.question_keyword" +
                " left join question_options as qo on qo.keyword = qa.answer_keyword" +
                " where qa.unique_id='"+unique_id+"'" +
                " and qa.form_id="+Form_id+" group by(qa.question_keyword)",null);
    }
}