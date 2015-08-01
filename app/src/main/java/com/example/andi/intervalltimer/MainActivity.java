package com.example.andi.intervalltimer;

import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;


public class MainActivity extends ActionBarActivity {


    private final static String SAVED_VALUE_TIMER_ONE ="timer_one";
    private final static String SAVED_VALUE_TIMER_TWO ="timer_two";

    private ToneGenerator toneG;

    private static final String FETCH_TIMER_TWO_VALUES = "timer_two";
    private static final String FETCH_TIMER_ONE_VALUES = "timer_one";

    private static final String FETCH_TIMER = "number_timer";
    private static final String ROUNDS = "rounds";

    private static final int interval = 1000;
    private boolean timerHasStarted = false;

    private Button startButton;
    private IntervalTimer mCountDownTimer_one;
    private IntervalTimer mCountDownTimer_two;
    private TextView timer_one;
    private TextView timer_two;
    private TextView rounds;


    private int mRounds = 0;


    private long mCountDownTime_one;
    private long mCountDownTime_two;

    public MainActivity() {

        toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 500);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Fetch the UI Elements
        timer_one = (TextView) findViewById(R.id.first_timer);
        timer_two = (TextView) findViewById(R.id.second_timer);
        rounds = (TextView) findViewById(R.id.rounds);

        //Fetch Start Button + setOnClickListener
        startButton = (Button) findViewById(R.id.button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (timerHasStarted == false) {

                    if (mRounds < 1) {
                        showToast("Please set rounds");
                    } else {
                        beep();
                        mRounds--;
                        rounds.setText(String.valueOf(mRounds));
                        mCountDownTimer_one.start();
                        timerHasStarted = true;
                        startButton.setText("Reset");
                    }

                } else {
                    initialState();
                }
            }
        });


        timer_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fetch_Min_Sec.class);
                intent.putExtra(FETCH_TIMER, "timer_one");
                startActivityForResult(intent, 1);
            }
        });

        timer_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fetch_Min_Sec.class);
                intent.putExtra(FETCH_TIMER, "timer_two");
                startActivityForResult(intent, 1);
            }
        });

        rounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fetch_Rounds.class);
                startActivityForResult(intent, 1);
            }
        });



    }

    //initial state, if "reset" is pressed
    private void initialState() {
        mCountDownTimer_one.cancel();
        mCountDownTimer_two.cancel();
        timerHasStarted = false;
        startButton.setText(getResources().getText(R.string.start));
        timer_one.setText(getResources().getText(R.string.zero));
        timer_two.setText(getResources().getText(R.string.zero));
        rounds.setText(getResources().getText(R.string.rounds));
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                if (data.getStringExtra(FETCH_TIMER).equals("timer_one")) {

                    //Fetch min and sec of Timer ONE
                    int[] values = getValues(data, FETCH_TIMER_ONE_VALUES);
                    int min_timer_one = values[0];
                    int sec_timer_one = values[1];

                    //convert min and sec of timer ONE into ms
                    mCountDownTime_one = countDownTimeMS(min_timer_one, sec_timer_one);

                    timer_one.setText(showTime(mCountDownTime_one));

                    //initialize new Intervaltimer with the given values
                    mCountDownTimer_one = new IntervalTimer(mCountDownTime_one, interval, 1);

                } else if (data.getStringExtra(FETCH_TIMER).equals("timer_two")) {

                    //Fetch min and sec of Timer TWO
                    int[] values = getValues(data, FETCH_TIMER_TWO_VALUES);
                    int min_timer_two = values[0];
                    int sec_timer_two = values[1];

                    //convert min and sec of timer TWO into ms
                    mCountDownTime_two = countDownTimeMS(min_timer_two, sec_timer_two);

                    timer_two.setText(showTime(mCountDownTime_two));

                    //initialize IntervallTimer TWO with given values
                    mCountDownTimer_two = new IntervalTimer(mCountDownTime_two, interval, 2);


                } else if (data.getStringExtra(FETCH_TIMER).equals("rounds")) {

                    //get number of rounds
                    mRounds = data.getIntExtra(ROUNDS, 0);
                    rounds.setText(String.valueOf(mRounds));
                }
            }
            if (resultCode == RESULT_CANCELED) {
                showToast("Error! Please set again!");
            }
        }
    }

    //Fetch data from Intent
    private int[] getValues(Intent data, String numberTimer) {
        return new int[]{data.getIntArrayExtra(numberTimer)[0], data.getIntArrayExtra(numberTimer)[1]};
    }

    //convert min and sec into ms
    private long countDownTimeMS(int min, int sec) {
        return min * 60000 + sec * 1000;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //convert ms into min:sec
    private String showTime(long time) {
        long min = time / 1000 / 60;
        long seconds = time / 1000 - min * 60;
        String erg = "";
        if (min < 10) {
            erg = "0" + String.valueOf(min);
        } else {
            erg = String.valueOf(min);
        }
        String sec = "";
        if (seconds < 10) {
            sec = "0" + String.valueOf(seconds);
        } else {
            sec = String.valueOf(seconds);
        }
        return erg + ":" + sec;
    }


    //inner class: IntervalTimer
    private class IntervalTimer extends CountDownTimer implements Serializable {

        private int number;

        public IntervalTimer(long startTime, long interval, int number) {
            super(startTime, interval);
            this.number = number;
        }


        @Override
        public void onFinish() {
            beep();
            //If First IntervalTimer is finished:
            if (number == 1) {

                //set 00:00 and beep
                timer_one.setText("00:00");


                //if countdown time of Timer two was set, start timer two;
                if (mCountDownTime_two != 0) {
                    mCountDownTimer_two.start();
                } else {
                    if (mRounds == 0) {
                        initialState();
                        showToast("Well Done");
                    } else {
                        denyRound();
                    }
                }

            } else {

                if (mRounds == 0) {
                    initialState();
                    showToast("Well Done");
                } else if (mRounds > 0) {
                    denyRound();

                }
            }

        }

        private void denyRound() {
            timer_two.setText("00:00");
            mRounds--;
            rounds.setText(String.valueOf(mRounds));
            mCountDownTimer_one.start();
        }

        @Override
        //countdown the specific Timer with Intervall and show the remaining time in UI Elements
        public void onTick(long millisUntilFinished) {
            if (number == 1) {
                timer_one.setText(showTime(millisUntilFinished));
            } else {
                timer_two.setText(showTime(millisUntilFinished));
            }
        }
    }

    //lets the toneG beep
    private void beep() {
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 600);
    }

    //shows a Toast with a given string
    private void showToast(String s) {
        Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);
        toast.show();
    }

}
