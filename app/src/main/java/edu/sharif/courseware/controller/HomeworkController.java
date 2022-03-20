package edu.sharif.courseware.controller;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import edu.sharif.courseware.model.Course;
import edu.sharif.courseware.model.Homework;
import edu.sharif.courseware.model.Professor;

public class HomeworkController {

    private Context context;

    public HomeworkController(Context context) {
        this.context = context;
    }

    public String getHomeworkError(String homeworkName) {
        if (homeworkName.isEmpty())
            return "This field can not be blank";
        return null;
    }

    public ArrayList<Homework> getHomeworksByCourse(int courseId) {
        Course course = Course.getCourse(context, courseId);
        try {
            return Homework.getHomeworksOfCourse(context, courseId);
        } catch (Exception e){
            return new ArrayList<>();
        }
    }

    public Homework createHomework(String course_id, String name, String question) {
        return Homework.createHomework(context, Integer.parseInt(course_id), name, question);
    }

    public Homework getHomeworkQuestion(String name, int courseId) {
        return Homework.getHomework(context, courseId, name);
    }
}
