package com.example.user.ncpaidemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.ncpaidemo.MainActivity.setListViewHeightBasedOnChildren;
import static com.example.user.ncpaidemo.SelfInActivity.REQUEST_CODE_MENU;
import static com.example.user.ncpaidemo.UserItemAdapter.isStringDouble;

public class RecipeAddActivity extends AppCompatActivity {

    private ArrayList<WideUserItem> list = new ArrayList<>();
    private ArrayList<WideUserItem> wList = new ArrayList<>();
    private ArrayList<String> keys = new ArrayList<>();
    WideUserItem wUserItem;
    private Recipe recipe;

    String recipeName = null;                                   //레시피 이름
    int recipePrice = 0;                                        //레시피 가격
    static ArrayList<RecipeItem> rList = new ArrayList<>();     //레시피 원재료 리스트


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //note 타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.content_recipe_add);

        EditText rName = (EditText) findViewById(R.id.recipe_name);
        EditText rPrice = (EditText) findViewById(R.id.recipe_price);

        findViewById(R.id.add_wUserItem_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecipeFinditemActivity.class);
                startActivityForResult(intent, REQUEST_CODE_MENU);
            }
        });


        findViewById(R.id.ok_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseRecipeHelper helper = new FirebaseRecipeHelper();

                boolean success = false;

                //레시피 가격
                if (isStringDouble(rPrice.getText().toString())) {
                    recipePrice = Integer.parseInt(rPrice.getText().toString());
                } else recipePrice = -1;

                //레시피 이름
                recipeName = rName.getText().toString();


                //레시피 원재료 리스트
                for (int i = 0; i < rList.size(); i++) {
                    if (rList.get(i).getAmount() <= 0 || rList.get(i).getCount() <= 0 || recipePrice <= 0) {
                        Toast.makeText(RecipeAddActivity.this, "빈칸 혹은 잘못된 값이 입력되었습니다", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    if (i == rList.size() - 1) success = true;
                }

                if (success) {

                    int total_unit_price = 0;
                    //원가 계산
                    for (int i = 0; i < rList.size(); i++) {
                        int total_price = 0;
                        int total_amount = 0;
                        int unit_amount;

                        for (int j = 0; j < wList.size(); j++) {
                            if (rList.get(i).getsCategory().equals(wList.get(j).getsCategory())) {

                                total_price = wList.get(j).getTotal_price();
                                total_amount = wList.get(j).getNowAmount();
                                break;
                            }
                        }
                        unit_amount = (rList.get(i).getAmount() * total_price) / total_amount;
                        System.out.println("########가격 : "+rList.get(i).getAmount()+" || " +total_price +"|| "+total_amount);
                        rList.get(i).setPrice(unit_amount); //아이템당 가격
                        total_unit_price += unit_amount;

                    }


                    recipe = new Recipe(recipeName, recipePrice, total_unit_price,1, rList);

                    helper.addRecipe(recipe, new FirebaseRecipeHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(ArrayList<Recipe> userItems, List<String> keys) {
                        }

                        @Override
                        public void DataIsInserted() {
                            Toast.makeText(RecipeAddActivity.this, "성공!", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode);
        System.out.println(resultCode);
        System.out.print(data);
        if (requestCode == REQUEST_CODE_MENU) {
            if (resultCode == RESULT_OK) {
                assert data != null;

                WideUserItem wUserItem = new WideUserItem(data.getStringExtra("sCategory"), data.getStringExtra("unit"),
                        data.getIntExtra("nowAmount", 0), data.getIntExtra("maxCount", 0), data.getIntExtra("total_price", 0));

                wList.add(wUserItem);

                RecipeItem item = new RecipeItem(wUserItem.getsCategory(),wUserItem.getUnit());


                ListView listView = findViewById(R.id.add_wUserItem_list);

                RecipeItemAdapters adapters = new RecipeItemAdapters(RecipeAddActivity.this, R.layout.content_recipe_add_list, item);
                //adapters.data.add(item);
                listView.setAdapter(adapters);



                //list.add((WideUserItem) data.getSerializableExtra("wUserItem"));
                //keys.add( data.getStringExtra("keys"));
                setListViewHeightBasedOnChildren(listView);
                //RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.add_wUserItem_list);
                // new RecipeItemAdapters().setConfig(mRecyclerView, RecipeAddActivity.this, list);
            }
        }
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == RESULT_OK) {
            }
        }
    }

}
