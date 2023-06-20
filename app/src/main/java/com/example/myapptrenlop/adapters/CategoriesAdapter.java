package com.example.myapptrenlop.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.activities.CategoryActivity;
import com.example.myapptrenlop.models.Category;
import com.example.myapptrenlop.models.Product;
import com.example.myapptrenlop.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>{




    public class CategoriesViewHolder extends RecyclerView.ViewHolder{
        TextView textViewCategoryItemTitle;
        LinearLayout category_item;
        CircleImageView img_avatar;
        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCategoryItemTitle = itemView.findViewById(R.id.textViewCategoryItemTitle);
            category_item=  itemView.findViewById( R.id.category_item );
            img_avatar = itemView.findViewById(R.id.img_avatar);
        }
    }

    private List<Category> categories;

    Context context;

    public CategoriesAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }


    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_category_item,parent,false);
        CategoriesViewHolder categoriesViewHolder = new CategoriesViewHolder(view);
        return categoriesViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        Category ctg= categories.get(position);
        holder.textViewCategoryItemTitle.setText(ctg.getCategoryName());
//        CircleImageView imageView =new CircleImageView(context);
//        if(ctg.getCategoryImage()!="")
//        {
//            Picasso.with(context).load(ctg.getCategoryImage()).into(holder.img_avatar);
//        }

        Bitmap bitmap = Constant.loadImage(ctg.getCategoryImage());
        holder.img_avatar.setImageBitmap(bitmap);
        holder.category_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,CategoryActivity.class);
                intent.putExtra("CategoryID",ctg.getCategoryID());
                context.startActivities(new Intent[]{intent});
            }
        });
    }


    @Override
    public int getItemCount() {
        return this.categories.size();
    }


}
