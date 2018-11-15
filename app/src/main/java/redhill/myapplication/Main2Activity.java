package redhill.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Random;

public class Main2Activity extends YouTubeBaseActivity implements View.OnClickListener {

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
    private String[] mTestArray;  private int generatedIndex;

    // (4)

    private CheckBox check;
    private CheckBox checkFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting Random genre, for random search in it in "_All"
        String[] arrayGenre = getResources().getStringArray(getResources().getIdentifier("randomGenre", "array", getPackageName()));
        String randomGenre = arrayGenre[new Random().nextInt(arrayGenre.length)];

        // (1) Part of updating the textview with one part of an array
        mTestArray = getResources().getStringArray(getResources().getIdentifier(randomGenre, "array", getPackageName()));
        updateTextView();

        check = (CheckBox) findViewById(R.id.checkBox);
        checkFav = (CheckBox) findViewById(R.id.checkBoxFav);
        favMovies();

        noMoreMovies();

        // (2-v2) Displaying directly the Youtube Video
        mTestArrayYouTube = getResources().getStringArray(getResources().getIdentifier("YTLink", "array", getPackageName()));
        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.imageView);

        // Gets the index, which is the link of the chosen movie
        for (int i=0; i<mTestArrayYouTube.length; i++) {
            if (mTestArrayYouTube[i].contains(mTestArray[generatedIndex])) {
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
                Uri uri = Uri.parse("https://www.youtube.com/results?search_query=" + mTestArray[generatedIndex] + " " + getResources().getString(R.string.MainActivity_trailer));
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
                startActivity(new Intent(Main2Activity.this, Splash2Screen.class));
            }
        });

        // (2) Displaying online image in imageView live

        // cover = (ImageView) findViewById(R.id.imageView);
        /* Gets image from own Server
            String image_url = "http://tomurcuk-dergisi.com/Prime Time/" + mTestArray[generatedIndex] + ".jpg";
        */

        /*
        // Get singletone instance of ImageLoader
        ImageLoader imageLoader = ImageLoader.getInstance();
        // Initialize ImageLoader with configuration. Do it once.
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        // Load and display image asynchronously
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.image_not_found) //this is the image that will be displayed if download fails
                .cacheInMemory()
                .cacheOnDisc()
                .build();
        imageLoader.displayImage(image_url, cover, options);
        */


        // (3) With Button click it opens the trailer link, generated as : "https://www.youtube.com/results?search_query="+choosen array+" trailer"
        /*
        trailer = (Button) findViewById(R.id.button2);
        trailer.setOnClickListener(this);
        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.youtube.com/results?search_query=" + mTestArray[generatedIndex] + " " + getResources().getString(R.string.MainActivity_trailer));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        */
    }

    // Gets Database of favourite movies and checks them
    public void favMovies(){
        DBHelperFav dbHelper2 = new DBHelperFav(this);
        SQLiteDatabase db2 = dbHelper2.getReadableDatabase();
        String selectQuery2 = "SELECT * FROM favourite_movies WHERE moviesFav LIKE '%"+mTestArray[generatedIndex]+"%' ";
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
            db2.deleteMovie2(new String[]{mTestArray[generatedIndex]});
        }
    }

    // Adds checked Movies to the Database
    private void addMovieToDB(){
        DBHelper db = new DBHelper(this);
        DBObject newMovie = new DBObject();
        newMovie.setName(mTestArray[generatedIndex]);

        db.insertMovie(newMovie);
    }

    // Adds checked Movies to the Database in Favourites DB
    private void addMovieToDBFavourite(){
        DBHelperFav db2 = new DBHelperFav(this);
        DBObjectFav newFavMovie = new DBObjectFav();
        newFavMovie.setName(mTestArray[generatedIndex]);

        if(!db2.getAllMovies2().contains(mTestArray[generatedIndex])){
            db2.insertMovie2(newFavMovie);
        }
    }

    // Gets Database of disabled movies and disables them
    public void disabledMovies(){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String slectQuery = "SELECT * FROM disabled_movies WHERE movies LIKE '%"+mTestArray[generatedIndex]+"%' ";
        Cursor cursor = db.rawQuery(slectQuery, null);

        if(cursor.moveToFirst()){
            finish(); //finish Activity.
            startActivity(new Intent(Main2Activity.this, Main2Activity.class));//Start the same Activity
        }
    }

    // Ends The Activity, if there are no more Movies and it checks disabledMovies()
    public void noMoreMovies(){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int count = 0;

        // Adds +1 to count integer, and gets with this method how many of the movies in the Database are.
        for(int i=0; i<mTestArray.length; i++) {
            Cursor cursor = db.rawQuery("SELECT * FROM disabled_movies WHERE movies LIKE '%" + mTestArray[i] + "%' ", null);

            if(cursor.moveToFirst()){
                count++;
            }
        }

        // If in the Database are all movies of Array then it goes back to YearSelectingTest Activity, or it displays the available content.
        Context c = Main2Activity.this;
        if(count == mTestArray.length){
            Toast toast = Toast.makeText(c, c.getResources().getString(R.string.MainActivity_toast) + ".\n"+ c.getResources().getString(R.string.MainActivity_toast_), Toast.LENGTH_SHORT);
            //Centering the Text of the Toast
            LinearLayout layout = (LinearLayout) toast.getView();
            if (layout.getChildCount() > 0) {
                TextView tv = (TextView) layout.getChildAt(0);
                tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            }
            toast.show();

            finish(); //finish Activity.
            startActivity(new Intent(Main2Activity.this, YearSelectingTest.class));
        }
        else{
            disabledMovies();
        }
    }

    // (1) Updates the textview of the movie title with a random array
    private void updateTextView() {
        TextView textView = (TextView)findViewById(R.id.textView1);
        int maxIndex = mTestArray.length;
        Random random = new Random();
        generatedIndex = random.nextInt(maxIndex);

        textView.setText(mTestArray[generatedIndex]);
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
