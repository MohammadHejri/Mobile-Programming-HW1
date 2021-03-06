package edu.sharif.courseware.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;

import edu.sharif.courseware.R;
import edu.sharif.courseware.controller.RegisterController;

public class RegisterActivity extends AppCompatActivity {

    private RegisterController registerController;

    private boolean isFirstNameValid = false;
    private boolean isLastNameValid = false;
    private boolean isUsernameValid = false;
    private boolean isPasswordValid = false;
    private boolean isConfirmPasswordValid = false;
    private boolean isUniNumberValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerController = new RegisterController(this);

        Button signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setEnabled(false);

        EditText firstNameEditText = findViewById(R.id.firstNameEditText);
        EditText lastNameEditText = findViewById(R.id.lastNameEditText);
        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        EditText confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        EditText extraInfoEditText = findViewById(R.id.extraInfoEditText);
        TextView signInTextView = findViewById(R.id.signInTextView);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton studentRadioButton = findViewById(R.id.studentRadioButton);

        studentRadioButton.setChecked(true);
        extraInfoEditText.setHint("Student number");
        extraInfoEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.studentRadioButton) {
                    extraInfoEditText.setHint("Student number");
                    extraInfoEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                } else {
                    extraInfoEditText.setHint("University name");
                    extraInfoEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                }
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
                    finish();
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


        extraInfoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String extraInfo = extraInfoEditText.getText().toString();
                String error;
                if (radioGroup.getCheckedRadioButtonId() == R.id.studentRadioButton)
                    error = registerController.getStudentNumberError(extraInfo);
                else
                    error = registerController.getUniversityNameError(extraInfo);
                extraInfoEditText.setError(error);
                isUniNumberValid = (error == null);
                signUpButton.setEnabled((signUpButtonEnabled()));
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
                finish();
            }
        });

    }

    private boolean signUpButtonEnabled() {
        return isFirstNameValid
                && isLastNameValid
                && isUsernameValid
                && isPasswordValid
                && isConfirmPasswordValid
                && isUniNumberValid;
    }
}