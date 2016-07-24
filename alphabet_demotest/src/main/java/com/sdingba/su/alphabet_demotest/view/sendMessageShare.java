package com.sdingba.su.alphabet_demotest.view;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.sdingba.su.alphabet_demotest.ConstantValue;
import com.sdingba.su.alphabet_demotest.R;
import com.sdingba.su.alphabet_demotest.SharPredInter;
import com.sdingba.su.alphabet_demotest.bean.SetDataYan;
import com.sdingba.su.alphabet_demotest.bean.messageSend;
import com.sdingba.su.alphabet_demotest.bean.userData;
import com.sdingba.su.alphabet_demotest.engine.Impl.UserDataEngineImpl;
import com.sdingba.su.alphabet_demotest.engine.Impl.messageShareEngineImpl;
import com.sdingba.su.alphabet_demotest.engine.UserDataEngine;
import com.sdingba.su.alphabet_demotest.engine.messageShareEngine;
import com.sdingba.su.alphabet_demotest.net.MyHttpAsyncTask;
import com.sdingba.su.alphabet_demotest.utils.PromptManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by su on 16-7-23.
 */
public class sendMessageShare extends Activity {
    private EditText title;
    private EditText content;
    private SharedPreferences pref;
    private Button sendButton;
    private Button data_zuijing;
    private Button data_setting;
    private Button clear_button;
    private TextView News_chart_goMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sendmessage);
        initEvent();
    }

    private void initEvent() {

        pref = getSharedPreferences(SharPredInter.SHAR_TABLE_NAME, MODE_PRIVATE);

        News_chart_goMain = (TextView) findViewById(R.id.News_chart_goMain);
        data_zuijing = (Button) findViewById(R.id.data_zuijing);
        data_setting = (Button) findViewById(R.id.data_setting);
        clear_button = (Button) findViewById(R.id.clear_button);

        News_chart_goMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                content.setText("");
            }
        });

        data_zuijing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernamexx = pref.getString(SharPredInter.USER_NAME, "");
                if (usernamexx.equals("")) {
                    PromptManager.showToast(sendMessageShare.this,"请先登陆...");
                    return;
                }
                new MyHttpAsyncTask<String, List<userData>>(sendMessageShare.this) {

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
                            PromptManager.showToast(sendMessageShare.this, "数据获取网路失败");
                            return;
                        }

                        StringBuilder builder = new StringBuilder();
                        builder.append("\n   ###% == ");
                        for (int i = 0; i < uus.size(); i++) {
                            userData next = uus.get(i);
                            builder.append(next.getDatetime());
                            builder.append("天,吸烟");
                            builder.append(next.getDataNumber());
                            builder.append("根,当天剩余");
                            builder.append(next.getSurplusNumber());
                            builder.append("根 ");
                        }
                        builder.append("==  %###  \n");
                        String str = content.getText().toString()+builder.toString();
                        content.setText(str);

                        super.onPostExecute(userDatas);
                    }
                }.executeProxy(usernamexx);

            }
        });

        data_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String datalist = pref.getString(SharPredInter.Schedule_table, "");
                if (datalist.equals("")) {
                    PromptManager.showToast(sendMessageShare.this,"还没有数据,请先设置...");
                    return;
                }
                String[] partyB = datalist.split("\\,");
                StringBuilder builder = new StringBuilder();
                builder.append("###% ==");
                for (String par : partyB) {
                    SetDataYan setdateOne = new SetDataYan();
                    String[] liss = par.split("\\:");

                    builder.append(liss[0]);
                    builder.append("之前可吸 ");
                    builder.append(liss[1]);
                    builder.append(" 根y烟, ");

                }
                builder.append("== %###");
//                builder.toString();

                String con = content.getText().toString() + builder.toString();
                content.setText(con);
            }
        });


        title = (EditText) findViewById(R.id.titlesendmessage);
        content = (EditText) findViewById(R.id.contextsendmessage);
        sendButton = (Button) findViewById(R.id.simbut_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameId = pref.getString(SharPredInter.USER_NAME, "");
                if (nameId != null && !nameId.equals("")) {

                    messageSend send = new messageSend();
                    send.setTitle(title.getText().toString());
                    send.setContext(content.getText().toString());
                    send.setUserId(nameId);

                    new MyHttpAsyncTask<messageSend, String>(sendMessageShare.this) {
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                        }

                        @Override
                        protected String doInBackground(messageSend... params) {
                            messageSend ss = params[0];
                            messageShareEngine engine = new messageShareEngineImpl();
                            return engine.setMessageSahreToWEB(ss);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            String ss = s;


                            if (s.equals("OkSetMessageShare")) {
                                title.setText("");
                                content.setText("");
                                finish();

                            } else if (s == null || s.equals("ErrorSetMessageShare")) {

                                PromptManager.showToast(sendMessageShare.this, "发送失败，请检查网络...");
                            }
                            super.onPostExecute(s);
                        }
                    }.executeProxy(send);


                }else{
                    PromptManager.showToast(sendMessageShare.this, "请先登陆");

                }

            }
        });




    }


}
