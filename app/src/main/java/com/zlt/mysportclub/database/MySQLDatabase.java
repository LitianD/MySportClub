package com.zlt.mysportclub.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLDatabase extends SQLiteOpenHelper {
    private static final String db_name = "test";//自定义的数据库名；
    private static final int version =1;//版本号
    public MySQLDatabase(Context context) {
        super(context, db_name, null, version);

    }
    //该方法会自动调用，首先系统会检查该程序中是否存在数据库名为‘myDatabase’的数据库，如果存在则不会执行该方法，如果不存在则会执行该方法。
    @Override
    public void onCreate(SQLiteDatabase arg0) {
        String  sql ="create table trainer(" +
                "id int primary key," +
                "name varchar(30)," +
                "phone varchar(20)," +
                "position varchar(50)," +
                "course varchar(30)" +
                ")";
        arg0.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}