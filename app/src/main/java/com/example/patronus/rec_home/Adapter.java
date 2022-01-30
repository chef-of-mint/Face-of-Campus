package com.example.patronus.rec_home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patronus.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Model> imglist;

    public Adapter(List<Model>imglist){this.imglist=imglist;}

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_home_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        int resource= imglist.get(position).getImageview();
        String text=imglist.get(position).getTextview();
        holder.setData(resource,text);
    }

    @Override
    public int getItemCount() {
        return imglist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgview;
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgview=itemView.findViewById(R.id.img);
            textView=itemView.findViewById(R.id.txt);
        }

        public void setData(int resource,String text) {
            imgview.setImageResource(resource);
            textView.setText(text);
        }
    }
}
