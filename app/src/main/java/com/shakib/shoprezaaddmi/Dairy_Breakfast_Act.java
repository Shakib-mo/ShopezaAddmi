package com.shakib.shoprezaaddmi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import com.shakib.shoprezaaddmi.adapter.Breackfast_Adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Dairy_Breakfast_Act extends AppCompatActivity {

    ImageView im_back,image_add;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    Breackfast_Adapter adapter;
    public String string_image;
    Bitmap bitmap;
    public static int in =0;
    public String b_id = "";
    ArrayList<Category_Set_get> arrayList = new ArrayList<>();
    void Display_breackfast(){
        progressBar.setVisibility(View.VISIBLE);
        requestQueue = Volley.newRequestQueue(Dairy_Breakfast_Act.this);
        String Url = "https://shakibmohd.000webhostapp.com/api_shop/breackfast_display.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray array = response;
                JSONObject object=new JSONObject();
                try {
                    for (int i =0;i<array.length();i++){
                        object = array.getJSONObject(i);
                        arrayList.add(new Category_Set_get(object.getString("b_id"),object.getString("b_name"),
                                object.getString("b_kg"),object.getString("b_price"),object.getString("pic")));

                    }
                    adapter = new Breackfast_Adapter(Dairy_Breakfast_Act.this,arrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);

                }catch (Exception e){
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Dairy_Breakfast_Act.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy_breakfast);
        im_back = findViewById(R.id.im_back);
        image_add = findViewById(R.id.image_add);
        recyclerView = findViewById(R.id.dery_reclycer_view);
        progressBar = findViewById(R.id.progress);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        Display_breackfast();

        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        image_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dairy_Breakfast_Act.this,Dairy_Breakfast_Edt_Act.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageView = findViewById(R.id.image_br);
        if (requestCode==100){
            bitmap =(Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,70,stream);
            byte[] array = stream.toByteArray();
            string_image = Base64.encodeToString(array,0);
            imageView.setImageBitmap(bitmap);
        }
        if (requestCode==in && resultCode==RESULT_OK && data !=null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,70,stream);
            byte[] arr = stream.toByteArray();
            string_image = Base64.encodeToString(arr,0);
            imageView.setImageBitmap(bitmap);

        }
    }
}