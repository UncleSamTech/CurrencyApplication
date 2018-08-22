package com.currencyapp.samuel.currencyapplication.ModelClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.currencyapp.samuel.currencyapplication.R;

import java.util.ArrayList;

/**
 * An adapter class for  for inflating our view with data
 * Created by SAMUEL on 8/21/2018.
 */


/**
 * Class declaration extends from the Array Adapter which goes with a generic arguement and passed a parameter of our model class
 */
public class CountryDetailsAdapterClass extends ArrayAdapter<CountryDetailsObjectClass> {
    ViewHolder holder;

//the constructor is declared and holds two arguement context using this adapter and the array list
    public CountryDetailsAdapterClass(@NonNull Context context, ArrayList<CountryDetailsObjectClass> countrydetails) {
        super(context, 0, countrydetails);
    }



    /**
     * This method is overriden with four parameters
     *
     * @param position
     * @param convertView
     * @param parent
     * @return list_view
     *
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       //the model class is declared and the object holds the getItem() method
        CountryDetailsObjectClass countryDetailsObjectClass = getItem(position);
        //the view object holds a refeerence to the second parameter convertView
        View list_view = convertView;
        /**
         * a check is done on view to see if it has being created
         * if it is null it is created and inlfated with our xml value
         */
        if(list_view==null){
            list_view = LayoutInflater.from(getContext()).inflate(R.layout.list_of_details, parent, false);
            holder = createViewFromViewHolder(list_view);
            list_view.setTag(holder);
        }

/**
 * the various views are referenced here and
 * set to a value from the getter methods in the model class
 */
        holder = (ViewHolder)list_view.getTag();
        holder.textViewCountryName.setText(countryDetailsObjectClass.getCountry_name());
        holder.textViewCountryCurrency.setText(countryDetailsObjectClass.getCurrency_name());
        holder.textViewCountryLanguage.setText(countryDetailsObjectClass.getLanguage());

        return list_view;
    }

    /**
     * this static view holder class helps to render a better user experience
     * with this viewholder each view widget doesnt need to be created more than once
     * the view holder class creates this view, references it and stores it for future use
     */
    private static class ViewHolder{

        TextView textViewCountryName = null;
        TextView textViewCountryCurrency = null;
        TextView textViewCountryLanguage = null;

        public ViewHolder(TextView textViewCountryName, TextView textViewCountryCurrency, TextView textViewCountryLanguage) {
            this.textViewCountryName = textViewCountryName;
            this.textViewCountryCurrency = textViewCountryCurrency;
            this.textViewCountryLanguage = textViewCountryLanguage;
        }
    }

    /**
     * this methods uses the constructor of the ViewHolder class to create a view
     * @param view
     * @return
     */
    private ViewHolder createViewFromViewHolder(View view){
TextView textViewCountryName = view.findViewById(R.id.text_view_name_of_country);
        TextView textViewCurrencyName = view.findViewById(R.id.text_view_name_of_currency);
        TextView textViewLanguageName = view.findViewById(R.id.text_view_name_of_language);

        return new ViewHolder(textViewCountryName, textViewCurrencyName,textViewLanguageName);
    }
}
