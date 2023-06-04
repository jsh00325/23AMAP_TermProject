package com.example.termproject.ClubPage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ClubPost extends Fragment {

    String name;
    boolean isManager;
    DocumentReference clubRef;
    int postCnt, cmp;
    private RecyclerView recyclerView;
    private ClubPostListAdapter adapter;
    private List<String> imageUrlList;
    private FirebaseFirestore db;

    public static ClubPost newInstance(int number, String name) {
        ClubPost clubpostFragment = new ClubPost();
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
        bundle.putString("name", name);
        clubpostFragment.setArguments(bundle);
        return clubpostFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            int num = getArguments().getInt("number");
            name = getArguments().getString("name");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.clubpage_fragment_post, container, false);
        db = FirebaseFirestore.getInstance();
        // 리사이클러뷰 초기화
        recyclerView = view.findViewById(R.id.clubpost_recycle);
        imageUrlList = new ArrayList<>();


        db.collection("club_post")
                .whereEqualTo("club_name", name)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        imageUrlList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            List<String> imageUrls = (List<String>) document.get("imageURL");
                            if (imageUrls != null ) {
                                imageUrlList.add(imageUrls.get(0));
                            }
                        }
                        adapter = new ClubPostListAdapter(imageUrlList);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                    } else {
                        Log.d("ClubPost", "Error getting documents: ", task.getException());
                    }
                });


        return view;
    }
}
