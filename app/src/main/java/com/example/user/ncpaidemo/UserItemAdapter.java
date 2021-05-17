package com.example.user.ncpaidemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kakao.usermgmt.response.model.User;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.example.user.ncpaidemo.SelfInActivity.REQUEST_CODE_MENU;

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


        //note 추가할 사용자 원재료 리스트

        if(layout==R.layout.content_select_in_list){

            //이름
            TextView name = (TextView) convertView.findViewById(R.id.item_name);
            name.setText(userItem.getName());

            //대분류+ 소분류
            TextView category = (TextView) convertView.findViewById(R.id.item_category);
            category.setText(userItem.getlCategory()+" ) "+userItem.getsCategory());
        }

        //note 추가된 사용자 원재료 리스트
        if(layout==R.layout.content_self_input_list){
            EditText name =(EditText)convertView.findViewById(R.id.item_name);
            name.setText(userItem.getName());

            /*TextView lCategory =(TextView) convertView.findViewById(R.id.item_lCategory);
            TextView sCategory =(TextView) convertView.findViewById(R.id.item_sCategory);
            lCategory.setText(userItem.getlCategory());
            sCategory.setText(userItem.getsCategory());*/

            TextView category = (TextView) convertView.findViewById(R.id.item_category);
            category.setText(userItem.getlCategory()+" ("+userItem.getsCategory()+")");

            convertView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    data.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        //note 영수증 인식 결과 리스트
        if(layout==R.layout.content_ocr_list){

            TextView name = (TextView)convertView.findViewById(R.id.item_name);
            TextView count = (TextView)convertView.findViewById(R.id.item_count);
            TextView unit_price = (TextView)convertView.findViewById(R.id.item_unit_price);
            TextView price =(TextView)convertView.findViewById(R.id.item_price);

            name.setText(userItem.getName());
            count.setText(userItem.getCount());
            price.setText(userItem.getPrice());
            unit_price.setText(userItem.getUnitPrice());

        }

        //note 영수증 상세 입력 리스트
        if(layout == R.layout.content_ocr_in_list){
            TextView name = (TextView)convertView.findViewById(R.id.item_name);
            TextView count = (TextView)convertView.findViewById(R.id.item_count);
            TextView unit_price = (TextView)convertView.findViewById(R.id.item_unit_price);
            TextView category = (TextView) convertView.findViewById(R.id.item_category);
            EditText nDay = (EditText) convertView.findViewById(R.id.item_nDay);

            name.setText(userItem.getName());
            count.setText(userItem.getCount());
            unit_price.setText(userItem.getUnitPrice());
            category.setText(userItem.getsCategory());
            //nDay.setText(userItem.getnDay());

            convertView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    data.remove(position);
                    notifyDataSetChanged();
                }
            });


            convertView.findViewById(R.id.item_category).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), SelectBaseActivity.class);
                    intent.putExtra("pos",position);

                    System.out.println("!!!!!!!!!!!!!!!Position:"+position);
                    ((Activity) v.getContext()).startActivityForResult(intent,REQUEST_CODE_MENU);
                }
            });
        }

        return convertView;
    }


}
