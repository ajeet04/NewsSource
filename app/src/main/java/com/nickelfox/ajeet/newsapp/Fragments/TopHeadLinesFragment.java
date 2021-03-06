package com.nickelfox.ajeet.newsapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.nickelfox.ajeet.newsapp.Activities.MainActivity;
import com.nickelfox.ajeet.newsapp.Adapters.EverythingAdapter;
import com.nickelfox.ajeet.newsapp.Interfaces.JsonPlaceHolder;
import com.nickelfox.ajeet.newsapp.Models.Articles;
import com.nickelfox.ajeet.newsapp.Models.EverthingsData;
import com.nickelfox.ajeet.newsapp.R;
import com.nickelfox.ajeet.newsapp.Utilities.RetrofitClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopHeadLinesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopHeadLinesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopHeadLinesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int color;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView topList;
    private ShimmerFrameLayout mShimmerViewContainer;

    private OnFragmentInteractionListener mListener;

    public TopHeadLinesFragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public TopHeadLinesFragment(int color) {
        this.color = color;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopHeadLinesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopHeadLinesFragment newInstance(String param1, String param2) {
        TopHeadLinesFragment fragment = new TopHeadLinesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_top_head_lines, container, false);

        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        topList=view.findViewById(R.id.top_list);
        mShimmerViewContainer.startShimmerAnimation();

        topList.setLayoutManager(new LinearLayoutManager(getContext()));
        topList.setHasFixedSize(true);
        topList.setItemViewCacheSize(20);
        topList.setDrawingCacheEnabled(true);
        topList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        Retrofit retrofit=RetrofitClient.getReference();
        JsonPlaceHolder jsonPlaceHolder=retrofit.create(JsonPlaceHolder.class);
        Call<Articles> call=jsonPlaceHolder.getHeadLine();
        call.enqueue(new Callback<Articles>() {
            @Override
            public void onResponse(Call<Articles> call, Response<Articles> response) {
                if(!response.isSuccessful()){
                    MainActivity.showSnackbar("Response : "+response.code());
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    return;
                }
                else{

                    Articles jsonResponse = response.body();
                    List<EverthingsData> data = new ArrayList<>(Arrays.asList(jsonResponse.getArticles()));
                    EverythingAdapter adapter=new EverythingAdapter(getContext(),data);
                    topList.setAdapter(adapter);
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<Articles> call, Throwable t) {
                MainActivity.showSnackbar("Error : "+t.getMessage());
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
