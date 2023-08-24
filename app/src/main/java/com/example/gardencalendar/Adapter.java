package com.example.gardencalendar;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>  {

    private final LayoutInflater layoutInflater;
    private final List<Note> notesList;

    Adapter(Context context, List<Note> notesList){
        this.layoutInflater = LayoutInflater.from(context);
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.custom_list_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        // i - position
        String  title    = notesList.get(i).getTitle();
        String  date     = notesList.get(i).getDate();
        String  time     = notesList.get(i).getTime();
        long    ID       = notesList.get(i).getID();

        viewHolder.nTitle.setText(title);
        viewHolder.nDate.setText(date);
        viewHolder.nTime.setText(time);
        viewHolder.nID.setText(String.valueOf(ID));
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nTitle,nDate,nTime,nID;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            nTitle  = itemView.findViewById(R.id.nTitle);
            nDate   = itemView.findViewById(R.id.nDate);
            nTime   = itemView.findViewById(R.id.nTime);
            nID     = itemView.findViewById(R.id.listId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(),Detail.class);
                    i.putExtra("ID",notesList.get(getAdapterPosition()).getID());
                    v.getContext().startActivity(i);
                }
            });
        }
    }
}