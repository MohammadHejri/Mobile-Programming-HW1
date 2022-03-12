package edu.sharif.courseware.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Student extends User {
    private String studentNumber;
    private static StudentDBHelper studentDBHelper = new StudentDBHelper(null);

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

    public static Student createStudent(String username, String password, String firstname, String lastname, String studentNumber) {
        SQLiteDatabase db = userDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        db.insert("user", null, contentValues);

        SQLiteDatabase stuDB = studentDBHelper.getWritableDatabase();
        ContentValues stuContentValues = new ContentValues();
        stuContentValues.put(StudentDBHelper.USERNAME, username);
        stuContentValues.put(StudentDBHelper.STUDENT_NUMBER, studentNumber);
        stuDB.insert(StudentDBHelper.TABLE_NAME, null, stuContentValues);
        return new Student(username, password, firstname, lastname, studentNumber);
    }

    public static Student getStudent(String username) {
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
        String[] stuColumns = {StudentDBHelper.USERNAME, StudentDBHelper.STUDENT_NUMBER};
        String stuSelection = StudentDBHelper.USERNAME + " = ?";
        String[] stuSelectionArgs = { username };
        Cursor stuCursor = stuDB.query(StudentDBHelper.TABLE_NAME, stuColumns, stuSelection, stuSelectionArgs,null,null,null);
        stuCursor.moveToNext();
        String studentNumber = stuCursor.getString(stuCursor.getColumnIndex(StudentDBHelper.STUDENT_NUMBER));
        return new Student(username, password, firstname, lastname, studentNumber);
    }

    static class StudentDBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "courseware";
        private static final String TABLE_NAME = "student";
        private static final String USER_TABLE_NAME = "user";
        private static final int DATABASE_VERSION = 1;
        private static final String USERNAME = "username";
        private static final String STUDENT_NUMBER = "stnum";
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                USERNAME + " VARCHAR(64) PRIMARY KEY , " + STUDENT_NUMBER + " VARCHAR(10) NOT NULL , "
                + "FOREIGN KEY (" + USERNAME + ") REFERENCES " + USER_TABLE_NAME + "(" + USERNAME +
                ") ON DELETE CASCADE ON UPDATE CASCADE );";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        public StudentDBHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                System.out.println("OnUpgrade");
                sqLiteDatabase.execSQL(DROP_TABLE);
                onCreate(sqLiteDatabase);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
