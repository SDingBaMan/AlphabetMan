package com.sdingba.su.alphabet_demotest.view;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sdingba.su.alphabet_demotest.R;
import com.sdingba.su.alphabet_demotest.SharPredInter;
import com.sdingba.su.alphabet_demotest.bean.SetDataYan;
import com.sdingba.su.alphabet_demotest.bean.senddataYanPlan;
import com.sdingba.su.alphabet_demotest.engine.Impl.SetDataEngineImpl;
import com.sdingba.su.alphabet_demotest.engine.SetDataEngine;
import com.sdingba.su.alphabet_demotest.net.MyHttpAsyncTask;
import com.sdingba.su.alphabet_demotest.utils.PromptManager;
import com.sdingba.su.alphabet_demotest.utils.dataTimeUtils;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by su on 16-6-26.
 */
public class SetDataPlan extends Activity {

    private Button AddsetData;

    private SharedPreferences pref;

    private dataListAdapter adapter;

    private TextView setdatanewday;

    private String newdayTime;
    //    private String[] setDataDay = null;
//    private String[] setYanNumber = null;
    private List<SetDataYan> setDataYenList = new ArrayList<SetDataYan>();
    ;

    private ListView listDataView;

    private Button simbutButton;

    private Boolean isCunData = false;


    //-------------------------------------doalog
    private Button bt_ok;
    private EditText et_setdataday;
    private EditText et_setyannumber;
    private Button bt_cancel;

