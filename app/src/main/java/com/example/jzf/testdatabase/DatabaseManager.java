package com.example.jzf.testdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JZF on 2015/7/20.
 */
public class DatabaseManager {

    private MyDatabaseHelper dbHelper;
    public static DatabaseManager instance = null;
    private SQLiteDatabase sqLiteDatabase;

    /**
     * 构造函数
     */
    public DatabaseManager(Context context){
        dbHelper = new MyDatabaseHelper(context,"BookStore.db",null,1);
        sqLiteDatabase = dbHelper.getReadableDatabase();
    }

    /**
     * 获取本类对象实例
     */
    public static final DatabaseManager getInstance(Context context){
        if ((instance == null))
            instance = new DatabaseManager(context);
        return instance;
    }

    /**
     * 关闭数据库
     */
    public void close(){
        if(sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
        if(dbHelper != null)
            dbHelper.close();
        if(instance != null)
            instance = null;
    }


    /**
     * 插入数据
     * @param table        表名
     * @param values       要插入的数据
     * @return result      返回新添加记录的行号，与主键ID无关
     */
    public Long insertData(String table,ContentValues values){
        long result = 0;
        if(sqLiteDatabase.isOpen())
            result = sqLiteDatabase.insert(table,null,values);
        return result;
    }


    /**
     * 更新数据
     * @param table         表名
     * @param values        表示更新的数据
     * @param whereClause   表示SQL语句中条件部分的语句
     * @param whereArgs     表示占位符的值
     * @return result
     */
    public int updateData(String table, ContentValues values, String whereClause, String[] whereArgs){
        int result = 0;
        if(sqLiteDatabase.isOpen()){
            result = sqLiteDatabase.update(table, values, whereClause, whereArgs);
        }
        return result;
    }

    /**
     * 删除数据
     * @param table         表名
     * @param whereClause   表示SQL语句中条件部分的语句
     * @param whereArgs     表示占位符的值
     * @return result
     */
    public int deleteData(String table, String whereClause, String[] whereArgs){
        int result = 0;
        if(sqLiteDatabase.isOpen()){
            result = sqLiteDatabase.delete(table, whereClause, whereArgs);
        }
        return result;
    }

    /**
     * 查询数据
     * @param table             表名
     * @param columns           查询结果
     * @param selection         查询条件_1
     * @param selectionArgs     查询条件_2
     * @return                  返回查询的游标，可对数据自行操作，需要自己关闭游标
     */
    public String queryData(String table, String[] columns , String selection ,String[] selectionArgs){
        String author = "";
        if(sqLiteDatabase.isOpen()){
            Cursor cursor = sqLiteDatabase.query(table, columns, selection, selectionArgs, null, null, null);
            if(cursor.moveToFirst()){
                do{
                    author = cursor.getString(cursor.getColumnIndex("author"));
//                    Log.i("qwert",author);
                } while (cursor.moveToNext());
            }cursor.close();
            return author;
        }
        return null;
    }

    /**
     * 查询数据
     * @param sql               执行查询操作的sql语句
     * @param selectionArgs     查询条件
     * @param object                Object的对象
     * @return List<Object>       返回查询结果
     */
//    public List<Object> queryData2Object(String sql, String[] selectionArgs, Object object) throws Exception{
//        List<Object> mList = new ArrayList<Object>();
//        if(sqLiteDatabase.isOpen()){
//            Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
//            Field[] f;
//            if(cursor != null && cursor.getCount() > 0) {
//                while(cursor.moveToNext()){
//                    f = object.getClass().getDeclaredFields();
//                    for(int i = 0; i < f.length; i++) {
//                        //为JavaBean 设值
//                        invokeSet(object, f[i].getName(), cursor.getString(cursor.getColumnIndex(f[i].getName())));
//                    }
//                    mList.add(object);
//                }
//            }
//            cursor.close();
//        }else{
//            Log.i("info", "数据库已关闭");
//        }
//        return mList;
//    }
}
