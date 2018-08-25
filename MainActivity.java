package com.currencyapp.samuel.currencyapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.currencyapp.samuel.currencyapplication.ControllerClass.CountrySwipeToDeleteClass;
import com.currencyapp.samuel.currencyapplication.ControllerClass.QueryUtilClass;
import com.currencyapp.samuel.currencyapplication.ModelClass.CountryDetailsAdapterClass;
import com.currencyapp.samuel.currencyapplication.ModelClass.CountryDetailsObjectClass;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //all the classes and widgets are declared here
    private CountryDetailsAdapterClass countryDetailsAdapterClass;
    private ListView listView;
    private Context context = MainActivity.this;
    private TextView textViewErrorView;
    private ProgressBar progressBar;
    ArrayList<CountryDetailsObjectClass> countryDetailsObjectClasses = new ArrayList<>();
    private ConnectivityManager connMgr;
    private CountrySwipeToDeleteClass swipeToDeleteClass;
    private FrameLayout frame;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * the list view , text view and progress bar are referenced
         * here to be able to use their objects on these views
         */
        listView = findViewById(R.id.lv_country);
        textViewErrorView = findViewById(R.id.text_view_error_display);
        frame = findViewById(R.id.frame_lay);

        progressBar = findViewById(R.id.progress_bar_country);
        progressBar = new ProgressBar(this);
        //the progress bar is set to show a display to the user while the loading is going on


//the ConnectivityManager object is referenced and called on the getSystemService method

            connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            //the NetworkInfo class gets the current state of the device network connection
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();



            /**
             * a check is carried out to see if there is internet or slight connection
             * when the check is true a success message is rendered and the
             * execute method is called on CountryDetailsAsyncTask class
             */
            if (networkInfo != null && networkInfo.isConnected()) {
                progressBar.setVisibility(View.VISIBLE);

                Toast.makeText(MainActivity.this, getString(R.string.msg_internet_connected), Toast.LENGTH_SHORT).show();
                new CountryDetailsAsyncTask().execute(QueryUtilClass.JSON_RESPONSE);
                progressBar.setVisibility(View.GONE);


            } else {
                progressBar.setVisibility(View.GONE);
                textViewErrorView.setText(R.string.error_msg_connected);
            }




/**
 * if check is false(i.e no internet connection or phone wifi off or phone in airplane mode)
 * the progress bar disappears and the textview shows the error message
 */





    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();

    }

    /**
     * A private class which extends from AsyncTask is created to handle all the network request on
     * a different thread. This is to avoid freezing the UI thread
     * it handles network request in the doInBackground method, fetches the data and
     * returns it as an array list of strings
     * the result is then passed on the main thread in the onPostExecute method
     *
     */

    private class CountryDetailsAsyncTask extends AsyncTask<String ,Void, ArrayList<CountryDetailsObjectClass>>{


        @Override
        protected ArrayList<CountryDetailsObjectClass> doInBackground(String... strings) {
            ArrayList<CountryDetailsObjectClass> countryDetailsObjectClasses;
            if(QueryUtilClass.JSON_RESPONSE==null){
                return null;
            }

            countryDetailsObjectClasses = QueryUtilClass.fetchCountryData(QueryUtilClass.JSON_RESPONSE);
            return countryDetailsObjectClasses;
        }

        @Override
        protected void onPostExecute(final ArrayList<CountryDetailsObjectClass> countryDetailsObjectClasses) {
            super.onPostExecute(countryDetailsObjectClasses);
            countryDetailsAdapterClass = new CountryDetailsAdapterClass(context,countryDetailsObjectClasses);
            listView.setAdapter(countryDetailsAdapterClass);
            /**
             *  the constructor of our CountrySwipeToDeleteClass is called and all the
             *  necessary interfaces are implemented to implement our swipe to delete functionality
             */



            swipeToDeleteClass = new CountrySwipeToDeleteClass(listView,  new CountrySwipeToDeleteClass.DismissCallbacks() {
                @Override
                public boolean canDismiss(int position) {
                    return true;
                }

                @Override
                public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                    for(int positions : reverseSortedPositions){

                        countryDetailsObjectClasses.remove(positions);
                        countryDetailsAdapterClass.notifyDataSetChanged();

                    }
                }




            });

            listView.setOnTouchListener(swipeToDeleteClass);
        }
    }







}
