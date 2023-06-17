package com.example.termproject.MyPage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.termproject.ClubApply.ClubApplyActivity;
import com.example.termproject.ClubApply.ClubApplyWatchActivity;
import com.example.termproject.R;
import com.example.termproject.UserManagement.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyPageFragment extends Fragment {

    TextView passwordResetTextView, logoutTextView, deleteTextView, myScrapList, clubmanage;

    String type, admin;
    TextView profileNameTextView;

    CircleImageView profileImageView;

    private FirebaseAuth mAuth ;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentSnapshot document;

    public MyPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mypage_fragment, container, false);

        passwordResetTextView = view.findViewById(R.id.mypage_password_reset);

        logoutTextView = view.findViewById(R.id.mypage_log_out);

        deleteTextView = view.findViewById(R.id.mypage_withdrawal);

        myScrapList = view.findViewById(R.id.mypage_scrap);
        clubmanage = view.findViewById(R.id.mypage_club_managing);

        mAuth = FirebaseAuth.getInstance();

        profileImageView = view.findViewById(R.id.mypage_profile_pic);

        profileNameTextView = view.findViewById(R.id.mypage_profile_name);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Retrieve user document from "users" collection
            DocumentReference userRef = FirebaseFirestore.getInstance().collection("users").document(user.getUid());
            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        String name = document.getString("name");
                        profileNameTextView.setText(name);
                        String imageUrl = document.getString("imageUrl");

                        // Load the image into profileImageView using Glide or any other library
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Glide.with(requireContext())
                                    .load(imageUrl)
                                    .into(profileImageView);
                        }

                    }
                } else {
                    // Handle error
                    // ...
                }
            });
        }

        String currentUserUid = user.getUid();
        db.collection("users")
                .document(currentUserUid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        document = task.getResult();
                        if (document != null && document.exists()) {
                            // 현재 사용자의 문서에서 bookMark 배열 가져오기
                            admin = (String)document.get("adminClub");

                            if (admin.isEmpty()) {
                                clubmanage.setVisibility(View.GONE);
                                // Toast.makeText(getActivity(), "adminClub이 비어있습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                clubmanage.setVisibility(View.VISIBLE);
                            }

                        }
                    } else {
                        Log.d("ClubPost", "Error getting document: ", task.getException());
                    }
                });

        passwordResetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PasswordResetActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        clubmanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ClubApplyWatchActivity.class);
                intent.putExtra("club_name", admin);
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
                            // TODO : 회원탈퇴 -> 정보를 지워야 하나...(일단 보류)
                            user.delete().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) Log.d("deleteuser", "회원 탈퇴 성공");
                            });

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