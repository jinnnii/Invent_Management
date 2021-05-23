package com.example.user.ncpaidemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.ncpaidemo.SelectInActivity.setListViewHeightBasedOnChildren;
import static com.example.user.ncpaidemo.UserItemAdapter.isStringDouble;

public class OcrInActivity extends AppCompatActivity {

    //note UserItem List!
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

        findViewById(R.id.buttontest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.print("######### 리스트 : ");
                for(int i=0; i<list.size(); i++){
                    list.get(i).print();
                }
                System.out.println("\n");

                for(int i=0; i<list.size(); i++){
                    if(list.get(i).getUnit_price()<=0 || list.get(i).getUnit_amount()<=0 ||
                            list.get(i).getCount()<=0 || list.get(i).getnDay()<=0){
                        System.out.println("###########################빈칸 이나 잘못된 값이 입력되었습니다");
                    }
                }


            }
        });


        //note 상세입력 모두 입력후 확인 버튼
        findViewById(R.id.ok_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<UserItem> addItem = new ArrayList<>(); //새로 추가할 아이템
                ArrayList<UserItem> updateItem = new ArrayList<>(); // 업데이트할 아이템

                FirebaseUserHelper userHelper = new FirebaseUserHelper();

                boolean success=false;

                for(int i=0; i<list.size(); i++){
                    if(list.get(i).getUnit_price()<=0 || list.get(i).getUnit_amount()<=0 ||
                            list.get(i).getCount()<=0 || list.get(i).getnDay()<=0){
                        Toast.makeText(OcrInActivity.this, "빈칸 혹은 잘못된 값이 입력되었습니다", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if(list.get(i).getlCategory()==null||list.get(i).getsCategory().equals("")||list.get(i).getUnit()=="▼"){
                        Toast.makeText(OcrInActivity.this, "카테고리를 다시 확인하세요", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if(i==list.size()-1) success=true;
                }
                if(success) {
                    userHelper.addUserItem(list, new FirebaseUserHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(ArrayList<UserItem> userItems, List<String> keys) {
                        }

                        @Override
                        public void DataIsInserted() {
                            Toast.makeText(OcrInActivity.this, "성공!", Toast.LENGTH_SHORT).show();
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

            }
        });

    }

}
