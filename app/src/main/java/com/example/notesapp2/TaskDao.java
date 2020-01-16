package com.example.notesapp2;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TaskDao {
//دا بيكون مربوط ب اسم التابل نيم
    @Query("SELECT * FROM mahdi")
    List<Task> getAll();



    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Update
    void update(Task task);

}