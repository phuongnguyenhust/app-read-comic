package com.example.androidcomicreader.Common;

import com.example.androidcomicreader.Model.Chapter;
import com.example.androidcomicreader.Model.Comic;

import java.util.ArrayList;
import java.util.List;

public class Common {
      public static List<Comic> comicList = new ArrayList<>();
      public static Comic selected_comic;
     public static Chapter selected_chapter;
     public static int chapter_index = -1;
     public  static List<Chapter> chapterList = new ArrayList<>();


    public static String formatString(String name) {
        //nếu tên chapter hay truyện quá dài
        StringBuilder finalResult = new StringBuilder(name.length() > 15 ? name.substring(0, 15)+"...": name);
        return finalResult.toString();
    }

    public static String[] categories = {
            "Action",
            "manga",
            "complete",
            "cooking",
            "Drama",
            "Romance",
            "School life",
            "Sports",
            "Top Read"
    };
}
