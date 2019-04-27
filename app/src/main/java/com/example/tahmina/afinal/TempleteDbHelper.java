package com.example.tahmina.afinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;


public class TempleteDbHelper {
    private myTempleteDbHelper myhelper;
    TempleteDbHelper(Context context)
    {
        myhelper = new myTempleteDbHelper(context);
    }
    long insertData(String name, String details)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myTempleteDbHelper.name, name);
        contentValues.put(myTempleteDbHelper.details, details);

        long id = dbb.insert(myTempleteDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }
    public String getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myTempleteDbHelper.UID,myTempleteDbHelper.name,myTempleteDbHelper.details};
        Cursor cursor =db.query(myTempleteDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myTempleteDbHelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(myTempleteDbHelper.name));
            String  details =cursor.getString(cursor.getColumnIndex(myTempleteDbHelper.details));
            buffer.append("name :"+name + "   details:" + details +"\n");
        }
        return buffer.toString();
    }
    public ArrayList<String> getAllGroup() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myTempleteDbHelper.UID,myTempleteDbHelper.name,myTempleteDbHelper.details};
        Cursor cursor =db.query(myTempleteDbHelper.TABLE_NAME,columns,null,null,null,null,null);

        while (cursor.moveToNext())
        {
            String name =cursor.getString(cursor.getColumnIndex(myTempleteDbHelper.name));
            array_list.add(name);
        }
        return array_list;
    }
    public ArrayList<String> getAllGroup2() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myTempleteDbHelper.UID,myTempleteDbHelper.name,myTempleteDbHelper.details};
        Cursor cursor =db.query(myTempleteDbHelper.TABLE_NAME,columns,null,null,null,null,null);

        while (cursor.moveToNext())
        {
            String name =cursor.getString(cursor.getColumnIndex(myTempleteDbHelper.details));
            array_list.add(name);
        }
        return array_list;
    }
    public String getDescription(String id) {
        String str="";
        String[] whereArgs ={id};
        SQLiteDatabase db = myhelper.getReadableDatabase();
        String[] columns = {myTempleteDbHelper.UID,myTempleteDbHelper.name,myTempleteDbHelper.details};
        Cursor cursor = (Cursor) db.query(myTempleteDbHelper.TABLE_NAME,columns,myTempleteDbHelper.UID+"=?",whereArgs,null,null,null,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        str =cursor.getString(cursor.getColumnIndex(myTempleteDbHelper.details));
        return str;
    }
    public  int delete(String uname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(myTempleteDbHelper.TABLE_NAME ,myTempleteDbHelper.name+" = ?",whereArgs);
        return  count;
    }

    public int upnamename(String oldname , String newname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myTempleteDbHelper.name,newname);
        String[] whereArgs= {oldname};
        int count =db.update(myTempleteDbHelper.TABLE_NAME,contentValues, myTempleteDbHelper.name+" = ?",whereArgs );
        return count;
    }

    static class myTempleteDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "BulkSms2.db";    // Database Name
        private static final String TABLE_NAME = "msgTemplete";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String UID="id";     // Column I (Primary Key)
        private static final String name = "name";    //Column II
        private static final String details= "details";    // Column III
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+name+" VARCHAR(255) ,"+details+" VARCHAR(3000));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public myTempleteDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();

            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Toast.makeText(context,"OnUpgrade",Toast.LENGTH_SHORT);
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();
            }
        }
    }
}