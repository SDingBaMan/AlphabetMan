package com.sdingba.su.alphabet_demotest.view.socket;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdingba.su.alphabet_demotest.ConstantValue;
import com.sdingba.su.alphabet_demotest.R;
import com.sdingba.su.alphabet_demotest.SharPredInter;
import com.sdingba.su.alphabet_demotest.utils.ChineseCharToEn;
import com.sdingba.su.alphabet_demotest.utils.PromptManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by su on 16-4-21.
 */
public class ChatActivity extends Activity implements View.OnClickListener, Runnable {


    /**
     * 自己的ID
     */
    private String name;
    /**
     * 接受数据人的ID
     */
//    private String revicesname = "sdingba";
    private String revicesname;

    private String UserNamePic;

    private TextView titleFriend;



    private Button mBtnSend;
    private Button mBtnBack;
    private EditText mEditTextContext;
    private SharedPreferences pref;
    private ListView mListView;
    private ChatMsgViewAdapter mAdapter;
    private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();

    Socket socket;
    Thread thread;
    private static final int PORT = 19990;
    private String ip = "172.25.23.4";
    DataInputStream in;
    DataOutputStream out;
    boolean flag = false;
    private String chat_txt;
    private String chat_in;

    private String[] msgArray = new String[]{
            "有大吗", "有！你呢？", "我也有",
            "那上吧", "打啊！你放大啊", "你tm咋不放大呢？留大抢人头那！Cao的。你个菜",
            "不解释", "xxxxx...."};
    private String[] dataArray = new String[]{
            "2012-09-01 18:00", "2012-09-01 18:10",
            "2012-09-01 18:11", "2012-09-01 18:20",
            "2012-09-01 18:30", "2012-09-01 18:35",
            "2012-09-01 18:40", "2012-09-01 18:50"};
    private final static int COUNT = 8;//数组大小

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_xiaohei);
        //启动的activity的时候，补自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.
                SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pref = getSharedPreferences(SharPredInter.SHAR_TABLE_NAME, MODE_PRIVATE);

        String namepref = pref.getString(SharPredInter.USER_NAME,"");

        if (namepref.equals("")) {
            name = "sdingba";
        }else{
            name=namepref;
        }
        Intent intent = getIntent();

        revicesname = intent.getStringExtra("reviceId");
        UserNamePic = intent.getStringExtra("reviceName");
        initView();

        intiDate();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(ConstantValue.SOCKET_IP, ConstantValue.SOCKET_PORT);
