package edu.sharif.courseware.model;

import android.content.Context;

public class HomeworkRepository {

    private static HomeworkRepository instance;
    private String homeworkName = null;

    public static HomeworkRepository getInstance() {
        if (instance == null)
            instance = new HomeworkRepository();
        return instance;
    }

    public String getHomeworkName() {
        return this.homeworkName;
    }

    public void setHomeworkName(String homeworkName) {
        this.homeworkName = homeworkName;
    }
}
