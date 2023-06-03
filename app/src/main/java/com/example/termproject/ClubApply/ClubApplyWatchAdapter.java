package com.example.termproject.ClubApply;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termproject.R;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.List;

import kr.co.prnd.readmore.ReadMoreTextView;

public class ClubApplyWatchAdapter extends RecyclerView.Adapter<ClubApplyWatchAdapter.WatchViewHolder> {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<String> items;

    public ClubApplyWatchAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public WatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WatchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.clubapply_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WatchViewHolder holder, int position) {
        String item = items.get(position);
        DocumentReference docRef = db.collection("club_apply").document(item);

        // DB에서 정보 불러오기
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc = task.getResult();

                holder.setTimeView(doc.getTimestamp("applyTime"));
                holder.applyText.setText(doc.getString("introduceText"));

                String curUserID = doc.getString("userId");

                db.collection("users").document(curUserID).get().addOnCompleteListener(userTask -> {
                    if(userTask.isSuccessful()) {
                        DocumentSnapshot userDoc = userTask.getResult();

                        holder.userNameView.setText(userDoc.getString("name"));

                        String major = userDoc.getString("department");
                        String schoolNumber = userDoc.getString("schoolNum");

                        holder.userInfoView.setText(major + " " + schoolNumber.substring(0, 2));

                        //============ DB에서 정보 가져오기 종료 ============//

                    } else Log.d("ClubApplyWatchAdapter", "DB에서 불러오기 실패");
                }).addOnFailureListener(e -> {
                    Log.e("ClubApplyWatchAdapter", "유저 정보 불러오기 중 오류", e);
                });
            } else Log.d("ClubApplyWatchAdapter", "DB에서 불러오기 실패");
        }).addOnFailureListener(e -> {
            Log.e("ClubApplyWatchAdapter", "정보 불러오기 중 오류", e);
        });

        // DB에서 정보 삭제하기
        holder.deleteBtn.setOnClickListener(view -> {
            docRef.delete().addOnSuccessListener(unused -> {
                Log.d("ClubApplyWatchAdapter", "DB에서 해당 문서 삭제 완료");
            }).addOnFailureListener(e -> {
                Log.e("ClubApplyWatchAdapter", "정보 삭제 중 오류", e);
            });
            items.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class WatchViewHolder extends RecyclerView.ViewHolder {
        private TextView userNameView, userInfoView, timeView, applyText, deleteBtn;

        public WatchViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameView = (TextView) itemView.findViewById(R.id.clubapply_item_name);
            userInfoView = (TextView) itemView.findViewById(R.id.clubapply_item_subInfo);
            timeView = (TextView) itemView.findViewById(R.id.clubapply_item_time);
            applyText = (TextView) itemView.findViewById(R.id.clubapply_item_applyText);
            deleteBtn = (TextView) itemView.findViewById(R.id.clubapply_item_delete);
        }

        public void setTimeView(Timestamp timestamp) {
            timeView.setText(new SimpleDateFormat("yy/MM/dd HH:mm").format(timestamp.toDate()));
        }
    }
}