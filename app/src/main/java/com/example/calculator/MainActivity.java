package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText first, second, res;
    private Button plus, minus, multi, div, his;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        action();
    }

    public void action() {
        first = findViewById(R.id.firstNumber);
        second = findViewById(R.id.secondNumber);
        res = findViewById(R.id.result);
        plus = findViewById(R.id.buttonPlus);
        minus = findViewById(R.id.buttonMinus);
        multi = findViewById(R.id.buttonMulti);
        div = findViewById(R.id.buttonDivision);
        his = findViewById(R.id.buttonHistory);

        plus.setOnClickListener(v -> actionButton("+", first, second));

        minus.setOnClickListener(v -> actionButton("-", first, second));

        multi.setOnClickListener(v -> actionButton("*", first, second));

        div.setOnClickListener(v -> actionButton("/", first, second));

        his.setOnClickListener(
                v -> {
                    Intent intent = new Intent(".History");
                    startActivity(intent);
                }
        );
    }

    private void actionButton(String mark, EditText first, EditText second) {
        if (first.getText().toString().equals("") || second.getText().toString().equals(""))
            res.setText("Одно из полей пустое");
        else if (second.getText().toString().equals("0") && mark.equals("/"))
            res.setText("Деление на ноль невозможно");
        else {
            float firstNum = Float.parseFloat(first.getText().toString());
            float secondNum = Float.parseFloat(second.getText().toString());
            float result;
            switch (mark) {
                case "+":
                    result = firstNum + secondNum;
                    res.setText(String.valueOf(result));
                    dbAction(firstNum, secondNum, result, mark);
                    break;
                case "-":
                    result = firstNum - secondNum;
                    res.setText(String.valueOf(result));
                    dbAction(firstNum, secondNum, result, mark);
                    break;
                case "*" :
                    result = firstNum * secondNum;
                    res.setText(String.valueOf(result));
                    dbAction(firstNum, secondNum, result, mark);
                    break;
                case "/":
                    result = firstNum / secondNum;
                    res.setText(String.valueOf(result));
                    dbAction(firstNum, secondNum, result, mark);
                    break;
            }
        }
    }

    private void dbAction(Float firstNum, Float secondNum, Float result, String mark) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("calculatorHistory.db", MODE_PRIVATE, null);
        ContentValues contentValues = new ContentValues();

        db.execSQL("CREATE TABLE IF NOT EXISTS history (numbers TEXT, result TEXT)");
        contentValues.put("numbers", firstNum + " " + mark + " " + secondNum);
        contentValues.put("result", String.valueOf(result));
        db.insert("history", null, contentValues);
        db.close();

        Toast.makeText(MainActivity.this, "Числа успешно сохранились", Toast.LENGTH_SHORT).show();
    }
}