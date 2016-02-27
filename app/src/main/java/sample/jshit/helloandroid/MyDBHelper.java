package sample.jshit.helloandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zero7 on 2016/2/27.
 */
public class MyDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="mydata.db";
    public static final int VERSION=1;
    private static SQLiteDatabase database;

    public MyDBHelper(Context context,String name,CursorFactory factory,int version){
        super(context,name, factory,version);
    }

    public static SQLiteDatabase getDatabase(Context context){
        if(database==null||!database.isOpen()){
            database=new MyDBHelper(context,DATABASE_NAME,null,VERSION).getWritableDatabase();
        }
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ItemDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ItemDAO.TABLE_NAME);
        onCreate(db);
    }
}
