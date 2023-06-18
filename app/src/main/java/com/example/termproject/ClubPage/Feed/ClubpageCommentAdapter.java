package com.example.termproject.ClubPage.Feed;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termproject.R;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.List;

public class ClubpageCommentAdapter extends RecyclerView.Adapter<ClubpageCommentAdapter.CommentViewHolder> {
    private List<String> items;
    private Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public ClubpageCommentAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CommentViewHolder(LayoutInflater.from(context).inflate(R.layout.clubpage_item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        String item = items.get(position);
        holder.layout.setVisibility(View.GONE);

        DocumentReference docRef = db.collection("comment").document(item);
        // DB에서 댓글 가져와서 정보 넣기
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc = task.getResult();

                if (doc.getLong("reportCount") >= 10) {
                    holder.mainText.setText("해당 댓글은 신고를 받아 삭제되었습니다.");
                    holder.mainText.setTextColor(Color.GRAY);
                    holder.reportBtn.setVisibility(View.GONE);
                    if (doc.getString("userID").equals(user.getUid()))
                        holder.deleteBtn.setVisibility(View.VISIBLE);
                } else {
                    holder.mainText.setText(doc.getString("comment"));

                    holder.setTimeText(doc.getTimestamp("time"));
                    if (doc.getString("userID").equals(user.getUid())) {
                        holder.deleteBtn.setVisibility(View.VISIBLE);
                        holder.reportBtn.setVisibility(View.GONE);
                    }
                }

                holder.layout.setVisibility(View.VISIBLE);
            } else Log.d("ClubpageCommentAdapter", "DB에서 불러오기 실패");
        }).addOnFailureListener(e -> {
            Log.e("ClubpageCommentAdapter", "정보 불러오기 중 오류", e);
        });

        // 삭제 버튼 리스너
        holder.deleteBtn.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("댓글 삭제").setMessage("이 댓글을 삭제하시겠습니까?");

            builder.setPositiveButton("네", (dialogInterface, i) -> {
                docRef.delete().addOnSuccessListener(unused -> {
                    Log.d("ClubpageCommentAdapter", "DB에서 해당 문서 삭제 완료");
                }).addOnFailureListener(e -> {
                    Log.e("ClubpageCommentAdapter", "정보 삭제 중 오류", e);
                });
                items.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

            }).setNegativeButton("아니오", (dialogInterface, i) -> {

            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        // 신고 버튼 리스너
        holder.reportBtn.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("댓글 신고").setMessage("이 댓글을 신고하시겠습니까?");

            builder.setPositiveButton("네", (dialogInterface, i) -> {
                docRef.update("reportCount", FieldValue.increment(1));
            }).setNegativeButton("아니오", (dialogInterface, i) -> {
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layout;
        private TextView deleteBtn, mainText, timeText, reportBtn;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.comment_item_mainView);
            deleteBtn = (TextView) itemView.findViewById(R.id.comment_item_deleteBtn);
            reportBtn = (TextView) itemView.findViewById(R.id.comment_item_reportBtn);
            mainText = (TextView) itemView.findViewById(R.id.comment_item_mainText);
            timeText = (TextView) itemView.findViewById(R.id.comment_item_timestamp);
        }

        private void setTimeText(Timestamp timestamp) {
            timeText.setText(new SimpleDateFormat("yy/MM/dd HH:mm").format(timestamp.toDate()));
        }
    }
}