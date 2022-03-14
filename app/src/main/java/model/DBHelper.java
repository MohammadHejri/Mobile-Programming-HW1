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
    private static final String USER_CREATE_TABLE = "CREATE TABLE " + USER_TABLE_NAME + " (" +
            USER_USERNAME + " VARCHAR(64) PRIMARY KEY , " + USER_PASSWORD + " VARCHAR(64) NOT NULL , " +
            USER_FIRST_NAME + " VARCHAR(128) , " + USER_LAST_NAME + " VARCHAR(128));";
    private static final String USER_DROP_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE_NAME + ";";

    public static final String STUDENT_TABLE_NAME = "student";
    public static final String STUDENT_USERNAME = "username";
    public static final String STUDENT_NUMBER = "stnum";
    private static final String STUDENT_CREATE_TABLE = "CREATE TABLE " + STUDENT_TABLE_NAME + " (" +
            STUDENT_USERNAME + " VARCHAR(64) PRIMARY KEY , " + STUDENT_NUMBER + " VARCHAR(10) NOT NULL , "
            + "FOREIGN KEY (" + STUDENT_USERNAME + ") REFERENCES " + USER_TABLE_NAME + "(" + USER_USERNAME +
            ") ON DELETE CASCADE ON UPDATE CASCADE );";
    private static final String STUDENT_DROP_TABLE = "DROP TABLE IF EXISTS " + STUDENT_TABLE_NAME + ";";

    public static final String PROFESSOR_TABLE_NAME = "professor";
    public static final String PROFESSOR_USERNAME = "username";
    public static final String PROFESSOR_UNIVERSITY_NAME = "uniname";
    private static final String PROFESSOR_CREATE_TABLE = "CREATE TABLE " + PROFESSOR_TABLE_NAME + " (" +
            PROFESSOR_USERNAME + " VARCHAR(64) PRIMARY KEY , " + PROFESSOR_UNIVERSITY_NAME + " VARCHAR(64) NOT NULL , "
            + "FOREIGN KEY (" + PROFESSOR_USERNAME + ") REFERENCES " + USER_TABLE_NAME + "(" + USER_USERNAME +
            ") ON DELETE CASCADE ON UPDATE CASCADE );";
    private static final String PROFESSOR_DROP_TABLE = "DROP TABLE IF EXISTS " + PROFESSOR_TABLE_NAME + ";";

    public static final String COURSE_TABLE_NAME = "course";
    public static final String COURSE_ID = "course_id";
    public static final String COURSE_NAME = "name";
    public static final String COURSE_OWNER = "owner";
    private static final String COURSE_CREATE_TABLE = "CREATE TABLE " + COURSE_TABLE_NAME + " ("
            + COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            COURSE_NAME + " VARCHAR(64) NOT NULL , " + COURSE_OWNER + " VARCHAR(64) NOT NULL , "
            + "FOREIGN KEY (" + COURSE_OWNER + ") REFERENCES " + PROFESSOR_TABLE_NAME + "(" + PROFESSOR_USERNAME +
            ") ON DELETE CASCADE ON UPDATE CASCADE );";
    private static final String COURSE_DROP_TABLE = "DROP TABLE IF EXISTS " + COURSE_TABLE_NAME + ";";

    public static final String COURSE_STUDENT_TABLE_NAME = "course_student";
    public static final String CO_STU_STUDENT = "student";
    public static final String CO_STU_STUDENT_FK = "username";
    private static final String COURSE_STUDENT_CREATE_TABLE = "CREATE TABLE " + COURSE_STUDENT_TABLE_NAME + " (" +
            COURSE_ID + " INTEGER NOT NULL ,"
            + CO_STU_STUDENT + " VARCHAR(64) NOT NULL , "
            + "FOREIGN KEY (" + COURSE_ID + ") REFERENCES " + COURSE_TABLE_NAME + "(" + COURSE_ID +
            ") ON DELETE CASCADE ON UPDATE CASCADE , " + "FOREIGN KEY (" + CO_STU_STUDENT + ") REFERENCES " + STUDENT_TABLE_NAME + "(" + CO_STU_STUDENT_FK +
            ") ON DELETE CASCADE ON UPDATE CASCADE , " + "PRIMARY KEY (" + COURSE_ID + " , " + CO_STU_STUDENT + ") );";
    private static final String COURSE_STUDENT_DROP_TABLE = "DROP TABLE IF EXISTS " + COURSE_STUDENT_TABLE_NAME + ";";

    public static final String HOMEWORK_TABLE_NAME = "homework";
    public static final String HOMEWORK_NAME = "name";
    public static final String HOMEWORK_QUESTION = "question";
    public static final String HOMEWORK_COURSE_ID = "course_id";
    private static final String HOMEWORK_CREATE_TABLE = "CREATE TABLE " + HOMEWORK_TABLE_NAME + " (" +
            HOMEWORK_NAME + " VARCHAR(64) NOT NULL , " + HOMEWORK_COURSE_ID + " INTEGER NOT NULL ," + HOMEWORK_QUESTION + " VARCHAR(256) , "
            + "FOREIGN KEY (" + HOMEWORK_COURSE_ID + ") REFERENCES " + COURSE_TABLE_NAME + "(" + COURSE_ID +
            ") ON DELETE CASCADE ON UPDATE CASCADE , " + "PRIMARY KEY (" + HOMEWORK_NAME + " , " + HOMEWORK_COURSE_ID + ") );";
    private static final String HOMEWORK_DROP_TABLE = "DROP TABLE IF EXISTS " + HOMEWORK_TABLE_NAME + ";";

    public static final String SUBMISSION_TABLE_NAME = "submission";
    public static final String SUBMISSION_ANSWER = "answer";
    public static final String SUBMISSION_MARK = "mark";
    public static final String SUBMISSION_HW_NAME = "hw_name";
    public static final String SUBMISSION_STUDENT = "student";
    private static final String SUBMISSION_CREATE_TABLE = "CREATE TABLE " + SUBMISSION_TABLE_NAME + " (" +
            SUBMISSION_HW_NAME + " VARCHAR(64) NOT NULL , " + HOMEWORK_COURSE_ID + " INTEGER NOT NULL ," + SUBMISSION_STUDENT + " VARCHAR(64) NOT NULL , "
            + SUBMISSION_ANSWER + " VARCHAR(256) , " + SUBMISSION_MARK + " FLOAT(10) , "
            + "FOREIGN KEY (" + SUBMISSION_HW_NAME + " , " + HOMEWORK_COURSE_ID + ") REFERENCES " + HOMEWORK_TABLE_NAME + "(" + HOMEWORK_NAME + " , " + HOMEWORK_COURSE_ID +
            ") ON DELETE CASCADE ON UPDATE CASCADE , "
            + "FOREIGN KEY (" + SUBMISSION_STUDENT + ") REFERENCES " + STUDENT_TABLE_NAME + "(" + STUDENT_USERNAME +
            ") ON DELETE CASCADE ON UPDATE CASCADE , "
            + "PRIMARY KEY (" + SUBMISSION_HW_NAME + " , " + HOMEWORK_COURSE_ID + " , " + SUBMISSION_STUDENT + ") );";
    private static final String SUBMISSION_DROP_TABLE = "DROP TABLE IF EXISTS " + SUBMISSION_TABLE_NAME + ";";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(USER_CREATE_TABLE);
            sqLiteDatabase.execSQL(STUDENT_CREATE_TABLE);
            sqLiteDatabase.execSQL(PROFESSOR_CREATE_TABLE);
            sqLiteDatabase.execSQL(COURSE_CREATE_TABLE);
            sqLiteDatabase.execSQL(COURSE_STUDENT_CREATE_TABLE);
            sqLiteDatabase.execSQL(HOMEWORK_CREATE_TABLE);
            sqLiteDatabase.execSQL(SUBMISSION_CREATE_TABLE);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            System.out.println("OnUpgrade");
            sqLiteDatabase.execSQL(SUBMISSION_DROP_TABLE);
            sqLiteDatabase.execSQL(HOMEWORK_DROP_TABLE);
            sqLiteDatabase.execSQL(COURSE_STUDENT_DROP_TABLE);
            sqLiteDatabase.execSQL(COURSE_DROP_TABLE);
            sqLiteDatabase.execSQL(STUDENT_DROP_TABLE);
            sqLiteDatabase.execSQL(PROFESSOR_DROP_TABLE);
            sqLiteDatabase.execSQL(USER_DROP_TABLE);
            onCreate(sqLiteDatabase);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
