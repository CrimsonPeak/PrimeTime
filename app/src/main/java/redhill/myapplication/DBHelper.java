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

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE = "database";
    private static final String TABLE = "disabled_movies";
    private static final String NAME = "movies";

    public DBHelper(Context context) {
        super(context, DATABASE, null, 1); //the number at last, is the version of DB
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+TABLE+" ("+NAME+" text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertMovie(DBObject object){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try{
            contentValues.put(NAME, object.getName());
            db.insert(TABLE, null, contentValues);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean deleteMovie(String[] name){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(TABLE, NAME+" = ?", name);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public ArrayList<DBObject> getAllMovies(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DBObject> objects = new ArrayList<DBObject>();

        try {
            Cursor cur = db.rawQuery("select * from " + TABLE, null);
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                DBObject o = new DBObject();
                o.setName(cur.getString(cur.getColumnIndex(NAME)));
                objects.add(o);
                cur.moveToNext();
            }
            return objects;
        }catch (Exception e){
            return null;
        }
    }

}
