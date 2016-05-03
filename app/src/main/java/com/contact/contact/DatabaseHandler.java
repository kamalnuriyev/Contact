package com.contact.contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kamal on 02.05.2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactManagerAPP";
    private static final String TABLE_CONTACTS = "contact";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_IMAGE_URI = "image_uri";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.v("DatabaseHandler", "DB Handler constructor method called");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("DatabaseHandler", "DB Handler onCreate method called");
        db.execSQL("CREATE TABLE "+TABLE_CONTACTS+" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_NAME+" TEXT, "+KEY_SURNAME+" TEXT, "+KEY_PHONE_NUMBER+" TEXT, "+KEY_IMAGE_URI+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTACTS);

        onCreate(db);
    }

    public void createContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_NAME, contact.getName());
        contentValues.put(KEY_SURNAME, contact.getSurname());
        contentValues.put(KEY_PHONE_NUMBER, contact.getPhoneNumber());
        contentValues.put(KEY_IMAGE_URI, contact.getImageUri().toString());

        db.insert(TABLE_CONTACTS, null, contentValues);
        db.close();
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID+"=?", new String[] {String.valueOf(contact.getID())});
        db.close();
    }

    public int updateContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int rowsAffected = 0;

        contentValues.put(KEY_NAME, contact.getName());
        contentValues.put(KEY_SURNAME, contact.getSurname());
        contentValues.put(KEY_PHONE_NUMBER, contact.getPhoneNumber());
        contentValues.put(KEY_IMAGE_URI, contact.getImageUri().toString());

        rowsAffected = db.update(TABLE_CONTACTS, contentValues, KEY_ID+"=?", new String[] {String.valueOf(contact.getID())});
        db.close();

        return rowsAffected;
    }

    public Contact getContact(int ID) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] {KEY_ID, KEY_NAME, KEY_SURNAME, KEY_PHONE_NUMBER, KEY_IMAGE_URI}, KEY_ID+"=?", new String[] {String.valueOf(ID)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Uri.parse(cursor.getString(4)));
        db.close();
        cursor.close();

        return contact;
    }

    public int getContactsCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_CONTACTS, null);
        int contactsCount = cursor.getCount();
        db.close();
        cursor.close();
        Log.v("DatabaseHandler", "DB Handler getContactsCount() method called and count is "+contactsCount);

        return contactsCount;
    }

    public List<Contact> getAllContactList() {
        List<Contact> contactList = new ArrayList<Contact>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_CONTACTS, null);

        if (cursor.moveToFirst()) {
            do {
                contactList.add(new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Uri.parse(cursor.getString(4))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.v("DatabaseHandler", "DB Handler getAllContactList() method called and list size is "+contactList.size());

        return contactList;
    }
}
