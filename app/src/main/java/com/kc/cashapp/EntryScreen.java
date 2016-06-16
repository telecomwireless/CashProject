package com.kc.cashapp;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Calendar;

public class EntryScreen extends AppCompatActivity {
    Context context;


    EditText amount, category, date;
    Button save, delete;
    Dao dao = new Dao(this, 0);
    Cash cash = new Cash();
    public String pattern;
    private ArrayList<Cash> cashList;
    private ArrayList<Cash> filteredList;

    EntryScreen() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_screen);
        date = (EditText) findViewById(R.id.dateedit);
        amount = (EditText) findViewById(R.id.amountedit);
        category = (EditText) findViewById(R.id.textedit);
        save = (Button) findViewById(R.id.save);
        delete = (Button) findViewById(R.id.deletebutton);

    }


    EntryScreen(long id) {

        Log.i("entrt2", String.valueOf(id));
        dao = new Dao(this, id);
        //  dao.open();
        Log.i("entrt1", String.valueOf(id));

    }

    public void show(View v) {

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        // System.out.println("the selected " + mDay);
        DatePickerDialog dialog = new DatePickerDialog(EntryScreen.this,
                new mDateSetListener(), mYear, mMonth, mDay);
        dialog.show();

    }


    /* public void edit(View v) {

        dao.open();

        category.setText();

    }   */



    public void save(View v) {

        //to do something on the database it should be turned on first opened
        dao.open();

        if (category.getText().toString().length() == 0) {
            // Log.i("saved ", "date also inserted");
            category.setError("required!");
        }
        else if (amount.getText().toString().length() == 0) {
            // Log.i("saved ", "date also inserted");
            amount.setError("required");

        }
        else {
            String category1 = category.getText().toString();
            double amount1 = Double.parseDouble(amount.getText().toString());
            // int date1 = Integer.parseInt(date.getText().toString());
            cash.setCategory(category1);
            cash.setAmount(amount1);
            cash.setDate(result());
            dao.store(cash);
            Log.i("saved ", "date also inserted");
            //  dao.close();
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        }
    }


    class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // getCalender();
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            StringBuilder k = new StringBuilder();
            k.append(mMonth + 1).append("/").append(mDay).append("/").append(mYear);
            String j = String.valueOf(k);
            pattern = j;
            //  i = Integer.parseInt(j);
            date.setText(k);
            result();
        }
    }

    //used for converting strings to int
    public int result() {

        int length = pattern.length();
        int parsedInt = 0;
        StringBuilder stringBuilder = new StringBuilder(pattern);

        switch (length) {

            case 10:
                String date = pattern.replace("/", "");
                parsedInt = Integer.parseInt(date);

                break;

            case 9:

                //     if()
                break;

            case 8:

                String dataStore;
                dataStore = stringBuilder.insert(0, '0').insert(3, '0').toString();
                dataStore = dataStore.replace("/", "");
                parsedInt = Integer.parseInt(dataStore);

        }

        Log.i("hello", String.valueOf(parsedInt));
        return parsedInt;

    }

    public void delete(View v) {

    }

    }

