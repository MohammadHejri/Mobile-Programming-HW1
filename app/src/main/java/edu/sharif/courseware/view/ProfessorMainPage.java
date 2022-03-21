package edu.sharif.courseware.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import edu.sharif.courseware.R;
import edu.sharif.courseware.adapters.CourseRecyclerAdapter;
import edu.sharif.courseware.controller.CourseController;
import edu.sharif.courseware.model.Course;
import edu.sharif.courseware.model.CourseRepository;
import edu.sharif.courseware.model.LoginRepository;

public class ProfessorMainPage extends AppCompatActivity implements CourseRecyclerAdapter.OnCourseListener {

    private CourseController courseController;

    private RecyclerView rvClasses;
    private CourseRecyclerAdapter adapter;
    private ArrayList<Course> mCourses;

    @SuppressLint("RestrictedApi")
    private void createClassViaPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create Course");
        builder.setMessage("Enter your course name and then press OK");
        builder.setIcon(R.drawable.ic_create);

        final EditText input = new EditText(this);
        input.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        input.setSingleLine();
        input.setHint("Course name");
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
                String courseName = input.getText().toString();
                String error = courseController.getCourseNameError(courseName);
                input.setError(error);
                if (error == null) {
                    String message = "Successfully created " + courseName + " course";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    Course newCourse = courseController.createCourse(courseName, LoginRepository.getInstance().getUsername());
                    mCourses.add(newCourse);
                    adapter.notifyItemInserted(mCourses.size() - 1);
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_main_page);

        courseController = new CourseController(this);

        Button createClassBtn = findViewById(R.id.createClassBtn);
        createClassBtn.setOnClickListener(view -> createClassViaPopUp());

        mCourses = courseController.getCoursesByProfessorID(LoginRepository.getInstance().getUsername());
        rvClasses = findViewById(R.id.professorMainList);
        rvClasses.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CourseRecyclerAdapter(mCourses,this);
        rvClasses.setAdapter(adapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        mCourses = courseController.getCoursesByProfessorID(LoginRepository.getInstance().getUsername());
        adapter.changeDataSet(mCourses);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCourseClick(int position) {
        Course course = mCourses.get(position);
        CourseRepository.getInstance().setCourseId(String.valueOf(course.getId()));
        startActivity(new Intent(ProfessorMainPage.this, ProfessorCoursePage.class));
    }

}