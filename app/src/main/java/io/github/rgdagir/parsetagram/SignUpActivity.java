package io.github.rgdagir.parsetagram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    private EditText name;
    private EditText email;
    private EditText username;
    private EditText password;
    private Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.signUpName);
        email = findViewById(R.id.signUpEmail);
        username = findViewById(R.id.signUpUsername);
        password = findViewById(R.id.signUpPassword);
        confirmBtn = findViewById(R.id.confirmSignUp);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the ParseUser
                ParseUser user = new ParseUser();
                // Set core properties
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                user.setEmail(email.getText().toString());
                // Set custom properties
                user.put("fullname", name.getText().toString());
                // Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(SignUpActivity.this, "@string/accCreated", 5).show();
                            finish();
                        } else {
                            Log.e("Sign Up Failed!", e.toString());
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
