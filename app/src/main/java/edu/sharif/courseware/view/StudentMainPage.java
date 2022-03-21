package edu.sharif.courseware.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class StudentMainPage extends AppCompatActivity implements CourseRecyclerAdapter.OnCourseListener {

    private CourseController courseController;

    private RecyclerView rvClasses;
    private CourseRecyclerAdapter adapter;
    private ArrayList<Course> mCourses;

    @SuppressLint("RestrictedApi")
    private void enterCourseManually() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Course");
        builder.setMessage("Enter your course ID");
        builder.setIcon(R.drawable.ic_enter);

        final EditText input = new EditText(this);
        input.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        input.setSingleLine();
        input.setHint("Course ID");
        builder.setView(input, 50, 0, 50, 0);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String courseID = input.getText().toString();
                String error = courseController.getCourseNameError(courseID);
                input.setError(error);
                if (error == null) {
                    Course course = courseController.getEnrolledCourse(courseID, LoginRepository.getInstance().getUsername());
                    if (course != null) {
                        String message = "Successfully entered " + course.getName() + " course";
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        CourseRepository.getInstance().setCourseId(String.valueOf(course.getId()));
                        startActivity(new Intent(StudentMainPage.this, StudentCoursePage.class));
                        dialog.dismiss();
                    } else {
                        input.setError("Course not found");
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main_page);

        courseController = new CourseController(StudentMainPage.this);

        Button joinClassBtn = findViewById(R.id.joinClassBtn);
        joinClassBtn.setOnClickListener(view -> startActivity(new Intent(StudentMainPage.this, StudentJoinClass.class)));

        Button manualEnterButton = (Button) findViewById(R.id.manualEnterButton);
        manualEnterButton.setOnClickListener(view -> enterCourseManually());

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
        Course course = mCourses.get(position);
        CourseRepository.getInstance().setCourseId(String.valueOf(course.getId()));
        String message = "Successfully entered " + course.getName() + " course";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(StudentMainPage.this, StudentCoursePage.class));
    }
}