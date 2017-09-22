package example.irshad.com.myapp.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Irshad on 22/9/17.
 */

public class CacheDBHeper {


    private CacheDB cacheDB;


    public CacheDBHeper(Context context)
    {
        cacheDB=new CacheDB(context);

    }

    private void insertIntoDBUtil(String query,String response)
    {
        SQLiteDatabase sqLiteDatabase=cacheDB.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(DBColumns.SEARCH_STRING,query);
        contentValues.put(DBColumns.SEARCH_RESPONSE,response);

        sqLiteDatabase.insert(DBColumns.TABLE_NAME,null,contentValues);


        sqLiteDatabase.close();
    }

    public void insertIntoDB(final String query, final String response){

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                insertIntoDBUtil(query,response);
            }
        });

        thread.start();
    }


    public String getSearchResponse(String query){
        SQLiteDatabase sqLiteDatabase=cacheDB.getReadableDatabase();

        Cursor cursor=sqLiteDatabase.query(DBColumns.TABLE_NAME,new String[]{DBColumns.SEARCH_RESPONSE},DBColumns.SEARCH_STRING+" = ?",new String[]{query},null,null,null);

        String response="no_result";
        while (cursor.moveToNext()){
             response=cursor.getString(cursor.getColumnIndex(DBColumns.SEARCH_RESPONSE));
        }
        cursor.close();
        sqLiteDatabase.close();
        return response;
    }



}
