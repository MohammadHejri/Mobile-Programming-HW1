package edu.sharif.courseware.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

import edu.sharif.courseware.R;
import edu.sharif.courseware.adapters.CourseRecyclerAdapter;
import edu.sharif.courseware.controller.CourseController;
import edu.sharif.courseware.model.Course;
import edu.sharif.courseware.model.LoginRepository;

public class StudentMainPage extends AppCompatActivity implements CourseRecyclerAdapter.OnCourseListener {

    private CourseController courseController;

    private RecyclerView rvClasses;
    private CourseRecyclerAdapter adapter;
    private ArrayList<Course> mCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main_page);

        courseController = new CourseController(StudentMainPage.this);

        Button joinClassBtn = findViewById(R.id.joinClassBtn);
        joinClassBtn.setOnClickListener(view -> startActivity(new Intent(StudentMainPage.this, StudentJoinClass.class)));

        mCourses = courseController.getStudentEnrolledCourses(LoginRepository.getInstance().getUsername());
        rvClasses = (RecyclerView) findViewById(R.id.studentMainList);
        rvClasses.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CourseRecyclerAdapter(mCourses, this);
        rvClasses.setAdapter(adapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        mCourses = courseController.getStudentEnrolledCourses(LoginRepository.getInstance().getUsername());
        adapter.changeDataSet(mCourses);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCourseClick(int position) {
        //TODO
    }
}