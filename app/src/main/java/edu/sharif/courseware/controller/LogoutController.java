package edu.sharif.courseware.controller;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import edu.sharif.courseware.model.LoginRepository;
import edu.sharif.courseware.view.LoginActivity;

public class LogoutController  {

    private static LogoutController instance;

    public static LogoutController getInstance() {
        if (instance == null)
            instance = new LogoutController();
        return instance;
    }

    public void logout(Context context) {
        LoginRepository.getInstance().logOut();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|FLAG_ACTIVITY_CLEAR_TOP);
        Toast.makeText(context,"Sad to see you go :(" , Toast.LENGTH_LONG).show();
        context.startActivity(intent);
    }
}
