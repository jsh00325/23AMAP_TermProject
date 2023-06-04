package com.example.termproject.BookMark;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.termproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookmarkFragment extends Fragment {

    RecyclerView recyclerView;
    Context mContext;
    BookmarkRecyclerViewAdapter bookmarkRecyclerViewAdapter;
    private FirebaseUser user;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentSnapshot userDoc;
    DocumentReference userRef;
    ArrayList<String>  bookMark = new ArrayList<>();
    ArrayList<BookmarkItem> bookmarkItemList = new ArrayList<>();
    int tenCnt = 0;

    public BookmarkFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bookmark_fragment, container, false);
        mContext = container.getContext();
        bookMark.clear();
        user = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = view.findViewById(R.id.fragment_bookmark_recyclerview);
        recyclerView.setHasFixedSize(true); //기존 성능 강화

        db.collection("users").document(user.getUid()).get().
                addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            userDoc = task.getResult();
                            userRef = userDoc.getReference();
                            if(!userDoc.exists()){
                                db.collection("club_list").document(user.getUid()).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.isSuccessful()){
                                                    Log.d("user", "동아리입니다");
                                                    userDoc = task.getResult();
                                                    userRef = userDoc.getReference();
                                                    getBookMarkClubList();
                                                }
                                            }
                                        });
                            }
                            else {
                                Log.d("user", "유저입니다");
                                getBookMarkClubList();
                            }
                        }
                        else Log.d("task", "실패");
                    }
                });
        return view;
    }

    public void getBookMarkClubList(){
        bookMark = (ArrayList<String>) userDoc.get("bookMark");
        bookmarkItemList.clear();
        ArrayList<String> clubList = new ArrayList<>();
        //파이썬 dict 같음

            for(String club : bookMark){
                clubList.add(club);
            }

        Log.d("user", "여기까진 왔다..1");
        tenCnt = clubList.size() / 10;
        if(clubList.size() % 10 == 0) tenCnt--;
        Log.d("user", "여기까지 왔다...3");

        if(tenCnt > 0) {
            for (int i = 1; i <= tenCnt; i++) {
                List<String> now = clubList.subList(10 * (i - 1), 10 * i - 1);
                db.collection("club_list").whereIn("club_name", now).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        bookmarkItemList.add(new BookmarkItem(
                                                (String) document.get("image_url"), (String) document.get("club_name"),
                                                (String) document.get("main_category"), (String) document.get("sub_category"), document.getReference()));
                                    }
                                }
                            }
                        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (bookmarkItemList.size() == tenCnt * 10) {
                                    List<String> now = clubList.subList(10 * tenCnt, clubList.size());
                                    db.collection("club_list").whereIn("club_name", now).get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            bookmarkItemList.add(new BookmarkItem(
                                                                    (String) document.get("image_url"), (String) document.get("club_name"),
                                                                    (String) document.get("main_category"), (String) document.get("sub_category"),
                                                                    document.getReference()));

                                                            if(bookmarkItemList.size() == clubList.size()){
                                                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                                                bookmarkRecyclerViewAdapter = new BookmarkRecyclerViewAdapter(bookmarkItemList, userRef, mContext);
                                                                recyclerView.setAdapter(bookmarkRecyclerViewAdapter);
                                                            }
                                                        }
                                                    }
                                                }
                                            });
                                }
                            }
                        });
            }
        }
        else if(clubList.size() > 0){
            List<String> now = clubList.subList(0, clubList.size());
            db.collection("club_list").whereIn("club_name", now).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    bookmarkItemList.add(new BookmarkItem(
                                            (String) document.get("image_url"), (String) document.get("club_name"),
                                            (String) document.get("main_category"), (String) document.get("sub_category"),
                                            document.getReference()));

                                    if(bookmarkItemList.size() == clubList.size()){
                                        Log.d("user", "여기까지 왔다...2");
                                        Log.d("user", String.valueOf(clubList));
                                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                        bookmarkRecyclerViewAdapter = new BookmarkRecyclerViewAdapter(bookmarkItemList, userRef, mContext);
                                        recyclerView.setAdapter(bookmarkRecyclerViewAdapter);
                                    }
                                }
                            }
                        }
                    });
        }
        else{

        }
    }
}