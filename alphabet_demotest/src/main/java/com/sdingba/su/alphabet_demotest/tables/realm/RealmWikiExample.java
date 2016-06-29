package com.sdingba.su.alphabet_demotest.tables.realm;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.realm.implementation.RealmBarData;
import com.github.mikephil.charting.data.realm.implementation.RealmBarDataSet;
import com.github.mikephil.charting.data.realm.implementation.RealmLineData;
import com.github.mikephil.charting.data.realm.implementation.RealmLineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sdingba.su.alphabet_demotest.R;


import java.util.ArrayList;

import io.realm.RealmResults;

//import com.xxmassdeveloper.mpchartexample.R;

/**
 * Created by Philipp Jahoda on 18/12/15.
 */
public class RealmWikiExample extends RealmBaseActivity {

    private LineChart lineChart;
    private BarChart barChart;
    private TextView goBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_realm_wiki);

        lineChart = (LineChart) findViewById(R.id.lineChart);
        barChart = (BarChart) findViewById(R.id.barChart);
        setup(lineChart);
        setup(barChart);
        goBack = (TextView) findViewById(R.id.RealmWiki_ogBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lineChart.setExtraBottomOffset(5f);
        barChart.setExtraBottomOffset(5f);

        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
    }

    @Override
    protected void onResume() {
        super.onResume(); // setup realm

        mRealm.beginTransaction();

        setDataScore();

        mRealm.commitTransaction();

        // add data to the chart
        setData();
    }

    /**
     * 给数据进行，赋值， 显示在数据的出口中
     */
    private void setDataScore() {
        // write some demo-data into the realm.io database
        // 在这里面显示数据的值，然后显示在数据中来，数据的显示是主要的；
        //// TODO: 16-5-19
        Score score1 = new Score(100f, 0, "Day 01");
        mRealm.copyToRealm(score1);
        Score score2 = new Score(10f, 1, "Day 02");
        mRealm.copyToRealm(score2);
        Score score3 = new Score(30f, 2, "Day 03");
        mRealm.copyToRealm(score3);
        Score score4 = new Score(70f, 3, "Day 04");
        mRealm.copyToRealm(score4);
        Score score5 = new Score(80f, 4, "Day 05");
        mRealm.copyToRealm(score5);
        Score score6 = new Score(80f, 5, "Day 06");
        mRealm.copyToRealm(score6);
        Score score7 = new Score(50f, 6, "Day 07");
        mRealm.copyToRealm(score7);
    }

    private void setData() {

        // LINE-CHART
        RealmResults<Score> results = mRealm.allObjects(Score.class);

        RealmLineDataSet<Score> lineDataSet = new RealmLineDataSet<Score>(
                results, "totalScore", "scoreNr");
        lineDataSet.setDrawCubic(false);
        lineDataSet.setLabel("sssss 数据");
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setColor(ColorTemplate.rgb("#FF5722"));
        lineDataSet.setCircleColor(ColorTemplate.rgb("#FF5722"));
        lineDataSet.setLineWidth(1.8f);
        lineDataSet.setCircleSize(3.6f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(lineDataSet);

        RealmLineData lineData = new RealmLineData(results,
                "playerName", dataSets);
        styleData(lineData);

        // set data
        lineChart.setData(lineData);
        lineChart.animateY(1400, Easing.EasingOption.EaseInOutQuart);


        // BAR-CHART
        RealmBarDataSet<Score> barDataSet = new RealmBarDataSet<Score>(results, "totalScore", "scoreNr");
        barDataSet.setColors(new int[]{ColorTemplate.rgb("#FF5722"), ColorTemplate.rgb("#03A9F4")});
        barDataSet.setLabel("xxxxxxx数据");

        ArrayList<IBarDataSet> barDataSets = new ArrayList<IBarDataSet>();
        barDataSets.add(barDataSet);

        RealmBarData barData = new RealmBarData(results, "playerName", barDataSets);
        styleData(barData);

        barChart.setData(barData);
        barChart.animateY(1400, Easing.EasingOption.EaseInOutQuart);
    }
}
