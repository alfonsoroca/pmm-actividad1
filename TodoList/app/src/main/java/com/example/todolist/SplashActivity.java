package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        // Ocultar ActionBar
        getSupportActionBar().hide();

        // Asignar animaciones a etiquetas
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation);
        TextView textViewTitle = findViewById(R.id.splashTitle);
        textViewTitle.startAnimation(animation);
        animation.setAnimationListener(this);

        TextView textViewSubtitle = findViewById(R.id.splashSubtitle);
        textViewSubtitle.startAnimation(animation);
        animation.setAnimationListener(this);

        ImageView imageViewSplashImage = findViewById(R.id.splashImage);
        imageViewSplashImage.startAnimation(animation);
        animation.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}