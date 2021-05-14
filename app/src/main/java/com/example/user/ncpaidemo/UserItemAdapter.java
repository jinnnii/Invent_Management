package com.example.user.ncpaidemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakao.usermgmt.response.model.User;

import java.util.ArrayList;

public class UserItemAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private ArrayList<UserItem> data; //Item 목록을 담을 배열
    private int layout;

    public UserItemAdapter(Context context, int layout, ArrayList<UserItem> data) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }
        UserItem userItem = data.get(position);

        if(layout==R.layout.content_select_in_list){

            //이름
            TextView name = (TextView) convertView.findViewById(R.id.item_name);
            name.setText(userItem.getName());

            //대분류+ 소분류
            TextView category = (TextView) convertView.findViewById(R.id.item_category);
            category.setText(userItem.getlCategory()+" ) "+userItem.getsCategory());
        }

        if(layout==R.layout.content_self_input_list){
            EditText name =(EditText)convertView.findViewById(R.id.item_name);
            name.setText(userItem.getName());
        }

        return convertView;
    }


}
