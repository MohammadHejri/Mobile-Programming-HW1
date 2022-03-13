package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Professor extends User {
    private String universityName;
    private static ProfessorDBHelper professorDBHelper = new ProfessorDBHelper(null);

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

    public static Professor createProfessor(String username, String password, String firstname, String lastname, String universityName) {
        SQLiteDatabase db = userDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        db.insert("user", null, contentValues);

        SQLiteDatabase profDB = professorDBHelper.getWritableDatabase();
        ContentValues profContentValues = new ContentValues();
        profContentValues.put(ProfessorDBHelper.USERNAME, username);
        profContentValues.put(ProfessorDBHelper.UNIVERSITY_NAME, universityName);
        profDB.insert(ProfessorDBHelper.TABLE_NAME, null, profContentValues);
        return new Professor(username, password, firstname, lastname, universityName);
    }

    public static Professor getProfessor(String username) {
        SQLiteDatabase db = userDBHelper.getReadableDatabase();
        String[] columns = {"username", "password", "firstname", "lastname"};
        String selection = "username" + " = ?";
        String[] selectionArgs = { username };
        Cursor cursor = db.query("user", columns, selection, selectionArgs,null,null,null);
        cursor.moveToNext();
        String password = cursor.getString(cursor.getColumnIndex("password"));
        String firstname = cursor.getString(cursor.getColumnIndex("firstname"));
        String lastname = cursor.getString(cursor.getColumnIndex("lastname"));

        SQLiteDatabase profDB = professorDBHelper.getReadableDatabase();
        String[] profColumns = {ProfessorDBHelper.USERNAME, ProfessorDBHelper.UNIVERSITY_NAME};
        String profSelection = ProfessorDBHelper.USERNAME + " = ?";
        String[] profSelectionArgs = { username };
        Cursor profCursor = profDB.query(ProfessorDBHelper.TABLE_NAME, profColumns, profSelection, profSelectionArgs,null,null,null);
        profCursor.moveToNext();
        String universityName = profCursor.getString(profCursor.getColumnIndex(ProfessorDBHelper.UNIVERSITY_NAME));
        return new Professor(username, password, firstname, lastname, universityName);
    }

    static class ProfessorDBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "courseware";
        private static final String TABLE_NAME = "professor";
        private static final String USER_TABLE_NAME = "user";
        private static final int DATABASE_VERSION = 1;
        private static final String USERNAME = "username";
        private static final String UNIVERSITY_NAME = "uniname";
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                USERNAME + " VARCHAR(64) PRIMARY KEY , " + UNIVERSITY_NAME + " VARCHAR(64) NOT NULL , "
                + "FOREIGN KEY (" + USERNAME + ") REFERENCES " + USER_TABLE_NAME + "(" + USERNAME +
                ") ON DELETE CASCADE ON UPDATE CASCADE );";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        public ProfessorDBHelper(@Nullable Context context) {
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
