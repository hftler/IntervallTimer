package com.example.andi.intervalltimer;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;


public class Fetch_Rounds extends ActionBarActivity {

    private NumberPicker mNumberPicker_rounds;
    private Button mSetButton;
    public static final String ROUNDS = "rounds";

    private  static final String FETCH_TIMER = "number_timer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch__rounds);

        mNumberPicker_rounds = (NumberPicker) findViewById(R.id.numberPicker_rounds);
        mNumberPicker_rounds.setMinValue(1);
        mNumberPicker_rounds.setMaxValue(100);

        mSetButton = (Button) findViewById(R.id.button_rounds);
        mSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onclick", "bin gerade am anfang");
                int rounds = mNumberPicker_rounds.getValue();


                Log.d("onclick", "rounds: " + rounds);



                Intent intent = new Intent(Fetch_Rounds.this, MainActivity.class);
                intent.putExtra(ROUNDS, rounds);
                intent.putExtra(FETCH_TIMER, "rounds");
                setResult(RESULT_OK,intent);



            Fetch_Rounds.this.finish();


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fetch__rounds, menu);
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
