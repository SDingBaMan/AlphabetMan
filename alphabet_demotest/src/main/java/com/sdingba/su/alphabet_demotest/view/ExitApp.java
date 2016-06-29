package com.sdingba.su.alphabet_demotest.view;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


import com.sdingba.su.alphabet_demotest.ConstantValue;
import com.sdingba.su.alphabet_demotest.MainActivity;
import com.sdingba.su.alphabet_demotest.R;
import com.sdingba.su.alphabet_demotest.SharPredInter;

/**
 * Created by su on 16-5-23.
 */
public class ExitApp extends Activity {
    private Button btn_exit;
    private Button btn_cancel;
    private SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout_actionsheet);
        btn_exit = (Button) findViewById(R.id.btn_exit);
        btn_cancel = (Button) findViewById(R.id.cancel);

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref = getSharedPreferences(SharPredInter.SHAR_TABLE_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                editor.putString(SharPredInter.USER_NAME, "");
                editor.putString(SharPredInter.FRIEND_LIST, "");
                editor.putString(SharPredInter.NEW_day_xiYan, "");
                editor.putString(SharPredInter.All_Yan_NUMBER, "");
                editor.putString(SharPredInter.Pre_ACTIVA_Time, "");
                editor.putString(SharPredInter.SECTION_Yan_Num, "");
                editor.putString(SharPredInter.LAST_SECTION_DAY, "");
                editor.putString(SharPredInter.Schedule_table, "");
                editor.putString(SharPredInter.Last_Schedule_table, "");
                editor.putBoolean(SharPredInter.isBooleOk, false);
                editor.putString(SharPredInter.beginTime, "");
                editor.putString(SharPredInter.timeDaySum, "");
                editor.putString(SharPredInter.OrigendDateNumber, "");
                editor.putString(SharPredInter.EndTimeSchedule, "");




                editor.commit();

                finish();
                MainActivity.getInstance().finish();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * 处理出现 悬浮窗口后，点击其他地方也能消除 悬浮窗口。
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }
}
