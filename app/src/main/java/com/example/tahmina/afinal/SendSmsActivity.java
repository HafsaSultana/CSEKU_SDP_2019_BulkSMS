package com.example.tahmina.afinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class SendSmsActivity extends AppCompatActivity {
    Button send,group,template;
    EditText EtGroup,EtMessage;
    ListView listView;
    String ConDetail[];

    ArrayList Grouplist;
    ArrayAdapter<String> adapter;
    DBHelper mydb;
    TempleteDbHelper tempdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
    }
}
