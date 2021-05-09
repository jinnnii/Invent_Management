package com.example.user.ncpaidemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

//첫 화면(로그인 화면)
public class StartActivity extends AppCompatActivity {
    private ISessionCallback mSessionCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSessionCallback = new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                //로그인 요청
                UserManagement.getInstance().me(new MeV2ResponseCallback() {
                    @Override
                    //세션닫기
                    public void onFailure(ErrorResult errorResult) {
                        Toast.makeText(StartActivity.this, "로그인 도중 오류사 생겼습니다. 다시 시도하십시오.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    //세션닫기
                    public void onSessionClosed(ErrorResult errorResult) {
                        Toast.makeText(StartActivity.this, "세션이 닫혔습니다. 다시 시도하십시오.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    //로그인 성공
                    public void onSuccess(MeV2Response result) {
                        //성공시 LoginActivity 클래스로 이동
                        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                        intent.putExtra("name", result.getKakaoAccount().getProfile().getNickname());
                        intent.putExtra("profileImg", result.getKakaoAccount().getProfile().getProfileImageUrl());
                        intent.putExtra("email", result.getKakaoAccount().getEmail());
                        startActivity(intent);

                        Toast.makeText(StartActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {
                Toast.makeText(StartActivity.this, "onSessionOpenFailed", Toast.LENGTH_SHORT).show();
            }
        };
        Session.getCurrentSession().addCallback(mSessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();


        //getAppHashKey();
    }
}
