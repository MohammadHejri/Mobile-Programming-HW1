package edu.sharif.courseware.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Homework {
    private String name;
    private String question;
    private Course course;
    private HomeworkDBHelper homeworkDBHelper = new HomeworkDBHelper(null);

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

    static class HomeworkDBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "courseware";
        private static final String TABLE_NAME = "homework";
        private static final String COURSE_TABLE_NAME = "course";
        private static final int DATABASE_VERSION = 1;
        private static final String NAME = "name";
        private static final String QUESTION = "question";
        private static final String COURSE_ID = "course_id";
//        private static final String COURSE_NAME = "course_name";
//        private static final String COURSE_OWNER = "course_owner";

        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                NAME + " VARCHAR(64) NOT NULL , " + COURSE_ID + " INTEGER NOT NULL ," + QUESTION + " VARCHAR(256) , "
                + "FOREIGN KEY (" + COURSE_ID + ") REFERENCES " + COURSE_TABLE_NAME + "(" + COURSE_ID +
                ") ON DELETE CASCADE ON UPDATE CASCADE , " + "PRIMARY KEY (" + NAME + " , " + COURSE_ID + ") );";

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
