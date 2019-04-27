package com.example.tahmina.afinal;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class
HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter viewPagerAdapter ;
    String groupName,Description="";

    DBHelper helper;
    TempleteDbHelper temper;

    ArrayList<String> contactName;
    ArrayList<String>  contactNumber;
    ArrayList<String> CnName;
    ArrayList<String> CnNumber;
    ListView listView;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        helper = new DBHelper(this);
        temper = new TempleteDbHelper(this);

        EnableRuntimePermission();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tabLayout =(TabLayout) findViewById(R.id.tabLayout);
        viewPager =(ViewPager) findViewById(R.id.viewPager);

        viewPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new GroupFragment(), "Groups");
        viewPagerAdapter.addFragments(new ContactFragment(), "Contact");
        viewPagerAdapter.addFragments(new TempleteFragment(), "Template");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateGroups();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void onResume()
    {
        super.onResume();
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home2, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.createTemp) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            Intent intent =new Intent(this, ReadCsvActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.create_template) {
            CreateTemp();

        } else if (id == R.id.nav_send) {

            Intent intent =new Intent(this, SmsActivity.class);
            startActivity(intent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void CreateTemp()
    {
        View mView = LayoutInflater.from(this).inflate(R.layout.create_template, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(mView);

        final EditText EtTemplateTitle= (EditText) mView.findViewById(R.id.templateTitle);
        final EditText EtTemplateMsg= (EditText) mView.findViewById(R.id.templateMsg);

        builder
                .setCancelable(false)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        String tempTitle= EtTemplateTitle.getText().toString();
                        String tempMsg= EtTemplateMsg.getText().toString();
                        long flag=0;
                        flag= temper.insertData(tempTitle,tempMsg);
                        if(flag >=0)
                            onResume();
                            //recreateActivityCompat(HomeActivity.this);

                        else
                            Toast.makeText(getApplication(),"Failed",Toast.LENGTH_LONG).show();

                    }
                })

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void EnableRuntimePermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
            }
        }
    }
    public void CreateGroups()
    {
        View mView = LayoutInflater.from(this).inflate(R.layout.create_group, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(mView);

        final EditText EtGroupName = (EditText) mView.findViewById(R.id.groupName);
        final Button btnAddContact =(Button) mView.findViewById(R.id.AddContact) ;
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessContact();

            }
        });

        builder
                .setCancelable(false)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        groupName= EtGroupName.getText().toString();
                        long flag = helper.insertData(groupName,Description);
                        if(flag>=0)
                            onResume();
                            //recreateActivityCompat(HomeActivity.this);

                        else
                            Toast.makeText(getApplication(),"Failed",Toast.LENGTH_LONG).show();

                    }
                })

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void AccessContact()
    {
        final View mView = LayoutInflater.from(this).inflate(R.layout.select_contact, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(mView);
        listView= (ListView)mView.findViewById(R.id.list);
        getContact();
        builder
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        SelectOption();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void getContact()
    {
        Cursor phones = getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,null, null);
        CnName=new ArrayList<String>();
        CnNumber=new ArrayList<String>();
        while (phones.moveToNext())
        {
            String Name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String Number=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            CnName.add(Name);
            CnNumber.add(Number);
        }

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, CnName);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
    }
    public void SelectOption() {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        contactName = new ArrayList<String>();
        contactNumber = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
            {
                String snum=CnNumber.get(position);
                String sname=adapter.getItem(position);
                contactName.add(sname);
                contactNumber.add(snum);
                Description+=sname+"="+snum+",";
            }

        }
    }
}
