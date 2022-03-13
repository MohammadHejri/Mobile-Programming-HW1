package edu.sharif.courseware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import adapters.CourseRecyclerAdapter;

public class StudentMainPage extends AppCompatActivity implements CourseRecyclerAdapter.OnCourseListener {

    Button joinClassBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main_page);
        joinClassBtn = (Button) findViewById(R.id.joinClassBtn);

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