package org.pursuit.ar_wrld.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.pursuit.ar_wrld.GameInformation;
import org.pursuit.ar_wrld.MainActivity;
import org.pursuit.ar_wrld.R;

public class UserHomeScreenActivity extends AppCompatActivity {

    private Spinner levelSpinner;
    private Button practiceButton;
    private Button playButton;
    private Button logoutButton;
    private TextView usernameTextView;
    private TextView userscoreTextView;
    private TextView userTitleTextView;

    private static final String USER_NAME = "username";
    private static final String USER_SCORE = "userscore";
    public static final String EASY_STRING = "EASY";
    public static final String MEDIUM_STRING = "MEDIUM";
    public static final String HARD_STRING = "HARD";
    public static final String BOSS_LEVEL = "BOSS";


    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser user;

//    @Override
//    protected void onStart() {
//        super.onStart();
//        firebaseAuth.addAuthStateListener(authStateListener);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_screen);


        usernameTextView = findViewById(R.id.user_name);
        userscoreTextView = findViewById(R.id.user_score);
        userTitleTextView = findViewById(R.id.user_title);
        levelSpinner = findViewById(R.id.level_spinner);
        practiceButton = findViewById(R.id.practice_button);
        playButton = findViewById(R.id.play_button);
        logoutButton = findViewById(R.id.logout_button);

//        firebaseAuth = FirebaseAuth.getInstance();


//        if(firebaseAuth.getCurrentUser() != null ){
//            user = firebaseAuth.getCurrentUser();
//            updateUI(user);
//        }

        playButton.setOnClickListener(v -> {
            String spinnerValue = levelSpinner.getSelectedItem().toString();
            Intent intent = new Intent(UserHomeScreenActivity.this, MainActivity.class);
            intent.putExtra(GameInformation.GAME_DIFFICULTY, spinnerValue);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
//            firebaseAuth.signOut();
            startActivity(new Intent(UserHomeScreenActivity.this, SignInActivity.class));

        });
    }

//    public  void updateUI (FirebaseUser user){
//        if(user != null){
//            String name = user.getDisplayName();
//            usernameTextView.setText(name);
//        }
//    }
}
