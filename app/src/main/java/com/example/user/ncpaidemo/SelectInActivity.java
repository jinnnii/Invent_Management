package com.example.user.ncpaidemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.RequiresApi;

import com.kakao.usermgmt.response.model.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.layout.simple_list_item_1;
import static android.view.Gravity.CENTER;
import static android.view.Gravity.CENTER_VERTICAL;
import static android.view.Gravity.RIGHT;

public class SelectInActivity extends PopupActivity {

    private int item_position;
    Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //note 타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //note 투명 배경
        getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        setContentView(R.layout.content_select_in);


        itemsList();

        Button ok = findViewById(R.id.ok_btn);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intent==null){
                    Toast.makeText(getApplicationContext(),"선택하세요",Toast.LENGTH_SHORT).show();
                }
                else {
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });


    }




    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("UseCompatLoadingForDrawables")
    public void itemsList() {

        ListView listView = findViewById(R.id.in_group);

        ArrayList<UserItem> list = new ArrayList<>();

        for(int i=0; i<10;i++){

            UserItem userItem = new UserItem("(특대)딸기","과일", "딸기");

            list.add(userItem);
        }

        UserItemAdapter adpater = new UserItemAdapter(this, R.layout.content_select_in_list, list);
        listView.setAdapter(adpater);

        setListViewHeightBasedOnChildren(listView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                intent = new Intent(getApplicationContext(),SelfInActivity.class);
                intent.putExtra("name", list.get(position).getName());
            }
        });
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
}