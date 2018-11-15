package redhill.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by LAL on 24.01.2017.
 */

public class DBHelperFav extends SQLiteOpenHelper {

    private static final String DATABASE2 = "databaseFav";
    private static final String TABLE2 = "favourite_movies";
    private static final String NAME2 = "moviesFav";

    public DBHelperFav(Context context) {
        super(context, DATABASE2, null, 1); //the number at last, is the version of DB
    }

    @Override
    public void onCreate(SQLiteDatabase db2) {
        db2.execSQL("create table if not exists "+TABLE2+" ("+NAME2+" text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db2, int oldVersion, int newVersion) {

    }

    public boolean insertMovie2(DBObjectFav object2){
        SQLiteDatabase db2 = this.getWritableDatabase();
        ContentValues contentValues2 = new ContentValues();
        try{
            contentValues2.put(NAME2, object2.getName());
            db2.insert(TABLE2, null, contentValues2);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean deleteMovie2(String[] name2){
        SQLiteDatabase db2 = this.getWritableDatabase();
        try{
            db2.delete(TABLE2, NAME2+" = ?", name2);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public ArrayList<DBObjectFav> getAllMovies2(){
        SQLiteDatabase db2 = this.getReadableDatabase();
        ArrayList<DBObjectFav> objects = new ArrayList<DBObjectFav>();

        try {
            Cursor cur2 = db2.rawQuery("select * from " + TABLE2, null);
            cur2.moveToFirst();
            while (!cur2.isAfterLast()) {
                DBObjectFav o2 = new DBObjectFav();
                o2.setName(cur2.getString(cur2.getColumnIndex(NAME2)));
                objects.add(o2);
                cur2.moveToNext();
            }
            return objects;
        }catch (Exception e){
            return null;
        }
    }

}
