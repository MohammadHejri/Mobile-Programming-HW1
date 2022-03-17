package controller;

import android.content.Context;

import model.Course;
import model.Professor;
import model.Student;

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
