package com.example.user.ncpaidemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.usermgmt.response.model.User;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.example.user.ncpaidemo.SelectBaseActivity.lStr;
import static com.example.user.ncpaidemo.SelectInActivity.setListViewHeightBasedOnChildren;
import static com.example.user.ncpaidemo.SelfInActivity.REQUEST_CODE_MENU;

public class OcrInActivity extends AppCompatActivity {

    private ArrayList<UserItem> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //note 타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //note 투명 배경
        getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));

        setContentView(R.layout.content_ocr_in);


        ListView listView = findViewById(R.id.in_group);
        Intent intent = getIntent();

        list = (ArrayList<UserItem>) intent.getSerializableExtra("userItem");

        UserItemAdapter adpater = new UserItemAdapter(this, R.layout.content_ocr_in_list, list);
        listView.setAdapter(adpater);

        setListViewHeightBasedOnChildren(listView);

        findViewById(R.id.ok_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new FirebaseUserHelper().addUserItem(list, new FirebaseUserHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<UserItem> userItems, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(OcrInActivity.this,"성공!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == REQUEST_CODE_MENU){
            if(resultCode == RESULT_OK){
                ListView listView = findViewById(R.id.in_group);

                int nDay = data.getIntExtra("nDay",0);
                int pos = data.getIntExtra("pos",0);
                String lCategory = data.getStringExtra("lCategory");
                String sCategory = data.getStringExtra("sCategory");

                list.get(pos).setBase(lCategory,sCategory,nDay);

                UserItemAdapter adapter = new UserItemAdapter(this, R.layout.content_ocr_in_list, list);
                listView.setAdapter(adapter);

                setListViewHeightBasedOnChildren(listView);

                System.out.println("@@@@@@position : "+pos);

                for(int i=0; i<list.size(); i++){
                    list.get(i).print();
                }

            }
        }
    }

}
