package com.example.What_s_color_now_;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private TextView showTime;
    private TextView showColor;
    private Toast toastBackgroundError;
    private Toast toastUnable;
    private RelativeLayout View;
    private Time now;
    Random rand;
    boolean needRand;
    CheckBox moreColor;
    int hour;
    int randomHour;
    int min;
    int randomMin;
    int sec;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        needRand = false;
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showTime(View);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }

    public void showTime(View view) {
        showTime = (TextView)findViewById(R.id.buttonTimer);
        showColor = (TextView)findViewById(R.id.buttonColor);
        toastBackgroundError = Toast.makeText(getApplicationContext(), "Can't change background...", Toast.LENGTH_SHORT);
        View = (RelativeLayout)findViewById(R.id.relativeLayout);
        now = new Time(Time.getCurrentTimezone());
        now.setToNow();
        hour = now.hour;
        min = now.minute;
        sec = now.second;
        if (((sec == 0) && needRand)||(needRand && (randomHour == 0) && (randomMin == 0))) {
            rand = new Random();
            randomHour = rand.nextInt(230);
            randomMin = rand.nextInt(190);
        }
        if (!needRand) {
            randomHour = 0;
            randomMin = 0;
        }
        showTime.setText(getStr(hour) + " : " + getStr(min) + " : " + getStr(sec));
        String color = "#" + getHex(hour + randomHour) + getHex(min + randomMin) + getHex(sec);
        showColor.setText(color);
        try {
            View.setBackgroundColor(Color.parseColor(color));
        } catch(Exception ex) {
            toastBackgroundError.show();
        }
    }

    public String getStr (int h) {
        if (h < 10) {
            return "0" + Integer.toString(h);
        } else {
            return Integer.toString(h);
        }
    }

    public String getHex (int h) {
        if (h <= 15) {
            return "0" + Integer.toHexString(h);
        } else {
            return Integer.toHexString(h);
        }
    }

    public void setRandomMode (View view) {
        //toastUnable = Toast.makeText(getApplicationContext(), "Have no implementation!", Toast.LENGTH_SHORT);
        //toastUnable.show();
        moreColor = (CheckBox)findViewById(R.id.wantMoreColor);
        if (moreColor.isChecked()) {
            needRand = true;
        } else {
            needRand = false;
        }
    }
}
