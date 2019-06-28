package com.example.kesbokar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_Reply extends BaseAdapter {
    ArrayList<Get_for_reply> get_for_replies;
    TextView date,replyby,reply,sno;
    LayoutInflater layoutInflater;
    Context context;
    Activity activity;
    public Adapter_Reply(Context context ,Reply_Help_Desk activity ,ArrayList<Get_for_reply> get_for_replies)
    {
        this.context=context;
        this.activity=activity;
        this.get_for_replies=get_for_replies;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return get_for_replies.size();
    }

    @Override
    public Object getItem(int position) {
        return get_for_replies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int pos=position+1;
        String i=""+pos;
        convertView=layoutInflater.inflate(R.layout.adapter_reply,null);
        reply=convertView.findViewById(R.id.reply);
        sno=convertView.findViewById(R.id.Sno);
        replyby=convertView.findViewById(R.id.replyby);
        date=convertView.findViewById(R.id.date);
        date.setText(get_for_replies.get(position).getDate());
        replyby.setText(get_for_replies.get(position).getReplyBy());
        reply.setText(get_for_replies.get(position).getReplyMessage());
        sno.setText(i);
        return convertView;
    }
}