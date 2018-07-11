package io.github.rgdagir.parsetagram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;
    private Button signUpBtn;
    private int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameInput = findViewById(R.id.usernameContainer);
        passwordInput = findViewById(R.id.passwordContainer);
        loginBtn = findViewById(R.id.loginBtn);
        signUpBtn = findViewById(R.id.signUpBtn);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) { // then prompt the home activity, not the login screen
            goHome();
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();

                login(username, password);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent register = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(register);
            }
        });
    }

    private void login (String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null){
                    Log.d("Login", "Success!");
                    Toast.makeText(MainActivity.this, "@string/logInSuccess", 5).show();
                    goHome();
                } else {
                    Log.d("Login", "Errrouuuuuu!");
                    Toast.makeText(MainActivity.this, "@string/wrongCredentials", 5).show();
                    e.printStackTrace();
                }
            }
        });
    }

    public void goHome(){
        Intent itsGoingHome = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(itsGoingHome);
    }
}
