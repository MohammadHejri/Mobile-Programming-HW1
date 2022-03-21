package edu.sharif.courseware.controller;

import android.content.Context;

import java.util.ArrayList;

import edu.sharif.courseware.model.Homework;
import edu.sharif.courseware.model.Submission;

public class SubmissionController {

    private Context context;

    public SubmissionController(Context context) {
        this.context = context;
    }

    public String getSubmissionError(String mark) {
        if (mark.isEmpty())
            return "This field can not be blank";
        try {
            float m = Float.parseFloat(mark);
            if (m > 20)
                return "Mark can not be more than 20";
            if (m < 0)
                return "Mark can not be less than 0";
        } catch (Exception e) {
            return "Only numbers are allowed";
        }
        return null;
    }

    public ArrayList<Submission> getSubmissionsOfHomework(int course_id, String hw_name) {
        try {
            return Submission.getSubmissionsOfHomework(context, course_id, hw_name);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void updateSubmissionMark(int course_id, String hwName, String studentUsername, float mark) {
        try {
            Submission.updateSubmissionMark(context, course_id, hwName, studentUsername, mark);
        } catch (Exception ignored) {
        }
    }
}
