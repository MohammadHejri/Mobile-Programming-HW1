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

    public Homework getHomework(String courseID, String homeworkName) {
        ArrayList<Homework> homeworks =  getHomeworksByCourse(Integer.parseInt(courseID));
        for (Homework homework : homeworks)
            if (homework.getName().equals(homeworkName))
                return homework;
        return null;
    }

    public ArrayList<Homework> getHomeworksByCourse(int courseId) {
        try {
            return Homework.getHomeworksOfCourse(context, courseId);
        } catch (Exception e){
            return new ArrayList<>();
        }
    }

    public Homework createHomework(String course_id, String name, String question) {
        try {
            Homework.getHomework(context, Integer.parseInt(course_id), name);
            return null;
        } catch (Exception e) {
            return Homework.createHomework(context, Integer.parseInt(course_id), name, question);
        }
    }

    public void updateHomeworkName(int course_id, String old_name, String newName) {
        Homework.updateHomeworkName(context, course_id, old_name, newName);
    }

    public Homework getHomeworkQuestion(String name, int courseId) {
        return Homework.getHomework(context, courseId, name);
    }
}
