package com.amoor.el_rawda.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.amoor.el_rawda.data.model.Product;

import java.util.ArrayList;

public class DBConnection extends SQLiteOpenHelper {
    public static final String DbName = "products";
    public static final String TABLE_NAME = "products_table";
    public static final String COL_ID = "_ID";
    public static final String COL__PRO_ID = "pro_id";
    public static final String COL_NAME = "pro_name";
    public static final String COL_PRICE = "pro_price";
    public static final int version = 1;

    public DBConnection(Context context) {
        super(context, DbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME + " ( " +
                COL_ID + " INTEGER primary key autoincrement, " +
                COL__PRO_ID + " text, " +
                COL_NAME + " text, " +
                COL_PRICE + " text )";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String sql = "Drop table " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public void insertProductRow(Context context, String product_id, String product_name, String product_price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("pro_id", product_id);
        contentValues.put("pro_name", product_name);
        contentValues.put("pro_price", product_price);
        long row = db.insert(TABLE_NAME, null, contentValues);
        if (row > 0) {
            Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
        }

    }

    public ArrayList<Product> getAllRecords() {
        String cols[] = {COL__PRO_ID, COL_NAME, COL_PRICE};
        ArrayList<Product> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, cols, null, null, null, null, COL_NAME);

        if (cursor.moveToFirst()) {
            do {
                int pro_id_index = cursor.getColumnIndex("pro_id");
                String pro_id = cursor.getString(pro_id_index);
                int pro_name_index = cursor.getColumnIndex("pro_name");
                String pro_name = cursor.getString(pro_name_index);
                int pro_price_index = cursor.getColumnIndex("pro_price");
                String pro_price = cursor.getString(pro_price_index);
                arrayList.add(new Product(pro_id, pro_name, pro_price));
            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    public void deleteProduct(String product_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from products_table where pro_id=" + product_id);
    }

    public void updateR(String product_id, Integer id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("update product set product_id=" + product_id + "where product_id=" + Integer.toString(id));
    }

    public ArrayList<Product> getSearch(String search_text)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Product> result_list = new ArrayList<>();
        String[] result_cols = {COL__PRO_ID, COL_NAME, COL_PRICE};
        String whereClause = COL_NAME + " like ? OR " + COL__PRO_ID + " like ?";
        String[] whereArgs = new String[]{search_text+"%",search_text+"%"};
        Cursor cursor = db.query( true,TABLE_NAME, result_cols, whereClause, whereArgs, null, null, COL_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                int pro_id_index = cursor.getColumnIndex("pro_id");
                String pro_id = cursor.getString(pro_id_index);
                int pro_name_index = cursor.getColumnIndex("pro_name");
                String pro_name = cursor.getString(pro_name_index);
                int pro_price_index = cursor.getColumnIndex("pro_price");
                String pro_price = cursor.getString(pro_price_index);
                result_list.add(new Product(pro_id, pro_name, pro_price));
            } while (cursor.moveToNext());

        }
        return result_list;

    }

}