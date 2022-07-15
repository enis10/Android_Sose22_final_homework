package com.example.abschlussuebung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abschlussuebung.utils.Utils;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.graphics.Color;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


public class ScoresActitvity extends AppCompatActivity {
    TextView txtScore, txtScore2, txtScore3, txtGame1, txtGame2, txtGame3;
    List<ScoreModel> allScores;
    boolean dialogOn = false;
    int dispayedLevel = 0;
    LinearLayout showStats1, showStats2 ,showStats3;

    ImageView ic_back;
    TextView ic_exit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores_actitvity);
        Hooks();
        setValues();
        activateButtons();


        //Listener für txtViews die die Statistik zeigen
        //****************txtGame1********************
        showStats1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispayedLevel= 0;
            showStatsDialog(0);
            }
        });
        //*********************txtGame2*****************
        showStats2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispayedLevel = 1;
                showStatsDialog(1);
            }
        });

        //*********************txtGame2*****************
        showStats3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispayedLevel = 2;
                showStatsDialog(2);
            }
        });
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ScoresActitvity.this, SelectionMenuActivity.class);
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
        txtScore = findViewById(R.id.displayScore);
        txtScore2 = findViewById(R.id.displayScore2);
        txtScore3 = findViewById(R.id.displayScore3);
        txtGame1 = findViewById(R.id.txtGame1);
        txtGame2 = findViewById(R.id.txtGame2);
        txtGame3 = findViewById(R.id.txtGame3);
        showStats1 = findViewById(R.id.showStats1);
        showStats2 = findViewById(R.id.showStats2);
        showStats3 = findViewById(R.id.showStats3);
        ic_back= findViewById(R.id.ic_back);
        ic_exit = findViewById(R.id.ic_exit);

    }






    private void setValues(){
        int[] maxArray = new int[3];
        int[] totalGames = new int[3];
        DatenBankHelfer datenBankHelfer = new DatenBankHelfer(ScoresActitvity.this);
        List<ScoreModel> allScores = datenBankHelfer.getEveryScore();
        for(int i = 0; i<3; i++){
            List<ScoreModel> filteredArray = Utils.getInstance().getFiltered(allScores,i);
            totalGames[i] = filteredArray.size();
            int max = Utils.getInstance().highestScore(filteredArray);
            maxArray[i] = max;
        }

        String endedGames = new String();
        if(Locale.getDefault().getLanguage() =="de"  ){
            endedGames = (" Beendete Spiele");
        }else {
            endedGames = (" Ended Games");
        }
        txtGame1.setText(String.valueOf(totalGames[0])+ endedGames);
        txtGame2.setText(String.valueOf(totalGames[1])+ endedGames);
        txtGame3.setText(String.valueOf(totalGames[2])+ endedGames);


        txtScore.setText(String.valueOf(maxArray[0]));
        txtScore2.setText(String.valueOf(maxArray[1]));
        txtScore3.setText(String.valueOf(maxArray[2]));
    }











    private void makeChart(LineChart lineChart, List<ScoreModel> list,int sLevel){
        //______________void make chart___________
        //Parametes for Chart
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);

        //set points
        ArrayList<Entry> yValues = new ArrayList<>();
      for(int i = 1; i<=list.size();i++){
          yValues.add(new Entry(i,list.get(i-1).getScore()));
      }


        // Styling
        boolean deutsch = Locale.getDefault().getLanguage() =="de";
        String label = new String();
        String level = new String();
        if(deutsch){
            label = ("Scores bei der Stufe ");
        }else {
            label = ("Scores at ");
        }


        if(sLevel ==0){
            if(deutsch)
                level = "Einfach";
            else
                level = "Easy";
        }
        else if (sLevel ==1){
            if(deutsch)
                level = "Mittel";
            else
                level = "Medium";
        }
        else{
            if(deutsch)
                level = "Schwer";
            else
                level = "Hard";
        }





        LineDataSet set1 = new LineDataSet(yValues, label+level);
        set1.setFillAlpha(110);
        set1.setColor(Color.RED);
        set1.setLineWidth(2);
        set1.setDrawFilled(true);
        set1.setDrawIcons(false);
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.parseColor("#4682B4"));
        set1.setCircleColor(Color.parseColor("#4682B4"));
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);
        set1.setFormLineWidth(1f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data  =new LineData(dataSets);
        // other Parametes
        lineChart.setData(data);
        lineChart.getXAxis().setGranularityEnabled(true);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getDescription().setEnabled(false);
    }


    //show dialog

  private void showStatsDialog(int level ){
      Dialog dialog = new Dialog(ScoresActitvity.this, R.style.ScoresChart);
      dialog.setCanceledOnTouchOutside(true);
      dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
      dialog.setContentView(R.layout.chart_all_scores_dialog);
      LineChart lineChart = dialog.findViewById(R.id.lineChart);
      DatenBankHelfer datenBankHelfer = new DatenBankHelfer(ScoresActitvity.this);
      List<ScoreModel> allScores = datenBankHelfer.getEveryScore();
      List<ScoreModel> arrayEasy = Utils.getInstance().getFiltered(allScores,level);
      makeChart(lineChart, arrayEasy, level);
      dialogOn = true;
      dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
          @Override
          public void onCancel(DialogInterface dialog) {
              dialogOn=false;
          }
      });
      dialog.show();
  }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("dialogOn", dialogOn);
        outState.putInt("dispayedLevel",dispayedLevel);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dialogOn = savedInstanceState.getBoolean("dialogOn");
        dispayedLevel = savedInstanceState.getInt("dispayedLevel");
        if(dialogOn)
            showStatsDialog(dispayedLevel);

    }


    private void activateButtons(){
        DatenBankHelfer datenBankHelfer = new DatenBankHelfer(ScoresActitvity.this);
        List<ScoreModel> allS = datenBankHelfer.getEveryScore();
        List<ScoreModel> filteredArray0 = Utils.getInstance().getFiltered(allS,0);
        List<ScoreModel> filteredArray1 = Utils.getInstance().getFiltered(allS,1);
        List<ScoreModel> filteredArray2 = Utils.getInstance().getFiltered(allS,2);
        if(filteredArray0.size() == 0)
            showStats1.setEnabled(false);
        if(filteredArray1.size() == 0)
            showStats2.setEnabled(false);
        if(filteredArray2.size() == 0)
            showStats3.setEnabled(false);
    }
}