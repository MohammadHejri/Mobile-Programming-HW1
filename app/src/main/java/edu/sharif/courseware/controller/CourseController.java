package edu.sharif.courseware.controller;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import edu.sharif.courseware.model.Course;
import edu.sharif.courseware.model.Homework;
import edu.sharif.courseware.model.LoginRepository;
import edu.sharif.courseware.model.Professor;
import edu.sharif.courseware.view.StudentMainPage;

public class CourseController {

    private Context context;

    public CourseController(Context context) {
        this.context = context;
    }

    public Course getCourse(int id) {
        return Course.getCourse(context, id);
    }

    public String getCourseNameError(String courseName) {
        if (courseName.isEmpty())
            return "This field can not be blank";
        return null;
    }

    public ArrayList<Course> getStudentEnrolledCourses(String studentUsername) {
        try {
            return Course.getStudentEnrolledCourses(context, studentUsername);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Course getEnrolledCourse(String courseID, String studentName) {
        ArrayList<Course> courses =  getStudentEnrolledCourses(studentName);
        for (Course course : courses)
            if (course.getId() == Integer.parseInt(courseID))
                return course;
        return null;
    }

    public Course getOwnedCourse(String courseID, String professorName) {
        ArrayList<Course> courses =  getCoursesByProfessorID(professorName);
        for (Course course : courses)
            if (course.getId() == Integer.parseInt(courseID))
                return course;
        return null;
    }

    public ArrayList<Course> getStudentNotEnrolledCourses(String studentUsername) {
        try {
            return Course.getStudentNotEnrolledCourses(context, studentUsername);
        } catch (Exception e) {
            return new ArrayList<>();
        }
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

    public Course createCourse(String courseName, String owner) {
        Professor professor = Professor.getProfessor(context, owner);
        return Course.createCourse(context, courseName, professor);
    }

    public void addStudentToCourse(String studentUsername, int courseId) {
        Course.addStudentToCourse(context,studentUsername,courseId);
    }
}
