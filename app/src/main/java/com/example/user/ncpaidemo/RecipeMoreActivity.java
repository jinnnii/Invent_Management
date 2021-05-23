package com.example.user.ncpaidemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.example.user.ncpaidemo.MainActivity.setListViewHeightBasedOnChildren;

public class RecipeMoreActivity extends AppCompatActivity {

    private Recipe list = new Recipe(); // 아이템 리스트
    //private ArrayList<String> keys = new ArrayList<>(); //키 리스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //note 타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.content_recipe_more);

        Intent intent = getIntent();

        list = (Recipe)intent.getSerializableExtra("recipeList");
        ArrayList<RecipeItem> rlist = list.getUserItems();

        TextView name = findViewById(R.id.recipe_name);
        TextView price = findViewById(R.id.recipe_price);
        TextView unit_price = findViewById(R.id.recipe_unit_price);


        name.setText(list.getName());
        price.setText(""+list.getPrice());
        unit_price.setText(""+list.getTotal_unit_price());



        ListView listView = findViewById(R.id.recipe_list);

        RecipeItemMoreAdapter adapters = new RecipeItemMoreAdapter(RecipeMoreActivity.this, R.layout.content_recipe_more_list, rlist);
        //adapters.data.add(item);
        listView.setAdapter(adapters);
        setListViewHeightBasedOnChildren(listView);
    }
}
