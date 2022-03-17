package controller;

import android.content.Context;

import java.util.List;

import model.Course;
import model.Homework;
import model.Professor;

public class CourseController {

    private Context context;

    public CourseController(Context context) {
        this.context = context;
    }

    public Course getCourse(int id) {
        return Course.getCourse(context, id);
    }

    public List<Course> getListedCourses(String studentUsername) {
        return Course.getStudentNotEnrolledCourses(context, studentUsername);
    }

    public List<Course> getUnlistedCourses(String studentUsername) {
        return Course.getStudentNotEnrolledCourses(context, studentUsername);
    }

    public List<Course> getCreatedCourses(String username) {
        Professor professor = Professor.getProfessor(context, username);
        return Course.getCoursesOfOwner(context, professor);
    }

    public List<Course> getAllCourses(String professorName) {
        Professor professor = Professor.getProfessor(context, professorName);
        return Course.getCoursesOfOwner(context,professor);
    }

    public List<Homework> getAllHomeworks(int id) {
        return Homework.getHomeworksOfCourse(context, id);
    }

    public Course createCourse(String name, String owner) {
        Professor professor = Professor.getProfessor(this.context, owner);
        return Course.createCourse(this.context, name, professor);
    }

    public void addStudentToCourse(String studentUsername, int courseId) {
        Course.addStudentToCourse(context,studentUsername,courseId);
    }
}
