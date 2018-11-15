package redhill.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Tronya on 19.11.2016.
 */

public class SplashScreen extends Activity implements Animation.AnimationListener {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    RelativeLayout splashScreen;
    ImageView logo;
    TextView title;
    Animation animZoom, animFade;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen);

        splashScreen = (RelativeLayout) findViewById(R.id.splashscreen);
        logo = (ImageView) findViewById(R.id.logo);
        title = (TextView) findViewById(R.id.title);

        animZoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in_2);
        animFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);

        splashScreen.startAnimation(animZoom);
        logo.startAnimation(animFade);
        title.startAnimation(animFade);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                startMainActivity();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    public void startMainActivity() {
        Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
        SplashScreen.this.startActivity(mainIntent);
        SplashScreen.this.finish();
    }


    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}