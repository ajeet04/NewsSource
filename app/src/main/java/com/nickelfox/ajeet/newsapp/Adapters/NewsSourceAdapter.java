package com.nickelfox.ajeet.newsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.nickelfox.ajeet.newsapp.Activities.WebViewActivity;
import com.nickelfox.ajeet.newsapp.Models.AllSource;
import com.nickelfox.ajeet.newsapp.R;

import java.util.ArrayList;
import java.util.List;

public class NewsSourceAdapter  extends RecyclerView.Adapter<NewsSourceAdapter.SourcesViewHolder> {
    private List<AllSource> data = new ArrayList<>();

    private Context context;

    public NewsSourceAdapter(Context context, List<AllSource> data) {
        this.context = context;
        this.data = data;


    }

    @Override
    public SourcesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.new_source_item, parent, false);
        return new SourcesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SourcesViewHolder holder, final int position) {


        holder.name.setText(data.get(position).getName());
        holder.mDescription.setText(data.get(position).getDescription());
        holder.mLang.setText(data.get(position).getLanguage().toUpperCase());
        holder.mCountry.setText(data.get(position).getCountry().toUpperCase());
        holder.mCatogery.setText(data.get(position).getCategory()+"");
        holder.mUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,WebViewActivity.class);
                i.putExtra("url",data.get(position).getUrl());
                context.startActivity(i);

            }
        });


    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public static class SourcesViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView mDescription;
        private TextView mCountry;
        private TextView mLang;
        private TextView mCatogery;
        private TextView mUrl;

        public SourcesViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id._name);
            mDescription = (TextView) itemView.findViewById(R.id.s_desc);
            mLang = (TextView) itemView.findViewById(R.id.lang);
            mCountry = itemView.findViewById(R.id.cont);
            mCatogery = (TextView) itemView.findViewById(R.id.catogery);
            mUrl = itemView.findViewById(R.id.url);
        }
    }
}

