package com.kc.cashapp;

// Excellent AdapterView tutorial  http://www.slideshare.net/KhaledAnaqwa1/android-training-adapterview-adapter
//An Adapter is responsible for creating and binding data to views. An Adapter isn't an actual view, but instead produces them.
// An AdapterView is a ViewGroup that gets its child views from an Adapter.
// a ListView has a child view for each row in its list.
// Those child views are created and bound with data by an Adapter.

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
//import android.support.v4.widget.SearchViewCompatIcs;
//import android.support.v4.widget.SearchViewCompatIcs;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener{
   Context context;
    ListView listView;
    // private ArrayAdapter<CharSequence> adapter;
    SearchView mSearchView;
    Dao dao;
    ArrayAdapter<Cash> arrayAdapter;
    CustomAdapter customAdapter;
   // SearchView searchView;
   public CustomAdapter customAdapter2;
    Boolean isClosed;
    ArrayList<Cash> arrayList;
    ImageView closeButton;
    Cash cash = new Cash();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


       // Log.i("month", month);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.list);


        dao = new Dao(this, 0);
        dao.open();
       // Log.i("getView", String.valueOf(customAdapter.getCount()));
        arrayList = dao.getData();
     //   Resources res = getResources();
     //   Log.i("first", "i am first");

        customAdapter =  new CustomAdapter(arrayList, this);
        listView.setAdapter(customAdapter);
        listView.setTextFilterEnabled(true);


                //    Log.i("count", String.valueOf(customAdapter.getItem()));
   /*    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               Log.i("position", String.valueOf(arrayList.get(position)));
            }
        });
*/
//Floating button

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent myIntent = new Intent(MainActivity.this, EntryScreen.class);
                MainActivity.this.startActivity(myIntent);

              //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
               //         .setAction("Action", null).show();
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      //  super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
      //  final MenuItem menuItem = D.findItem(R.id.action_search);
      //  menuItem.collapseActionView();
/*        MenuItemCompat.setOnActionExpandListener(null,new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                //Do whatever you want
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                //Do whatever you want
                return true;
            }
        });  */
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setOnQueryTextListener(this);
      //  mSearchView.setOnCloseListener(this);
            mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    Toast t = Toast.makeText(MainActivity.this, "close", Toast.LENGTH_SHORT);
                    t.show();

                    return false;
                }
            });



        /*   mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                listView.setAdapter(customAdapter);
                if(!queryTextFocused) {
                    menuItem.collapseActionView();
                    mSearchView.setQuery("", false);
                }
            }
        });  */


        return true;
    }





    @Override
    protected void onResume() {
        dao.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dao.close();
        super.onPause();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (TextUtils.isEmpty(query)) {
            customAdapter.getFilter().filter("");
            listView.clearTextFilter();
        } else {
            customAdapter.getFilter().filter(query.toString());
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    @Override
        public boolean onClose() {
            Log.i("onclose", "i was called");
            customAdapter.getFilter().filter("");
            return true;
        }






}



