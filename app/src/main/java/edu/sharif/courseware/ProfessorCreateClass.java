package edu.sharif.courseware;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfessorCreateClass extends AppCompatActivity {

    Button createClassBtn;
    TextView classNameText;
    TextView lecturerNameText;

    //Error Dialog
    AlertDialog.Builder builderDialog;
    AlertDialog alertDialog;

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
                showAlertDialog(R.layout.my_error_dialog);
            }
        });
    }

    private void showAlertDialog(int myLayout) {
        builderDialog = new AlertDialog.Builder(this);
        View layoutView = getLayoutInflater().inflate(myLayout,null);

        AppCompatButton dialogButton = layoutView.findViewById(R.id.bottomOk);
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