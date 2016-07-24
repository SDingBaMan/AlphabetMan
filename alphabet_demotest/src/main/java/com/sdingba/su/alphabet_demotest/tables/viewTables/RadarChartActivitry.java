
package com.sdingba.su.alphabet_demotest.tables.viewTables;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sdingba.su.alphabet_demotest.R;
import com.sdingba.su.alphabet_demotest.tables.custom.MyMarkerView;
import com.sdingba.su.alphabet_demotest.tables.notimportant.DemoBase;


import java.util.ArrayList;

public class RadarChartActivitry extends DemoBase {

    private RadarChart mChart;
    private Typeface tf;

    private TextView goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_radarchart);

        mChart = (RadarChart) findViewById(R.id.chart1);

        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        goBack = (TextView) findViewById(R.id.RadarChart_goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        mChart.setDescription("");

        mChart.setWebLineWidth(1.5f);
        mChart.setWebLineWidthInner(0.75f);
        mChart.setWebAlpha(100);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // set the marker to the chart
        mChart.setMarkerView(mv);


        addTable();
    }

    
    private void addTable() {

        int cnt = 7;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
//        ArrayList<Entry> yVals3 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < cnt; i++) {
//          yVals1.add(new Entry((float) (Math.random() * mult) + mult / 2, i));
            if (i % 2==1) {
                yVals1.add(new Entry((float) 13, i));
            }else{
                yVals1.add(new Entry((float) 10, i));
            }
        }

        for (int i = 0; i < cnt; i++) {
//          yVals2.add(new Entry((float) (Math.random() * mult) + mult / 2, i));

            yVals2.add(new Entry((float) i*2+3, i));

        }

        setData(yVals1,yVals2);

        mChart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(tf);
        xAxis.setTextSize(9f);

        YAxis yAxis = mChart.getYAxis();
        yAxis.setTypeface(tf);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinValue(0f);

        Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.RIGHT_OF_CHART);
        l.setTypeface(tf);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
    }

    private String[] mParties = new String[]{
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I"
    };

    private String[] mPartiesday= new String[]{
            "第一天", "第二天", "第三天", "第四天", "第五天", "第六天", "第七天"
    };

    public void setData(ArrayList<Entry> yVals1, ArrayList<Entry> yVals2) {

        float mult = 150;
        int cnt = 7;

        /*ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
//        ArrayList<Entry> yVals3 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < cnt; i++) {
//          yVals1.add(new Entry((float) (Math.random() * mult) + mult / 2, i));
            if (i % 2==1) {
                yVals1.add(new Entry((float) 13, i));
            }else{
                yVals1.add(new Entry((float) 10, i));
            }
        }

        for (int i = 0; i < cnt; i++) {
//          yVals2.add(new Entry((float) (Math.random() * mult) + mult / 2, i));

            yVals2.add(new Entry((float) i*2+3, i));

        }*/

//        for (int i = 0; i < cnt; i++) {
////            yVals3.add(new Entry((float) (Math.random() * mult) + mult / 2, i));
//            yVals3.add(new Entry((float) i+8+3+2, i));
//
//        }



        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < cnt; i++)
            xVals.add(mPartiesday[i % mPartiesday.length]);

        RadarDataSet set1 = new RadarDataSet(yVals1, "整体数据");
        set1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        set1.setFillColor(ColorTemplate.VORDIPLOM_COLORS[0]);

        set1.setValueTextSize(38f);
        set1.setLineWidth(7f);
        set1.setDrawFilled(true);


        RadarDataSet set2 = new RadarDataSet(yVals2, "个人数据");
        set2.setColor(ColorTemplate.VORDIPLOM_COLORS[4]);
        set2.setFillColor(ColorTemplate.VORDIPLOM_COLORS[4]);

        set2.setValueTextSize(38f);
        set2.setLineWidth(3f);
        set2.setDrawFilled(true);

//        RadarDataSet set3 = new RadarDataSet(yVals3, "Set xxxxxx");
//        set3.setColor(ColorTemplate.VORDIPLOM_COLORS[3]);
//        set3.setFillColor(ColorTemplate.VORDIPLOM_COLORS[3]);
//        set3.setDrawFilled(true);
//        set3.setLineWidth(2f);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);
        sets.add(set2);
//        sets.add(set3);



        RadarData data = new RadarData(xVals, sets);
        data.setValueTypeface(tf);
        data.setValueTextSize(2f);
//        data.setValueTextSize(20f);

        data.setDrawValues(false);

        mChart.setData(data);

        mChart.invalidate();

    }
}
