
package com.sdingba.su.alphabet_demotest.tables.viewTables;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sdingba.su.alphabet_demotest.R;
import com.sdingba.su.alphabet_demotest.SharPredInter;
import com.sdingba.su.alphabet_demotest.bean.userData;
import com.sdingba.su.alphabet_demotest.engine.Impl.UserDataEngineImpl;
import com.sdingba.su.alphabet_demotest.engine.UserDataEngine;
import com.sdingba.su.alphabet_demotest.net.MyHttpAsyncTask;
import com.sdingba.su.alphabet_demotest.tables.notimportant.DemoBase;
import com.sdingba.su.alphabet_demotest.utils.PromptManager;

import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends DemoBase
        implements OnSeekBarChangeListener,
        OnChartValueSelectedListener {


    private static final String TAG = "PieChartActivity";
    private PieChart mChart;
//    private SeekBar mSeekBarX, mSeekBarY;
//    private TextView tvX, tvY;


    private SharedPreferences pref;

    private Typeface tf;
    private TextView goBack;

    private TextView xiyanValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_piechart);

//        tvX = (TextView) findViewById(R.id.tvXMax);
//        tvY = (TextView) findViewById(R.id.tvYMax);


        xiyanValue = (TextView) findViewById(R.id.xiyanNumber);
//        mSeekBarX = (SeekBar) findViewById(R.id.seekBar1);
//        mSeekBarY = (SeekBar) findViewById(R.id.seekBar2);
        goBack = (TextView) findViewById(R.id.pieChart_goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//        mSeekBarY.setProgress(10);

//        mSeekBarX.setOnSeekBarChangeListener(this);
//        mSeekBarY.setOnSeekBarChangeListener(this);

        mChart = (PieChart) findViewById(R.id.chart1);
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);

        addTable();


        pref = getSharedPreferences(SharPredInter.SHAR_TABLE_NAME, MODE_PRIVATE);

        String usernamexx = pref.getString(SharPredInter.USER_NAME, "");

        if (usernamexx != null && usernamexx != "") {

            new MyHttpAsyncTask<String, List<userData>>(PieChartActivity.this) {

                @Override
                protected void onPreExecute() {


                    super.onPreExecute();
                }

                @Override
                protected List<userData> doInBackground(String... params) {
                    String userName = params[0];
                    List<userData> userDatas = null;
                    UserDataEngine userData = new UserDataEngineImpl();

                    userDatas = userData.pullUserDatafromWeb(userName);

                    return userDatas;
                }

                @Override
                protected void onPostExecute(List<userData> userDatas) {
                    List<userData> uus = userDatas;
                    if (uus == null) {
                        PromptManager.showToast(PieChartActivity.this, "数据获取网路失败");
                        return;
                    }
                    int valueNumberXiYan = 0;
                    ArrayList<Entry> yVals1 = new ArrayList<Entry>();
                    ArrayList<String> xVals = new ArrayList<String>();
                    for (int i = 0; i < uus.size(); i++) {
                        userData next = uus.get(i);
                        int yiNumber = Integer.parseInt(next.getDataNumber());
                        valueNumberXiYan += yiNumber;
                        yVals1.add(new Entry((float) yiNumber + 1, i));
                        xVals.add(next.getDatetime());
                    }
                    xiyanValue.setText(valueNumberXiYan + " （根）");
                    setData(7, 100, yVals1, xVals);

                    super.onPostExecute(userDatas);
                }
            }.executeProxy(usernamexx);


        } else {
            xiyanValue.setText("没有登陆无数据");
        }



    }

    private void addTable() {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        for (int i = 0; i < 7; i++) {
            yVals1.add(new Entry((float) i + 1, i));
        }


        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < 7; i++) {
            //todo 添加  右边的数据 的  以及  图像上面的时间显示
//            xVals.add(mParties2[i % mParties2.length]);
            xVals.add(mParties2[i % mParties2.length]);

        }
        setData(7, 100, yVals1,xVals);
//        tvX.setText("" + (3 + 1));
//        tvY.setText("" + (100));
//        tvX.setText("");
//        tvY.setText("");


//        mSeekBarX.setProgress(7);
//        mSeekBarY.setProgress(10);
//        mSeekBarX.setVisibility(View.INVISIBLE);
//        mSeekBarY.setVisibility(View.INVISIBLE);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                for (IDataSet<?> set : mChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHole: {
                if (mChart.isDrawHoleEnabled())
                    mChart.setDrawHoleEnabled(false);
                else
                    mChart.setDrawHoleEnabled(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionDrawCenter: {
                if (mChart.isDrawCenterTextEnabled())
                    mChart.setDrawCenterText(false);
                else
                    mChart.setDrawCenterText(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleXVals: {

                mChart.setDrawSliceText(!mChart.isDrawSliceTextEnabled());
                mChart.invalidate();
                break;
            }
            case R.id.actionSave: {
                // mChart.saveToGallery("title"+System.currentTimeMillis());
                mChart.saveToPath("title" + System.currentTimeMillis(), "");
                break;
            }
            case R.id.actionTogglePercent:
                mChart.setUsePercentValues(!mChart.isUsePercentValuesEnabled());
                mChart.invalidate();
                break;
            case R.id.animateX: {
                mChart.animateX(1400);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(1400);
                break;
            }
            case R.id.animateXY: {
                mChart.animateXY(1400, 1400);
                break;
            }
        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

//        tvX.setText("" + (mSeekBarX.getProgress() + 1));
//        tvY.setText("" + (mSeekBarY.getProgress()));
//        Log.i(TAG,"xxxxxxxxxxxxxxxxxxx" + mSeekBarX.getProgress());
//        Log.i(TAG,"xxxxxxxxxxxxxxxxxxx" + mSeekBarY.getProgress());
//      setData(mSeekBarX.getProgress(), mSeekBarY.getProgress());
//        setData(7, 10);
    }

    private void setData(int count, float range, ArrayList<Entry> yVals1, ArrayList<String> xVals) {

        float mult = range;


        // PromptManager.showToast(PieChartActivity.this, String.valueOf(count));
        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.

        // for (int i = 0; i < count; i++) {


        // TODO: 16-7-20  添加数据的函数。
//            yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
//            yVals1.add(new Entry((float) 10, i));
        // yVals1.add(new Entry((float) i+1, i));
//            Log.i(TAG, "sssssssssssssssss" + (Math.random() * mult) + mult / 5);
//            Log.i(TAG,"xxxxxxxxxxxxxxxxx     :    "+i);
        //  }

//        ArrayList<String> xVals = new ArrayList<String>();
//        for (int i = 0; i < count; i++) {
//            //todo 添加  右边的数据 的  以及  图像上面的时间显示
////            xVals.add(mParties2[i % mParties2.length]);
//            xVals.add(mParties2[i % mParties2.length]);
//
//        }
        //添加的vertical的  显示。
        PieDataSet dataSet = new PieDataSet(yVals1, "  时间显示");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(4f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        //// TODO: 16-7-20
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(tf);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();

    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString(" SDingBa Man \n developed by Alphabet man");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);

    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }


}
