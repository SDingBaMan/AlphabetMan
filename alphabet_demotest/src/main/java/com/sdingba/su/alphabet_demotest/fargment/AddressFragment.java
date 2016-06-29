package com.sdingba.su.alphabet_demotest.fargment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sdingba.su.alphabet_demotest.MainActivity;
import com.sdingba.su.alphabet_demotest.R;
import com.sdingba.su.alphabet_demotest.tables.realm.RealmWikiExample;
import com.sdingba.su.alphabet_demotest.tables.viewTables.ListViewBarChartActivity;
import com.sdingba.su.alphabet_demotest.tables.viewTables.PieChartActivity;
import com.sdingba.su.alphabet_demotest.tables.viewTables.RadarChartActivitry;
import com.sdingba.su.alphabet_demotest.utils.PromptManager;
import com.sdingba.su.alphabet_demotest.view.newsActivity;

public class AddressFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout yuanxing, zhuxing, multi_zhuxing, realmAC;
    private RelativeLayout New_Viewpager;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab03, container, false);

        yuanxing = (RelativeLayout) view.findViewById(R.id.yuanxing);
        zhuxing = (RelativeLayout) view.findViewById(R.id.zhuxing);
        multi_zhuxing = (RelativeLayout) view.findViewById(R.id.multi_zhuxing);
        realmAC = (RelativeLayout) view.findViewById(R.id.xianxing_tables);
        New_Viewpager = (RelativeLayout) view.findViewById(R.id.new_list_view);


        yuanxing.setOnClickListener(this);
        zhuxing.setOnClickListener(this);
        multi_zhuxing.setOnClickListener(this);
        realmAC.setOnClickListener(this);
        New_Viewpager.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {

        super.onStart();

        Log.i("Main", "3333333333333333---onStart()");

    }

    @Override
    public void onResume() {

        super.onResume();

        Log.i("Main", "3333333333333333---onResume()");

    }

    @Override
    public void onPause() {

        super.onPause();

        Log.i("Main", "3333333333333333   t2---onPause()");

    }

    @Override
    public void onStop() {

        super.onStop();

        Log.i("Main", "3333333333333333---onStop()");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yuanxing:
                Intent intent = new Intent();
                intent.setClass(getActivity(), PieChartActivity.class);
                startActivity(intent);
                PromptManager.showToastTest(getActivity(), "PieChartActivity");

                break;
            case R.id.zhuxing:
//                RadarChartActivitry
                Intent intent2 = new Intent();
                intent2.setClass(getActivity(), RadarChartActivitry.class);
                startActivity(intent2);
                PromptManager.showToastTest(getActivity(), "RadarChartActivitry");
                break;

            case R.id.multi_zhuxing:
//                RadarChartActivitry
                Intent intent3 = new Intent();
                intent3.setClass(getActivity(), ListViewBarChartActivity.class);
                startActivity(intent3);
                PromptManager.showToastTest(getActivity(), "ListViewBarChartActivity");
                break;

            case R.id.xianxing_tables:
//                RadarChartActivitry
                Intent intent4 = new Intent();
                intent4.setClass(getActivity(), RealmWikiExample.class);
                startActivity(intent4);
                PromptManager.showToastTest(getActivity(), "RealmWikiExample");
                break;
            case R.id.new_list_view:
                // TODO: 16-5-23 设置 ViewPager  用于显示戒烟新闻的内容的；
                Intent intent5 = new Intent();
                intent5.setClass(getActivity(), newsActivity.class);
                startActivity(intent5);
                break;
        }
    }

//    public void onclickID(View v){
//        switch (v.getId()) {
//            case R.id.yuanxing:
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), PieChartActivity.class);
//                startActivity(intent);
//
//                break;
//            case R.id.zhuxing:
////                RadarChartActivitry
//                Intent intent2 = new Intent();
//                intent2.setClass(getActivity(), RadarChartActivitry.class);
//                startActivity(intent2);
//                Toast.makeText(getActivity(), "xxxx", Toast.LENGTH_LONG).show();
//                break;
//
//            case R.id.multi_zhuxing:
////                RadarChartActivitry
//                Intent intent3 = new Intent();
//                intent3.setClass(getActivity(), ListViewBarChartActivity.class);
//                startActivity(intent3);
//                Toast.makeText(getActivity(), "xxxx", Toast.LENGTH_LONG).show();
//                break;
//
//            case R.id.realmAC:
////                RadarChartActivitry
//                Intent intent4 = new Intent();
//                intent4.setClass(getActivity(), RealmWikiExample.class);
//                startActivity(intent4);
//                Toast.makeText(getActivity(), "xxxx", Toast.LENGTH_LONG).show();
//                break;
//        }
//    }

//    @Override
//    public void onDestroyView() {
//
//        super.onDestroyView();
//        Log.i("Main", "3333333333333333---onDestroyView()");
//
//    }
//
//    @Override
//    public void onDestroy() {
//
//        super.onDestroy();
//        Log.i("Main", "3333333333333333---onDestroy()");
//    }
//
//    @Override
//    public void onDetach() {
//
//        super.onDetach();
//        Log.i("Main", "3333333333333333---onDetach()");
//    }
}
