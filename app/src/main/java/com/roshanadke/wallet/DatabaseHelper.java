package com.roshanadke.wallet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="practice.db";
    private static final String TABLE_NAME="wallet";
    //private static final String TEST="cutomer";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

        // SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " +TABLE_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT,CATEGORY TEXT,NOTE TEXT,AMT TEXT)");
        //sqLiteDatabase.execSQL("CREATE TABLE " +TEST+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,MOBILE_NUMBER INTEGER,EMAIL TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //===============Insert Data=======================
    public  boolean insertData(String date,String cate,String note,String amt)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("DATE",date);
        contentValues.put("CATEGORY",cate);
        contentValues.put("NOTE",note);
        contentValues.put("AMT",amt);
        Log.d("SSSS",amt);

        Long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

        if (result==-1)

            return  false;
        else
            return true;
    }

    //==============Select data============================
    public Cursor getData(String cate)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        String query ="SELECT * FROM " +TABLE_NAME+ " WHERE CATEGORY LIKE '" + cate + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        return cursor;
    }


    public Cursor showData()
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        //  String query =("SELECT * FROM " +TABLE_NAME+ );
        String query="SELECT * FROM " +TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        return cursor;
    }
    public Cursor getData2(String date2)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        String query ="SELECT * FROM " +TABLE_NAME+ " WHERE DATE LIKE '" + date2 + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        return cursor;
    }


}
