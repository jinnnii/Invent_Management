package com.example.user.ncpaidemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

import static com.example.user.ncpaidemo.SelectInActivity.setListViewHeightBasedOnChildren;

public class SelfInActivity extends PopupActivity{

    ArrayList<UserItem> list = new ArrayList<>();
    public static final int REQUEST_CODE_MENU = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //note 타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //note 투명 배경
        getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        setContentView(R.layout.content_self_input);

        findViewById(R.id.select_in_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SelectInActivity.class);
                startActivityForResult(intent,REQUEST_CODE_MENU);
            }
        });


        }


        public void itemsList(Intent intent){

            ListView listView = findViewById(R.id.in_group);

            String name = intent.getStringExtra("name");

            UserItem userItem = new UserItem(name, null,null);
            list.add(userItem);

            UserItemAdapter adpater = new UserItemAdapter(this, R.layout.content_self_input_list, list);
            listView.setAdapter(adpater);

            setListViewHeightBasedOnChildren(listView);

        }


        //note 리스트뷰 자동 높이 조절
        public static void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;

            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                //listItem.measure(0, 0);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();

            params.height = totalHeight;
            listView.setLayoutParams(params);

            listView.requestLayout();
        }

        @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == REQUEST_CODE_MENU){
            if(resultCode == RESULT_OK){
                itemsList(data);
            }
        }
        }

}
