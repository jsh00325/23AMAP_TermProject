package com.example.termproject.ClubPage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.termproject.ClubApply.ClubApplyActivity;
import com.example.termproject.MyPage.PasswordResetActivity;
import com.example.termproject.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ClubInfo extends Fragment {
    static String club_name;
    private FirebaseFirestore db;
    public static ClubInfo newInstance(int number, String name) {
        club_name = name;
        ClubInfo clubinfoFragment = new ClubInfo();
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
        bundle.putString("name", name);
        clubinfoFragment.setArguments(bundle);
        return clubinfoFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.clubpage_fragment_info, container, false);

        // 원하는 View들을 찾아서 대체
        ImageView clubImage = rootView.findViewById(R.id.club_image);
        TextView profileManage = rootView.findViewById(R.id.profile_manage);
        TextView clubInfoName = rootView.findViewById(R.id.club_info_name);
        Button clubInfoButton = rootView.findViewById(R.id.club_info_button);
        TextView clubInfoDetail = rootView.findViewById(R.id.club_info_detail);

        // DB에서 club_name을 이용하여 필요한 정보를 가져오는 작업 수행
        db = FirebaseFirestore.getInstance();
        Query query = db.collection("club_list").whereEqualTo("club_name", club_name);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    // 첫 번째로 일치하는 문서 가져오기
                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);

                    // 가져온 동아리 정보를 각 View에 설정
                    Glide.with(this).load(document.getString("image_url")).into(clubImage);
                    String mainCategory = document.getString("main_category");
                    String subCategory = document.getString("sub_category");
                    String profileText = mainCategory + " > " + subCategory;
                    profileManage.setText(profileText);
                    clubInfoName.setText(document.getString("club_name"));
                    //clubInfoSimple.setText(document.getString("simple_intro")); 없다
                    clubInfoButton.setText("신청 버튼");
                    clubInfoDetail.setText(document.getString("description").replace("\\n", "\n"));
                }
            } else {
                // 문서 가져오기 실패 처리
            }
        });
        clubInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ClubApplyActivity.class);
                intent.putExtra("club_name", club_name);
                view.getContext().startActivity(intent);
            }
        });

        return rootView;
    }
}