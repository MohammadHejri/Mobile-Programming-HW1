package edu.sharif.courseware.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.sharif.courseware.R;
import edu.sharif.courseware.controller.LoginController;
import edu.sharif.courseware.model.LoginRepository;
import edu.sharif.courseware.model.Professor;
import edu.sharif.courseware.model.Student;
import edu.sharif.courseware.model.User;

public class LoginActivity extends AppCompatActivity {

    private LoginController loginController;

    private boolean isUsernameValid = false;
    private boolean isPasswordValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginController = new LoginController(this);

        Button signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setEnabled(false);

        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        TextView signUpTextView = (TextView) findViewById(R.id.signUpTextView);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                User user = loginController.getLoginResult(username, password);
                if (user == null) {
                    String errorMessage = "User not found or wrong password!";
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                } else {
                    LoginRepository.getInstance().logIn(username);
                    String welcomeMessage = "Welcome " + user.getFirstname() + "!";
                    Toast.makeText(getApplicationContext(), welcomeMessage, Toast.LENGTH_SHORT).show();
                    if (user instanceof Professor) {
                        Intent intent = new Intent(LoginActivity.this, ProfessorMainPage.class);
                        startActivity(intent);
                    } else if (user instanceof Student) {
                        Intent intent = new Intent(LoginActivity.this, StudentMainPage.class);
                        startActivity(intent);
                    }

                }
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
                String error = loginController.getUsernameError(username);
                usernameEditText.setError(error);
                isUsernameValid = (error == null);
                signInButton.setEnabled(isUsernameValid && isPasswordValid);
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
                String error = loginController.getPasswordError(password);
                passwordEditText.setError(error);
                isPasswordValid = (error == null);
                signInButton.setEnabled(isUsernameValid && isPasswordValid);
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}