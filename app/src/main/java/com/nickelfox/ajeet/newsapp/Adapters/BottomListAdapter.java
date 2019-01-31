package com.nickelfox.ajeet.newsapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nickelfox.ajeet.newsapp.Activities.SourceFilterActivity;
import com.nickelfox.ajeet.newsapp.Models.EverthingsData;
import com.nickelfox.ajeet.newsapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BottomListAdapter extends RecyclerView.Adapter<BottomListAdapter.BH>  {
    private String[] data ;
 private String from;
 private int mCheckedPostion;
    private Context context;

    public BottomListAdapter(Context context,String[] data,String from) {
        this.context = context;
        this.data=data;
        this.from=from;


    }



    @NonNull
    @Override
    public BH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.bootomlist_item, viewGroup, false);
        return new BH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BH bh, final int i) {
        // checking which list are selected.....
         if(from.equals("country")) {
             bh.mTitle.setText(getCodeName(data[i].toUpperCase()));

         }
         if(from.equals("lang")){
             bh.mTitle.setText(getCodeName(data[i]));
         }
         if(from.equals("cat")){
             bh.mTitle.setText(data[i]);
         }

        bh.mCard.setChecked(i ==mCheckedPostion);
        bh.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

           // only one check box selection.........
                if (i == mCheckedPostion) {
                    bh.mCard.setChecked(true);
                    mCheckedPostion = -1;
                } else {
                    mCheckedPostion = i;
                    notifyDataSetChanged();
                    // updating data in sourcefilterActivity...................
                    if(from.equals("lang")){

                        SourceFilterActivity.selected_lang=data[i];
                    }
                    if(from.equals("country")){
                        SourceFilterActivity.selected_country=data[i];
                    }
                    if(from.equals("cat")){
                        SourceFilterActivity.selected_catogery=data[i];
                    }
                }

            }
        });

    }




    @Override
    public int getItemCount() {
        return data == null ? 0 : data.length;
    }

    public static class BH extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private CheckBox mCard;

        public BH(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.n_ame);
            mCard=itemView.findViewById(R.id.checkBox);

        }
    }

    public String getCodeName(String code){
        String json = null;
        try {
            InputStream is = context.getAssets().open("langauge.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        Log.e("data", json);
        try {
            JSONObject jsonobject = new JSONObject(json);
             return jsonobject.getString(code);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

}


