package edu.sharif.courseware.controller;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import edu.sharif.courseware.model.Course;
import edu.sharif.courseware.model.Homework;
import edu.sharif.courseware.model.LoginRepository;
import edu.sharif.courseware.model.Professor;

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

    public ArrayList<Course> getCoursesByProfessorID(String professorID) {
        Professor professor = Professor.getProfessor(context, professorID);
        try {
            return Course.getCoursesOfOwner(context, professor);
        } catch (Exception e){
            return new ArrayList<>();
        }
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
