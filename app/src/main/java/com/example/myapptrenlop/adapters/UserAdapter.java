package com.example.myapptrenlop.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.models.Cart;
import com.example.myapptrenlop.models.User;
import com.example.myapptrenlop.utils.Constant;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    List<User> users;
    Context context;
    public UserAdapter(List<User> users, Context context){
        this.users=users;
        this.context= context;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View orderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_user, parent, false);
        UserViewHolder orderViewHolder = new UserViewHolder(orderView);
        return orderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user =users.get(position);
        holder.txtName.setText(user.getFirstName()+" "+user.getLastName());
        try{
            Bitmap bitmap = Constant.loadImage(user.getImageBase64());
            holder.circleImageView.setImageBitmap(bitmap);
        }
        catch (Exception e){

        }


    }

    @Override
    public int getItemCount() {
         return this.users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView txtName;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.img_avatar);
            txtName =itemView.findViewById(R.id.txtNameUser);
        }

    }
}
