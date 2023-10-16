package com.zy.snakesladders;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Zy_SnL_MainActivity extends AppCompatActivity {
    ImageView Dice;
    Button Play, Reset;
    int DiceValue;
    int CurrentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zy_snl_activity_main);

        Dice = findViewById(R.id.dice);
        Play = findViewById(R.id.playBtn);
        Reset = findViewById(R.id.resetBtn);

        Play.setOnClickListener(v -> {
            Play();
        });
        Reset.setOnClickListener(v -> {
            Reset();
        });
    }

    void Reset() {
        DiceValue = 0;
        CurrentPos = 0;
        UpdateDice();
        UpdateBoxes();
    }

    void Animate() {
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(
                Dice,
                View.TRANSLATION_X,
                -800f,
                0f
        );

        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(
                Dice,
                View.ROTATION,
                0f,
                360f
        );

        translationAnimator.setDuration(1000);
        rotationAnimator.setDuration(1000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationAnimator, rotationAnimator);
        animatorSet.start();
    }

    void Play() {
        DiceValue = (int) (Math.random() * 6) + 1;
        Log.i("Dice", "Rolled: " + DiceValue);
        UpdateDice();

        CurrentPos += DiceValue;

        if (CurrentPos > 60) {
            int excess = CurrentPos - 60;
            CurrentPos = 60 - excess;
        }

        if (CurrentPos == 60) {
            PlayFinishAudio();
        } else {
            PlayTransitionAudio();
        }

        UpdateBoxes();

        Animate();
    }

    void UpdateBoxes() {
        for (int i = 1; i < 61; i++) {
            int id = getResources().getIdentifier("box" + i, "id", getPackageName());
            TextView box = findViewById(id);

            if (i == CurrentPos) {
                box.setBackgroundResource(R.drawable.selected_border_color);
            } else {
                box.setBackgroundResource(R.drawable.border_color);
            }
        }
    }

    void UpdateDice() {
        switch (DiceValue) {
            case 1:
                Dice.setBackgroundResource(R.drawable.dice_z1);
                break;
            case 2:
                Dice.setBackgroundResource(R.drawable.dice_z2);
                break;
            case 3:
                Dice.setBackgroundResource(R.drawable.dice_z3);
                break;
            case 4:
                Dice.setBackgroundResource(R.drawable.dice_z4);
                break;
            case 5:
                Dice.setBackgroundResource(R.drawable.dice_z5);
                break;
            case 6:
                Dice.setBackgroundResource(R.drawable.dice_z6);
                break;
        }
    }

    void PlayTransitionAudio() {
        MediaPlayer.create(this, R.raw.transition).start();
    }

    void PlayFinishAudio() {
        MediaPlayer.create(this, R.raw.finish).start();
    }
}