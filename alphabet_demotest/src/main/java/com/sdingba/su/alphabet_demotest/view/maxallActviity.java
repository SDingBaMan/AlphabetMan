package com.sdingba.su.alphabet_demotest.view;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sdingba.su.alphabet_demotest.R;
import com.sdingba.su.alphabet_demotest.SharPredInter;
import com.sdingba.su.alphabet_demotest.engine.Impl.UserDataEngineImpl;
import com.sdingba.su.alphabet_demotest.engine.UserDataEngine;
import com.sdingba.su.alphabet_demotest.net.MyHttpAsyncTask;

/**
 * Created by su on 16-7-24.
 */
public class maxallActviity extends Activity {

    private TextView allmax;
    private SharedPreferences preferences;
    private TextView maxall_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maxall);
        allmax = (TextView) findViewById(R.id.allmax);
        maxall_back = (TextView) findViewById(R.id.maxall_back);

        maxall_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        preferences = getSharedPreferences(SharPredInter.SHAR_TABLE_NAME, MODE_PRIVATE);
        String userName = preferences.getString(SharPredInter.USER_NAME, "");
        new MyHttpAsyncTask<String, String[]>(maxallActviity.this) {
            @Override
            protected String[] doInBackground(String... params) {
                String user = params[0];
                UserDataEngine engine = new UserDataEngineImpl();
                return engine.getMaxAll(user);
            }

            @Override
            protected void onPostExecute(String[] s) {
                String number[] = s;
                if (number == null) {

                }else{
                    allmax.setText("根据你的吸烟情况，" +
                            "\n你的吸烟总数为吸" +
                            "：" + number[0] + "根，\n" +
                            "用户平均的吸烟数据是：" + number[1] +
                            ",\n你的吸烟平均数据是" + number[2]+
                            ",\n\n" +
                            "建议：需要继续努力...");

                }

                super.onPostExecute(s);
            }
        }.executeProxy(userName);

    }


}
