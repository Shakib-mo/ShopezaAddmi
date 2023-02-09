package com.shakib.shoprezaaddmi.adapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shakib.shoprezaaddmi.Display_Product_Add;
import com.shakib.shoprezaaddmi.Product_Get_Set;
import com.shakib.shoprezaaddmi.Product_Update_Act;
import com.shakib.shoprezaaddmi.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MyProductAdapter extends Adapter<MyProductAdapter.ViewHolder>{
    Activity context;
    ArrayList<Product_Get_Set> arrayList;
    private static int i = 0;
    public MyProductAdapter(Activity conte,ArrayList<Product_Get_Set> array){
        context = conte;
        arrayList = array;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyProductAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product_Get_Set getSet = arrayList.get(position);
        holder.te_description.setText(arrayList.get(position).getPr_description());
        holder.te_pr_name.setText(arrayList.get(position).getPr_productname());
        holder.te_price.setText(arrayList.get(position).getPr_price());
        holder.te_offer_price.setText(arrayList.get(position).getPr_offer_price());
        Picasso.with(context).load(getSet.getPr_image()).into(holder.imageView);

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
                                ((Display_Product_Add)context).productid=arrayList.get(position).getPr_productid();
                                context.startActivityForResult(intent,i);
                                return true;
                            case R.id.camera:
                                Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                ((Display_Product_Add)context).productid=arrayList.get(position).getPr_productid();
                                context.startActivityForResult(in, 100);
                                return true;
                        }
                        return true;
                    }
                });
                return true;
            }
        });

        Intent intent = new Intent();
        intent.putExtra("prid",arrayList.get(position).getPr_productid());


        holder.bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Product_Update_Act.class);
                intent.putExtra("prid",arrayList.get(position).getPr_productid());
                intent.putExtra("na",arrayList.get(position).getPr_productname());
                intent.putExtra("pr",arrayList.get(position).getPr_price());
                intent.putExtra("off",arrayList.get(position).getPr_offer_price());
                intent.putExtra("im",arrayList.get(position).getPr_image());
                intent.putExtra("de",arrayList.get(position).getPr_description());
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_sight,R.anim.slide_sight);

            }

        });
        holder.bt_delete.setOnClickListener(new View.OnClickListener() {
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
                        Deleted_from_server(arrayList.get(position).getPr_productid(),position);
                        notifyDataSetChanged();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bt_delete,imageView;
        Button bt_update;
        TextView te_pr_name,te_price,te_offer_price,te_description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            te_description = itemView.findViewById(R.id.te_description);
            te_pr_name = itemView.findViewById(R.id.te_product_name);
            te_price = itemView.findViewById(R.id.te_product_price);
            te_offer_price = itemView.findViewById(R.id.te_product_offer_price);
            imageView = itemView.findViewById(R.id.image_product);
            bt_update = itemView.findViewById(R.id.bt_update);
            bt_delete = itemView.findViewById(R.id.bt_delete);
        }
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
            object.put("productid",suid);
        }catch (Exception e){}
        String Url = "https://shakibmohd.000webhostapp.com/api_shop/product_delete.php";
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
    void Save_Pic(String productid, String pic){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String Url = "https://shakibmohd.000webhostapp.com/api_shop/product_image.php";
        JSONObject object = new JSONObject();
        try {
            object.put("productid",productid);
            object.put("pic",pic);
        }catch (Exception e){
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(context, response.getString("status"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}