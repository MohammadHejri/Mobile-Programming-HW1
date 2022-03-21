package edu.sharif.courseware.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.sharif.courseware.R;
import edu.sharif.courseware.adapters.HomeworkRecyclerAdapter;
import edu.sharif.courseware.adapters.SubmissionRecyclerAdapter;
import edu.sharif.courseware.controller.CourseController;
import edu.sharif.courseware.controller.HomeworkController;
import edu.sharif.courseware.controller.LogoutController;
import edu.sharif.courseware.controller.SubmissionController;
import edu.sharif.courseware.model.Course;
import edu.sharif.courseware.model.CourseRepository;
import edu.sharif.courseware.model.Homework;
import edu.sharif.courseware.model.HomeworkRepository;
import edu.sharif.courseware.model.LoginRepository;
import edu.sharif.courseware.model.Submission;

public class ProfessorHomeworkPage extends AppCompatActivity implements SubmissionRecyclerAdapter.OnSubmissionListener{

    private SubmissionController submissionController;

    private RecyclerView rvClasses;
    private SubmissionRecyclerAdapter adapter;
    private ArrayList<Submission> mSubmissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_homework_page);

        submissionController = new SubmissionController(this);

        Course course = CourseRepository.getInstance().getCourse(getApplicationContext());
        Homework homework = Homework.getHomework(getApplicationContext(),course.getId(),
                HomeworkRepository.getInstance().getHomeworkName());

        ImageView editNameImageView = findViewById(R.id.editNameIcon);
        editNameImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });

        TextView homeworkNameTextView = findViewById(R.id.homeworkName);
        homeworkNameTextView.setText(homework.getName());
        TextView homeworkQuestionTextView = findViewById(R.id.homeworkQuestion);
        homeworkQuestionTextView.setText(homework.getQuestion());

        mSubmissions = submissionController.getSubmissionsOfHomework(course.getId(), homework.getName());
        rvClasses = findViewById(R.id.homeworkSubmissions);
        rvClasses.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubmissionRecyclerAdapter(mSubmissions,this);
        rvClasses.setAdapter(adapter);

        ImageView logoutIcon = findViewById(R.id.logoutIcon);
        logoutIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogoutController.getInstance().logout(getApplicationContext());
                finish();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        Course course = CourseRepository.getInstance().getCourse(getApplicationContext());
        Homework homework = Homework.getHomework(getApplicationContext(),course.getId(),
                HomeworkRepository.getInstance().getHomeworkName());
        mSubmissions = submissionController.getSubmissionsOfHomework(course.getId(), homework.getName());
        adapter.changeDataSet(mSubmissions);
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onSubmissionClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mark Homework Submission");
        builder.setMessage("Read student answer and mark it");
        builder.setIcon(R.drawable.ic_grading);

        Course course = CourseRepository.getInstance().getCourse(getApplicationContext());
        Homework homework = Homework.getHomework(getApplicationContext(),course.getId(),
                HomeworkRepository.getInstance().getHomeworkName());
        Submission submission = mSubmissions.get(position);

        final TextView answerTextView = new TextView(this);
        answerTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        answerTextView.setText(submission.getAnswer());

        final EditText markInput = new EditText(this);
        markInput.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        markInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        markInput.setHint("Mark");
        markInput.setSingleLine();

        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(answerTextView);
        layout.addView(markInput);
        builder.setView(layout, 50, 0, 50, 0);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v)
            {
                String mark = markInput.getText().toString();
                String error = submissionController.getSubmissionError(mark);
                markInput.setError(error);
                if (error == null) {
                    submissionController.updateSubmissionMark(course.getId(), homework.getName(), submission.getStudent().getUsername(), Float.parseFloat(mark));
                    String message = "Successfully marked " + submission.getStudent().getUsername() + "'s submission";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    mSubmissions = submissionController.getSubmissionsOfHomework(course.getId(), homework.getName());
                    adapter.changeDataSet(mSubmissions);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
    }
}