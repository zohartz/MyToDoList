package mygui.com.zohar.mytodolist;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_location = "location";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_MEMNAME = "memname";
    public static final String KEY_STATUS = "status";




    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CATEGORY + " TEXT,"
                + KEY_PRIORITY + " TEXT," + KEY_location + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_MEMNAME + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addProduct(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY, task.get_category()); // task category
        values.put(KEY_PRIORITY, task.get_priority()); // task priority
        values.put(KEY_location, task.get_location()); // task location
        values.put(KEY_DESCRIPTION, task.get_description()); // task des
        values.put(KEY_MEMNAME, task.get_memName()); // task teamName


        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    Task getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_CATEGORY, KEY_PRIORITY, KEY_location, KEY_DESCRIPTION, KEY_MEMNAME }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Task task = new Task(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4), cursor.getString(5));
        // return contact
        return task;
    }

    // Getting All Contacts
    public List<Task> getAllContacts() {
        List<Task> contactList = new ArrayList<Task>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) do {
            Task task = new Task();
            task.set_id(Integer.parseInt(cursor.getString(0)));
            task.set_category(cursor.getString(1));
            task.set_priorityy(cursor.getString(2));
            task.set_location(cursor.getString(3));
            task.set_description(cursor.getString(4));
            task.set_memName(cursor.getString(5));
            // Adding contact to list
            contactList.add(task);
        } while (cursor.moveToNext());

        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateContact(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY, task.get_category());
        values.put(KEY_PRIORITY, task.get_priority());
        values.put(KEY_location, task.get_location());
        values.put(KEY_DESCRIPTION, task.get_description());
        values.put(KEY_MEMNAME, task.get_memName());


        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(task.get_id()) });
    }

    // Deleting single contact
    public void deleteContact(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(task.get_id()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


}
