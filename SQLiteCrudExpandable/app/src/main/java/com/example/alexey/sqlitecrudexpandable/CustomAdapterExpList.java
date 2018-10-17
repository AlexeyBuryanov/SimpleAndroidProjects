package com.example.alexey.sqlitecrudexpandable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Alexey on 14.12.2017.
 * Кастомный адаптер ExpandableListView.
 */
public class CustomAdapterExpList extends BaseExpandableListAdapter
{
    private ArrayList<GroupElement> _group;
    private Context _context;

    CustomAdapterExpList(Context context, ArrayList<GroupElement> groups) {
        _group = groups;
        _context = context;
    } // CustomAdapterExpList


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ChildElement> productList =
                _group.get(groupPosition).getChildList();
        return productList.get(childPosition);
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<ChildElement> productList =
                _group.get(groupPosition).getChildList();
        return productList.size();
    }
    @Override
    public Object getGroup(int groupPosition) {
        return _group.get(groupPosition);
    }
    @Override
    public int getGroupCount() {
        return _group.size();
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder groupViewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(_context).inflate(R.layout.group_view, parent, false);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            // Если viewHolder уже есть, то он хранится в тэге
            // Таким образом экономим ресурсы на лишнюю инициализацию
            groupViewHolder = (GroupViewHolder)convertView.getTag();
        } // if-else

        final GroupElement item = (GroupElement)getGroup(groupPosition);
        groupViewHolder.iconGroup.setImageResource(item.get_icon());
        groupViewHolder.textGroup.setText(item.get_name());

        return convertView;
    } // getGroupView


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder childViewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(_context).inflate(R.layout.child_view, parent, false);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder)convertView.getTag();
        } // if-else

        final ChildElement item = (ChildElement)getChild(groupPosition, childPosition);
        childViewHolder.iconChild.setImageResource(item.get_icon());
        childViewHolder.tvName.setText(item.get_name());
        childViewHolder.tvPrice.setText(String.valueOf(item.get_price()));
        childViewHolder.tvType.setText(item.get_type());

        return convertView;
    } // getChildView


    /**
     * Класс для определения views группы.
     */
    private class GroupViewHolder {
        final ImageView iconGroup;
        final TextView textGroup;
        GroupViewHolder(View view) {
            iconGroup = view.findViewById(R.id.iconGroup);
            textGroup = view.findViewById(R.id.textGroup);
        } // GroupViewHolder ctor
    } // GroupViewHolder class
    /**
     * Класс для определения views подэлементов группы.
     */
    private class ChildViewHolder {
        final ImageView iconChild;
        final TextView tvName, tvPrice, tvType;
        ChildViewHolder(View view) {
            tvName = view.findViewById(R.id.tvName);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvType = view.findViewById(R.id.tvType);
            iconChild = view.findViewById(R.id.iconChild);
        } // ChildViewHolder ctor
    } // ChildViewHolder class
} // CustomAdapterExpList