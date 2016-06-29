package com.sdingba.su.alphabet_beta1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private LinearLayout mTabWeixin;
    private LinearLayout mTabFrd;
    private LinearLayout mTabAddress;
    private LinearLayout mTabSettings;

    private ImageButton mImgWeixin;
    private ImageButton mImgFrd;
    private ImageButton mImgSettings;
    private ImageButton mImgAddress;

    private Fragment mTab01;
    private Fragment mTab02;
    private Fragment mTab03;
    private Fragment mTab04;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        setSelect(0);
    }

    private void initEvent() {

        mTabWeixin.setOnClickListener(this);
        mTabFrd.setOnClickListener(this);
        mTabAddress.setOnClickListener(this);
        mTabSettings.setOnClickListener(this);

    }

    private void initView() {

        mTabWeixin = (LinearLayout) findViewById(R.id.id_tab_weixin);
        mTabFrd = (LinearLayout) findViewById(R.id.id_tab_frd);
        mTabAddress = (LinearLayout) findViewById(R.id.id_tab_address);
        mTabSettings = (LinearLayout) findViewById(R.id.id_tab_settings);

        mImgWeixin = (ImageButton) findViewById(R.id.id_tab_weixin_img);
        mImgFrd = (ImageButton) findViewById(R.id.id_tab_frd_img);
        mImgAddress = (ImageButton) findViewById(R.id.id_tab_address_img);
        mImgSettings = (ImageButton) findViewById(R.id.id_tab_settings_img);
    }


    @Override
    public void onClick(View v) {

        resetImgs();
        switch (v.getId()) {
            case R.id.id_tab_weixin:

                setSelect(0);


                break;
            case R.id.id_tab_frd:

                setSelect(1);


                break;
            case R.id.id_tab_address:

                setSelect(2);

                break;
            case R.id.id_tab_settings:

                setSelect(3);

                break;
        }
    }

    private void resetImgs() {

        mImgWeixin.setImageResource(R.drawable.tab_weixin_normal);
        mImgFrd.setImageResource(R.drawable.tab_find_frd_normal);
        mImgAddress.setImageResource(R.drawable.tab_address_normal);
        mImgSettings.setImageResource(R.drawable.tab_settings_normal);
    }

    private void setSelect(int i) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        //把图片设置为亮
        //设置内容区域
        switch (i) {
            case 0:

                if (mTab01 == null) {
                    mTab01 = new WeixinFragment();
                    //绑定一个bundle:
                    Bundle bundle = new Bundle();
                    bundle.putString("name", "cccccccc");
                    //设置  bundle:
                    mTab01.setArguments(bundle);
                    transaction.replace(R.id.id_content, mTab01);
                } else {
                    transaction.show(mTab01);
                }
                mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed);
                break;
            case 1:
                if (mTab02 == null) {
                    mTab02 = new FrdFragment();

                    transaction.add(R.id.id_content, mTab02);
                } else {

                    transaction.show(mTab02);
                }
                mImgFrd.setImageResource(R.drawable.tab_find_frd_pressed);
                break;
            case 2:
                if (mTab03 == null) {
                    mTab03 = new AddressFragment();
                    transaction.add(R.id.id_content, mTab03);
                } else {
                    transaction.show(mTab03);
                }
                mImgAddress.setImageResource(R.drawable.tab_address_pressed);
                break;
            case 3:
                if (mTab04 == null) {
                    mTab04 = new SettingFragment();
                    transaction.add(R.id.id_content, mTab04);
                } else {
                    transaction.show(mTab04);
                }
                mImgSettings.setImageResource(R.drawable.tab_settings_pressed);
                break;

            default:
                break;
        }

        transaction.commit();

    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mTab01 != null) {
            transaction.hide(mTab01);
        }
        if (mTab02 != null) {
            transaction.hide(mTab02);
        }
        if (mTab03 != null) {
            transaction.hide(mTab03);
        }
        if (mTab04 != null) {
            transaction.hide(mTab04);
        }

    }


    /**
     * 该方法和上面的方法不同的地方是，这儿使用的  replace   来替换   fragment
     * 这样的话，fragment可以走完全部的生命周期函数；
     *
     * @param i
     */
    private void setSelectReplace(int i) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        //把图片设置为亮
        //设置内容区域
        switch (i) {
            case 0:
                //if (mTab01 == null) {
                mTab01 = new WeixinFragment();
                transaction.replace(R.id.id_content, mTab01);
                //} else {
                //    transaction.show(mTab01);
                // }
                mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed);
                break;
            case 1:
//                if (mTab02 == null) {
                mTab02 = new FrdFragment();
                transaction.replace(R.id.id_content, mTab02);
//                } else {
//                    transaction.show(mTab02);
//
//                }
                mImgFrd.setImageResource(R.drawable.tab_find_frd_pressed);
                break;
            case 2:
//                if (mTab03 == null) {
                mTab03 = new AddressFragment();
                transaction.replace(R.id.id_content, mTab03);
//                } else {
//                    transaction.show(mTab03);
//                }
                mImgAddress.setImageResource(R.drawable.tab_address_pressed);
                break;
            case 3:
                // if (mTab04 == null) {
                mTab04 = new SettingFragment();
                transaction.replace(R.id.id_content, mTab04);
                // } else {
                //     transaction.show(mTab04);
                // }
                mImgSettings.setImageResource(R.drawable.tab_settings_pressed);
                break;

            default:
                break;
        }

        transaction.commit();

    }

}
