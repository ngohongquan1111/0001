package com.example.phonglinh.myapplication.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.phonglinh.myapplication.classdefine.SmsClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class smssent_activity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    SearchView editsearch;
    AdapterListSmsRecived adapter;
    ListView listView;
    Button btnToCallLog, btnToSmsRecived,btnThisActivity;
    int iposition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smssent_activity);



         show();

         btnToCallLog = (Button)findViewById(R.id.TabCallLog);
         btnToCallLog.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(smssent_activity.this,MainActivity.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                 startActivity(intent);
             }
         });

        btnToSmsRecived = (Button)findViewById(R.id.TabRec);
        btnToSmsRecived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(smssent_activity.this,smsrecived_activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        editsearch = (SearchView) findViewById(R.id.searchpnsmssent);
        editsearch.setOnQueryTextListener(this);

        listView.setOnItemLongClickListener(listLongClick);
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu2,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String sgetdata = adapter.getinfo(iposition);
        String[] dataArr= sgetdata.trim().split("/");
        int idSms = Integer.parseInt(dataArr[3]) ;
        switch (item.getItemId()){
//            case R.id.deletesms:
//               Cursor c = getContentResolver().query(Telephony.Sms.Sent.CONTENT_URI, null, null, null, null);
//               while (c.moveToNext()){
//                   getContentResolver().delete(Uri.withAppendedPath(Telephony.Sms.Sent.CONTENT_URI, String.valueOf(idSms)), "", null);
//               }
//               show();
//               return  true;
            case R.id.showdata:
                Toast.makeText(this,sgetdata,Toast.LENGTH_LONG).show();
               default:
                   return super.onContextItemSelected(item);
        }

    }

    public AdapterView.OnItemLongClickListener listLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            iposition = position;
            return false;
        }
    };


    private void show(){

        listView = (ListView) findViewById(R.id.listsmssent);
        List<SmsClass> datalist = getListData();
        adapter = new AdapterListSmsRecived(this,datalist);
        listView.setAdapter(adapter);
    }

    private List<SmsClass> getListData() {
        List<SmsClass> list = new ArrayList<SmsClass>();
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
//        }
        Cursor c = getContentResolver().query(Telephony.Sms.Sent.CONTENT_URI, null, null, null, null);
        int add = c.getColumnIndex(Telephony.Sms.Sent.ADDRESS);
        int date = c.getColumnIndex(Telephony.Sms.Sent.DATE);;
        int body = c.getColumnIndex(Telephony.Sms.Sent.BODY);
        int smsID = c.getColumnIndex(Telephony.Sms.Sent._ID);
        while (c.moveToNext()){
            String pNumber = c.getString(add);
            String Date = c.getString(date);
            String Body = c.getString(body);
            String SmsId = c.getString(smsID);
            java.util.Date callDayTime = new Date(Long.valueOf(Date));
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm");
            String dateString =formatter.format(callDayTime);

            SmsClass smsClass = new SmsClass("Người nhận: "+pNumber,"Thời gian: "+dateString,"Nội dung: "+Body,SmsId);
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
