package com.sdingba.su.alphabet_demotest.view;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.sdingba.su.alphabet_demotest.ConstantValue;
import com.sdingba.su.alphabet_demotest.MainActivity;
import com.sdingba.su.alphabet_demotest.R;
import com.sdingba.su.alphabet_demotest.SharPredInter;
import com.sdingba.su.alphabet_demotest.bean.Netbean.UserData;
import com.sdingba.su.alphabet_demotest.bean.Netbean.userDataDay;
import com.sdingba.su.alphabet_demotest.bean.friendBean;
import com.sdingba.su.alphabet_demotest.engine.Impl.UserDataEngineImpl;
import com.sdingba.su.alphabet_demotest.engine.Impl.UserEngineImpl;
import com.sdingba.su.alphabet_demotest.engine.Impl.sendNumberEngineImpl;
import com.sdingba.su.alphabet_demotest.engine.UserDataEngine;
import com.sdingba.su.alphabet_demotest.engine.UserEngine;
import com.sdingba.su.alphabet_demotest.engine.sendNumberEngine;
import com.sdingba.su.alphabet_demotest.net.MyHttpAsyncTask;
import com.sdingba.su.alphabet_demotest.utils.PromptManager;
import com.sdingba.su.alphabet_demotest.utils.dataTimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by su on 16-6-23.
 * 初始化  函数  的类
 */
public class splash extends Activity {

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        pref = getSharedPreferences(SharPredInter.SHAR_TABLE_NAME, MODE_PRIVATE);

        String usernamexx = pref.getString(SharPredInter.USER_NAME, "");

        // TODO: 16-6-23 获取今天的时间，和  存储的  比较一下。

