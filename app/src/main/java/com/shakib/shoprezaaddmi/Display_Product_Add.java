package com.shakib.shoprezaaddmi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.shakib.shoprezaaddmi.adapter.MyProductAdapter;
import com.shakib.shoprezaaddmi.adapter.MySubcategory_Adapter;
import com.shakib.shoprezaaddmi.adapter.SubItem_Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Display_Product_Add extends AppCompatActivity {

    ImageView bt_back;
    ImageButton image_add_product;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    RequestQueue requestQueue;
    MyProductAdapter adapter;

    ArrayList<Product_Get_Set> arrayList = new ArrayList<>();

    private static int i = 0;
    private static Bitmap bitmap;
    String string_image;
    public String productid;

    void Save_Pic(String productid, String pic){
        RequestQueue requestQueue = Volley.newRequestQueue(Display_Product_Add.this);
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
                    Toast.makeText(Display_Product_Add.this, response.getString("status"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Display_Product_Add.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }



    void Disply_product(){
        progressBar.setVisibility(View.VISIBLE);
       requestQueue = Volley.newRequestQueue(Display_Product_Add.this);
       String Url = "https://shakibmohd.000webhostapp.com/api_shop/product_Display.php";
       JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Url, null, new Response.Listener<JSONArray>() {
           @Override
           public void onResponse(JSONArray response) {
               JSONArray array = response;
               JSONObject object = new JSONObject();
               try {
                   for (int i = 0;i<array.length();i++){
                       object = array.getJSONObject(i);
                       arrayList.add(new Product_Get_Set(object.getString("productid"),object.getString("productname")
                               ,object.getString("price"),object.getString("offerprice"),object.getString("subcatid")
                               ,object.getString("description"),object.getString("pic")));
                   }
                   adapter = new MyProductAdapter(Display_Product_Add.this,arrayList);
                   recyclerView.setAdapter(adapter);
                   adapter.notifyDataSetChanged();
                   progressBar.setVisibility(View.GONE);
               }catch (Exception e){

               }
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(Display_Product_Add.this, error.toString(), Toast.LENGTH_SHORT).show();
               progressBar.setVisibility(View.GONE);
           }
       });
       requestQueue.add(jsonArrayRequest);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_product_add);
        bt_back = findViewById(R.id.back_);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar_pro);
        image_add_product = findViewById(R.id.image_add_product);



        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        Disply_product();

        image_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Display_Product_Add.this, Product_Edit_Act.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
            }
        });
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if(ContextCompat.checkSelfPermission(Display_Product_Add.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Display_Product_Add.this, new String[] {
                    Manifest.permission.CAMERA}, 15);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //imageView =(ImageView) findViewById(R.id.image_product);

        if (requestCode == 100) {
            bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            byte[] byte_arr = stream.toByteArray();
            string_image = Base64.encodeToString(byte_arr, 0);
            //imageView.setImageBitmap(bitmap);
            Save_Pic(productid,string_image);
        }
        if (requestCode ==i && resultCode==RESULT_OK && data!=null){
            try {
                bitmap = MediaStore.Images.Media.getBitmap(Display_Product_Add.this.getContentResolver(),data.getData());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,70,byteArrayOutputStream);
                byte[] byt_arr = byteArrayOutputStream.toByteArray();
                string_image = Base64.encodeToString(byt_arr,0);
                //imageView.setImageBitmap(bitmap);
                Save_Pic(productid,string_image);

            }catch (Exception e){
            }
        }
    }
}