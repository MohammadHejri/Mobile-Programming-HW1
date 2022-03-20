package edu.sharif.courseware.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import edu.sharif.courseware.R;
import edu.sharif.courseware.adapters.CourseRecyclerAdapter;
import edu.sharif.courseware.controller.CourseController;
import edu.sharif.courseware.controller.UserController;
import edu.sharif.courseware.model.Course;

public class StudentMainPage extends AppCompatActivity implements CourseRecyclerAdapter.OnCourseListener {

    Button joinClassBtn;
    RecyclerView rvClasses;
    CourseRecyclerAdapter adapter;
    private CourseController courseController;
    private UserController userController;
    private ArrayList<Course> mCourses = new ArrayList<>();
    private String studentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main_page);

        Intent intent = getIntent();
        this.studentUsername = intent.getStringExtra("studentUsername");

        //Instancing Controllers.
        courseController = new CourseController(StudentMainPage.this);
        userController = new UserController(StudentMainPage.this);

        //Instancing Views.
        joinClassBtn = (Button) findViewById(R.id.joinClassBtn);
        rvClasses = (RecyclerView) findViewById(R.id.studentNewClassList);

        //Recycler View.
        mCourses = Course.getStudentEnrolledCourses(StudentMainPage.this,studentUsername);
        adapter = new CourseRecyclerAdapter(mCourses,this);
        rvClasses.setAdapter(adapter);
        rvClasses.setLayoutManager(new LinearLayoutManager(this));

        //Add functionality.
        joinClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentMainPage.this, StudentJoinClass.class);
                intent.putExtra("studentUsername", studentUsername);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO
    }

    @Override
    public void onCourseClick(int position) {
        //TODO
    }
}