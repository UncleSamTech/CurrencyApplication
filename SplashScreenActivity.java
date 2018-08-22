package com.currencyapp.samuel.currencyapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {
    //variable status declared to hold integer value and assigned a value of 0
    int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //the method is called here in the onCreate method
        executeProgress();
    }

    /**
     * A method
     * @method executeProgress used for handling the splash screen display for a time limit
     * it overides the thread runnable class to handle that
     */
    private void executeProgress() {

        new Thread(new Runnable() {
            public void run() {
                while (status < 100) {
                    status += 5;

                    try {

                        Thread.sleep(200);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        }).start();
    }
}
