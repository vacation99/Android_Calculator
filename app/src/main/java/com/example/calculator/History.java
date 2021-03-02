package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class History extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        textView = findViewById(R.id.historyField);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("calculatorHistory.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS history (numbers TEXT, result TEXT)");
        Cursor cursor = db.rawQuery("SELECT * FROM history;", null);

        while (cursor.moveToNext()) {
            String numbers = cursor.getString(0);
            String res = cursor.getString(1);
            textView.append("\n" + "Числа: " + numbers + ", результат: " + res);
        }
        cursor.close();
        db.close();
    }

    public void clearHistory(View view) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("calculatorHistory.db", MODE_PRIVATE, null);
        db.execSQL("DROP TABLE history");
        db.close();

        textView = findViewById(R.id.historyField);
        textView.setText("Последние операции:");

        Toast.makeText(History.this, "История успешно очистилась", Toast.LENGTH_SHORT).show();
    }
}