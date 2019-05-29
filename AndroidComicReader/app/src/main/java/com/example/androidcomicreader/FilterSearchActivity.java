package com.example.androidcomicreader;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidcomicreader.Adapter.MyComicAdapter;
import com.example.androidcomicreader.Common.Common;
import com.example.androidcomicreader.Model.Comic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterSearchActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    RecyclerView recycler_filter_search;
    Cursor c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_search);

        recycler_filter_search = (RecyclerView)findViewById(R.id.recycler_filter_search);
        recycler_filter_search.setHasFixedSize(true);
        recycler_filter_search.setLayoutManager(new GridLayoutManager(this, 2));


        // thêm new
        SQLiteDataController db = new SQLiteDataController(this);
        try {
            db.isCreatedDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
     //   db.openDataBase();
        c = db.queryComic();

        // end them new

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_nav);
        bottomNavigationView.inflateMenu(R.menu.main_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.action_filter :
                        showFilterDialog();

                        break;
                    case R.id.action_search :
                        showSearchDialog();
                        break;
                        default:
                            break;
                }
                return true;
            }
        });
    }

    private void showSearchDialog() {
        Toast.makeText(FilterSearchActivity.this, "thong báo", Toast.LENGTH_SHORT).show();
       // AlertDialog.Builder alertDialog = new AlertDialog.Builder(FilterSearchActivity.this);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FilterSearchActivity.this);
        alertDialog.setTitle("Search");

         LayoutInflater inflater = this.getLayoutInflater();
        View search_layout = inflater.inflate(R.layout.dialog_search, null);
        final EditText edit_search = (EditText)search_layout.findViewById(R.id.edit_search);
        alertDialog.setView(search_layout);

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        alertDialog.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                fetchSearchComic(edit_search.getText().toString());

            }
        });
        alertDialog.show();

    }

    private void fetchSearchComic(String query) {
        Toast.makeText(FilterSearchActivity.this, "ten" + query, Toast.LENGTH_SHORT ).show();
        List<Comic> comic_search = new ArrayList<>();
        c.moveToFirst();

        while (!c.isAfterLast()) {

            if (c.getString(1).equalsIgnoreCase(query)) {
                Comic comic = new Comic(c.getInt(0), c.getString(1), c.getString(2));
                comic_search.add(comic);
                c.moveToNext();

            } else {
                c.moveToNext();
            }

        }

        if(comic_search.size() > 0){
            recycler_filter_search.setAdapter(new MyComicAdapter(getBaseContext(), comic_search));
        }
        else{
            Toast.makeText(FilterSearchActivity.this, "Không tìm thấy truyện" + query, Toast.LENGTH_SHORT).show();
        }

    }



    private void showFilterDialog() {
        Toast.makeText(FilterSearchActivity.this, "thong báo", Toast.LENGTH_SHORT).show();
        // AlertDialog.Builder alertDialog = new AlertDialog.Builder(FilterSearchActivity.this);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FilterSearchActivity.this);
        alertDialog.setTitle("Search Category");

        LayoutInflater inflater = this.getLayoutInflater();
        View search_layout = inflater.inflate(R.layout.dialog_option, null);
        final EditText edit_search = (EditText)search_layout.findViewById(R.id.edit_search_category);
        alertDialog.setView(search_layout);

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        alertDialog.setPositiveButton("Filter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                fetchSearchCategory(edit_search.getText().toString());

            }
        });
        alertDialog.show();

    }

    private void fetchSearchCategory(String query) {
      //  Toast.makeText(FilterSearchActivity.this, "the" + query, Toast.LENGTH_SHORT ).show();
        List<Comic> comic_search_category = new ArrayList<>();
        c.moveToFirst();

        while (!c.isAfterLast()) {

            if (c.getString(3).equalsIgnoreCase(query)) {
                Comic comic = new Comic(c.getInt(0), c.getString(1), c.getString(2));
                comic_search_category.add(comic);
                c.moveToNext();

            } else {
                c.moveToNext();
            }

        }

        if(comic_search_category.size() > 0){
            recycler_filter_search.setAdapter(new MyComicAdapter(getBaseContext(), comic_search_category));
        }
        else{
            Toast.makeText(FilterSearchActivity.this, "Không tìm thấy truyện" + query, Toast.LENGTH_SHORT).show();
        }

    }


//    private void showFilterDialog() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FilterSearchActivity.this);
//        alertDialog.setTitle("Selected Category");
//
//        final LayoutInflater inflater = this.getLayoutInflater();
//        View filter_layout = inflater.inflate(R.layout.dialog_option, null);
//
////        final AutoCompleteTextView txt_category = (AutoCompleteTextView)filter_layout.findViewById(R.id.txt_category);
////        final ChipGroup chipGroup = (ChipGroup)filter_layout.findViewById(R.id.chipGroup);
////
////       //Create autocomplete
////        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, Common.categories);
////        txt_category.setAdapter(adapter);
////        txt_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////            @Override
////            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                //Clear
////                txt_category.setText("");
////
////                //Create tags
////                Chip chip = (Chip)inflater.inflate(R.layout.chip_item, null, false);
////                chip.setText(((TextView)view).getText());
////                chip.setOnCloseIconClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        chipGroup.removeView(v);
////                    }
////                });
////                chipGroup.addView(chip);
////            }
////        });
////
////        alertDialog.setView(filter_layout);
////        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialogInterface, int i) {
////                dialogInterface.dismiss();
////
////            }
////        });
////        alertDialog.setPositiveButton("FILTER", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialogInterface, int i) {
////                List<String> filter_key = new ArrayList<>();
////                StringBuilder filter_query = new StringBuilder("");
////                for (int j=0;j<chipGroup.getChildCount(); j++){
////                    Chip chip = (Chip)chipGroup.getChildAt(j);
////                    filter_key.add(chip.getText().toString());
////
////                }
////
////                //bởi vì trong database, category lưu tu A-Z và
////                // nên chúng ta cần sort our filter_key
////                Collections.sort(filter_key);
////                //chuyển list sang string
////                for(String key:filter_key){
////                    filter_query.append(key).append(",");
////
////                }
////                //xóa dấu"," ở cuối
////                filter_query.setLength(filter_query.length()-1);
////
////                //
////                fetchFilerCategory(filter_query.toString());
////
////            }
////        });
//        alertDialog.show();
//
//    }
//
//    private void fetchFilerCategory(String query) {
//        //Toast.makeText(FilterSearchActivity.this, "ten" + query, Toast.LENGTH_SHORT ).show();
//        List<Comic> comic_category_search = new ArrayList<>();
//        c.moveToFirst();
//
//        while (!c.isAfterLast()) {
//
//            if (c.getString(3).equalsIgnoreCase(query)) {
//                Comic comic = new Comic(c.getInt(0), c.getString(1), c.getString(2));
//                comic_category_search.add(comic);
//                c.moveToNext();
//
//            } else {
//                c.moveToNext();
//            }
//
//        }
//
//        if(comic_category_search.size() > 0){
//            recycler_filter_search.setAdapter(new MyComicAdapter(getBaseContext(), comic_category_search));
//        }
//        else{
//            Toast.makeText(FilterSearchActivity.this, "Không tìm thấy truyện có thể loại" + query, Toast.LENGTH_SHORT).show();
//        }
//
//    }

}
