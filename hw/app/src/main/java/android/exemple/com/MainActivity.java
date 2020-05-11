package android.exemple.com;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Counter for Basketball Games
 */

public class MainActivity extends AppCompatActivity {
    public int scoreTeamA = 0;
    public int scoreTeamB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }

    public void changeQuantity (View view) {
        if(view.getParent().equals(findViewById(R.id.TeamA))){
            if(view.equals(findViewById(R.id.buttonPlusThreeA))) {
                    scoreTeamA += 3;
                }
            if(view.equals(findViewById(R.id.buttonPlusTwoA))) {
                    scoreTeamA += 2;
                }
            if(view.equals(findViewById(R.id.buttonPlusOneA))) {
                    scoreTeamA ++;
                }
        }

        if(view.getParent().equals(findViewById(R.id.TeamB))){
            if(view.equals(findViewById(R.id.buttonPlusThreeB))) {
                scoreTeamB += 3;
            }
            if(view.equals(findViewById(R.id.buttonPlusTwoB))) {
                scoreTeamB += 2;
            }
            if(view.equals(findViewById(R.id.buttonPlusOneB))) {
                scoreTeamB ++;
            }
        }
        displayScore();
    }

    public void Reset(View view){
        scoreTeamA = 0;
        scoreTeamB = 0;
        EditText scoreTextViewA = (EditText) findViewById(R.id.name_teamA);
        scoreTextViewA.setText("Team A");
        EditText scoreTextViewB = (EditText) findViewById(R.id.name_teamB);
        scoreTextViewB.setText("Team B");
        displayScore();
    }

    private void displayScore (){
        TextView scoreTextViewA = (TextView) findViewById(R.id.scoreTeamA);
        TextView scoreTextViewB = (TextView) findViewById(R.id.scoreTeamB);
        scoreTextViewA.setText("" + scoreTeamA);
        scoreTextViewB.setText("" + scoreTeamB);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("scoreTeamA", scoreTeamA);
        outState.putInt("scoreTeamB", scoreTeamB);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        scoreTeamA = savedInstanceState.getInt("scoreTeamA");
        scoreTeamB = savedInstanceState.getInt("scoreTeamB");
        displayScore();
    }
}
