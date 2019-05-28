package com.example.androidcomicreader;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
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
import com.example.androidcomicreader.Retrofit.IComicAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ChapterActivity extends AppCompatActivity {
    Cursor c;
    IComicAPI iComicAPI;
    RecyclerView recycler_chapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    TextView txt_chapter;
     List<Chapter> chapterList = new ArrayList<>();
     MyChapterAdapter adapter;
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        // thêm new
        SQLiteDataController db = new SQLiteDataController(this);
        try {
            db.isCreatedDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.openDataBase();
        c = db.queryChapter();

        // end them new


        iComicAPI = Common.getAPI();

        //View
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(Common.selected_comic.getName());

        //Set icon for toolbar
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//go back
            }
        });

        recycler_chapter = (RecyclerView)findViewById(R.id.recycler_chapter);
        recycler_chapter.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_chapter.setLayoutManager(layoutManager);
        recycler_chapter.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));

        txt_chapter = (TextView)findViewById(R.id.txt_chapter);

      //  fetchChapter(Common.selected_comic.getID());
        /*for(int i = 0; i<5; i++){
            Chapter chapter = new Chapter( 0, "chapter" +i, 0);
            chapterList.add(chapter);

        }*/

        c.moveToFirst();
        switch (Common.selected_comic.getName()){

            case "Doraemon" :
                c.moveToFirst();
                while ((!c.isAfterLast()) ){
                    if((c.getInt(2) == 3)) {
                        Chapter chapter = new Chapter(c.getInt(0), c.getString(1));
                        chapterList.add(chapter);
                        c.moveToNext();
                    }
                    else{
                        c.moveToNext();
                    }

                }

                adapter = new MyChapterAdapter(ChapterActivity.this, chapterList);
                recycler_chapter.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;

            case "Dragon Ball" :
                c.moveToFirst();
                while ((!c.isAfterLast())&& (c.getInt(2) == 1)){
                      Chapter chapter = new Chapter(c.getInt(0), c.getString(1));
                        chapterList.add(chapter);
                        c.moveToNext();

                    }

                adapter = new MyChapterAdapter(ChapterActivity.this, chapterList);
                recycler_chapter.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
              case "Spider-Man":
                c.moveToFirst();
                while (!c.isAfterLast()){
                    if(c.getInt(2) == 2){
                        Chapter chapter = new Chapter(c.getInt(0), c.getString(1));
                        chapterList.add(chapter);
                        c.moveToNext();
                    }

                    else{
                        c.moveToNext();
                    }
                }
                adapter = new MyChapterAdapter(ChapterActivity.this, chapterList);
                recycler_chapter.setAdapter(adapter);
                adapter.notifyDataSetChanged();


                break;

        }




     /*   while (!c.isAfterLast()){
            Chapter chapter = new Chapter(c.getInt(0), c.getString(1), c.getInt(2));
            chapterList.add(chapter);
            c.moveToNext();
        }*/

       // adapter = new MyChapterAdapter(ChapterActivity.this, chapterList);
//        adapter = new MyChapterAdapter(ChapterActivity.this, chapterList);
//        recycler_chapter.setAdapter(adapter);
//        adapter.notifyDataSetChanged();

       // toolbar.setNavigationIcon(R.drawable.ic_chevron_right_24dp);
    }
    private void fetchChapter(int comicId) {
        final AlertDialog dialog = new SpotsDialog.Builder().setContext(this).setMessage("Please wait...").setCancelable(false).build();
        dialog.show();

        compositeDisposable.add(iComicAPI.getChapterList(comicId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Chapter>>() {
                               @Override
                               public void accept(List<Chapter> chapters) throws Exception {
                                   Common.chapterList = chapters; //new chapter to back, next
                                   recycler_chapter.setAdapter((new MyChapterAdapter(ChapterActivity.this, chapters )));
                                   txt_chapter.setText(new StringBuilder("NEW COMIC (")
                                           .append(chapters.size())
                                           .append(")")
                                   );
                                   dialog.dismiss();
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   dialog.dismiss();
                                   Toast.makeText(ChapterActivity.this, "Error while loading comic", Toast.LENGTH_SHORT).show();
                               }
                               }));

    }
}
