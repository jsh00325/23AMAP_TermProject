package com.example.termproject.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.termproject.Home.Feed.HomeFeedAdapter;
import com.example.termproject.Home.Filter.HomeFilterActivity;
import com.example.termproject.Post.PostActivity;
import com.example.termproject.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private View view;
    private ImageButton filterBtn;
    private RecyclerView homeFeed;
    private SwipeRefreshLayout homeSrl;
    private FloatingActionButton homeFab;
    private Context context;
    private List<String> filterList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.home_fragment, container, false);
        context = container.getContext();

        filterBtn = (ImageButton) view.findViewById(R.id.feed_filter_btn);
        filterBtn.setOnClickListener(curView -> {
            Intent it = new Intent(context, HomeFilterActivity.class);
            getActivity().startActivityForResult(it, 0);
        });

        homeFeed = (RecyclerView) view.findViewById(R.id.home_recycle_view);
        homeFeed.setLayoutManager(new LinearLayoutManager(container.getContext()));

        homeSrl = (SwipeRefreshLayout) view.findViewById(R.id.home_srl);
        homeSrl.setColorSchemeColors(getResources().getColor(R.color.Primary));
        homeSrl.setOnRefreshListener(() -> {
            loadHomeFeed();
            homeSrl.setRefreshing(false);
        });

        // TODO : 관리자 계정이 아니라면 숨기기
        homeFab = (FloatingActionButton) view.findViewById(R.id.home_fab_writePost);
        homeFab.setOnClickListener(view1 -> {
            Intent it = new Intent(context, PostActivity.class);
            getActivity().startActivityForResult(it, 0);
            // TODO : 받은 result로 저장되었다면 새로고침
        });
        
        loadHomeFeed();
        return view;
    }

    public void loadHomeFeed() {
        // 캐시에 저장된 필터링 정보 불러와서 filters에서 적용
        filterList = readCacheData("userFilterInfo");

        List<String> postDocumentIDs = new ArrayList<>();
        db.collection("club_post").whereIn("category", filterList).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                for (QueryDocumentSnapshot document : querySnapshot) {
                    postDocumentIDs.add(document.getId());
                }
            } else Log.d("HomeReadPost", "불러오기 실패");

            homeFeed.setAdapter(new HomeFeedAdapter(postDocumentIDs));
        });
    }

    private void saveCacheData(String fileName, List<String> data) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private List<String> readCacheData(String fileName) {
        List<String> dataList = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            dataList = (List<String>) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException fnfe) {
            // 파일이 존재하지 않을 때 -> 모두 존재하는 값으로 저장.
            dataList = new ArrayList<>(Arrays.asList("IT", "인문", "자연", "진로/발명/창업",
                    "봉사", "밴드", "악기", "노래", "연극", "미술", "요리", "댄스", "사진/영상",
                    "구기", "라켓", "무술", "익스트림 스포츠", "양궁", "게임", "기독교", "불교", "천주교"));
            saveCacheData(fileName, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }
}