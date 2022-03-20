package edu.sharif.courseware.model;

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
        courseCursor.close();
        return new Course(id, name, owner);
    }

    public static ArrayList<Course> getCoursesOfOwner(Context context, Professor owner) {
        courseDBHelper = new DBHelper(context);
        SQLiteDatabase courseDB = courseDBHelper.getReadableDatabase();
        String[] courseColumns = {DBHelper.COURSE_ID, DBHelper.COURSE_NAME, DBHelper.COURSE_OWNER};
        String courseSelection = DBHelper.COURSE_OWNER + " = ?";
        String[] courseSelectionArgs = { owner.getUsername() };
        Cursor courseCursor = courseDB.query(DBHelper.COURSE_TABLE_NAME, courseColumns, courseSelection, courseSelectionArgs,null,null,null);
        ArrayList<Course> courses = new ArrayList<>();
        while (courseCursor.moveToNext()) {
            int id = (int) courseCursor.getLong(courseCursor.getColumnIndex(DBHelper.COURSE_ID));
            String name = courseCursor.getString(courseCursor.getColumnIndex(DBHelper.COURSE_NAME));
            courses.add(new Course(id, name, owner));
        }
        courseCursor.close();
        return courses;
    }

    public static void addStudentToCourse(Context context, String student_username, int course_id) {
        courseDBHelper = new DBHelper(context);
        SQLiteDatabase db = courseDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COURSE_ID, course_id);
        contentValues.put(DBHelper.CO_STU_STUDENT, student_username);

        db.insert(DBHelper.COURSE_STUDENT_TABLE_NAME, null, contentValues);
    }

    public static ArrayList<Student> getCourseAudience(Context context, int course_id) {
        courseDBHelper = new DBHelper(context);
        SQLiteDatabase db = courseDBHelper.getReadableDatabase();
        String[] columns = {DBHelper.COURSE_ID, DBHelper.CO_STU_STUDENT};
        String selection = DBHelper.COURSE_ID + " = ?";
        String[] selectionArgs = { String.valueOf(course_id) };
        Cursor cursor = db.query(DBHelper.COURSE_STUDENT_TABLE_NAME, columns, selection, selectionArgs,null,null,null);
        ArrayList<Student> students = new ArrayList<>();
        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndex(DBHelper.CO_STU_STUDENT));
            students.add(Student.getStudent(context, username));
        }
        cursor.close();
        return students;
    }

//    public static ArrayList<Course> getStudentEnrolledCourses(Context context, String student_username) {
//        courseDBHelper = new DBHelper(context);
//        SQLiteDatabase db = courseDBHelper.getReadableDatabase();
//        String[] columns = {DBHelper.COURSE_ID, DBHelper.CO_STU_STUDENT};
//        String selection = DBHelper.CO_STU_STUDENT + " = ?";
//        String[] selectionArgs = { student_username };
//        Cursor cursor = db.query(DBHelper.COURSE_STUDENT_TABLE_NAME, columns, selection, selectionArgs,null,null,null);
//        ArrayList<Course> courses = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            int course_id = (int) cursor.getLong(cursor.getColumnIndex(DBHelper.COURSE_ID));
//            courses.add(Course.getCourse(context, course_id));
//        }
//        cursor.close();
//        return courses;
//    }

    public static ArrayList<Course> getStudentEnrolledCourses(Context context, String student_username) {
        courseDBHelper = new DBHelper(context);
        SQLiteDatabase db = courseDBHelper.getReadableDatabase();
        String query = "SELECT DISTINCT course_id FROM course_student ";
        Cursor cursor = db.rawQuery(query, new String[]{student_username});
        ArrayList<Course> courses = new ArrayList<>();
        while (cursor.moveToNext()) {
            int course_id = (int) cursor.getLong(cursor.getColumnIndex(DBHelper.COURSE_ID));
            courses.add(Course.getCourse(context, course_id));
        }
        cursor.close();
        return courses;
    }

    public static ArrayList<Course> getStudentNotEnrolledCourses(Context context, String student_username) {
        courseDBHelper = new DBHelper(context);
        SQLiteDatabase db = courseDBHelper.getReadableDatabase();
        String query = "SELECT DISTINCT course_id FROM course";
        Cursor cursor = db.rawQuery(query, new String[]{});
        ArrayList<Course> courses = new ArrayList<>();
        while (cursor.moveToNext()) {
            int course_id = (int) cursor.getLong(cursor.getColumnIndex(DBHelper.COURSE_ID));
            courses.add(Course.getCourse(context, course_id));
        }
        cursor.close();
        return courses;
    }

}
