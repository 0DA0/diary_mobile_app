package com.dogukanaras.gunluk_projesi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class NewDiaryList extends AppCompatActivity {

    private EditText edtTitle, edtContent;
    private Button btnSave;
    private DiaryDAO diaryDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_diary_list);

        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        btnSave = findViewById(R.id.btnSave);

        diaryDAO = new DiaryDAO(this);
        diaryDAO.open();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();
                diaryDAO.insertDiary(title, content);
                finish(); // Aktiviteyi sonlandır
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        diaryDAO.close();
    }
}