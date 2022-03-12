package edu.sharif.courseware.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public class Course {
    private int id;
    private String name;
    private final Professor owner;
    private static CourseDBHelper courseDBHelper = new CourseDBHelper(null);

    public Course(int id, String name, Professor owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Professor getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return name.equals(course.name) && owner.equals(course.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner);
    }

    public static Course createCourse(String name, Professor owner) {
        SQLiteDatabase db = courseDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CourseDBHelper.NAME, name);
        contentValues.put(CourseDBHelper.OWNER, owner.getUsername());

        long id = db.insert("user", null, contentValues);
        return new Course((int)id, name, owner);
    }

    public static Course getCourse(int id) {
        SQLiteDatabase courseDB = courseDBHelper.getReadableDatabase();
        String[] courseColumns = {CourseDBHelper.ID, CourseDBHelper.NAME, CourseDBHelper.OWNER};
        String courseSelection = CourseDBHelper.ID + " = ?";
        String[] courseSelectionArgs = { String.valueOf(id) };
        Cursor courseCursor = courseDB.query(CourseDBHelper.TABLE_NAME, courseColumns, courseSelection, courseSelectionArgs,null,null,null);
        courseCursor.moveToNext();
        String name = courseCursor.getString(courseCursor.getColumnIndex(CourseDBHelper.NAME));
        String owner_user = courseCursor.getString(courseCursor.getColumnIndex(CourseDBHelper.OWNER));
        Professor owner = Professor.getProfessor(owner_user);
        return new Course(id, name, owner);
    }

    public static ArrayList<Course> getCoursesOfOwner(Professor owner) {
        return null; // TODO
    }

    static class CourseDBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "courseware";
        private static final String TABLE_NAME = "course";
        private static final String CO_STU_TABLE_NAME = "course_student";
        private static final String STU_TABLE_NAME = "student";
        private static final String PROF_TABLE_NAME = "professor";
        private static final int DATABASE_VERSION = 1;
        private static final String ID = "course_id";
        private static final String NAME = "name";
        private static final String OWNER = "owner";
        private static final String OWNER_FK = "username";
//        private static final String COURSE_NAME = "course_name";
//        private static final String COURSE_OWNER = "course_owner";
        private static final String STUDENT = "student";
        private static final String STUDENT_FK = "username";

        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                NAME + " VARCHAR(64) NOT NULL , " + OWNER + " VARCHAR(64) NOT NULL , "
                + "FOREIGN KEY (" + OWNER + ") REFERENCES " + PROF_TABLE_NAME + "(" + OWNER_FK +
                ") ON DELETE CASCADE ON UPDATE CASCADE );";

        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        private static final String CREATE_CO_STU_TABLE = "CREATE TABLE " + CO_STU_TABLE_NAME + " (" +
                ID + " INTEGER NOT NULL ,"
                + STUDENT + " VARCHAR(64) NOT NULL , "
                + "FOREIGN KEY (" + ID + ") REFERENCES " + TABLE_NAME + "(" + ID +
                ") ON DELETE CASCADE ON UPDATE CASCADE , " + "FOREIGN KEY (" + STUDENT + ") REFERENCES " + STU_TABLE_NAME + "(" + STUDENT_FK +
                ") ON DELETE CASCADE ON UPDATE CASCADE , " + "PRIMARY KEY (" + ID + " , " + STUDENT + ") );";

        private static final String DROP_CO_STU_TABLE = "DROP TABLE IF EXISTS " + CO_STU_TABLE_NAME + ";";

        public CourseDBHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(CREATE_TABLE);
                sqLiteDatabase.execSQL(CREATE_CO_STU_TABLE);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                System.out.println("OnUpgrade");
                sqLiteDatabase.execSQL(DROP_CO_STU_TABLE);
                sqLiteDatabase.execSQL(DROP_TABLE);
                onCreate(sqLiteDatabase);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
