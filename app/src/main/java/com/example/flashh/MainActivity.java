package com.example.flashh;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private boolean isShowingAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        isShowingAnswers = true;

        findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);

        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
            }
        });

        // *** ANSWER CHOICES ***
        findViewById(R.id.flashcard_answer_correct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer_correct).setBackgroundColor(
                        getResources().getColor(R.color.colorAnswerRight));
            }
        });

        findViewById(R.id.flashcard_answer_wrong1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer_correct).setBackgroundColor(
                        getResources().getColor(R.color.colorAnswerRight));

                findViewById(R.id.flashcard_answer_wrong1).setBackgroundColor(
                        getResources().getColor(R.color.colorAnswerWrong));
            }
        });

        findViewById(R.id.flashcard_answer_wrong2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer_correct).setBackgroundColor(
                        getResources().getColor(R.color.colorAnswerRight));

                findViewById(R.id.flashcard_answer_wrong2).setBackgroundColor(
                        getResources().getColor(R.color.colorAnswerWrong));
            }
        });

        findViewById(R.id.background_flash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer_correct).setBackgroundColor(
                        getResources().getColor(R.color.colorAnswerChoice));

                findViewById(R.id.flashcard_answer_wrong1).setBackgroundColor(
                        getResources().getColor(R.color.colorAnswerChoice));

                findViewById(R.id.flashcard_answer_wrong2).setBackgroundColor(
                        getResources().getColor(R.color.colorAnswerChoice));
            }
        });


        findViewById(R.id.toggle_choices_visibility).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowingAnswers)
                {
                    findViewById(R.id.flashcard_answer_correct).setVisibility(View.INVISIBLE);
                    findViewById(R.id.flashcard_answer_wrong1).setVisibility(View.INVISIBLE);
                    findViewById(R.id.flashcard_answer_wrong2).setVisibility(View.INVISIBLE);
                }
                else
                {
                    findViewById(R.id.flashcard_answer_correct).setVisibility(View.VISIBLE);
                    findViewById(R.id.flashcard_answer_wrong1).setVisibility(View.VISIBLE);
                    findViewById(R.id.flashcard_answer_wrong2).setVisibility(View.VISIBLE);
                }
                isShowingAnswers = !isShowingAnswers;
            }
        });
    }
}
