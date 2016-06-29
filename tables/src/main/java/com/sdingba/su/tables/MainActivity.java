package com.sdingba.su.tables;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sdingba.su.tables.realm.RealmWikiExample;
import com.sdingba.su.tables.viewTables.ListViewBarChartActivity;
import com.sdingba.su.tables.viewTables.PieChartActivity;
import com.sdingba.su.tables.viewTables.RadarChartActivitry;

public class MainActivity extends AppCompatActivity {

//    private Button button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onclickID(View v){
        switch (v.getId()) {
            case R.id.yuanxing:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PieChartActivity.class);
                startActivity(intent);

                break;
            case R.id.zhuxing:
//                RadarChartActivitry
                Intent intent2 = new Intent();
                intent2.setClass(MainActivity.this, RadarChartActivitry.class);
                startActivity(intent2);
                Toast.makeText(MainActivity.this, "xxxx", Toast.LENGTH_LONG).show();
                break;

            case R.id.multi_zhuxing:
//                RadarChartActivitry
                Intent intent3 = new Intent();
                intent3.setClass(MainActivity.this, ListViewBarChartActivity.class);
                startActivity(intent3);
                Toast.makeText(MainActivity.this, "xxxx", Toast.LENGTH_LONG).show();
                break;

            case R.id.realmAC:
//                RadarChartActivitry
                Intent intent4 = new Intent();
                intent4.setClass(MainActivity.this, RealmWikiExample.class);
                startActivity(intent4);
                Toast.makeText(MainActivity.this, "xxxx", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
