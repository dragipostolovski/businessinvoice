package com.thevividworld.businessinvoice.businessinvoice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dragi Postolovski on 18-Aug-17.
 */

public class Database extends SQLiteOpenHelper {

    private static final String TAG = "Database";

    //your database
    private static final String DATABASE_NAME = "invoices.db";
    private static final int DATABASE_VERSION = 1;

    //invoice table
    private static final String INVOICE_TABLE = "invoice";
    private static final String USER_ID = "ID";
    private static final String INVOICE_NUMBER = "invoice_number";
    private static final String FIRST_NAME = "first_name";

    //client table
    private static final String CLIENT_TABLE = "client";
    private static final String CLIENT_ID = "client_ID";
    private static final String CLIENT_FIRST_NAME = "client_first_name";
    private static final String CLIENT_LAST_NAME = "client_last_name";
    private static final String CLIENT_PHONE_NUMBER = "client_phone_number";
    private static final String CLIENT_CITY = "client_city";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createInvoicesTable = "CREATE TABLE " + INVOICE_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                INVOICE_NUMBER +" TEXT, " + FIRST_NAME + " TEXT)";
        String createClientsTable = "CREATE TABLE " + CLIENT_TABLE + " (" + CLIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CLIENT_FIRST_NAME +" TEXT, " + CLIENT_LAST_NAME + " TEXT, " +
                CLIENT_PHONE_NUMBER + " TEXT, " + CLIENT_CITY + " TEXT)";

        sqLiteDatabase.execSQL(createInvoicesTable);
        sqLiteDatabase.execSQL(createClientsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + INVOICE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CLIENT_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String invoiceNumber, String firstName) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(INVOICE_NUMBER, invoiceNumber);
        contentValues.put(FIRST_NAME, firstName);

        Log.d(TAG, "addData: Adding " + invoiceNumber + " and " + firstName + " to " + INVOICE_TABLE);

        long result = sqLiteDatabase.insert(INVOICE_TABLE, null, contentValues);
        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addClient(String firstName, String lastName, String phoneNumber, String city) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues clientValues = new ContentValues();
        clientValues.put(CLIENT_FIRST_NAME, firstName);
        clientValues.put(CLIENT_LAST_NAME, lastName);
        clientValues.put(CLIENT_PHONE_NUMBER, phoneNumber);
        clientValues.put(CLIENT_CITY, city);

        Log.d(TAG, "addData: Adding " + firstName + " and " + lastName + " to " + INVOICE_TABLE);

        long result = sqLiteDatabase.insert(CLIENT_TABLE, null, clientValues);
        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns all the data from database
     * @return
     */
    public Cursor getData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT * FROM " + INVOICE_TABLE;
        Cursor data = sqLiteDatabase.rawQuery(query, null);
        return data;
    }

    /**
     *
     * @return
     */
    public Cursor getClient(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String queryClients = "SELECT * FROM " + CLIENT_TABLE;
        Cursor clients = sqLiteDatabase.rawQuery(queryClients, null);
        return clients;
    }

    /**
     * Returns only the ID that matches the name passed in
     * @param invoiceNumber
     * @return
     */
    public Cursor getItemID(String invoiceNumber){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT " + USER_ID + " FROM " + INVOICE_TABLE +
                " WHERE " + INVOICE_NUMBER + " = '" + invoiceNumber + "'";
        Cursor data = sqLiteDatabase.rawQuery(query, null);
        return data;
    }

    /**
     * Delete from database
     * @param itemID
     */
    public void deleteName(int itemID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "DELETE FROM " + INVOICE_TABLE + " WHERE "
                + USER_ID + " = '" + itemID + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + itemID + " from database.");
        sqLiteDatabase.execSQL(query);
    }


}
