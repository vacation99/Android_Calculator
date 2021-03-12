package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
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

        first = findViewById(R.id.firstNumber);
        second = findViewById(R.id.secondNumber);
        res = findViewById(R.id.result);
        plus = findViewById(R.id.buttonPlus);
        minus = findViewById(R.id.buttonMinus);
        multi = findViewById(R.id.buttonMulti);
        div = findViewById(R.id.buttonDivision);
        his = findViewById(R.id.buttonHistory);
    }

    public void plusAction(View view) {
        actionButton("+", first, second);
    }

    public void minusAction(View view) {
        actionButton("-", first, second);
    }

    public void  multiAction(View view) {
        actionButton("*", first, second);
    }

    public void divAction(View view) {
        actionButton("/", first, second);
    }

    public void hisAction(View view) {
        Intent intent = new Intent(".History");
        startActivity(intent);
    }

    private void actionButton(String mark, EditText first, EditText second) {
        if (first.getText().toString().equals("") || second.getText().toString().equals(""))
            res.setText("Одно из полей пустое");
        else if (second.getText().toString().equals("0") && mark.equals("/"))
            res.setText("Деление на ноль невозможно");
        else {
            float firstNum = Float.parseFloat(first.getText().toString());
            float secondNum = Float.parseFloat(second.getText().toString());
            float result = 0;
            switch (mark) {
                case "+":
                    result = firstNum + secondNum;
                    break;
                case "-":
                    result = firstNum - secondNum;
                    break;
                case "*" :
                    result = firstNum * secondNum;
                    break;
                case "/":
                    result = firstNum / secondNum;
                    break;
            }
            res.setText(String.valueOf(result));
            dbAction(firstNum, secondNum, result, mark);
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