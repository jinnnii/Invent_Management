package com.example.user.ncpaidemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.ncpaidemo.BaseActivity.inList;
import static com.example.user.ncpaidemo.BaseActivity.strNick;

public class HomeActivity extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_home, container, false);

        System.out.println("##### HomeActivity 에서의 inList : "+inList);

        TextView title= (TextView) view.findViewById(R.id.title);
        title.setText(strNick+" 냉장고");

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.item_list);
        new FirebaseUserHelper().readUserItem(new FirebaseUserHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<UserItem> userItems, List<String> keys) {
                if(strNick ==null){
                    Toast.makeText(getContext(), "strNick 이 종료됨", Toast.LENGTH_SHORT).show();
                }

                new HomeAdapter().setConfig(mRecyclerView, getContext(), userItems, keys);

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

        view.findViewById(R.id.input).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), AddImageTypeMenu.class);
                startActivityForResult(intent, 1);
            }
        });

        view.findViewById(R.id.setting_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), AuthActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
}
