package com.sdingba.su.alphabet_demotest.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;
import com.sdingba.su.alphabet_demotest.GlobalParams;
import com.sdingba.su.alphabet_demotest.R;
import com.sdingba.su.alphabet_demotest.bean.NewInfo;
import com.sdingba.su.alphabet_demotest.net.MyHttpAsyncTask;
import com.sdingba.su.alphabet_demotest.net.NetUtil;
import com.sdingba.su.alphabet_demotest.net.NewsNet;
import com.sdingba.su.alphabet_demotest.utils.LogUtils;
import com.sdingba.su.alphabet_demotest.utils.PromptManager;

import java.util.ArrayList;
import java.util.List;

public class newsActivity extends AppCompatActivity {


    private static final int SUCCESS = 1;
    private static final int FAILED = 0;
    //无网络
    private static final int NONET = 2;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    //给ListView列表绑定数据

                    //为下面的额public int getCount(){
                    //  return newInfoList.size();
                    // }
                    // 中的size()提取数据。
                    list_news_item = (List<NewInfo>) msg.obj;
                    news_xiyan.setAdapter(new NewsListAdapter());
                    break;
                case FAILED:
                    Toast.makeText(newsActivity.this, "当前网络崩溃了.", Toast.LENGTH_SHORT).show();
                    break;
                case NONET:
                    PromptManager.showToast(newsActivity.this, "服务器出现问题或者网络出现问题，");
                    newsActivity.this.finish();
                    break;
                default:
                    break;
            }

        }
    };


    /**
     * 戒烟 新闻 的 listView
     */
    private ListView news_xiyan;

    /**
     * 吸烟危害 的 listView
     */
    private ListView xiYan_weihai;

    /**
     * 吸烟危害 的 listView
     */
    private ListView qitaData;

    //    private NewsListAdapter adapterNews;
    private List<NewInfo> list_news_item;


    private static final String TAG = "newsActivity";
    private ViewPager viewPager;
    /**
     *  填  充   View 的 数组
     */
    private List<View> pagers;

    /**
     *  存  网络 返回的数据
     */
    private List<NewInfo> newInfoList;
    private ImageView underLine;

    // 记录ViewPger上一个界面的position信息
    private int lastPosition = 0;

    private TextView fcTitle;
    private TextView tcTitle;
    private TextView gpcTitle;

    private TextView News_chart_goMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        viewPager = (ViewPager) findViewById(R.id.tabpager);

        initView();
        initPager();
        viewPager.setAdapter(new NewsPagerAdapter());

        initTabStrip();
    }

    /**
     * 初始化其他的布局
     */
    private void initView() {

        News_chart_goMain = (TextView) findViewById(R.id.News_chart_goMain);
        News_chart_goMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        news_xiyan = new ListView(this);
        xiYan_weihai = new ListView(this);
        qitaData = new ListView(this);



//        adapterNews = new NewsListAdapter();

        list_news_item = new ArrayList<NewInfo>();


//        //抓取新闻数据
//        new Thread() {
//            Message msg = Message.obtain();
//            @Override
//            public void run() {
//                if (NetUtil.checkNetWork(newsActivity.this)){
//                    Log.i(TAG, " 能联网  ");
//
//                    newInfoList = newsNet.getNewsFromInternet();
//
//                    if (newInfoList != null) {
//                        msg.what = SUCCESS;
//                        msg.obj = newInfoList;
//                    } else {
//                        msg.what = FAILED;
//                    }
//                    handler.sendMessage(msg);
//
//                }else{
//                    msg.what = NONET;
//                    handler.sendMessage(msg);
//                }
//            }
//        }.start();

        newsList();


        //对于  listView item的点击事件。
        news_xiyan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PromptManager.showToastTest(newsActivity.this, "点击了" + position);
            }
        });

