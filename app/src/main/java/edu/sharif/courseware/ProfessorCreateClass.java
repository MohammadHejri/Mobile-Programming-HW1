package edu.sharif.courseware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfessorCreateClass extends AppCompatActivity {

    Button createClassBtn;
    TextView classNameText;
    TextView lecturerNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_create_class);
        createClassBtn = (Button) findViewById(R.id.createClassBtn);
        classNameText = (TextView) findViewById(R.id.classNameText);
        lecturerNameText = (TextView) findViewById(R.id.lecturerNameText);

        createClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String className = classNameText.getText().toString();
                String lecturerName = lecturerNameText.getText().toString();
                //TODO
            }
        });
    }
}