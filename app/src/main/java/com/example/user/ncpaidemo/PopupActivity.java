package com.example.user.ncpaidemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;


public class PopupActivity extends Activity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //타이틀바 없애기
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.flag_plus);

            findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                    startActivity(intent);
                }
            });

            findViewById(R.id.gallery).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
                    startActivity(intent);
                }
            });

            // 권한 체크
            TedPermission.with(getApplicationContext())
                    .setPermissionListener(permissionListener)
                    .setRationaleMessage("카메라 권한이 필요합니다.")
                    .setDeniedMessage("거부하셨습니다.")
                    .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                    .check();



        }

        //확인 버튼 클릭
        public void mOnClose(View v){

            //액티비티(팝업) 닫기
            finish();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            //바깥레이어 클릭시 안닫히게
            if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
                return false;
            }
            return true;
        }

        @Override
        public void onBackPressed() {
            //안드로이드 백버튼 막기
            return;
        }


        //권한 허용 및 거부
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(getApplicationContext(), "권한이 허용됨",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(getApplicationContext(), "권한이 거부됨",Toast.LENGTH_SHORT).show();
            }
        };
    }
