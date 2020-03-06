package com.example.flashhh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private boolean isShowingAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        isShowingAnswers = false;

        findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
        findViewById(R.id.toggle_choices_visibility).setVisibility(View.INVISIBLE);
        makeMultipleChoiceInvisible();

        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShowingAnswers)
                { // mc needs to be off in order to use flashcard flip-mode
                    findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                    findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
                }
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
                resetMultipleChoiceColor();
            }
        });


        findViewById(R.id.toggle_choices_visibility).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowingAnswers) {
                    makeMultipleChoiceInvisible();
                } else {
                    makeMultipleChoiceVisible();
                }
                isShowingAnswers = !isShowingAnswers;
            }
        });


        // *** CREATING NEW ACTIVITY ***
        findViewById(R.id.add_card_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });


        findViewById(R.id.edit_card_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);

                TextView q = findViewById(R.id.flashcard_question);
                intent.putExtra("question", q.getText());

                TextView a = findViewById(R.id.flashcard_answer);
                intent.putExtra("answer", a.getText());

                TextView w1 = findViewById(R.id.flashcard_answer_wrong1);
                intent.putExtra("wrongAnswer1", w1.getText());

                TextView w2 = findViewById(R.id.flashcard_answer_wrong2);
                intent.putExtra("wrongAnswer2", w2.getText());

                MainActivity.this.startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && data != null && resultCode == RESULT_OK) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String question = data.getExtras().getString("question"); // 'string1' needs to match the key we used when we put the string in the Intent
            String answer = data.getExtras().getString("answer");
            String wrongAnswer1 = data.getExtras().getString("wrongAnswer1");
            String wrongAnswer2 = data.getExtras().getString("wrongAnswer2");

            TextView q = findViewById(R.id.flashcard_question);
            q.setText(question);

            TextView a = findViewById(R.id.flashcard_answer);
            a.setText(answer);

            TextView mcA = findViewById(R.id.flashcard_answer_correct);
            TextView w1 = findViewById(R.id.flashcard_answer_wrong1);
            TextView w2 = findViewById(R.id.flashcard_answer_wrong2);

            mcA.setText(answer);
            w1.setText(wrongAnswer1);
            w2.setText(wrongAnswer2);

            if (!wrongAnswer1.equals("") && !wrongAnswer2.equals("")) // MC options
            {
                makeMultipleChoiceVisible();
                resetMultipleChoiceColor();
                isShowingAnswers = true;
            }
            else  // Flash Card Flip Option
            {
                if (isShowingAnswers) // ** MAKE MC OPTIONS INVISIBLE
                {
                    makeMultipleChoiceInvisible();
                    resetMultipleChoiceColor();
                    isShowingAnswers = !isShowingAnswers;
                }
            }

            Snackbar.make(findViewById(R.id.flashcard_question),
                    "Flashcard successfully created", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void makeMultipleChoiceInvisible()
    {
        findViewById(R.id.flashcard_answer_correct).setVisibility(View.INVISIBLE);
        findViewById(R.id.flashcard_answer_wrong1).setVisibility(View.INVISIBLE);
        findViewById(R.id.flashcard_answer_wrong2).setVisibility(View.INVISIBLE);
    }

    private void makeMultipleChoiceVisible()
    {
        findViewById(R.id.flashcard_answer_correct).setVisibility(View.VISIBLE);
        findViewById(R.id.flashcard_answer_wrong1).setVisibility(View.VISIBLE);
        findViewById(R.id.flashcard_answer_wrong2).setVisibility(View.VISIBLE);
    }

    private void resetMultipleChoiceColor()
    {
        findViewById(R.id.flashcard_answer_correct).setBackgroundColor(
                getResources().getColor(R.color.colorAnswerChoice));

        findViewById(R.id.flashcard_answer_wrong1).setBackgroundColor(
                getResources().getColor(R.color.colorAnswerChoice));

        findViewById(R.id.flashcard_answer_wrong2).setBackgroundColor(
                getResources().getColor(R.color.colorAnswerChoice));
    }
}
