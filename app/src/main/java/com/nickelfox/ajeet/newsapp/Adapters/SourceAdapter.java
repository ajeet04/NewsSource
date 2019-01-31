package com.nickelfox.ajeet.newsapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nickelfox.ajeet.newsapp.Models.AllSource;
import com.nickelfox.ajeet.newsapp.Models.EverthingsData;
import com.nickelfox.ajeet.newsapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SourceAdapter  extends RecyclerView.Adapter<SourceAdapter.SourceViewHolder>  {
    private List<AllSource> data = new ArrayList<>();

    private Context context;

    public SourceAdapter(Context context,List<AllSource> data) {
        this.context = context;
        this.data=data;


    }

    @Override
    public SourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.source_item, parent, false);
        return new SourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SourceViewHolder holder, int position) {


       holder.name.setText(data.get(position).getName());
       holder.mDescription.setText(data.get(position).getDescription());
        holder.mLang.setText(data.get(position).getLanguage().toUpperCase());
        holder.mCountry.setText(data.get(position).getCountry().toUpperCase());


    }



    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public static class SourceViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView mDescription;
        private TextView mCountry;
        private TextView mLang;

        public SourceViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id._name);
            mDescription = (TextView) itemView.findViewById(R.id.s_desc);
            mLang = (TextView) itemView.findViewById(R.id.lang);
            mCountry=itemView.findViewById(R.id.cont);
        }
    }
}

