package com.sdingba.su.alphabet_demotest.net;

import android.util.Log;
import android.util.Xml;

import com.sdingba.su.alphabet_demotest.ConstantValue;
import com.sdingba.su.alphabet_demotest.bean.NewInfo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by su on 16-5-28.
 */
public class NewsNet {
    private static final String TAG = "NewsNet";
    private List<NewInfo> newInfoList;


    /**
     * 返回  list《NewInfo》 数组
     * @return
     */
    public List<NewInfo> getNewsFromInternet() {
        HttpClient client = null;

        try {
            client = new DefaultHttpClient();
            HttpGet get = new HttpGet(
                    ConstantValue.NEWS_LISTVIEW_ITEM);
            HttpResponse response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {

                InputStream is = response.getEntity().getContent();
                List<NewInfo> newInfoList = getNewListFromInputStream(is);

                Log.i(TAG, "chengg" + statusCode);
                return newInfoList;
            } else {
                Log.i(TAG, "访问失败" + statusCode);
            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.getConnectionManager().shutdown();
            }

        }
        return null;
    }


    /**
     * 返回 戒烟的 数据
     * @return
     */
    public List<NewInfo> getJieYanFromInternet() {
        HttpClient client = null;

        try {
            client = new DefaultHttpClient();
            HttpGet get = new HttpGet(
                    ConstantValue.JIEYAN_LISTVIEW_ITEM);
            HttpResponse response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {

                InputStream is = response.getEntity().getContent();
                List<NewInfo> newInfoList = getNewListFromInputStream(is);

                Log.i(TAG, "chengg" + statusCode);
                return newInfoList;
            } else {
                Log.i(TAG, "访问失败" + statusCode);
            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.getConnectionManager().shutdown();
            }

        }
        return null;
    }

    /**
     * 返回 戒烟的 数据
     * @return
     */
    public List<NewInfo> getQitaFromInternet() {
        HttpClient client = null;

        try {
            client = new DefaultHttpClient();
            HttpGet get = new HttpGet(
                    ConstantValue.QITA_LISTVIEW_ITEM);
            HttpResponse response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {

                InputStream is = response.getEntity().getContent();
                List<NewInfo> newInfoList = getNewListFromInputStream(is);

                Log.i(TAG, "chengg" + statusCode);
                return newInfoList;
            } else {
                Log.i(TAG, "访问失败" + statusCode);
            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.getConnectionManager().shutdown();
            }

        }
        return null;
    }


    public List<NewInfo> getNewListFromInputStream(InputStream is) throws XmlPullParserException, IOException {
        // 1 ，获取XmlPullParser
       /*
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
       */
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "utf-8");

        int eventType = parser.getEventType();

        List<NewInfo> newInfos = null;
        NewInfo newInfo = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = parser.getName();//节点名称
            switch (eventType) {
                case XmlPullParser.START_TAG://<news>:
                    if ("news".equals(tagName)) {
                        newInfoList = new ArrayList<NewInfo>();
                    } else if ("new".equals(tagName)) {
                        newInfo = new NewInfo();
                    } else if ("title".equals(tagName)) {
                        newInfo.setTitle(parser.nextText());
                    } else if ("detail".equals(tagName)) {
                        newInfo.setDetail(parser.nextText());
                    } else if ("comment".equals(tagName)) {
                        newInfo.setComment(Integer.valueOf(parser.nextText()));
                    } else if ("image".equals(tagName)) {
                        newInfo.setImageUrl(parser.nextText());
                    }
                    break;


                case XmlPullParser.END_TAG:
                    if ("new".equals(tagName)) {
                        newInfoList.add(newInfo);
                    }
                    break;
                default:
                    break;
            }
            eventType = parser.next();//取下一个事件类型
        }


        return newInfoList;
    }
}
