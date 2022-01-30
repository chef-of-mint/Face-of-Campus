package com.example.patronus.List_from_search;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patronus.R;
import com.example.patronus.rec_home.Adapter;


import java.util.List;

public class Search_Adapter extends RecyclerView.Adapter<Search_Adapter.ViewHolder>{
    private List<Search_Model> searchList;
    public Search_Adapter(List<Search_Model>searchList){this.searchList=searchList;}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rec_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nametext.setText(searchList.get(position).getName());
        holder.placetext.setText(searchList.get(position).getPlace());
        holder.branchtext.setText(searchList.get(position).getBranch());
        holder.yeartext.setText(searchList.get(position).getYear());

        holder.imgview.setImageBitmap(setData(searchList.get(position).getImg()));
        //holder.imgview.setImageResource(R.drawable.ic_launcher_background);
        Log.d("hhhh",setData(searchList.get(position).getImg()).toString());
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgview;
        private TextView nametext,placetext,branchtext,yeartext;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgview=itemView.findViewById(R.id.imageView3);
            nametext=itemView.findViewById(R.id.textView5);
            placetext=itemView.findViewById(R.id.textView6);
            branchtext=itemView.findViewById(R.id.textView7);
            yeartext=itemView.findViewById(R.id.textView8);
        }
    }

    public Bitmap setData(String base) {
        byte[] decodedString = Base64.decode(base, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte ;
    }
}
