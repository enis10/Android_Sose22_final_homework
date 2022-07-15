package com.example.abschlussuebung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.Arrays;
import java.util.Locale;

public class GameTwoActivity extends AppCompatActivity implements View.OnClickListener {
    TextView card_option1, card_option2,card_option3, txtScore;
    CardView card1, card2,card3,card4;
    LinearLayout nextBtn;
    boolean paused = false;
    Game2 game = new Game2();
    ImageView btnPause;
    int timerValue = 40;
    ImageView ic_back;
    TextView ic_exit;

    boolean l1 = false,l2=false,l3=false,l4=false;
    LinearProgressIndicator timer;

    //Sensor Objekten
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private double mLastShakeTime;

    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;






    CountDownTimer countDownTimer = new CountDownTimer(60000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timerValue --;
            timer.setProgress(timerValue);
            // falls man pausiert
            if(timerValue == 0){
                countDownTimer.onFinish();
                countDownTimer.cancel();}
        }

        @Override
        public void onFinish() {
            Intent intent = new Intent(GameTwoActivity.this, EndedGameActivity.class);
            intent.putExtra("scoreValue", game.getScore());
            startActivity(intent);




        }
    }.start();
    private SensorEventListener sensorEventListener =new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                long curTime = System.currentTimeMillis();
                if ((curTime - mLastShakeTime) > 2000) {

                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];

                    double acceleration = Math.sqrt(Math.pow(x, 2) +
                            Math.pow(y, 2) +
                            Math.pow(z, 2)) - SensorManager.GRAVITY_EARTH;


                    if (acceleration > 8 ) {
                        mLastShakeTime = curTime;
                        pause();} } }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_two);
        Hooks();
        setFirstQuestion();
        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        btnPause.setOnClickListener(this);


        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                Intent intent = new Intent(GameTwoActivity.this, SelectionMenuActivity.class);
                startActivity(intent);
            }
        });
        ic_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                finishAffinity();
                System.exit(0);
            }
        });









    }

    private void Hooks(){
        card_option1 = findViewById(R.id.card_option1);
        card_option2 = findViewById(R.id.card_option2);
        card_option3 = findViewById(R.id.card_option3);
        card1= findViewById(R.id.card1);
        card2= findViewById(R.id.card2);
        card3= findViewById(R.id.card3);
        card4= findViewById(R.id.card4);
        nextBtn = findViewById(R.id.nextBtn);
        txtScore = findViewById(R.id.txtScore);
        txtScore.setText("0");
        nextBtn.setEnabled(false);
        timer = findViewById(R.id.quiz_timer);
        btnPause = findViewById(R.id.btnPause);
        timer.setMax(40);
        ic_back= findViewById(R.id.ic_back);
        ic_exit = findViewById(R.id.ic_exit);
        //Sensor Stuffs
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        // sensor objects
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }
    private void setFirstQuestion(){
        card_option1.setText(game.getQuestion1().getQuestionPhrase());
        card_option2.setText(game.getQuestion2().getQuestionPhrase());
        card_option3.setText(game.getQuestion3().getQuestionPhrase());

    }
    private void resetButtons(){
        card1.setBackgroundColor(getResources().getColor(R.color.white));
        card2.setBackgroundColor(getResources().getColor(R.color.white));
        card3.setBackgroundColor(getResources().getColor(R.color.white));
        card4.setBackgroundColor(getResources().getColor(R.color.white));
        txtScore.setText(String.valueOf(game.getScore()));
        nextBtn.setEnabled(false);
        card1.setEnabled(true);
        card2.setEnabled(true);
        card3.setEnabled(true);
        card4.setEnabled(true);
        l1= false;  l2= false; l3= false; l4= false;


    }

