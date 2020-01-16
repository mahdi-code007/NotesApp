package com.example.notesapp2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.notesapp2.R;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CustomDialogClass extends Dialog implements android.view.View.OnClickListener {
    public Activity c;
    public Dialog d;
    public Button yes, no , btn_save;
    CompositeDisposable compositeDisposable;
    EditText et_task , et_desc , et_finish_by ;

    private List<Task> List2;
    NotesAdapter adapter = new NotesAdapter(c);
    public CustomDialogClass(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customdialog);
        yes =  findViewById(R.id.btn_yes);
        no =  findViewById(R.id.btn_no);
        btn_save = findViewById(R.id.btn_save);
        et_task = findViewById(R.id.et_task);
        et_desc = findViewById(R.id.et_desc);
        et_finish_by = findViewById(R.id.et_finish_by);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        btn_save.setOnClickListener(this);


        compositeDisposable = new  CompositeDisposable();

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                c.finish();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            case R.id.btn_save:
                insertData();
                break;
            default:
                break;
        }
        dismiss();
    }

    void insertData() {
        String sTask = et_task.getText().toString().trim();
        String sDesc = et_desc.getText().toString().trim();
        String sFinishBy = et_finish_by.getText().toString().trim();
        Task task = new Task();
        task.setTask(sTask);
        task.setDesc(sDesc);
        task.setFinishBy(sFinishBy);
        task.setFinished(false);
        TaskDatabase database = TaskDatabase.getDatabase(c);
        TaskDao dao = database.taskDao();
        Disposable disposable = Single.fromCallable(()->{
            dao.insert(task);
            return true;
        }).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(List -> Log.e("success","success"), error -> Log.e("error", error.getMessage()));

        compositeDisposable.add(disposable);
    }
}

