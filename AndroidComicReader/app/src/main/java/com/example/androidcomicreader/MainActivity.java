package com.example.androidcomicreader;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
//import android.support.v7.app.AlertController.RecycleListView
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidcomicreader.Adapter.MyComicAdapter;
import com.example.androidcomicreader.Adapter.MySliderAdapter;
import com.example.androidcomicreader.Common.Common;
import com.example.androidcomicreader.DAO.ComicDAO;
import com.example.androidcomicreader.Model.Banner;
import com.example.androidcomicreader.Model.Comic;
import com.example.androidcomicreader.Retrofit.IComicAPI;
import com.example.androidcomicreader.Service.PicassoImageLoadingService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ss.com.bannerslider.Slider;

//public class MainActivity extends AppCompatActivity {
public class MainActivity extends AppCompatActivity {

    public final static String ID_EXTRA = "comic ID";

    //View
    Slider slider;
    IComicAPI iComicAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    List<Comic> dsComic;
    RecyclerView recycler_comic;
    TextView txt_comic;
    List<Banner> bannerList = new ArrayList<>();
    List<Comic> comicList = new ArrayList<>();
    //them
    Cursor c;


    MySliderAdapter adapter;
    MyComicAdapter adapter1;

    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // thêm new
        SQLiteDataController db = new SQLiteDataController(this);
        try {
            db.isCreatedDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.openDataBase();
        c = db.queryComic();

        // end them new
        iComicAPI = Common.getAPI();
        slider = (Slider) findViewById(R.id.banner_slider);
        Slider.init(new PicassoImageLoadingService());

        recycler_comic = (RecyclerView) findViewById(R.id.recycler_comic);
        recycler_comic.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recycler_comic.setLayoutManager(gridLayoutManager);

        txt_comic = (TextView) findViewById(R.id.txt_comic);

        for (int i = 0; i < 5; i++) {

            Banner banner = new Banner(i, "http://sohanews.sohacdn.com/thumb_w/660/2018/photo1544515241907-1544515245018-crop-15445153387811714841569.jpg");
            bannerList.add(banner);

        }
        adapter = new MySliderAdapter(bannerList);
        slider.setAdapter(adapter);

       // Log.e("log", c.getString(2));
       // Log.d("Log " , "msg" + c.getInt(0));
        c.moveToFirst();

       while (!c.isAfterLast()) {

            Comic comic = new Comic(c.getInt(0), c.getString(1), c.getString(2));
            comicList.add(comic);
            c.moveToNext();

       }

        adapter1 = new MyComicAdapter(MainActivity.this, comicList);
        recycler_comic.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

        //end them new

/*
        // thêm

       ComicDAO comicDAO = new ComicDAO(this);
        comicDAO.open();
        Comic comic1 =  new Comic();
        comic1.setName("Dragon Ball");
        comic1.setImage("https://avt.mkklcdnv3.com/avatar_225/3326-read_dragon_ball_manga_online_for_free2.jpg");
        comicDAO.ThemComic(comic1);

        Comic comic2 =  new Comic();
        comic2.setName("Doraemon");
        comic2.setImage("https://avt.mkklcdnv3.com/avatar_225/1279-doraemon.jpg");
        comicDAO.ThemComic(comic2);


        Comic comic3 =  new Comic();
        comic3.setName("The Amazing Spider-Man (1963)");
        comic3.setImage("https://avt.mkklcdnv3.com/avatar_225/11297-spiderman.jpg");
        comicDAO.ThemComic(comic3);

        Comic comic4 =  new Comic();
        comic4.setName("Spider-Man");
        comic4.setImage("https://avt.mkklcdnv3.com/avatar_225/11297-spiderman.jpg");
        comicDAO.ThemComic(comic4);




        //end

        */

        //Init API
    /* newbo
       iComicAPI = Common.getAPI();
        slider = (Slider)findViewById(R.id.banner_slider);
        Slider.init(new PicassoImageLoadingService());

        recycler_comic = (RecyclerView)findViewById(R.id.recycler_comic);
        recycler_comic.setHasFixedSize(true);
        recycler_comic.setLayoutManager(new GridLayoutManager(this, 2));
        txt_comic= (TextView)findViewById(R.id.txt_comic);
*/
/*
        //them
         dsComic = comicDAO.LayDanhSachComic();
        //end

   */    // fetchBanner();
      /* bo new
        for(int i=0; i<5; i++) {

        Banner banner = new Banner(i, "http://sohanews.sohacdn.com/thumb_w/660/2018/photo1544515241907-1544515245018-crop-15445153387811714841569.jpg");
        bannerList.add(banner);

         }
        adapter = new MySliderAdapter(bannerList);
        slider.setAdapter(adapter);
      */


        //  fetchComic();
        /*bo new
        for(int i =0; i<5;i++){
            Comic comic = new Comic(i, "Name ", "https://avt.mkklcdnv3.com/avatar_225/3326-read_dragon_ball_manga_online_for_free2.jpg");
            comicList.add(comic);
        }
        adapter1 = new MyComicAdapter(MainActivity.this, comicList);
        recycler_comic.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

        */
// them
     /*   for(int i =0; i<3;i++){
            Comic comic = new Comic(dsComic.get(i).getID(), "Name ", dsComic.get(i).getImage());
           // comicList.add(comic);
        }
*/
        //end them

        /* bo new
        adapter1 = new MyComicAdapter(MainActivity.this, comicList);
        recycler_comic.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
      //



    }
//    public void onListItemClick(ListView list, View view , int position, long id){
//        Intent i = new Intent(MainActivity.this, ChapterActivity.class);
//        i.putExtra(ID_EXTRA, String.valueOf(id));
//        startActivity(i);
//
//    }

 /*   private void fetchComic() {
        final AlertDialog dialog = new SpotsDialog.Builder().setContext(this).setMessage("Please wait...").setCancelable(false).build();
        dialog.show();

        compositeDisposable.add(iComicAPI.getComicList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Comic>>() {
                    @Override
                    public void accept(List<Comic> comics) throws Exception {
                        recycler_comic.setAdapter(new MyComicAdapter(getBaseContext(), comics));
                      txt_comic.setText(new StringBuilder("NEW COMIC (")
                                .append(comics.size())
                                .append(")")
                        );
                        dialog.dismiss();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Error while loading comic", Toast.LENGTH_SHORT).show();
                    }
                }));




    }

    private void fetchBanner() {
        compositeDisposable.add(iComicAPI.getBannerList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<Banner>>() {
            @Override
            public void accept(List<Banner> banners) throws Exception {
                slider.setAdapter(new MySliderAdapter(banners));

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(MainActivity.this, "Error while loading banner", Toast.LENGTH_SHORT).show();
            }
        }));

    }*/


    }
}


