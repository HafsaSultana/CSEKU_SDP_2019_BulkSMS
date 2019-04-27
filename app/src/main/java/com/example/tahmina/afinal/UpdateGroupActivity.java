package com.example.tahmina.afinal;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class UpdateGroupActivity extends AppCompatActivity {

    private ListView listview;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_group);

        Intent intent=getIntent();
        Bundle dataBundle=intent.getExtras();
        String id = String.valueOf(dataBundle.getInt("id"));


        mydb = new DBHelper(this);
        String sall= mydb.getDescription(id);
        String Str[]=sall.split(",");


        listview = (ListView)findViewById(R.id.seeContact);


        List<HashMap<String,String>> itemDataList = new ArrayList<HashMap<String,String>>();
        for (int i=0;i<Str.length;i++)
        {
            HashMap<String,String> listItemMap = new HashMap<String,String>();
            String split[]=Str[i].split("=");
            listItemMap.put("name",split[0]);
            listItemMap.put("number",split[1]);
            itemDataList.add(listItemMap);
        }
        String[] from = new String[]{"name", "number"};
        int[] to = new int[]{android.R.id.text1, android.R.id.text2};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,itemDataList,android.R.layout.simple_list_item_2,from,to);
        listview.setAdapter(simpleAdapter);
    }
    public void onBackPressed() {
        super.onBackPressed();
    }

}
