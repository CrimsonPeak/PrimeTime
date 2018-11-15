package redhill.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by LAL on 19.01.2017.
 */

public class GenreActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        prefs = this.getSharedPreferences("Genre", Context.MODE_PRIVATE);

        button = (Button)findViewById(R.id.buttonAction);
        button.setOnClickListener(this);

        button = (Button)findViewById(R.id.buttonActionComedy);
        button.setOnClickListener(this);

        button = (Button)findViewById(R.id.buttonAdventure);
        button.setOnClickListener(this);

        button = (Button)findViewById(R.id.buttonAnimation);
        button.setOnClickListener(this);

        button = (Button)findViewById(R.id.buttonBiography);
        button.setOnClickListener(this);

        button = (Button)findViewById(R.id.buttonComedy);
        button.setOnClickListener(this);

        button = (Button)findViewById(R.id.buttonCrime);
        button.setOnClickListener(this);

        button = (Button)findViewById(R.id.buttonDrama);
        button.setOnClickListener(this);

        button = (Button)findViewById(R.id.buttonFamily);
        button.setOnClickListener(this);

        button = (Button)findViewById(R.id.buttonFantasy);
        button.setOnClickListener(this);

        button = (Button)findViewById(R.id.buttonHistory);
        button.setOnClickListener(this);

        button = (Button)findViewById(R.id.buttonHorror);
        button.setOnClickListener(this);

        button = (Button)findViewById(R.id.buttonMysteryThriller);
        button.setOnClickListener(this);

        button = (Button)findViewById(R.id.buttonRomanceComedy);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        editor = prefs.edit();
        switch(view.getId()) {
            case R.id.buttonAction:
                editor.putString("genre", "action_");
                break;
            case R.id.buttonActionComedy:
                editor.putString("genre", "actionComedy_");
                break;
            case R.id.buttonAdventure:
                editor.putString("genre", "adventure_");
                break;
            case R.id.buttonAnimation:
                editor.putString("genre", "animation_");
                break;
            case R.id.buttonBiography:
                editor.putString("genre", "biography_");
                break;
            case R.id.buttonComedy:
                editor.putString("genre", "comedy_");
                break;
            case R.id.buttonCrime:
                editor.putString("genre", "crime_");
                break;
            case R.id.buttonDrama:
                editor.putString("genre", "drama_");
                break;
            case R.id.buttonFamily:
                editor.putString("genre", "family_");
                break;
            case R.id.buttonFantasy:
                editor.putString("genre", "fantasy_");
                break;
            case R.id.buttonHistory:
                editor.putString("genre", "history_");
                break;
            case R.id.buttonHorror:
                editor.putString("genre", "horror_");
                break;
            case R.id.buttonMysteryThriller:
                editor.putString("genre", "mysteryThriller_");
                break;
            case R.id.buttonRomanceComedy:
                editor.putString("genre", "romanceComedy_");
                break;
        }
        editor.apply();
        startActivity(new Intent(GenreActivity.this, YearSelectingTest.class));
    }

}