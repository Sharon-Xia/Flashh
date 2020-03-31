package com.example.flashhh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Random;

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
        allFlashcards = flashcardDatabase.getAllCards();
        isShowingAnswers = false;


        //makeMultipleChoiceInvisible();
        findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);

        updateCard(true);


        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("hi", "" + allFlashcards.size());
                if (allFlashcards.size() != 0 && !isShowingAnswers)
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
                findViewById(R.id.flashcard_answer_correct).setBackground(
                        getResources().getDrawable(R.drawable.multiple_choice_correct));
            }
        });

        findViewById(R.id.flashcard_answer_wrong1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer_correct).setBackground(
                        getResources().getDrawable(R.drawable.multiple_choice_correct));

                findViewById(R.id.flashcard_answer_wrong1).setBackground(
                        getResources().getDrawable(R.drawable.multiple_choice_wrong));

                findViewById(R.id.flashcard_answer_wrong2).setBackground(
                        getResources().getDrawable(R.drawable.multiple_choice_background));

            }
        });

        findViewById(R.id.flashcard_answer_wrong2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer_correct).setBackground(
                        getResources().getDrawable(R.drawable.multiple_choice_correct));

                findViewById(R.id.flashcard_answer_wrong1).setBackground(
                        getResources().getDrawable(R.drawable.multiple_choice_background));

                findViewById(R.id.flashcard_answer_wrong2).setBackground(
                        getResources().getDrawable(R.drawable.multiple_choice_wrong));
            }
        });


        findViewById(R.id.background_flash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetMultipleChoiceColor();
            }
        });



        // *** CREATING NEW ACTIVITY ***
        findViewById(R.id.add_card_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                intent.putExtra("edit", false);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });


        findViewById(R.id.edit_card_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allFlashcards.size() != 0)
                {
                    Intent intent = new Intent(MainActivity.this, AddCardActivity.class);

                    intent.putExtra("edit", true);

                    TextView q = findViewById(R.id.flashcard_question);
                    intent.putExtra("question", q.getText());

                    TextView a = findViewById(R.id.flashcard_answer);
                    intent.putExtra("answer", a.getText());

                    TextView w1 = findViewById(R.id.flashcard_answer_wrong1);
                    intent.putExtra("wrongAnswer1", w1.getText());

                    TextView w2 = findViewById(R.id.flashcard_answer_wrong2);
                    intent.putExtra("wrongAnswer2", w2.getText());

                    flashcardDatabase.deleteCard((String) q.getText());

                    MainActivity.this.startActivityForResult(intent, 100);
                }
                else {
                    Log.i("error", "cannot edit a card that doesn't exist");
                }
            }
        });


        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // changes the current index & displays card
                updateCard(true);

                if (allFlashcards.size() != 0 &&
                        !allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1().equals("") &&
                        !allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2().equals("")) // MC options
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


        findViewById(R.id.trash_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allFlashcards.size() != 0)
                {
                    flashcardDatabase.deleteCard(((TextView) findViewById(R.id.flashcard_question)).getText().toString());
                    allFlashcards = flashcardDatabase.getAllCards();
                    updateCard(true);
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

            if (!data.getExtras().getBoolean("edit")) { // create new card
                flashcardDatabase.insertCard(new Flashcard(question, answer, wrongAnswer1, wrongAnswer2));
                allFlashcards = flashcardDatabase.getAllCards();
                currentCardDisplayedIndex = allFlashcards.size() - 1; // depends on prev line
                Log.i("Index after addCard", "" + currentCardDisplayedIndex);
                Log.i("Size after addCard", "" + allFlashcards.size());

                updateCard(false);

                Snackbar.make(findViewById(R.id.flashcard_question),
                        "Flashcard successfully created", Snackbar.LENGTH_SHORT).show();
            }
            else { // edit card
                flashcardDatabase.insertCard(new Flashcard(question, answer, wrongAnswer1, wrongAnswer2));
                allFlashcards = flashcardDatabase.getAllCards();
                currentCardDisplayedIndex = allFlashcards.size() - 1;
                Log.i("Editing card", "updating");
                // index stays the same

                updateCard(false);

                Snackbar.make(findViewById(R.id.flashcard_question),
                        "Flashcard successfully edited", Snackbar.LENGTH_SHORT).show();
            }
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
        findViewById(R.id.flashcard_answer_correct).setBackground(
                getResources().getDrawable(R.drawable.multiple_choice_background));

        findViewById(R.id.flashcard_answer_wrong1).setBackground(
                getResources().getDrawable(R.drawable.multiple_choice_background));

        findViewById(R.id.flashcard_answer_wrong2).setBackground(
                getResources().getDrawable(R.drawable.multiple_choice_background));
    }


    private void updateCard(boolean random)
    {
        Log.i("debug", "CardList size is " + allFlashcards.size());
        if (allFlashcards.size() == 0)
        {
            ((TextView) findViewById(R.id.flashcard_question)).setText("Add a card :)");
        }
        else if(allFlashcards.size() > 1)
        {
            while(random && true)
            {
                int i = getRandomNumber(0, allFlashcards.size()-1);

                if (i != currentCardDisplayedIndex)
                {
                    currentCardDisplayedIndex = i;
                    break;
                }
            }
            // set the question and answer TextViews with data from the database
        }
        // if size == 1, everything stays the same
        else // if size == 1, everything stays the same
        {
            currentCardDisplayedIndex = 0;
        }
        updateCardMCVisuals();
        updateOpacitiesOfIcons();
    }


    private void updateCardMCVisuals()
    {
        if (allFlashcards.size() > 0)
        {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
            ((TextView) findViewById(R.id.flashcard_answer_correct)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
            ((TextView) findViewById(R.id.flashcard_answer_wrong1)).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
            ((TextView) findViewById(R.id.flashcard_answer_wrong2)).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());


            if (!allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1().equals("")
                    && !allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2().equals("")) // MC options
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
                    isShowingAnswers = false;
                }
            }
        }
        else
        {
            makeMultipleChoiceInvisible();
            resetMultipleChoiceColor();
            isShowingAnswers = false;
        }
    }


    private void updateOpacitiesOfIcons()
    {
        if (allFlashcards.size() <= 1)
        {
            ((ImageView)findViewById(R.id.next_button)).setAlpha(Integer.parseInt("100"));
        }
        else
        {
            ((ImageView)findViewById(R.id.next_button)).setAlpha(Integer.parseInt("255")); // set next button opacity to 100%
        }

        if (allFlashcards.size() == 0)
        {
            ((ImageView)findViewById(R.id.trash_button)).setAlpha(Integer.parseInt("100"));
            ((ImageView)findViewById(R.id.edit_card_icon)).setAlpha(Integer.parseInt("100"));
            Log.i("debug", "opacity works");
        }
        else
        {
            ((ImageView)findViewById(R.id.trash_button)).setAlpha(Integer.parseInt("255"));
            ((ImageView)findViewById(R.id.edit_card_icon)).setAlpha(Integer.parseInt("255"));
        }

    }


    // returns a random number between minNumber and maxNumber, inclusive.
    // for example, if i called getRandomNumber(1, 3), there's an equal chance of it returning either 1, 2, or 3.
    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }
}
