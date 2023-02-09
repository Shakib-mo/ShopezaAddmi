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
import android.widget.ImageView;
import android.widget.PopupMenu;
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
import com.shakib.shoprezaaddmi.DisPlay_Add_Categry_Act;
import com.shakib.shoprezaaddmi.Display_Product_Add;
import com.shakib.shoprezaaddmi.R;
import com.shakib.shoprezaaddmi.Category_Set_get;
import com.shakib.shoprezaaddmi.Update_Disply_add_categry_Act;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MyCategory_Adapter extends RecyclerView.Adapter<MyCategory_Adapter.ViewHolder>{
    Activity context;
    ArrayList<Category_Set_get> arrayList;

    private static int in = 0;
    public MyCategory_Adapter(Activity cont, ArrayList<Category_Set_get> arry) {
        arrayList = arry;
        context = cont;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_v_add_cotegry, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyCategory_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Category_Set_get setGet = arrayList.get(position);
        Picasso.with(context).load(setGet.getImage()).into(holder.imagem);
        holder.te_name.setText(arrayList.get(position).getName());

        holder.imagem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.camera:
                                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                ((DisPlay_Add_Categry_Act)context).catid=arrayList.get(position).getCatid();
                                context.startActivityForResult(i, 100);
                                return true;
                            case R.id.gallery:
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                ((DisPlay_Add_Categry_Act)context).catid=arrayList.get(position).getCatid();
                                context.startActivityForResult(intent,in);
                                return true;
                        }
                        return true;
                    }
                });
                return true;
            }
        });
        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Update_Disply_add_categry_Act.class);
                intent.putExtra("cat_id",arrayList.get(position).getCatid());
                intent.putExtra("name", arrayList.get(position).getName());
                context.startActivity(intent);
            }
        });
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to Delete item?");
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
                        Delete_from_php(arrayList.get(position).getCatid(), position);
                        notifyDataSetChanged();
                        notifyItemChanged(position);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagem, img_delete, img_edit;
        TextView te_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            te_name = itemView.findViewById(R.id.te_pro_name);
            img_delete = itemView.findViewById(R.id.img_delete);
            img_edit = itemView.findViewById(R.id.img_edit);
            imagem = itemView.findViewById(R.id.image_view_ca);
        }
    }
    private void Deleted_co(int position) {
        arrayList.remove(position);
        notifyItemChanged(position);
    }

    public void Delete_from_php(String catid, int po) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject object = new JSONObject();
        ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading");
        pDialog.show();
        try {
            object.put("cat_id",catid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "https://shakibmohd.000webhostapp.com/api_shop/reza_delete.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(context, response.getString("Status"), Toast.LENGTH_SHORT).show();
                    notifyItemChanged(po);
                    notifyDataSetChanged();
                    Deleted_co(po);
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
}