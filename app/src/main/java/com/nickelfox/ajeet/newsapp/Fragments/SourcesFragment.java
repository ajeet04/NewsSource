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
import android.widget.FrameLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.nickelfox.ajeet.newsapp.Activities.MainActivity;
import com.nickelfox.ajeet.newsapp.Adapters.SourceAdapter;
import com.nickelfox.ajeet.newsapp.Interfaces.JsonPlaceHolder;
import com.nickelfox.ajeet.newsapp.Models.AllSource;
import com.nickelfox.ajeet.newsapp.Models.SourceData;
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
 * {@link SourcesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SourcesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SourcesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ShimmerFrameLayout mShimmerViewContainer;
  private int color;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
     RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    public SourcesFragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public SourcesFragment(int color) {
        this.color = color;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SourcesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SourcesFragment newInstance(String param1, String param2) {
        SourcesFragment fragment = new SourcesFragment();
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
        View view= inflater.inflate(R.layout.fragment_sources, container, false);
        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);

        frameLayout.setBackgroundColor(color);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.startShimmerAnimation();

        recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        // Intializing Retrofit.......................
        Retrofit retrofit=RetrofitClient.getReference();
        JsonPlaceHolder jsonPlaceHolder=retrofit.create(JsonPlaceHolder.class);
        Call<SourceData> call=jsonPlaceHolder.getAllSource();
        call.enqueue(new Callback<SourceData>() {
            @Override
            public void onResponse(Call<SourceData> call, Response<SourceData> response) {
                if(!response.isSuccessful()){
                    MainActivity.showSnackbar("Response : "+response.code());
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    return;
                }
                else{
                    SourceData jsonResponse = response.body();

                    List<AllSource> data = new ArrayList<>(Arrays.asList(jsonResponse.getSource()));
                    SourceAdapter adapter=new SourceAdapter(getContext(),data);
                    recyclerView.setAdapter(adapter);
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);


                }
            }

            @Override
            public void onFailure(Call<SourceData> call, Throwable t) {

                MainActivity.showSnackbar(t.getMessage());
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
