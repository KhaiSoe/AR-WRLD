package org.pursuit.ar_wrld.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.jakewharton.rxbinding.view.RxView;

import org.pursuit.ar_wrld.GameInformation;
import org.pursuit.ar_wrld.MainActivity;
import org.pursuit.ar_wrld.R;
import org.pursuit.ar_wrld.perks.PerkPickForUser;

import java.util.concurrent.TimeUnit;

import static org.pursuit.ar_wrld.login.SignUpActivity.USERNAME_KEY;

public class UserHomeScreenActivity extends AppCompatActivity {

    private Spinner levelSpinner;
    private Button practiceButton;
    private Button playButton;
    private Button logoutButton;
    private Button pickAPerkButton;
    private TextView usernameTextView;
    private TextView userscoreTextView;
    private TextView userTitleTextView;
    private SharedPreferences sharedPreferences;
    private RecyclerView recyclerView;

    public static final String EASY_STRING = "EASY";
    public static final String MEDIUM_STRING = "MEDIUM";
    public static final String HARD_STRING = "HARD";
    public static final String BOSS_LEVEL = "BOSS";


    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_screen);
        firebaseAuth = FirebaseAuth.getInstance();


        usernameTextView = findViewById(R.id.user_name);
        userscoreTextView = findViewById(R.id.user_score);
        userTitleTextView = findViewById(R.id.user_title);
        recyclerView = findViewById(R.id.top_play_recyclerview);
        levelSpinner = findViewById(R.id.level_spinner);
        pickAPerkButton = findViewById(R.id.pick_a_perk);
        practiceButton = findViewById(R.id.practice_button);
        playButton = findViewById(R.id.play_button);
        logoutButton = findViewById(R.id.logout_button);

        retrieveUsername();

        RxView.clicks(playButton).throttleFirst(2000, TimeUnit.MILLISECONDS).subscribe(empty -> {
            String spinnerValue = levelSpinner.getSelectedItem().toString();
            Intent playIntent = new Intent(UserHomeScreenActivity.this, MainActivity.class);
            playIntent.putExtra(GameInformation.GAME_DIFFICULTY, spinnerValue);
            startActivity(playIntent);
            finish();

        });

        pickAPerkButton.setOnClickListener(v -> {
            Intent perkIntent = new Intent(UserHomeScreenActivity.this, PerkPickForUser.class);
            startActivity(perkIntent);
            finish();
        });

        RxView.clicks(logoutButton).throttleFirst(2000, TimeUnit.MILLISECONDS).subscribe(empty -> {
            firebaseAuth.signOut();
            startActivity(new Intent(UserHomeScreenActivity.this, SignInActivity.class));
            finish();
        });


    }

    private void retrieveUsername() {
        sharedPreferences = getSharedPreferences(SignUpActivity.MYSHAREDPREF, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(USERNAME_KEY)) {
            usernameTextView.setText(sharedPreferences.getString(USERNAME_KEY, ""));
        }

    }

//    public  void updateUI (FirebaseUser user){
//        if(user != null){
//            String name = user.getDisplayName();
//            usernameTextView.setText(name);
//        }
//    }
}
