package com.example.flashhh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private boolean isShowingAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        isShowingAnswers = false;

        findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
        findViewById(R.id.flashcard_answer_correct).setVisibility(View.INVISIBLE);
        findViewById(R.id.flashcard_answer_wrong1).setVisibility(View.INVISIBLE);
        findViewById(R.id.flashcard_answer_wrong2).setVisibility(View.INVISIBLE);

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

                findViewById(R.id.flashcard_answer_wrong2).setBackgroundColor(
                        getResources().getColor(R.color.colorAnswerChoice));

            }
        });

        findViewById(R.id.flashcard_answer_wrong2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer_correct).setBackgroundColor(
                        getResources().getColor(R.color.colorAnswerRight));

                findViewById(R.id.flashcard_answer_wrong1).setBackgroundColor(
                        getResources().getColor(R.color.colorAnswerChoice));

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


        // *** CREATING NEW ACTIVITY ***
        findViewById(R.id.add_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && data != null && resultCode == RESULT_OK) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String question = data.getExtras().getString("question"); // 'string1' needs to match the key we used when we put the string in the Intent
            String answer = data.getExtras().getString("answer");

            TextView q = findViewById(R.id.flashcard_question);
            q.setText(question);

            TextView a = findViewById(R.id.flashcard_answer);
            a.setText(answer);

            if (isShowingAnswers)
            {
                findViewById(R.id.toggle_choices_visibility).performClick(); // make 'em invisible
                findViewById(R.id.background_flash).performClick(); // reset coloring
            }
            findViewById(R.id.toggle_choices_visibility).setVisibility(View.INVISIBLE);
        }
    }
}
