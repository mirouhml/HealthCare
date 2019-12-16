package com.example.phoenix.healthcare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.phoenix.healthcare.Models.ChatModel;
import com.example.phoenix.healthcare.R;
import com.github.library.bubbleview.BubbleTextView;

import java.util.List;

public class ChatAdapter extends BaseAdapter{

    private List<ChatModel> list_chat_models;
    private Context context;
    private LayoutInflater layoutInflater;

    public ChatAdapter(List<ChatModel> list_chat_models, Context context) {
        this.list_chat_models = list_chat_models;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list_chat_models.size();
    }

    @Override
    public Object getItem(int position) {
        return list_chat_models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null)
        {
            if(list_chat_models.get(position).isSend)
                view = layoutInflater.inflate(R.layout.messenger_send,null);
            else
                view = layoutInflater.inflate(R.layout.messenger_receive,null);

            BubbleTextView text_message = view.findViewById(R.id.text_message);
            TextView date = view.findViewById(R.id.date);
            text_message.setText(list_chat_models.get(position).message);
            date.setText(list_chat_models.get(position).date);

        }
        return view;
    }
}
