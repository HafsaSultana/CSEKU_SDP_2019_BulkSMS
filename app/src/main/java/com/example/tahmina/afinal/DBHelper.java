package com.example.tahmina.afinal;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.widget.Toast;

        import java.util.ArrayList;


public class DBHelper {
    private myDbHelper myhelper;
    DBHelper(Context context)
    {
        myhelper = new myDbHelper(context);
    }
    long insertData(String name, String details)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.name, name);
        contentValues.put(myDbHelper.details, details);

        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }
    public String getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.name,myDbHelper.details};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.name));
            String  details =cursor.getString(cursor.getColumnIndex(myDbHelper.details));
            buffer.append("name :"+name + "   details:" + details +"\n");
        }
        return buffer.toString();
    }
    public ArrayList<String> getAllGroup() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.name,myDbHelper.details};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);

        while (cursor.moveToNext())
        {
            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.name));
            array_list.add(name);
        }
        return array_list;
    }
    public ArrayList<String> getAllGroup2() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.name,myDbHelper.details};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);

        while (cursor.moveToNext())
        {
            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.details));
            array_list.add(name);
        }
        return array_list;
    }
    public String getDescription(String id) {
        String str="";
        String[] whereArgs ={id};
        SQLiteDatabase db = myhelper.getReadableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.name,myDbHelper.details};
        Cursor cursor = (Cursor) db.query(myDbHelper.TABLE_NAME,columns,myDbHelper.UID+"=?",whereArgs,null,null,null,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        str =cursor.getString(cursor.getColumnIndex(myDbHelper.details));

        return str;
    }
    public  int delete(String uname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(myDbHelper.TABLE_NAME ,myDbHelper.name+" = ?",whereArgs);
        return  count;
    }

    public int upnamename(String oldname , String newname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.name,newname);
        String[] whereArgs= {oldname};
        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.name+" = ?",whereArgs );
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "BulkSms.db";    // Database Name
        private static final String TABLE_NAME = "contact";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String UID="id";     // Column I (Primary Key)
        private static final String name = "name";    //Column II
        private static final String details= "details";    // Column III
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+name+" VARCHAR(255) ,"+details+" VARCHAR(3000));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
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