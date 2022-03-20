package edu.sharif.courseware.controller;

import android.content.Context;

import edu.sharif.courseware.model.Professor;
import edu.sharif.courseware.model.Student;

public class UserController {

    Context context;


    public UserController(Context context) {
        this.context = context;
    }

    public Student getStudent(String username) {
        return Student.getStudent(context,username);
    }

    public Professor getProfessor(String username) {
        return Professor.getProfessor(context, username);
    }

}
