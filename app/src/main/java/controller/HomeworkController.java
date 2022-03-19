package controller;

import android.content.Context;

import java.util.List;

import model.Course;
import model.Homework;

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
