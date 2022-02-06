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
        // Animación izquierda
        Animation leftAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_left_animation);
        TextView textViewTitle = findViewById(R.id.splashTitle);
        textViewTitle.startAnimation(leftAnimation);
        leftAnimation.setAnimationListener(this);

        // Animación derecha
        Animation rightAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_right_animation);
        TextView textViewSubtitle = findViewById(R.id.splashSubtitle);
        textViewSubtitle.startAnimation(rightAnimation);
        rightAnimation.setAnimationListener(this);

        // Animación inferior
        Animation bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_bottom_animation);
        ImageView imageViewSplashImage = findViewById(R.id.splashImage);
        imageViewSplashImage.startAnimation(bottomAnimation);
        bottomAnimation.setAnimationListener(this);
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