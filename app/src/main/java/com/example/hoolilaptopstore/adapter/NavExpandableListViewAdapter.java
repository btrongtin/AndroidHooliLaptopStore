package com.example.hoolilaptopstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoolilaptopstore.R;
import com.example.hoolilaptopstore.model.NavItem;
import com.example.hoolilaptopstore.model.ThuongHieu;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class NavExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    private HashMap<NavItem, List<ThuongHieu>> listChild;
    private List<NavItem> headerList;

    public NavExpandableListViewAdapter(Context context, HashMap<NavItem, List<ThuongHieu>> listChild, List<NavItem> headerList) {
        this.context = context;
        this.listChild = listChild;
        this.headerList = headerList;
    }

    @Override
    public int getGroupCount() {
        return headerList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        return listChild.get(headerList.get(groupPosition)).size();
        if (this.listChild.get(this.headerList.get(groupPosition)) == null)
            return 0;
        else
            return this.listChild.get(this.headerList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headerList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChild.get(headerList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public class ViewHolder{
        TextView txtHeaderTitle;
        ImageView headerImg;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        //String headerTitle = headerList.get(groupPosition);
        //String headerTitle = (String) getGroup(groupPosition);
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expand_nav_group_item, null);
            viewHolder.txtHeaderTitle = convertView.findViewById(R.id.groupTitle);
            viewHolder.headerImg = convertView.findViewById(R.id.groupImage);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NavItem navItem = (NavItem) getGroup(groupPosition);
        viewHolder.txtHeaderTitle.setText(navItem.getTitle());
        //Picasso.with(context).load(navItem.getImage()).into(viewHolder.headerImg);
        viewHolder.headerImg.setImageResource(navItem.getImage());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        //String headerTitle = headerList.get(groupPosition);
        //String headerTitle = (String) getGroup(groupPosition);
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expand_nav_child_item, null);
            viewHolder.txtHeaderTitle = convertView.findViewById(R.id.childTitle);
            viewHolder.headerImg = convertView.findViewById(R.id.childImage);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ThuongHieu thuongHieu = (ThuongHieu) getChild(groupPosition, childPosition);
        viewHolder.txtHeaderTitle.setText(thuongHieu.getTen());
        Picasso.with(context).load(thuongHieu.getHinhAnh())
                .into(viewHolder.headerImg);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
