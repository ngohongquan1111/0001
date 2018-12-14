package com.example.phonglinh.myapplication.activity;

import android.content.Intent;
import android.database.Cursor;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.phonglinh.myapplication.R;
import com.example.phonglinh.myapplication.adapterlistview.AdapterListSmsRecived;
import com.example.phonglinh.myapplication.classdefine.CallLogClass;
import com.example.phonglinh.myapplication.classdefine.SmsClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class smsrecived_activity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    SearchView editsearch;
    AdapterListSmsRecived adapter;
    ListView listView;
    Button btnToCallLog, btnToSmsSent,btnThisActivity;
    int iposition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsrecived_activity);



        show();

        editsearch = (SearchView) findViewById(R.id.searchsmsrecived);
        editsearch.setOnQueryTextListener(this);

        btnToCallLog =(Button) findViewById(R.id.btnCallLog1);
        btnToCallLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(smsrecived_activity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        btnToSmsSent = (Button)findViewById(R.id.btnSmsSent1);
        btnToSmsSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(smsrecived_activity.this,smssent_activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(listLongClick);
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menusmsrecived,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String data=adapter.getinfo(iposition);
        String[] DataArr = data.trim().split("/");
        String[] phoneArr = DataArr[0].trim().split(" ");
        String sdt = phoneArr[2];
        switch (item.getItemId()){
            case R.id.showdatasr:
                Toast.makeText(this,data,Toast.LENGTH_LONG).show();
                return true;
            case R.id.btnreply:
                Intent myintent = new Intent(smsrecived_activity.this,reply_sms.class);
                myintent.putExtra("phoneNumber",sdt);
                startActivity(myintent);

        }
        return super.onContextItemSelected(item);
    }

    public AdapterView.OnItemLongClickListener listLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            iposition = position;
            return false;
        }
    };

    private void show(){

        listView = (ListView) findViewById(R.id.listsmsrecived);
        List<SmsClass> datalist = getListData();
        adapter = new AdapterListSmsRecived(this,datalist);
        listView.setAdapter(adapter);
    }

    private List<SmsClass> getListData() {
        List<SmsClass> list = new ArrayList<SmsClass>();
        Cursor c = getContentResolver().query(Telephony.Sms.Inbox.CONTENT_URI, null, null, null, null);
        int add = c.getColumnIndex(Telephony.Sms.Inbox.ADDRESS);
        int date = c.getColumnIndex(Telephony.Sms.Inbox.DATE);
        int body = c.getColumnIndex(Telephony.Sms.Inbox.BODY);
        int idsmsrc = c.getColumnIndex(Telephony.Sms.Inbox._ID);
        while (c.moveToNext()){
            String pNumber = c.getString(add);
            String Date = c.getString(date);
            String Body = c.getString(body);
            String Id = c.getString(idsmsrc);
            java.util.Date callDayTime = new Date(Long.valueOf(Date));
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm");
            String dateString =formatter.format(callDayTime);

            SmsClass smsClass = new SmsClass("Người gửi: "+pNumber,"Thời gian: "+dateString,"Nội dung: "+Body,Id);
            list.add(smsClass);
        }
        return list;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }
}
