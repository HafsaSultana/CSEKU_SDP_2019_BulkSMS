package com.example.tahmina.afinal;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadCsvActivity extends AppCompatActivity {
    Button readButton,sendButton;
    EditText csvFileName,message;
    ArrayList<String> contactName;
    ArrayList<String>  contactNumber;
    ArrayList<String>  address;
    static boolean flag=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_csv);

        csvFileName = (EditText) findViewById(R.id.myInputText);
        message = (EditText) findViewById(R.id.message);
        readButton = (Button) findViewById(R.id.getExternalStorage);
        sendButton = (Button) findViewById(R.id.send);
        
        contactName= new ArrayList<String>();
        contactNumber= new ArrayList<String>();
        address= new ArrayList<String>();

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReadPermissionGranted()) {
                    String filename = csvFileName.getText().toString();
                    File f = new File(Environment.getExternalStorageDirectory()
                            .getAbsolutePath() + File.separator + filename);
                    try {
                        readerCsv(f);
                        flag=true;
                        Toast.makeText(ReadCsvActivity.this,"read successfull",Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(ReadCsvActivity.this,"read unsuccessful",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
            }

        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sms = message.getText().toString();
                for(int i=0;i<contactNumber.size();i++)
                {
                    sms=sms.replace("XXX",contactName.get(i));
                    sms=sms.replace("ADD",address.get(i));
                    String num=contactNumber.get(i);
                    try{
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(num,null,sms,null,null);
                        Toast.makeText(ReadCsvActivity.this,sms,Toast.LENGTH_LONG).show();
                    }
                    catch (Exception e)
                    {
                        if (!flag)
                            Toast.makeText(ReadCsvActivity.this,"read file first",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(ReadCsvActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void readerCsv(File file) throws IOException {
        List<String[]> rows = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        String csvSplitBy = ",";

        br.readLine();
        while ((line = br.readLine()) != null) {
            String[] row = line.split(csvSplitBy);
            rows.add(row);
        }
        for (int i = 0; i < rows.size(); i++) {
            contactName.add(rows.get(i)[0]);
            String num= rows.get(i)[1];
            contactNumber.add("0"+num.trim());
            address.add(rows.get(i)[2]);
            //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isReadPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                // Log.v(TAG,"Permission is granted");
                return true;
            } else {

                // Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            // Log.v(TAG,"Permission is granted");
            return true;
        }
    }

}