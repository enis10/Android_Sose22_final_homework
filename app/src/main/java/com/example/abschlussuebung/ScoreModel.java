package com.example.abschlussuebung;

public class ScoreModel {
    int id;
    int score;
    int mode;
    int level;

    public ScoreModel(int id, int score, int mode, int level) {
        this.id = id;
        this.score = score;
        this.mode = mode;
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ScoreModel{" +
                "id=" + id +
                ", score=" + score +
                ", mode=" + mode +
                ", level=" + level +
                '}';
    }
}
