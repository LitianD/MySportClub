package com.zlt.mysportclub.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.zlt.mysportclub.model.Trainer;
import java.util.ArrayList;
import java.util.HashMap;

public class TrainerRepo {
    private MySQLDatabase dbHelper;

    public TrainerRepo(Context context ){
        dbHelper = new MySQLDatabase(context);
    }

    public int insert(Trainer trainer){
        //打开连接，写入数据
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Trainer.KEY_ID,trainer.ID);
        values.put(Trainer.KEY_name,trainer.name);
        values.put(Trainer.KEY_phone,trainer.phone);
        values.put(Trainer.KEY_postion,trainer.position);
        values.put(Trainer.KEY_course,trainer.course);
        //
        long student_Id=db.insert(Trainer.TABLE,null,values);
        db.close();
        return (int)student_Id;
    }

    public void delete(int student_Id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(Trainer.TABLE,Trainer.KEY_ID+"=?", new String[]{String.valueOf(student_Id)});
        db.close();
    }
    public void update(Trainer trainer){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Trainer.KEY_phone,trainer.phone);
        values.put(Trainer.KEY_postion,trainer.position);
        values.put(Trainer.KEY_name,trainer.name);
        values.put(Trainer.KEY_course,trainer.course);

        db.update(Trainer.TABLE,values,Trainer.KEY_name+"=?",new String[] { String.valueOf(trainer.name) });
        db.close();
    }

    public ArrayList<Trainer> getTrainerList(){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                Trainer.KEY_ID+","+
                Trainer.KEY_name+","+
                Trainer.KEY_postion+","+
                Trainer.KEY_course+","+
                Trainer.KEY_phone+" FROM "+Trainer.TABLE;
        ArrayList<Trainer> trainerList=new ArrayList<Trainer>();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                Trainer trainer = new Trainer();
                trainer.ID = cursor.getInt(cursor.getColumnIndex(Trainer.KEY_ID));
                trainer.name = cursor.getString(cursor.getColumnIndex(Trainer.KEY_name));
                trainer.phone = cursor.getString(cursor.getColumnIndex(Trainer.KEY_phone));
                trainer.position = cursor.getString(cursor.getColumnIndex(Trainer.KEY_postion));
                trainer.course = cursor.getString(cursor.getColumnIndex(Trainer.KEY_course));
                trainerList.add(trainer);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return trainerList;
    }

    public Trainer getStudentById(int Id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                Trainer.KEY_ID + "," +
                Trainer.KEY_name + "," +
                Trainer.KEY_postion + "," +
                Trainer.KEY_course + "," +
                Trainer.KEY_phone +
                " FROM " + Trainer.TABLE
                + " WHERE " +
                Trainer.KEY_ID + "=?";
        int iCount = 0;
        Trainer trainer = new Trainer();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});
        if (cursor.moveToFirst()) {
            do {
                trainer.ID = cursor.getInt(cursor.getColumnIndex(Trainer.KEY_ID));
                trainer.name = cursor.getString(cursor.getColumnIndex(Trainer.KEY_name));
                trainer.phone = cursor.getString(cursor.getColumnIndex(Trainer.KEY_phone));
                trainer.position = cursor.getString(cursor.getColumnIndex(Trainer.KEY_postion));
                trainer.course = cursor.getString(cursor.getColumnIndex(Trainer.KEY_course));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return trainer;
    }

}
