package com.dogukanaras.gunluk_projesi;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class DiaryList extends AppCompatActivity {

    private ListView listView;
    private Button btnNewEntry, btnSettings, btnAbout;
    private DiaryDAO diaryDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_list);

        listView = findViewById(R.id.listView);
        btnNewEntry = findViewById(R.id.btnNewEntry);
        btnSettings = findViewById(R.id.btnSettings);
        btnAbout = findViewById(R.id.btnAbout);

        diaryDAO = new DiaryDAO(this);
        diaryDAO.open();

        // Günlük listesini al ve listView'e bağla
        List<String> diaryList = diaryDAO.getAllDiaries();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, diaryList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = diaryDAO.getDiary(id);
                if (cursor.moveToFirst()) {
                    long diaryId = cursor.getLong(0);
                    String title = cursor.getString(1);
                    String content = cursor.getString(2);

                    Intent intent = new Intent(DiaryList.this, EditDiaryEnetry.class);
                    intent.putExtra("DIARY_ID", diaryId);
                    intent.putExtra("DIARY_TITLE", title);
                    intent.putExtra("DIARY_CONTENT", content);
                    startActivity(intent);
                }
                cursor.close();
            }
        });

        btnNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryList.this, NewDiaryList.class);
                startActivity(intent);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryList.this, Settings.class);
                startActivity(intent);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryList.this, About.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Günlük listesini güncelle
        List<String> diaryList = diaryDAO.getAllDiaries();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, diaryList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        diaryDAO.close();
    }
}