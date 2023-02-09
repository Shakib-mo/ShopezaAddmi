package com.shakib.shoprezaaddmi.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.shakib.shoprezaaddmi.Category_Set_get;
import com.shakib.shoprezaaddmi.Dairy_Breakfast_Act;
import com.shakib.shoprezaaddmi.Display_Product_Add;
import com.shakib.shoprezaaddmi.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Breackfast_Adapter extends RecyclerView.Adapter<Breackfast_Adapter.ViewHolder> {
    ArrayList<Category_Set_get> arrayList;
    Activity activity;
    public static int i=0;
    public Breackfast_Adapter(Activity act,ArrayList<Category_Set_get> arry){
        arrayList = arry;
        activity = act;
    }
    @NonNull
    @Override
    public Breackfast_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view_breackfast,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Breackfast_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Category_Set_get get = arrayList.get(position);
        Picasso.with(activity).load(get.b_pic).into(holder.imageView);
        holder.te_name.setText(arrayList.get(position).getB_name());
        holder.te_kg.setText(arrayList.get(position).getB_kg());
        holder.te_price.setText(arrayList.get(position).getB_price());
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(),view);
                popupMenu.getMenuInflater().inflate(R.menu.menu,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.gallery:
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                activity.startActivityForResult(intent,i);
                                ((Dairy_Breakfast_Act)activity).b_id=arrayList.get(position).getB_id();
                                return true;
                            case R.id.camera:
                                Intent inte = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                ((Dairy_Breakfast_Act)activity).b_id=arrayList.get(position).getB_id();
                                activity.startActivityForResult(inte,100);
                                return true;
                        }
                        return true;
                    }
                });
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,bt_delete;
        TextView te_name,te_kg,te_price;
        Button bt_update;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_br);
            te_name = itemView.findViewById(R.id.te_name);
            te_kg = itemView.findViewById(R.id.te_kg);
            te_price = itemView.findViewById(R.id.te_price);
            bt_delete = itemView.findViewById(R.id.bt_delete);
            bt_update = itemView.findViewById(R.id.bt_update);
        }
    }
}
