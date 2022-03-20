package edu.sharif.courseware.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import edu.sharif.courseware.R;
import edu.sharif.courseware.adapters.CourseRecyclerAdapter;
import edu.sharif.courseware.controller.CourseController;
import edu.sharif.courseware.controller.UserController;
import edu.sharif.courseware.model.Course;
import edu.sharif.courseware.model.LoginRepository;

public class StudentJoinClass extends AppCompatActivity implements CourseRecyclerAdapter.OnCourseListener {

    Button joinClassBtn;
    TextView classIdJoin;
    RecyclerView rvClasses;
    CourseRecyclerAdapter adapter;
    private ArrayList<Course> mCourses = new ArrayList<>();
    private CourseController courseController;
    private UserController userController;
    private String studentUsername;

    AlertDialog.Builder builderDialog;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_join_class);

        this.studentUsername = LoginRepository.getInstance().getUsername();

        //Instancing Controllers
        courseController = new CourseController(StudentJoinClass.this);
        userController = new UserController(StudentJoinClass.this);


        //Instancing Views.
        joinClassBtn = (Button) findViewById(R.id.joinClassBtn);
        classIdJoin = (TextView) findViewById(R.id.classIdJoin);
        rvClasses = (RecyclerView) findViewById(R.id.studentJoinList);


        //Recycler View.
        try {
            mCourses = Course.getStudentNotEnrolledCourses(StudentJoinClass.this, LoginRepository.getInstance().getUsername());
            Log.d("DEBUG", Integer.toString(mCourses.size()));
        } catch (Exception e) {
            mCourses = new ArrayList<>();
        }
        adapter = new CourseRecyclerAdapter(mCourses, this);
        rvClasses.setAdapter(adapter);
        rvClasses.setLayoutManager(new LinearLayoutManager(this));

        //Adding functionality.
        joinClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String classId = classIdJoin.getText().toString();
                if (classId.isEmpty()) {
                    showAlertDialog(R.layout.my_error_dialog, "Class Id is empty.");
                } else {
                    courseController.addStudentToCourse(studentUsername, Integer.parseInt(classId));
                    finish();
                }
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
        //TODO
    }

    private void showAlertDialog(int myLayout, String errorMessage) {
        builderDialog = new AlertDialog.Builder(this);
        View layoutView = getLayoutInflater().inflate(myLayout, null);

        AppCompatButton dialogButton = layoutView.findViewById(R.id.bottomOk);
        TextView textView = layoutView.findViewById(R.id.popUpErrorMessage);


        textView.setText(errorMessage);
        builderDialog.setView(layoutView);
        alertDialog = builderDialog.create();
        alertDialog.show();

        //Close the dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}