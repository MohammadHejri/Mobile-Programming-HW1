package edu.sharif.courseware.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import edu.sharif.courseware.R;
import edu.sharif.courseware.adapters.CourseRecyclerAdapter;
import edu.sharif.courseware.controller.CourseController;
import edu.sharif.courseware.controller.UserController;
import edu.sharif.courseware.model.Course;
import edu.sharif.courseware.model.LoginRepository;

public class StudentMainPage extends AppCompatActivity implements CourseRecyclerAdapter.OnCourseListener {

    Button joinClassBtn;
    RecyclerView rvClasses;
    CourseRecyclerAdapter adapter;
    private CourseController courseController;
    private UserController userController;
    private ArrayList<Course> mCourses;
    private String studentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main_page);

        //Instancing Controllers.
        courseController = new CourseController(StudentMainPage.this);
        userController = new UserController(StudentMainPage.this);

        //Instancing Views.
        joinClassBtn = (Button) findViewById(R.id.joinClassBtn);
        rvClasses = (RecyclerView) findViewById(R.id.studentMainList);

        //Recycler View.
        try {
            mCourses = Course.getStudentEnrolledCourses(StudentMainPage.this, LoginRepository.getInstance().getUsername());
            Log.d("DEBUG",Integer.toString(mCourses.size()));
        }catch (Exception e) {
            Log.d("DEBUG",e.getMessage());
            mCourses = new ArrayList<>();
        }
        initRecycler();

        //Add functionality.
        joinClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentMainPage.this, StudentJoinClass.class));
            }
        });
    }

    private void initRecycler() {
        adapter = new CourseRecyclerAdapter(mCourses,this);
        rvClasses.setAdapter(adapter);
        rvClasses.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mCourses = Course.getStudentEnrolledCourses(StudentMainPage.this, LoginRepository.getInstance().getUsername());
        }catch (Exception e) {
            mCourses = new ArrayList<>();
        }
        initRecycler();
        //TODO
    }

    @Override
    public void onCourseClick(int position) {
        //TODO
    }
}