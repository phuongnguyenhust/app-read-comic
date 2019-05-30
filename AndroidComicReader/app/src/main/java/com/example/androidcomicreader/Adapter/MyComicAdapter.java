package com.example.androidcomicreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidcomicreader.ChapterActivity;
import com.example.androidcomicreader.Common.Common;
import com.example.androidcomicreader.Interface.IRecyclerOnClick;
import com.example.androidcomicreader.Model.Comic;
import com.example.androidcomicreader.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyComicAdapter extends RecyclerView.Adapter<MyComicAdapter.MyViewHolder> {

    Context context;
    List<Comic> comicList;
    // hàm khởi tạo của class MyComicAdapter
    public MyComicAdapter(Context context, List<Comic> comicList){
        this.context = context;
        this.comicList = comicList;

    }

    @NonNull
    @Override
    public MyComicAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.comic_item,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // load ảnh thông qua đường dẫn  sử dụng Picasso
        Picasso.get().load(comicList.get(i).getImage()).into(myViewHolder.imageView);
        // thay đổi lại tên truyện tương ứng
        myViewHolder.textView.setText(comicList.get(i).getName());
        // xử lí sự kiện khi người dùng click vào các truyện
        myViewHolder.setiRecyclerOnClick(new IRecyclerOnClick() {
            @Override
            public void onClick(View view, int position) {
                // tạo một activity mới , để chuyển sang activity ChapterActivity
                context.startActivity(new Intent(context, ChapterActivity.class));
                Common.selected_comic = comicList.get(position);

            }
        });

    }

    // phương thức trả về số comic có trong danh sách truyện
    @Override
    public int getItemCount() {
        return comicList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;
        IRecyclerOnClick iRecyclerOnClick;

        public void setiRecyclerOnClick(IRecyclerOnClick iRecyclerOnClick) {
            this.iRecyclerOnClick = iRecyclerOnClick;
        }

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            imageView=(ImageView)itemView.findViewById(R.id.image_view);
            textView=(TextView)itemView.findViewById(R.id.manga_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iRecyclerOnClick.onClick(view, getAdapterPosition());
        }
    }
}
