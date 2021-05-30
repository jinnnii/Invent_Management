package com.example.user.ncpaidemo;


import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private ChartActivity frag1;
    private HomeActivity frag2;
    private RecipeActivity frag3;

    //public static String image_path = null; //이미지 파일 경로


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        /* ***************************************************************************************************************** */

        //strNick = getIntent().getStringExtra("name");


        //프래그
        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.action_chart:
                        setFrag(0);
                        break;
                    case R.id.action_home:
                        setFrag(1);
                        break;
                    case R.id.action_menu:
                        setFrag(2);
                        break;
                }
                return true;
            }
        });

        System.out.println("::::::::::::::::::::::: strNick ::::::::::::::::::::: " +strNick);

        frag1=new ChartActivity();
        frag2=new HomeActivity();
        frag3=new RecipeActivity();

        Bundle bundle1 = new Bundle();
        bundle1.putString("strNick",strNick);

        Bundle bundle2 = new Bundle();
        bundle2.putString("strNick",strNick);

        Bundle bundle3 = new Bundle();
        bundle3.putString("strNick",strNick);


        frag1.setArguments(bundle1);
        frag2.setArguments(bundle2);
        frag3.setArguments(bundle3);

        int frag= getIntent().getIntExtra("frag",0);
        if(frag ==0){
            setFrag(1);
        }
        else setFrag(frag);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        getAppHashKey(); //해쉬키 얻기
    }
    private void getAppHashKey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("HashKey",something);
            }
        }catch(Exception e){
            Log.e("name not found", e.toString());
        }
    }

    // 프레그먼트 교체
    private void setFrag(int n)
    {
        fm = getSupportFragmentManager();
        ft= fm.beginTransaction();
        switch (n)
        {
            case 0:
                ft.replace(R.id.Main_Frame,frag1);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.Main_Frame,frag2);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.Main_Frame,frag3);
                ft.commit();
                break;
        }
    }

    //버튼 팝업 액티비티





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

