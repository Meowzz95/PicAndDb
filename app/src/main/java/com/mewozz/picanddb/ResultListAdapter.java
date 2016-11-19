package com.mewozz.picanddb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jjzzz on 11/19/2016.
 */

public class ResultListAdapter extends BaseAdapter {
    private Context context;
    private List<People> list;

    public ResultListAdapter(Context context, List<People> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView==null){
            vh=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.adapter_result_list,parent,false);
            vh.img = (ImageView) convertView.findViewById(R.id.result_list_img);
            vh.name = (TextView) convertView.findViewById(R.id.result_list_tv_name);
            convertView.setTag(vh);
        }
        else
            vh= (ViewHolder) convertView.getTag();
        People current = list.get(position);

        vh.name.setText(current.getName());
        vh.img.setImageBitmap(current.getBmp());
        return convertView;
    }

    static class ViewHolder{
        TextView name;
        ImageView img;
    }
}
