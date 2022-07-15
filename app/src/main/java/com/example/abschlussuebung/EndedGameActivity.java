package com.example.abschlussuebung;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abschlussuebung.utils.Utils;

import java.util.List;
import java.util.Locale;

public class EndedGameActivity extends AppCompatActivity {
    TextView txtScore, ic_exit;
    ImageView ic_back;
    int scoreValue;
    LinearLayout btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ended_game);
        //Score holen
        txtScore = findViewById(R.id.txtScore);
        ic_back = findViewById(R.id.ic_back);
        ic_exit = findViewById(R.id.ic_exit);
        btnShare = findViewById(R.id.btnShare);
        scoreValue= getIntent().getIntExtra("scoreValue",0);
        txtScore.setText(String.valueOf(scoreValue));

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndedGameActivity.this, SelectionMenuActivity.class);
                startActivity(intent);
            }
        });
        ic_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               EndedGameActivity.this.finish();
               System.exit(0);
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

        // Save Score in Data Base
        ScoreModel  scoreModel= new ScoreModel(-1, scoreValue, 0,Utils.getInstance().getLevel());
       DatenBankHelfer datenBankHelfer = new DatenBankHelfer(EndedGameActivity.this);
       // boolean succes = datenBankHelfer.addOne(scoreModel);
       // Toast.makeText(EndedGameActivity.this, "Adding score int DB Success = "+succes, Toast.LENGTH_SHORT).show();
       // wenn eine neue höschte Score gibt
        List<ScoreModel> allScores = datenBankHelfer.getEveryScore();
        List<ScoreModel> filteredArray = Utils.getInstance().getFiltered(allScores,Utils.getInstance().getLevel());
        int max = Utils.getInstance().highestScore(filteredArray);

        if(max< scoreValue){
            datenBankHelfer.addOne(scoreModel);

            Dialog dialog = new Dialog(EndedGameActivity.this, R.style.HightScore);
            dialog.setCanceledOnTouchOutside(true);
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            dialog.setContentView(R.layout.highest_score_dialog);
            TextView txtScore = dialog.findViewById(R.id.highScore);
            txtScore.setText(Utils.getInstance().getLevelText(Utils.getInstance().getLevel()));
            TextView txtScorePts = dialog.findViewById(R.id.heigScorePts);
            txtScorePts.setText(String.valueOf(scoreValue));
            dialog.show();
        }else {
            datenBankHelfer.addOne(scoreModel);
        }
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage= "\nI got "+scoreValue+" points !!! you can also try!!\n\n";
                    if(Locale.getDefault().getLanguage() == "de")
                        shareMessage= "\nIch habe "+scoreValue+" Punkten !!! kannst du auch mal probieren !!\n\n";

                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {}
                }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EndedGameActivity.this, SelectionMenuActivity.class);
        startActivity(intent);
    }


}