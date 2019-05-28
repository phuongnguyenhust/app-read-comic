package com.example.androidcomicreader.DAO;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

//import com.example.appcomicreader.DTO.ComicDTO;
import com.example.androidcomicreader.Model.Comic;
import com.example.androidcomicreader.SQLiteHelper.TaoDatabaseComic;

import java.util.ArrayList;
import java.util.List;

public class ComicDAO {
    SQLiteDatabase database;
    TaoDatabaseComic taoDatabaseComic;
    public ComicDAO(Context context){
        taoDatabaseComic = new TaoDatabaseComic(context);


    }
    // mo database
    public void open(){
        database = taoDatabaseComic.getWritableDatabase();
    }
    //dong database
    public void close(){
        taoDatabaseComic.close();
    }
    // them comic
    public  boolean ThemComic(Comic comic){
        ContentValues contentValues=  new ContentValues();
        //contentValues.put(TaoDatabaseComic.ID_TBCOMIC); khog can id do no tu tang
        contentValues.put(TaoDatabaseComic.TENCOMIC_TBCOMIC, comic.getName() );
        contentValues.put(TaoDatabaseComic.LINKANH_TBCOMIC,comic.getImage());
        long id_comic = database.insert(TaoDatabaseComic.TABLE_COMIC,null,contentValues );
        if(id_comic != 0){
            return true;

        }else {
            return false;
        }

    }
    public List<Comic> LayDanhSachComic(){
        List<Comic> danhSachComic = new ArrayList<Comic>();
        String cautruyvan = "SELECT * from  " + TaoDatabaseComic.TABLE_COMIC;
        Cursor cursor= database.rawQuery(cautruyvan,null);
        while (!cursor.isAfterLast()){
            int id_comic = cursor.getInt(cursor.getColumnIndex(TaoDatabaseComic.ID_TBCOMIC));
            String name = cursor.getString(cursor.getColumnIndex(TaoDatabaseComic.TENCOMIC_TBCOMIC));
            String link = cursor.getString(cursor.getColumnIndex(TaoDatabaseComic.LINKANH_TBCOMIC));

            Comic comic = new Comic();
            comic.setID(id_comic);
            comic.setName(name);
            comic.setImage(link);

            danhSachComic.add(comic);
            cursor.moveToNext();


        }
        return danhSachComic;
    }
}
