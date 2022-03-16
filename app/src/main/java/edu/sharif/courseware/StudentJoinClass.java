package edu.sharif.courseware;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import adapters.CourseRecyclerAdapter;
import model.Course;
import model.Professor;

public class StudentJoinClass extends AppCompatActivity implements CourseRecyclerAdapter.OnCourseListener {

    Button joinClassBtn;
    TextView classIdJoin;
    RecyclerView rvClasses;
    CourseRecyclerAdapter adapter;
    private ArrayList<Course> mCourses = new ArrayList<>();
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_join_class);


        rvClasses = (RecyclerView) findViewById(R.id.studentNewClassList);

        Professor prof = new Professor("a","b","c","d","e");

        for(int i = 0; i < 20; i++) {
            mCourses.add(new Course(12,"a",prof));
        }
        adapter = new CourseRecyclerAdapter(mCourses,this);

        rvClasses.setAdapter(adapter);
        rvClasses.setLayoutManager(new LinearLayoutManager(this));

        joinClassBtn = (Button) findViewById(R.id.joinClassBtn);
        classIdJoin = (TextView) findViewById(R.id.classIdJoin);

        joinClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String classId = classIdJoin.getText().toString();
                //TODO
            }
        });
    }

    @Override
    public void onCourseClick(int position) {
        if(position == -1)
            return;
        mCourses.remove(position);
        adapter.notifyItemRemoved(position);
        //TODO
    }
}