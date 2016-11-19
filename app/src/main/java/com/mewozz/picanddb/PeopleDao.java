package com.mewozz.picanddb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjzzz on 11/19/2016.
 */

public class PeopleDao {
    private static final String TAG = "PeopleDao";
    private static final String FOLDER="/MyApp/";
    private static final String DB_NAME="mydb.db";
    private static final String TABLE_PEOPLE="people";



    public PeopleDao() {


    }

    public static void savePeople(People people){
        SQLiteDatabase db=initDb();
        ContentValues values=new ContentValues();
        values.put("name",people.getName());
        long id=db.insert(TABLE_PEOPLE,null,values);
        String savedBmpPath=saveBitmapToSd(people.getBmp(),String.valueOf(id));
        ContentValues updatePicValues=new ContentValues();
        updatePicValues.put("pic",savedBmpPath);
        db.update(TABLE_PEOPLE,updatePicValues,"id="+id,null);
        db.close();
    }
    public static List<People> getPeopleByName(String name){
        List<People> peopleList=new ArrayList<>();
        SQLiteDatabase db=initDb();
        String sql="select * from "+TABLE_PEOPLE+" where name='"+name+"'";
        Cursor cursor=db.rawQuery(sql,null);
        while(cursor.moveToNext()){
            People people=new People();
            people.setId(cursor.getInt(0));
            people.setName(cursor.getString(1));
            people.setBmp(BitmapFactory.decodeFile(cursor.getString(2)));
            peopleList.add(people);
        }
        cursor.close();
        return peopleList;
    }
    private static String saveBitmapToSd(Bitmap bmp, String fileName){
        String pngName=Environment.getExternalStorageDirectory().toString()+FOLDER+fileName+".png";
        FileOutputStream fos= null;
        boolean success=false;
        try {
            fos = new FileOutputStream(new File(pngName));
            bmp.compress(Bitmap.CompressFormat.PNG,100,fos);
            success=true;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "saveBitmapToSd: ", e);
        }
        finally {
            if(fos!=null)
                try {
                    fos.close();
                } catch (IOException e) {
                    Log.e(TAG, "saveBitmapToSd: ",e );
                }
        }
        if(success)
            return pngName;
        else
            return null;
    }
    private static SQLiteDatabase initDb(){
        File appFolder=new File(Environment.getExternalStorageDirectory().toString()+FOLDER);
        if(!appFolder.exists()){
            appFolder.mkdir();
        }
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(new File(Environment.getExternalStorageDirectory().toString()+FOLDER+DB_NAME),null);
        String sql="create table if not exists "+TABLE_PEOPLE+" (id integer primary key AUTOINCREMENT,name text,pic text)";
        db.execSQL(sql);

        return db;
    }

}
