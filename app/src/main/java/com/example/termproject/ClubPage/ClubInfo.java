package com.example.termproject.ClubPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.termproject.R;

public class ClubInfo extends Fragment {

    public static ClubInfo newInstance(int number, String name) {
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
        return inflater.inflate(R.layout.clubpage_fragment_info, container, false);
    }
}