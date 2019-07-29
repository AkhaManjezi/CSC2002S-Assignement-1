package com.example.assignment1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.DiaryEntryViewHolder> {
    private ArrayList<DiaryEntry> diaryEntries;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class DiaryEntryViewHolder extends RecyclerView.ViewHolder {

        public TextView dateView;
        public TextView NKIView;

        public DiaryEntryViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            dateView = itemView.findViewById(R.id.dateText);
            NKIView = itemView.findViewById(R.id.NKIValue);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public DiaryEntryAdapter(ArrayList<DiaryEntry> diaryEntryArrayList){
        diaryEntries = diaryEntryArrayList;
    }

    @NonNull
    @Override
    public DiaryEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_item, parent, false);
        DiaryEntryViewHolder diaryEntryViewHolder = new DiaryEntryViewHolder(v, mListener);
        return diaryEntryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryEntryViewHolder holder, int position) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE d MMM yyyy");
        DiaryEntry currentEntry = diaryEntries.get(position);
        holder.NKIView.setText(currentEntry.getNKI());
        holder.dateView.setText(formatter.format(currentEntry.getDate().getTime()));

    }

    @Override
    public int getItemCount() {
        return diaryEntries.size();
    }
}
