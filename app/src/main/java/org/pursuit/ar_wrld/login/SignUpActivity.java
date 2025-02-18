package org.pursuit.ar_wrld.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.pursuit.ar_wrld.GameInformation;
import org.pursuit.ar_wrld.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailId;
    private EditText username;
    private EditText passwordCheck;
    private TextView passwordRequirement;
    private Button signupBtn;
    private TextView toLogIn;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private static final String TAG = "SIGNUP_PAGE";
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;
    public static final String USERNAME_KEY = "Username Preferences";
    public static final String MYSHAREDPREF = "myPref";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        sharedPreferences = getApplicationContext().getSharedPreferences(GameInformation.SHARED_PREF_KEY, MODE_PRIVATE);


        username = findViewById(R.id.input_username);
        emailId = findViewById(R.id.input_email);
        progressBar = findViewById(R.id.progressBar);
        passwordCheck = findViewById(R.id.input_password);
        passwordRequirement = findViewById(R.id.password_requirement);
        toLogIn = findViewById(R.id.login_page);

        signupBtn = findViewById(R.id.btn_signup);

        signupBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, UserHomeScreenActivity.class);
            startActivity(intent);
            finish();
        });


        signupBtn.setOnClickListener(v -> {
            String userName = username.getText().toString();
            sharedPreferences.edit().putString(GameInformation.USERNAME_KEY, userName).apply();
            Log.d(TAG, "Khaing" + sharedPreferences.getString(GameInformation.USERNAME_KEY, "a"));

            String email = emailId.getText().toString();
            String password = passwordCheck.getText().toString();

            if (TextUtils.isEmpty(userName)) {
                Toast.makeText(getApplicationContext(), "Enter Username", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Enter Email Id", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignUpActivity.this, task -> {

                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(userName)
                                    .build();
                            user.updateProfile(request);
                            goBacktoLogIn();
                            Log.d(TAG, "signUpNewUsers: " + username);
                            Log.d(TAG, "createUserWithEmail:success" + user.getDisplayName());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });

    }


    public void goBacktoLogIn(){
        Intent intent = new Intent(SignUpActivity.this, UserHomeScreenActivity.class);
        startActivity(intent);
        finish();
    }

}

