package com.example.phonglinh.myapplication.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonglinh.myapplication.R;

public class reply_sms extends AppCompatActivity {
Button btnsent;
EditText etSms;
TextView tvPhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_sms);

        Intent myIntent = getIntent();
        final String phoneNumber1 = myIntent.getStringExtra("phoneNumber");
        tvPhoneNumber = (TextView) findViewById(R.id.txtreplyto);
        tvPhoneNumber.setText(phoneNumber1);
        etSms =(EditText) findViewById(R.id.etsmscontent);
        final String smsContent = etSms.getText().toString();

        btnsent = (Button)findViewById(R.id.btnsentSms);
        btnsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber1, null, smsContent, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();

                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
