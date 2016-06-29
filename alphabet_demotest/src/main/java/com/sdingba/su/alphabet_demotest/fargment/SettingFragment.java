package com.sdingba.su.alphabet_demotest.fargment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdingba.su.alphabet_demotest.ConstantValue;
import com.sdingba.su.alphabet_demotest.R;
import com.sdingba.su.alphabet_demotest.SharPredInter;
import com.sdingba.su.alphabet_demotest.view.ExitApp;
import com.sdingba.su.alphabet_demotest.view.Login;

public class SettingFragment extends Fragment {

    private SharedPreferences pref = null;
    private Button exitButton;
    private RelativeLayout login;
    private TextView login_text;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab04, container, false);


        initView(view);
        initLineres();

        return view;
    }

    /**
     * 初始化  设配
     */
    private void initView(View view) {
        exitButton = (Button) view.findViewById(R.id.settingCancelbutton);
        login = (RelativeLayout) view.findViewById(R.id.login_activity);
        login_text = (TextView) view.findViewById(R.id.login_tv);

    }


    /**
     * 适配器的 使用
     */
    private void initLineres() {

        pref = getActivity().getSharedPreferences(SharPredInter.SHAR_TABLE_NAME, Context.MODE_PRIVATE);


        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ExitApp.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Login.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i("Main", "44444444444444444---onStart()");

    }

    @Override
    public void onResume() {

        super.onResume();
        Log.i("Main", "44444444444444444---onResume()");
        String username = pref.getString("username", "");
        if (username.equals("") || username == null) {
            login_text.setText("登陆");
        }else{
            login_text.setText(username);
        }
    }

    @Override
    public void onPause() {

        super.onPause();

        Log.i("Main", "44444444444444444   t2---onPause()");

    }

    @Override
    public void onStop() {

        super.onStop();

        Log.i("Main", "44444444444444444---onStop()");

    }


//	@Override
//	public void onDestroyView() {
//		// TODO Auto-generated method stub
//		super.onDestroyView();
//		Log.i("Main", "44444444444444444---onDestroyView()");
//
//	}
//
//	@Override
//	public void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		Log.i("Main", "44444444444444444---onDestroy()");
//	}
//
//	@Override
//	public void onDetach() {
//		// TODO Auto-generated method stub
//		super.onDetach();
//		Log.i("Main", "44444444444444444---onDetach()");
//	}
}
