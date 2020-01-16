package com.example.notesapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
CompositeDisposable compositeDisposable;

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomDialogClass cdd = new CustomDialogClass(MainActivity.this);
        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });

        getData();
        compositeDisposable = new  CompositeDisposable();
    }
    private void getData(){
        TaskDatabase database = TaskDatabase.getDatabase(getApplicationContext());
        final TaskDao dao = database.taskDao();
        Single.fromCallable(() -> dao.getAll()).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(List-> {
                    recyclerView = findViewById(R.id.recycler_view);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    NotesAdapter notesAdapter = new NotesAdapter(MainActivity.this , List );
                    recyclerView.setAdapter(notesAdapter);

                }
                , error -> Log.e("error1", error.getMessage()));
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(compositeDisposable != null){
            compositeDisposable.dispose();
        }
    }
}
