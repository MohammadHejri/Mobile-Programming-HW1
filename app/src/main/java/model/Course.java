package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class Course {
    private int id;
    private String name;
    private final Professor owner;
    private static DBHelper courseDBHelper;

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

    public static Course createCourse(Context context, String name, Professor owner) {
        courseDBHelper = new DBHelper(context);
        SQLiteDatabase db = courseDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COURSE_NAME, name);
        contentValues.put(DBHelper.COURSE_OWNER, owner.getUsername());

        long id = db.insert(DBHelper.COURSE_TABLE_NAME, null, contentValues);
        return new Course((int)id, name, owner);
    }

    public static Course getCourse(Context context, int id) {
        courseDBHelper = new DBHelper(context);
        SQLiteDatabase courseDB = courseDBHelper.getReadableDatabase();
        String[] courseColumns = {DBHelper.COURSE_ID, DBHelper.COURSE_NAME, DBHelper.COURSE_OWNER};
        String courseSelection = DBHelper.COURSE_ID + " = ?";
        String[] courseSelectionArgs = { String.valueOf(id) };
        Cursor courseCursor = courseDB.query(DBHelper.COURSE_TABLE_NAME, courseColumns, courseSelection, courseSelectionArgs,null,null,null);
        courseCursor.moveToNext();
        String name = courseCursor.getString(courseCursor.getColumnIndex(DBHelper.COURSE_NAME));
        String owner_user = courseCursor.getString(courseCursor.getColumnIndex(DBHelper.COURSE_OWNER));
        Professor owner = Professor.getProfessor(context, owner_user);
        return new Course(id, name, owner);
    }

    public static ArrayList<Course> getCoursesOfOwner(Professor owner) {
        return null; // TODO
    }

}
