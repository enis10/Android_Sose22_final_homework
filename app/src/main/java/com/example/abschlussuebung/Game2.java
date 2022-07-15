package com.example.abschlussuebung;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.abschlussuebung.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Game2 implements Parcelable {

    private Question question1, question2, question3;
    List<Question> curentQuestions;
    int correctAnswers, selectedAnswers;
    int steps;
    private int score=0;

    public  Game2(){
       setCurrentQuestions();
        steps = 0;
    }


    protected Game2(Parcel in) {
        correctAnswers = in.readInt();
        selectedAnswers = in.readInt();
        steps = in.readInt();
        score = in.readInt();
    }

    public static final Creator<Game2> CREATOR = new Creator<Game2>() {
        @Override
        public Game2 createFromParcel(Parcel in) {
            return new Game2(in);
        }

        @Override
        public Game2[] newArray(int size) {
            return new Game2[size];
        }
    };

    //**********Chek if a question is correct*******
     public  boolean checkOneQuestion(Question question){
        if(question.getProposition() == question.getAnswer()){
            return  true;
        }else
            return  false;
    }
    //***********************************************

    //****************Hooks**************************
    public void setCurrentQuestions(){
        question1 = new Question(Utils.getInstance().getArea());
        question2 = new Question(Utils.getInstance().getArea());
        question3 = new Question(Utils.getInstance().getArea());
        curentQuestions = new ArrayList<Question>();
        curentQuestions.add(question1);
        curentQuestions.add(question2);
        curentQuestions.add(question3);
        correctAnswers =0;
        selectedAnswers= 0;
    }
    //*********************************************


    // ************Prüft ob die gewählte Antworten reichen*******
    // es kann sein dass mehere Fragen richtig sind
    public boolean checkChoices(){
        correctAnswers= 0;
        for (Question q: curentQuestions){
            if(checkOneQuestion(q))
                correctAnswers++;
        }
        if (selectedAnswers == correctAnswers){
            steps++;
            score = steps *10;
            return true;}
        else
            return false;
    }
    //*******************w wenn man eine Antwort wählt**************
    public void incrementSelectedChoices(){
        selectedAnswers++;
    }

    public int howManyAnswers(){
        int ans = 0;
        for (Question q: curentQuestions){
            if(checkOneQuestion(q))
                ans++;
        }
        return ans;
    }



    public String[] displayCorrectAnswers (){
        String[] correctAnswers = new String[3];
      for(int i= 0; i<curentQuestions.size();i++){
          if(checkOneQuestion(curentQuestions.get(i))){
              correctAnswers[i]= curentQuestions.get(i).getQuestionPhrase();
          }else{
              correctAnswers[i]="";
          }
      }
        return correctAnswers;
    }










    //****************   Setters und Getters   *************************

    public Question getQuestion1() {
        return question1;
    }

    public void setQuestion1(Question question1) {
        this.question1 = question1;
    }

    public Question getQuestion2() {
        return question2;
    }

    public void setQuestion2(Question question2) {
        this.question2 = question2;
    }

    public Question getQuestion3() {
        return question3;
    }

    public void setQuestion3(Question question3) {
        this.question3 = question3;
    }

    public List<Question> getCurentQuestions() {
        return curentQuestions;
    }

    public void setCurentQuestions(List<Question> curentQuestions) {
        this.curentQuestions = curentQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getSelectedAnswers() {
        return selectedAnswers;
    }

    public void setSelectedAnswers(int selectedAnswers) {
        this.selectedAnswers = selectedAnswers;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(correctAnswers);
        dest.writeInt(selectedAnswers);
        dest.writeInt(steps);
        dest.writeInt(score);
    }
}
