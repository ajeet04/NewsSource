package com.nickelfox.ajeet.newsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nickelfox.ajeet.newsapp.Activities.FullArticleActivity;
import com.nickelfox.ajeet.newsapp.Models.EverthingsData;
import com.nickelfox.ajeet.newsapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EverythingAdapter extends RecyclerView.Adapter<EverythingAdapter.EverythingViewHolder>  {
    private List<EverthingsData> data = new ArrayList<>();

    private Context context;

    public EverythingAdapter(Context context,List<EverthingsData> data) {
        this.context = context;
        this.data=data;


    }

    @Override
    public EverythingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.everything_item, parent, false);
        return new EverythingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EverythingViewHolder holder, final int position) {

        // setting data in view....................
        holder.mTitle.setText(data.get(position).getTitle());
        holder.mDescription.setText(data.get(position).getDescription());
        holder.mTime.setText(data.get(position).getPublishedAt());
        ImageView mPoster=holder.poster;
        // Loading image from url.......................
        Picasso.get().load(data.get(position).getUrlToImage()).fit()
                .centerCrop().placeholder(R.drawable.header).into(mPoster);
        holder.article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open fullArticleView.............................
         Intent i=new Intent(context,FullArticleActivity.class);
                i.putExtra("imageurl",data.get(position).getUrlToImage());
                i.putExtra("author",data.get(position).getAuthor());
                i.putExtra("url",data.get(position).getUrl());
                i.putExtra("description",data.get(position).getDescription());
                i.putExtra("content",data.get(position).getContent());
                i.putExtra("publishedAt",data.get(position).getPublishedAt());
                i.putExtra("title",data.get(position).getTitle());
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public static class EverythingViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mDescription;
        private TextView mTime;
        private ImageView poster;
        private TextView article;

        public EverythingViewHolder(View itemView) {
            super(itemView);
           //binding view...........................
            mTitle = (TextView) itemView.findViewById(R.id.title_);
            mDescription = (TextView) itemView.findViewById(R.id.descreption);
            mTime = (TextView) itemView.findViewById(R.id.publish_time);
            poster=itemView.findViewById(R.id.poster);
            article=itemView.findViewById(R.id.view_article);

        }
    }
}