    private String datalist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_data_scholler);

        newdayTime = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
        pref = getSharedPreferences(SharPredInter.SHAR_TABLE_NAME, MODE_PRIVATE);

        initview();


    }

    private void initview() {

        AddsetData = (Button) findViewById(R.id.add_setData);
        setdatanewday = (TextView) findViewById(R.id.setdatanewday);

        listDataView = (ListView) findViewById(R.id.set_data_scoloer);
        adapter = new dataListAdapter();

        //setDataYenList 判断 是否 为空，显示的 是那些数据。
        datalist = pref.getString(SharPredInter.Schedule_table, "");

        if (!datalist.equals("")) {

            String startTime = pref.getString(SharPredInter.beginTime, "");
            if (!startTime.equals("")) {
                setdatanewday.setText("    设置开始时间为 ： " + startTime);
            }
            isCunData = true;

//            setdatanewday.setText("    设置开始时间为 ： " +
//                    new SimpleDateFormat("yyyyMMdd").format(new Date()).toString());

            AddsetData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PromptManager.showToast(SetDataPlan.this, "计划没有完成，不可以修改或添加哦");
                }
            });

            //给 setDataYenList 进行赋值
            //解析6：3:8,1:8,1:8,3:8,3:8,8:12,1:8,3:8,3:8

            String[] partyB = datalist.split("\\,");
            for (String par : partyB) {
                SetDataYan setdateOne = new SetDataYan();
                String[] liss = par.split("\\:");
                setdateOne.setDaytime(liss[0]);
                setdateOne.setYanNumber(liss[1]);
                setDataYenList.add(setdateOne);
            }

        } else {
            isCunData = false;

            setdatanewday.setText("    设置开始时间为 ： " + newdayTime);


            AddsetData.setClickable(true);
            AddsetData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SetDataPlan.this);
                    final AlertDialog dialog = builder.create();
                    View contenView = View.inflate(SetDataPlan.this, R.layout.dialog_add_setdata, null);
                    et_setdataday = (EditText) contenView.findViewById(R.id.et_setdataday);
                    et_setyannumber = (EditText) contenView.findViewById(R.id.et_setyannumber);
                    bt_cancel = (Button) contenView.findViewById(R.id.cancel);
                    bt_ok = (Button) contenView.findViewById(R.id.ok);
                    dialog.setView(contenView, 0, 0, 0, 0);
                    dialog.show();
                    bt_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //获取  2 个文本信息 的 值
                            String daynumber = et_setdataday.getText().toString();
                            String yansetnumber = et_setyannumber.getText().toString();
                            if (daynumber.equals("") || yansetnumber.equals("")) {
                                PromptManager.showToast(SetDataPlan.this, "数据 不能为空");
                            } else {

                                SetDataYan dataYan = new SetDataYan();
                                dataYan.setDaytime(daynumber);
                                dataYan.setYanNumber(yansetnumber);
                                setDataYenList.add(dataYan);
                            }

                            //todo 通知更新 adapter
                            adapter.notifyDataSetChanged();

                            dialog.dismiss();

                        }
                    });
                    bt_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                        }
                    });
                }
            });
        }


        listDataView.setAdapter(adapter);


        simbutButton = (Button) findViewById(R.id.simbut_button);
        simbutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isCunData) {
                    PromptManager.showToast(SetDataPlan.this, "计划没有完成，不可以重复提交哦...");
                } else {
                    //暂时没有数据
                    //1 提交数据给服务器，
                    //a 封装数据；
                    //SubmitSetDataYan?username=sdingba&starttime=20160808&
                    // datasetSc=6:6,6:6,6:6,6:12,6:12,7:12(datalist)
                    final StringBuffer bf = new StringBuffer();
                    for (SetDataYan aa : setDataYenList) {
                        bf.append(aa.getDaytime());
                        bf.append(":");
                        bf.append(aa.getYanNumber());
                        bf.append(",");
                    }
                    int length = bf.length();
                    bf.delete(length - 1, length);
                    final String datastrdata = bf.toString();


                    senddataYanPlan senddataYanPlan = new senddataYanPlan();
                    String username = pref.getString(SharPredInter.USER_NAME, "");


                    senddataYanPlan.setDatasetSc(datastrdata);
                    senddataYanPlan.setStarttime(newdayTime);
                    senddataYanPlan.setUsername(username);

                    new MyHttpAsyncTask<senddataYanPlan, String>(SetDataPlan.this) {
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                        }

                        @Override
                        protected String doInBackground(senddataYanPlan... params) {
                            senddataYanPlan sendObject = params[0];
                            SetDataEngine engine = new SetDataEngineImpl();
                            String reslute = engine.sendServerData(sendObject);
                            return reslute;
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            String rel = s;
                            if (rel.equals("") || rel == null) {
                                PromptManager.showToast(SetDataPlan.this, "服务出现异常，请检测网络...");
                            } else {
                                if (rel.equals("ok")) {
                                    PromptManager.showToast(SetDataPlan.this, "提交成功");
                                    //2，初始化本地数据

                                    //给出提示信息，是否确定添加
                                    dataTimeUtils dateUtils = new dataTimeUtils(SetDataPlan.this);
                                    dateUtils.PullStringToDate(newdayTime, datastrdata);

                                    isCunData = true;
                                } else if (rel.equals("error")) {
                                    isCunData = false;
                                    PromptManager.showToast(SetDataPlan.this, "服务出现异常，请检测网络...");
                                }
                            }
                            super.onPostExecute(s);
                        }
                    }.executeProxy(senddataYanPlan);


                }
            }
        });
    }


    private class dataListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return setDataYenList.size();
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

            View view;
            ViewHolder holder;
            if (convertView == null) {
                view = View.inflate(getApplicationContext(), R.layout.set_date_plan_item, null);
                holder = new ViewHolder();

                holder.tv_black_number = (TextView) view.findViewById(R.id.tv_black_number);
                holder.tv_block_mode = (TextView) view.findViewById(R.id.tv_block_mode);

                holder.tv_day = (TextView) view.findViewById(R.id.set_data_day);
                holder.tv_number = (TextView) view.findViewById(R.id.set_data_YanNumber);
                holder.iv_delete = (ImageView) view.findViewById(R.id.iv_delete);

                view.setTag(holder);


            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            if (isCunData) {
                holder.tv_black_number.setText(" 到期时间为 : ");
                holder.tv_block_mode.setText(" 在此期间可以抽的烟数（根） : ");
            }

            holder.tv_day.setText(setDataYenList.get(position).getDaytime());
            holder.tv_number.setText(setDataYenList.get(position).getYanNumber());

            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isCunData) {
                        PromptManager.showToast(SetDataPlan.this, "计划没有完成，不可以删除...");
                    } else {
                        setDataYenList.remove(position);
                        //通知
                        adapter.notifyDataSetChanged();
                    }
                }
            });
            return view;
        }
    }

    private static class ViewHolder {

        private TextView tv_black_number;

        private TextView tv_block_mode;

        private TextView tv_day;

        private TextView tv_number;
        private ImageView iv_delete;

    }


}
