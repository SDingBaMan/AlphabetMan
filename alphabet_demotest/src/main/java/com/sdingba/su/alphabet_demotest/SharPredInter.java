package com.sdingba.su.alphabet_demotest;

/**
 * Created by su on 16-6-23.
 * 数据存储的函数
 */
public interface SharPredInter {

    /**
     * 文件名称
     */
    String SHAR_TABLE_NAME = "config";

    /**
     * 用户存储的用户名
     */
    String USER_NAME = "username";

    /**
     * 用户存储的 好友 列表
     */
    String FRIEND_LIST = "friend";

    /**
     * 今日吸烟次数 已经吸烟次数
     */
    String NEW_day_xiYan = "newDayYanNumber";

    /**
     * 这次 每天可以吸烟 的根数
     */
    String SECTION_Yan_Num = "sectionYan";

    /**
     * 这次 每天可以吸烟 的根数
     */
    String ZUIHOU_Yan_Num = "zuihouXiYan";

    /**
     * 总共吸烟次数
     */
    String All_Yan_NUMBER = "allYanNumber";

    /**
     * 最后一次激活时间
     * 存储形式 YYYYMMDD
     * 用于每天激活
     */
    String Pre_ACTIVA_Time = "lastTime";



    /**
     * 这段  戒烟 计划的最后一天  是几号
     */
    String LAST_SECTION_DAY = "lastSectionDay";

    /**
     * 最近一次的安排 表
     * 字符串 的 集合
     * * todo 格式  结束时间 : 结束时间之前可以吸烟次数
     * 20160308:18,20160314:15,20160324:10,20160503:7
     */
    String Schedule_table = "ScheduleTable";

    /**
     * 最近一次的  剩余次数 的 计划安排 表
     */
    String Last_Schedule_table = "lastSchedule";

    // TODO: 16-6-23 最近数据的存储发送给数据库，直接在这个SharPrefInt里面分段获取。

    /**
     * 今日 是否 吸烟 结束
     * boolean 类型
     * false代表不可以了
     * true 可以 吸烟，可以同步数据
     */
    String isBooleOk = "isBooleOk";

    /**
     * 开始的时间
     */
    String beginTime = "begintime";

    /**
     * 使用的时间
     * 这次计划一共使用的时间
     */
    String timeDaySum = "timedaysum";

    /**
     * 最原始的安排表 数据 类型
     */
    String OrigendDateNumber = "dateOrigen";

    /**
     * 计划的结束时间
     */
    String EndTimeSchedule = "endtimeSchedule";


    /**
     * 存储 今日 赠送 的 的 数量
     */
    String SEND_YAN_OTHER = "sendyanNumber";

    /**
     * 存储 别人送我的烟
     */
    String FOR_ME_YANNUMBER = "formeyanNumber";



}
