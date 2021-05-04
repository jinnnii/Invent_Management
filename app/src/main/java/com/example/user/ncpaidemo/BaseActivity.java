package com.example.user.ncpaidemo;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseActivity extends AppCompatActivity {

    //n  이미지 경로
    public static String image_path = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_actions, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {

            case R.id.menu_home:

                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                return true;

            case R.id.menu_auth:

                intent = new Intent(getApplicationContext(), AuthActivity.class);
                startActivity(intent);

                return true;

            default :
                return super.onOptionsItemSelected(item);
        }
    }

    //언더 네비게이션 메뉴
    public static class Frag1 extends Fragment
    {

        private View view;


        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
        {
            view = inflater.inflate(R.layout.flag_plus,container,false);

            return view;
        }
    }

}


