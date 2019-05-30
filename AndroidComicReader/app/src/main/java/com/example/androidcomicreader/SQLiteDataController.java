package com.example.androidcomicreader;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SQLiteDataController extends SQLiteOpenHelper {

    // đường dẫn nơi chứa database
    public String DB_PATH = "//data//data//%s//databases//";

    private static String DB_NAME = "Story.sqlite";// tên database
    public SQLiteDatabase database;
    private final Context mContext;
    public SQLiteDataController(Context con) {
        super(con, DB_NAME, null, 1);
        DB_PATH = String.format(DB_PATH, con.getPackageName());
        this.mContext = con;
    }
    public boolean isCreatedDatabase() throws IOException {
        // Default là đã có DB  Nếu chưa tồn tại DB thì copy từ Asses vào Data
        boolean result = true;
        // lấy dữ liệu đọc từ đatabase
        this.getReadableDatabase();
        try {
            copyDataBase();
            result = false;
        } catch (Exception e) {
            throw new Error("Error copying database");// bắt lỗi thông báo lỗi
        }
        this.close();
        return result;
    }
    // phương thức kiểm tra database đã tồn tại chưa
    private boolean checkExistDataBase() {
        try {
            /// lấy đường dẫn đến database
            String myPath = DB_PATH + DB_NAME;

            File fileDB = new File(myPath);
            // nếu tồn tại file lưu trữ database trả về giá trị true
            if (fileDB.exists()) {
                return true;
            } else
                return false;// không tồn tại trả về giá trị false
        } catch (Exception e) {
            return false;
        }
    }

    // copy cở sỡ dữ liệu trong file

    private void copyDataBase() throws IOException {
        InputStream myInput = mContext.getAssets().open(DB_NAME);
        OutputStream myOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] buffer = new byte[1024];
        int length;
        // ghi vào buffer
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
    // phương thức mở database khi sử dụng
    public void openDataBase() throws SQLException {
        database = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null,
                SQLiteDatabase.OPEN_READWRITE);
    }
    // đóng database khi không sử dụng nữa
    @Override
    public synchronized void close() {
        if (database != null)
            database.close();
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

   // phương thức lấy tất cả thông tin trong bảng Comic
    public Cursor queryComic(){
        String sql = "select * from Comic";// câu truy vấn lấy dữ liệu trong bảng
        SQLiteDatabase db = this.getReadableDatabase();// đọc dữ liệu gán vào db
        Cursor c = db.rawQuery(sql, null);
        return c ;
    }
    // phương thức lấy tất cả thông tin trong bảng Chapter
    public  Cursor queryChapter(){
        String sql = "select * from Chapter";// câu truy vấn lấy tất cả thông tin trong bảng Chapter
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    // phương thức lấy tất cả thông tin lưu trữ trong bảng link ( chứa link ảnh)
    public  Cursor queryLink(){
        String sql = " select * from Links";// câu truy vấn lấy thông tin trong bảng Links
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
}
