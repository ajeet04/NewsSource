package com.nickelfox.ajeet.newsapp.Activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.JsonObject;
import com.nickelfox.ajeet.newsapp.Adapters.BottomListAdapter;

import com.nickelfox.ajeet.newsapp.Adapters.NewsSourceAdapter;
import com.nickelfox.ajeet.newsapp.Adapters.SourceAdapter;
import com.nickelfox.ajeet.newsapp.Interfaces.JsonPlaceHolder;
;
import com.nickelfox.ajeet.newsapp.Models.AllSource;
import com.nickelfox.ajeet.newsapp.Models.SourceData;
import com.nickelfox.ajeet.newsapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SourceFilterActivity extends AppCompatActivity {
public RecyclerView categoryList,countryList,LangList,sourceList;
private ShimmerFrameLayout mShimmerViewContainer;
    public static String selected_lang,selected_country,selected_catogery;
    private static String  BASE_URL="https://newsapi.org/v2/";
    List<AllSource> dataList=new ArrayList<>();
    CoordinatorLayout coordinatorLayout;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_filter);
        //Activity recylerview..........................
        sourceList=findViewById(R.id.sourceList);
        sourceList.setLayoutManager(new LinearLayoutManager(this));
        sourceList.setHasFixedSize(true);

        // statusbar color change......................
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));
        // toolbar......................

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("News Sources");

         //Facebook shimmer Animation...............
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.setVisibility(View.GONE);



        // bootom view widget binding.......................
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id._coordinator);
        View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);
        categoryList=bottomSheet.findViewById(R.id.catg);
        countryList=bottomSheet.findViewById(R.id.cont);
        LangList=bottomSheet.findViewById(R.id.langg);


        categoryList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        countryList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        LangList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        Button apply_bttn=bottomSheet.findViewById(R.id.apply);
        //data from resource array.xml
        String[] cnt=getResources().getStringArray(R.array.country);
        String[] catgg=getResources().getStringArray(R.array.category);
        String[] langgg=getResources().getStringArray(R.array.langg);
        countryList.setHasFixedSize(true);
        BottomListAdapter adapter1=new BottomListAdapter(this,cnt,"country");
        countryList.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
        BottomListAdapter adapter2=new BottomListAdapter(this,langgg,"lang");
        LangList.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
        BottomListAdapter adapter3=new BottomListAdapter(this,catgg,"cat");
        categoryList.setAdapter(adapter3);
        adapter3.notifyDataSetChanged();
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);

        // on click of bottomsheet button ........................

        apply_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url=null;

                if(selected_country!=null||selected_lang!=null||selected_catogery!=null) {
                    mShimmerViewContainer.startShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.VISIBLE);

                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    behavior.setPeekHeight(100);

            // url for when select only country
                    if(selected_country!=null && selected_lang==null) {

                        url = BASE_URL + "sources?country=" + selected_country + "&apiKey=c915eb9a21964da9903c0669a199dd62";

                        }
                    // url for when select only langauge
                    if(selected_country==null && selected_lang!=null){
                        url=BASE_URL+"sources?language="+selected_lang+"&apiKey=c915eb9a21964da9903c0669a199dd62";


                    }
                    // url for when select both
                    if(selected_country!=null && selected_lang!=null){

                        url=BASE_URL+"sources?language="+selected_lang+"&country="+selected_country+"&apiKey=c915eb9a21964da9903c0669a199dd62";

                        }

                      // intialize volley request..........
                    RequestQueue queue = Volley.newRequestQueue(SourceFilterActivity.this);

                    final String finalUrl = url;
                    // Request for JSon object...................
                    JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
                       @Override
                       public void onResponse(JSONObject response) {
                           if(response!=null) {
                               try {
                                   Log.d("urlll", finalUrl);

                                           // Parsing json Array.........................
                                           JSONArray jsonArray = response.getJSONArray("sources");
                                           Log.d("Arraydata",jsonArray.toString());
                                           for (int i = 0; i < jsonArray.length(); i++) {
                                               JSONObject jsonObject=jsonArray.getJSONObject(i);
                                               String id=jsonObject.getString("id");
                                               String name=jsonObject.getString("name");
                                               String description=jsonObject.getString("description");
                                               String url=jsonObject.getString("url");
                                               String category=jsonObject.getString("category");
                                               String language=jsonObject.getString("language");
                                               String country=jsonObject.getString("country");

                                               // Adding data in List..............................
                                               AllSource source=new AllSource(id, name, description, url,
                                                      category, language, country);
                                               // check for category if selected................
                                               if(selected_catogery!=null) {
                                                   if (selected_catogery.equals(category)) {
                                                       dataList.add(source);
                                                   }
                                               }
                                               else {
                                                   dataList.add(source);
                                               }


                                   }
                                 //  Toast.makeText(SourceFilterActivity.this, dataList.size()+"", Toast.LENGTH_SHORT).show();
                                   if(dataList.size()>0) {
                                       NewsSourceAdapter adapter = new NewsSourceAdapter(SourceFilterActivity.this, dataList);
                                       sourceList.setAdapter(adapter);
                                   }else{
                                       showSnackbar("No data available for selected item");
                                   }

                                   // dissmising filter...................
                                   selected_lang="null";
                                   selected_country="null";
                                   selected_catogery="null";

                                   //Animation stop......................
                                   mShimmerViewContainer.stopShimmerAnimation();
                                   mShimmerViewContainer.setVisibility(View.GONE);

                               } catch (JSONException e) {
                                   e.printStackTrace();
                                   showSnackbar(e.getMessage());
                                   mShimmerViewContainer.stopShimmerAnimation();
                                   mShimmerViewContainer.setVisibility(View.GONE);
                               }
                           }
                           else{
                               showSnackbar("try again later");
                               mShimmerViewContainer.stopShimmerAnimation();
                               mShimmerViewContainer.setVisibility(View.GONE);
                           }

                       }
                   }, new com.android.volley.Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                           showSnackbar(error.getMessage());
                           mShimmerViewContainer.stopShimmerAnimation();
                           mShimmerViewContainer.setVisibility(View.GONE);
                          // Toast.makeText(SourceFilterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                       }
                   });
                   queue.add(jsonObjectRequest);

                }
                else{
                    showSnackbar("You have'nt selected any item");
                }

            }
        });


    }

public void showSnackbar(String message){
    Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
    snackbar.show();
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
