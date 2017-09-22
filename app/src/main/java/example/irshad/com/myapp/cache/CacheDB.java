package example.irshad.com.myapp.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Irshad on 22/9/17.
 */

public class CacheDB extends SQLiteOpenHelper {


    public CacheDB(Context context) {
        super(context, DBColumns.DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE " + DBColumns.TABLE_NAME + "(" + DBColumns._ID + " INTEGER PRIMARY KEY," + DBColumns.SEARCH_STRING + " TEXT," + DBColumns.SEARCH_RESPONSE + " TEXT" + ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
