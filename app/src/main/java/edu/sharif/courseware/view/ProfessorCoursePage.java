package edu.sharif.courseware.view;

import androidx.annotation.GravityInt;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

import edu.sharif.courseware.R;
import edu.sharif.courseware.adapters.CourseRecyclerAdapter;
import edu.sharif.courseware.adapters.HomeworkRecyclerAdapter;
import edu.sharif.courseware.controller.CourseController;
import edu.sharif.courseware.controller.HomeworkController;
import edu.sharif.courseware.model.Course;
import edu.sharif.courseware.model.CourseRepository;
import edu.sharif.courseware.model.Homework;
import edu.sharif.courseware.model.LoginRepository;

public class ProfessorCoursePage extends AppCompatActivity implements HomeworkRecyclerAdapter.OnHomeworkListener {

    private HomeworkController homeworkController;

    private RecyclerView rvClasses;
    private HomeworkRecyclerAdapter adapter;
    private ArrayList<Homework> mHomeworks;

    private void createHomeworkViaPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter homework description");

        final EditText nameInput = new EditText(this);
        final EditText questionInput = new EditText(this);
        nameInput.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        nameInput.setHint("Name");
        questionInput.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                getWindowManager().getDefaultDisplay().getHeight() / 4));
        questionInput.setHint("Question");
        questionInput.setGravity(Gravity.BOTTOM);
        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(nameInput);
        layout.addView(questionInput);
        builder.setView(layout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String homeworkName = nameInput.getText().toString();
                String homeworkQuestion = questionInput.getText().toString();
                String error = homeworkController.getHomeworkError(homeworkName);
                nameInput.setError(error);
                if (error == null) {
                    Homework newHomework = homeworkController.createHomework(CourseRepository.getInstance().getCourseId(), homeworkName, homeworkQuestion);
                    mHomeworks.add(newHomework);
                    adapter.notifyItemInserted(mHomeworks.size() - 1);
                    dialog.dismiss();
                }
            }});
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

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