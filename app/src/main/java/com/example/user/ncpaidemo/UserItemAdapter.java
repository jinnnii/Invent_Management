package com.example.user.ncpaidemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
        UserItem userItem =  data.get(position);


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
            count.setText(""+userItem.getCount());
            price.setText(userItem.getPrice());
            unit_price.setText(userItem.getUnitPrice());

        }

        //note 영수증 상세 입력 리스트
        if(layout == R.layout.content_ocr_in_list){

            EditText name = (EditText) convertView.findViewById(R.id.item_name);
            EditText count = (EditText) convertView.findViewById(R.id.item_count);
            EditText unit_price = (EditText) convertView.findViewById(R.id.item_unit_price);
            EditText amount = (EditText) convertView.findViewById(R.id.item_amount);
            TextView category = (TextView) convertView.findViewById(R.id.item_category);
            TextView nDay = (EditText) convertView.findViewById(R.id.item_nDay);
            Spinner unit = (Spinner) convertView.findViewById(R.id.spinner);

            name.setText(userItem.getName());
            count.setText(""+userItem.getCount());
            unit_price.setText(userItem.getUnitPrice());

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

                    ((Activity) v.getContext()).startActivityForResult(intent,REQUEST_CODE_MENU);
                }
            });

            if(userItem.getnDay()!=0){
                nDay.setText(""+userItem.getnDay());
                category.setText(userItem.getsCategory()+"("+userItem.getlCategory()+")");
            }



            //note Spinner
            unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {
                    String str= (String)unit.getSelectedItem();
                    userItem.setUnit(str);
                    userItem.print();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });


            //note Edit뷰!
            name.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String str = name.getText().toString();
                    userItem.setName(str);
                    userItem.print();

                }
                @Override public void beforeTextChanged(CharSequence s, int start,int count, int after) { }
                @Override public void afterTextChanged(Editable s) { }});


            unit_price.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String str = unit_price.getText().toString();
                    userItem.setUnit_price(str);
                }
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                @Override public void afterTextChanged(Editable s) { }});


            count.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int c) {
                    int str = Integer.parseInt(count.getText().toString());
                    userItem.setCount(str);
                    userItem.print();
                }

                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                @Override public void afterTextChanged(Editable s) { }});


            nDay.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int str = Integer.parseInt(nDay.getText().toString());
                    userItem.setnDay(str);
                    userItem.print();
                }

                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                @Override public void afterTextChanged(Editable s) { }});


            amount.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int str = Integer.parseInt(amount.getText().toString());
                    userItem.setAmount(str);
                    userItem.print();
                }
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                @Override public void afterTextChanged(Editable s) { }});
        }

        return convertView;
    }


}
