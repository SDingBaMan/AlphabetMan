package com.sdingba.su.wenshidu;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    private MyTempView mTempView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTempView = (MyTempView) findViewById(R.id.mTemp);
        mTempView.setTemp("38.5", 39.5f,
                35.4f, this, 380);
    }
}
