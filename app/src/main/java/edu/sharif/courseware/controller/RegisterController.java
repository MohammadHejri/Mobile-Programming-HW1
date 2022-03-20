package edu.sharif.courseware.controller;

import android.content.Context;

import java.util.regex.Pattern;

import edu.sharif.courseware.model.Professor;
import edu.sharif.courseware.model.Student;
import edu.sharif.courseware.model.User;

public class RegisterController {

    private Context context;

    public RegisterController(Context context) {
        this.context = context;
    }

    public String getNameError(String name) {
        if (name.isEmpty())
            return "This field can not be blank";
        return null;
    }

    public String getUsernameError(String username) {
        if (username.isEmpty())
            return "This field can not be blank";
        if (username.length() < 3)
            return "Username must be at least 3 characters long";
        if (!Pattern.matches("^[a-zA-Z0-9]+$", username))
            return "Only letters (a-z) and numbers (0-9) are allowed";
        if (User.getUser(context, username) != null)
            return "Username already taken";
        return null;
    }

    public String getPasswordError(String password) {
        if (password.isEmpty())
            return "This field can not be blank";
        if (password.length() < 4)
            return "Password must be at least 4 characters long";
        return null;
    }

    public User registerStudent(String firstName, String lastName, String username, String password, String studentNumber) {
        if (User.getUser(context, username) == null)
            return Student.createStudent(context, username, password, firstName, lastName, studentNumber);
        return null;
    }

    public User registerProfessor(String firstName, String lastName, String username, String password, String universityName) {
        if (User.getUser(context, username) == null)
            return Professor.createProfessor(context, username, password, firstName, lastName, universityName);
        return null;
    }

}
