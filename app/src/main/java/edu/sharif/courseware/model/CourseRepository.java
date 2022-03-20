package edu.sharif.courseware.model;

import android.content.Context;

public class CourseRepository {

    private static CourseRepository instance;
    private String courseId = null;

    public static CourseRepository getInstance() {
        if (instance == null)
            instance = new CourseRepository();
        return instance;
    }

    public String getCourseId() {
        return this.courseId;
    }

    public Course getCourse(Context context) {
        try {
            return Course.getCourse(context, Integer.parseInt(courseId));
        }catch(Exception e) {
            return null;
        }
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
