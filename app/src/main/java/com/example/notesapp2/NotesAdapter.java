package com.example.notesapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private Context context;
    private List<Task> NotesList;

    public NotesAdapter(Context context , List<Task> list) {
        this.context = context;
        this.NotesList = list;
    }

    public NotesAdapter(Context context ) {
        this.context = context;

    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item , parent , false);
        NotesViewHolder notesViewHolder = new NotesViewHolder(view);
        return notesViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Task currentTask = NotesList.get(position);
        holder.tv_task.setText(currentTask.getTask());
        holder.tv_desc.setText(currentTask.getDesc());
        holder.tv_finish_by.setText(currentTask.getFinishBy());
    }
    @Override
    public int getItemCount() {
        return NotesList.size();
    }

    public void setNotes(List<Task> notesList) {
        this.NotesList = notesList;
        notifyDataSetChanged();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder{
        TextView tv_task , tv_desc , tv_finish_by ;
        private NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_task = itemView.findViewById(R.id.tv_task);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_finish_by = itemView.findViewById(R.id.tv_finish_by);
        }
    }
}
