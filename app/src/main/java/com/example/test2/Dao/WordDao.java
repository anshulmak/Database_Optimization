package com.example.test2.Dao;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.test2.Entity.Word;

import java.util.List;

@Dao
public interface WordDao {

    @Query("SELECT * FROM word")
    List<Word> getAll();

    @Query("SELECT COUNT(*) from word")
    int countWords();

    @Insert
    void insertAll(Word... words);

    @Delete
    void delete(Word word);
}