//                                    Log.i("xxxx", "    " + ip + "   ");
                    in = new DataInputStream(socket.getInputStream());
                    out = new DataOutputStream(socket.getOutputStream());
                    Date now = new Date(System.currentTimeMillis());
                    SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
                    String nowStr = format.format(now);
                    out.writeUTF("[$$" + ":" + name + ":" + revicesname + ":" + nowStr + "上线了]");

                } catch (IOException e1) {
                    System.out.println("can not connect");
                }
            }
        }).start();


        thread = new Thread(ChatActivity.this);
        thread.start();
        flag = true;

    }


    private void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnBack = (Button) findViewById(R.id.btn_back);


        mBtnBack.setOnClickListener(this);
        mBtnSend.setOnClickListener(this);
        titleFriend = (TextView) findViewById(R.id.title_id_friendid);
        titleFriend.setText(UserNamePic);
        mEditTextContext = (EditText) findViewById(R.id.et_sendmessage);

    }


    private void intiDate() {
        for (int i = 0; i < COUNT; i++) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setDate(dataArray[i]);
            if (i % 2 == 0) {
                entity.setName(revicesname);
                entity.setMsgType(true);
                entity.setUserName(UserNamePic);
            } else {
                entity.setName(name);
                entity.setMsgType(false);
            }
            entity.setText(msgArray[i]);
            mDataArrays.add(entity);
        }
        mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
        mListView.setAdapter(mAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                send();


                break;
            case R.id.btn_back:
                backActivityRoll();
                finish();
                break;
        }
    }

    private void send() {

        if (socket == null) {
            PromptManager.showToastTest(this,"没有链接 服务器 不能发送； ");
            return;
        }

        String conString = mEditTextContext.getText().toString();
        if (flag == false) {
            Toast.makeText(ChatActivity.this, "没有登录，请登录！", Toast.LENGTH_SHORT).show();
            return;
        }

        chat_txt = conString;

        if (chat_txt != null) {
            Date now = new Date(System.currentTimeMillis());
            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
            String nowStr = format.format(now);
            try {

                if (conString.length() > 0) {
                    ChatMsgEntity entity = new ChatMsgEntity();
                    entity.setDate(getDate());
                    entity.setName(name);
                    entity.setMsgType(false);
                    entity.setText(conString);

                    //对list进行添加，如果，add(0,entity),加载到第一条，而不是最后一条
                    mDataArrays.add(entity);
                    mAdapter.notifyDataSetChanged();
                    mEditTextContext.setText("");
                    //把listView的焦点 处于 mKistView.getCount-1上面，显示效果好
                    mListView.setSelection(mListView.getCount() - 1);
                }

                out.writeUTF("[id=" + name + ":"
                        + "message=" + chat_txt + ":"
                        + "time=" + nowStr + ":"
                        + "reciver=" + revicesname + "]");
//                String sendStr = "我" + ":" + chat_txt;

//  historyEdit.append(sendStr);//在 history 里面 添加 数据


            } catch (IOException e2) {
            }
        } else {
            try {
//                out.writeUTF("请说话");
                Toast.makeText(ChatActivity.this, "服务器为开启。！", Toast.LENGTH_SHORT).show();
            } catch (Exception e3) {
            }
        }


    }

    public String getDate() {
        Calendar c = Calendar.getInstance();
        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH));
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String mins = String.valueOf(c.get(Calendar.MINUTE));
        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":" + mins);

        return sbBuffer.toString();
    }


    public void head_xiaohei(View v) {
//        Intent intent = new Intent(ChatActivity.this, InfoXiaohei.class);
//        startActivity(intent);
    }


    @Override
    public void run() {
        while (true) {
            try {
                chat_in = in.readUTF();
                chat_in = chat_in + "\n";

                Message message = new Message();
                message.obj = chat_in;
                mHandler.sendMessage(message);
            } catch (Exception e) {
            }
        }
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String aaa = (String) msg.obj;
            if (aaa.contains("history$$$")){
                String messString = chat_in.replace("history$$$", "");
                String[] messsageList = messString.split("\\;");
                for (String mess : messsageList) {
                    messageUpdate(mess);
                }
            }else{
                messageUpdate(aaa);
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 传入数据，解析后更新到ListView中，
     * 格式：    ID:Message  (note： 单条数据。)
     * @param mess
     */
    private void messageUpdate(String mess) {

        if (mess.equals("") && mess == null) {
            return;
        }
        String revice[] = mess.split(":");
        String reviceTextPerson = revice[0];
        String reviceMessage = revice[1];

        ChatMsgEntity entity = new ChatMsgEntity();
        entity.setDate(getDate());
        entity.setName(reviceTextPerson);
        entity.setMsgType(true);
        entity.setUserName(UserNamePic);
        entity.setText(reviceMessage);

        //对list进行添加，如果，add(0,entity),加载到第一条，而不是最后一条
        mDataArrays.add(entity);

        mAdapter.notifyDataSetChanged();
        mEditTextContext.setText("");
        //把listView的焦点 处于 mKistView.getCount-1上面，显示效果好
        mListView.setSelection(mListView.getCount() - 1);
    }

    @Override
    public void onBackPressed() {
        backActivityRoll();
        super.onBackPressed();
    }

    private void backActivityRoll() {
        if (flag == true) {
            if (flag == false) {
                Toast.makeText(ChatActivity.this, "没有登录，请登录！", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        try {
            if (socket == null) {
                PromptManager.showToastTest(this,"退出。。。 ");
                return;
            }else{

                out.writeUTF("[##" + ":" + name + ":" + revicesname + ":" + "下线了]");
            }

            out.close();
            in.close();
            socket.close();

        } catch (IOException e4) {
        }
        flag = false;
        Toast.makeText(ChatActivity.this, "已退出！", Toast.LENGTH_SHORT).show();

    }
}
