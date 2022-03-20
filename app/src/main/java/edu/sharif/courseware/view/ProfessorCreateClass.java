package edu.sharif.courseware.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import edu.sharif.courseware.R;
import edu.sharif.courseware.controller.CourseController;
import edu.sharif.courseware.controller.UserController;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfessorCreateClass extends AppCompatActivity {

    Button createClassBtn;
    TextView classNameText;
    TextView lecturerNameText;
    //Error Dialog
    AlertDialog.Builder builderDialog;
    AlertDialog alertDialog;

    private CourseController courseController;
    private UserController userController;
    private String professorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Probably needs to be deleted.
        Intent intent = getIntent();
        this.professorName = intent.getStringExtra("professorName");

        //Controllers.
        userController = new UserController(ProfessorCreateClass.this);
        courseController = new CourseController(ProfessorCreateClass.this);



        setContentView(R.layout.activity_professor_create_class);
        createClassBtn = (Button) findViewById(R.id.createClassBtn);
        classNameText = (TextView) findViewById(R.id.classNameText);
        lecturerNameText = (TextView) findViewById(R.id.lecturerNameText);

        createClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String className = classNameText.getText().toString();
                String lecturerName = lecturerNameText.getText().toString();
                if (className.isEmpty() || lecturerName.isEmpty()) {
                    String errorMessage = "please fill out all required fields.";
                    showAlertDialog(R.layout.my_error_dialog, errorMessage);
                }
                courseController.createCourse(className, lecturerName);
                //TODO
                finish();
            }
        });
    }

    private void showAlertDialog(int myLayout, String errorMessage) {
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
    }
}