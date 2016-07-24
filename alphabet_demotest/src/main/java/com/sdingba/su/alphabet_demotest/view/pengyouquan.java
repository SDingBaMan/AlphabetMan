package com.sdingba.su.alphabet_demotest.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sdingba.su.alphabet_demotest.R;
import com.sdingba.su.alphabet_demotest.bean.messageShareNet;
import com.sdingba.su.alphabet_demotest.engine.Impl.messageShareEngineImpl;
import com.sdingba.su.alphabet_demotest.engine.messageShareEngine;
import com.sdingba.su.alphabet_demotest.net.MyHttpAsyncTask;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by su on 16-7-22.
 */
public class pengyouquan extends Activity {

    private ListView pengyouListView;

    private pengyouAdapter pengAdapter;

    private ImageView sendShareButton;

    private TextView News_chart_goMain;



    private int[] item_logo = {
            R.drawable.skin1,
            R.drawable.skin2,
            R.drawable.skin3,
            R.drawable.skin4,

            R.drawable.skin5,


    };


    private List<messageShareNet> AdapterList = null;



//    private String[] name_item1 = {
//            "十点半", "Alphabet", "Man",
//            "那天", "链接", "帅帅",
//            "我是谁", "Alphabet", "字母表",
//            "JayZhou", "周杰伦", "Xiong"};
//
//    private String[] context_item1 = {
//            "这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG", "团队名就是字母表", "字母表的男人",
//            "这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG", "我是链接器", "这是我的本来的名字",
//            "这是我的OMG", "这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG这是我的OMG", "字母表的男人",
//            "不能说的秘密", "我是链接器", "这是我的本来的名字"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pengyouquanadapter);

        initEvent();


    }

    /**
     * 网络填充数据
     */
    private void setData() {
        new MyHttpAsyncTask<String, List<messageShareNet>>(this) {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected List<messageShareNet> doInBackground(String... params) {
                messageShareEngine messageS = new messageShareEngineImpl();
                return messageS.getListMessageShare();
            }

            @Override
            protected void onPostExecute(List<messageShareNet> messageShareNets) {
                List<messageShareNet> lists = messageShareNets;
                if (lists == null) {

                    AdapterList = new ArrayList<messageShareNet>();

                    messageShareNet mess = new messageShareNet();
                    mess.setUserId("暂无数据");
                    mess.setTitle("暂无数据");
                    mess.setContent("暂无数据");

                    AdapterList.add(mess);
                    //System.out.println(AdapterList.toString() + "++++++++_+_+_+_+_+_+");

                } else {
                    AdapterList = lists;
                    //System.out.println(AdapterList.toString() + "++++++++_+_+_+_+_+_+");

                }

                pengAdapter = new pengyouAdapter();
                pengyouListView.setAdapter(pengAdapter);

                super.onPostExecute(messageShareNets);
            }
        }.executeProxy("");
    }

    private void initEvent() {

        News_chart_goMain = (TextView) findViewById(R.id.News_chart_goMain);
        News_chart_goMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        sendShareButton = (ImageView) findViewById(R.id.sendShareButton);
        sendShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //编写分享的数据
                Intent intent = new Intent();
                intent.setClass(pengyouquan.this, sendMessageShare.class);
                startActivity(intent);
            }
        });
        pengyouListView = (ListView) findViewById(R.id.pengyouquan_list);


    }


    private class pengyouAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return AdapterList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHodler hodler = null;
            if (convertView == null) {
                hodler = new ViewHodler();
                convertView = View.inflate(getApplicationContext(), R.layout.pengyouquan, null);
                hodler.title = (TextView) convertView.findViewById(R.id.tv_listview_item_title);
                hodler.Context = (TextView) convertView.findViewById(R.id.tv_listview_item_detail);

                hodler.shareTime = (TextView) convertView.findViewById(R.id.shareTime);
                hodler.PicsNameId = (TextView) convertView.findViewById(R.id.PicsNameId);
                hodler.head_image = (ImageView) convertView.findViewById(R.id.head_image);
                hodler.userName = (TextView) convertView.findViewById(R.id.tv_listview_item_username);

                convertView.setTag(hodler);

            } else {
                hodler = (ViewHodler) convertView.getTag();

            }

//            hodler.title.setText(name_item1[position]);
//
//            hodler.Context.setText(context_item1[position]);
//
//            hodler.shareTime.setText("20160809");

            messageShareNet mess = new messageShareNet();
            mess = AdapterList.get(position);

            hodler.userName.setText(mess.getUserId());

            hodler.title.setText(mess.getTitle());
//
            hodler.Context.setText(mess.getContent());

            hodler.shareTime.setText(mess.getDatetime());

//            if (AdapterList.get(position).getUserId() =="") {
            hodler.PicsNameId.setText(mess.getUserId().substring(0, 1).toUpperCase());
//            }else{
//                hodler.PicsNameId.setText("Me");
//            }
            hodler.head_image.setImageResource(item_logo[position % 5]);

            return convertView;
        }


    }

    class ViewHodler {
        private ImageView head_image;

        private TextView title;
        private TextView Context;
        private TextView shareTime;
        private TextView PicsNameId;
        private TextView userName;

    }


    @Override
    protected void onResume() {
//        if (pengAdapter != null) {
//
//            pengAdapter.notifyDataSetChanged();
//
//        }
        setData();


        super.onResume();
    }
}
