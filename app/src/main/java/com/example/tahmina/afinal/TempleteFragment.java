package com.example.tahmina.afinal;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TempleteFragment extends Fragment {

    TempleteDbHelper mydb = null;
    private ListView listview;
    ArrayList<String> message;
    ArrayList<String> name;

    public TempleteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_templete, container, false);
        listview = (ListView)view.findViewById(R.id.list);

        mydb = new TempleteDbHelper(getContext());
        ArrayList<String> array_list = mydb.getAllGroup();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_expandable_list_item_1, array_list);

        listview = (ListView)view.findViewById(R.id.list);
        listview.setAdapter(arrayAdapter);



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = arg2 + 1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(getContext(), GroupSmsActivity.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    public void  avoid()
    {
        name = new ArrayList<>();
        message = new ArrayList<>();

        List<HashMap<String,String>> itemDataList = new ArrayList<HashMap<String,String>>();
        for (int i=0;i<name.size();i++)
        {
            HashMap<String,String> listItemMap = new HashMap<String,String>();
            listItemMap.put("name",name.get(i));
            listItemMap.put("message",message.get(i));
            itemDataList.add(listItemMap);
        }
        String[] from = new String[]{"name", "message"};
        int[] to = new int[]{android.R.id.text1, android.R.id.text2};
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),itemDataList,android.R.layout.simple_list_item_2,from,to);
        listview.setAdapter(simpleAdapter);

    }

}
