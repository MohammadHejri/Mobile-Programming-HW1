package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        contentValues.put(DBHelper.USER_USERNAME, username);
        contentValues.put(DBHelper.USER_PASSWORD, password);
        contentValues.put(DBHelper.USER_FIRST_NAME, firstname);
        contentValues.put(DBHelper.USER_LAST_NAME, lastname);
        db.insert(DBHelper.USER_TABLE_NAME, null, contentValues);

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
        String[] columns = {DBHelper.USER_USERNAME, DBHelper.USER_PASSWORD, DBHelper.USER_FIRST_NAME, DBHelper.USER_LAST_NAME};
        String selection = DBHelper.USER_USERNAME + " = ?";
        String[] selectionArgs = { username };
        Cursor cursor = db.query(DBHelper.USER_TABLE_NAME, columns, selection, selectionArgs,null,null,null);
        cursor.moveToNext();
        String password = cursor.getString(cursor.getColumnIndex(DBHelper.USER_PASSWORD));
        String firstname = cursor.getString(cursor.getColumnIndex(DBHelper.USER_FIRST_NAME));
        String lastname = cursor.getString(cursor.getColumnIndex(DBHelper.USER_LAST_NAME));

        SQLiteDatabase stuDB = studentDBHelper.getReadableDatabase();
        String[] stuColumns = {DBHelper.STUDENT_USERNAME, DBHelper.STUDENT_NUMBER};
        String stuSelection = DBHelper.STUDENT_USERNAME + " = ?";
        String[] stuSelectionArgs = { username };
        Cursor stuCursor = stuDB.query(DBHelper.STUDENT_TABLE_NAME, stuColumns, stuSelection, stuSelectionArgs,null,null,null);
        stuCursor.moveToNext();
        String studentNumber = stuCursor.getString(stuCursor.getColumnIndex(DBHelper.STUDENT_NUMBER));
        return new Student(username, password, firstname, lastname, studentNumber);
    }

}
