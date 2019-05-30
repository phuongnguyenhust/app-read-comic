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
        SQLiteDataController db = SQLiteDataController.getInstance(this);
        try {
            db.isCreatedDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
     //   db.openDataBase();
        c = db.queryComic();

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_nav);
        bottomNavigationView.inflateMenu(R.menu.main_menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    // trường hợp người dùng lựa chọn filter- tìm kiếm theo thể loại
                    case R.id.action_filter :
                        showFilterDialog();
                        break;
                        // trường hợp người dùng lựa chọn search - tìm kiếm theo tên truyện
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
        // tạo alertDialog để thực hiện tìm kiếm theo tên truyện
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FilterSearchActivity.this);
        alertDialog.setTitle("Search");
        LayoutInflater inflater = this.getLayoutInflater();
        // sử dụng layout dialog_search để thực hiện nhập dữ liệu (tên truyện) khi muốn tìm kiếm
        View search_layout = inflater.inflate(R.layout.dialog_search, null);
        final EditText edit_search = (EditText)search_layout.findViewById(R.id.edit_search);
        alertDialog.setView(search_layout);
        // trong hộp alertDialog  nếu người dùng lựa chọn Cancel thì hủy bỏ tìm kiếm
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        // còn lựa chọn Search thì sẽ thực hiện quá trình tìm kiếm
        alertDialog.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                fetchSearchComic(edit_search.getText().toString());

            }
        });
        alertDialog.show();// hiển thi alertDialog  ra màn hình

    }

    private void fetchSearchComic(String query) {// query là chuỗi nhập vào trong editText

        List<Comic> comic_search = new ArrayList<>();
        c.moveToFirst();

        while (!c.isAfterLast()) {

            if (c.getString(1).equalsIgnoreCase(query)) {// nếu tìm thấy
                // tạo đối tượng comic tương ứng
                Comic comic = new Comic(c.getInt(0), c.getString(1), c.getString(2));
                //thêm vào danh sách truyện tìm được
                comic_search.add(comic);
                c.moveToNext();

            } else {
                c.moveToNext();
            }

        }

        if(comic_search.size() > 0){// danh sách tìm kiếm khác rỗng
            recycler_filter_search.setAdapter(new MyComicAdapter(getBaseContext(), comic_search));
        }
        else{// tìm kiếm rỗng đưa ra thông báo cho người dùng
            Toast.makeText(FilterSearchActivity.this, "Không tìm thấy truyện" + query, Toast.LENGTH_SHORT).show();
        }

    }



    private void showFilterDialog() {
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
            Toast.makeText(FilterSearchActivity.this, "Không tìm thấy truyện thuộc loại" + query, Toast.LENGTH_SHORT).show();
        }

    }

}
