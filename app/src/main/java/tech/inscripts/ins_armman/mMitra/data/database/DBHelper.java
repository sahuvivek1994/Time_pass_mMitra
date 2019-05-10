package tech.inscripts.ins_armman.mMitra.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

import static tech.inscripts.ins_armman.mMitra.data.database.DatabaseContract.*;

/**
 * Created by lenovo on 11/10/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = DBHelper.class.getSimpleName();

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
}