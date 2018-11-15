package redhill.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by LAL on 19.11.2016.
 */

public class YearSelectingTest extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private SharedPreferences prefs; private SharedPreferences prefsSpinner;
    private SharedPreferences.Editor editor; private SharedPreferences.Editor editorSpinner;
    private String genre;

    //InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.year_selecting_test);

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //interstitialAd();
                finish();
                startActivity(new Intent(YearSelectingTest.this, SplashScreen.class));
            }
        });

        // Get "SharedPreferences" from GenreActivity.java class
        prefs = this.getSharedPreferences("Genre", Context.MODE_PRIVATE);
        genre = prefs.getString("genre", ""); //0 is the default value

        // Creats new "SharedPreferences" for the next Activity
        prefs = this.getSharedPreferences("Year", Context.MODE_PRIVATE);

        // Creats new "SharedPreferences" for the next Activity just Spinner selection
        prefsSpinner = this.getSharedPreferences("Spinner", Context.MODE_PRIVATE);

        // Spinner sets
        spinner = (Spinner)findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.years, R.layout.spinner);
        adapter.setDropDownViewResource(R.layout.spinnerdropdown);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor = prefs.edit();
                editor.putString("year", genre+spinner.getSelectedItem().toString());
                editor.apply();

                editorSpinner = prefsSpinner.edit();
                editorSpinner.putString("spin", spinner.getSelectedItem().toString());
                editorSpinner.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*
        // Display the actual "SharedPreferences" String
        TextView textView = (TextView)findViewById(R.id.textView);
        String[] arrayGenreName = getResources().getStringArray(getResources().getIdentifier("yearSelecting_genreName", "array", getPackageName()));
        for (int i = 0; i < arrayGenreName.length; i++) {
            if (genre.contains(arrayGenreName[i])) {
                textView.setText(arrayGenreName[i]);
            }
        }
        */

    }

    /* private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private void interstitialAd(){
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5215375185046126/1806364299");

        AdRequest adRequest = new AdRequest.Builder()
                //TEST PART OF INTERSTITIAL AD----------------------
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //--------------------------------------------------
                .build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });
    } */

    @Override
    public void onClick(View view) {
    }


}
