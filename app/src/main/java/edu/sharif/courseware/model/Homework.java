package edu.sharif.courseware.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class Homework {
    private String name;
    private String question;
    private Course course;
    private static DBHelper homeworkDBHelper;

    public Homework(String name, String question, Course course) {
        this.name = name;
        this.question = question;
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Homework homework = (Homework) o;
        return name.equals(homework.name) && course.equals(homework.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, course);
    }

    public static Homework createHomework(Context context, int course_id, String name, String question) {
        homeworkDBHelper = new DBHelper(context);
        SQLiteDatabase db = homeworkDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.HOMEWORK_NAME, name);
        contentValues.put(DBHelper.HOMEWORK_COURSE_ID, course_id);
        contentValues.put(DBHelper.HOMEWORK_QUESTION, question);

        db.insert(DBHelper.HOMEWORK_TABLE_NAME, null, contentValues);
        Course course = Course.getCourse(context, course_id);
        return new Homework(name, question, course);
    }

    public static Homework getHomework(Context context, int course_id, String name) {
        homeworkDBHelper = new DBHelper(context);
        SQLiteDatabase db = homeworkDBHelper.getReadableDatabase();
        String[] columns = {DBHelper.HOMEWORK_COURSE_ID, DBHelper.HOMEWORK_NAME, DBHelper.HOMEWORK_QUESTION};
        String selection = DBHelper.HOMEWORK_COURSE_ID + " = ? AND " + DBHelper.HOMEWORK_NAME + " = ? ";
        String[] selectionArgs = { String.valueOf(course_id), name };
        Cursor cursor = db.query(DBHelper.HOMEWORK_TABLE_NAME, columns, selection, selectionArgs,null,null,null);
        cursor.moveToNext();
        String question = cursor.getString(cursor.getColumnIndex(DBHelper.HOMEWORK_QUESTION));
        Course course = Course.getCourse(context, course_id);
        cursor.close();
        return new Homework(name, question, course);
    }

    public static ArrayList<Homework> getHomeworksOfCourse(Context context, int course_id) {
        homeworkDBHelper = new DBHelper(context);
        SQLiteDatabase db = homeworkDBHelper.getReadableDatabase();
        String[] columns = {DBHelper.HOMEWORK_COURSE_ID, DBHelper.HOMEWORK_NAME, DBHelper.HOMEWORK_QUESTION};
        String selection = DBHelper.HOMEWORK_COURSE_ID + " = ? ";
        String[] selectionArgs = { String.valueOf(course_id) };
        Cursor cursor = db.query(DBHelper.HOMEWORK_TABLE_NAME, columns, selection, selectionArgs,null,null,null);
        ArrayList<Homework> homeworks = new ArrayList<>();
        Course course = Course.getCourse(context, course_id);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(DBHelper.HOMEWORK_NAME));
            String question = cursor.getString(cursor.getColumnIndex(DBHelper.HOMEWORK_QUESTION));
            homeworks.add(new Homework(name, question, course));
        }
        cursor.close();
        return homeworks;
    }

    public static void updateHomeworkName(Context context, int course_id, String old_name, String newName) {
        homeworkDBHelper = new DBHelper(context);
        SQLiteDatabase db = homeworkDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.HOMEWORK_NAME, newName);
        String selection = DBHelper.HOMEWORK_COURSE_ID + " = ? AND " + DBHelper.HOMEWORK_NAME + " = ? ";
        String[] selectionArgs = { String.valueOf(course_id), old_name };
        db.update(DBHelper.HOMEWORK_TABLE_NAME, contentValues, selection, selectionArgs);
    }

}
