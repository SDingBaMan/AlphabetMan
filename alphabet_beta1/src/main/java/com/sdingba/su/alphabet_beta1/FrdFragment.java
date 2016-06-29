package com.sdingba.su.alphabet_beta1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FrdFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Main", "FrdFragment---onCreateViewonCreateViewonCreateView()");
        return inflater.inflate(R.layout.tab02, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        Log.i("Main", "FrdFragment---onAttach()");

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.i("Main", "FrdFragment---onCreate()");

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        Log.i("Main", "FrdFragment---onActivityCreated()");

    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Log.i("Main", "FrdFragment---onStart()");

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.i("Main", "FrdFragment---onResume()");

    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Log.i("Main", "FrdFragment---onPause()");

    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        Log.i("Main", "FrdFragment---onStop()");

    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
        Log.i("Main", "FrdFragment---onDestroyView()");

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.i("Main", "FrdFragment---onDestroy()");
    }

    @Override
    public void onDetach() {
        // TODO Auto-generated method stub
        super.onDetach();
        Log.i("Main", "FrdFragment---onDetach()");
    }
}
