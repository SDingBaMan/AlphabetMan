package com.sdingba.su.alphabet_demotest;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.gesture.Prediction;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sdingba.su.alphabet_demotest.bean.Netbean.FriendUser;
import com.sdingba.su.alphabet_demotest.bean.friendBean;
import com.sdingba.su.alphabet_demotest.engine.Impl.UserEngineImpl;
import com.sdingba.su.alphabet_demotest.engine.UserEngine;
import com.sdingba.su.alphabet_demotest.fargment.AddressFragment;
import com.sdingba.su.alphabet_demotest.fargment.FrdFragment;
import com.sdingba.su.alphabet_demotest.fargment.SettingFragment;
import com.sdingba.su.alphabet_demotest.fargment.HallFragment;
import com.sdingba.su.alphabet_demotest.net.MyHttpAsyncTask;
import com.sdingba.su.alphabet_demotest.utils.PromptManager;
import com.sdingba.su.alphabet_demotest.view.ExitApp;
import com.sdingba.su.alphabet_demotest.view.lanya.DeviceListActivity;
import com.sdingba.su.alphabet_demotest.view.lanya.UartService;


import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends FragmentActivity implements HallFragment.HallFragmentMyListener, View.OnClickListener {

//    lanya===========================================================================
    private static final int REQUEST_SELECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int UART_PROFILE_READY = 10;

    private static final int UART_PROFILE_CONNECTED = 20;
    private static final int UART_PROFILE_DISCONNECTED = 21;
    private static final int STATE_OFF = 10;


    private int mState = UART_PROFILE_DISCONNECTED;

    private UartService mService = null;

    private BluetoothDevice mDevice = null;
    private BluetoothAdapter mBtAdapter = null;





//  lanya===========================================================================


//    private static MainActivity instance = null;
    private static final String TAG = "MainActivity";


    private static MainActivity singleton;



    /**
     * 返回   主  界面的实例；
     *
     * @return
     */
    public static MainActivity getInstance() {
        return (singleton != null) ? singleton : null;
    }

    private ViewPager mViewPager;
    private FragmentStatePagerAdapter mAdapter;
    private List<Fragment> mFragment;

    private LinearLayout mTabWeixin;
    private LinearLayout mTabFrd;
    private LinearLayout mTabAddress;
    private LinearLayout mTabSettings;

    private ImageButton mImgWeixin;
    private ImageButton mImgAddress;
    private ImageButton mImgFrd;
    private ImageButton mImgSettings;

    private TextView idMainTitle;

    private Button LinkLanyaCC;

    private TextView OFFSTRINGState;

    //fragment里面的button
//    private Button Hall_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBtAdapter == null) {
//            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            Toast.makeText(this, " Bluetooth is not available------Bluetooth 手机不支持", Toast.LENGTH_LONG).show();
            // TODO: 16-6-21 +++++++++++ 模拟器调试 线注释
//            finish();
            return;
        }




        //// TODO: 16-6-22 xxxxx
        service_init();

        singleton = this;
        initView();
        initEvent();
        setSelect(0);
        initQiTa();
    }



    /**
     * 绑定 服务
     */
    private void service_init() {
        //对服务进行绑定；
        Intent bindIntent = new Intent(this, UartService.class);
        bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

        LocalBroadcastManager.getInstance(this).
                registerReceiver(UARTStatusChangeReceiver, makeGattUpdateIntentFilter());
    }

    /**
     * 给广播 添加 IntentFilter 事件。
     * @return
     */
    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UartService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(UartService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(UartService.DEVICE_DOES_NOT_SUPPORT_UART);
        return intentFilter;
    }

    //UART service connected/disconnected
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        //4. 当服务被连接的时候调用 服务别成功 绑定的时候调用
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
            //服务的绑定，
            mService = ((UartService.LocalBinder) rawBinder).getService();
            Log.d(TAG, "onServiceConnected mService= " + mService);
            if (!mService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }

        }
        //当服务失去连接的时候调用（一般进程挂了，服务被异常杀死）
        public void onServiceDisconnected(ComponentName classname) {
            ////     mService.disconnect(mDevice);
            mService = null;
        }
    };

