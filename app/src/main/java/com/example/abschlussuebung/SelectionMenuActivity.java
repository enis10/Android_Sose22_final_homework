package com.example.abschlussuebung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.abschlussuebung.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SelectionMenuActivity extends AppCompatActivity {
    LinearLayout llEasy, llMiddle, llHard, llScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_menu);
        Hooks();

        llEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getInstance().setLevel(0);
                Intent intent = new Intent(SelectionMenuActivity.this, EasyActivity.class);
                Utils.getInstance().setArea(10);
                startActivity(intent);
            }
        });

        llMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getInstance().setLevel(1);
                Utils.getInstance().setArea(30);
                Intent intent = new Intent(SelectionMenuActivity.this, EasyActivity.class);
                startActivity(intent);
            }
        });


        llHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getInstance().setLevel(2);
                Utils.getInstance().setArea(30);
                Intent intent = new Intent(SelectionMenuActivity.this, GameTwoActivity.class);
                startActivity(intent);
            }
        });


        //Access to scores
        llScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatenBankHelfer datenBankHelfer = new DatenBankHelfer(SelectionMenuActivity.this);
                List<ScoreModel> allScores = datenBankHelfer.getEveryScore();
                List<ScoreModel> filteredArray = Utils.getInstance().getFiltered(allScores,1);
                int max = Utils.getInstance().highestScore(filteredArray);
                //Toast.makeText(SelectionMenuActivity.this, filteredArray.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SelectionMenuActivity.this, ScoresActitvity.class);
                startActivity(intent);

            }
        });

    }

    private void Hooks() {
        llScores = findViewById(R.id.llScores);
        llEasy = findViewById(R.id.llEinfach);
        llMiddle = findViewById(R.id.llMittel);
        llHard = findViewById(R.id.llHard);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        finishAffinity();
        System.exit(0);

    }
}