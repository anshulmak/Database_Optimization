package com.example.test2.Entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word")
public class Word {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "word")
    private String word_name;

    @ColumnInfo(name = "figure of speech")
    private String figure_of_speech;

    @ColumnInfo(name = "meaning")
    private String meaning;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getWord_name() {
        return word_name;
    }

    public void setWord_name(String word_name) {
        this.word_name = word_name;
    }

    public String getFigure_of_speech() {
        return figure_of_speech;
    }

    public void setFigure_of_speech(String figure_of_speech) {
        this.figure_of_speech = figure_of_speech;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

}