package edu.sharif.courseware.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import edu.sharif.courseware.R;
import edu.sharif.courseware.adapters.HomeworkRecyclerAdapter;
import edu.sharif.courseware.controller.HomeworkController;
import edu.sharif.courseware.model.CourseRepository;
import edu.sharif.courseware.model.Homework;

public class StudentCoursePage extends AppCompatActivity implements HomeworkRecyclerAdapter.OnHomeworkListener {

    private HomeworkController homeworkController;

    private RecyclerView rvClasses;
    private HomeworkRecyclerAdapter adapter;
    private ArrayList<Homework> mHomeworks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_page);

        homeworkController = new HomeworkController(this);

        mHomeworks = homeworkController.getHomeworksByCourse(Integer.parseInt(CourseRepository.getInstance().getCourseId()));
        rvClasses = findViewById(R.id.courseHomeworks);
        rvClasses.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HomeworkRecyclerAdapter(mHomeworks,this);
        rvClasses.setAdapter(adapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        mHomeworks = homeworkController.getHomeworksByCourse(Integer.parseInt(CourseRepository.getInstance().getCourseId()));
        adapter.changeDataSet(mHomeworks);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onHomeworkClick(int position) {
        Homework homework = mHomeworks.get(position);
        //CourseRepository.getInstance().setCourseId(String.valueOf(course.getId()));
        Toast.makeText(getApplicationContext(), homework.getName(), Toast.LENGTH_LONG).show();
        //startActivity(new Intent(StudentCoursePage.this, ProfessorCoursePage.class));
    }

}