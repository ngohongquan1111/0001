package com.example.phonglinh.myapplication.adapterlistview;

import android.content.Context;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.phonglinh.myapplication.R;
import com.example.phonglinh.myapplication.classdefine.CallLogClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterListCallLog extends BaseAdapter {
    private List<CallLogClass> listDataCallLog;
    private ArrayList<CallLogClass> arraylist;
    private LayoutInflater layoutInflater;
    private Context context;
    String sInfo;

    public AdapterListCallLog(Context aContext, List<CallLogClass> listData) {
        this.context = aContext;
        this.listDataCallLog = listData;
        this.arraylist= new ArrayList<CallLogClass>();
        this.arraylist.addAll(listData);
        layoutInflater = LayoutInflater.from(aContext);

    }

    @Override
    public int getCount() {
        return listDataCallLog.size();
    }

    @Override
    public Object getItem(int position) {
        return listDataCallLog.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_listviewcalllog, null);
            holder = new ViewHolder();
            holder.phoneNameView = (TextView) convertView.findViewById(R.id.txtName);
            holder.phoneNumberView = (TextView) convertView.findViewById(R.id.txtPnumber);
            holder.callTypeView = (TextView) convertView.findViewById(R.id.txtCallType);
            holder.DurationView = (TextView) convertView.findViewById(R.id.txtDuration);
            holder.TimeCallView = (TextView) convertView.findViewById(R.id.txtTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CallLogClass callLogClass = this.listDataCallLog.get(position);
        holder.phoneNameView.setText(callLogClass.getPhoneName());
        holder.phoneNumberView.setText(callLogClass.getPhoneNumber());
        holder.callTypeView.setText(callLogClass.getCallType());
        holder.DurationView.setText(callLogClass.getDuration());
        holder.TimeCallView.setText(callLogClass.getTimeCall());


        return convertView;
    }

    static class ViewHolder {
        TextView phoneNameView;
        TextView phoneNumberView;
        TextView callTypeView;
        TextView DurationView;
        TextView TimeCallView;
    }

    public String GetInfo(int position){
        CallLogClass callLogClass = listDataCallLog.get(position);
        sInfo= callLogClass.getPhoneName()+"/"+callLogClass.getPhoneNumber()+"/"+callLogClass.getCallType()+"/"+callLogClass.getDuration()+"/"+callLogClass.getTimeCall()+"/"+callLogClass.getIDCall();
       return sInfo;
    }

    public void filterbyType(int type) {
        listDataCallLog.clear();
       if(type==1){
           for (CallLogClass wp : arraylist) {
               if (wp.getCallType().toLowerCase(Locale.getDefault()).contains("cuộc gọi đến")) {
                   listDataCallLog.add(wp);
               }
           }
       }else if(type ==2){
           listDataCallLog.clear();
           for (CallLogClass wp : arraylist) {
               if (wp.getCallType().toLowerCase(Locale.getDefault()).contains("cuộc gọi đi")) {
                   listDataCallLog.add(wp);
               }
           }
       }else if(type ==3){
           listDataCallLog.clear();
           for (CallLogClass wp : arraylist) {
               if (wp.getCallType().toLowerCase(Locale.getDefault()).contains("cuộc gọi nhỡ")) {
                   listDataCallLog.add(wp);
               }
           }
       }
        notifyDataSetChanged();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        listDataCallLog.clear();
        if (charText.length() == 0) {
            listDataCallLog.addAll(arraylist);
        } else {
            for (CallLogClass wp : arraylist) {
                if (wp.getPhoneNumber().toLowerCase(Locale.getDefault()).contains(charText)) {
                    listDataCallLog.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
    }
