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
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
    ImageView btn_filter_search;
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

        btn_filter_search = (ImageView) findViewById(R.id.btn_show_filter_search);
        btn_filter_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FilterSearchActivity.class));

            }
        });

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

    }
}
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);
        MenuItem searchview = menu.findItem(R.id.itSearchView);
        SearchView searchView = (SearchView) searchview.getActionView();
        searchView.setOnQueryTextListener(MainActivity.this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.itSearchView) {
            Toast.makeText(MainActivity.this, "Bạn đã click vào Menu: "+ item.getTitle(),Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Toast.makeText(MainActivity.this, s ,Toast.LENGTH_SHORT).show();
        return false;
    }
}

*/
