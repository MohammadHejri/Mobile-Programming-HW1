package edu.sharif.courseware.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.sharif.courseware.R;
import edu.sharif.courseware.model.Course;
import edu.sharif.courseware.model.CourseRepository;
import edu.sharif.courseware.model.Homework;
import edu.sharif.courseware.model.HomeworkRepository;
import edu.sharif.courseware.model.LoginRepository;
import edu.sharif.courseware.model.Submission;

public class StudentHomeworkPage extends AppCompatActivity {

    Submission submission;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_homework_page);
        Course course = CourseRepository.getInstance().getCourse(getApplicationContext());
        Homework homework = Homework.getHomework(getApplicationContext(),course.getId(),
                HomeworkRepository.getInstance().getHomeworkName());
        TextView homeworkName = findViewById(R.id.homeworkName);
        TextView homeworkQuestion = findViewById(R.id.homeworkQuestion);
        TextView homeworkGrade = findViewById(R.id.homeworkGrade);
        TextView previousAnswer = findViewById(R.id.previousAnswer);
        EditText homeworkAnswer = findViewById(R.id.homeworkAnswer);
        Button submitButton = findViewById(R.id.submitButton);

        try {
            submission = Submission.getSubmission(getApplicationContext(),course.getId(),homework.getName(),
                    LoginRepository.getInstance().getUsername());
            previousAnswer.setText(submission.getAnswer());
            homeworkGrade.setText(submission.getMark() == -1 ? "Grade : N/A" : "Grade : " + Float.toString(submission.getMark()));
        }catch (Exception e) {
            submission = null;
            homeworkGrade.setText("Grade : N/A");
            previousAnswer.setText("You have not answered this question.");
        }

        submitButton.setEnabled(!homeworkGrade.equals("Grade : N/A"));
        homeworkName.setText(homework.getName());
        homeworkQuestion.setText(homework.getQuestion());
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer = homeworkAnswer.getText().toString();
                previousAnswer.setText(answer);
                if(submission == null){
                    submission = Submission.createSubmission(getApplicationContext(), course.getId(),
                            homework.getName(), LoginRepository.getInstance().getUsername(), answer);
                    Toast.makeText(getApplicationContext(), "Your answer has been submitted.", Toast.LENGTH_SHORT).show();
                } else {
                    Submission.updateSubmissionAnswer(getApplicationContext(), course.getId(),
                            homework.getName(), LoginRepository.getInstance().getUsername(), answer);
                    Toast.makeText(getApplicationContext(), "Your answer has been updated.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}