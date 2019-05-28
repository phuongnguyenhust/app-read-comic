package com.example.androidcomicreader.SQLiteHelper;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaoDatabaseComic extends SQLiteOpenHelper {
    public static final String  DB_QUANLICOMIC ="QuanLyComic";
    public static final int  DB_VERSION =1;
    public static final String  TABLE_COMIC = "COMIC";
    public static final String ID_TBCOMIC = "_id";
    public static final String TENCOMIC_TBCOMIC = "TenComic";
    public static final String LINKANH_TBCOMIC = "LinkAnhComic";
    public TaoDatabaseComic(Context context) {
        super(context, DB_QUANLICOMIC, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String cautruyvan = "CREATE TABLE " + TABLE_COMIC + " ( " + ID_TBCOMIC + " INTERGER PRIMARY KEY AUTOINCREMENT, "
                +  TENCOMIC_TBCOMIC + " TEXT, " + LINKANH_TBCOMIC + " TEXT );";
        db.execSQL(cautruyvan);

      /*  String cautruyvan2 = " INSERT INTO " + TABLE_COMIC + " VALUES( 'Dragon Ball','https://avt.mkklcdnv3.com/avatar_225/3326-read_dragon_ball_manga_online_for_free2.jpg' ); " ;
        db.execSQL(cautruyvan2);
        String cautruyvan3 = " INSERT INTO " + TABLE_COMIC + " VALUES( 'Doraemon','https://avt.mkklcdnv3.com/avatar_225/1279-doraemon.jpg' ); " ;
        db.execSQL(cautruyvan3);
        String cautruyvan4 = " INSERT INTO " + TABLE_COMIC + " VALUES( 'Dragon Ball','https://avt.mkklcdnv3.com/avatar_225/3326-read_dragon_ball_manga_online_for_free2.jpg' ); " ;
        db.execSQL(cautruyvan4);
        String cautruyvan5 = " INSERT INTO " + TABLE_COMIC + " VALUES( 'Dragon Ball','https://avt.mkklcdnv3.com/avatar_225/3326-read_dragon_ball_manga_online_for_free2.jpg' ); " ;
        db.execSQL(cautruyvan5);
        String cautruyvan6 = " INSERT INTO " + TABLE_COMIC + " VALUES( 'Dragon Ball','https://avt.mkklcdnv3.com/avatar_225/3326-read_dragon_ball_manga_online_for_free2.jpg' ); " ;
        db.execSQL(cautruyvan6);*/




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMIC );
        onCreate(db);

    }
}
