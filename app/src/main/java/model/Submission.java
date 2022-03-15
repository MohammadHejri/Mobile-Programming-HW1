package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class Submission {
    private String answer;
    private float mark;
    private Student student;
    private Homework homework;
    private static DBHelper submissionDBHelper;

    public Submission(String answer, Student student, Homework homework) {
        this.answer = answer;
        this.student = student;
        this.homework = homework;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public Student getStudent() {
        return student;
    }

    public Homework getHomework() {
        return homework;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Submission that = (Submission) o;
        return student.equals(that.student) && homework.equals(that.homework);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, homework);
    }

    public static Submission createSubmission(Context context, int course_id, String hwName, String studentUsername, String answer) {
        submissionDBHelper = new DBHelper(context);
        SQLiteDatabase db = submissionDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.SUBMISSION_HW_NAME, hwName);
        contentValues.put(DBHelper.HOMEWORK_COURSE_ID, course_id);
        contentValues.put(DBHelper.SUBMISSION_STUDENT, studentUsername);
        contentValues.put(DBHelper.SUBMISSION_ANSWER, answer);

        db.insert(DBHelper.SUBMISSION_TABLE_NAME, null, contentValues);
        Homework homework = Homework.getHomework(context, course_id, hwName);
        Student student = Student.getStudent(context, studentUsername);
        return new Submission(answer, student, homework);
    }

    public static Submission getSubmission(Context context, int course_id, String hwName, String studentUsername) {
        submissionDBHelper = new DBHelper(context);
        SQLiteDatabase db = submissionDBHelper.getReadableDatabase();
        String[] columns = {DBHelper.HOMEWORK_COURSE_ID, DBHelper.SUBMISSION_HW_NAME, DBHelper.SUBMISSION_STUDENT, DBHelper.SUBMISSION_ANSWER, DBHelper.SUBMISSION_MARK};
        String selection = DBHelper.HOMEWORK_COURSE_ID + " = ? AND " + DBHelper.SUBMISSION_HW_NAME + " = ? AND " + DBHelper.SUBMISSION_STUDENT + " = ? ";
        String[] selectionArgs = { String.valueOf(course_id), hwName, studentUsername };
        Cursor cursor = db.query(DBHelper.SUBMISSION_TABLE_NAME, columns, selection, selectionArgs,null,null,null);
        cursor.moveToNext();
        String answer = cursor.getString(cursor.getColumnIndex(DBHelper.SUBMISSION_ANSWER));
        float mark = cursor.getFloat(cursor.getColumnIndex(DBHelper.SUBMISSION_MARK));
        cursor.close();
        Homework homework = Homework.getHomework(context, course_id, hwName);
        Student student = Student.getStudent(context, studentUsername);
        Submission submission = new Submission(answer, student, homework);
        submission.setMark(mark);
        return submission;
    }

    public static ArrayList<Submission> getSubmissionsOfHomework(Context context, int course_id, String hw_name) {
        submissionDBHelper = new DBHelper(context);
        SQLiteDatabase db = submissionDBHelper.getReadableDatabase();
        String[] columns = {DBHelper.HOMEWORK_COURSE_ID, DBHelper.SUBMISSION_HW_NAME, DBHelper.SUBMISSION_STUDENT, DBHelper.SUBMISSION_ANSWER, DBHelper.SUBMISSION_MARK};
        String selection = DBHelper.HOMEWORK_COURSE_ID + " = ? AND " + DBHelper.SUBMISSION_HW_NAME + " = ? ";
        String[] selectionArgs = { String.valueOf(course_id), hw_name };
        Cursor cursor = db.query(DBHelper.SUBMISSION_TABLE_NAME, columns, selection, selectionArgs,null,null,null);
        ArrayList<Submission> submissions = new ArrayList<>();
        Homework homework = Homework.getHomework(context, course_id, hw_name);
        while (cursor.moveToNext()) {
            String studentUsername = cursor.getString(cursor.getColumnIndex(DBHelper.SUBMISSION_STUDENT));
            String answer = cursor.getString(cursor.getColumnIndex(DBHelper.SUBMISSION_ANSWER));
            float mark = cursor.getFloat(cursor.getColumnIndex(DBHelper.SUBMISSION_MARK));
            Student student = Student.getStudent(context, studentUsername);
            Submission submission = new Submission(answer, student, homework);
            submission.setMark(mark);
            submissions.add(submission);
        }
        cursor.close();
        return submissions;
    }

    public static void updateSubmissionAnswer(Context context, int course_id, String hwName, String studentUsername, String answer) {
        submissionDBHelper = new DBHelper(context);
        SQLiteDatabase db = submissionDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.SUBMISSION_ANSWER, answer);
        String selection = DBHelper.HOMEWORK_COURSE_ID + " = ? AND " + DBHelper.SUBMISSION_HW_NAME + " = ? AND " + DBHelper.SUBMISSION_STUDENT + " = ? ";
        String[] selectionArgs = { String.valueOf(course_id), hwName, studentUsername };
        db.update(DBHelper.SUBMISSION_TABLE_NAME, contentValues, selection, selectionArgs);
    }

    public static void updateSubmissionMark(Context context, int course_id, String hwName, String studentUsername, float mark) {
        submissionDBHelper = new DBHelper(context);
        SQLiteDatabase db = submissionDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.SUBMISSION_MARK, mark);
        String selection = DBHelper.HOMEWORK_COURSE_ID + " = ? AND " + DBHelper.SUBMISSION_HW_NAME + " = ? AND " + DBHelper.SUBMISSION_STUDENT + " = ? ";
        String[] selectionArgs = { String.valueOf(course_id), hwName, studentUsername };
        db.update(DBHelper.SUBMISSION_TABLE_NAME, contentValues, selection, selectionArgs);
    }

}
