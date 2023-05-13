package com.example.termproject.ClubPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import com.example.termproject.view.ExpandableHeightGridView;
import com.example.termproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link club_Bulletin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class club_Bulletin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ExpandableHeightGridView gridview = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public club_Bulletin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment club_Bulletin.
     */
    // TODO: Rename and change types and number of parameters

    public static club_Bulletin newInstance(int number, String name) {
        club_Bulletin postFragment = new club_Bulletin();
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
        bundle.putString("name", name);
        postFragment.setArguments(bundle);
        return postFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_club__bulletin, container, false);
    }
}