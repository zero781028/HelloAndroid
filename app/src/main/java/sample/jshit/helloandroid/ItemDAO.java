package sample.jshit.helloandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zero7 on 2016/2/27.
 */
public class ItemDAO {
    public static final String TABLE_NAME="item";
    public static final String KEY_ID="_id";

    public static final String DATETIME_COLUMN="datetime";
    public static final String COLOR_COLUMN="color";
    public static final String TITLE_COLUMN="title";
    public static final String CONTENT_COLUMN="content";
    public static final String FILENAME_COLUMN="filename";
    public static final String RECFILENAME_COLUMN="recfilename";
    public static final String LATITUDE_COLUMN="latitude";
    public static final String LONGITUDE_COLUMN="longitude";
    public static final String LASTMODIFY_COLUMN="lastmodify";

    public static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("+
            KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            DATETIME_COLUMN+" INTEGER NOT NULL,"+
            COLOR_COLUMN+" INTEGER NOT NULL,"+
            TITLE_COLUMN+" TEXT NOT NULL,"+
            CONTENT_COLUMN+" TEXT NOT NULL,"+
            FILENAME_COLUMN+" TEXT,"+
            RECFILENAME_COLUMN+" TEXT,"+
            LATITUDE_COLUMN+" REAL,"+
            LONGITUDE_COLUMN+" REAL,"+
            LASTMODIFY_COLUMN+" INTEGER)";
    private SQLiteDatabase db;

    public ItemDAO(Context context){
        db=MyDBHelper.getDatabase(context);
    }

    public void Close(){
        db.close();
    }

    public Item insert(Item item){
        ContentValues cv=new ContentValues();

        cv.put(DATETIME_COLUMN,item.getDatetime());
        cv.put(COLOR_COLUMN,item.getColor().parseColor());
        cv.put(TITLE_COLUMN,item.getTitle());
        cv.put(CONTENT_COLUMN,item.getContent());
        cv.put(FILENAME_COLUMN,item.getFileName());
        cv.put(RECFILENAME_COLUMN,item.getRecFileName());
        cv.put(LATITUDE_COLUMN,item.getLatitude());
        cv.put(LONGITUDE_COLUMN,item.getLongitude());
        cv.put(LASTMODIFY_COLUMN,item.getLastModify());

        long id=db.insert(TABLE_NAME,null,cv);
        item.setId(id);

        return item;
    }

    public boolean update(Item item){
        ContentValues cv=new ContentValues();

        cv.put(DATETIME_COLUMN,item.getDatetime());
        cv.put(COLOR_COLUMN,item.getColor().parseColor());
        cv.put(TITLE_COLUMN,item.getTitle());
        cv.put(CONTENT_COLUMN,item.getContent());
        cv.put(FILENAME_COLUMN,item.getFileName());
        cv.put(RECFILENAME_COLUMN,item.getRecFileName());
        cv.put(LATITUDE_COLUMN,item.getLatitude());
        cv.put(LONGITUDE_COLUMN, item.getLongitude());
        cv.put(LASTMODIFY_COLUMN, item.getLastModify());

        String where=KEY_ID+"="+item.getId();

        return db.update(TABLE_NAME,cv,where,null)>0;
    }

    public boolean delete(Item item){
        String where=KEY_ID+"="+item.getId();
        return db.delete(TABLE_NAME, where, null)>0;
    }

    public List<Item>getAll(){
        List<Item>result=new ArrayList<>();

        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null);

        while(cursor.moveToNext()){
            result.add(getRecord(cursor));
        }

        cursor.close();

        return result;
    }

    public Item get(long id){
        Item item=null;

        String where=KEY_ID+"="+id;

        Cursor result=db.query(TABLE_NAME,null,where,null,null,null,null,null);

        if(result.moveToFirst()){
            item=getRecord(result);
        }

        result.close();

        return item;
    }

    public Item getRecord(Cursor cursor){
        Item result=new Item();

        result.setId(cursor.getLong(0));
        result.setDatetime(cursor.getLong(1));
        result.setColor(ItemActivity.getColors(cursor.getInt(2)));
        result.setTitle(cursor.getString(3));
        result.setContent(cursor.getString(4));
        result.setFileName(cursor.getString(5));
        result.setRecFileName(cursor.getString(6));
        result.setLatitude(cursor.getDouble(7));
        result.setLongitude(cursor.getDouble(8));
        result.setLastModify(cursor.getLong(9));

        return result;
    }

    public int getCount(){
        int result=0;

        Cursor cursor=db.rawQuery("SELECT COUNT(*) FROM "+TABLE_NAME,null);

        if(cursor.moveToNext()){
            result=cursor.getInt(0);
        }

        return result;
    }

    public void sample(){
        Item item = new Item(0, new Date().getTime(), Colors.RED, "關於Android Tutorial的事情.", "Hello content", "", "", 0, 0, 0);
        Item item2 = new Item(0, new Date().getTime(), Colors.BLUE, "一隻非常可愛的小狗狗!", "她的名字叫「大熱狗」，又叫\n作「奶嘴」，是一隻非常可愛\n的小狗。", "", "", 25.04719, 121.516981, 0);
        Item item3 = new Item(0, new Date().getTime(), Colors.GREEN, "一首非常好聽的音樂！", "Hello content", "", "", 0, 0, 0);
        Item item4 = new Item(0, new Date().getTime(), Colors.ORANGE, "儲存在資料庫的資料", "Hello content", "", "", 0, 0, 0);

        insert(item);
        insert(item2);
        insert(item3);
        insert(item4);
    }
}
