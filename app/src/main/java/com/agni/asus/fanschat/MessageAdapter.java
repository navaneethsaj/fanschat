package com.agni.asus.fanschat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 12/20/2017.
 */

public class MessageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MessageObject> arrayList;
    LayoutInflater layoutInflater;

    public MessageAdapter(Context context, ArrayList<MessageObject> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view=layoutInflater.inflate(R.layout.adapter_listview,null);
        }

        TextView displayname=(TextView)view.findViewById(R.id.display_name);
        TextView displaymessage=(TextView)view.findViewById(R.id.display_message);
        //ImageView picaso_image_View=(ImageView)view.findViewById(R.id.picasso_image);

        displaymessage.setVisibility(View.VISIBLE);
        //picaso_image_View.setVisibility(View.VISIBLE);

        displayname.setText(arrayList.get(i).getName());
        displaymessage.setText(arrayList.get(i).getText());
        //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(picaso_image_View);

        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
