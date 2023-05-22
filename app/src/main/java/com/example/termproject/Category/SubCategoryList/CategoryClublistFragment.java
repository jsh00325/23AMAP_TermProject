package com.example.termproject.Category.SubCategoryList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termproject.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CategoryClublistFragment extends Fragment {
    public FirebaseFirestore db;
    public View view;
    public RecyclerView clubList;
    public ShimmerFrameLayout clubListLoading;
    public String clubSubKey = "empty";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.category_fragment_clublist, container, false);

        db =  FirebaseFirestore.getInstance();

        clubListLoading = (ShimmerFrameLayout) view.findViewById(R.id.category_clubList_load);

        clubList = (RecyclerView) view.findViewById(R.id.category_clubList_RV);
        clubList.setLayoutManager(new LinearLayoutManager(container.getContext()));

        if (getArguments() != null) clubSubKey = getArguments().getString("clubSubKey");

        ArrayList<CategoryClubData> clubNameList = new ArrayList<>();

        showShimmer();
        db.collection("club_list").whereEqualTo("sub_category", clubSubKey).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot doc : task.getResult()) {
                    String imgURL = doc.getString("image_url");
                    String name = doc.getString("club_name");
                    String main_cat = doc.getString("main_category");
                    String sub_cat = doc.getString("sub_category");
                    clubNameList.add(new CategoryClubData(imgURL, name, main_cat, sub_cat));
                }
                clubList.setAdapter(new CategoryClublistAdapter(clubNameList));
            } else {
                Toast.makeText(container.getContext(), "불러오기 에러!", Toast.LENGTH_SHORT).show();
            }
            hideShimmer();
        });

        return view;
    }

    public void showShimmer() {
        clubListLoading.startShimmer();
        clubListLoading.setVisibility(View.VISIBLE);
        clubList.setVisibility(View.GONE);
    }

    public void hideShimmer() {
        clubListLoading.stopShimmer();
        clubListLoading.setVisibility(View.GONE);
        clubList.setVisibility(View.VISIBLE);
    }
}
