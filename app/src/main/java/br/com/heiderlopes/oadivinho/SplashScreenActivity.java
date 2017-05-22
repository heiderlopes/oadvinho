package br.com.heiderlopes.oadivinho;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 4500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        executarSom();
        carregar();
    }

    private void executarSom() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*MediaPlayer player = new MediaPlayer().create(SplashScreenActivity.this, R.raw.genio);
                player.start();*/
                MediaPlayer player = new MediaPlayer().create(SplashScreenActivity.this, R.raw.oooh);
                player.start();

            }
        }, 1500);
    }

    private void carregar() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.splash);
        anim.reset();

        ImageView iv = (ImageView) findViewById(R.id.splash);
        if (iv != null) {
            iv.clearAnimation();
            iv.startAnimation(anim);
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this,
                        MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
