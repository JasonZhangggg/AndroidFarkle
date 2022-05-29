/*
* Jason Zhang
* Period 5
* Farkle
 */
package com.example.a383349.farkle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Farkle extends Activity implements View.OnClickListener {
    //create the buttons
    ImageButton[] buttons = new ImageButton[6];
    int[] buttonState = new int[6];
    int[] dieImage = new int[6];
    int[] dieValue = new int[6];
    final int HOT_DIE = 0;
    final int SCORE_DIE = 1;
    final int LOCKED_Die = 2;
    Button roll;
    Button score;
    Button stop;
    TextView currentScoreTV;
    TextView totalScoreTV;
    TextView currentRoundTV;
    int currentScore;
    int totalScore;
    int totalRound;
    int currentRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //crate the images
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farkle);
        buttons[0] = (ImageButton) this.findViewById(R.id.imageButton1);
        buttons[1] = (ImageButton) this.findViewById(R.id.imageButton2);
        buttons[2] = (ImageButton) this.findViewById(R.id.imageButton3);
        buttons[3] = (ImageButton) this.findViewById(R.id.imageButton4);
        buttons[4] = (ImageButton) this.findViewById(R.id.imageButton5);
        buttons[5] = (ImageButton) this.findViewById(R.id.imageButton6);
        //on click for all buttons
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setOnClickListener(this);
            buttons[i].setEnabled(false);
            buttons[i].setBackgroundColor(Color.LTGRAY);
        }
        //crate roll button, score button and stop button
        roll = (Button) this.findViewById(R.id.button1);
        roll.setOnClickListener(this);
        score = (Button) this.findViewById(R.id.button2);
        score.setOnClickListener(this);
        score.setEnabled(false);
        stop = (Button) this.findViewById(R.id.button3);
        stop.setOnClickListener(this);
        stop.setEnabled(false);
        //crate text views
        currentScoreTV = (TextView) this.findViewById(R.id.textView1);
        totalScoreTV = (TextView) this.findViewById(R.id.textView2);
        currentRoundTV = (TextView) this.findViewById(R.id.textView3);
        //create the dice images
        dieImage[0] = R.drawable.onetest;
        dieImage[1] = R.drawable.two;
        dieImage[2] = R.drawable.three;
        dieImage[3] = R.drawable.four;
        dieImage[4] = R.drawable.five;
        dieImage[5] = R.drawable.six;


    }

    @Override
    public void onClick(View v) {
        //if roll is clicked
        if (v.equals(roll)) {
            for (int i = 0; i < buttons.length; i++) {
                if (buttonState[i] == HOT_DIE) {
                    //give the dice a random value, as long as it has not been selected
                    int choice = (int) (Math.random() * 6);
                    dieValue[i] = choice;
                    //set the image
                    buttons[i].setImageResource(dieImage[choice]);
                    buttons[i].setEnabled(true);
                    //disable the roll and stop button so they can only score
                    score.setEnabled(true);
                    roll.setEnabled(false);
                    stop.setEnabled(false);
                }
            }
        }
        //if they click score
        else if (v.equals(score)) {
            //check if all their selections are valid
            int[] valueCount = new int[7];
            for (int i = 0; i < buttonState.length; i++) {
                if (buttonState[i] == SCORE_DIE) {
                    valueCount[dieValue[i] + 1]++;
                }
            }
            if ((valueCount[2] > 0 && valueCount[2] < 3) ||
                    (valueCount[3] > 0 && valueCount[3] < 3) ||
                    (valueCount[4] > 0 && valueCount[4] < 3) ||
                    (valueCount[6] > 0 && valueCount[6] < 3)) {
                //if not tell them
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Invalid Die selected!");
                alertDialogBuilder
                        .setMessage("You can only select scoring dice")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
            //if they did not select anything
            else if(valueCount[1] == 0&&valueCount[2] == 0&&valueCount[3] == 0&&valueCount[4] == 0&&valueCount[5] == 0&&valueCount[6] == 0){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("No score!");
                alertDialogBuilder
                        .setMessage("Forfeit score and go to next round?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                currentScore = 0;
                                currentRound++;
                                currentScoreTV.setText("Current Score: " + currentScore);
                                currentRoundTV.setText("Current Round: " + currentRound);
                                resetDice();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
            else {
                //update the score for all the dice
                if (valueCount[1] < 3) {
                    currentScore += (valueCount[1] * 100);
                }
                if (valueCount[5] < 3) {
                    currentScore += (valueCount[5] * 100);
                }
                if (valueCount[1] >= 3) {
                    currentScore += (1000 * (valueCount[1] - 2));
                }
                if (valueCount[2] >= 3) {
                    currentScore += (200 * (valueCount[2] - 2));
                }
                if (valueCount[3] >= 3) {
                    currentScore += (300 * (valueCount[3] - 2));
                }
                if (valueCount[4] >= 3) {
                    currentScore += (400 * (valueCount[4] - 2));
                }
                if (valueCount[5] >= 3) {
                    currentScore += (500 * (valueCount[5] - 2));
                }
                if (valueCount[6] >= 3) {
                    currentScore += (600 * (valueCount[6] - 2));
                }
                //write the score uot
                currentScoreTV.setText("Current Score: " + currentScore);
                for (int i = 0; i < buttons.length; i++) {
                    if (buttonState[i] == SCORE_DIE) {
                        buttonState[i] = LOCKED_Die;
                        buttons[i].setBackgroundColor(Color.BLUE);
                        buttons[i].setEnabled(false);
                    }
                }
                int lockedCount = 0;
                for (int i = 0; i <buttons.length ; i++) {
                    if(buttonState[i] == LOCKED_Die){
                        lockedCount++;
                    }
                }
                if(lockedCount == 6){
                    for (int i = 0; i <buttons.length ; i++) {
                        buttonState[i] = HOT_DIE;
                        buttons[i].setBackgroundColor(Color.LTGRAY);
                    }
                }
                roll.setEnabled(true);
                stop.setEnabled(true);
                score.setEnabled(false);

            }
        } else if (v.equals(stop)) {
            //if they clicked stop
            totalScore += currentScore;
            //update the score and total score
            currentScore = 0;
            currentScoreTV.setText("Current Score: " + currentScore);
            totalScoreTV.setText("Total Score: " + totalScore);
            currentRound++;
            currentRoundTV.setText("Round: " + currentRound);
            resetDice();
        } else {
            for (int i = 0; i < buttons.length; i++) {
                if (v.equals(buttons[i])) {
                    if (buttonState[i] == HOT_DIE) {
                        buttons[i].setBackgroundColor(Color.RED);
                        buttonState[i] = SCORE_DIE;
                    } else {
                        buttons[i].setBackgroundColor(Color.LTGRAY);
                        buttonState[i] = HOT_DIE;
                    }
                }
            }
        }
    }
    //reset the dice after the board is cleared
    private void resetDice() {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setEnabled(false);
            buttonState[i] = HOT_DIE;
            buttons[i].setBackgroundColor(Color.LTGRAY);
        }
        roll.setEnabled(true);
        stop.setEnabled(false);
        score.setEnabled(false);
    }
}
