package com.example.test2.Utils;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.test2.Database.AppDatabase;
import com.example.test2.Entity.Word;

import java.util.List;

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    private static Word addWord(@NonNull final AppDatabase db, Word word) {
        db.wordDao().insertAll(word);
        return word;
    }

    private static void populateWithTestData(AppDatabase db) {
        Word word = new Word();
        word.setWord_name("Amrut");
        word.setFigure_of_speech("Adj");
        word.setMeaning("Smart");
        addWord(db, word);

        List<Word> userList = db.wordDao().getAll();
        Log.d(DatabaseInitializer.TAG, "Rows Count: " + userList.size());
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }
}
