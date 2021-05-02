package com.example.user.ncpaidemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.ncp.ai.demo.process.OcrProc;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class OcrActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_result);

        SharedPreferences sharedPref = getSharedPreferences("PREF", Context.MODE_PRIVATE);

        final String ocrApiGwUrl = sharedPref.getString("ocr_api_gw_url", "");
        final String ocrSecretKey = sharedPref.getString("ocr_secret_key", "");

        OcrActivity.PapagoNmtTask nmtTask = new OcrActivity.PapagoNmtTask();
        nmtTask.execute(ocrApiGwUrl, ocrSecretKey);

    }


    public class PapagoNmtTask extends AsyncTask<String, String, String> {

        @Override
        public String doInBackground(String... strings) {

            return OcrProc.main(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(String result) {

            ReturnThreadResult(result);
        }
    }

    public void ReturnThreadResult(String result) {
        System.out.println("###  Retrun Thread Result");
        String translateText = "";

        String rlt = result;
        try {
            JSONObject jsonObject = new JSONObject(rlt);

            /*JSONArray jsonArray  = jsonObject.getJSONArray("images");


            for (int i = 0; i < jsonArray.length(); i++ ){

                JSONArray jsonArray_subResult  = jsonArray.getJSONObject(i).getJSONArray("result.subResults.items");

                for (int j=0; j < jsonArray_subResult.length(); j++ ){

                    String item = jsonArray_subResult.getJSONObject(j).getString("name");
                    translateText += item;
                    translateText += " ";
                }
            }*/

            TextView txtResult = (TextView) findViewById(R.id.textView_ocr_result);
            txtResult.setText(result);

        } catch (Exception e){

        }
    }
}
