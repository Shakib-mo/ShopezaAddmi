package com.shakib.shoprezaaddmi.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shakib.shoprezaaddmi.Display_Product_Add;
import com.shakib.shoprezaaddmi.R;
import com.shakib.shoprezaaddmi.SubCotegory_Get_Set;
import com.shakib.shoprezaaddmi.Update_SubCategory;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MySubcategory_Adapter extends RecyclerView.Adapter<MySubcategory_Adapter.ViewHolder> {
    Context context;
    ArrayList<SubCotegory_Get_Set> arrayList;
    public MySubcategory_Adapter(Context contex,ArrayList<SubCotegory_Get_Set> array){
        context = contex;
        arrayList = array;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view_subcategory,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MySubcategory_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        SubCotegory_Get_Set getSet = arrayList.get(position);
        Picasso.with(context).load(getSet.getPic()).into(holder.img_sub);
        holder.subname.setText(arrayList.get(position).getSubname());
        holder.catid.setText(arrayList.get(position).getCat_id());


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure You want to Deleted item ?");
                builder.setCancelable(true);
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Deleted_from_server(arrayList.get(position).getSubcatid(),position);
                        notifyDataSetChanged();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Update_SubCategory.class);
                intent.putExtra("sub_id",arrayList.get(position).getSubcatid());
                intent.putExtra("sub",arrayList.get(position).getSubname());
                intent.putExtra("cat",arrayList.get(position).getCat_id());
                context.startActivity(intent);
            }
        });
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_sub,delete,update;
        TextView catid,subname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_sub = itemView.findViewById(R.id.img_sub_category);
            delete = itemView.findViewById(R.id.img_delete);
            update = itemView.findViewById(R.id.img_edit);

            catid = itemView.findViewById(R.id.te_cat_name_su);
            subname = itemView.findViewById(R.id.te_sub_name);}
    }

    public void Delete_position(int position){
        arrayList.remove(position);
        notifyItemChanged(position);
    }
    public void Deleted_from_server(String suid,int posi){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject object = new JSONObject();
        ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading");
        pDialog.show();
        try {
            object.put("subcatid",suid);
        }catch (Exception e){}
        String Url = "https://shakibmohd.000webhostapp.com/api_shop/subname_catname_delete.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(context, response.getString("status"), Toast.LENGTH_SHORT).show();
                    Delete_position(posi);
                    notifyItemChanged(posi);
                    notifyDataSetChanged();
                    pDialog.hide();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                pDialog.hide();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
