package com.currencyapp.samuel.currencyapplication.ControllerClass;

import android.text.TextUtils;
import android.util.Log;

import com.currencyapp.samuel.currencyapplication.ModelClass.CountryDetailsObjectClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by SAMUEL on 8/21/2018.
 */

public class QueryUtilClass {
    //the header tag for loging errors tied to this class is declared and assigned value
    public static final String LOG_TAG = QueryUtilClass.class.getSimpleName();
    //the api provided for all countries
    public static final String JSON_RESPONSE = "https://restcountries.eu/rest/v2/all";

    public QueryUtilClass() {
    }

    /**
     * This method helps to convert our string address to url format for retreiving the json data
     *
     * @param urlAdress
     * @return url
     */
    private static URL createUrlAddress(String urlAdress) {

        URL url = null;
        try {
            url = new URL(urlAdress);
        } catch (MalformedURLException e) {
            Log.i("LOG_TAG", "Error as a result of : ", e);
        }
        return url;
    }

    /**
     * This method uses the HttpUrlConnection class to make network request
     *
     * @param url
     * @return jsonResponse
     * @throws IOException
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        //check if url is null and exit early
        //return jsonResponse as empty string
        if (url == null) {
            return jsonResponse;
        }
/**
 * HttpURLConnection class is declared and assigned a null value
 * InputStream class is also used for reading data
 */
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        /**
         * line 88 the htttpURLConnection object holds the result of openConnection method called on the url object
         * line 89  the setRequestMethod is passed a GET because we are making a network request
         * line 90 the setReadTimeout is also set to 10000 milliseconds which is the time it can stay before it times out if no response
         * line 91 the setConnectTimeout is also set to 15000 milliseconds for allowable time if able to conect
         * line 92 the connect method is finally called to make a connection
         * line 93 a check is made to see if the response code received is 200 which means connection is ok
         * line 94 the condition if met, the inputStream is read and passed to the object input stream
         * line 95 the method @method readFromStream is passed the inputStream which reads it line by line builds it uses the String Builder and returns it as a string
         * the finally is used for disconnecting to avoid memory leaks and conserving our apps
         *
         *
         *
         */
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000 /*milliseconds*/);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error as a result of : " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.i(LOG_TAG, "Error as a result of ", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * This method takes a parameter of InputStream in UTF-8 standard reads it line by line, builds it with StringBuilder
     * class and returns a string of already read data
     *
     * @param inputStream
     * @return
     * @throws IOException
     */

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * This method takes a string json string value and traverse through from the root node down to all the needed fields
     * which are country names, currency and languages
     * @param jSonValues
     * @return jSonResponse
     */

    public static ArrayList<CountryDetailsObjectClass> extractCountryData(String jSonValues) {
        //the final string values that will hold data are declared and intialized to an empty string
        String country_name = "";
        String currency_name = "";
        String language_name = "";
        //the arrays list is declared an instantiated for holding the extracted data from the traversed json string values
        ArrayList<CountryDetailsObjectClass> jSonResponse = new ArrayList<>();

        /**
         * a check is conducted for empty string or null values
         * it returns null if the passed string parameter doesnt hold values
         */
        if (TextUtils.isEmpty(jSonValues)) {
            return null;
        }
try{
    /**
     * The root node for our JSON files is an array so it is
     * called first and passed a value of string
     * The json files is traversed as it is an array of objects of countries with their various properties
     */
    JSONArray jsonArray = new JSONArray(jSonValues);
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            country_name = jsonObject.optString("name");

            /**
             * This area traverses through the currencies node to access the name of  currency for every country
             */
            JSONArray currencies = jsonObject.optJSONArray("currencies");
            for(int j = 0 ; j < currencies.length() ; j++){
                JSONObject jsonObject1 = currencies.optJSONObject(j);
                currency_name  = jsonObject1.optString("name");

            }
            /**
             * This area traverses through the languages node to access the name of  language for every country
             */

            JSONArray languages = jsonObject.optJSONArray("languages");
            for(int j = 0 ; j < languages.length() ; j++){
                JSONObject jsonObject1 = languages.optJSONObject(j);
                language_name  = jsonObject1.optString("name");

            }


            /**
             * The Model class constructor is called and added to our array list which is finally returned
             */
            CountryDetailsObjectClass countryDetailsObjectClass = new CountryDetailsObjectClass(country_name,currency_name,language_name);
            jSonResponse.add(countryDetailsObjectClass);


        }}
        catch (JSONException js){Log.e(LOG_TAG,"Error as a result of " + js.getMessage());}


        return jSonResponse;
    }

    /**
     * The fetchCountryData method is used to fetch the country data
     *
     * it takes a
     * @param urlAddr
     * and returns
     * @return  newsCheckObject arraylist
     */
    public static ArrayList<CountryDetailsObjectClass> fetchCountryData(String urlAddr) {
        //create the url object
        URL url1 = createUrlAddress(urlAddr);


        String jsonResponse = "";

        //make http request and store the
        //response on a string object
        try {
            jsonResponse = makeHttpRequest(url1);


        } catch (IOException io) {
            Log.i(LOG_TAG, "Error as a result of : " +  io.getMessage());
        }
        ArrayList<CountryDetailsObjectClass> countryDetails = extractCountryData(jsonResponse);

        return countryDetails;
    }

}
