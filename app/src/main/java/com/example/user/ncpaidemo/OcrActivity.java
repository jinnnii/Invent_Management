package com.example.user.ncpaidemo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.ncp.ai.demo.process.OcrProc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OcrActivity extends PopupActivity {

    String year, month, day;
    String store_name;

    ArrayList<String> item_name = new ArrayList<>();
    ArrayList<String> item_count = new ArrayList<>();
    ArrayList<String> item_unit_price = new ArrayList<>();
    ArrayList<String> item_price = new ArrayList<>();

    String total_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //note 타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //note 투명 배경
        getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));

        setContentView(R.layout.content_ocr_result);

        /*SharedPreferences sharedPref = getSharedPreferences("PREF", Context.MODE_PRIVATE);

        final String ocrApiGwUrl = sharedPref.getString("ocr_api_gw_url", "");
        final String ocrSecretKey = sharedPref.getString("ocr_secret_key", "");

        OcrActivity.PapagoNmtTask nmtTask = new OcrActivity.PapagoNmtTask();
        nmtTask.execute(ocrApiGwUrl, ocrSecretKey);*/

        String str = getString(R.string.ocr_sample);
        TextView txtResult = (TextView) findViewById(R.id.textView_ocr_result);

        ocr_test(str); //note JSON 결과값 나누기

    }


    public class PapagoNmtTask extends AsyncTask<String, String, String> {

        @Override
        public String doInBackground(String... strings) {

            return OcrProc.main(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(String result) {

            // ReturnThreadResult(result);
        }
    }

    //ReturnThreadResult
    public void ocr_test(String rlt) {
        System.out.println("###  Retrun Thread Result");
        String translateText = "";


        String img = rlt;

        try {
            JSONObject jsonObject = new JSONObject(img);
            AddJsonPath path = new AddJsonPath(jsonObject);


            //note 매장명명
            store_name = path.store();

            //note 결제날짜
            year = path.date("year"); //note 년
            month = path.date("month"); //note 월
            day = path.date("day"); //note 일

            //note 항목 그룹 정보

            for (int i = 0; i < path.subResults.length(); i++) {
                JSONArray items = path.subResults.getJSONObject(i).getJSONArray("items");

                for (int j = 0; j < items.length(); j++) {

                    item_name.add(path.items("item_name", i, j)); //note 항목명
                    item_count.add(path.items("item_count", i, j)); //note 항목 수량
                    item_unit_price.add(path.items("item_unit_price", i, j)); //note 단가
                    item_price.add(path.items("item_price", i, j)); //note 금액


                }
            }

            //note 총 금액
            total_price = path.total();


            TextView txtResult = (TextView) findViewById(R.id.textView_ocr_result);
            txtResult.setText(translateText);


            printValue(path); // note 로그 출력

        } catch (Exception e) {
            System.out.println("Error!!");
            e.printStackTrace();
        }
    }


    //note Log 출력
    public void printValue(AddJsonPath path) throws JSONException {

        System.out.println("#################################### 매장명 : " + store_name);
        System.out.println("#################################### 결제날짜 : " + year + "년" + month + "월" + day + "일");
        StringBuilder item = new StringBuilder();
        StringBuilder count = new StringBuilder();
        StringBuilder unit = new StringBuilder();
        StringBuilder price = new StringBuilder();

        for (int i = 0; i < path.subResults.length(); i++) {
            JSONArray items = path.subResults.getJSONObject(i).getJSONArray("items");
            for (int j = 0; j < items.length(); j++) {
                item.append(j).append("번 째 품목 : ").append(item_name.get(j)).append("\n");
                count.append(j).append("번 째 품목 : ").append(item_count.get(j)).append("\n");
                unit.append(j).append("번 째 품목 : ").append(item_unit_price.get(j)).append("\n");
                price.append(j).append("번 째 품목 : ").append(item_price.get(j)).append("\n");
            }
        }
        System.out.println("#################################### 품목 : " + item);
        System.out.println("#################################### 수량 : " + count);
        System.out.println("#################################### 단가 : " + unit);
        System.out.println("#################################### 금액 : " + price);

        System.out.println("#################################### 총금액 : " + total_price);
    }


    //note JSON 결과값 경로 나누기
    public class AddJsonPath {

        JSONObject result;
        JSONArray subResults;
        String path = "";

        public AddJsonPath(JSONObject jsonObject) throws JSONException {
            result = jsonObject.getJSONArray("images").getJSONObject(0).getJSONObject("receipt").getJSONObject("result");

            //note 항목경로
            subResults = result.getJSONArray("subResults");

        }

        //note 매장 명
        public String store() throws JSONException {

            if(result.getJSONObject("storeInfo")!=null){
                return result.getJSONObject("storeInfo").getJSONObject("name").optString("text","noValue");
            }
            return null;

        }

        //note 결제 날짜
        public String date(String value) throws JSONException {

            //note value = year, month, day
            if(result.getJSONObject("paymentInfo").getJSONObject("date")!=null){
                JSONObject date = result.getJSONObject("paymentInfo").getJSONObject("date").getJSONObject("formatted");
                return date.optString(value,"noValue");
            }
            return null;

        }

        //note 총 금액
        public String total() throws JSONException {

            if( result.getJSONObject("totalPrice").getJSONObject("price")!=null){
                return result.getJSONObject("totalPrice").getJSONObject("price").optString("text","novalue");
            }
            return null;
        }


        //note 항목 상세
        public String items(String value, int i, int j) throws JSONException {
            JSONArray items = subResults.getJSONObject(i).getJSONArray("items");
            JSONObject item = items.getJSONObject(j);
            JSONObject price = item.getJSONObject("price");

            switch (value) {
                case "item_name": //note 항목 이름
                    if(item.optJSONObject("name")!=null){
                        path = item.getJSONObject("name").optString("text", "noValue");
                    }
                    break;
                case "item_count": //note 항목 수량
                    if(item.optJSONObject("count")!=null)
                    {
                        path = item.optJSONObject("count").optString("text", "noValue");
                    }
                    break;
                case "item_unit_price": //note 단가
                    if(item.optJSONObject("name")!=null){
                        path = price.getJSONObject("unitPrice").optString("text", "noValue");
                    }
                    break;
                case "item_price": //note 금액
                    if(item.optJSONObject("name")!=null){
                        path = price.getJSONObject("price").optString("text", "noValue");
                    }
                    break;
            }
            return path;
        }
    }


}
