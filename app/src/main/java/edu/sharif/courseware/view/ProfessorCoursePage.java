package edu.sharif.courseware.view;

import androidx.annotation.GravityInt;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.sharif.courseware.R;
import edu.sharif.courseware.adapters.CourseRecyclerAdapter;
import edu.sharif.courseware.adapters.HomeworkRecyclerAdapter;
import edu.sharif.courseware.controller.CourseController;
import edu.sharif.courseware.controller.HomeworkController;
import edu.sharif.courseware.model.Course;
import edu.sharif.courseware.model.CourseRepository;
import edu.sharif.courseware.model.Homework;
import edu.sharif.courseware.model.HomeworkRepository;
import edu.sharif.courseware.model.LoginRepository;

public class ProfessorCoursePage extends AppCompatActivity implements HomeworkRecyclerAdapter.OnHomeworkListener {

    private HomeworkController homeworkController;

    private RecyclerView rvClasses;
    private HomeworkRecyclerAdapter adapter;
    private ArrayList<Homework> mHomeworks;

    @SuppressLint("RestrictedApi")
    private void createHomeworkViaPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create Homework");
        builder.setMessage("Enter your homework name and question.");
        builder.setIcon(R.drawable.ic_homework);

        final EditText nameInput = new EditText(this);
        nameInput.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        nameInput.setHint("Name");
        nameInput.setSingleLine();

        final EditText questionInput = new EditText(this);
        questionInput.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                getWindowManager().getDefaultDisplay().getHeight() / 4));
        questionInput.setHint("Question");
        questionInput.setGravity(Gravity.TOP);
        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(nameInput);
        layout.addView(questionInput);
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
            @Override
            public void onClick(View v)
            {
                String homeworkName = nameInput.getText().toString();
                String homeworkQuestion = questionInput.getText().toString();
                String errorName = homeworkController.getHomeworkError(homeworkName);
                String errorQuestion = homeworkController.getHomeworkError(homeworkQuestion);
                nameInput.setError(errorName);
                questionInput.setError(errorQuestion);
                if (errorName == null && errorQuestion == null) {
                    Homework newHomework = homeworkController.createHomework(CourseRepository.getInstance().getCourseId(), homeworkName, homeworkQuestion);
                    if (newHomework != null) {
                        String message = "Successfully created " + homeworkName + " homework";
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        mHomeworks.add(newHomework);
                        adapter.notifyItemInserted(mHomeworks.size() - 1);
                        dialog.dismiss();
                    } else {
                        nameInput.setError("Homework name already taken");
                    }
                }
            }
        });
    }

    @SuppressLint("RestrictedApi")
    private void enterHomeworkManually() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Homework Page");
        builder.setMessage("Enter your homework name");
        builder.setIcon(R.drawable.ic_enter);

        final EditText input = new EditText(this);
        input.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        input.setSingleLine();
        input.setHint("Homework Name");
        builder.setView(input, 50, 0, 50, 0);
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
            @Override
            public void onClick(View v)
            {
                String homeworkName = input.getText().toString();
                String error = homeworkController.getHomeworkError(homeworkName);
                input.setError(error);
                if (error == null) {
                    Homework homework = homeworkController.getHomework(CourseRepository.getInstance().getCourseId(), homeworkName);
                    if (homework != null) {
                        String message = "Successfully entered " + homework.getName() + " homework";
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        HomeworkRepository.getInstance().setHomeworkName(homeworkName);
                        startActivity(new Intent(ProfessorCoursePage.this, ProfessorCoursePage.class));
                        dialog.dismiss();
                    } else {
                        input.setError("Homework not found");
                    }
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_course_page);

        homeworkController = new HomeworkController(this);

        Button createHomeworkButton = findViewById(R.id.createHomeworkButton);
        createHomeworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createHomeworkViaPopUp();
            }
        });

        Button manualEnterButton = (Button) findViewById(R.id.manualEnterButton);
        manualEnterButton.setOnClickListener(view -> enterHomeworkManually());

        String username = LoginRepository.getInstance().getUsername();
        String courseID = CourseRepository.getInstance().getCourseId();
        Course course = new CourseController(this).getOwnedCourse(courseID, username);
        ((TextView) findViewById(R.id.studentJoinTitle)).setText(course.getName() + " Homeworks");
        String professorName = course.getOwner().getFirstname() + " " + course.getOwner().getLastname();
        ((TextView) findViewById(R.id.professorNameTextView)).setText("Instructed by " + professorName);

        mHomeworks = homeworkController.getHomeworksByCourse(Integer.parseInt(CourseRepository.getInstance().getCourseId()));
        rvClasses = findViewById(R.id.courseHomeworks);
        rvClasses.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HomeworkRecyclerAdapter(mHomeworks,this);
        rvClasses.setAdapter(adapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        mHomeworks = homeworkController.getHomeworksByCourse(Integer.parseInt(CourseRepository.getInstance().getCourseId()));
        adapter.changeDataSet(mHomeworks);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onHomeworkClick(int position) {
        // TODO
    }

}