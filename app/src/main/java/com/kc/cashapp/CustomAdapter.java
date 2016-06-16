package com.kc.cashapp;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by 5078682 on 5/20/2016.
 */
public class CustomAdapter extends BaseAdapter implements Filterable {

    LayoutInflater layoutInflater;
    Context context;
    ArrayList<Cash> cash = new ArrayList<>();
    Cash cash1 = new Cash();
    EntryScreen entryScreen;


    //  int i = 0;
    ArrayList<Cash> filteredList = new ArrayList<>();

    public CustomAdapter(ArrayList<Cash> cash, Context context) {
   /*   Log.i("customadapter", String.valueOf(cash.get(1)) +"   " + String.valueOf(cash.get(1))
                + "   "  +String.valueOf(cash.get(2)) +"    "   + String.valueOf(cash.get(3))
                + "   " +String.valueOf(cash.get(4)));   */

        this.cash = cash;
        this.context = context;
    }

    //getView calls this method to get the count of rows
    @Override
    public int getCount() {
        //this is ternary condition where arraylist cash is checked for null if true cash.size() is returned to getView()
        //else '0' is returned and hence no listView will be empty
       return cash != null? cash.size() : 0 ;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       // Log.i("publish", "inside getView");
        Viewholder viewholder = new Viewholder();
        View row = convertView;

        //Log.i("getView", String.valueOf(i));

        if (row == null) {

            //if there are 10 rows to be filled then this section will be executed 10 times.
            //    Log.i("convertview", "i am in convertview");
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.custom_layout, parent, false);
            viewholder.category = (TextView) row.findViewById(R.id.categorytext);

            viewholder.amount = (TextView) row.findViewById(R.id.amounttext);
            row.setTag(viewholder);


        }


        Button  delete = (Button)   row.findViewById(R.id.deletebutton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             long k = position + 1;
           //     dao.open();
                entryScreen = new EntryScreen(k);
//                dao.open();

            }
        });



        viewholder = (Viewholder) row.getTag();


        cash1 = cash.get(position);
        // Log.i("viewholder", String.valueOf(cash1.getCategory()));
        // Log.i("viewholder", String.valueOf(cash1.getAmount()));

        viewholder.category.setText(cash1.getCategory());
        viewholder.amount.setText(String.valueOf(cash1.getAmount()));

        //  Toast.makeText(context, "nothing to show",Toast.LENGTH_SHORT).show();


        return row;
    }


    public static class Viewholder {
        public TextView category, amount;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                Log.i("getFilter", String.valueOf(constraint));
                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();
                ArrayList<Cash> founded = new ArrayList<Cash>();


                if (constraint.length() == 0 ) {
                    founded.addAll(cash);
                } else {
                    for (Cash cash2 : cash) {
                        if (cash2.getCategory().toLowerCase(Locale.getDefault()).contains(constraint)) {
                            founded.add(cash2);
                        }

                    }

                    result.values = founded;
                    result.count = founded.size();
                }

                // Log.i("publish", String.valueOf(founded.size()));
                //following two lines is very important
                //as publish result can only take FilterResults objects

                return result;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

             cash  = (ArrayList<Cash>) results.values;

                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                   // Log.i("publish", "inside publish");
                    notifyDataSetInvalidated();
                }


            }


        };



        }

}


