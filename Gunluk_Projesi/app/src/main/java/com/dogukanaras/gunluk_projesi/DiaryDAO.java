package com.dogukanaras.gunluk_projesi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class DiaryDAO {
    private SQLiteDatabase db;
    private DiaryDatabaseHelper dbHelper;

    public DiaryDAO(Context context) {
        dbHelper = new DiaryDatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertDiary(String title, String content) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        db.insert("diary", null, values);
    }

    public void updateDiary(long id, String title, String content) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        db.update("diary", values, "_id = ?", new String[]{String.valueOf(id)});
    }

    public List<String> getAllDiaries() {
        List<String> diaryList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM diary", null);
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(1);
                String content = cursor.getString(2);
                diaryList.add(title + "\n" + content);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return diaryList;
    }

    public Cursor getDiary(long id) {
        return db.rawQuery("SELECT * FROM diary WHERE _id = ?", new String[]{String.valueOf(id)});
    }
}