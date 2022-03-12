package edu.sharif.courseware.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Submission {
    private String answer;
    private float mark;
    private Student student;
    private Homework homework;

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

    static class HomeworkDBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "courseware";
        private static final String TABLE_NAME = "submission";
        private static final String HW_TABLE_NAME = "homework";
        private static final String STU_TABLE_NAME = "student";
        private static final int DATABASE_VERSION = 1;
        private static final String ANSWER = "answer";
        private static final String MARK = "mark";
        private static final String HW_NAME = "hw_name";
        private static final String HW_NAME_FK = "name";
        private static final String HW_COURSE_ID = "course_id";
//        private static final String HW_CO_NAME = "course_name";
//        private static final String HW_CO_OWNER = "course_owner";
        private static final String STUDENT = "student";
        private static final String STUDENT_FK = "username";

        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                HW_NAME + " VARCHAR(64) NOT NULL , " + HW_COURSE_ID + " INTEGER NOT NULL ," + STUDENT + " VARCHAR(64) NOT NULL , "
                + ANSWER + " VARCHAR(256) , " + MARK + " FLOAT(10) , "
                + "FOREIGN KEY (" + HW_NAME + " , " + HW_COURSE_ID + ") REFERENCES " + HW_TABLE_NAME + "(" + HW_NAME_FK + " , " +HW_COURSE_ID +
                ") ON DELETE CASCADE ON UPDATE CASCADE , "
                + "FOREIGN KEY (" + STUDENT + ") REFERENCES " + STU_TABLE_NAME + "(" + STUDENT_FK +
                ") ON DELETE CASCADE ON UPDATE CASCADE , "
                + "PRIMARY KEY (" + HW_NAME + " , " + HW_COURSE_ID + " , " + STUDENT + ") );";

        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        public HomeworkDBHelper(@Nullable Context context) {
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
