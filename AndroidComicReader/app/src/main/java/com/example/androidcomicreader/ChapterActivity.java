package com.example.androidcomicreader;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.androidcomicreader.Adapter.MyChapterAdapter;
import com.example.androidcomicreader.Common.Common;
import com.example.androidcomicreader.Model.Chapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ChapterActivity extends AppCompatActivity {
    Cursor c;
    RecyclerView recycler_chapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    TextView txt_chapter;
     List<Chapter> chapterList = new ArrayList<>();// lưu trữ danh sách chapter
     MyChapterAdapter adapter;
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        // khởi tạo đối tượng db - dữ liệu sử dụng
        SQLiteDataController db =  SQLiteDataController.getInstance(this);
        try {
            db.isCreatedDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        c = db.queryChapter();
        // Cursor c  trỏ đến bảng Chapter
        //View
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(Common.selected_comic.getName());

        //thay đổi icon cho toolbar
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recycler_chapter = (RecyclerView)findViewById(R.id.recycler_chapter);
        recycler_chapter.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_chapter.setLayoutManager(layoutManager);
        recycler_chapter.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));

        txt_chapter = (TextView)findViewById(R.id.txt_chapter);

        fetchChapter(Common.selected_comic.getID());



    }
    // phương thức lấy các chapter của truyện có id là comicId
    private void fetchChapter(int comicId)
    {
        c.moveToFirst();
         while(!c.isAfterLast())// duyệt cho đến phần tử cuối cùng của bảng Chapter
         {
           if(c.getInt(2) == comicId)// là chapter của comic có id là comicId
              {
                  Chapter chapter = new Chapter(c.getInt(0), c.getString(1));
                  chapterList.add(chapter);
                  c.moveToNext();
              }

              else
                  {
                  c.moveToNext();
              }

         }

        Common.chapterList =  chapterList;
        adapter = new MyChapterAdapter(ChapterActivity.this, chapterList);
      //  Common.chapterList =  chapterList;
        recycler_chapter.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //thay đổi, cập nhật lại số chap trong truyện đó
        txt_chapter.setText(new StringBuilder("NEW COMIC (").append(chapterList.size()).append(")"));



    }
}
