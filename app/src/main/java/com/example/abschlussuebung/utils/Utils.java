package com.example.abschlussuebung.utils;

import com.example.abschlussuebung.ScoreModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Utils {

    private static Utils instance;
    private static int level;
    private static int area;
    private static int mode ;

    public Utils(){
        initData();
    }

    // initialize data
    private void initData(){

        // level = 0 => default :Easy, level = 1 =>  Medium, level = 2
        area =10;
        level = 0;
        mode = 0;
    }


    public static Utils getInstance() {
        if (null != instance){
            return  instance;
        }else {
            instance = new Utils();
            return instance;
        }
    }
     // DatenBank nach Level-Grad einsotieren
    public List<ScoreModel> getFiltered(List<ScoreModel> array,int level ){
        List<ScoreModel> returnedArray = new ArrayList<>();
        for(ScoreModel sm: array){
            if(sm.getLevel() == level){
                returnedArray.add(sm);
            } }
        return  returnedArray;
    }

    //Höchste Score eingeben
    public int highestScore(List<ScoreModel> array){
        int max = 0;
        for(ScoreModel score: array){
            if(score.getScore() > max){
                max = score.getScore();
            } }
        return max;
    }

    public static void setInstance(Utils instance) {
        Utils.instance = instance;

    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int s) {
        Utils.level = s;
    }

    public static int getArea() {
        return area;
    }

    public static void setArea(int area) {
        Utils.area = area;
    }

    public static int getMode() {
        return mode;
    }

    public static void setMode(int mode) {
        Utils.mode = mode;
    }
    public String getLevelText(int l){
        boolean deutsch = Locale.getDefault().getLanguage() == "de";
        String level = new String();
        if(l== 0){
            if(deutsch)
                level ="Einfach";
            else
                level= "Easy";
        }
        else  if(l== 1){
            if(deutsch)
                level ="Mittel";
            else
                level= "Medium";
        }
        else  {
            if(deutsch)
                level ="Schwer";
            else
                level= "Hard";
        }
        String nhs = new String();
        if(deutsch){
            nhs = " Stufe "+level+"\n"+"Höchste Score !!!";
        }
        else {
            nhs = "Level "+ level +"\n"+"New Heigh Score !!!\n";
        }
        return nhs;
    }
}
