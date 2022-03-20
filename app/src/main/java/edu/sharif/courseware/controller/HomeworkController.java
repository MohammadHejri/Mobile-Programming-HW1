package edu.sharif.courseware.controller;

import android.content.Context;

import java.util.List;

import edu.sharif.courseware.model.Course;

public class HomeworkController {

    private Context context;

    public HomeworkController(Context context) {
        this.context = context;
    }


    public List<String> getAllAnswers(String name, Course course) {
        //TODO
        return null;
    }

    public Homework getHomeworkQuestion(String name, int courseId) {
        return Homework.getHomework(context, courseId, name);
    }
}
