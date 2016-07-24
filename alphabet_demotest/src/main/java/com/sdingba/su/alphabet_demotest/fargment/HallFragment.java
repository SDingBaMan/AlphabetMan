package com.sdingba.su.alphabet_demotest.fargment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdingba.su.alphabet_demotest.R;
import com.sdingba.su.alphabet_demotest.SharPredInter;
import com.sdingba.su.alphabet_demotest.tables.viewTables.PieChartActivity;
import com.sdingba.su.alphabet_demotest.utils.LogUtils;
import com.sdingba.su.alphabet_demotest.utils.PromptManager;
import com.sdingba.su.alphabet_demotest.view.SetDataPlan;
import com.sdingba.su.alphabet_demotest.view.maxallActviity;

import java.io.InputStream;
import java.util.ArrayList;

public class HallFragment extends Fragment {
    private static final String TAG = "HallFragment";
    private ViewPager viewPager;

    public HallFragmentMyListener listener;

    private LinearLayout pointGroup;

    private TextView iamgeDesc;

    private Button buttonPanel;

    private TextView revicesMainString;

    private EditText sendDataToLanyaEditText;


    /**
     * 剩余吸烟数
     */
    private TextView lastNumber;

    /**
     * 今日吸烟次数
     */
    private TextView newDayXiNumber;

    /**
     * AllYunNumber吸烟总数
     */
    private TextView AllYunNumber;

    private SharedPreferences pref;

    /**
     * 制定计划 的 点击事件
     */
    private LinearLayout PlanButton;

    /**
     * 显示图标的
     */
    private LinearLayout Showtable;

    /**
     * 第四个按钮
     */
    private LinearLayout tubiaoTable;

    /**
     * 第3个按钮
     */
    private LinearLayout fenxiBao;

    /**
     * 第2个按钮
     */
    private LinearLayout setLanya;


    /**
     * 第1个按钮
     */
    private LinearLayout dataOnline;


    private ReseiveDataToActivity reseiveDatatoActivityformView;

    // 图片资源ID
    private final int[] imageIds = {
            R.drawable.images, R.drawable.imagessw,
            R.drawable.imagessall,

            R.drawable.imageshall, R.drawable.imageshal};
//	// 图片资源ID
//	private final int[] imageIds = {
//			R.drawable.a, R.drawable.b,
//			R.drawable.c,
//
//			R.drawable.d, R.drawable.e };

    //图片标题集合
    private final String[] imageDescriptions = {
            "不要在吸烟了，停止吧！！",
            "拒绝吸烟，弯转你的烟吧",
            "少抽一根烟，爱护自然多一点",
            "黑色T恤，拒绝吸烟",
            "对吸烟说NO,不许抽烟啦。。"
    };


    private ArrayList<ImageView> imageList;

    /**
     * 上一个页面的位置
     */
    protected int lastPosition;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (HallFragmentMyListener) activity;
        Log.i("Main", "onAttach: -------------------------------");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab01, container, false);
        pref = getActivity().getSharedPreferences(SharPredInter.SHAR_TABLE_NAME, Context.MODE_PRIVATE);
        Log.i("Main", "xxxxxxxxxxxxxx  start()");
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        pointGroup = (LinearLayout) view.findViewById(R.id.point_group);
        iamgeDesc = (TextView) view.findViewById(R.id.image_desc);


        //--------------------6个主要的点击事件-------------------------------
        //--------------------6个主要的点击事件-------------------------------
        //--------------------6个主要的点击事件-------------------------------
        //--------------------6个主要的点击事件-------------------------------

        PlanButton = (LinearLayout) view.findViewById(R.id.zhiding_plan);
        Showtable = (LinearLayout) view.findViewById(R.id.show_table);
        tubiaoTable = (LinearLayout) view.findViewById(R.id.tubiaoTable);
        fenxiBao = (LinearLayout) view.findViewById(R.id.fenxiBao);
        setLanya = (LinearLayout) view.findViewById(R.id.setLanya);
        dataOnline = (LinearLayout) view.findViewById(R.id.dataOnline);

        fenxiBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent t = new Intent();
                t.setClass(getActivity(), maxallActviity.class);
                startActivity(t);

            }
        });
        setLanya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = pref.getString(SharPredInter.ZUIHOU_Yan_Num, "");
                if (number.equals("")) {
                    PromptManager.showToast(getActivity(), "无数据...");
                } else {

                    boolean islisv = pref.getBoolean(SharPredInter.isBooleOk, false);

                    if (islisv) {
                        if (Integer.valueOf(number) > 10) {
                            String str = "on";
                            str += number;
                            listener.sendActivity(str);
                            revicesMainString.setText(str);
                        } else {
                            String str = "in";
                            str += number;
                            listener.sendActivity("in");
                            revicesMainString.setText(str);
                        }

                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean(SharPredInter.isBooleOk, false);
                        editor.commit();
                    }else{
                        PromptManager.showToast(getActivity(), "今日不可激活了...");
                    }



                }
            }
        });
        dataOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.sendActivity("get");
                revicesMainString.setText("get");
            }
        });

        tubiaoTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "4", Toast.LENGTH_SHORT).show();

            }
        });

        Showtable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(getActivity(), PieChartActivity.class);
                startActivity(intent);
