package com.dogukanaras.gunluk_projesi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditDiaryEnetry extends AppCompatActivity {

    private EditText edtTitle, edtContent;
    private Button btnSave;
    private DiaryDAO diaryDAO;
    private long diaryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_diary_enetry);

        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        btnSave = findViewById(R.id.btnSave);

        diaryDAO = new DiaryDAO(this);
        diaryDAO.open();

        // Intent'ten veri al
        Intent intent = getIntent();
        diaryId = intent.getLongExtra("DIARY_ID", -1);
        String title = intent.getStringExtra("DIARY_TITLE");
        String content = intent.getStringExtra("DIARY_CONTENT");

        edtTitle.setText(title);
        edtContent.setText(content);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedTitle = edtTitle.getText().toString();
                String updatedContent = edtContent.getText().toString();
                diaryDAO.updateDiary(diaryId, updatedTitle, updatedContent);
                finish();
            }  
        });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        diaryDAO.close();
    }
}