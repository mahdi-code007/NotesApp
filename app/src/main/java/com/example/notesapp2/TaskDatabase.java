package com.example.notesapp2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1 , exportSchema =  false)
public abstract class TaskDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    private static volatile TaskDatabase INSTANCE;
//استاتيك
    public static TaskDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (TaskDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            TaskDatabase.class, "task_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}