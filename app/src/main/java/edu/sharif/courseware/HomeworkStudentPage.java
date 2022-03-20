package edu.sharif.courseware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.sharif.courseware.controller.CourseController;
import edu.sharif.courseware.controller.HomeworkController;
import edu.sharif.courseware.controller.UserController;
import edu.sharif.courseware.model.Homework;

public class HomeworkStudentPage extends AppCompatActivity {

    Button saveButton;
    TextView question;
    TextView grade;
    EditText homeworkAnswer;

    private CourseController courseController;
    private UserController userController;
    private HomeworkController homeworkController;


    private int courseId;
    private String homeworkName;
    private String studentId;
    private Homework homework;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_student_page);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        //Intent.
        courseId = extras.getInt("coursesId");
        homeworkName = extras.getString("homeworkName");
        studentId = extras.getString("studentId");

        //Controllers.
        userController = new UserController(HomeworkStudentPage.this);
        courseController = new CourseController(HomeworkStudentPage.this);
        homeworkController = new HomeworkController(HomeworkStudentPage.this);


        //Instancing Views.
        homework = homeworkController.getHomeworkQuestion(homeworkName, courseId);

        saveButton = (Button) findViewById(R.id.saveButton);
        question = (TextView) findViewById(R.id.homeworkQuestion);
        grade = (TextView) findViewById(R.id.homeworkGrade);
        homeworkAnswer = (EditText) findViewById(R.id.homeworkAnswer);


        question.setText(homework.getQuestion());


        //grade.setText("Your grade : ");


        //homeworkAnswer.setText(homeworkController.);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Save student's answer.
            }
        });


    }
}