package com.example.flashhh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private boolean isShowingAnswers;
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        isShowingAnswers = false;

        findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
        findViewById(R.id.toggle_choices_visibility).setVisibility(View.INVISIBLE);
        makeMultipleChoiceInvisible();


        if (allFlashcards != null && allFlashcards.size() > 0)
        {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());

            ((TextView)findViewById(R.id.flashcard_answer_correct)).setText(allFlashcards.get(0).getAnswer());
            ((TextView)findViewById(R.id.flashcard_answer_wrong1)).setText(allFlashcards.get(0).getWrongAnswer1());
            ((TextView)findViewById(R.id.flashcard_answer_wrong2)).setText(allFlashcards.get(0).getWrongAnswer2());
        }


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


        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                // set the question and answer TextViews with data from the database
                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                ((TextView)findViewById(R.id.flashcard_answer_correct)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                ((TextView)findViewById(R.id.flashcard_answer_wrong1)).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                ((TextView)findViewById(R.id.flashcard_answer_wrong2)).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());


                if (!allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1().equals("") && !allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2().equals("")) // MC options
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

            flashcardDatabase.insertCard(new Flashcard(question, answer, wrongAnswer1, wrongAnswer2));
            allFlashcards = flashcardDatabase.getAllCards();

            ((TextView)findViewById(R.id.flashcard_question)).setText(question);
            ((TextView)findViewById(R.id.flashcard_answer)).setText(answer);
            ((TextView)findViewById(R.id.flashcard_answer_correct)).setText(answer);
            ((TextView)findViewById(R.id.flashcard_answer_wrong1)).setText(wrongAnswer1);
            ((TextView)findViewById(R.id.flashcard_answer_wrong2)).setText(wrongAnswer2);


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
