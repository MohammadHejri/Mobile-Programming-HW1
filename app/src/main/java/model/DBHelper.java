package model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "courseware";
    private static final int DATABASE_VERSION = 1;

    public static final String USER_TABLE_NAME = "user";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_FIRST_NAME = "firstname";
    public static final String USER_LAST_NAME = "lastname";
    public static final String USER_CREATE_TABLE = "CREATE TABLE " + USER_TABLE_NAME + " (" +
            USER_USERNAME + " VARCHAR(64) PRIMARY KEY , " + USER_PASSWORD + " VARCHAR(64) NOT NULL , " +
            USER_FIRST_NAME + " VARCHAR(128) , " + USER_LAST_NAME + " VARCHAR(128));";
    public static final String USER_DROP_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE_NAME + ";";

    public static final String STUDENT_TABLE_NAME = "student";
    public static final String STUDENT_USERNAME = "username";
    public static final String STUDENT_NUMBER = "stnum";
    public static final String STUDENT_CREATE_TABLE = "CREATE TABLE " + STUDENT_TABLE_NAME + " (" +
            STUDENT_USERNAME + " VARCHAR(64) PRIMARY KEY , " + STUDENT_NUMBER + " VARCHAR(10) NOT NULL , "
            + "FOREIGN KEY (" + STUDENT_USERNAME + ") REFERENCES " + USER_TABLE_NAME + "(" + STUDENT_USERNAME +
            ") ON DELETE CASCADE ON UPDATE CASCADE );";
    public static final String STUDENT_DROP_TABLE = "DROP TABLE IF EXISTS " + STUDENT_TABLE_NAME + ";";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(USER_CREATE_TABLE);
            sqLiteDatabase.execSQL(STUDENT_CREATE_TABLE);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            System.out.println("OnUpgrade");
            sqLiteDatabase.execSQL(STUDENT_DROP_TABLE);
            sqLiteDatabase.execSQL(USER_DROP_TABLE);
            onCreate(sqLiteDatabase);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