//				PromptManager.showToastTest(getActivity(), "PieChartActivity");
            }
        });

        PlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), SetDataPlan.class);
                startActivity(intent);
            }
        });

        //-------------------------------------------------
        //-------------------------------------------------
        //-------------------------------------------------
        //-------------------------------------------------
//
        revicesMainString = (TextView) view.findViewById(R.id.revicesMainText);
        sendDataToLanyaEditText = (EditText) view.findViewById(R.id.sendDataToLanya);

        lastNumber = (TextView) view.findViewById(R.id.lastNumber);
        newDayXiNumber = (TextView) view.findViewById(R.id.newDayXiNumber);
        AllYunNumber = (TextView) view.findViewById(R.id.AllYunNumber);

        iamgeDesc.setText(imageDescriptions[0]);

        buttonPanel = (Button) view.findViewById(R.id.buttonPanel);
        buttonPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String aa = sendDataToLanyaEditText.getText().toString();
                listener.sendActivity(aa);
                revicesMainString.setText(aa);

            }
        });

        updateYunNumber();


        registerBroadcast();


        ADViewPager();
        //最重要的 步骤之一
        viewPager.setAdapter(new MyPagerAdapter());

        //最重要的 步骤之一
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            /**
             * 页面切换后调用
             * position  新的页面位置
             */
            public void onPageSelected(int position) {

                position = position % imageList.size();

                //设置文字描述内容
                iamgeDesc.setText(imageDescriptions[position]);

                pointGroup.getChildAt(position).setEnabled(true);
                //把上一个点设为false
                pointGroup.getChildAt(lastPosition).setEnabled(false);
                lastPosition = position;

            }

            @Override
            /**
             * 页面正在滑动的时候，回调
             */
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            /**
             * 当页面状态发生变化的时候，回调
             */
            public void onPageScrollStateChanged(int state) {

            }
        });

		 /*
          * 自动循环：
		  * 1、定时器：Timer
		  * 2、开子线程 while  true 循环
		  * 3、ColckManager
		  * 4、 用handler 发送延时信息，实现循环
		  */
        isRunning = true;
        handler.sendEmptyMessageDelayed(0, 3000);

        return view;
    }

    /**
     * 更新吸烟 显示的 数
     */
    private void updateYunNumber() {
        String newNubmer = pref.getString(SharPredInter.NEW_day_xiYan, "");
        String lastNubmer = pref.getString(SharPredInter.SECTION_Yan_Num, "");
        String zuihouNubmer = pref.getString(SharPredInter.ZUIHOU_Yan_Num, "");
        //String allNubmer = pref.getString(SharPredInter.All_Yan_NUMBER, "");
        if (newNubmer.equals("") || lastNubmer.equals("") || zuihouNubmer.equals("")) {
//			PromptManager.showToast(getActivity(),"还没有设置数据");
        } else {
            int aaa = Integer.parseInt(zuihouNubmer) - Integer.parseInt(newNubmer);
            lastNumber.setText(aaa + "");
            newDayXiNumber.setText(newNubmer);
            AllYunNumber.setText(zuihouNubmer);
        }
    }

    /**
     * 注册 广播
     * 目的： 使得 activity能够 传递数据 给 HallFragment
     */
    private void registerBroadcast() {
        //注册广播
        reseiveDatatoActivityformView = new ReseiveDataToActivity();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.sdingba.activityToHallFragment");
        getActivity().registerReceiver(reseiveDatatoActivityformView, filter);
    }

    /**
     * 所有  图片 显示的 加载。ad 栏目
     */
    private void ADViewPager() {
        imageList = new ArrayList<ImageView>();
        for (int i = 0; i < imageIds.length; i++) {

            //初始化图片资源
            setImageList(imageIds[i]);

            //添加指示点//添加图片下面的点数位置
            ImageView point = new ImageView(getActivity());
            //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(5dp,5dp);或下面的一句话
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = 20;
            point.setLayoutParams(params);

            point.setBackgroundResource(R.drawable.point_bg);
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }
            pointGroup.addView(point);
        }
    }

    /**
     * 判断是否自动滚动
     */
    private boolean isRunning = false;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            if (isRunning) {
                handler.sendEmptyMessageDelayed(0, 3000);
            }
        }
    };


    /**
     * 防止  OOM  的出现
     * 加载 图片；
     *
     * @param imageId
     */
    private void setImageList(int imageId) {
        ImageView image = new ImageView(getActivity());

        BitmapFactory.Options opt = new BitmapFactory.Options();

        opt.inPreferredConfig = Bitmap.Config.RGB_565;

        opt.inPurgeable = true;

        opt.inInputShareable = true;

        InputStream is = getResources().openRawResource(
                imageId);

        Bitmap bm = BitmapFactory.decodeStream(is, null, opt);

        BitmapDrawable bd = new BitmapDrawable(getResources(), bm);

        image.setBackground(bd);

        imageList.add(image);
    }


    /******************************************************************/
    private class MyPagerAdapter extends PagerAdapter {
        @Override
        /**
         * 获得页面的总数
         */
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        /**
         * 获得相应位置上的view
         * container  view的容器，其实就是viewpager自身
         * position 	相应的位置
         */
        public Object instantiateItem(ViewGroup container, int position) {
            System.out.println("instantiateItem  ::" + position);

            container.addView(imageList.get(position % imageList.size()));

            return imageList.get(position % imageList.size());
        }

        @Override
        /**
         * 判断 view和object的对应关系
         */
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        /**
         * 销毁对应位置上的object
         */
        public void destroyItem(ViewGroup container, int position, Object object) {
            System.out.println("destroyItem  ::" + position);
            container.removeView((View) object);
            object = null;
        }
    }


    @Override
    public void onStart() {

        super.onStart();
//		System.out.println("isRunning:::"+isRunning);
        if (isRunning != true) {
            Log.i("Main", "onStart 值是" + isRunning);
            isRunning = true;
            handler.sendEmptyMessage(0);
        }
        Log.i("Main", "111111111111111    onStart()");

    }

    @Override
    public void onResume() {

        super.onResume();
//		System.out.println("isRunning:::"+isRunning);
        if (isRunning != true) {
            Log.i("Main", "onResume 值是" + isRunning);
            isRunning = true;
            handler.sendEmptyMessage(0);
        }
        Log.i("Main", "11111111111111111    onResume()");

    }

    @Override
    public void onPause() {

        super.onPause();
        if (isRunning != false) {
            isRunning = false;
        }

        Log.i("Main", "11111111111111111    onPause()");

    }

    @Override
    public void onStop() {
        super.onStop();
        if (isRunning != false) {
            isRunning = false;

        }
        Log.i("Main", "11111111111111111    onStop()");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("Main", "11111111111111111---onDestroyView()");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        listener = null;
        if (reseiveDatatoActivityformView != null) {
            getActivity().unregisterReceiver(reseiveDatatoActivityformView);
        }
        Log.i("Main", "11111111111111111---onDestroy()");
    }

    ;

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("Main", "1111111111111111111---onDetach()");
    }

    public interface HallFragmentMyListener {
        /**
         * 向 MainActivity 发送 数据
         *
         * @param data
         */
        public void sendActivity(String data);


    }

    private class ReseiveDataToActivity extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

//			String action = intent.getAction();
//
//			final Intent mIntent = intent;
//			//********** 连接成功 的 时候 调用。***********//
//			if (action.equals("tag")) {


            Bundle data = intent.getExtras();


//			buttonPanel.setText(data.getString("aaa"));
//			LogUtils.Logi(TAG,":::"+data.getString("aaa"));

            String number = data.getString("aaa");
            revicesMainString.setText("" + number);

            String newNumber = pref.getString(SharPredInter.NEW_day_xiYan, "");
            if (newNumber.equals("")) {
                PromptManager.showToast(getActivity(),"同步蓝牙错误...");
            }else{
                if (Integer.valueOf(newNumber) > Integer.valueOf(number)) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(SharPredInter.NEW_day_xiYan, number);
                    editor.commit();
                }else{
                    PromptManager.showToast(getActivity(),"同步蓝牙错误(同步数据小于历史数据)...");
                }

            }




            LogUtils.Logi(TAG, ":::" + data.getString("aaa"));


        }
    }
}
