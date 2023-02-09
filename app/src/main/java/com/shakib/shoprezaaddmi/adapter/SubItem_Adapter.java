package com.shakib.shoprezaaddmi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shakib.shoprezaaddmi.Category_Set_get;
import com.shakib.shoprezaaddmi.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SubItem_Adapter extends RecyclerView.Adapter<SubItem_Adapter.ViewHolder> {
    private static ClickListener clickListener;
    Context context;
    ArrayList<Category_Set_get> arrayList;
    public SubItem_Adapter(Context con, ArrayList<Category_Set_get> array,ClickListener listener){
        context = con;
        arrayList = array;
        clickListener = listener;
    }
    @NonNull
    @Override
    public SubItem_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view_sub_item_brand,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubItem_Adapter.ViewHolder holder, int position) {
        Category_Set_get categorySetGet = arrayList.get(position);
        Picasso.with(context).load(categorySetGet.getImage()).into(holder.imageView);
        holder.te_cat.setText(arrayList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView te_cat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            te_cat = itemView.findViewById(R.id.te_cat_id_);
            imageView = itemView.findViewById(R.id.im_cat_);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            clickListener.onItemClick(view,position);
        }
    }
    public interface ClickListener{
        void onItemClick(View view, int position);

    }
}
