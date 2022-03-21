package edu.sharif.courseware.controller;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import edu.sharif.courseware.R;
import edu.sharif.courseware.model.LoginRepository;
import edu.sharif.courseware.view.LoginActivity;
import edu.sharif.courseware.view.ProfessorMainPage;

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
        Toast.makeText(context,"Successfully signed out" , Toast.LENGTH_SHORT).show();
        context.startActivity(intent);
    }

    public void confirmationPopUp(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Sign Out")
                .setMessage("Are you sure to sign out?")
                .setIcon(R.drawable.ic_logout)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    public void onClick(DialogInterface dialog, int whichButton) {
                        logout(context);
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }
}
