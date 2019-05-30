package com.example.androidcomicreader;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidcomicreader.Adapter.MyChapterAdapter;
import com.example.androidcomicreader.Adapter.MyViewPagerAdapter;
import com.example.androidcomicreader.Common.Common;
import com.example.androidcomicreader.Model.Chapter;
import com.example.androidcomicreader.Model.Link;
//import com.example.androidcomicreader.Retrofit.IComicAPI;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ViewDetail extends AppCompatActivity {
  // IComicAPI iComicAPI;
    ViewPager myViewPager;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    TextView txt_chapter_name;// tên chapter
    View back, next;
    Cursor c;
    List<Link> linkList = new ArrayList<>();// khởi tạo đối tượng lưu trữ các link ảnh trong một chapter
    MyViewPagerAdapter adapter;
    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);
       // tạo đối tương db để thao tác với dữ liệu trong database
        SQLiteDataController db =  SQLiteDataController.getInstance(this);
        try {
            db.isCreatedDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        c = db.queryLink();



        myViewPager = (ViewPager) findViewById(R.id.view_pager);
        txt_chapter_name = (TextView) findViewById(R.id.txt_chapter_name);
        next = findViewById(R.id.chapter_next);
        back = findViewById(R.id.chapter_back);

        // lắng nghe sự kiện người dùng click vào nút back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Common.chapter_index == 0){// nếu đây là chap đầu tiên sẽ gửi thông báo
                    Toast.makeText(ViewDetail.this, "You are reading first chapter", Toast.LENGTH_SHORT).show();

                }else {
                   // còn không thì giảm vị trí của chap hiện tại đi một
                    Common.chapter_index --;
                    fetchLinks(Common.chapterList.get(Common.chapter_index).getID());

                }

            }
        });
        // lắng nghe sự kiện người dùng click vào nút next
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Common.chapter_index == Common.chapterList.size() - 1) // nếu người dùng ở trang  cuối
                {
                    Toast.makeText(ViewDetail.this, "You are reading last chapter", Toast.LENGTH_SHORT).show();

                }else {
                    // tăng vị trí chap hiện tại nếu không ở trang cuối
                    Common.chapter_index++;
                    fetchLinks(Common.chapterList.get(Common.chapter_index).getID());

                }
            }
        });


        fetchLinks(Common.selected_chapter.getID());
    }
  // lấy links ảnh tương ứng với từng Chapter có mã id
    private void fetchLinks(int id){
       c.moveToFirst();// đưa cursor c về vị trí đầu của bảng để duyệt
        while ((!c.isAfterLast()) ){// duyệt đến khi đến phần tử cuối cùng trong bảng
            if(((c.getInt(2))== id)) {
                Link link = new Link(c.getInt(0), c.getString(1));
                // thêm link vào mảng linkList nếu có idchapter trong bảng Links bằng với id trong bảng Chapter
                linkList.add(link);
                c.moveToNext();
            }
            else{
                c.moveToNext();
            }
        }
         if(linkList.size() >0) {// kiểm tra có phần tử có trong mảng linkList hay không ?
             // nếu có  thì hiển thị sang trang chi tiết
             MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(ViewDetail.this, linkList);
             myViewPager.setAdapter(myViewPagerAdapter);
         }else {
            // còn nếu số phần tử là bằng không thì gửi thông báo cho người dùng
             Toast.makeText(ViewDetail.this, "No image here", Toast.LENGTH_SHORT).show();
         }
        // thay đổi lại tên của chapter thành chapter  được chọn
        txt_chapter_name.setText(Common.formatString(Common.selected_chapter.getName()));

        //Create book Flip page
        BookFlipPageTransformer bookFlipPageTransformer = new BookFlipPageTransformer();
        bookFlipPageTransformer.setScaleAmountPercent(10f);
        myViewPager.setPageTransformer(true, bookFlipPageTransformer);

    }


  }
