package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusiclistAdapter extends RecyclerView.Adapter<MusiclistAdapter.ViewHolder> {

    ArrayList<AudioModel> songlist;
    Context context;

    public MusiclistAdapter(ArrayList<AudioModel> songlist, Context context) {
        this.songlist = songlist;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new MusiclistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        AudioModel songdata = songlist.get(position);
        holder.titleview.setText(songdata.getTitle());

        if(Mymediaplayer.currentIndex == position){
            holder.titleview.setTextColor(Color.parseColor("#F10F0F"));
        }
        else {
            holder.titleview.setTextColor(Color.parseColor("#000000"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mymediaplayer.getInstance().reset();
                Mymediaplayer.currentIndex = position;
                Intent intent = new Intent(context,MusicPlayerActivity.class);
                intent.putExtra("LIST" ,songlist);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView titleview;
        ImageView iconview;
        public ViewHolder( View itemView) {
            super(itemView);
            titleview = itemView.findViewById(R.id.music_list);
            iconview = itemView.findViewById(R.id.icon_view);
        }
    }
}
