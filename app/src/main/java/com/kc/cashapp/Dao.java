package com.kc.cashapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;

/**
 * Created by 5078682 on 5/20/2016.
 */
public class Dao extends SQLiteOpenHelper{
    ArrayList<Cash> arrayList = new ArrayList<>();
    private SQLiteDatabase sqLiteDatabase;
    private String allColumns[] = {COLUMN_ID, COLUMN_category, COLUMN_amount, COLUMN_date};
    long id;
    Context context;
    public static final String DATABASE_NAME =    "cash.db";
    public static final String TABLE_NAME =              "cash";
    public static final String COLUMN_ID =        "_id";
    public static final String COLUMN_category =  "category";
    public static final String COLUMN_amount =    "amount";
    public static final String COLUMN_date =      "date";
    private static final int   DATABASE_VERSION = 1;
    public  final String CREATE_TABLE =     " create table " + TABLE_NAME +
            "( " + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_category + " TEXT, " + COLUMN_amount + " REAL, " + COLUMN_date + " INTEGER ); ";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.i ("success", "successfully created database");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Dao.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Dao(Context context, long id) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
       // this.context = context;
        this.id = id;
        if(id >0) {

            delete(id);
        }
    }


    public void open() throws SQLException {
        sqLiteDatabase = this.getWritableDatabase();

    }

    public void close() {

        this.close();
    }



    public void store(Cash cash) {

        ContentValues contentValues = new ContentValues();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, allColumns, null, null, null, null, null);
        int k =0;
        while (cursor.moveToNext()) {
            Cash cash1 = cursorToComment(cursor);
            if (cash1.getCategory().toLowerCase().contains(cash.getCategory().toLowerCase())) {
                k++;
                Log.i("compare", String.valueOf(cursor.getLong(cursor.getColumnIndex("_id"))));
                ContentValues Values = new ContentValues();
                double temp = cash1.getAmount();
                temp += cash.getAmount();
                Values.put(COLUMN_amount, temp);
                sqLiteDatabase.update(TABLE_NAME, Values, COLUMN_ID + " = " + cursor.getLong(cursor.getColumnIndex("_id")), null);
                //  System.out.print( sqLiteDatabase.update(sqlHelper.TABLE_NAME, Values , sqlHelper.COLUMN_ID + " = " + cash1.getId() , null));
               // System.out.println(cash1.getAmount() + cash.getAmount());

            }


        }

    if(k==0) {
        contentValues.put(COLUMN_category, cash.getCategory());
        contentValues.put(COLUMN_amount, cash.getAmount());
        contentValues.put(COLUMN_date, cash.getDate());
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        Log.i("insert", String.valueOf(contentValues.get(COLUMN_date)));

    }
        }


    public ArrayList<Cash> getData(){
      Cursor cursor = sqLiteDatabase.query(TABLE_NAME, allColumns, null, null, null, null, null);
      while (cursor.moveToNext()) {
          Cash cash = cursorToComment(cursor);
          arrayList.add(cash);
      }
      cursor.close();
      return arrayList;
  }



public Cash cursorToComment(Cursor cursor){

    Cash cash = new Cash();
  //  cash.setId(cursor.getLong(0));
    cash.setCategory(cursor.getString(1));
    cash.setAmount(cursor.getDouble(2));
 //   Log.i("amount",cursor.getString(1) );
    cash.setDate(cursor.getInt(3));
   // Log.i("info",cursor.getString() );
   // Log.i("Date", String.valueOf(cursor.getInt(2)));
   return cash;
}


public void delete(long id) {
           //open();

            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME, COLUMN_ID + " = " + id , null);


    }
}


//("preferences", "ssid = '"+ssid+"'",null)