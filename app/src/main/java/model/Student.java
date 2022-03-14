package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Student extends User {
    private String studentNumber;
    private static DBHelper studentDBHelper;

    public Student(String username, String password, String firstname, String lastname, String studentNumber) {
        super(username, password, firstname, lastname);
        this.studentNumber = studentNumber;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public static Student createStudent(Context context, String username, String password, String firstname, String lastname, String studentNumber) {
        userDBHelper = new DBHelper(context);
        studentDBHelper = new DBHelper(context);
        SQLiteDatabase db = userDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        db.insert("user", null, contentValues);

        SQLiteDatabase stuDB = studentDBHelper.getWritableDatabase();
        ContentValues stuContentValues = new ContentValues();
        stuContentValues.put(DBHelper.STUDENT_USERNAME, username);
        stuContentValues.put(DBHelper.STUDENT_NUMBER, studentNumber);
        stuDB.insert(DBHelper.STUDENT_TABLE_NAME, null, stuContentValues);
        return new Student(username, password, firstname, lastname, studentNumber);
    }

    public static Student getStudent(Context context, String username) {
        userDBHelper = new DBHelper(context);
        studentDBHelper = new DBHelper(context);
        SQLiteDatabase db = userDBHelper.getReadableDatabase();
        String[] columns = {"username", "password", "firstname", "lastname"};
        String selection = "username" + " = ?";
        String[] selectionArgs = { username };
        Cursor cursor = db.query("user", columns, selection, selectionArgs,null,null,null);
        cursor.moveToNext();
        String password = cursor.getString(cursor.getColumnIndex("password"));
        String firstname = cursor.getString(cursor.getColumnIndex("firstname"));
        String lastname = cursor.getString(cursor.getColumnIndex("lastname"));

        SQLiteDatabase stuDB = studentDBHelper.getReadableDatabase();
        String[] stuColumns = {DBHelper.STUDENT_USERNAME, DBHelper.STUDENT_NUMBER};
        String stuSelection = DBHelper.STUDENT_USERNAME + " = ?";
        String[] stuSelectionArgs = { username };
        Cursor stuCursor = stuDB.query(DBHelper.STUDENT_TABLE_NAME, stuColumns, stuSelection, stuSelectionArgs,null,null,null);
        stuCursor.moveToNext();
        String studentNumber = stuCursor.getString(stuCursor.getColumnIndex(DBHelper.STUDENT_NUMBER));
        return new Student(username, password, firstname, lastname, studentNumber);
    }

//    static class StudentDBHelper extends SQLiteOpenHelper {
//        private static final String DATABASE_NAME = "courseware";
//        private static final int DATABASE_VERSION = 1;
//        private static final String USER_TABLE_NAME = "user";
//
//        private static final String STUDENT_TABLE_NAME = "student";
//        private static final String STUDENT_USERNAME = "username";
//        private static final String STUDENT_NUMBER = "stnum";
//        private static final String STUDENT_CREATE_TABLE = "CREATE TABLE " + STUDENT_TABLE_NAME + " (" +
//                STUDENT_USERNAME + " VARCHAR(64) PRIMARY KEY , " + STUDENT_NUMBER + " VARCHAR(10) NOT NULL , "
//                + "FOREIGN KEY (" + STUDENT_USERNAME + ") REFERENCES " + USER_TABLE_NAME + "(" + STUDENT_USERNAME +
//                ") ON DELETE CASCADE ON UPDATE CASCADE );";
//        private static final String STUDENT_DROP_TABLE = "DROP TABLE IF EXISTS " + STUDENT_TABLE_NAME + ";";
//
//        public StudentDBHelper(@Nullable Context context) {
//            super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase sqLiteDatabase) {
//            try {
//                sqLiteDatabase.execSQL(STUDENT_CREATE_TABLE);
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//            try {
//                System.out.println("OnUpgrade");
//                sqLiteDatabase.execSQL(STUDENT_DROP_TABLE);
//                onCreate(sqLiteDatabase);
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }
//    }
}
