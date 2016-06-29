package com.sdingba.su.alphabet_demotest.fargment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ImageFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sdingba.su.alphabet_demotest.ConstantValue;
import com.sdingba.su.alphabet_demotest.R;
import com.sdingba.su.alphabet_demotest.SharPredInter;
import com.sdingba.su.alphabet_demotest.utils.ChineseCharToEn;
import com.sdingba.su.alphabet_demotest.view.socket.ChatActivity;

public class FrdFragment extends Fragment {

    private ListView freind_list;
    private SharedPreferences pref;

    private static int[] item_logo = {
            R.drawable.skin1,
            R.drawable.skin2,
            R.drawable.skin3,
            R.drawable.skin4,
            R.drawable.skin5,
            R.drawable.skin5,
            R.drawable.skin1,
            R.drawable.skin2,
            R.drawable.skin3,
            R.drawable.skin4,
            R.drawable.skin5,
            R.drawable.skin5,
    };


    private String[] name_item;
    private String[] context_item;
//    private String[] time_item;

    private String[] name_item1 = {
            "十点半", "Alphabet", "Man",
            "那天", "链接", "帅帅",
            "我是谁", "Alphabet", "字母表",
            "JayZhou", "周杰伦", "Xiong"};

    private String[] context_item1 = {
            "这是我的OMG", "团队名就是字母表", "字母表的男人",
            "不能说的秘密", "我是链接器", "这是我的本来的名字",
            "这是我的OMG", "团队名就是字母表", "字母表的男人",
            "不能说的秘密", "我是链接器", "这是我的本来的名字"};

//    private String[] time_item1 = {
//            "昨天", "20160512", "20140909",
//            "昨天", "20160512", "20140909",
//            "昨天", "20160512", "20140909",
//            "昨天", "20160512", "20140909"};


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab02, container, false);
        freind_list = (ListView) view.findViewById(R.id.friend_list);
        initFriend();


        freind_list.setAdapter(new FriendListItem());
        freind_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.putExtra("reviceId", context_item[position]);
                intent.putExtra("reviceName", name_item[position]);
                intent.setClass(getActivity(), ChatActivity.class);
                startActivity(intent);

            }
        });
        return view;
    }

    private void initFriend() {
        pref = getActivity().getSharedPreferences(SharPredInter.SHAR_TABLE_NAME, Context.MODE_PRIVATE);
        String friendListStr = pref.getString(SharPredInter.FRIEND_LIST, "");
        if (!friendListStr.equals("")) {
            System.out.println("lllllll   " + friendListStr);

            //解析字符串
            String[] frelist = friendListStr.split("\\,");
            int longFriend = frelist.length;
            name_item = new String[longFriend];
            context_item = new String[longFriend];
            for (int i = 0; i < longFriend; i++) {

                String[] person = frelist[i].split("\\:");
                context_item[i] = person[0];
                name_item[i] = person[1];

            }
        } else {
            name_item = new String[1];
            context_item = new String[1];
            name_item[0] = "戒烟客服";
            context_item[0] = "××暂时没有好友××";

//            name_item = name_item1;
//            context_item = context_item1;
//            time_item = time_item1;
        }
    }

    @Override
    public void onStart() {

        super.onStart();

        Log.i("Main", "2222222222222222222---onStart()");

    }

    @Override
    public void onResume() {

        super.onResume();

        Log.i("Main", "2222222222222222222---onResume()");

    }

    @Override
    public void onPause() {

        super.onPause();

        Log.i("Main", "2222222222222222222   t2---onPause()");

    }

    @Override
    public void onStop() {

        super.onStop();

        Log.i("Main", "2222222222222222222---onStop()");

    }


    class FriendListItem extends BaseAdapter {

        @Override
        public int getCount() {
            return name_item.length;
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
            ViewHodler hodler = null;
            if (convertView == null) {
                hodler = new ViewHodler();
                convertView = View.inflate(getContext(), R.layout.listview_item_friend, null);
                hodler.imageViewLogo = (ImageView) convertView.findViewById(R.id.head_image);
                hodler.friend_name = (TextView) convertView.
                        findViewById(R.id.friend_list_item_name);
                hodler.friend_time = (TextView) convertView.findViewById(R.id.friend_list_time);
                hodler.friend_context = (TextView) convertView.findViewById(R.id.friend_list_context);
                hodler.friend_name_first = (TextView) convertView.findViewById(R.id.name_first);

                convertView.setTag(hodler);
            } else {
                hodler = (ViewHodler) convertView.getTag();
            }
            hodler.imageViewLogo.setImageResource(item_logo[position % 12]);
            hodler.friend_name.setText(name_item[position]);
//            hodler.friend_time.setText(time_item[position]);
            hodler.friend_time.setText("今天");
            hodler.friend_context.setText(context_item[position]);

            String name_first_char = name_item[position].substring(0, 1);

            hodler.friend_name_first.setText(
                    new ChineseCharToEn().getFirstLetter(name_first_char).toUpperCase());

            hodler.imageViewLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 0) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), ChatActivity.class);
                        startActivity(intent);

                    }
                }
            });
            return convertView;
        }
    }

    class ViewHodler {

        private ImageView imageViewLogo;
        private TextView friend_time;
        private TextView friend_name;
        private TextView friend_context;
        private TextView friend_name_first;
    }

}
