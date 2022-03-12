package edu.sharif.courseware.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Objects;

public abstract class User {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    protected static UserDBHelper userDBHelper = new UserDBHelper(null);

    public User(String username, String password, String firstname, String lastname) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    static class UserDBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "courseware";
        private static final String TABLE_NAME = "user";
        private static final int DATABASE_VERSION = 1;
        private static final String USERNAME = "username";
        private static final String PASSWORD = "password";
        private static final String FIRST_NAME = "firstname";
        private static final String LAST_NAME = "lastname";
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                USERNAME + " VARCHAR(64) PRIMARY KEY , " + PASSWORD + " VARCHAR(64) NOT NULL , " +
                FIRST_NAME + " VARCHAR(128) , " + LAST_NAME + " VARCHAR(128));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        public UserDBHelper(@Nullable Context context) {
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
