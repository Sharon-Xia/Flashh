package com.example.flashhh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddCardActivity extends AppCompatActivity {

    String question;
    String answer;
    String w1;
    String w2;
    boolean edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        // *** INITIALIZE ***
        if (getIntent() != null) // if edit is called
        {
            question = getIntent().getStringExtra("question");
            answer = getIntent().getStringExtra("answer");
            w1 = getIntent().getStringExtra("wrongAnswer1");
            w2 = getIntent().getStringExtra("wrongAnswer2");


            EditText insertQuestion = findViewById(R.id.insert_question);
            insertQuestion.setText(question);

            EditText answerToQuestion = findViewById(R.id.insert_answerToQuestion);
            answerToQuestion.setText(answer);

            EditText wrongAnswer1 = findViewById(R.id.insert_wrongAnswer1);
            wrongAnswer1.setText(w1);

            EditText wrongAnswer2 = findViewById(R.id.insert_wrongAnswer2);
            wrongAnswer2.setText(w2);
        }


        // *** EXIT ACTIVITY ***
        findViewById(R.id.cancel_add_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });


        findViewById(R.id.save_card_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText insertQuestion = findViewById(R.id.insert_question);
                question = insertQuestion.getText().toString();

                EditText answerToQuestion = findViewById(R.id.insert_answerToQuestion);
                answer = answerToQuestion.getText().toString();

                EditText wrongAnswer1 = findViewById(R.id.insert_wrongAnswer1);
                w1 = wrongAnswer1.getText().toString();

                EditText wrongAnswer2 = findViewById(R.id.insert_wrongAnswer2);
                w2 = wrongAnswer2.getText().toString();


                if (question.equals("") || answer.equals(""))
                {
                    Toast.makeText(getApplicationContext(),
                            "Must enter both question and answer", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent data = new Intent();
                    data.putExtra("question", question);
                    data.putExtra("answer", answer);
                    data.putExtra("wrongAnswer1", w1);
                    data.putExtra("wrongAnswer2", w2);
                    data.putExtra("edit", edit);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });



    }

}
