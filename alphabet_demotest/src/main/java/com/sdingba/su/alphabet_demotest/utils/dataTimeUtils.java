package com.sdingba.su.alphabet_demotest.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.sdingba.su.alphabet_demotest.SharPredInter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class dataTimeUtils {

    private Context context;

    public dataTimeUtils(Context context) {
        this.context = context;
    }

    private SharedPreferences pref;


    /**
     * 减去 最近一次的
     * 生成 剩余的安排表 并且修改数据
     */
    public String getLastDataSc() {

        pref = context.getSharedPreferences(SharPredInter.SHAR_TABLE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();





        String lastSc = pref.getString(SharPredInter.Last_Schedule_table, "");
        if (lastSc.equals("")) {
            return "error";
        }
        String[] list = lastSc.split("\\,");
        StringBuffer lastDateSc = new StringBuffer();
        int leng = list.length;

        if (leng > 1) {
            for (int i = 1; i < leng; i++) {
                lastDateSc.append(list[i] + ",");
            }
            lastDateSc.delete(lastDateSc.length() - 1, lastDateSc.length());
            // TODO: 16-6-23 修改  值
            String[] dataL = list[0].split("\\:");
            editor.putString(SharPredInter.LAST_SECTION_DAY, dataL[0]);
            // TODO: 16-7-23
            editor.putString(SharPredInter.SECTION_Yan_Num, dataL[1]);
            editor.putString(SharPredInter.Last_Schedule_table, lastDateSc.toString());
//            editor.putString(SharPredInter.NEW_day_xiYan, "0");
        } else {
            String[] dataLLO = list[0].split("\\:");
            editor.putString(SharPredInter.LAST_SECTION_DAY, dataLLO[0]);
            editor.putString(SharPredInter.SECTION_Yan_Num, dataLLO[1]);
            editor.putString(SharPredInter.Last_Schedule_table, "");
//            editor.putString(SharPredInter.NEW_day_xiYan, "0");
        }


        editor.commit();
//        System.out.println(sss[0]+"xxxx"+sss[1]);

        System.out.println(lastDateSc.toString());
        return "ok";
    }

    /**
     * 只调用一次，每次生成 数据 的时候调用
     *
     * @param datastr 设置日期
     * @param scheel  处理安排表
     * @return all 总共时间安排表  生成的安排表
     * <p/>
     * 初始化，历史表，剩余表，上次时间时间，课吸烟次数
     */
    public void PullStringToDate(String datastr, String scheel) {
        try {

            pref = context.getSharedPreferences(SharPredInter.SHAR_TABLE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();


            Date date = new SimpleDateFormat("yyyyMMdd").parse(datastr);

            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
            String ActividayTime = df.format(date);

            editor.putString(SharPredInter.Pre_ACTIVA_Time, ActividayTime);
            editor.putString(SharPredInter.beginTime, ActividayTime);


            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            /**
             * 生成的安排表
             */
            StringBuffer anPaibiao = new StringBuffer();

            int allday = 0;

            String[] list = scheel.split("\\,");
            for (String foo : list) {

                String[] anPai = foo.split("\\:");

                int dayNum = Integer.parseInt(anPai[0]);

                allday += dayNum;
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + dayNum);//让日期加1

                String dayTime = df.format(calendar.getTime());
                anPaibiao.append(dayTime + ":" + anPai[1] + ",");
            }

            String NumYan = list[0].split("\\:")[1];
            String DataTime = list[0].split("\\:")[0];

            int leng = anPaibiao.length();
            anPaibiao.delete(leng - 1, leng);

            System.out.println(anPaibiao.toString());

            System.out.println("一共吸烟天使 ： " + allday);

            //计算结束时间
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + allday);
            String dayTime = df.format(calendar.getTime());

            editor.putString(SharPredInter.EndTimeSchedule, dayTime);

            editor.putString(SharPredInter.timeDaySum, String.valueOf(allday));
            editor.putString(SharPredInter.LAST_SECTION_DAY, DataTime);
            editor.putString(SharPredInter.Schedule_table, anPaibiao.toString());
            editor.putString(SharPredInter.Last_Schedule_table, anPaibiao.toString());
            editor.putString(SharPredInter.SECTION_Yan_Num, NumYan);
            editor.putString(SharPredInter.ZUIHOU_Yan_Num, NumYan);
            editor.putString(SharPredInter.NEW_day_xiYan, "0");
            editor.putString(SharPredInter.All_Yan_NUMBER, "33");
            editor.putString(SharPredInter.OrigendDateNumber, scheel);
            editor.putBoolean(SharPredInter.isBooleOk, true);

            editor.commit();
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("cccc");
        }
    }


    /**
     * 返回 今天的 时间
     *
     * @return string
     */
    public static String getNewDayTime() {


        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String newDay = df.format(new Date());
        return newDay;
    }
}
