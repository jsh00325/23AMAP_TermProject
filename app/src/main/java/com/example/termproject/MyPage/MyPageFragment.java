package com.example.termproject.MyPage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.termproject.MyPage.PasswordResetActivity;
import com.example.termproject.R;
import com.example.termproject.UserManagement.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MyPageFragment extends Fragment {

    TextView passwordResetTextView, logoutTextView, deleteTextView, myScrapList;

    String type;
    private FirebaseAuth mAuth ;

    public MyPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mypage_fragment, container, false);

        passwordResetTextView = view.findViewById(R.id.mypage_password_reset);

        logoutTextView = view.findViewById(R.id.mypage_log_out);

        deleteTextView = view.findViewById(R.id.mypage_withdrawal);

        myScrapList = view.findViewById(R.id.mypage_scrap);

        mAuth = FirebaseAuth.getInstance();

        passwordResetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PasswordResetActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("알람 팝업")
                        .setMessage("로그 아웃하시겠습니까??")
                        .setPositiveButton("로그 아웃", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                requireActivity().finish();
                            }
                        })
                        .show();

            }
        });

        deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("알람 팝업")
                        .setMessage("탈퇴하시겠습니까?")
                        .setPositiveButton("삭제", (dialogInterface, i) -> {
//                            Objects.requireNonNull(mAuth.getCurrentUser()).delete();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            requireActivity().finish();
                            startActivity(intent);
                        })
                        .show();
            }
        });

        myScrapList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScrapListActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });


        return view;
    }
}