//        news_xiyan.setAdapter(adapterNews);  //
//        news_xiyan.setFadingEdgeLength(0);// 删除黑边（上下）
    }

    /**
     * 对  新闻 的 listView填充数据
     */
    private void newsList() {
        final NewsNet newsNet = new NewsNet();
        new MyHttpAsyncTask<String, List<NewInfo>>(newsActivity.this) {


            @Override
            protected List<NewInfo> doInBackground(String... params) {
                newInfoList = newsNet.getNewsFromInternet();
                return newInfoList;
            }

            @Override
            protected void onPostExecute(List<NewInfo> newInfos) {
                if (newInfos != null) {
                    list_news_item = newInfos;
//                  LogUtils.Logi("AnyscTask","xxxxx  ok  ok  ok");
                    news_xiyan.setAdapter(new NewsListAdapter());

                } else {
                    PromptManager.showToast(newsActivity.this, "网络出现异常");
                    finish();
                }
                super.onPostExecute(newInfos);
            }
        }.executeProxy("");
    }


    private void initTabStrip() {

        underLine = (ImageView) findViewById(R.id.ii_category_selector);

        fcTitle = (TextView) findViewById(R.id.ii_category_fc);
        tcTitle = (TextView) findViewById(R.id.ii_category_tc);
        gpcTitle = (TextView) findViewById(R.id.ii_category_gpc);

        fcTitle.setTextColor(Color.RED);
        // 屏幕宽度

        // 小图片的宽度
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.id_category_selector);

        int offset = (GlobalParams.WIN_WIDTH / 3 - bitmap.getWidth()) / 2;

        // 设置图片初始位置——向右偏移
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        underLine.setImageMatrix(matrix);

    }

    /**
     * 初始化ViewPager
     */
    private void initPager() {

        pagers = new ArrayList<View>();
        pagers.add(news_xiyan);


        TextView item = new TextView(this);
        item.setText("xxxx");
        pagers.add(xiYan_weihai);

        item = new TextView(this);
        item.setText("dddd");
        pagers.add(qitaData);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // position:0

                // fromXDelta toXDelta:相对于图片初始位置需要增加的量

                TranslateAnimation animation = new TranslateAnimation(lastPosition * GlobalParams.WIN_WIDTH / 3, position * GlobalParams.WIN_WIDTH / 3, 0, 0);
                animation.setDuration(300);
                animation.setFillAfter(true);

                underLine.startAnimation(animation);
                lastPosition = position;

                fcTitle.setTextColor(Color.BLACK);
                tcTitle.setTextColor(Color.BLACK);
                gpcTitle.setTextColor(Color.BLACK);

                switch (position) {
                    case 0:
                        fcTitle.setTextColor(Color.RED);
                        if (news_xiyan.getAdapter() == null) {
                            newsList();
                        }

                        break;
                    case 1:
                        tcTitle.setTextColor(Color.RED);

                        if (xiYan_weihai.getAdapter() == null) {


                            jieyanList();
                        }
                        break;
                    case 2:
                        gpcTitle.setTextColor(Color.RED);
                        if (qitaData.getAdapter() == null) {


                            qitaLists();
                        }
                        break;
                }
            }

            /**
             * 向其他的  listView  填充数据
             */
            private void qitaLists() {
                final NewsNet newsNet = new NewsNet();
                new MyHttpAsyncTask<String, List<NewInfo>>(newsActivity.this) {


                    @Override
                    protected List<NewInfo> doInBackground(String... params) {
                        newInfoList = newsNet.getQitaFromInternet();
                        return newInfoList;
                    }

                    @Override
                    protected void onPostExecute(List<NewInfo> newInfos) {
                        if (newInfos != null) {
                            list_news_item = newInfos;
//                  LogUtils.Logi("AnyscTask","xxxxx  ok  ok  ok");
                            qitaData.setAdapter(new NewsListAdapter());


                        } else {
                            PromptManager.showToast(newsActivity.this, "网络出现异常");
                            finish();
                        }
                        super.onPostExecute(newInfos);
                    }
                }.executeProxy("");
            }

            /**
             * 向 戒烟 的 的 listview 填充数据
             */
            private void jieyanList() {
                final NewsNet newsNet = new NewsNet();
                new MyHttpAsyncTask<String, List<NewInfo>>(newsActivity.this) {


                    @Override
                    protected List<NewInfo> doInBackground(String... params) {
                        newInfoList = newsNet.getJieYanFromInternet();
                        return newInfoList;
                    }

                    @Override
                    protected void onPostExecute(List<NewInfo> newInfos) {
                        if (newInfos != null) {
                            list_news_item = newInfos;
//                  LogUtils.Logi("AnyscTask","xxxxx  ok  ok  ok");
                            xiYan_weihai.setAdapter(new NewsListAdapter());


                        } else {
                            PromptManager.showToast(newsActivity.this, "网络出现异常");
                            finish();
                        }
                        super.onPostExecute(newInfos);
                    }
                }.executeProxy("");
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // 属性动画
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });


    }


    /**
     * Viewpager用adapter
     *
     * @author Administrator
     */
    private class NewsPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return pagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pagers.get(position));
            return pagers.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(pagers.get(position));
        }
    }


    private class NewsListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list_news_item.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {

                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.listview_new_item, null);
//                view = View.inflate(getApplicationContext(),
//                        R.layout.list_item_callsms, null);
            } else {
                view = convertView;
            }

            //重新赋值，不会产生缓存对象，中原来数据保留的现象
            SmartImageView sivIcon =
                    (SmartImageView) view.findViewById(R.id.siv_listview_item_icon);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_listview_item_title);
            TextView tvDetail = (TextView) view.findViewById(R.id.tv_listview_item_detail);
            TextView tvComment = (TextView) view.findViewById(R.id.tv_listview_item_comment);

            NewInfo newinfo = list_news_item.get(position);
            sivIcon.setImageUrl(newinfo.getImageUrl());
            tvTitle.setText(newinfo.getTitle());
            tvDetail.setText(newinfo.getDetail());
            tvComment.setText(newinfo.getComment() + "跟帖");
            return view;

        }
    }
}
