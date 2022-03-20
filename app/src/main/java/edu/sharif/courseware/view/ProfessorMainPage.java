package edu.sharif.courseware.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import edu.sharif.courseware.R;
import edu.sharif.courseware.adapters.CourseRecyclerAdapter;
import edu.sharif.courseware.controller.CourseController;
import edu.sharif.courseware.model.Course;
import edu.sharif.courseware.model.CourseRepository;
import edu.sharif.courseware.model.LoginRepository;

public class ProfessorMainPage extends AppCompatActivity implements CourseRecyclerAdapter.OnCourseListener {

    private CourseController courseController;

    private RecyclerView rvClasses;
    private CourseRecyclerAdapter adapter;
    private ArrayList<Course> mCourses;

    private void createClassViaPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter course name");

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String courseName = input.getText().toString();
            String error = courseController.getCourseNameError(courseName);
            input.setError(error);
            if (error == null) {
                Course newCourse = courseController.createCourse(courseName, LoginRepository.getInstance().getUsername());
                mCourses.add(newCourse);
                adapter.notifyItemInserted(mCourses.size() - 1);
                dialog.dismiss();
        }
    });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_main_page);

        courseController = new CourseController(this);

        Button createClassBtn = findViewById(R.id.createClassBtn);
        createClassBtn.setOnClickListener(view -> createClassViaPopUp());

        mCourses = courseController.getCoursesByProfessorID(LoginRepository.getInstance().getUsername());
        rvClasses = findViewById(R.id.professorMainList);
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
        Course course = mCourses.get(position);
        CourseRepository.getInstance().setCourseId(String.valueOf(course.getId()));
        Toast.makeText(getApplicationContext(), course.getName(), Toast.LENGTH_LONG).show();
        startActivity(new Intent(ProfessorMainPage.this, ProfessorCoursePage.class));
    }

}