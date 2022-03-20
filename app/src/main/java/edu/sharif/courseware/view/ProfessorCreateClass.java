package edu.sharif.courseware.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import edu.sharif.courseware.R;
import edu.sharif.courseware.controller.CourseController;
import edu.sharif.courseware.controller.UserController;
import edu.sharif.courseware.model.LoginRepository;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfessorCreateClass extends AppCompatActivity {

    /*//Error Dialog
    AlertDialog.Builder builderDialog;
    AlertDialog alertDialog;*/

    private CourseController courseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_create_class);

        courseController = new CourseController(ProfessorCreateClass.this);

        Button createClassBtn = (Button) findViewById(R.id.createClassBtn);
        TextView classNameText = (TextView) findViewById(R.id.classNameText);
        createClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseName = classNameText.getText().toString();
                String error = courseController.getCourseNameError(courseName);
                classNameText.setError(error);
                if (error == null) {
                    courseController.createCourse(courseName, LoginRepository.getInstance().getUsername());
                    finish();
                }
            }
        });
    }

    /*private void showAlertDialog(int myLayout, String errorMessage) {
        builderDialog = new AlertDialog.Builder(this);
        View layoutView = getLayoutInflater().inflate(myLayout, null);

        AppCompatButton dialogButton = layoutView.findViewById(R.id.bottomOk);
        TextView textView = layoutView.findViewById(R.id.popUpErrorMessage);


        textView.setText(errorMessage);
        builderDialog.setView(layoutView);
        alertDialog = builderDialog.create();
        alertDialog.show();

        //Close the dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }*/
}