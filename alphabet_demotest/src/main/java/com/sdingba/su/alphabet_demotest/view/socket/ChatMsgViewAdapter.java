package com.sdingba.su.alphabet_demotest.view.socket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdingba.su.alphabet_demotest.R;
import com.sdingba.su.alphabet_demotest.utils.ChineseCharToEn;

import java.util.List;

/**
 * Created by su on 16-4-21.
 */
public class ChatMsgViewAdapter extends BaseAdapter {

    private static interface IMsgViewType {
        int IMVT_COM_MSG = 0;
        int IMVT_TO_MSG = 1;
    }

    private static final String TAG = ChatMsgViewAdapter.class.getSimpleName();
    private List<ChatMsgEntity> coll;

    private Context ctx;
    private LayoutInflater mInflater;

    public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> mDataArrays) {
        ctx = context;
        this.coll = mDataArrays;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return coll.size();
    }

    @Override
    public Object getItem(int position) {
        return coll.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMsgEntity entity = coll.get(position);
        if (entity.getMsgType()) {
            return IMsgViewType.IMVT_COM_MSG;
        } else {
            return IMsgViewType.IMVT_TO_MSG;
        }

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMsgEntity entity = coll.get(position);
        boolean isConMsg = entity.getMsgType();
//        currentType = getItemViewType(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            if (isConMsg) {
                convertView = mInflater.inflate(R.layout.chatting_item_msg_text_left, null);

            } else {
                convertView = mInflater.inflate(R.layout.chatting_item_msg_text_right, null);

            }
            viewHolder = new ViewHolder();
            viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
            viewHolder.tvpics = (TextView) convertView.findViewById(R.id.PicsNameId);
            viewHolder.isComMsg = isConMsg;
            convertView.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvSendTime.setText(entity.getDate());
        viewHolder.tvUserName.setText(entity.getName());
        viewHolder.tvContent.setText(entity.getText());
        if (isConMsg) {
            viewHolder.tvpics.setText(entity.getUserName().substring(0,1));
        }else{
//            viewHolder.tvpics.setText(entity.getName().substring(0,1));

        }

        return convertView;
    }


    static class ViewHolder {
        public TextView tvSendTime;
        public TextView tvUserName;

        public TextView tvContent;
        public TextView tvpics;
        public boolean isComMsg = true;
    }

    /*

    将操作添加到类中使用，。
    在
    if(){
         convertView = mInflater.inflate(R.layout.chatting_item_msg_text_left, null);
        new ViewHolder(converView);
    }

    ///////////////////////////////////////////

         class ViewHolder {
             ImageView iv_icon;
             TextView tv_name;

             public ViewHolder(View view) {
                 iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                 tv_name = (TextView) view.findViewById(R.id.tv_name);
                 view.setTag(this);
            }
         }

     */

}
