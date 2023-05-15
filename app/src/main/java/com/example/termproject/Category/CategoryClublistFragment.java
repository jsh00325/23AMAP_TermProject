package com.example.termproject.Category;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CategoryClublistFragment extends Fragment {
    public FirebaseFirestore db;
    public View view;
    public RecyclerView clubList;
    public String clubSubKey = "empty";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category_clublist, container, false);

        db =  FirebaseFirestore.getInstance();

        clubList = (RecyclerView) view.findViewById(R.id.category_clublist_RV);
        clubList.setLayoutManager(new LinearLayoutManager(container.getContext()));

        if (getArguments() != null) {
            clubSubKey = getArguments().getString("clubSubKey");
        }

        ArrayList<CategoryClubData> clubNameList = new ArrayList<>();
        db.collection("club_category").whereEqualTo("sub_category", clubSubKey).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult()) {
                        String imgURL = doc.getString("image_url");
                        String name = doc.getString("club_name");
                        String main_cat = doc.getString("main_category");
                        String sub_cat = doc.getString("sub_category_name");
                        clubNameList.add(new CategoryClubData(imgURL, name, main_cat, sub_cat));
                    }
                    clubList.setAdapter(new CategoryClublistAdapter(clubNameList));
                } else {
                    Toast.makeText(container.getContext(), "불러오기 에러!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