//    private Handler mHandler = new Handler() {
//        @Override
//        //Handler events that received from UART service
//        public void handleMessage(Message msg) {
//        }
//    };

    private final BroadcastReceiver UARTStatusChangeReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            final Intent mIntent = intent;
            //********** 连接成功 的 时候 调用。***********//
            if (action.equals(UartService.ACTION_GATT_CONNECTED)) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                        Log.d(TAG, "UART_CONNECT_MSG");
                        LinkLanyaCC.setText("OFF");
//                        edtMessage.setEnabled(true);
//                        btnSend.setEnabled(true);
                        OFFSTRINGState.setText(mDevice.getName().toString());
                        Log.i(TAG, "[" + currentDateTimeString + "] Connected to: " + mDevice.getName());
//                        listAdapter.add("["+currentDateTimeString+"] Connected to: "+ mDevice.getName());
//                        messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
                        mState = UART_PROFILE_CONNECTED;
                    }
                });
            }

            //*********** 断开 链接 的时候 调用  **********//
            if (action.equals(UartService.ACTION_GATT_DISCONNECTED)) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                        Log.d(TAG, "UART_DISCONNECT_MSG");
                        LinkLanyaCC.setText("ON");
//                        edtMessage.setEnabled(false);
//                        btnSend.setEnabled(false);
                        OFFSTRINGState.setText("Not Connected");

                        Log.i(TAG,"["+currentDateTimeString+"] Disconnected to: "+ mDevice.getName());
//                        listAdapter.add("["+currentDateTimeString+"] Disconnected to: "+ mDevice.getName());
                        mState = UART_PROFILE_DISCONNECTED;
                        mService.close();
                        //setUiState();

                    }
                });
            }

            //*********************//
            if (action.equals(UartService.ACTION_GATT_SERVICES_DISCOVERED)) {
                mService.enableTXNotification();
                Log.d(TAG,"SDingBaLanYan : ====ACTION_GATT_SERVICdddddddES_DISCOVERED");
            }
            //*********** 服务 连通的时候的数据，。的数据，。**********//
            //*********** 服务 连通的时候的数据，。的数据，。**********//
            //*********** 服务 连通的时候的数据，。的数据，。**********//
            //*********** 服务 连通的时候的数据，。的数据，。**********//
            //*********** 服务 连通的时候的数据，。的数据，。**********//
            if (action.equals(UartService.ACTION_DATA_AVAILABLE)) {

                final byte[] txValue = intent.getByteArrayExtra(UartService.EXTRA_DATA);
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {

                            String text = new String(txValue, "UTF-8");

                            String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());


                            Log.i(TAG,"["+currentDateTimeString+"] RX: "+text);
//                            listAdapter.add("["+currentDateTimeString+"] RX: "+text);


                            Intent intent = new Intent("com.sdingba.activityToHallFragment");
                            intent.putExtra("aaa", "["+currentDateTimeString+"] RX: "+text);
                            sendBroadcast(intent);




//                            messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);

                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                });
            }

            //******** 设备 不支持  *************//
            if (action.equals(UartService.DEVICE_DOES_NOT_SUPPORT_UART)){
                // TODO: 16-6-5 经常出现的那句话，就是这句话，
//                showMessage("???Device doesn't support UART. Disconnecting设备 不支持???");
                //// TODO: 16-6-5 少用 disconnect（）特别是上面的操作；
                mService.disconnect();
            }
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////



    /**
     * 其他全局的初始化
     */
    private void initQiTa() {
        //初始化  屏幕宽度
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        GlobalParams.WIN_WIDTH = metrics.widthPixels;


    }


    private void initEvent() {
        mTabWeixin.setOnClickListener(this);
        mTabFrd.setOnClickListener(this);
        mTabAddress.setOnClickListener(this);
        mTabSettings.setOnClickListener(this);
        LinkLanyaCC.setOnClickListener(this);
    }

    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.id_viewPager);

        mTabWeixin = (LinearLayout) findViewById(R.id.id_tab_weixin);
        mTabFrd = (LinearLayout) findViewById(R.id.id_tab_frd);
        mTabAddress = (LinearLayout) findViewById(R.id.id_tab_address);
        mTabSettings = (LinearLayout) findViewById(R.id.id_tab_settings);

        idMainTitle = (TextView) findViewById(R.id.id_main_title);

        mImgWeixin = (ImageButton) findViewById(R.id.id_tab_weixin_img);
        mImgFrd = (ImageButton) findViewById(R.id.id_tab_frd_img);
        mImgAddress = (ImageButton) findViewById(R.id.id_tab_address_img);
        mImgSettings = (ImageButton) findViewById(R.id.id_tab_settings_img);

        LinkLanyaCC = (Button) findViewById(R.id.Link_lanya_cc);

        mFragment = new ArrayList<Fragment>();

        OFFSTRINGState = (TextView) findViewById(R.id.id_main_flag_lanya);

        //
