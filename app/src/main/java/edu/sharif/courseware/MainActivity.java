package edu.sharif.courseware;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import model.Student;

public class MainActivity extends AppCompatActivity {

    Button kir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kir = (Button) findViewById(R.id.button2);
        kir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student an = Student.createStudent("an","an","sadegh","heyvon","98106015");
                Student an2 = Student.getStudent("an");
                Log.d("SAG", an2.getFirstname() + "\n" + an.getLastname() + "\n" + an2.getStudentNumber());
            }
        });
    }
}