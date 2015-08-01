package com.example.andi.intervalltimer;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;


public class Fetch_Min_Sec extends ActionBarActivity {




    private static final String FETCH_TIMER_ONE_VALUES ="timer_one";
    private static final String FETCH_TIMER_TWO_VALUES ="timer_two";

    private static final String FETCH_TIMER ="number_timer";

    private NumberPicker mNumberPicker_min;
    private NumberPicker mNumberPicker_sec;
    private Button mSetButton;

    private boolean timer_one = false;
    private boolean timer_two = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch__min__sec);




        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            if(bundle.getString(FETCH_TIMER).equals("timer_one")){
                mNumberPicker_min = (NumberPicker) findViewById(R.id.numberPicker_min);
                mNumberPicker_min.setMinValue(0);
                mNumberPicker_min.setMaxValue(500);

                mNumberPicker_sec = (NumberPicker) findViewById(R.id.numberPicker_sec);
                mNumberPicker_sec.setMinValue(0);
                mNumberPicker_sec.setMaxValue(60);

                timer_one = true;
            }else if(bundle.getString(FETCH_TIMER).equals("timer_two")){
                mNumberPicker_min = (NumberPicker) findViewById(R.id.numberPicker_min);
                mNumberPicker_min.setMinValue(0);
                mNumberPicker_min.setMaxValue(500);

                mNumberPicker_sec = (NumberPicker) findViewById(R.id.numberPicker_sec);
                mNumberPicker_sec.setMinValue(0);
                mNumberPicker_sec.setMaxValue(60);

                timer_two = true;
            }
        }

        mSetButton = (Button) findViewById(R.id.setButton);
        mSetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("onclick", "bin gerade am anfang");
                int min = mNumberPicker_min.getValue();
                int sec = mNumberPicker_sec.getValue();
                int[] values = {min, sec};
                Log.d("onclick", "min: " + min + " sec: " + sec);
                Intent intent = new Intent(Fetch_Min_Sec.this, MainActivity.class);
                if(timer_one){

                    intent.putExtra(FETCH_TIMER_ONE_VALUES,values);
                    intent.putExtra(FETCH_TIMER, "timer_one");
                    setResult(RESULT_OK, intent);


                }else if(timer_two){
                    intent.putExtra(FETCH_TIMER_TWO_VALUES,values);
                    intent.putExtra(FETCH_TIMER, "timer_two");
                    setResult(RESULT_OK,intent);

                }

                Fetch_Min_Sec.this.finish();


                //Fetch_Min_Sec.this.finish();


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fetch__min__sec, menu);
        return true;
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
}
