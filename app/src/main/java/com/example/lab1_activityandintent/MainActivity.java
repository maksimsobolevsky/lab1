package com.example.lab1_activityandintent;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText question;
    private Button submit;
    private TextView lastAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.question = (EditText)this.findViewById(R.id.inputQuestion);
        this.lastAnswer = (TextView) this.findViewById(R.id.textAnswer);

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        switch (result.getResultCode()){
                            case Activity.RESULT_OK: {
                                Intent data = result.getData();
                                String answerString = data.getStringExtra("ANSWER_TEXT");
                                MainActivity.this.lastAnswer.setText(answerString);
                            break;
                            }
                            case Activity.RESULT_CANCELED: {
                                String answerString = "операция отменена";
                                MainActivity.this.lastAnswer.setText(answerString);
                                break;
                            }

                        }
                    }
                });

        this.submit = (Button)this.findViewById(R.id.buttonSubmitQuestion);
        this.submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                String questionText = MainActivity.this.question.getText().toString();
                intent.putExtra("QUESTION_TEXT", questionText);
                someActivityResultLauncher.launch(intent);
            }
        });
    }
}