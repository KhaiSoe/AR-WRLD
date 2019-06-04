package org.pursuit.ar_wrld;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.pursuit.ar_wrld.database.FirebaseDatabaseHelper;
import org.pursuit.ar_wrld.login.UserHomeScreenActivity;
import org.pursuit.ar_wrld.usermodel.UserInformation;

import java.util.List;

public class ResultPage extends AppCompatActivity {

    private TextView nameTextView;
    private TextView scoreTextView;
    private TextView titleForScore;
    private Button playAgainButton;
    private Button saveScoreButton;
    private FirebaseDatabaseHelper firebaseDatabaseHelper;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultpage);
        sharedPreferences = getApplicationContext().getSharedPreferences(GameInformation.SHARED_PREF_KEY, MODE_PRIVATE);

        nameTextView = findViewById(R.id.player_name);
        titleForScore = findViewById(R.id.title_for_player_score);
        scoreTextView = findViewById(R.id.player_score);
        saveScoreButton = findViewById(R.id.save_score_button);
        playAgainButton = findViewById(R.id.playagain_button);

        String playerName = sharedPreferences.getString(GameInformation.USERNAME_KEY, "");
        nameTextView.setText(playerName);

        long userScore = sharedPreferences.getInt(GameInformation.USER_SCORE_KEY, -1);
        scoreTextView.setText(String.valueOf(userScore));

        //retrieveUsername();
        //retrieveUserScore();

        saveScoreButton.setOnClickListener(v -> {
            UserInformation userInformation = new UserInformation();
            userInformation.setUsername(playerName);
            userInformation.setUserscore(userScore);

            new FirebaseDatabaseHelper().addUser(userInformation, new FirebaseDatabaseHelper.DataStatus() {
                @Override
                public void dataIsLoaded(List<UserInformation> userInformations, List<String> keys) {

                }

                @Override
                public void dataIsInserted() {
                    Toast.makeText(ResultPage.this, "Data has been saved successfully!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void dataIsUpdated() {

                }
            });

        });

        playAgainButton.setOnClickListener(v -> {
            startActivity(new Intent(ResultPage.this, UserHomeScreenActivity.class));
            finish();
        });

    }

//    private void retrieveUsername() {
//        String playerName = sharedPreferences.getString(GameInformation.USERNAME_KEY, "");
//        nameTextView.setText(playerName);
//    }

//    public void retrieveUserScore() {
//        int userScore = sharedPreferences.getInt(GameInformation.USER_SCORE_KEY, -1);
//        scoreTextView.setText(String.valueOf(userScore));
//    }

}

