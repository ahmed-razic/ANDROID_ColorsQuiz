package com.example.colorquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int score = 0;
    String[] addresses = {"ahmed.razic@gmail.com", "ahmed.razic@gmail.org", "ahmed.razic@gmail.net"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submit(View view) {

        calculateScore();

        EditText editTextName = findViewById(R.id.name_text_field);
        Editable editableName = editTextName.getText();
        String name = editableName.toString();

        boolean isPassed = calculateIsPassed(score);
        int award = calculateAward(score);
        String message = resultMessage(name, score, isPassed, award);

        displayToast(message);
        displayResult(message);
        composeEmail(addresses, getString(R.string.subject_email, name), message);
    }

    private void displayToast(String message) {

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    private void calculateScore() {

        RadioButton radioButton = findViewById(R.id.q1_radiobutton3);
        if (radioButton.isChecked()) {
            score += 1;
            Log.i("Answer1", Integer.toString(score));
        }

        EditText editTextQ2 = findViewById(R.id.q2_text_field);
        Editable editableQ2 = editTextQ2.getText();
        String answerQ2 = editableQ2.toString();

        if (answerQ2.equals("darker")) {
            score += 1;
            Log.i("Answer2", Integer.toString(score));
        }

        CheckBox checkBox = findViewById(R.id.q3_checkbox_4);
        if (checkBox.isChecked()) {
            score += 1;
            Log.i("Answer3", Integer.toString(score));
        }

        EditText editTextQ4 = findViewById(R.id.q4_text_field);
        Editable editableQ4 = editTextQ4.getText();
        String answerQ4 = editableQ4.toString();

        if (answerQ4.equals("hue")) {
            score += 1;
            Log.i("Answer4", Integer.toString(score));
        }
    }

    private int calculateAward(int correctAnswers) {
        return correctAnswers * 2;
    }

    private boolean calculateIsPassed(int score) {
        if (score >= 3) {
            return true;
        } else {
            return false;
        }
    }

    private String resultMessage(String name, int score, boolean passed, int award) {
        String message = getString(R.string.name, name);
        message += "\n" + getString(R.string.score, score);
        message += "\n" + getString(R.string.passed, passed);
        if (passed == true) {
            message += "\n" + getString(R.string.award, NumberFormat.getCurrencyInstance().format(award));
        }
        message += "\n" + getString(R.string.thanks);

        return message;
    }

    private void displayResult(String message) {
        TextView result = findViewById(R.id.result_textview);
        result.setText(message);
    }

    private void composeEmail(String[] addresses, String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(intent);
    }
}
