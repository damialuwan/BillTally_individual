package com.example.individual3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import com.example.individual3.BillData;
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ElectricityBills.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "bills";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_MONTH = "month";
    private static final String COLUMN_KWH = "kWhUsed";
    private static final String COLUMN_TOTAL = "totalCharges";
    private static final String COLUMN_REBATE = "rebatePercentage";
    private static final String COLUMN_FINAL_COST = "finalCost";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_MONTH + " TEXT, "
                + COLUMN_KWH + " INTEGER, "
                + COLUMN_TOTAL + " REAL, "
                + COLUMN_REBATE + " INTEGER, "
                + COLUMN_FINAL_COST + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to save a bill record
    public void saveBill(String month, int kWhUsed, double totalCharges, int rebatePercentage, double finalCost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MONTH, month);
        values.put(COLUMN_KWH, kWhUsed);
        values.put(COLUMN_TOTAL, totalCharges);
        values.put(COLUMN_REBATE, rebatePercentage);
        values.put(COLUMN_FINAL_COST, finalCost);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Method to get all saved bills
    public List<String> getAllMonths() {
        List<String> months = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + COLUMN_MONTH + " FROM " + TABLE_NAME, null);

        while (cursor.moveToNext()) {
            months.add(cursor.getString(0));
        }

        cursor.close();
        db.close();
        return months;
    }

    // Method to get bill details for a selected month
    public BillData getBillDetails(String month) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM bills WHERE month=?", new String[]{month});

        if (cursor.moveToFirst()) {
            BillData bill = new BillData(
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getDouble(3),
                    cursor.getInt(4),
                    cursor.getDouble(5)
            );
            cursor.close();
            db.close();
            return bill;
        }

        cursor.close();
        db.close();
        return null; // Prevents crash if no data exists
    }
}
