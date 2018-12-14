package com.example.phonglinh.myapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.phonglinh.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PhoneLogInfo_Activity extends AppCompatActivity {
    MainActivity mainActivity = new MainActivity();
    TextView textView1, textView2, textView3, textView4;
    Button button1, button2, button3;
    int count = 0,count1=0,count2=0,count3=0;
    String sdt;
    int IDcall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_log_info_);
        textView1 = (TextView) findViewById(R.id.txtphoneName);
        textView2 = (TextView) findViewById(R.id.txtphoneNumber1);
        textView3 = (TextView) findViewById(R.id.txtCallLogs);
        textView4 = (TextView) findViewById(R.id.txtsumcall);
        Intent myIntent = getIntent();
        String intentString = myIntent.getStringExtra("sItemInfo");
        String[] array1 = intentString.trim().split("/");
        IDcall = Integer.parseInt(array1[5]);
        textView1.setText(array1[0]);//Tên người gọi
        textView2.setText(array1[1]);//Số điẹn thoại
        textView3.setText(getCall(phoneValidator(array1[1])));
        sdt = phoneValidator(array1[1]);

        textView4.setText("Tổng số cuộc gọi: " + count);

        button1 = (Button) findViewById(R.id.btnDelete);
        button1.setOnClickListener(deleteCall);



        button3 = (Button) findViewById(R.id.btnCalling);
        button3.setOnClickListener(CallingNumber);
    }
//Tách phần "Số điện thoại"
    private String phoneValidator(String sPhoneNumber) {
        String PhoneNumber = "";
        String[] array = sPhoneNumber.trim().split(" ");
        PhoneNumber = array[3];
        return PhoneNumber;
    }


    private Button.OnClickListener deleteCall = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                String[] strNumberOne = {sdt};
                Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, CallLog.Calls.NUMBER + " = ? ", strNumberOne, "");
                boolean bol = cursor.moveToFirst();
                if (bol) {
                    do {
                        int idOfRowToDelete = cursor.getInt(cursor.getColumnIndex(CallLog.Calls._ID));
                        getContentResolver().delete(Uri.withAppendedPath(CallLog.Calls.CONTENT_URI, String.valueOf(idOfRowToDelete)), "", null);
                    } while (cursor.moveToNext());
                }
            } catch (Exception ex) {
                System.out.print("Exception here ");
            }
            Intent intent = new Intent(PhoneLogInfo_Activity.this, MainActivity.class);
            startActivity(intent);
        }

    };




    private Button.OnClickListener CallingNumber = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+sdt));
            startActivity(callIntent);
        }
    };


    private String getCall(String sdt) {
        StringBuffer sb = new StringBuffer();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
        }
        Cursor c = getContentResolver().query(android.provider.CallLog.Calls.CONTENT_URI, null, null, null, null);
        int number = c.getColumnIndex(android.provider.CallLog.Calls.NUMBER);
        int type = c.getColumnIndex(android.provider.CallLog.Calls.TYPE);
        int date = c.getColumnIndex(android.provider.CallLog.Calls.DATE);
        int duration = c.getColumnIndex(android.provider.CallLog.Calls.DURATION);
        int name = c.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME);
        sb.append("_________________________________________");

        while (c.moveToNext()) {
            String pNumber = c.getString(number);
            if(pNumber.equals(sdt)){
                count++;
                String cDuration =c.getString(duration) ;
                String stype = c.getString(type);
                String Date = c.getString(date);
                java.util.Date callDayTime = new Date(Long.valueOf(Date));
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                String dateString = formatter.format(callDayTime);
                String dir = null;
                int dircode = Integer.parseInt(stype);
                switch (dircode) {
                    case android.provider.CallLog.Calls.OUTGOING_TYPE:
                        dir = "Cuộc gọi đi";
                        count1++;

                        break;
                    case android.provider.CallLog.Calls.INCOMING_TYPE:
                        dir = "Cuộc gọi đến";
                         count2++;
                        break;
                    case android.provider.CallLog.Calls.MISSED_TYPE:
                        dir = "Cuộc gọi nhỡ";
                         count3++;
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

                sb.append("\nThời gian: "+dateString+ "\nThời lượng: "+thoigian+"\nLoại cuộc gọi:"+dir );
                sb.append("\n_________________________________________");}

        }
        c.close();
        return sb.toString();
    }
}
