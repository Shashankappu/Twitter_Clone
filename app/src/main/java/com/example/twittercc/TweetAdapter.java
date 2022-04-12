package com.example.twittercc;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.MytweeetHolder> {
    private Context context;
    private List<Tweet> tweetList;
    public TweetAdapter(Context context, int simple_list_item_1, List<Tweet> tweets){

        this.context = context;
        this.tweetList = tweets;
    }

    @NonNull
    @Override
    public MytweeetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet,parent,false);
        return  new MytweeetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MytweeetHolder holder, int position) {
        Tweet tweet = tweetList.get(position);
        holder.Displayname.setText(tweet.getDisplayname());
        holder.Username.setText("@" + tweet.getUsername());
        holder.Tweet.setText(tweet.getTweet());
        holder.Tweettime.setText(tweet.getPublishedTime());
        holder.Displayname.setTextColor(Color.BLACK);
        holder.Username.setTextColor(Color.BLACK);
        holder.Tweet.setTextColor(Color.BLACK);
        holder.Tweettime.setTextColor(Color.BLACK);
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }

    public class  MytweeetHolder extends RecyclerView.ViewHolder{
        public TextView Displayname;
        public TextView Username;
        public TextView Tweet;
        public TextView Tweettime;

        public MytweeetHolder(View v){
             super(v);
             Displayname = v.findViewById(R.id.txt_displayname);
             Username = v.findViewById(R.id.txt_username);
             Tweet = v.findViewById(R.id.txt_tweet);
             Tweettime = v.findViewById(R.id.txt_tweettime);
        }

    }
}
