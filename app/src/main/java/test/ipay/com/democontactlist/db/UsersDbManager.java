package test.ipay.com.democontactlist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import test.ipay.com.democontactlist.model.SelectUser;


public class UsersDbManager {
    private static String TAG = UsersDbManager.class.getSimpleName();

    private static UsersDbManager instance;
    private Context mContext;

    private UsersDbManager(Context context) {
        this.mContext = context;
    }

    public static UsersDbManager getInstance(Context context) {
        if (instance == null) {
            instance = new UsersDbManager(context);
        }

        return instance;
    }

    /* get  data from table */
    public ArrayList<SelectUser> getDataFromDB(int limit) {
        ArrayList<SelectUser> modelList = new ArrayList<SelectUser>();
        DbHelper dbHelper = new DbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "select * from " + DbHelper.TABLE_NAME + " where iD > " + 0 + " limit " + limit;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                SelectUser model = new SelectUser();
                model.setName(cursor.getString(1));
                model.setPhone(cursor.getString(2));

                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        dbHelper.close();

        Log.d("contactList data", modelList.toString());

        return modelList;
    }

    public void saveAllUsers(ArrayList<SelectUser> allUsers) {

        for (SelectUser User : allUsers) {
            saveUser(User);
        }
        Log.d(TAG, "saveAllUsers");
    }

    public void saveUser(SelectUser User) {
        ContentValues cv = new ContentValues();
        DbHelper dbHelper = new DbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cv.put(DbHelper.USER_FULL_NAME, User.getName());
        cv.put(DbHelper.PHONE_NO, User.getPhone());
        db.insert(DbHelper.TABLE_NAME, null, cv);
        dbHelper.close();
    }

}

