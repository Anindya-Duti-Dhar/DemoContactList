package test.ipay.com.democontactlist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    private String TAG = DbHelper.class.getSimpleName();

    private static final String DB_NAME = "ipaycontacts";

    public static final String DB_COLUMN_ID = "iD";
    public static final String USER_FULL_NAME = "userFullName";
    public static final String PHONE_NO= "phoneNo";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "calllist";
    public static final String TABLE_STATEMENT1 = "create table "+TABLE_NAME +"(iD INTEGER PRIMARY KEY AUTOINCREMENT,userFullName TEXT,phoneNo TEXT)";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "--- onCreate database ---");
        // statement for contact table
        db.execSQL(TABLE_STATEMENT1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
