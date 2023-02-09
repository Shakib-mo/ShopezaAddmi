package com.shakib.shoprezaaddmi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
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
import com.shakib.shoprezaaddmi.adapter.MyCategory_Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DisPlay_Add_Categry_Act extends AppCompatActivity {

    ImageView im_add,im_back;
    RequestQueue requestQueue,requestQueue_pic;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    String string_image;
    public static int i = 0;
    Bitmap bitmap;
    public String catid;

    ArrayList<Category_Set_get> arrayList= new ArrayList<>();
    MyCategory_Adapter adapter;
    void Display_Data(){
        requestQueue = Volley.newRequestQueue(DisPlay_Add_Categry_Act.this);
        progressBar.setVisibility(View.VISIBLE);
        String url = "https://shakibmohd.000webhostapp.com/api_shop/reza_display.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray array = response;
                JSONObject object = new JSONObject();
               try {
                   for (int i = 0;i<array.length();i++){
                       object =array.getJSONObject(i);
                       arrayList.add(new Category_Set_get(object.getString("catid"),object.getString("name"),
                               object.getString("pic")));
                   }
                   adapter = new MyCategory_Adapter(DisPlay_Add_Categry_Act.this,arrayList);
                   recyclerView.setAdapter(adapter);
                   adapter.notifyDataSetChanged();
                   progressBar.setVisibility(View.GONE);

               }catch (Exception e){
               }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DisPlay_Add_Categry_Act.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    void Save_pic(String cat,String pic){
        String Url = "https://shakibmohd.000webhostapp.com/api_shop/cat_image.php";
        requestQueue_pic = Volley.newRequestQueue(DisPlay_Add_Categry_Act.this);
        JSONObject object = new JSONObject();
        try {
            object.put("cat_id",cat);
            object.put("pic",pic);
        }catch (Exception e){
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(DisPlay_Add_Categry_Act.this, response.getString("status"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DisPlay_Add_Categry_Act.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue_pic.add(jsonObjectRequest);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dssply_add_categry);

        recyclerView = findViewById(R.id.recy_clerviw);
        im_add = findViewById(R.id.image_add);
        progressBar = findViewById(R.id.progress_bar);
        im_back = findViewById(R.id.im_back);

        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DisPlay_Add_Categry_Act.this));
        Display_Data();

        im_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisPlay_Add_Categry_Act.this,Edt_add_categry_Act.class);
                startActivity(intent);
                finish();
            }
        });


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
            Save_pic(catid,string_image);
        }
        if (requestCode ==i && resultCode==RESULT_OK && data!=null){
            try {
                bitmap = MediaStore.Images.Media.getBitmap(DisPlay_Add_Categry_Act.this.getContentResolver(),data.getData());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,70,byteArrayOutputStream);
                byte[] byt_arr = byteArrayOutputStream.toByteArray();
                string_image = Base64.encodeToString(byt_arr,0);
                //imageView.setImageBitmap(bitmap);
                Save_pic(catid,string_image);

            }catch (Exception e){
            }
        }
    }
}