package edu.sharif.courseware.model;

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

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
