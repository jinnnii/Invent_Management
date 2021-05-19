package com.example.user.ncpaidemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.example.user.ncpaidemo.SelectBaseActivity.lStr;

public class UserItemAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<UserItem> data; //Item 목록을 담을 배열
    static String unitStr[] = {"g","kg","mL","L"};
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

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }
        UserItem userItem = data.get(position);


        //note 추가할 사용자 원재료 리스트

        if (layout == R.layout.content_select_in_list) {

            //이름
            TextView name = (TextView) convertView.findViewById(R.id.item_name);
            name.setText(userItem.getName());

            //대분류+ 소분류
            TextView category = (TextView) convertView.findViewById(R.id.item_category);
            category.setText(userItem.getlCategory() + " ) " + userItem.getsCategory());
        }

        //note 추가된 사용자 원재료 리스트
        if (layout == R.layout.content_self_input_list) {
            EditText name = (EditText) convertView.findViewById(R.id.item_name);
            TextView category = (TextView) convertView.findViewById(R.id.item_category);
            EditText nDay = (EditText) convertView.findViewById(R.id.item_nDay);

            userItem.setName(userItem.getsCategory());
            name.setText(userItem.getName());
            nDay.setText("" + userItem.getnDay());

            /*TextView lCategory =(TextView) convertView.findViewById(R.id.item_lCategory);
            TextView sCategory =(TextView) convertView.findViewById(R.id.item_sCategory);
            lCategory.setText(userItem.getlCategory());
            sCategory.setText(userItem.getsCategory());*/

            category.setText(userItem.getlCategory());
            userItem.print();
            System.out.println(userItem.getnDay());

            convertView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    data.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        //note 영수증 인식 결과 리스트
        if (layout == R.layout.content_ocr_list) {

            TextView name = (TextView) convertView.findViewById(R.id.item_name);
            TextView count = (TextView) convertView.findViewById(R.id.item_count);
            TextView unit_price = (TextView) convertView.findViewById(R.id.item_unit_price);
            TextView price = (TextView) convertView.findViewById(R.id.item_price);

            name.setText(userItem.getName());
            count.setText("" + userItem.getCount());
            price.setText(userItem.getPrice());
            unit_price.setText(userItem.getUnit_price());

        }

        //note 영수증 상세 입력 리스트
        if (layout == R.layout.content_ocr_in_list) {

            EditText name = (EditText) convertView.findViewById(R.id.item_name);
            EditText count = (EditText) convertView.findViewById(R.id.item_count);
            EditText unit_price = (EditText) convertView.findViewById(R.id.item_unit_price);
            EditText unit_amount = (EditText) convertView.findViewById(R.id.item_unit_amount);
            TextView nDay = (EditText) convertView.findViewById(R.id.item_nDay);
            Spinner unit = (Spinner) convertView.findViewById(R.id.spinner);
            Spinner category = (Spinner) convertView.findViewById(R.id.item_category);

            name.setText(userItem.getName());
            count.setText("" + userItem.getCount());
            unit_price.setText(userItem.getUnit_price());

            convertView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    data.remove(position);
                    notifyDataSetChanged();
                }
            });

            new FirebaseUserHelper().readUserItem(new FirebaseUserHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<UserItem> userItems, List<String> keys) {
                    for (int i = 0; i < userItems.size(); i++) {
                        if (userItem.getName().equals(userItems.get(i).getName())) {
                            userItem.setnDay(userItems.get(i).getnDay());                   //유통기한
                            userItem.setUnit_amount(userItems.get(i).getUnit_amount());     //상세수량
                            userItem.setUnit(userItems.get(i).getUnit());                   //단위
                            userItem.setlCategory(userItems.get(i).getlCategory());         //대분류
                            userItem.setsCategory(userItems.get(i).getsCategory());         //소분류
                            System.out.println("################ocr 리스트와 userItem 조회 ##################"+getStrPosition(userItems.get(i).getlCategory(),lStr));
                            userItems.get(i).print();
                            userItem.print();

                            nDay.setText(""+userItems.get(i).getnDay());
                            unit_amount.setText(""+userItems.get(i).getUnit_amount());
                            category.setSelection(getStrPosition(userItems.get(i).getlCategory(),lStr));
                            unit.setSelection(getStrPosition(userItems.get(i).getUnit(),unitStr));

                            break;

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


            //note Spinner

            category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {
                    String str = (String) category.getSelectedItem();

                    AssetManager assetManager = arg1.getResources().getAssets();

                    try {
                        InputStream is = assetManager.open("base.json");
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader reader = new BufferedReader(isr);

                        StringBuffer buffer = new StringBuffer();
                        String line = reader.readLine();
                        while (line != null) {
                            buffer.append(line + "\n");
                            line = reader.readLine();
                        }
                        String jsonData = buffer.toString();

                        JSONArray jsonArray = new JSONObject(jsonData).getJSONObject("BaseInfo").getJSONObject("lCategory").getJSONArray(str);
                        ArrayList<String> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            list.add(jsonArray.getJSONObject(i).getString("sCategory"));

                        }
                        for (int i = 0; i < list.size(); i++) {
                            if (userItem.getName().contains(list.get(i))) {
                                userItem.setsCategory(jsonArray.getJSONObject(i).getString("sCategory"));
                                userItem.setnDay(jsonArray.getJSONObject(i).getInt("nDay"));
                                nDay.setText("" + userItem.getnDay());
                                break;
                            } else {
                                userItem.setnDay(-1);
                                nDay.setText("-1");
                            }
                        }

                        userItem.setlCategory(str);


                    } catch (IOException | JSONException e) { }

                    userItem.print();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {
                    String str = (String) unit.getSelectedItem();

                    userItem.setUnit(str);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


            //note Edit뷰!
            name.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String str = name.getText().toString();
                    userItem.setName(str);
                    userItem.print();

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });


            unit_price.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String str = unit_price.getText().toString();
                    userItem.setUnit_price(str);
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });


            count.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int c) {
                    if (isStringDouble(count.getText().toString()) == false) {
                        userItem.setCount(-1);
                    } else {
                        int str = Integer.parseInt(count.getText().toString());
                        userItem.setCount(str);
                        userItem.print();
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });


            nDay.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (isStringDouble(nDay.getText().toString()) == false) {
                        userItem.setnDay(-1);
                    } else {
                        int str = Integer.parseInt(nDay.getText().toString());
                        userItem.setnDay(str);
                    }
                    userItem.print();
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });


            unit_amount.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (isStringDouble(unit_amount.getText().toString()) == false) {
                        userItem.setUnit_amount(-1);
                    } else {
                        int str = Integer.parseInt(unit_amount.getText().toString());
                        userItem.setUnit_amount(str);
                        userItem.print();
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        return convertView;
    }

    //문자열이 숫자인지 판별하는 함수
    public static boolean isStringDouble(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int getStrPosition(String str, String[] strArray){
        int pos = 0;
        for(int i =0; i<strArray.length;i++){
            if(strArray[i].equals(str)){
                pos=i+1;
                break;
            }
        }
        return pos;
    }

}
