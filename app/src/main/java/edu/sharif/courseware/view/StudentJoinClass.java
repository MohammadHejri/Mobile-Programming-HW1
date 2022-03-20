package edu.sharif.courseware.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.sharif.courseware.R;
import edu.sharif.courseware.adapters.CourseRecyclerAdapter;
import edu.sharif.courseware.controller.CourseController;
import edu.sharif.courseware.model.Course;
import edu.sharif.courseware.model.LoginRepository;

public class StudentJoinClass extends AppCompatActivity implements CourseRecyclerAdapter.OnCourseListener {

    private CourseController courseController;

    private RecyclerView rvClasses;
    private CourseRecyclerAdapter adapter;
    private ArrayList<Course> mCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_join_class);

        courseController = new CourseController(StudentJoinClass.this);

        Button joinClassBtn = findViewById(R.id.joinClassBtn);
        TextView classIdJoin = findViewById(R.id.classIdJoin);

        mCourses = courseController.getStudentNotEnrolledCourses(LoginRepository.getInstance().getUsername());
        rvClasses = findViewById(R.id.studentJoinList);
        adapter = new CourseRecyclerAdapter(mCourses, this);
        rvClasses.setAdapter(adapter);
        rvClasses.setLayoutManager(new LinearLayoutManager(this));

        joinClassBtn.setOnClickListener(view -> {
            String classId = classIdJoin.getText().toString();
            String error = courseController.getCourseNameError(classId);
            classIdJoin.setError(error);
            if (error == null) {
                int position = -1;
                for (Course course : mCourses)
                    if (course.getId() == Integer.parseInt(classId))
                        position = mCourses.indexOf(course);
                if (position == -1) {
                    classIdJoin.setError("Course not found");
                } else {
                    int finalPosition = position;
                    new AlertDialog.Builder(this)
                            .setTitle("Course Enrollment")
                            .setMessage("Are you sure to join " + mCourses.get(position).getName() + " course?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    courseController.addStudentToCourse(LoginRepository.getInstance().getUsername(), mCourses.get(finalPosition).getId());
                                    mCourses.remove(finalPosition);
                                    adapter.notifyItemRemoved(finalPosition);
                                    Toast.makeText(StudentJoinClass.this, "Successfully joined!", Toast.LENGTH_SHORT).show();
                                    classIdJoin.setText(null);
                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
            }
        });
    }

    @Override
    public void onCourseClick(int position) {
        if (position == -1)
            return;
        new AlertDialog.Builder(this)
                .setTitle("Course Enrollment")
                .setMessage("Are you sure to join " + mCourses.get(position).getName() + " course?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        courseController.addStudentToCourse(LoginRepository.getInstance().getUsername(), mCourses.get(position).getId());
                        mCourses.remove(position);
                        adapter.notifyItemRemoved(position);
                        Toast.makeText(StudentJoinClass.this, "Successfully joined!", Toast.LENGTH_SHORT).show();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }
}