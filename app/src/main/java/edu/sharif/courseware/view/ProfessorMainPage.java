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

import java.util.ArrayList;

import edu.sharif.courseware.R;
import edu.sharif.courseware.adapters.CourseRecyclerAdapter;
import edu.sharif.courseware.controller.CourseController;
import edu.sharif.courseware.model.Course;
import edu.sharif.courseware.model.LoginRepository;

public class ProfessorMainPage extends AppCompatActivity implements CourseRecyclerAdapter.OnCourseListener {

    private CourseController courseController;

    private RecyclerView rvClasses;
    private CourseRecyclerAdapter adapter;
    private ArrayList<Course> mCourses;

    private void popUpEditText() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Name of the class.");

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String courseName = input.getText().toString();
                String error = courseController.getCourseNameError(courseName);
                input.setError(error);
                if (error == null) {
                    Course newCourse = courseController.createCourse(courseName, LoginRepository.getInstance().getUsername());
                    mCourses.add(newCourse);
                    adapter.notifyItemInserted(mCourses.size() - 1);
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
        setContentView(R.layout.activity_professor_main_page);

        courseController = new CourseController(this);

        Button createClassBtn = (Button) findViewById(R.id.createClassBtn);
        createClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpEditText();
                //startActivity(new Intent(ProfessorMainPage.this, ProfessorCreateClass.class));
            }
        });

        mCourses = courseController.getCoursesByProfessorID(LoginRepository.getInstance().getUsername());
        rvClasses = (RecyclerView) findViewById(R.id.professorMainList);
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
        // TODO
    }

}