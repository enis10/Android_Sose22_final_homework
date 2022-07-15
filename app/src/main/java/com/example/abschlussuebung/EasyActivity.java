package com.example.abschlussuebung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.Collection;

public class EasyActivity extends AppCompatActivity {
   CardView cRight, cWrong;
   ImageView btnPause, ic_back;
   TextView cardQuestion,  score, ic_exit;

    LinearProgressIndicator timer;
    Game game = new Game();

    int timerValue = 20, scoreValue = 0;
    boolean paused = false, gameOver = false;
    boolean started = false;
    int sstart = 0;


    //For Timer
    private static final long START_TIME_IN_MILLIS = 20000;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;


    //Sensor Objekten
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private double mLastShakeTime;

    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;



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


                    if (acceleration > 8) {
                        mLastShakeTime = curTime;
                        pause();} } }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    CountDownTimer  countDownTimer = new CountDownTimer(mTimeLeftInMillis,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mTimeLeftInMillis = millisUntilFinished;
            mTimerRunning = true;
            timer.setProgress(timerValue);
            timerValue --;
            timer.setProgress(timerValue);



            // falls man pausiert
            if(timerValue == 0){
                countDownTimer.onFinish();
                countDownTimer.cancel();}
        }

        @Override
        public void onFinish() {
            mTimerRunning = false;
            Intent intent = new Intent(EasyActivity.this, EndedGameActivity.class);
            intent.putExtra("scoreValue", game.getScore());
            startActivity(intent);

        }
    }.start();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);
        Hooks();


        // set Anfangswert für die erste Frage
        cardQuestion.setText(game.getCurrentQuestion().getQuestionPhrase());


         // **********Listner für falsch und richtig festlegen***************
        cWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(game.checkAnswer(false)){
                    nextTurn();
                }else {
                    gameOver();
                }
            }
        });
        cRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(game.checkAnswer(true)){
                    nextTurn();
                }else {
                    gameOver();
                }
            }
        });



        //************************ Pause Button*************
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                Intent intent = new Intent(EasyActivity.this, SelectionMenuActivity.class);
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
        cRight = findViewById(R.id.cRight);
        cWrong = findViewById(R.id.cWrong);
        cardQuestion = findViewById(R.id.card_question);
        score = findViewById(R.id.score);
        btnPause = findViewById(R.id.btnPause);
        timer = findViewById(R.id.quiz_timer);
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
    //update View if only the answer is true
    private void  nextTurn(){
        game.makeNewQuestion();

        cardQuestion.setText(game.getCurrentQuestion().getQuestionPhrase());
        scoreValue= game.getScore();
        score.setText(String.valueOf(scoreValue));

    }

    //***********Wenn Man Verliert
    private void gameOver(){
        Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        v.vibrate(500);
        gameOver = true;
        countDownTimer.cancel();
        Dialog dialog = new Dialog(EasyActivity.this, R.style.CustomAlertDialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.setContentView(R.layout.game_over_dialog);

        //Dialog Buttons
        dialog.findViewById(R.id.btnTryAgain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EasyActivity.this, EasyActivity.class);
                startActivity(intent);
            }
        });

        dialog.findViewById(R.id.btnMainMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EasyActivity.this, SelectionMenuActivity.class);
                startActivity(intent);
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Intent intent  = new Intent(EasyActivity.this , SelectionMenuActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();

    }


    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
    }


    private void pause(){
        if(!paused){
            countDownTimer.cancel();
            cRight.setEnabled(false);
            cWrong.setEnabled(false);

            btnPause.setImageResource(R.drawable.ic_play_white);
            paused = true;
        }else{
            paused = false;
            countDownTimer.start();
            nextTurn();
            cRight.setEnabled(true);
            cWrong.setEnabled(true);
            btnPause.setImageResource(R.drawable.ic_pause_white);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // cancel the countDownTimer when back button is pressed
        // or countDownTimer will countinue the execution
        countDownTimer.cancel();
        EasyActivity.this.finish();

    }
}

