package com.door2door.delivery.Utilities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    final static String DB_NAME="my_db";
    final static String TBL_CART="tbl_cart";
    final static String TBL_WISH="tbl_wish";

    final static String CART_01 ="ID";
    final static String CART_02 ="ITEMID";
    final static String CART_03 ="ITEMNAME";
    final static String CART_04 ="ITEMPRICE";
    final static String CART_05 ="UNITPRICE";
    final static String CART_06 ="DISCOUNT";
    final static String CART_07 ="QTY";
    final static String CART_08 ="IMG";
    final static String CART_09 ="COMPANYID";
    final static String CART_10 ="DESC";

    final static String WISH_01 ="W_ID";
    final static String WISH_02 ="W_ITEMID";
    final static String WISH_03 ="W_ITEMNAME";
    final static String WISH_04 ="W_ITEMPRICE";
    final static String WISH_05 ="W_UNITPRICE";
    final static String WISH_06 ="W_DISCOUNT";
    final static String WISH_07 ="W_QTY";
    final static String WISH_08 ="W_IMG";
    final static String WISH_09 ="W_DESC";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }
    String tb_cart="CREATE TABLE tbl_cart(ID INTEGER PRIMARY KEY AUTOINCREMENT,ITEMID TEXT UNIQUE,ITEMNAME TEXT,ITEMPRICE TEXT,UNITPRICE TEXT,DISCOUNT TEXT,QTY TEXT,IMG TEXT,COMPANYID TEXT,DESC TEXT)";

    String tb_wish="CREATE TABLE tbl_wish(W_ID INTEGER PRIMARY KEY AUTOINCREMENT,W_ITEMID TEXT UNIQUE,W_ITEMNAME TEXT,W_ITEMPRICE TEXT,W_UNITPRICE TEXT,W_DISCOUNT TEXT,W_QTY TEXT,W_IMG TEXT,W_DESC TEXT)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tb_cart);
        db.execSQL(tb_wish);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean insertCartData(String item_id,String item_name,String item_price,String unit_price,String item_discount,String item_qty,String image_url,String COMPANYID,String desc){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(CART_02,item_id);
        contentValues.put(CART_03,item_name);
        contentValues.put(CART_04,item_price);
        contentValues.put(CART_05,unit_price);
        contentValues.put(CART_06,item_discount);
        contentValues.put(CART_07,item_qty);
        contentValues.put(CART_08,image_url);
        contentValues.put(CART_09,COMPANYID);
        contentValues.put(CART_10,desc);
        try {
            Long result = db.insert(TBL_CART, null, contentValues);
            if (result == -1) {
                db.close();
                return false;
            } else {
                db.close();
                return true;
            }
        }catch (SQLiteConstraintException e){
            return false;
        }
    }
    public Cursor getCartData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+TBL_CART,null);
        return res;
    }
    public Cursor getCartCount(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT COUNT(ID) FROM "+TBL_CART,null);
        return res;
    }

    public boolean getItemByID(String itemID){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TBL_CART + " WHERE " + CART_02 + "='" + itemID +"'";
        Cursor cursor =  db.rawQuery(sql, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    public void deleteCartAll (Activity sActivity){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TBL_CART);
        SharedPref.setCartCount(0,sActivity);
        Log.e("DeleteAction",SharedPref.getCartCount(sActivity)+"");
    }
}