//        Hall_button = (Button) findViewById(R.id.buttonPanel);

        Fragment mTab01 = new HallFragment();
        Fragment mTab02 = new FrdFragment();
        Fragment mTab03 = new AddressFragment();
        Fragment mTab04 = new SettingFragment();
        mFragment.add(mTab01);
        mFragment.add(mTab02);
        mFragment.add(mTab03);
        mFragment.add(mTab04);


        //FragmentStatePagerAdapter
        //FragmentPagerAdapter
        mAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                System.out.println("cccccccccccccccc   " + position);
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }

        };

        mViewPager.setAdapter(mAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int currentItem = mViewPager.getCurrentItem();
                setTab(currentItem);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
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
            //  连接 蓝牙的 按钮
            case R.id.Link_lanya_cc:
//                LinkLanyaCC.setText("ON");
//              Hall_button.setText("ac");

                if (!mBtAdapter.isEnabled()) {
//                    此时 ： mBtAdapter.isEnabled()  == null 判断蓝牙是否打开
                    //如果没有连接。
//                    showMessage("连接按钮的mBtAdapter.isEnabled() = "+mBtAdapter.isEnabled());
                    //mBtAdapter.isEnabled()::::Return true if Bluetooth is currently enabled and ready for use.
                    //Equivalent to: getBluetoothState() == STATE_ON

                    Log.i(TAG, "onClick - BT not enabled yet");
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                }
                else {
                    //TODO 该 处 存在一个BUG
                    //// TODO: 16-6-5  当用户退出后，按钮这回显示的是还原的初始化的设置，即使现在是 连接 的 状态

                    //判断如果，这儿的按钮 是 显示的 Connect 才可以进行打开 listView的列表；
                    showMessage(" 当前状态是什么："+mState+"    " +
                            "    21 = UART_PROFILE_DISCONNECTED ; 20 = connection");
                    if (LinkLanyaCC.getText().equals("ON")){
                        //Connect button pressed, open DeviceListActivity
                        // class, with popup windows that scan for devices
                        //showMessage(" =-=======-= ");
                        Intent newIntent = new Intent(MainActivity.this,
                                DeviceListActivity.class);
                        startActivityForResult(newIntent, REQUEST_SELECT_DEVICE);
                    } else {

                        showMessage(" ******** ");
                        //Disconnect button pressed
                        if (mDevice!=null)
                        {
                            mService.disconnect();
                        }
                    }
                }



                break;
            default:
                break;
        }

    }


    private void setSelect(int i) {
        setTab(i);
        mViewPager.setCurrentItem(i);
    }

    private void setTab(int i) {

        resetImgs();
        //设置图片为亮点
        //切换内容区域
        switch (i) {
            case 0:
//                mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed);
                mImgWeixin.setImageResource(R.drawable.comui_tab_home_selected);
                idMainTitle.setText("Hall");
                LinkLanyaCC.setVisibility(View.VISIBLE);
                break;

            case 1:
                mImgFrd.setImageResource(R.drawable.comui_tab_message_selected);
                idMainTitle.setText("Friend");
//                LinkLanyaCC.setVisibility(View.GONE);
                break;

            case 2:
                mImgAddress.setImageResource(R.drawable.comui_tab_find_selected);
                idMainTitle.setText("View");
//                LinkLanyaCC.setVisibility(View.GONE);
                break;

            case 3:
                mImgSettings.setImageResource(R.drawable.comui_tab_person_selected);
                idMainTitle.setText("Setting");
//                LinkLanyaCC.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 切换图片至暗色
     */
    private void resetImgs() {
        mImgWeixin.setImageResource(R.drawable.comui_tab_home);
        mImgFrd.setImageResource(R.drawable.comui_tab_message);
        mImgAddress.setImageResource(R.drawable.comui_tab_find);
        mImgSettings.setImageResource(R.drawable.comui_tab_person);
    }

    @Override
    public void sendActivity(String data) {
//        Toast.makeText(MainActivity.this, "fragment发送来的数据 ： " + data, Toast.LENGTH_LONG).show();
//        idMainTitle.setText(data);
        Log.i(TAG,"["+"sssss"+"] RX: "+data);
        
        //// TODO: 16-6-13 在这儿发送给 蓝牙 的数据


        String message = data;
        sendMessageToLanya(message);

    }

    /**
     * 发送 数据给 蓝牙板
     * @param message
     */
    private void sendMessageToLanya(String message) {
        appSendDataToLanyaCC(message);
    }

    /**
     * app 发送数据给 蓝牙cc2541的代码，
     * @param message
     */
    private void appSendDataToLanyaCC(String message) {
        byte[] value;
        try {
            //send data to service
            value = message.getBytes("UTF-8");
            mService.writeRXCharacteristic(value);

            //Update the log with time stamp
            String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    ////////////  //////////////

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            LocalBroadcastManager.
                    getInstance(this).
                    unregisterReceiver(UARTStatusChangeReceiver);
        } catch (Exception ignore) {
            Log.e(TAG, ignore.toString());
        }
//        }

        // TODO: 16-6-6 取消绑定；
        unbindService(mServiceConnection);
        mService.stopSelf();
        mService= null;

        Log.d(TAG, "onDestroy()");
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }


    //返回键   的    事件 监听 处理。
    //    @Override
//    public void onBackPressed() {
////      super.onBackPressed();
//        Intent intent = new Intent();
//        intent.setClass(MainActivity.this, ExitApp.class);
//        startActivity(intent);
//    }

    @Override
    public void onResume() {
        super.onResume();

//// TODO: 16-6-21 检测是否开启蓝牙
        Log.d(TAG, "onResume");
        Log.d(TAG, "onResume     :     "+mBtAdapter.isEnabled());


        if (!mBtAdapter.isEnabled()) {
//            showMessage("onResume     :     "+mBtAdapter.isEnabled());
//            showMessage("onResume : mBtAdapter.isEnabled() is :"+mBtAdapter.isEnabled());
            Log.i(TAG, "onResume - BT not enabled yet");
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case REQUEST_SELECT_DEVICE:
                //When the DeviceListActivity return, with the selected device address
                if (resultCode == Activity.RESULT_OK && data != null) {
                    String deviceAddress = data.getStringExtra(BluetoothDevice.EXTRA_DEVICE);
                    mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceAddress);

                    Log.d(TAG, "... onActivityResultdevice.address==" + mDevice + "mserviceValue" + mService);
                    OFFSTRINGState.setText(mDevice.getName()+ " - connecting");
                    mService.connect(deviceAddress);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "Bluetooth has turned on for onActivityResult ", Toast.LENGTH_SHORT).show();

                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, "Problem in BT Turning ON for onActivityResult", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "finish(); is acticiyty");
                    finish();
                }
                break;
            default:
                Log.e(TAG, "wrong request code");
                break;
        }
    }






    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (mState == UART_PROFILE_CONNECTED) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            showMessage("onBackPressed : nRFUART's running in background.\n " +
                    " Disconnect to exit");
        }
        else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.popup_title)
                    .setMessage(R.string.popup_message)
                    .setPositiveButton(R.string.popup_yes, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.popup_no, null)
                    .show();
        }
    }


}
