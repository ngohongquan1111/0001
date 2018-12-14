package com.example.phonglinh.myapplication.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.phonglinh.myapplication.R;
import com.example.phonglinh.myapplication.adapterlistview.AdapterListCallLog;
import com.example.phonglinh.myapplication.classdefine.CallLogClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    SearchView editsearch;
    AdapterListCallLog adapter;
    ListView listView;
    String sItemClicked;
    int iposition;
    Button btnDen,btnDi,btnNho,btnTatca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDen = (Button)findViewById(R.id.callLogIncome);
        btnDen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.filterbyType(1);
            }
        });

        btnTatca =(Button) findViewById(R.id.callLogAll);
        btnTatca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });

        btnDi = (Button)findViewById(R.id.callLogOutGoing);
        btnDi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.filterbyType(2);
            }
        });
        btnNho=(Button)findViewById(R.id.callLongMissedCall);
        btnNho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.filterbyType(3);
            }
        });


        Button btnSmsRecived =(Button) findViewById(R.id.tabSmsRecived);
        SearchView searchView = findViewById(R.id.search);
        btnSmsRecived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,smsrecived_activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        Button btnSmsSent =(Button) findViewById(R.id.tabSmsSent);
        btnSmsSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,smssent_activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
       show();

        editsearch = (SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);

         listView.setOnItemClickListener(listclick);
         listView.setOnItemLongClickListener(listlongclick);
         registerForContextMenu(listView);

//        if (ContextCompat.checkSelfPermission(MainActivity.this,
//                Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
//                    Manifest.permission.READ_CALL_LOG)) {
//                ActivityCompat.requestPermissions(MainActivity.this,
//                        new String[]{Manifest.permission.READ_CALL_LOG}, 2);
//            } else {
//                ActivityCompat.requestPermissions(MainActivity.this,
//                        new String[]{Manifest.permission.READ_CALL_LOG}, 2);
//            }
//        } else {
//            List<CallLogClass> image_details = getListData();
//            final ListView listView = (ListView) findViewById(R.id.listCallLog);
//            listView.setAdapter(new AdapterListCallLog(this, image_details));
//        }

//

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_longclickcalllog,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String itemclicked = adapter.GetInfo(iposition);
        String[] phonelog = itemclicked.trim().split("/");
        int idphonelog = Integer.parseInt(phonelog[5]);
        String[] phoneNumberarr = phonelog[1].trim().split(" ");
        String sdt = phoneNumberarr[3];
        switch(item.getItemId()){
            case R.id.testDataLongClick:
           Toast.makeText(this,itemclicked,Toast.LENGTH_LONG).show();
                return true;
            case R.id.callThisContact:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+sdt));
                startActivity(callIntent);

                default:    return super.onContextItemSelected(item);
        }

    }

    public AdapterView.OnItemLongClickListener listlongclick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            iposition = position;
            return false;
        }
    };

    public void show(){
        listView = (ListView) findViewById(R.id.listCallLog);
        List<CallLogClass> datalist = getListData();
        adapter = new AdapterListCallLog(this,datalist);
        listView.setAdapter(adapter);
    }


    public AdapterView.OnItemClickListener listclick = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sItemClicked= adapter.GetInfo(position);
                Intent intent = new Intent(MainActivity.this,PhoneLogInfo_Activity.class);
                intent.putExtra("sItemInfo",sItemClicked);
                startActivity(intent);

            }
        };


//        private void searchFn(String keyword) {
//            List<CallLog> list = new ArrayList<>();
//            for (CallLog callLog : list) {
//                if (callLog.getPhoneNumber().equals(keyword))
//                    list.add(callLog);
//            }
//            filterData.clear();
//            filterData.addAll(list);
//            customListAdapter.notifyDataSetChanged();
//        }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        int i = requestCode;
//        String s = Integer.toString(i);
//        Log.e(log,s);
//        switch (requestCode) {
//            case 2: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ContextCompat.checkSelfPermission(MainActivity.this,
//                            Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
//                        List<CallLogClass> image_details = getListData();
//                        final ListView listView = (ListView) findViewById(R.id.listCallLog);
//                        listView.setAdapter(new AdapterListCallLog(this, image_details));
//                    }
//                } else {
//                    Toast.makeText(this, "NO PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
//
//                }
//                return;
//            }
//
//        }
//    }

    private List<CallLogClass> getListData() {
        List<CallLogClass> list = new ArrayList<CallLogClass>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
        }
        Cursor c = getContentResolver().query(android.provider.CallLog.Calls.CONTENT_URI, null, null, null, null);

        int number = c.getColumnIndex(android.provider.CallLog.Calls.NUMBER);
        int type = c.getColumnIndex(android.provider.CallLog.Calls.TYPE);
        int date = c.getColumnIndex(android.provider.CallLog.Calls.DATE);
        int duration = c.getColumnIndex(android.provider.CallLog.Calls.DURATION);
        int name = c.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME);
        int id = c.getColumnIndex(CallLog.Calls._ID);

        while (c.moveToNext()) {
            String pNumber = c.getString(number);
            String cDuration =c.getString(duration) ;
            String pName = c.getString(name);
            String stype = c.getString(type);
            String sIDCall = c.getString(id);
            String Date = c.getString(date);
            java.util.Date callDayTime = new Date(Long.valueOf(Date));
           SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String dateString = formatter.format(callDayTime);
            String dir = null;
            int dircode = Integer.parseInt(stype);
            switch (dircode) {
                case android.provider.CallLog.Calls.OUTGOING_TYPE:
                    dir = "cuộc gọi đi";
                    break;
                case android.provider.CallLog.Calls.INCOMING_TYPE:
                    dir = "cuộc gọi đến";
                    break;
                case android.provider.CallLog.Calls.MISSED_TYPE:
                    dir = "cuộc gọi nhỡ";
                    break;
            }
            String thoigian=null;
            int phut=0;
            int giay=0;
            int time= Integer.parseInt(cDuration);
            if(time<60){
                thoigian = "0 phút "+time +" giây";
            }else if(time>=60&&time<3600){

                phut=time/60;
                giay=time-(phut*60);
                thoigian= phut+" phút "+giay+" giây";
            }

            if(pName==null)
            {
                pName="Số lạ";
            }

            CallLogClass callLog = new CallLogClass("Người gọi: "+pName,"Số điện thoại: "+pNumber,dir,"Thời lượng: "+thoigian,dateString,sIDCall);
            list.add(callLog);
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
