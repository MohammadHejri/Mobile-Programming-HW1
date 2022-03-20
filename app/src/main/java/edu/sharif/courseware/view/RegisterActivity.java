package edu.sharif.courseware.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import edu.sharif.courseware.R;
import edu.sharif.courseware.controller.RegisterController;

public class RegisterActivity extends AppCompatActivity {

    private RegisterController registerController;

    private boolean isFirstNameValid = false;
    private boolean isLastNameValid = false;
    private boolean isUsernameValid = false;
    private boolean isPasswordValid = false;
    private boolean isConfirmPasswordValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerController = new RegisterController(this);

        Button signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setEnabled(false);

        EditText firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        EditText lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        EditText confirmPasswordEditText = (EditText) findViewById(R.id.confirmPasswordEditText);
        EditText extraInfoEditText = (EditText) findViewById(R.id.extraInfoEditText);
        TextView signInTextView = (TextView) findViewById(R.id.signInTextView);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        RadioButton studentRadioButton = (RadioButton) findViewById(R.id.studentRadioButton);
        RadioButton professorRadioButton = (RadioButton) findViewById(R.id.professorRadioButton);

        studentRadioButton.setChecked(true);
        extraInfoEditText.setHint("Student number");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.studentRadioButton) {
                    extraInfoEditText.setHint("Student number");
                }else
                    extraInfoEditText.setHint("University name");
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String extraInfo = extraInfoEditText.getText().toString();
                if (!password.equals(confirmPassword)) {
                    String errorMessage = "Passwords don't match!";
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                } else {
                    if (radioGroup.getCheckedRadioButtonId() == R.id.studentRadioButton)
                        registerController.registerStudent(firstName, lastName, username, password, extraInfo);
                    else
                        registerController.registerProfessor(firstName, lastName, username, password, extraInfo);
                    String welcomeMessage = "Account successfully created!";
                    Toast.makeText(getApplicationContext(), welcomeMessage, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
            }
        });

        firstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String firstName = firstNameEditText.getText().toString();
                String error = registerController.getNameError(firstName);
                firstNameEditText.setError(error);
                isFirstNameValid = (error == null);
                signUpButton.setEnabled(signUpButtonEnabled());
            }
        });

        lastNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String lastName = lastNameEditText.getText().toString();
                String error = registerController.getNameError(lastName);
                lastNameEditText.setError(error);
                isLastNameValid = (error == null);
                signUpButton.setEnabled(signUpButtonEnabled());
            }
        });

        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String username = usernameEditText.getText().toString();
                String error = registerController.getUsernameError(username);
                usernameEditText.setError(error);
                isUsernameValid = (error == null);
                signUpButton.setEnabled(signUpButtonEnabled());
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password = passwordEditText.getText().toString();
                String error = registerController.getPasswordError(password);
                passwordEditText.setError(error);
                isPasswordValid = (error == null);
                signUpButton.setEnabled(signUpButtonEnabled());
            }
        });

        confirmPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password = confirmPasswordEditText.getText().toString();
                String error = registerController.getPasswordError(password);
                confirmPasswordEditText.setError(error);
                isConfirmPasswordValid = (error == null);
                signUpButton.setEnabled(signUpButtonEnabled());
            }
        });

        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }

    private boolean signUpButtonEnabled() {
        return isFirstNameValid
                && isLastNameValid
                && isUsernameValid
                && isPasswordValid
                && isConfirmPasswordValid;
    }
}