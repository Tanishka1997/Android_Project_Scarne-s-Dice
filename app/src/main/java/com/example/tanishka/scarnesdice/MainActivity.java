package com.example.tanishka.scarnesdice;

import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView turnScore;
    TextView userScore;
    TextView computerScore;
    Button btHold;
    private int computerTurnScore,computerOverallScore,userTurnScore,userOverallScore;
    private int[] dicefaces={R.drawable.dice1,R.drawable.dice2,R.drawable.dice3,R.drawable.dice4,R.drawable.dice5,R.drawable.dice6};
    private ImageView diceFace;
    Button btRoll;
    Button btReset;
    Handler handler;
    Runnable runnable;
    Random rand_no;
    Random rand_chance;
    int chance;
    int played;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        turnScore=(TextView) findViewById(R.id.tvTurnScore);
        userScore=(TextView) findViewById(R.id.tvUserOverallScore);
        computerScore=(TextView) findViewById(R.id.tvComputerOverallScore);
        btHold=(Button) findViewById(R.id.btHold);
        btRoll=(Button) findViewById(R.id.btRoll);
        btReset=(Button) findViewById(R.id.btReset);
        diceFace=(ImageView) findViewById(R.id.diceFace);
        computerTurnScore=0;
        computerOverallScore=0;
        userTurnScore=0;
        userOverallScore=0;
        chance=0;
        played=0;
        handler=new Handler();
        rand_no=new Random();
        rand_chance=new Random();
        btHold.setOnClickListener(this);
        btRoll.setOnClickListener(this);
        btReset.setOnClickListener(this);
        btHold.setEnabled(false);

    }

    private int number_gen(){
        int no;
        no=rand_no.nextInt(6);
        return no;
    }
   private void showComputer(){
       turnScore.setText("Computer's Turn score:"+computerTurnScore);
       userScore.setText("Your Overall Score:"+userOverallScore);
       computerScore.setText("Computer's Overall Score:"+computerOverallScore);
   }

    private void showUser(){
        turnScore.setText("Your Turn Score:"+userTurnScore);
        userScore.setText("Your Overall Score:"+userOverallScore);
        computerScore.setText("Computer's Overall Score:"+computerOverallScore);
    }
    private void computerRoll(){
       btRoll.setEnabled(false);
        boolean no_chance;
       int no;
       while (true){
           no_chance=rand_chance.nextBoolean();
           no=number_gen();
           if(no_chance&&played==1){
               Toast.makeText(this,"Hold Switched to You",Toast.LENGTH_LONG).show();
               showComputer();
               hold_switch();
               break;
           }
           else {
               played=1;
               if (no==0){
                   showComputer();
                   diceFace.setImageResource(dicefaces[no]);
                   computerTurnScore=0;
                   hold_switch();
                   Toast.makeText(this,"Hold Switched to You",Toast.LENGTH_LONG).show();
                   break;
               }
              else {
                   showComputer();
                   diceFace.setImageResource(dicefaces[no]);
                   computerTurnScore+=no+1;
                   if((computerOverallScore+computerTurnScore)>=100)
                   {turnScore.setText("You Lost");
                       computerScore.setVisibility(View.INVISIBLE);
                       userScore.setVisibility(View.INVISIBLE);
                       btRoll.setEnabled(false);
                       btHold.setEnabled(false);
                       return;
                   }

               }
           }


       }
   btHold.setEnabled(false);
   btRoll.setEnabled(true);
    }

    private void hold_switch(){
        if (chance==0){
            chance=1;
            userOverallScore+=userTurnScore;
            showUser();
            userTurnScore=0;
            played=0;

        }else {
            computerOverallScore+=computerTurnScore;
            showComputer();
            computerTurnScore=0;
            chance=0;
            played=0;

        }

    }
    private void user_roll(){
        played=1;
        int no=number_gen();
        btHold.setEnabled(true);
        diceFace.setImageResource(dicefaces[no]);
         if (no==0) {
             userTurnScore=0;
             hold_switch();
             Toast.makeText(this,"Hold Switched to computer",Toast.LENGTH_LONG).show();
             computerRoll();
             return;
         }
        else{
             btHold.setEnabled(true);
             userTurnScore+=no+1;
             if((userOverallScore+userTurnScore)>=100)
             {turnScore.setText("You are winner");
                 computerScore.setVisibility(View.INVISIBLE);
                 userScore.setVisibility(View.INVISIBLE);
                 btHold.setEnabled(false);
                 btRoll.setEnabled(false);
                 return;
             }
         }
        showUser();

    }


    private void user_hold(){
        hold_switch();
        Toast.makeText(this,"Hold Switched to Computer",Toast.LENGTH_LONG).show();
        computerRoll();
    }


    private void reset(){
        computerOverallScore=0;
        computerTurnScore=0;
        userOverallScore=0;
        userTurnScore=0;
        diceFace.setImageResource(dicefaces[0]);
        turnScore.setVisibility(View.INVISIBLE);
        userScore.setVisibility(View.VISIBLE);
        computerScore.setVisibility(View.VISIBLE);
        btRoll.setEnabled(true);
        userScore.setText("Your Overall Score:"+userOverallScore);
        computerScore.setText("Computer's Overall Score:"+computerOverallScore);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
         case R.id.btHold:
             user_hold();
             break;
         case R.id.btRoll:
             turnScore.setVisibility(View.VISIBLE);
             user_roll();
             break;
         case R.id.btReset:
             reset();
             break;
      }
    }
}
