package com.currencyapp.samuel.currencyapplication.ModelClass;

/**
 * Created by SAMUEL on 8/21/2018.
 */

/**
 * this is our model class for accessing our results
 */
public class CountryDetailsObjectClass {


        String country_name;
        String currency_name;
        String language;

        public CountryDetailsObjectClass(String country_name, String currency_name, String language) {
            this.country_name = country_name;
            this.currency_name = currency_name;
            this.language = language;
        }

        public String getCountry_name() {
            return country_name;
        }

        public String getCurrency_name() {
            return currency_name;
        }

        public String getLanguage() {
            return language;
        }



}
