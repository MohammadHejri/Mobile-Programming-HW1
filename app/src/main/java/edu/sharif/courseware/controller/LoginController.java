package edu.sharif.courseware.controller;

import android.content.Context;

import java.util.regex.Pattern;

import edu.sharif.courseware.model.Professor;
import edu.sharif.courseware.model.Student;
import edu.sharif.courseware.model.User;

public class LoginController {

    private Context context;

    public LoginController(Context context) {
        this.context = context;
    }

    public String getUsernameError(String username) {
        if (username.isEmpty())
            return "This field can not be blank";
        if (username.length() < 3)
            return "Username must be at least 3 characters long";
        if (!Pattern.matches("^[a-zA-Z0-9]+$", username))
            return "Only letters (a-z) and numbers (0-9) are allowed";
        return null;
    }

    public String getPasswordError(String password) {
        if (password.isEmpty())
            return "This field can not be blank";
        if (password.length() < 4)
            return "Password must be at least 4 characters long";
        return null;
    }

    public User getLoginResult(String username, String password) {
        try {
            Student student = Student.getStudent(context, username);
            if (student.getPassword().equals(password))
                return student;
            else
                return null;
        } catch (Exception studentException) {
            try {
                Professor professor = Professor.getProfessor(context, username);
                if (professor.getPassword().equals(password))
                    return professor;
                else
                    return null;
            } catch (Exception professorException) {
                return null;
            }
        }
    }

}
