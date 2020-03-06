package com.example.flashhh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {

    String question;
    String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

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
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });



    }

}
