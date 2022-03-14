package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Professor extends User {
    private String universityName;
    private static DBHelper professorDBHelper;

    public Professor(String username, String password, String firstname, String lastname, String universityName) {
        super(username, password, firstname, lastname);
        this.universityName = universityName;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public static Professor createProfessor(Context context, String username, String password, String firstname, String lastname, String universityName) {
        userDBHelper = new DBHelper(context);
        professorDBHelper = new DBHelper(context);
        SQLiteDatabase db = userDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.USER_USERNAME, username);
        contentValues.put(DBHelper.USER_PASSWORD, password);
        contentValues.put(DBHelper.USER_FIRST_NAME, firstname);
        contentValues.put(DBHelper.USER_LAST_NAME, lastname);
        db.insert(DBHelper.USER_TABLE_NAME, null, contentValues);

        SQLiteDatabase profDB = professorDBHelper.getWritableDatabase();
        ContentValues profContentValues = new ContentValues();
        profContentValues.put(DBHelper.PROFESSOR_USERNAME, username);
        profContentValues.put(DBHelper.PROFESSOR_UNIVERSITY_NAME, universityName);
        profDB.insert(DBHelper.PROFESSOR_TABLE_NAME, null, profContentValues);
        return new Professor(username, password, firstname, lastname, universityName);
    }

    public static Professor getProfessor(Context context, String username) {
        userDBHelper = new DBHelper(context);
        professorDBHelper = new DBHelper(context);
        SQLiteDatabase db = userDBHelper.getReadableDatabase();
        String[] columns = {DBHelper.USER_USERNAME, DBHelper.USER_PASSWORD, DBHelper.USER_FIRST_NAME, DBHelper.USER_LAST_NAME};
        String selection = DBHelper.USER_USERNAME + " = ?";
        String[] selectionArgs = { username };
        Cursor cursor = db.query(DBHelper.USER_TABLE_NAME, columns, selection, selectionArgs,null,null,null);
        cursor.moveToNext();
        String password = cursor.getString(cursor.getColumnIndex(DBHelper.USER_PASSWORD));
        String firstname = cursor.getString(cursor.getColumnIndex(DBHelper.USER_FIRST_NAME));
        String lastname = cursor.getString(cursor.getColumnIndex(DBHelper.USER_LAST_NAME));

        SQLiteDatabase profDB = professorDBHelper.getReadableDatabase();
        String[] profColumns = {DBHelper.PROFESSOR_USERNAME, DBHelper.PROFESSOR_UNIVERSITY_NAME};
        String profSelection = DBHelper.PROFESSOR_USERNAME + " = ?";
        String[] profSelectionArgs = { username };
        Cursor profCursor = profDB.query(DBHelper.PROFESSOR_TABLE_NAME, profColumns, profSelection, profSelectionArgs,null,null,null);
        profCursor.moveToNext();
        String universityName = profCursor.getString(profCursor.getColumnIndex(DBHelper.PROFESSOR_UNIVERSITY_NAME));
        return new Professor(username, password, firstname, lastname, universityName);
    }

}