//****************** onClick listeners**************************
    @Override
    public void onClick(View v) {


        if(v.getId() == card1.getId()){
            if(game.checkOneQuestion(game.getQuestion1())) {
                game.incrementSelectedChoices();
                card1.setBackgroundColor(getResources().getColor(R.color.green));
                nextBtn.setEnabled(true);
                card1.setEnabled(false);
                card4.setEnabled(false);
                l1 = true;


            }
            else
                gameOver(game.getQuestion1());
        }
        else if(v.getId() == card2.getId()){
            if(game.checkOneQuestion(game.getQuestion2())){
                game.incrementSelectedChoices();
                card2.setBackgroundColor(getResources().getColor(R.color.green));
                nextBtn.setEnabled(true);
                card2.setEnabled(false);
                card4.setEnabled(false);
                l2 = true;
            }

            else
                gameOver(game.getQuestion2());
        }
        else if(v.getId() == card3.getId()){
            if(game.checkOneQuestion(game.getQuestion3())){
                game.incrementSelectedChoices();
                card3.setEnabled(false);
                card3.setBackgroundColor(getResources().getColor(R.color.green));
                nextBtn.setEnabled(true);
                card4.setEnabled(false);
                l3 = true;
            }
            else
                gameOver(game.getQuestion3());
        }
        //no Option is right
       else if(v.getId() == card4.getId()){

           if(0 == game.howManyAnswers()){
               card4.setBackgroundColor(getResources().getColor(R.color.green));
               card4.setEnabled(false);
               nextBtn.setEnabled(true);
             ;
               l4 = true;
           }
           else
               gameOver();

        }

        else  if(v.getId() == nextBtn.getId()){
            if(game.checkChoices()){
                nextTurn();

            }
            else
                gameOver();
        }

        if(v.getId() == btnPause.getId()){
            pause();

        }
    }//*********************End Listener**********************************

    private void nextTurn(){
        game.setCurrentQuestions();
        resetButtons();
        setFirstQuestion();
    }

    private void gameOver(Question question){
        Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }

        countDownTimer.cancel();
        Dialog dialog = new Dialog(GameTwoActivity.this, R.style.CustomAlertDialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.setContentView(R.layout.game_over_dialog);
        TextView loss = dialog.findViewById(R.id.txtLoss);
        if(Locale.getDefault().getLanguage() == "de")
            loss.setText("Opps....\n "+question.getQuestionPhrase() +" ist falsch");
        else
            loss.setText("Opps....\n "+question.getQuestionPhrase() +" is wrong");




        dialog.findViewById(R.id.btnTryAgain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                Intent intent = new Intent(GameTwoActivity.this, GameTwoActivity.class);
                startActivity(intent);
            }
        });
        dialog.findViewById(R.id.btnMainMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                Intent intent  = new Intent(GameTwoActivity.this , SelectionMenuActivity.class);
                startActivity(intent);
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Intent intent  = new Intent(GameTwoActivity.this , SelectionMenuActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }

    private void gameOver(){
        Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }

        countDownTimer.cancel();
        Dialog dialog = new Dialog(GameTwoActivity.this, R.style.CustomAlertDialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.setContentView(R.layout.game_over_dialog);
        TextView loss = dialog.findViewById(R.id.txtLoss);
        String lossLabel = new String();
        if(Locale.getDefault().getLanguage()  == "de") {
            lossLabel = "Es gab " + game.howManyAnswers() + " richtige Optionen:\n";
            if (game.howManyAnswers() == 1)
                lossLabel = "Es gab " + "eine richtige Option\n";
        }else {
            lossLabel = "There was  " + game.howManyAnswers() + " right Options:\n";
            if (game.howManyAnswers() == 1)
                lossLabel = "There was a right option:\n";
        }


        String[] cc = game.displayCorrectAnswers();
        for(String c: cc){
            lossLabel += c+"  ";
        }
        loss.setText(lossLabel);
        dialog.findViewById(R.id.btnTryAgain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                Intent intent = new Intent(GameTwoActivity.this, GameTwoActivity.class);
                startActivity(intent);
            }
        });
        dialog.findViewById(R.id.btnMainMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                Intent intent  = new Intent(GameTwoActivity.this , SelectionMenuActivity.class);
                startActivity(intent);
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Intent intent  = new Intent(GameTwoActivity.this , SelectionMenuActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();
        Intent intent  = new Intent(GameTwoActivity.this , SelectionMenuActivity.class);
        startActivity(intent);
    }


    //************Pause the process*****************
    private void pause(){

        if(!paused){
            btnPause.setImageResource(R.drawable.ic_play_white);
            countDownTimer.cancel();
            paused = true;
        }
        else{
            paused= false;
            nextTurn();
            countDownTimer.start();
            btnPause.setImageResource(R.drawable.ic_pause_white);
        }
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
    }

}