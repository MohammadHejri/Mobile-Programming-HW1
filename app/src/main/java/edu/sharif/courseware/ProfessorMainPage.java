package edu.sharif.courseware;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import adapters.CourseRecyclerAdapter;
import controller.CourseController;
import controller.UserController;
import model.Course;
import model.Professor;

public class ProfessorMainPage extends AppCompatActivity implements CourseRecyclerAdapter.OnCourseListener {

    Button joinClassBtn;
    RecyclerView rvClasses;
    CourseRecyclerAdapter adapter;


    private ArrayList<Course> mCourses = new ArrayList<>();
    private CourseController courseController;
    private UserController userController;
    private String professorName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_main_page);

        Intent intent = getIntent();
        this.professorName = intent.getStringExtra("professorName");

        //Instancing Controllers
        courseController = new CourseController(ProfessorMainPage.this);
        userController = new UserController(ProfessorMainPage.this);

        //Instancing Views.
        joinClassBtn = (Button) findViewById(R.id.joinClassBtn);
        rvClasses = (RecyclerView) findViewById(R.id.studentNewClassList);

        //Recycler View
        mCourses = (ArrayList<Course>) courseController.getAllCourses(professorName);
        adapter = new CourseRecyclerAdapter(mCourses,this);
        rvClasses.setAdapter(adapter);
        rvClasses.setLayoutManager(new LinearLayoutManager(this));

        //Add functionality.
        joinClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfessorMainPage.this, ProfessorCreateClass.class);
                intent.putExtra("professorName",professorName);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO
    }

    @Override
    public void onCourseClick(int position) {
        //TODO
    }

}