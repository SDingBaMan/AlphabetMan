package com.sdingba.su.alphabet_demotest.view;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sdingba.su.alphabet_demotest.ConstantValue;
import com.sdingba.su.alphabet_demotest.R;
import com.sdingba.su.alphabet_demotest.SharPredInter;
import com.sdingba.su.alphabet_demotest.bean.Netbean.UserLogin;
import com.sdingba.su.alphabet_demotest.bean.Netbean.UserMan;
import com.sdingba.su.alphabet_demotest.bean.SetDataYan;
import com.sdingba.su.alphabet_demotest.bean.friendBean;
import com.sdingba.su.alphabet_demotest.bean.getsetdataBean;
import com.sdingba.su.alphabet_demotest.engine.Impl.SetDataEngineImpl;
import com.sdingba.su.alphabet_demotest.engine.Impl.UserEngineImpl;
import com.sdingba.su.alphabet_demotest.engine.SetDataEngine;
import com.sdingba.su.alphabet_demotest.engine.UserEngine;
import com.sdingba.su.alphabet_demotest.engine.aaaa;
import com.sdingba.su.alphabet_demotest.net.MyHttpAsyncTask;
import com.sdingba.su.alphabet_demotest.utils.BeanFactory;
import com.sdingba.su.alphabet_demotest.utils.PromptManager;
import com.sdingba.su.alphabet_demotest.utils.dataTimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by su on 16-6-21.
 */
public class Login extends Activity {
    private Button Login;
    private TextView zhanghao;
    private TextView password;
    SharedPreferences pref;
    private boolean isLive;
    private String zh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Login = (Button) findViewById(R.id.btn_login);
        zhanghao = (TextView) findViewById(R.id.login_zh);
        password = (TextView) findViewById(R.id.login_password);
        isLive=false;

        linsern();

    }

    private void linsern() {

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zh = zhanghao.getText().toString().trim();
                String pw = password.getText().toString();
                System.out.println(zh + " ++   ++ " + pw);

                UserLogin userlogin = new UserLogin();
                userlogin.setUlId(zh);
                userlogin.setUlpassword(pw);

                new MyHttpAsyncTask<UserLogin, UserMan>(Login.this) {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected UserMan doInBackground(UserLogin... params) {
                        UserLogin user = params[0];
                        System.out.println(user.getUlId()+"llllll");
//                        UserEngine engine = BeanFactory
//                                .getImpl(UserEngine.class);


                        UserEngine engine = new UserEngineImpl();

                        UserMan userMan = engine.login(user);

                        return userMan;
                    }

                    @Override
                    protected void onPostExecute(UserMan userMan) {
                        if (userMan == null) {

//
                            PromptManager.showToast(Login.this,"账号或密码错误");

                            isLive = false;

                        }  else {

                            //登陆成功
                            pref = getSharedPreferences(SharPredInter.SHAR_TABLE_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();

                            //----------------------------------------------------------------
                            editor.putString(SharPredInter.USER_NAME, "");
                            editor.putString(SharPredInter.FRIEND_LIST, "");
                            editor.putString(SharPredInter.NEW_day_xiYan, "");
                            editor.putString(SharPredInter.All_Yan_NUMBER, "33");

                            editor.putString(SharPredInter.Pre_ACTIVA_Time, "");

                            editor.putString(SharPredInter.SECTION_Yan_Num, "");
                            editor.putString(SharPredInter.ZUIHOU_Yan_Num, "");
                            editor.putString(SharPredInter.LAST_SECTION_DAY, "");
                            editor.putString(SharPredInter.Schedule_table, "");
                            editor.putString(SharPredInter.Last_Schedule_table, "");
                            editor.putBoolean(SharPredInter.isBooleOk, false);
                            editor.putString(SharPredInter.beginTime, "");
                            editor.putString(SharPredInter.timeDaySum, "");
                            editor.putString(SharPredInter.OrigendDateNumber, "");
                            editor.putString(SharPredInter.EndTimeSchedule, "");

                            editor.putString(SharPredInter.SEND_YAN_OTHER, "");
                            //----------------------------------------------------------------

                            editor.putString(SharPredInter.USER_NAME, userMan.getUmId());
                            editor.commit();
                            isLive = true;

                            initSetData(zh);

                        }
                        Login.this.finish();
                        super.onPostExecute(userMan);
                    }
                }.executeProxy(userlogin);


            }
        });

    }

    private void initSetData(String zh) {

        new MyHttpAsyncTask<String, getsetdataBean>(Login.this) {
            @Override
            protected getsetdataBean doInBackground(String... params) {

                String usrname = params[0];

                SetDataEngine engine = new SetDataEngineImpl();
                getsetdataBean setdate = engine.initSetDateinService(usrname);


                return setdate;
            }

            @Override
            protected void onPostExecute(getsetdataBean setDataYan) {
                if (setDataYan == null) {
                    PromptManager.showToast(Login.this, "获取 初始化 数据失败");
                } else {
//                    System.out.println("uuuuuu " + setDataYan.getDataJiHua() + "eeee" + setDataYan.getStartTime());

                    //给出提示信息，是否确定添加
                    dataTimeUtils dateUtils = new dataTimeUtils(Login.this);
                    dateUtils.PullStringToDate(setDataYan.getStartTime(), setDataYan.getDataJiHua());

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(SharPredInter.Pre_ACTIVA_Time, dataTimeUtils.getNewDayTime());
                    editor.commit();
                }

                super.onPostExecute(setDataYan);
            }
        }.executeProxy(zh);
    }

    @Override
    protected void onDestroy() {
        if (isLive) {
            /**
             * 获取 friend 列表
             */
            new MyHttpAsyncTask<String, List<friendBean>>(Login.this) {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected List<friendBean> doInBackground(String... params) {
                    List<friendBean> friendList = null;
                    String username = params[0];
                    UserEngine userengine = new UserEngineImpl();
                    friendList = userengine.returnFrienfList(username);

                    return friendList;
                }

                @Override
                protected void onPostExecute(List<friendBean> friendBean) {
                    if(friendBean!=null){
                        List<friendBean> friendBeanlist = friendBean;
                        StringBuffer str = new StringBuffer();
                        for (friendBean bean : friendBeanlist) {
                            str.append(bean.getUmId());
                            str.append(":");
                            str.append(bean.getUsername());
                            str.append(",");
                        }
                        str.substring(0, str.length() - 1);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(SharPredInter.FRIEND_LIST, str.toString());
                        editor.commit();

                    }else{
                        PromptManager.showToast(Login.this,"网络崩溃,狗逼程序员加班中...");
                    }
                    super.onPostExecute(friendBean);
                }
            }.executeProxy(zh);
        }
        super.onDestroy();
    }
}
