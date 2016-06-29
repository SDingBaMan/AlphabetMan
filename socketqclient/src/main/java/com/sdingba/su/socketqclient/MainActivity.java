package com.sdingba.su.socketqclient;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements Runnable {

    private EditText usernameEdit;
    private EditText ipEdit;
    private EditText historyEdit;
    private EditText messageEdit;

    private Button loginButton;
    private Button sendButton;
    private Button leaveButton;

    private String username, ip, chat_txt, chat_in;
    private static final int PORT = 19990;
    Socket socket;
    Thread thread;
    DataInputStream in;
    DataOutputStream out;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEdit = (EditText) findViewById(R.id.username);
        ipEdit = (EditText) findViewById(R.id.ip);
        historyEdit = (EditText) findViewById(R.id.history);
        messageEdit = (EditText) findViewById(R.id.message);

        loginButton = (Button) findViewById(R.id.LoginButton);
        sendButton = (Button) findViewById(R.id.SendButton);
        leaveButton = (Button) findViewById(R.id.leaveButton);

        loginButton.setOnClickListener(listener);
        leaveButton.setOnClickListener(listener);
        sendButton.setOnClickListener(listener);
    }

    @SuppressLint("SimpleDateFormat")
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.LoginButton:
                    if (flag == true) {
                        Toast.makeText(MainActivity.this, "已经登录过了！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    username = usernameEdit.getText().toString().trim();
                    ip = ipEdit.getText().toString().trim();
                    if (username != "" && username != null && username != "用户名输入" && ip != null) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    socket = new Socket(ip, PORT);
//                                    Log.i("xxxx", "    " + ip + "   ");
                                    in = new DataInputStream(socket.getInputStream());
                                    out = new DataOutputStream(socket.getOutputStream());
                                    Date now = new Date(System.currentTimeMillis());
                                    SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
                                    String nowStr = format.format(now);
                                    out.writeUTF("[$$" + ":" + username + ":" + " " + nowStr + "上线了]");

                                } catch (IOException e1) {
                                    System.out.println("can not connect");
                                }
                            }
                        }).start();


                        thread = new Thread(MainActivity.this);
                        thread.start();
                        flag = true;
                    }
                    break;
                case R.id.SendButton:
                    if (flag == false) {
                        Toast.makeText(MainActivity.this, "没有登录，请登录！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    chat_txt = messageEdit.getText().toString();
                    if (chat_txt != null) {
                        Date now = new Date(System.currentTimeMillis());
                        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
                        String nowStr = format.format(now);
                        try {
                            out.writeUTF("[id=" + username + ":"
                                    + "message=" + chat_txt + ":"
                                    + "time=" + nowStr + ":"
                                    + "reciver=" + "sdingba" + "]");
                            String sendStr = "我" + ":" + chat_txt;
                            historyEdit.append(sendStr);//在 history 里面 添加 数据
                        } catch (IOException e2) {
                        }
                    } else {
                        try {
                            out.writeUTF("请说话");
                        } catch (Exception e3) {
                        }
                    }
                    break;

                case R.id.leaveButton:
                    if (flag == true) {
                        if (flag == false) {
                            Toast.makeText(MainActivity.this, "没有登录，请登录！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    try {
                        out.writeUTF("[##" + ":" + username + ":" + "下线了]");
                        out.close();
                        in.close();
                        socket.close();

                    } catch (IOException e4) {
                    }
                    flag = false;
                    Toast.makeText(MainActivity.this, "已退出！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void run() {
        while (true) {
            try {
                chat_in = in.readUTF();
                chat_in = chat_in + "\n";
                mHandler.sendMessage(mHandler.obtainMessage());
            } catch (Exception e) {
            }
        }
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            historyEdit.append(chat_in);
            super.handleMessage(msg);
        }
    };

}
