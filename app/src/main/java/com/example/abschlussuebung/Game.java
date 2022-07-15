package com.example.abschlussuebung;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.abschlussuebung.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Game implements Parcelable {
    private List<Question> questions ;

    private int numberCorrect;
    private int nimberIncorrect;
    private int totalQuestions;
    private int score;
    private Question currentQuestion;

    public Game(){
        questions = new ArrayList<Question>();
        numberCorrect = 0;
        nimberIncorrect = 0;
        totalQuestions = 0;
        score = 0;
        currentQuestion = new Question(Utils.getInstance().getArea());
    }

    protected Game(Parcel in) {
        numberCorrect = in.readInt();
        nimberIncorrect = in.readInt();
        totalQuestions = in.readInt();
        score = in.readInt();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public void makeNewQuestion(){
        currentQuestion = new Question(Utils.getInstance().getArea());
        totalQuestions ++;
        questions.add(currentQuestion);
    }
    public boolean checkAnswer(boolean submittedAnswer){
        boolean isCorrect;
        boolean fact = currentQuestion.getAnswer() == currentQuestion.getProposition();
        if(fact == submittedAnswer){
            numberCorrect++;
            isCorrect = true;
        }else{

            isCorrect = false;
        }
        score = numberCorrect * 10;
        return isCorrect;

    }



    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getNumberCorrect() {
        return numberCorrect;
    }

    public void setNumberCorrect(int numberCorrect) {
        this.numberCorrect = numberCorrect;
    }

    public int getNimberIncorrect() {
        return nimberIncorrect;
    }

    public void setNimberIncorrect(int nimberIncorrect) {
        this.nimberIncorrect = nimberIncorrect;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(numberCorrect);
        dest.writeInt(nimberIncorrect);
        dest.writeInt(totalQuestions);
        dest.writeInt(score);
    }
}
