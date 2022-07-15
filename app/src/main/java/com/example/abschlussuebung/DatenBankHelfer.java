package com.example.abschlussuebung;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatenBankHelfer  extends SQLiteOpenHelper {


    public static final String COLUMN_SCORE = "SCORE";
    public static final String SCORE_TABLE = COLUMN_SCORE + "_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_MODE = "MODE";
    public static final String COLUMN_LEVEL = "LEVEL";

    public DatenBankHelfer(@Nullable Context context) {
        super(context, "scores7.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + SCORE_TABLE + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SCORE + " INT ," +
                COLUMN_MODE + " INT, " + COLUMN_LEVEL + " INT)";
        db.execSQL(createTableStatement);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(ScoreModel scoreModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SCORE, scoreModel.getScore());
        cv.put(COLUMN_MODE, scoreModel.getMode());
        cv.put(COLUMN_LEVEL, scoreModel.getLevel());
        long insert = db.insert(SCORE_TABLE, null, cv);
        if (insert == -1) {
            return false;
        }
        return true;
    }

    public List<ScoreModel> getEveryScore(){
        List<ScoreModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+SCORE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        // Wenn der erster Element vohanden ist => DB nicht leer
        if(cursor.moveToFirst()){
            // loop through the cursor
            do{

                int scoreID = cursor.getInt(0);
                int scoreValue = cursor.getInt(1);
                int mode = cursor.getInt(2);
                int level = cursor.getInt(3);
                ScoreModel scoreModel = new ScoreModel(scoreID, scoreValue, mode, level);
                returnList.add(scoreModel);

            }while (cursor.moveToNext());

        }else{
            // failure
        }
        cursor.close();
        db.close();
        return   returnList;
    }
}
