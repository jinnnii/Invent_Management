package com.example.user.ncpaidemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.load.engine.Resource;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.user.ncpaidemo.SelectInActivity.setListViewHeightBasedOnChildren;

public class SelfInActivity extends MainActivity{

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

        /*findViewById(R.id.select_in_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddItemTypeMenu.class);

                startActivity(intent);
                //startActivityForResult(intent,REQUEST_CODE_MENU);
            }
        });*/


        ImageButton selectInBtn = (ImageButton) findViewById(R.id.select_in_btn);

        selectInBtn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){

                AlertDialog.Builder dlg = new AlertDialog.Builder(SelfInActivity.this);
                dlg.setTitle("가져올 원재료를 선택하세요"); //제목
                String[] versionArray = new String[] {"베이스 원재료","사용자 원재료"};

                dlg.setItems(versionArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(versionArray[which]=="사용자 원재료") {
                            Intent intent = new Intent(getApplicationContext(), SelectInActivity.class);
                            startActivityForResult(intent,REQUEST_CODE_MENU);
                        }
                        else{ //베이스 원재료
                            Intent intent = new Intent(getApplicationContext(), SelectBaseActivity.class);
                            startActivityForResult(intent,REQUEST_CODE_MENU);
                        }

                    }
                });
                dlg.show();
            }
        });


        }


        public void itemsList(Intent intent){

            ListView listView = findViewById(R.id.in_group);

            String name = intent.getStringExtra("name");
            String lCategory = intent.getStringExtra("lCategory");
            String sCategory = intent.getStringExtra("sCategory");
            int nDay = intent.getIntExtra("nDay",1);

            UserItem userItem = new UserItem(name, lCategory,sCategory, nDay );
            list.add(userItem);

            UserItemAdapter adapter = new UserItemAdapter(this, R.layout.content_self_input_list, list);
            listView.setAdapter(adapter);

            setListViewHeightBasedOnChildren(listView);

        }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        System.out.println(requestCode);
        System.out.println(resultCode);
            System.out.print(data);
        if(requestCode == REQUEST_CODE_MENU){
            if(resultCode == RESULT_OK){
                itemsList(data);
                System.out.print(data.getExtras());
            }
        }
        }

}
