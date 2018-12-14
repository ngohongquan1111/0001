package com.example.phonglinh.myapplication.adapterlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.phonglinh.myapplication.R;
import com.example.phonglinh.myapplication.classdefine.SmsClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterListSmsRecived extends BaseAdapter {
    private List<SmsClass> listDataSms;
    private ArrayList<SmsClass> arraylist;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterListSmsRecived(Context aContext, List<SmsClass> listData){
        this.context = aContext;
        this.listDataSms = listData;
        this.arraylist= new ArrayList<SmsClass>();
        this.arraylist.addAll(listData);
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return listDataSms.size();
    }

    @Override
    public Object getItem(int position) {
        return listDataSms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView phoneNumberView;
        TextView datetime;
        TextView content;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_listsmsrecived, null);
            holder = new ViewHolder();
            holder.phoneNumberView = (TextView) convertView.findViewById(R.id.txtsmsFrom);
            holder.datetime = (TextView) convertView.findViewById(R.id.txtdateTime);
            holder.content = (TextView) convertView.findViewById(R.id.txtcontent);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SmsClass smsClass = this.listDataSms.get(position);
        holder.phoneNumberView.setText(smsClass.getPhoneNumber());
        holder.datetime.setText(smsClass.getTime());
        holder.content.setText(smsClass.getContent());

        return convertView;
    }

    public String getinfo(int position){
        SmsClass smsClass = listDataSms.get(position);
        String data = smsClass.getPhoneNumber()+"/"+smsClass.getContent()+"/"+smsClass.getTime()+"/"+smsClass.getIdsms();
        return  data;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        listDataSms.clear();
        if (charText.length() == 0) {
            listDataSms.addAll(arraylist);
        } else {
            for (SmsClass wp : arraylist) {
                if (wp.getPhoneNumber().toLowerCase(Locale.getDefault()).contains(charText)) {
                    listDataSms.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
