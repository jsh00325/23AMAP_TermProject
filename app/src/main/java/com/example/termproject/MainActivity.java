package com.example.termproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navbar = (BottomNavigationView) findViewById(R.id.activity_main_navbar);

//        TODO : 유저의 로그인 확인하고, 로그아웃이면 로그아웃 액티비티 실행
//        ...
//        ...
//        ...

//        TODO : 각각의 프래그먼트 만들면 그 프래그먼트 연결하기!
//        navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                FragmentManager fm = getSupportFragmentManager();
//                for (int i = 0; i < fm.getBackStackEntryCount(); i++)
//                    fm.popBackStack();
//
//                // TODO: 각각의 프래그먼트 만들어서 이어주기
//                switch (item.getItemId()) {
//                    case R.id.home :
//                        return true;
//                    case R.id.bookmark:
//                        return true;
//                    case R.id.category:
//                        return true;
//                    case R.id.myPage:
//                        return true;
//                }
//                return false;
//            }
//        });
    }

    /** address주소의 프래그먼트 슬롯을 frag라는 프래그먼트로 교체
     *  @param address 프래그먼트 슬롯의 주소
     *  @param frag 변경할 프래그먼트   */
    private void changeFragment(int address, Fragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(address, frag);
        ft.commit();
    }
}