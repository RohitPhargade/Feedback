package com.example.feedback;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.feedback.MyAdapter.MyViewHolder;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<Model> mList;
    Context context;

    public MyAdapter(Context context, ArrayList<Model> mList) {

        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Model model = mList.get(position);
        holder.name.setText(model.getName());
        holder.phone.setText(model.getNumber());
        holder.suggestions.setText(model.getSuggestions());
        holder.radio.setText(model.getRadio());
        if (holder.radio.getText().toString() == "Excellent") {
            holder.radio.setTextColor(Color.parseColor("#7CFC00"));
        } else if (model.getRadio() == "Average") {
            holder.radio.setTextColor(Color.parseColor("#FF0000"));

        } else {
            holder.radio.setTextColor(Color.parseColor("#00FA9A"));
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView phone, suggestions, radio, name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nametext);
            phone = itemView.findViewById(R.id.numbertext);
            suggestions = itemView.findViewById(R.id.suggestionstext);
            radio = itemView.findViewById(R.id.radiotext);
//            if (radio != null && "Excellent" == radio.getText().toString()) {
////                radio.setTextColor(Color.parseColor("#FF0000"));
////                phone.setTextColor(#FF0000);
//            } else {
//                radio.setTextColor(Color.parseColor("#7CFC00"));
//            }


        }
    }


}
