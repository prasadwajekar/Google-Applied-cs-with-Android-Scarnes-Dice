package saurabhjn76.com.scarnesdice;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int userTotalScore=0;
    private int userTurnScore=0;
    private  int computerTotalScore=0;
    private int computerTurnScore=0;
    private Button roll,reset,hold;
    private TextView userScore,computerScore;
    private ImageView dieImage;
    private Random random = new Random();
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis();
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        roll=(Button)findViewById(R.id.roll);
        hold=(Button) findViewById(R.id.hold);
        reset =(Button) findViewById(R.id.reset);
        userScore =(TextView) findViewById(R.id.scoreUser);
        computerScore =(TextView) findViewById(R.id.scoreComputer);
        dieImage =(ImageView) findViewById(R.id.dieImage);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               rollDice(1);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userTurnScore=0;
                userTotalScore=0;
                computerTurnScore=0;
                computerTotalScore=0;
                userScore.setText("User's Score: "+userTotalScore);
                computerScore.setText("Computer's Score: "+computerTotalScore);

            }
        });
        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holdScore(1);
                computerTurn();

            }
        });
    }
    public void holdScore(int turn){
        if(turn==1){
            userTotalScore+=userTurnScore;
            userTurnScore=0;
            userScore.setText("User's Score: "+userTotalScore);
        }
        else{
            computerTotalScore+=computerTurnScore;
           computerTurnScore=0;
            computerScore.setText("Computer's Score: "+computerTotalScore);
        }
        if(userTotalScore>100){
            userScore.setText("User Wins!!");
            computerScore.setText("Congratulation!!");
            userTurnScore=0;
            userTotalScore=0;
            computerTurnScore=0;
            computerTotalScore=0;

        }
        else if(computerTotalScore>100){
            computerScore.setText("Better luck next time!");
            userScore.setText("Computer Wins!!");
            userTurnScore=0;
            userTotalScore=0;
            computerTurnScore=0;
            computerTotalScore=0;
            
        }
    }
    public  int rollDice(int turn){
        int diceRoll= random.nextInt(6)+1;
        int [] arr= {R.drawable.dice1,R.drawable.dice2,R.drawable.dice3,R.drawable.dice4,R.drawable.dice5,R.drawable.dice6};
        dieImage.setImageDrawable(getResources().getDrawable(arr[diceRoll-1]));
        if(diceRoll!=1){
            if(turn==1) {
                userTurnScore += diceRoll;
                userScore.setText("User's Score: " + userTotalScore + " Your turnScore:" + userTurnScore);
            }
            else{
                computerTurnScore += diceRoll;
               computerScore.setText("Computer's Score: " + computerTotalScore + " Computer turnScore:" + computerTurnScore);
            }
        }
        else{
            if(turn==1) {
                userTurnScore = 0;
                holdScore(1);
                computerTurn();
                //userScore.setText("User's Score: " + userTotalScore + " Your turnScore:" + userTurnScore);
            }
            else{
                computerTurnScore=0;
                holdScore(2);
               // computerScore.setText("Computer's Score: " + computerTotalScore + " Computer turnScore:" + computerTurnScore);
            }
        }
    return diceRoll;
    }
    public void computerTurn(){
        roll.setEnabled(false);
        hold.setEnabled(false);
           if(rollDice(2)!=1 && computerTurnScore<20){
               new Thread(){
                   @Override
                   public void run() {
                       try {
                           sleep(1000);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }

                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               computerTurn();
                           }
                       });

                   }
               }.start();
           }
        else{
               holdScore(2);
               roll.setEnabled(true);
               hold.setEnabled(true);
           }
            //timerHandler.postDelayed(timerRunnable,500);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
