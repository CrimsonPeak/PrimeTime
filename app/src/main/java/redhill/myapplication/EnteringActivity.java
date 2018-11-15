package redhill.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by LAL on 19.11.2016.
 */

public class EnteringActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private ImageButton info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);

        info = (ImageButton) findViewById(R.id.info);
        info.setOnClickListener(this);
        info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(EnteringActivity.this, PopUp.class));
            }
        });

        button = (Button)findViewById(R.id.button4);
        button.setOnClickListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EnteringActivity.this, GenreActivity.class));
            }
        });

        button = (Button)findViewById(R.id.button5);
        button.setOnClickListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EnteringActivity.this, Splash2Screen.class));
            }
        });

        button = (Button)findViewById(R.id.button3);
        button.setOnClickListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EnteringActivity.this, DisabledMovies.class));
            }
        });

        button = (Button)findViewById(R.id.buttonFav);
        button.setOnClickListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EnteringActivity.this, FavouriteMovies.class));
            }
        });

    }

    @Override
    public void onClick(View view) {
    }

}
