package edu.sharif.courseware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.CourseRecyclerAdapter;
import model.Course;

public class StudentJoinClass extends AppCompatActivity implements CourseRecyclerAdapter.OnCourseListener {

    Button joinClassBtn;
    TextView classIdJoin;
    private ArrayList<Course> mCourses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_join_class);

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
        //TODO
    }
}