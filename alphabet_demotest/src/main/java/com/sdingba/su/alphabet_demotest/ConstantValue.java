package com.sdingba.su.alphabet_demotest;

/**
 * Created by su on 16-5-29.
 */
public interface ConstantValue {
    String ENCODING = "utf-8";

    /**
     * socket的服务器；IP地址；
     * 10.0.2.2
     */
    String SOCKET_IP = "10.10.39.11";// TODO: 16-6-28 改
//    String SOCKET_IP = "172.16.178.3";// TODO: 16-6-28 改

    /**
     *
     * 服务器  ip  一
     * 10.0.2.2
     */
    String WEB_URL_CONSTANT = "10.10.39.11:8080";//创新501 // TODO: 16-6-28 改
//    String WEB_URL_CONSTANT = "172.16.178.3:8080";// linshi

    /**
     * 服务器  ip  二
     */
    //   public static String WEB_URL_CONSTANT = "192.168.1.100:8080";//3教6楼

    /**
     * 服务器地址 ：
     */
    String WEB_URL = "http://" + WEB_URL_CONSTANT + "/alphabetService";


    /**
     * 新闻 访问 数据的 url
     * 用于 新闻 的处理事件的url
     */
    String NEWS_LISTVIEW_ITEM = WEB_URL + "/new.xml";

    /**
     * 新闻 访问 数据的 url
     * 用于 新闻 的处理事件的url
     */
    String JIEYAN_LISTVIEW_ITEM = WEB_URL + "/jieyannew.xml";

    /**
     * 新闻 访问 数据的 url
     * 用于 新闻 的处理事件的url
     */
    String QITA_LISTVIEW_ITEM = WEB_URL + "/qitannew.xml";


    /**
     * socket 服务器的端口号；
     */
    int SOCKET_PORT = 19990;
    /**
     * 登陆 界面
     */
    String Login = WEB_URL + "/LoginIn";
    /**
     * 获取 好友
     */
    String FriendList = WEB_URL + "/GetFriendById";

//    String PREF_NAME_STR = "config";

    /**
     * 提交每天吸烟次数的数据
     */
    String SendXiYanNumberDay = WEB_URL + "/SendUserData";

    /**
     * 提交设置的数据
     */
    String sendDataYanPlan = WEB_URL + "/SubmitSetDataYan";


    /**
     * 获取 设置数据，
     */
    String getServierSetDataPlan = WEB_URL + "/ReturnSetDateYan";

    /**
     * 获取 用户 的吸烟 历史数据
     */
    String huoquUserData = WEB_URL + "/UserDataServlet";

    /**
     * 设置 分享的信息
     */
    String SET_MessageShare_Serlvet = WEB_URL + "/setmessageShareSerlvet";

    /**
     * 获取 分享的信息
     */
    String GET_MessageShare_Serlvet = WEB_URL + "/getmessageShareSerlvet";


    /**
     * 获取 分享的信息
     */
    String SET_SendNumberYan_Serlvet = WEB_URL + "/setsendNumberYanServlet";


    /**
     * 获取 分享的信息
     */
    String GET_SendNumberYan_Serlvet = WEB_URL + "/getsendNumberYanServlet";



    /**
     * 获取 分享的信息
     */
    String GET_MAX_ALL_Serlvet = WEB_URL + "/userMaxallXiyanServlet";


}
