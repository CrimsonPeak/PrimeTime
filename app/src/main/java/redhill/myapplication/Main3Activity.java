package redhill.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Arrays;

public class Main3Activity extends YouTubeBaseActivity implements View.OnClickListener {

    private static final String TAG="MainActivity";

    // (2)
    private ImageView cover;
    private String[] mTestArrayYouTube;
    private int index;
    YouTubePlayerView mYouTubePlayerView;
    Button playButton;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;

    // (3)
    private Button trailer; private Button refresh;

    // (1) + (3)
    private String mTestArray;  private int generatedIndex;

    // (4)
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private String favMovie;

    private CheckBox check;
    private CheckBox checkFav;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView1);

        // Get "SharedPreferences" from FavouriteMovies.java class
        prefs = this.getSharedPreferences("Favourite", Context.MODE_PRIVATE);
        favMovie = prefs.getString("fav", ""); //0 is the default value

        // (1) Part of updating the textview with one part of an array
        mTestArray = favMovie;
        updateTextViewFav();

        check = (CheckBox) findViewById(R.id.checkBox);
        checkFav = (CheckBox) findViewById(R.id.checkBoxFav);
        favMovies();

        // (2-v2) Displaying directly the Youtube Video
        mTestArrayYouTube = getResources().getStringArray(getResources().getIdentifier("YTLink", "array", getPackageName()));
        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.imageView);

        // Gets the index, which is the link of the chosen movie
        for (int i=0; i<mTestArrayYouTube.length; i++) {
            if (mTestArrayYouTube[i].contains(mTestArray)) {
                index = i;
                break;
            }
        }

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG, "onClick: Done Initializing.");

                // Disables the make fullscreen button
                YouTubePlayer.PlayerStyle style = YouTubePlayer.PlayerStyle.DEFAULT;
                youTubePlayer.setPlayerStyle(style);
                youTubePlayer.setShowFullscreenButton(false);

                //Deletes the moviename before the link and make it just the link
                String link = mTestArrayYouTube[index].replaceAll(".*=", "");

                youTubePlayer.loadVideo(link);
                youTubePlayer.pause();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "onClick: Failed to Initialize.");
            }
        };

        Log.d(TAG, "onClick: Initializing YouTube Player.");
        mYouTubePlayerView.initialize(YoutTubeConfig.getApiKey(), mOnInitializedListener);

        //Opens Youtube and searches for the trailer of chosen movie
        playButton = (Button) findViewById(R.id.button2);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                // IF dont open direct, then open extern and make youtube search with buttonClick
                Uri uri = Uri.parse("https://www.youtube.com/results?search_query=" + mTestArray + " " + getResources().getString(R.string.MainActivity_trailer));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        check.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (check.isChecked()){
                    checkFav.setEnabled(false);
                    addMovieToDB();
                }
                else
                    checkFav.setEnabled(true);
            }
        });

        checkFav.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (checkFav.isChecked()){
                    check.setEnabled(false);
                    addMovieToDBFavourite();
                }
                else{
                    check.setEnabled(true);
                    favMovieUncheck();
                }
            }
        });

        //Opens the SplashScreen
        refresh = (Button) findViewById(R.id.button_refresh);
        refresh.setOnClickListener(this);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); //finish Activity.
                startActivity(new Intent(Main3Activity.this, Splash2Screen.class));
            }
        });

    }

    // Gets Database of favourite movies and checks them
    public void favMovies(){
        DBHelperFav dbHelper2 = new DBHelperFav(this);
        SQLiteDatabase db2 = dbHelper2.getReadableDatabase();
        String selectQuery2 = "SELECT * FROM favourite_movies WHERE moviesFav LIKE '%"+mTestArray+"%' ";
        Cursor cursor2 = db2.rawQuery(selectQuery2, null);

        if(cursor2.moveToFirst()){
            checkFav.setChecked(true);
            check.setEnabled(false);
        }
    }

    // Deletes if favourite movie was unchecked from Favourites Array
    public void favMovieUncheck(){
        DBHelperFav db2 = new DBHelperFav(this);

        if(!checkFav.isChecked()){
            db2.deleteMovie2(new String[]{mTestArray});
        }
    }

    // Adds checked Movies to the Database
    private void addMovieToDB(){
        DBHelper db = new DBHelper(this);
        DBObject newMovie = new DBObject();
        newMovie.setName(mTestArray);

        db.insertMovie(newMovie);
    }

    // Adds checked Movies to the Database in Favourites DB
    private void addMovieToDBFavourite(){
        DBHelperFav db2 = new DBHelperFav(this);
        DBObjectFav newFavMovie = new DBObjectFav();
        newFavMovie.setName(mTestArray);

        if(!db2.getAllMovies2().contains(mTestArray)){
            db2.insertMovie2(newFavMovie);
        }

    }

    private void updateTextViewFav() {
        generatedIndex = Arrays.asList(mTestArray).indexOf(favMovie);

        textView.setText(mTestArray);
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    protected void onStart() {
        super.onStart(); // Always call the superclass method first
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

}
