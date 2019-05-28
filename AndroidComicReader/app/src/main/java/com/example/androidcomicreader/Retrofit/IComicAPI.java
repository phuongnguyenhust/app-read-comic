package com.example.androidcomicreader.Retrofit;

//import android.database.Observable;

import com.example.androidcomicreader.Model.Banner;
import com.example.androidcomicreader.Model.Chapter;
import com.example.androidcomicreader.Model.Comic;
import com.example.androidcomicreader.Model.Link;

import io.reactivex.Observable;
import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface IComicAPI {

    @GET("banner")
    Observable<List<Banner>> getBannerList();

    @GET("comic")
    Observable<List<Comic>> getComicList();

    @GET("chapter/{comicid}")
    Observable<List<Chapter>>getChapterList(@Path("comicid")int comicid);

    @GET("links/{chapterid}")
    Observable<List<Link>>getImageList(@Path("chapterid")int chapterid);


}
