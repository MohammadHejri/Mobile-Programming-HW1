package edu.sharif.courseware.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    Button joinClassBtn;
    TextView classIdJoin;
    RecyclerView rvClasses;
    CourseRecyclerAdapter adapter;
    private ArrayList<Course> mCourses = new ArrayList<>();
    private CourseController courseController;
    private String studentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_join_class);

        this.studentUsername = LoginRepository.getInstance().getUsername();

        //Instancing Controllers
        courseController = new CourseController(StudentJoinClass.this);

        //Instancing Views.
        joinClassBtn = findViewById(R.id.joinClassBtn);
        classIdJoin = findViewById(R.id.classIdJoin);
        rvClasses = findViewById(R.id.studentJoinList);


        //Recycler View.
        try {
            mCourses = Course.getStudentNotEnrolledCourses(StudentJoinClass.this, LoginRepository.getInstance().getUsername());
        } catch (Exception e) {
            mCourses = new ArrayList<>();
        }
        adapter = new CourseRecyclerAdapter(mCourses, this);
        rvClasses.setAdapter(adapter);
        rvClasses.setLayoutManager(new LinearLayoutManager(this));

        //Adding functionality.
        joinClassBtn.setOnClickListener(view -> {
            String classId = classIdJoin.getText().toString();
            if (classId.isEmpty()) {
                CharSequence text = "Please enter the name of the class.";
                Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.show();
            } else {
                courseController.addStudentToCourse(studentUsername, Integer.parseInt(classId));
                finish();
            }
        });
    }

    @Override
    public void onCourseClick(int position) {
        if (position == -1)
            return;
        courseController.addStudentToCourse(studentUsername, mCourses.get(position).getId());
        mCourses.remove(position);
        adapter.notifyItemRemoved(position);
    }
}