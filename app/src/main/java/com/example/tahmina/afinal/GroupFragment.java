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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment {


    DBHelper mydb;
    private ListView listview;

    public GroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_group, container, false);

        mydb = new DBHelper(getContext());
        ArrayList array_list = mydb.getAllGroup();

        ArrayAdapter arrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_expandable_list_item_1, array_list);

        listview = (ListView)view.findViewById(R.id.list);
        listview.setAdapter(arrayAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = arg2 + 1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(getContext(), UpdateGroupActivity.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
        return view;
    }

}
