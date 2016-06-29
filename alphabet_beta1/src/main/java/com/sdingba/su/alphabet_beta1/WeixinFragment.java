package com.sdingba.su.alphabet_beta1;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class WeixinFragment extends Fragment {


    private ViewPager viewPager;

    private LinearLayout pointGroup;

    private TextView iamgeDesc;

    private TextView tesst;

    // 图片资源ID
    private final int[] imageIds = {R.drawable.a, R.drawable.b, R.drawable.c,
            R.drawable.d, R.drawable.e};

    //图片标题集合
    private final String[] imageDescriptions = {
            "巩俐不低俗，我就不能低俗",
            "扑树又回来啦！再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大派送",
            "热血屌丝的反杀"
    };
    private ArrayList<ImageView> imageList;

    /**
     * 上一个页面的位置
     */
    protected int lastPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab01, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        pointGroup = (LinearLayout) view.findViewById(R.id.point_group);
        iamgeDesc = (TextView) view.findViewById(R.id.image_desc);
        iamgeDesc.setText(imageDescriptions[0]);
        tesst = (TextView) view.findViewById(R.id.aaaa);

        String text=getArguments().get("name")+"";

        tesst.setText(text);

        imageList = new ArrayList<ImageView>();
        ADViewPager();
        //最重要的 步骤之一
        viewPager.setAdapter(new MyPagerAdapter());

        //最重要的 步骤之一
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            /**
             * position 新的页面位置
             */
            public void onPageSelected(int position) {

                position = position % imageList.size();


                iamgeDesc.setText(imageDescriptions[position]);


                pointGroup.getChildAt(position).setEnabled(true);

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
        handler.sendEmptyMessageDelayed(0, 2000);
        return view;
    }

    /**
     * 加载所有的  ad   图像栏目的操作
     */
    private void ADViewPager() {
        for (int i = 0; i < imageIds.length; i++) {
            ////////////////////////////////////////////
            setImageList(imageIds[i]);
            /////////////////////////////////////////


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
     * 对  list（ 广告 图片）  进行图片的初始化，
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

        image.setBackgroundDrawable(bd);

        imageList.add(image);
    }

    /**
     * 判断是否自动滚动
     */
    private static boolean isRunning = false;




    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            //让viewPager 滑动到下一页
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            if (isRunning) {
                handler.sendEmptyMessageDelayed(0, 2000);
            }

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;

    }

    ;


    /**
     * 图片加载适配器
     *
     *
     * */
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

            // 给 container 添加一个view
            container.addView(imageList.get(position % imageList.size()));
            //返回一个和该view相对的object
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
        if (isRunning != true) {
            isRunning = true;
            handler.sendEmptyMessageDelayed(0, 2000);
        }
        Log.i("Main", "Fragment2---onStart()");

    }

    @Override
    public void onResume() {

        super.onResume();
        if (isRunning != true) {
            isRunning = true;
            handler.sendEmptyMessageDelayed(0, 2000);
        }
        Log.i("Main", "Fragment2---onResume()");

    }

    @Override
    public void onPause() {

        super.onPause();
        if (isRunning != false) {
            isRunning = false;
        }
        Log.i("Main", "Fragment2---onPause()");

    }

    @Override
    public void onStop() {

        super.onStop();
        if (isRunning != false) {
            isRunning = false;
        }
        Log.i("Main", "Fragment2---onStop()");

    }




}
