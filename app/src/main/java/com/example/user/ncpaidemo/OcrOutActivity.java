package com.example.user.ncpaidemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OcrOutActivity extends AppCompatActivity {

   /* //note UserItem List!
    private ArrayList<UserItem> list = new ArrayList<>();

    //note 메뉴에 들어가는 원재료 리스트
    private ArrayList<ArrayList<UserItem>> insertItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_ocr_in);

        Intent intent = getIntent();

        list = (ArrayList<UserItem>) intent.getSerializableExtra("userItem");  //영수증 아이템 리스트

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.item_list);
        new FirebaseUserHelper().readUserItem(new FirebaseUserHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<UserItem> userItems, List<String> keys) {    //메뉴 리스트
                for(int i=0;i<list.size(); i++){
                    for(int j=0; j<userItems.size();j++){
                        if(list.get(i).getName().equals(userItems.get(j).getName())){
                            insertItems.add(userItems.get(j).getUserItems());
                        }
                    }
                }
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });


        findViewById(R.id.buttontest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i =0; i<list.size(); i++){

                    /*EditText name = (EditText) listView.getAdapter().findViewById(R.id.item_name);
                    EditText count = (EditText) listView.findViewById(R.id.item_count);
                    EditText unit_price = (EditText) listView.findViewById(R.id.item_unit_price);
                    EditText unit_amount = (EditText) listView.findViewById(R.id.item_unit_amount);
                    EditText nDay = (EditText) listView.findViewById(R.id.item_nDay);


                    list.get(i).setName(name.getText().toString());


                    if(isStringDouble(nDay.getText().toString()))
                        list.get(i).setnDay(Integer.parseInt(nDay.getText().toString()));
                    else list.get(i).setnDay(-1);


                }
                System.out.print("######### 리스트 : ");
                for(int i=0; i<list.size(); i++){
                    System.out.print(list.get(i).getName()+" | ");
                }
                System.out.println("\n");



            }
        });


        //note 상세입력 모두 입력후 확인 버튼
        findViewById(R.id.ok_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<UserItem> addItem = new ArrayList<>(); //새로 추가할 아이템
                ArrayList<UserItem> updateItem = new ArrayList<>(); // 업데이트할 아이템

                FirebaseUserHelper userHelper = new FirebaseUserHelper();


                userHelper.addUserItem(list, new FirebaseUserHelper.DataStatus() {
                    @Override public void DataIsLoaded(ArrayList<MenuItem> userItems, List<String> keys) { }
                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(OcrOutActivity.this, "성공!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    @Override public void DataIsUpdated() { }
                    @Override public void DataIsDeleted() { }
                });

            }
        });

    }*/

}