        /**
         * 1,date newday = ;
         *
         * 2,比较 newd 和  maxday
         *
         * 3,
         *
         *
         */
        if (!usernamexx.equals("")) {

//            initDataTest();

            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
            String newDay = df.format(new Date());

//        String lastdate = pref.getString(SharPredInter.LAST_SECTION_DAY, "");
//        String preAvtivi = pref.getString(SharPredInter.Pre_ACTIVA_Time, "");
//        if (preAvtivi.equals("") || lastdate.equals("")) {
//            // netInitSchedule(usernamexx);  获取网络说句
//        }

            updateSchello(newDay);

            everyDayActive(newDay, usernamexx, newDay);

            initFriend(usernamexx);

        } else {
            PromptManager.showToast(splash.this, "请先登陆...");
            threadTiaoz();
        }


    }

    /**
     * 以网络的形式 初始化 Schedule_table
     * todo 登陆以后获取，不是每一次都要获取
     *
     * @param usernamexx
     */
    private void netInitSchedule(String usernamexx) {
        if (!usernamexx.equals("")) {
            //todo 获取网络上面的数据
        }
    }


    /**
     * 测试 填充 数据
     */
    private void initDataTest() {
        //初始化一下
        dataTimeUtils dateUtils = new dataTimeUtils(splash.this);
        dateUtils.PullStringToDate("20160610", "3:8,1:8,1:8,3:8,3:8,8:12,1:8,3:8,3:8");
//        dateUtils.PullStringToDate("20160622", "1:8");

//////        // 1
    }

    /**
     * 检测 是 否要 更新 数据项
     * 更 新新 的 近端 时间 的 安排
     *
     * @param newDay
     */
    private void updateSchello(String newDay) {
        dataTimeUtils dateUtils = new dataTimeUtils(splash.this);
        String lastdate = pref.getString(SharPredInter.LAST_SECTION_DAY, "");

        if (!lastdate.equals("")) {
            //今日时间    上次激活时间
            while (Integer.parseInt(newDay) > Integer.parseInt(lastdate)) {
//                if (Integer.parseInt(newDay) > Integer.parseInt(endTime)) {
//                    PromptManager.showToast(splash.this, "计划结束了。。。");
//                    break;
//                }

                String returndd = dateUtils.getLastDataSc();

                if (returndd.equals("ok")) {
                    lastdate = pref.getString(SharPredInter.LAST_SECTION_DAY, "");
                } else if (returndd.equals("error")) {
                    PromptManager.showToast(splash.this, "error 上次设置数据到期，请重新设置");
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(SharPredInter.Schedule_table, "");
                    editor.commit();

                    break;
                }
            }
        } else {
//            //// TODO: 16-6-24 这次计划用完了，或者 真的为空（没有初始化）。
//            String endTime = pref.getString(SharPredInter.EndTimeSchedule, "");
//            if (endTime.equals("")) {
//                // TODO: 16-6-24
//                PromptManager.showToast(splash.this, "没有数据，请先登陆获取数据...");
//            }
        }
    }

    /**
     * 每天激活
     *
     * @param newDay
     * @param day
     */
    private void everyDayActive(final String newDay, String username, String day) {

        String preAvtivi = pref.getString(SharPredInter.Pre_ACTIVA_Time, "");

        String endTime = pref.getString(SharPredInter.EndTimeSchedule, "");

        if (endTime.equals("")) {
            /// TODO: 16-6-24 这次计划用完了，或者 真的为空（没有初始化）。
//            if(username.equals(""))
            PromptManager.showToast(splash.this, "未获取到上次吸烟数据...");
            return;
        } else {
            if (Integer.parseInt(endTime) >= Integer.parseInt(newDay)) {  //  计划结束时间   和   今天的时间

                if (!preAvtivi.equals("")) {
                    if (Integer.parseInt(newDay) > Integer.parseInt(preAvtivi)) {//  上一次激活的时间   和   今天的时间




//

                        //今天是否可以吸烟了；

                        // TODO: 16-6-24 把“昨天”（上一次） 的 数据告诉服务器
                        // 上传 数据给服务器 。昨天的数据 。

                        String DayxiYanNumber = pref.getString(SharPredInter.NEW_day_xiYan, "");
                        String dayAll = pref.getString(SharPredInter.SECTION_Yan_Num, "");
                        String lastYanNumber = String.valueOf(Integer.parseInt(dayAll) - Integer.parseInt(DayxiYanNumber));

                        userDataDay userdate = new userDataDay();
                        userdate.setId(username);
                        userdate.setDatetime(day);
                        userdate.setLastYanNumber(lastYanNumber);
                        userdate.setYunNum(DayxiYanNumber);


                        new MyHttpAsyncTask<userDataDay, String>(splash.this) {

                            @Override
                            protected void onPreExecute() {


                                super.onPreExecute();
                            }

                            @Override
                            protected String doInBackground(userDataDay... params) {
                                userDataDay par = params[0];
                                UserDataEngine userData = new UserDataEngineImpl();
                                String res = userData.SendUserDayXiYanDay(par);

                                return res;
                            }

                            @Override
                            protected void onPostExecute(String s) {
                                String relut = s;
                                if ("error".equals(s)) {
                                    PromptManager.showToast(splash.this, "error 提交数据错误");
                                } else if ("ok".equals(s)) {
                                    PromptManager.showToast(splash.this, "ok 提交数据成功");

                                    //修改今日吸烟的数量。

                                    SharedPreferences.Editor editorT = pref.edit();
                                    editorT.putString(SharPredInter.Pre_ACTIVA_Time, newDay);
                                    editorT.putBoolean(SharPredInter.isBooleOk, true);


                                    ///////////////////////////////////////////////

                                    String sengYan = pref.getString(SharPredInter.SEND_YAN_OTHER, "");
                                    String keyiYanNumber = pref.getString(SharPredInter.SECTION_Yan_Num, "");
                                    editorT.putString(SharPredInter.ZUIHOU_Yan_Num, keyiYanNumber);
                                    int number = 0;
                                    if (!sengYan.equals("")) {//
                                        number = Integer.valueOf(sengYan);
                                        if (number > 0) {

                                            int mun = Integer.parseInt(keyiYanNumber) - number;

                                            if (mun > 0) {
                                                String yanNumber = String.valueOf(mun);
                                                editorT.putString(SharPredInter.ZUIHOU_Yan_Num, yanNumber);
                                            } else {
                                                editorT.putString(SharPredInter.ZUIHOU_Yan_Num, "0");

                                            }

                                        }
                                    }
                                    editorT.commit();

                                    ///////////////////////////////////////////////



                                }
                                super.onPostExecute(s);
                            }
                        }.executeProxy(userdate);

//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////

//                        PromptManager.showToast(splash.this, "昨天 数据 告诉服务器 了");
                        new MyHttpAsyncTask<String, String>(splash.this) {
                            @Override
                            protected String doInBackground(String... params) {
                                String userId = params[0];
                                sendNumberEngine engine = new sendNumberEngineImpl();

                                return engine.getSendYanNumber(userId);
                            }

                            @Override
                            protected void onPostExecute(String s) {

                                String next = s;
                                if (next == null || next.equals("")) {

                                }else{
                                    String keyiYanNumber2 = pref.getString(SharPredInter.ZUIHOU_Yan_Num, "");
                                    int num = Integer.valueOf(next);
                                    SharedPreferences.Editor editor = pref.edit();

                                        int mun = Integer.parseInt(keyiYanNumber2) + num;
                                        String yanNum = String.valueOf(mun);

                                        editor.putString(SharPredInter.ZUIHOU_Yan_Num, yanNum);


                                    editor.commit();
                                }

                                super.onPostExecute(s);
                            }
                        }.executeProxy(username);
                    } else {
                        //PromptManager.showToast(splash.this, "系统出现问题，上次记录时间大于今天时间。");
                    }






                }
            } else {
                SharedPreferences.Editor editorT = pref.edit();
                editorT.putBoolean(SharPredInter.isBooleOk, false);
                editorT.commit();
                PromptManager.showToast(splash.this, "上次设置数据到期，请重新设置");
            }


        }
    }

    /**
     * 初始化 好友列表
     * initFriend();
     */
    private void initFriend(String usernamexx) {

        if (!usernamexx.equals("")) {

            new MyHttpAsyncTask<String, List<friendBean>>(this) {
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
                    if (friendBean != null) {
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

                    } else {
                        PromptManager.showToast(splash.this, "网络崩溃,狗逼程序员加班中...");
                    }

                    tiaoZ();


                    super.onPostExecute(friendBean);
                }
            }.executeProxy(usernamexx);
        }
    }

    private void threadTiaoz() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tiaoZ();
            }
        }).start();
    }

    private void tiaoZ() {
        Intent intent = new Intent();
        intent.setClass(splash.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
