package edu.sharif.courseware;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import adapters.CourseRecyclerAdapter;
import model.Course;
import model.Professor;

public class StudentMainPage extends AppCompatActivity implements CourseRecyclerAdapter.OnCourseListener {

    Button joinClassBtn;
    RecyclerView rvClasses;
    CourseRecyclerAdapter adapter;
    private ArrayList<Course> mCourses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main_page);
        joinClassBtn = (Button) findViewById(R.id.joinClassBtn);

        rvClasses = (RecyclerView) findViewById(R.id.studentNewClassList);

        adapter = new CourseRecyclerAdapter(mCourses,this);

        Professor prof = new Professor("a","b","c","d","e");

        for(int i = 0; i < 20; i++) {
            mCourses.add(new Course(i,"Main",prof));
        }
        adapter = new CourseRecyclerAdapter(mCourses,this);

        rvClasses.setAdapter(adapter);
        rvClasses.setLayoutManager(new LinearLayoutManager(this));

        joinClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentMainPage.this, StudentJoinClass.class);
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