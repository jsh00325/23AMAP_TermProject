package com.example.termproject.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.termproject.R;

import java.util.List;
import java.util.Map;

public class CategoryExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> groupList;
    private Map<String, List<String>> childMap;

    public CategoryExpandableAdapter(Context context, List<String> groupList, Map<String, List<String>> childMap) {
        this.context = context;
        this.groupList = groupList;
        this.childMap = childMap;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        List<String> child = childMap.get(groupList.get(i));
        return (child == null ? 0 : child.size());
    }

    @Override
    public Object getGroup(int i) {
        return groupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        String group = groupList.get(i);
        List<String> children = childMap.get(group);
        return children != null ? children.get(i1) : null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.category_main_item, null);
        }
        TextView mainTextView = view.findViewById(R.id.category_main_TextView);
        String main = (String) getGroup(i);
        mainTextView.setText(main);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.category_sub_item, null);
        }
        TextView subTextView = view.findViewById(R.id.category_sub_TextView);
        String child = (String) getChild(i, i1);
        subTextView.setText(child);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
